# How to Test & Continue Development

## 🚀 Testing the Fixes

### Step 1: Build the Mod
```powershell
cd g:\WitcherCraft
.\gradlew build --no-daemon
```

Expected output: `BUILD SUCCESSFUL` ✅

### Step 2: Run the Game
```powershell
.\gradlew runClient --no-daemon
```

This launches Minecraft with your mod loaded.

### Step 3: Test Items
1. Create new world or enter existing one
2. Open creative inventory (E key)
3. Search for these items:
   - `silver_sword` ✅
   - `steel_sword` ✅
   - `swallow_potion` ✅
   - `cat_potion` ✅
   - `celandine` ✅
   - `drowner_brain` ✅

All should appear and be placeable in inventory.

### Step 4: Test Signs
1. Pick up any item (or stay in survival)
2. Press **Q** key
   - Should see: "§6Casting Igni!" message on action bar
   - Should see: Fire particles in front of player
   - Should damage entities within range
3. Press **E**, **R**, **F**, **G** keys
   - Should see message for each sign
   - Other signs not yet implemented (but framework ready)

### Step 5: Test Cooldown
1. Press Q to cast Igni
2. Try pressing Q immediately again
3. Should wait ~1.5 seconds before next cast works
4. Confirms cooldown system working

---

## 🔧 Next Development Tasks

### Priority 1: Implement Remaining Signs (2-3 hours)

Create 4 new sign classes following the IgniSign.java pattern:

#### 1. AardSign.java (Knockback)
```java
public class AardSign extends WitcherSign {
    public AardSign() {
        super("aard", 100, 20); // 5 sec cooldown, 20 mana
    }
    
    @Override
    public boolean cast(Player player, Level level) {
        Vec3 lookVec = player.getLookAngle();
        Vec3 playerPos = player.getEyePosition();
        
        // Get nearby entities in cone
        AABB searchBox = new AABB(playerPos, playerPos).inflate(5.0);
        List<Entity> entities = level.getEntities(player, searchBox);
        
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity target && target != player) {
                // Check if in cone
                Vec3 toEntity = entity.position().subtract(playerPos).normalize();
                double angle = Math.acos(Math.min(1.0, Math.max(-1.0, lookVec.dot(toEntity)))) * (180.0 / Math.PI);
                
                if (angle <= 22.5) {
                    // Push entity away
                    Vec3 knockback = lookVec.scale(2.0); // Adjust power as needed
                    target.push(knockback.x, knockback.y, knockback.z);
                    target.hurt(target.damageSources().magic(), 2.0f);
                }
            }
        }
        
        // Particle effects
        if (level instanceof ServerLevel serverLevel) {
            // Add dust particles
        }
        
        return true;
    }
}
```

#### 2. QuenSign.java (Shield)
```java
public class QuenSign extends WitcherSign {
    private static final int SHIELD_DURATION = 200; // 10 seconds
    private static final float MAX_SHIELD = 20.0f;
    
    @Override
    public boolean cast(Player player, Level level) {
        // Apply damage absorption effect
        player.addEffect(new MobEffectInstance(
            MobEffects.DAMAGE_RESISTANCE,
            SHIELD_DURATION,
            1
        ));
        
        // Could also use damage absorption effect
        // Particles and feedback
        
        return true;
    }
}
```

#### 3. YrdenSign.java (Slowing Trap)
```java
public class YrdenSign extends WitcherSign {
    @Override
    public boolean cast(Player player, Level level) {
        // Create area of effect on ground
        Vec3 playerPos = player.getEyePosition();
        
        // Get entities in area
        AABB areaBox = new AABB(playerPos, playerPos).inflate(5.0, 1.0, 5.0);
        List<Entity> entities = level.getEntities(player, areaBox);
        
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity target && target != player) {
                // Apply slowness
                target.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SLOWDOWN,
                    100, // 5 seconds
                    1
                ));
            }
        }
        
        return true;
    }
}
```

#### 4. AxiiSign.java (Confusion)
```java
public class AxiiSign extends WitcherSign {
    @Override
    public boolean cast(Player player, Level level) {
        Vec3 playerPos = player.getEyePosition();
        
        // Get nearby creatures
        AABB searchBox = new AABB(playerPos, playerPos).inflate(5.0);
        List<Entity> entities = level.getEntities(player, searchBox);
        
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity target && target != player) {
                // Apply confusion (nausea)
                target.addEffect(new MobEffectInstance(
                    MobEffects.CONFUSION,
                    100, // 5 seconds
                    1
                ));
                
                // Optional: make them attack each other (advanced)
            }
        }
        
        return true;
    }
}
```

#### 5. Register in CastSignPacket.java

Add to the server handler switch statement:
```java
case "aard":
    new AardSign().cast(player, player.level());
    break;
case "quen":
    new QuenSign().cast(player, player.level());
    break;
case "yrden":
    new YrdenSign().cast(player, player.level());
    break;
case "axii":
    new AxiiSign().cast(player, player.level());
    break;
```

---

### Priority 2: Create Visual Assets (2-3 hours)

#### Create Item Textures
1. Create 16x16 PNG images for each item
2. Place in: `src/main/resources/assets/witchercraft/textures/item/`
3. Files needed:
   - `silver_sword.png`
   - `steel_sword.png`
   - `swallow_potion.png`
   - `cat_potion.png`
   - `celandine.png`
   - `drowner_brain.png`

#### Create Item Models
1. Create JSON files for each item model
2. Place in: `src/main/resources/assets/witchercraft/models/item/`
3. Example `silver_sword.json`:
```json
{
  "parent": "item/handheld",
  "textures": {
    "layer0": "witchercraft:item/silver_sword"
  }
}
```

#### Add Translations
Create `src/main/resources/assets/witchercraft/lang/en_us.json`:
```json
{
  "item.witchercraft.silver_sword": "Silver Sword",
  "item.witchercraft.steel_sword": "Steel Sword",
  "item.witchercraft.swallow_potion": "Swallow Potion",
  "item.witchercraft.cat_potion": "Cat Potion",
  "item.witchercraft.celandine": "Celandine",
  "item.witchercraft.drowner_brain": "Drowner Brain",
  "block.witchercraft.alchemy_table": "Alchemy Table",
  "block.witchercraft.grindstone_witcher": "Witcher's Grindstone"
}
```

---

### Priority 3: Stamina/Mana System (2-3 hours)

This would involve:
1. Creating a player data attachment for stamina
2. Adding mana costs to signs
3. Creating HUD display for stamina bar
4. Implementing mana regeneration
5. Preventing signs from casting without enough mana

---

## 📋 Testing Checklist Before Moving Forward

- [ ] Mod builds successfully
- [ ] Game launches without crashes
- [ ] All 6 items appear in creative mode
- [ ] All 2 blocks can be placed
- [ ] Q key causes Igni effect (fire particles, damage)
- [ ] E/R/F/G keys show messages (for unimplemented signs)
- [ ] Cooldown works (can't spam casts)
- [ ] No errors in console

---

## 🎯 Suggested Order of Work

1. **This session:** Test the fixes work
2. **Next session:** Implement Aard sign (easy, similar to Igni)
3. **Then:** Implement Quen, Yrden, Axii (progressively more complex)
4. **After:** Create basic textures and models
5. **Finally:** Add stamina system for depth

---

## 💡 Tips for Development

- Test after each change: `./gradlew runClient`
- Use `Witchercraft.LOGGER.info()` for debug messages
- Keep the sign system modular - easy to extend
- Add particle effects for visual feedback
- Use cooldowns to prevent spam
- Test with multiple players if possible

---

**Status: ✅ Ready for testing and continued development**

See `FIXES_APPLIED.md` for technical details.
