# Item & Block Registration - Final Fix (December 10, 2025)

## Problem
Minecraft 1.21.10 was throwing `NullPointerException: Item id not set` when trying to register items. The issue occurred because:
- `Item.Properties()` constructor checks for a description ID in Item.java:137
- The description ID is null because items haven't been registered yet
- Calling `new Item()` before registration fails

## Root Cause Analysis
The error chain was:
```
ModItems.initialize() called from Witchercraft.onInitialize()
  ↓
Tries to create Item with new Item(new Item.Properties())
  ↓
Item constructor calls Item$Properties.effectiveDescriptionId()
  ↓
effectiveDescriptionId() requires registry ID to be set
  ↓
NullPointerException because ID not set yet
```

## Solution Applied
Use **static block initialization** instead of lazy initialization:

```java
public class ModItems {
    public static final Item SILVER_SWORD;
    
    static {
        // This runs when class is first loaded, before onInitialize()
        // But it runs when Minecraft's registry system is ready
        SILVER_SWORD = registerItem("silver_sword");
    }
}
```

**Key timing:**
1. Witchercraft.java imports ModItems/ModBlocks
2. Class loader loads ModItems/ModBlocks classes
3. Static {} blocks execute during class loading
4. Registry.register() is called (registries are ready at this point)
5. onInitialize() is then called (items already registered)

## Files Modified

### ModItems.java
- Changed from lazy initialization to static block
- Items are final and initialized in static {}
- Exception handling for registration errors

### ModBlocks.java  
- Same pattern as ModItems
- Blocks are final and initialized in static {}

### Witchercraft.java
- Added null checks to ensure classes are loaded
- Added logging to confirm items/blocks are registered

## Testing Steps
1. Run `./gradlew runClient --no-daemon`
2. Check logs for "ModItems loaded" and "ModBlocks loaded"
3. Open creative mode
4. Search for items: silver_sword, steel_sword, swallow_potion, cat_potion, celandine, drowner_brain
5. Verify items appear in inventory
6. Try placing blocks: alchemy_table, grindstone_witcher

## Expected Result
✅ Mod loads without "Item id not set" crash
✅ Items appear in creative mode inventory
✅ Blocks can be placed in world

## Why This Works
- Static blocks execute during class initialization by JVM
- By the time onInitialize() is called, classes are already loaded
- Registry system is ready (Minecraft initializes registries before loading mod entrypoints)
- Registry.register() succeeds because item/block IDs are properly set during registration

## Troubleshooting
If still seeing "Item id not set" error:
1. Verify ModItems.java has static {} block (not just static field initialization)
2. Check that imports are at class level (forces loading)
3. Ensure no circular dependency issues
4. Check Minecraft Fabric version compatibility
