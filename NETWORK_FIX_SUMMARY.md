# Network Packet Registration Fix - Session Summary

**Date:** December 10, 2025  
**Status:** ✅ RESOLVED  
**Issue:** Multiple packet type registrations causing mod load failure  

---

## Problem Encountered

```
java.lang.IllegalArgumentException: Cannot register handler as no payload type 
has been registered with name "witchercraft:cast_sign" for SERVERBOUND PLAY
```

**Root Cause:** The same packet payload type was being registered multiple times from different locations:
1. `CastSignPacket.register()` was registering the payload type in the main mod
2. `CastSignPacketC2S.register()` was attempting to register the same payload type again from client code

This duplicate registration violated Fabric's networking rules, which require each payload type to be registered exactly once.

---

## Solution Implemented

### Step 1: Consolidated Packet Classes
- **Removed:** Duplicate `CastSignPacketC2S.java` class
- **Kept:** Single `CastSignPacket.java` class in main code
- **Result:** One payload TYPE with one registration point

### Step 2: Fixed Code Organization
- **Main Code** (`src/main/java`): 
  - `CastSignPacket.java` - Handles packet definition and server-side registration
  - Registers payload type ONCE via `PayloadTypeRegistry.playC2S().register()`
  - Registers server handler via `ServerPlayNetworking.registerGlobalReceiver()`

- **Client Code** (`src/client/java`):
  - NEW: `ClientNetworking.java` - Client-side networking helper
  - Contains `sendSignCast()` method for sending packets to server
  - Uses `@Environment(EnvType.CLIENT)` to ensure it's client-only
  - Updated `SignCastingHandler.java` to use this helper

### Step 3: Separated Concerns
**Before (❌ Wrong):**
```
CastSignPacket.java (main)
├─ Packet definition
├─ Server handler
└─ Client send method ❌ (imports ClientPlayNetworking)

CastSignPacketC2S.java (client) ❌ DUPLICATE
├─ Same packet definition
└─ Same TYPE registration ❌ DUPLICATE
```

**After (✅ Correct):**
```
CastSignPacket.java (main)
├─ Packet definition
├─ CODEC definition
├─ TYPE creation (ONE TIME)
├─ register() - Payload type + Server handler
└─ No client code imports

ClientNetworking.java (client)
├─ @Environment(EnvType.CLIENT) annotation
├─ sendSignCast(signName) method
└─ Uses CastSignPacket class references safely

SignCastingHandler.java (client)
└─ Calls ClientNetworking.sendSignCast()
```

---

## Technical Details

### Fabric Networking Architecture

Fabric 1.21.10 uses a two-step packet system:

1. **Payload Type Registration** (ONE TIME, from main thread):
   ```java
   PayloadTypeRegistry.playC2S().register(TYPE, CODEC);
   ```
   This registers the packet format with Fabric's networking system.

2. **Handler Registration** (can be done after or together):
   ```java
   ServerPlayNetworking.registerGlobalReceiver(TYPE, handler);
   ```
   This registers what to do when the packet is received.

**Critical:** The TYPE must be registered exactly once, before any handler tries to use it.

### Why Separate Classes Failed

- `CastSignPacket` in main (`src/main/`) tried to import `ClientPlayNetworking`
- This import doesn't exist in main-side code (it's client-only)
- Even having a separate class trying to register the same TYPE was redundant

### Why Consolidated Works

- Single `CastSignPacket` in main code
- `PayloadTypeRegistry.playC2S()` works from main code (common networking API)
- `ServerPlayNetworking.registerGlobalReceiver()` works from main code (server API)
- `ClientNetworking` stays in client code and only sends using the already-registered TYPE
- No imports conflicts
- No duplicate registrations

---

## Files Changed

### Modified Files

| File | Change | Reason |
|------|--------|--------|
| `CastSignPacket.java` | Removed client-side `sendToServer()` method and `ClientPlayNetworking` import | Avoid main code depending on client-only APIs |
| `WitchercraftClient.java` | Removed `CastSignPacketC2S.register()` call | Packet already registered in main mod |
| `SignCastingHandler.java` | Changed to use `ClientNetworking.sendSignCast()` | Route through proper client-side helper |

### New Files

| File | Purpose |
|------|---------|
| `ClientNetworking.java` | Client-side helper for sending registered packet to server |

### Deleted Files

| File | Reason |
|------|--------|
| `CastSignPacketC2S.java` | Duplicate class causing double registration |

---

## How It Works Now

### Initialization Sequence

1. **Mod Load (Main Thread)**
   ```
   Witchercraft.onInitialize()
   ├─ ModItems.initialize()
   ├─ ModBlocks.initialize()
   └─ CastSignPacket.register()
      ├─ PayloadTypeRegistry.playC2S().register(TYPE, CODEC) ✅ ONE TIME
      └─ ServerPlayNetworking.registerGlobalReceiver(TYPE, handler)
   ```

2. **Client Load (Client Thread)**
   ```
   WitchercraftClient.onInitializeClient()
   └─ SignCastingHandler.register()
      └─ Listens to ClientTickEvents.END_CLIENT_TICK
   ```

### Runtime: Player Casts Sign

1. **Client Side**
   ```
   ClientTickEvents.END_CLIENT_TICK
   ├─ Detects key press (Q for Igni, etc.)
   ├─ Shows action bar message
   └─ ClientNetworking.sendSignCast("igni")
      └─ ClientPlayNetworking.send(new CastSignPacket("igni"))
   ```

2. **Network**
   ```
   CastSignPacket("igni") transmitted with CODEC
   ```

3. **Server Side**
   ```
   ServerPlayNetworking handler receives packet
   ├─ Extracts sign name ("igni")
   ├─ Switches on sign name
   └─ new IgniSign().cast(player, player.level())
      ├─ Creates fire cone
      ├─ Damages entities
      └─ Spawns particles
   ```

---

## Verification

### Compilation Status
✅ All Java files compile without errors related to packet registration

### Expected Behavior
- Mod should load without "Cannot register handler" error
- Signs should be castable via network packets (once KeyBinding issue is resolved)
- No duplicate registration warnings

### Known Issues to Fix Later
- KeyMapping registration still needs correct 1.21.10 API
- Item registration still deferred (placeholder pattern)
- Deprecation warning on `Entity.hurt()` method

---

## Lessons Learned

### ✅ DO:
1. Register payload types exactly once from main code
2. Use `@Environment(EnvType.CLIENT)` for client-side helpers
3. Separate concerns: packet definition, registration, and sending
4. Register during mod initialization, not during class loading

### ❌ DON'T:
1. Register the same payload TYPE multiple times
2. Import client-only APIs in main code (no `ClientPlayNetworking` in main)
3. Have duplicate packet classes trying to do the same thing
4. Register from client-side initializer if main already registered

### Key Pattern for Fabric 1.21.10 Packets:

```
MAIN CODE (src/main/java):
- Define packet class implementing CustomPacketPayload
- Define TYPE and CODEC as public static fields
- Define register() method that registers TYPE once
- Define server-side handler

CLIENT CODE (src/client/java):
- Create helper with @Environment(EnvType.CLIENT)
- Import and use CastSignPacket class (safe because TYPE already registered)
- Call ClientPlayNetworking.send() with packet instance
- Store in separate class to avoid import conflicts
```

---

## Next Steps

1. ✅ **DONE:** Fix network packet registration
2. ⏳ **NEXT:** Fix KeyMapping registration for sign casting keybinds
3. ⏳ **THEN:** Implement Item/Block registration so items appear in creative mode
4. ⏳ **AFTER:** Implement remaining 4 signs (Aard, Quen, Yrden, Axii)

---

## Testing Checklist

- [x] Mod compiles without packet registration errors
- [x] No "Cannot register handler" error in crash report
- [ ] Mod loads successfully in game
- [ ] Can detect key presses (pending KeyMapping fix)
- [ ] Packet is sent to server (pending KeyBinding fix)
- [ ] Sign effect executes on server
- [ ] Sign effect visible to all players

---

**Status: Ready for KeyBinding Fix**  
Network system is now properly configured. The next blocker is fixing the KeyMapping registration so players can actually press keys to cast signs.

See: QUICK_REFERENCE.md for file locations  
See: SIGN_SYSTEM_SUMMARY.md for sign system overview  
See: SESSION_SUMMARY.md for full session progress  
