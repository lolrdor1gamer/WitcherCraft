# WitcherCraft Development Roadmap

## Project Overview
A Minecraft Fabric 1.21.10 mod inspired by The Witcher series, featuring signs, alchemy, monsters, and Witcher-style gameplay.

**Dependencies:**
- YACL (Yet Another Config Lib) - Configuration
- Malilib - UI and utilities
- MagicLib - Magic system support
- TCDCommons - Common utilities
- Architectury API - Cross-platform support

---

## Phase 1: Foundation (START HERE) ✓ STARTED

### 1.1 Project Setup ✓
- [x] Basic mod structure
- [x] Gradle dependencies
- [x] Main mod class

### 1.2 Registry System ✓
- [x] Create ModItems registry
- [x] Create ModBlocks registry
- [x] Update block/potion codecs to 1.21 APIs
- [ ] Create ModEffects registry
- [ ] Create ModEntities registry
- [ ] Create ModSounds registry

### 1.3 Basic Items (NEXT STEP)
- [x] Silver Sword (basic)
- [x] Steel Sword (basic)
- [x] Implement proper sword functionality
- [x] Add sword damage and attack speed
- [x] Create item models and textures
- [x] Basic potions (Swallow, Cat)
- [x] Implement potion effects

---

## Phase 2: Signs System (Core Feature)

### 2.1 Sign Infrastructure ✓
- [x] WitcherSign base class
- [x] Igni (Fire) sign implementation
- [x] Sign keybinding system
- [ ] Stamina/Mana system
- [ ] Sign cooldown manager
- [ ] Visual effects and particles

### 2.2 Implement All Signs
- [x] **Igni** - Fire blast (DONE)
- [x] **Aard** - Force push/knockback
- [x] **Quen** - Protective shield
- [ ] **Yrden** - Magic trap (slows enemies)
- [ ] **Axii** - Mind control/confusion

### 2.3 Sign Upgrades
- [ ] Alternate sign modes
- [ ] Sign intensity levels
- [ ] Skill tree integration

---

## Phase 3: Alchemy System

### 3.1 Ingredients
- [x] Basic herbs (Celandine)
- [x] Monster parts (Drowner Brain)
- [ ] More herbs (Verbena, Wolfsbane, etc.)
- [ ] More monster drops
- [ ] Ore/mineral ingredients

### 3.2 Potions
- [x] **Swallow** - Health regeneration
- [x] **Cat** - Night vision
- [x] **Thunderbolt** - Increased damage
- [x] **White Raffard's Decoction** - Instant healing
- [x] **Tawny Owl** - Stamina regeneration
- [ ] Potion toxicity system
- [x] Potion brewing mechanics

### 3.3 Oils & Bombs
- [ ] Blade oils (Necrophage, Specter, etc.)
- [ ] Bombs (Grapeshot, Samum, etc.)
- [ ] Decoctions (long-lasting buffs)

### 3.4 Crafting
- [x] Alchemy Table block
- [x] Potion data reload listeners migrated to new resource pipeline
- [ ] Alchemy Table functionality/GUI
- [ ] Recipe system integration
- [ ] Witcher's laboratory structure

---

## Phase 4: Combat & Weapons

### 4.1 Witcher Swords
- [x] Proper damage values
- [x] Silver sword effectiveness vs monsters
- [x] Steel sword effectiveness vs humans
- [ ] Durability and repair system
- [ ] Enchantment compatibility

### 4.2 Combat Mechanics
- [ ] Dodge/roll mechanic
- [ ] Parry system
- [ ] Critical strikes
- [ ] Combat animations (if possible)
- [ ] Adrenaline/Focus system

### 4.3 Armor
- [ ] Witcher armor sets (School of the Wolf, Cat, Bear, etc.)
- [ ] Armor stats and bonuses
- [ ] Armor upgrades

---

## Phase 5: Monsters & Entities

### 5.1 Basic Monsters
- [ ] **Drowner** - Aquatic necrophage
- [ ] **Nekker** - Small aggressive creature
- [ ] **Ghoul** - Necrophage
- [ ] Monster AI behaviors
- [ ] Monster drops

### 5.2 Advanced Monsters
- [ ] **Griffin** - Flying beast
- [ ] **Wraith** - Spectral enemy
- [ ] **Werewolf** - Cursed creature
- [ ] **Leshen** - Forest spirit
- [ ] Boss monsters (Fiend, etc.)

### 5.3 Monster Contracts
- [ ] Contract system
- [ ] Monster tracking
- [ ] Rewards system

---

## Phase 6: Skills & Character Progression

### 6.1 Skill Trees
- [ ] Combat skills
- [ ] Sign skills
- [ ] Alchemy skills
- [ ] Skill points system
- [ ] Leveling system

### 6.2 Mutations
- [ ] Mutation system
- [ ] Special abilities
- [ ] Visual effects

---

## Phase 7: World Generation & Structures

### 7.1 Structures
- [ ] Abandoned sites
- [ ] Monster nests
- [ ] Places of Power (skill point locations)
- [ ] Witcher keeps/schools
- [x] Kaedwen village blueprint set (streets, forge, tavern, watchtower, etc.)
- [ ] Export Kaedwen blueprints to `.nbt` templates and wire into pools
- [ ] Validate Kaedwen structure processor lists/biome tags in-game

### 7.2 World Integration
- [ ] Custom biomes (optional)
- [x] Biome structure tags for Kaedwen settlement placement
- [ ] Herb spawning in world
- [ ] Monster spawning rules

---

## Phase 8: Configuration & UI

### 8.1 YACL Configuration
- [ ] Fix WitcherConfig.java
- [ ] Sign settings (cooldowns, damage)
- [ ] Potion settings
- [ ] Monster settings
- [ ] Difficulty options

### 8.2 UI Elements
- [ ] Sign selection wheel
- [ ] Alchemy UI
- [ ] Skill tree UI
- [ ] Bestiary UI
- [ ] Inventory integration

---

## Phase 9: Polish & Features

### 9.1 Visual Effects
- [ ] Particle effects for signs
- [ ] Potion drinking effects
- [ ] Witcher senses effect
- [ ] Custom shaders (optional)

### 9.2 Audio
- [ ] Sign casting sounds
- [ ] Sword swing sounds
- [ ] Monster sounds
- [ ] Ambient music (if licensing allows)

### 9.3 Integration
- [ ] Fabric API integration
- [ ] Malilib UI integration
- [ ] Architectury compatibility
- [ ] TCDCommons integration

---

## Immediate Next Steps (Priority Order)

1. **Export Kaedwen structures to `.nbt`** - Rebuild from blueprints, capture via structure blocks, and copy into `data/witchercraft/structures`.
2. **Validate datapack load** - Run client after exports to ensure processor lists, biome tags, and template pools bind with no registry errors.
3. **Fix missing models/textures** - Address the particle/model warnings for notice boards, drying racks, slabs, portcullis, alchemy table, and witcher grindstone.
4. **Build Kaedwen worldgen QA suite** - `/place template` regression pass plus survival-gen checks to confirm doors/POIs aren't blocked.
5. **Set up keybindings** - Wire sign casting to dedicated inputs.
6. **Create stamina/toxicity systems** - Shared resource for signs and potion limits.
7. **Implement Aard sign** - Follow-up sign with knockback tests.
8. **Add potion brewing mechanics** - Recipes for Thunderbolt/Tawny Owl via alchemy table.
9. **Create first monster (Drowner)** - Baseline combat target and loot loop.
10. **Expand YACL config** - User toggles for swords, potions, difficulty, and structure toggles.

---

## Resources Needed

### Assets
- [ ] Item textures (32x32 or 16x16)
- [ ] Block textures
- [ ] Entity models and textures
- [ ] Particle textures
- [ ] Sound effects

### Code Structure
```
src/main/java/org/tgr/witchercraft/
├── Witchercraft.java (main class)
├── config/
│   └── WitcherConfig.java
├── registry/
│   ├── ModItems.java ✓
│   ├── ModBlocks.java ✓
│   ├── ModEffects.java
│   ├── ModEntities.java
│   └── ModSounds.java
├── signs/
│   ├── WitcherSign.java ✓
│   ├── IgniSign.java ✓
│   ├── AardSign.java
│   ├── QuenSign.java
│   ├── YrdenSign.java
│   └── AxiiSign.java
├── items/
│   ├── WitcherSwordItem.java
│   ├── PotionItem.java
│   └── AlchemyIngredientItem.java
├── effects/
│   └── Custom potion effects
├── entities/
│   └── Monster entities
├── skills/
│   └── Skill system
└── util/
    └── Helper classes
```

---

## Testing Checklist

- [x] Items register correctly
- [x] Blocks place and break
- [ ] Signs cast without errors
- [x] Potions apply effects
- [ ] Monsters spawn and behave correctly
- [ ] Combat mechanics work
- [ ] Config saves and loads
- [ ] Structure datapack loads without registry errors
- [ ] No crashes or major bugs

---

## Notes

- Focus on signs system first as it's the most iconic Witcher feature
- Keep performance in mind when implementing particles and effects
- Test multiplayer compatibility regularly
- Consider adding JEI/REI integration for recipe viewing
- Plan for localization (translations)
- Document your code well for future expansion

---

**Current Status:** Foundation phase underway; block/item registries stabilized against 1.21 API changes, potion reload path modernized, and Kaedwen village structure blueprints drafted for export.

**Next Milestone:** Ship playable Kaedwen settlement worldgen (blueprints → `.nbt`, datapack validation, texture/model polish) before resuming sign and stamina systems.
