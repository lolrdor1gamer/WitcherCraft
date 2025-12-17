# WitcherCraft Development Roadmap

## Project Overview
A Minecraft Fabric 1.21.1 mod inspired by The Witcher series, featuring signs, alchemy, monsters, and Witcher-style gameplay.

---

## ✅ Phase 1: Foundation - COMPLETE

### Registry System
- ✅ ModItems, ModBlocks, ModSigns all working
- ✅ Block/item registration updated to 1.21.1 APIs
- ✅ All items have models and textures (placeholders)

### Basic Items
- ✅ Silver & Steel Swords (proper damage, effectiveness messages)
- ✅ Armor sets (Witcher, Feline, Ursine, Griffin, Viper) with Equippable
- ✅ Basic potions with effects (Swallow, Cat, Thunderbolt, etc.)
- ✅ Ingredients (herbs, monster parts)

---

## ✅ Phase 2: Signs System - COMPLETE

### Sign Implementation
- ✅ All 5 signs registered and functional:
  - Aard (Z) - Force push/knockback
  - Igni (X) - Fire blast  
  - Quen (C) - Shield with absorption
  - Yrden (V) - Slowing trap
  - Axii (B) - Confusion effect
- ✅ Keybinding system working
- ✅ Network packets for sign casting
- ✅ Visual effects and particles

### Future Sign Enhancements
- [ ] Alternate sign modes (hold vs tap)
- ✅ Sign intensity from skill tree (Signs attribute scales effects)
- [ ] Stamina/mana cost system
- [ ] Cooldown visual indicators
- [ ] Sign combination casting (Igni+Aard, Yrden+Quen, etc.)
- [ ] Environmental interactions (Igni burns wood, Aard breaks walls)
- [ ] Quick sign radial wheel for fast switching

---

## 🔄 Phase 3: Player Progression - IN PROGRESS

### UI Screens 
- ✅ CharacterStatsScreen (C key) - Level, XP, attributes
- ✅ SkillTreeScreen (K key) - Combat/Signs/Alchemy trees
- ✅ QuestJournalScreen (J key) - Active/completed quests
- ✅ All screens use StringWidget pattern
- ⚠️ Network sync for real player data

### Progression System
- ✅ PlayerLevel (XP, leveling)
- ✅ PlayerAttributes (Strength, Vitality, Combat, Signs, Alchemy)
- ✅ PlayerSkills (skill tree with tiers)
- ✅ Skills affect gameplay (Combat damage, Sign intensity, Vitality health, Critical hits)
- ✅ Network synchronization implemented
- [ ] Witcher School specializations (Wolf, Cat, Bear, Griffin, Viper)
- [ ] Mutation tree (endgame character customization)
- [ ] Mutagen socket system (Red/Blue/Green with synergies)
- [ ] Character background/school selection at start
- [ ] Places of Power (world structures granting skill points)

### Quest System
- ✅ Quest framework with objectives
- ✅ Quest board GUI working
- ✅ Reputation system with factions
- ⚠️ Need more quests and contracts
- [ ] Monster contract system with investigation phases
- [ ] Contract negotiation dialogue system
- [ ] Dynamic contract generation based on world state
- [ ] Contract board progression (reputation unlocks harder hunts)
- [ ] Multi-stage contracts (find, prepare, hunt, collect proof)
- [ ] Diagram treasure hunts for legendary gear

---

## 🔄 Phase 4: Alchemy - PARTIALLY COMPLETE

### Potions
- ✅ Basic potions working (Swallow, Cat, Thunderbolt, etc.)
- ✅ Toxicity system implemented
- ✅ Potion effects functional
- ✅ Recipe data system
- ✅ Alchemy attribute scales potion duration (+10% per point)
- [ ] Potion brewing mini-game (temperature, stirring, timing)
- [ ] Alchemy experimentation (combine ingredients without recipes)
- [ ] Potion quality tiers (based on crafting skill/location)
- [ ] Potion overdose hallucinations at high toxicity
- [ ] Potion toxicity combos and synergies
- [ ] Potion hotbar for quick access
- [ ] Auto-replenish potions during meditation

### Blocks
- ✅ Alchemy Table block exists
- ✅ Alchemy Table GUI functional
- ✅ Forgery block exists  
- ✅ Forgery GUI functional
- [ ] Alchemy Table brewing mechanics with heat control
- [ ] Grindstone for weapon sharpening/maintenance
- [ ] Herb drying racks
- [ ] Meditation spots (campfires, beds)

### Advanced Alchemy (NOT STARTED)
- [ ] Blade oils (apply to weapons for bonus damage)
- [ ] Oil crafting recipes and application mechanics
- [ ] Oil duration/charge system
- [ ] Bombs (throwable items with area effects)
- [ ] Decoctions (long-duration buffs from rare monster parts)
- [ ] Decoction high toxicity cost (only 1-2 active)
- [ ] Alchemy ingredient properties and hidden synergies
- [ ] Failed experiment effects (explosions, weird potions)
- [ ] Ingredient discovery journal
- [ ] Master alchemist NPCs for rare recipes

---

## ❌ Phase 5: Monsters & Combat - NOT STARTED

### Monster Entities
- ✅ Spawn eggs exist for: Drowner, Nekker, Ghoul, Alghoul, etc.
- ❌ **NEED**: Actual entity implementations
- ❌ Monster AI and behaviors
- ❌ Loot drop tables
- ❌ Silver/Steel effectiveness mechanics
- [ ] Monster ecology (time-of-day spawning, habitats)
- [ ] Pack behaviors and coordination
- [ ] Monster adaptation AI (learn player tactics)
- [ ] Dynamic monster nests with spawning/growth
- [ ] Monster knowledge progression (unlock bestiary info)
- [ ] Monster weaknesses system (fire, traps, oils)
- [ ] Monster part harvesting from corpses
- [ ] Harvest quality based on kill method

### Bestiary System
- [ ] Auto-unlock entries on first encounter
- [ ] Progressive information reveal (kills unlock details)
- [ ] Weakness information and recommended preparations
- [ ] Lore descriptions for each monster
- [ ] Kill count tracking and statistics
- [ ] Master rank bonuses (10+ kills = damage bonus)

### Monster Behavior
- [ ] Drowners ambush near water
- [ ] Nekkers dig tunnels (break blocks)
- [ ] Ghouls attracted to corpses/graveyards
- [ ] Werewolves transform during full moon
- [ ] Wraiths only spawn at night
- [ ] Griffin aerial attacks
- [ ] Leshen forest control abilities

### Combat Mechanics
- ✅ Skill bonuses affecting combat (Combat attribute +2% damage per point)
- ✅ Critical hit system from Combat attribute (5% base + 1% per point, 1.5x damage)
- [ ] Dodge/roll mechanics with iframes
- [ ] Parry/counterattack system
- [ ] Finisher animations on low-health enemies
- [ ] Stamina management in combat
- [ ] Adrenaline/Focus system
- [ ] Combat damage numbers (optional floating text)
- [ ] Weapon durability & maintenance system
- [ ] Sharpening oils for temporary boosts
- [ ] Training dummy for practice
- [ ] Sign casting during combat combos

---

## ❌ Phase 6: World & Structures - NOT STARTED

### Witcher Senses System
- [ ] Witcher vision mode (hold key to activate)
- [ ] World turns grayscale with highlighted objects
- [ ] Track/trail following (footprints, blood, scratches)
- [ ] Scent detection for monsters
- [ ] Clue analysis and investigation
- [ ] Monster weak points highlighted in combat
- [ ] Limited duration with cooldown
- [ ] Herb and ingredient highlighting

### Meditation & Time System
- [ ] Meditation mechanic (heal, advance time, allocate skills)
- [ ] Potion preparation requires meditation
- [ ] Toxicity decay during meditation
- [ ] Meditation spot bonuses (campfire = warmth buff)
- [ ] Time skip in 1-hour increments
- [ ] Day/night cycle significance for monsters
- [ ] Weather effects on gameplay (rain weakens Igni, storms empower monsters)

### Structures
- ✅ Village structure files exist (Kaedwen)
- ❌ Structure generation in world
- [ ] Witcher keep/lab structures (School locations)
- [ ] Monster nests with destruction mechanics
- [ ] Contract notice boards in villages
- [ ] Abandoned sites with loot
- [ ] Places of Power (grant skill points)
- [ ] Treasure hunt locations for diagrams
- [ ] Monster dens and lairs
- [ ] Corrupted areas around nests

### World Integration
- [ ] Herb gathering locations with respawn timers
- [ ] Monster spawn zones based on biome/time
- [ ] NPC villagers with trades
- [ ] Dynamic contract generation
- [ ] Living world events (monster attacks on villages)
- [ ] Environmental hazards (swamp gas, quicksand, poisonous plants)
- [ ] Monster pheromone areas
- [ ] Reactive NPC reputation system
- [ ] Witcher prejudice in some areas
- [ ] Economy & trading (monster parts, herbs, repairs)
- [ ] Master craftsmen NPCs (diagrams required)
- [ ] Blacksmith upgrade services
- [ ] Random encounters during travel

---

## 🎯 Phase 7: Equipment & Gear Systems

### Witcher Gear Sets
- ✅ Basic armor sets exist (Witcher, Feline, Ursine, Griffin, Viper)
- [ ] Set bonuses (2/4/6 piece effects)
- [ ] Legendary full set transformations (Wolf ghost companion, etc.)
- [ ] Diagram discovery system (treasure hunts)
- [ ] Gear upgrade paths (Enhanced, Superior, Mastercrafted)
- [ ] School-specific gear bonuses matching playstyle
- [ ] Visual customization options
- [ ] Gear degradation and repair mechanics

### Weapons & Enhancements
- ✅ Silver & Steel swords with effectiveness system
- [ ] Runestone system (weapon enchantments, multiple tiers)
- [ ] Glyph system (armor enchantments)
- [ ] Socket system for runes/glyphs
- [ ] Crafting quality tiers for weapons/armor
- [ ] Silver maintenance (rare silver ore, sharpening)
- [ ] Weapon/armor display in player home
- [ ] Trophy system (monster heads for passive bonuses)

### Blood & Silver Economy
- [ ] Silver as rare crafting resource
- [ ] Special silver oils for maintenance
- [ ] Economic sink for endgame players
- [ ] Monster part vendor pricing
- [ ] Repair cost balancing

---

## 🎯 Phase 8: Advanced Systems

### Curse & Affliction System
- [ ] Monster curses that persist through death
- [ ] Curse types (Werewolf transformation, Wraith haunting, etc.)
- [ ] Cure methods (potions, priests, quests)
- [ ] Visual effects for cursed players
- [ ] Curse progression over time

### Mutation & Mutagen System
- [ ] Mutagen drops from monsters (Red/Blue/Green)
- [ ] Socket system for permanent bonuses
- [ ] Color-coded synergies with equipped skills
- [ ] Trade-offs (power vs drawbacks)
- [ ] Visual changes (glowing eyes, enhanced reflexes)
- [ ] Mutation tree (endgame, level 20+)
- [ ] Limited mutation slots (3-4 max)
- [ ] Mutation removal/replacement mechanics

### Gwent Card Game (Ambitious)
- [ ] Collectible card system
- [ ] Card types and factions
- [ ] NPC opponents with decks
- [ ] Tournament system
- [ ] Card rewards from quests
- [ ] Deck building interface
- [ ] AI for card game logic

---

## Immediate Next Steps

### 1. **Build & Test Current Implementation** (5 min)
```powershell
.\gradlew build
.\gradlew runClient
# Press C, K, J to test UI screens
# Test Combat damage and Critical hits
# Test Sign intensity scaling (Igni, Yrden, Quen, Axii)
# Check Vitality health bonus
```

### 2. **Monster Entities** (High Priority - 4-8 hours)
- Implement Drowner entity (start simple)
- Basic AI (hostile, melee attack)
- Loot drops (drowner brain)
- Test silver/steel damage effectiveness
- Add bestiary entry unlock on first kill
- Repeat for Nekker, Ghoul

### 3. **Witcher Senses** (Medium Priority - 3-5 hours)
- Implement vision mode toggle (hold key)
- Grayscale shader with highlights
- Track highlighting (footprints, blood)
- Herb/ingredient detection
- Monster weak point highlighting

### 4. **Blade Oils** (Medium Priority - 2-3 hours)
- Create oil items (Necrophage Oil, Specter Oil, etc.)
- Right-click weapon to apply oil
- Damage bonus against specific monster types
- Duration/charge system (lasts X hits)
- Visual effect on weapon

### 5. **Monster Nests** (Medium Priority - 2-4 hours)
- Nest block entity that spawns monsters
- Growth mechanic if not destroyed
- Bomb destruction mechanics
- Visual corruption spreading
- Loot rewards for destruction

### 6. **Meditation System** (Low Priority - 2-3 hours)
- Meditation GUI (time skip, heal, allocate skills)
- Potion replenishment (if ingredients available)
- Toxicity decay over time
- Campfire/bed meditation spots
- Time advancement mechanics

### 7. **Equipment Sets & Bonuses** (Low Priority - 3-4 hours)
- 2/4/6 piece set bonus system
- School-specific bonuses
- Visual effects for full set
- Trophy system for monster heads
- Runestone/Glyph socketing

### 8. **Contract Investigation** (Low Priority - 4-6 hours)
- Contract with investigation phase
- Use Witcher senses to find clues
- Track monster to lair
- Prepare based on bestiary knowledge
- Boss fight and proof collection

---

## Long-term Vision

### Polish & Balance
- Better textures (replace placeholders)
- Sound effects for signs and combat
- Particle effect improvements
- GUI texture theming (Witcher aesthetic)
- Balancing all systems (damage, toxicity, costs)
- Performance optimization
- Combat animations and finishers
- Visual feedback for crits and combos

### Advanced Features
- Witcher meditation system with time skip
- Alchemy ingredient discovery and experimentation
- Monster contract investigation mechanics
- Dynamic difficulty scaling based on player level
- Cross-mod compatibility (JEI, REI integration)
- Potion brewing mini-game
- Monster adaptation AI
- Living world events (village attacks)
- Curse and affliction persistence

### Content Expansion  
- More signs/mutations (alternative modes)
- Additional monster types (30+ planned)
- More quest chains and contracts
- Legendary equipment sets with transformations
- Boss monsters (Fiend, Leshen, Griffin alpha)
- Witcher School locations and lore
- Places of Power scattered in world
- Diagram treasure hunts
- Gwent card game (ambitious)
- Character customization and backgrounds

---

## Technical Debt

### High Priority
- ✅ Network sync for player progression
- ✅ Block entity GUIs (Alchemy, Forgery)
- [ ] Entity implementations for spawn eggs
- [ ] SavedData API fix (WitcherPlayerProgress)
- [ ] Monster AI framework
- [ ] Bestiary unlock system

### Medium Priority
- [ ] Better texture generation (16x16 or 32x32)
- [ ] Sound event registration (signs, combat, ambient)
- [ ] Particle effect system overhaul
- [ ] Witcher senses shader implementation
- [ ] Contract investigation framework
- [ ] Mutation tree UI
- [ ] Trophy display system
- [ ] Oil application mechanics

### Low Priority
- [ ] Code documentation and JavaDocs
- [ ] Unit tests for core systems
- [ ] Performance optimization (entity count, particles)
- [ ] Multi-language support (i18n)
- [ ] Durability and repair balance
- [ ] Enchantment compatibility with vanilla
- [ ] Meditation GUI polish
- [ ] Bestiary UI refinement
- [ ] Quest journal improvements

---

## Feature Highlights & Innovations

### Unique Mechanics (Set WitcherCraft Apart)
1. **Monster Adaptation AI** - Monsters learn player tactics and adjust
2. **Sign Combination Casting** - Combo two signs for unique effects
3. **Investigation Contracts** - Use Witcher senses to track and prepare
4. **Alchemy Experimentation** - Discover recipes through trial & error
5. **Dynamic Monster Nests** - Grow stronger if not destroyed
6. **Brewing Mini-Game** - Interactive potion crafting with timing
7. **Living World Events** - Villages under attack, player can intervene
8. **Monster Knowledge System** - Progressive bestiary unlocks with kills
9. **Potion Overdose System** - High toxicity = hallucinations
10. **Trophy Bonuses** - Equip monster heads for passive effects

### Core Witcher Fantasy
- ✅ **Signs** - All 5 signs functional with combos planned
- ✅ **Silver & Steel** - Effectiveness system working
- ✅ **Progression** - Level, attributes, skills with gameplay effects
- [ ] **Witcher Senses** - Investigation and tracking
- [ ] **Meditation** - Heal, prepare, advance time
- [ ] **Blade Oils** - Monster-specific preparation
- [ ] **Bestiary** - Knowledge gathering and weaknesses
- [ ] **Contracts** - Hunt, investigate, collect proof
- [ ] **Mutations** - Endgame character customization
- [ ] **Schools** - Different playstyle specializations

---

## Testing Checklist

- [x] Items register correctly
- [x] Blocks place and break
- [x] Signs cast without errors (all 5 working)
- [x] Potions apply effects
- [x] UI screens functional (Character, Skills, Quests)
- [x] Network packets sync player data
- [x] Combat damage scales with attributes
- [x] Critical hits trigger and display
- [x] Sign intensity scales with Signs attribute
- [x] Vitality increases max health
- [ ] Monsters spawn and behave correctly
- [ ] Monster AI targets and attacks
- [ ] Silver/Steel effectiveness in combat
- [ ] Loot drops from monsters
- [ ] Bestiary unlocks on kills
- [ ] Oils apply to weapons
- [ ] Nests spawn monsters
- [ ] Witcher senses highlights work
- [ ] Meditation heals and restores
- [ ] Contract investigation mechanics
- [ ] Set bonuses activate
- [ ] Mutations apply correctly
- [ ] Config saves and loads
- [ ] Structure datapack loads without registry errors
- [ ] No crashes or major bugs
- [ ] Multiplayer compatibility
- [ ] Performance acceptable (60+ FPS)

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
