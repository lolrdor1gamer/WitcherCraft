# WitcherCraft - Knowledge & Discovery System

## Overview
A comprehensive progression system tracking player knowledge through exploration, investigation, and experimentation. This system rewards curiosity, creates "eureka" moments, and provides long-term meta-progression separate from levels and attributes.

---

## 📚 The Witcher's Journal

### **Journal Structure** (Main UI)

Player's journal has **5 tabs**:

```
╔══════════════════════════════════════════════════════════╗
║  WITCHER'S JOURNAL                             [Year 1272] ║
╠══════════════════════════════════════════════════════════╣
║ [Bestiary] [Herbarium] [Alchemy] [Maps] [Codex] [Trophies] ║
╠══════════════════════════════════════════════════════════╣
║                                                          ║
║  (Content area - changes based on tab selected)          ║
║                                                          ║
║                                                          ║
╚══════════════════════════════════════════════════════════╝
```

### **Access:** Press `J` to open journal, persists across sessions

---

## 🐺 Bestiary System

### **Monster Entry Progression**

Each monster has **3 knowledge levels**:

#### **Level 0: Unknown** (Before first encounter)
```
╔═════════════════════════════════════════╗
║ DROWNER                          [?/?]  ║
╠═════════════════════════════════════════╣
║ Status: UNKNOWN                         ║
║                                         ║
║ You have not encountered this creature  ║
║ yet. Investigate monster nests, ask     ║
║ locals, or consult books to learn more. ║
║                                         ║
║ [No information available]              ║
╚═════════════════════════════════════════╝
```

#### **Level 1: Basic Knowledge** (After first kill OR reading book)
```
╔═════════════════════════════════════════╗
║ DROWNER                         [1/3]   ║
╠═════════════════════════════════════════╣
║ Status: BASIC KNOWLEDGE                 ║
║                                         ║
║ Description:                            ║
║ Aquatic necrophage drawn to corpses     ║
║ in water. Humanoid shape, fish-like     ║
║ features, bloated body.                 ║
║                                         ║
║ Habitat: Rivers, marshes, lakes         ║
║ Activity: Day and night                 ║
║                                         ║
║ 🗡️ Weaknesses: ???                     ║
║ 🧪 Vulnerabilities: ???                ║
║ 💎 Loot: Drowner Brain (common)         ║
║                                         ║
║ Kill 5 more to unlock full entry.       ║
║ Progress: [█░░░░] 1/5                   ║
╚═════════════════════════════════════════╝
```

#### **Level 2: Detailed Knowledge** (After 5 kills)
```
╔═════════════════════════════════════════╗
║ DROWNER                         [2/3]   ║
╠═════════════════════════════════════════╣
║ Status: EXPERIENCED HUNTER              ║
║                                         ║
║ [Full description expanded]             ║
║                                         ║
║ Combat Pattern:                         ║
║ - Lunging melee attacks                 ║
║ - Vomit attack (poison, 2m range)       ║
║ - Pack tactics (2-5 individuals)        ║
║ - Flees when health <20%                ║
║                                         ║
║ 🗡️ Weaknesses:                         ║
║  ✓ Silver swords (+25% damage)         ║
║  ✓ Igni sign (fear effect)             ║
║                                         ║
║ 🧪 Vulnerabilities:                    ║
║  ✓ Necrophage Oil (+50% damage)        ║
║  ✓ Grapeshot bombs (fear)              ║
║                                         ║
║ 💎 Loot Table:                          ║
║  - Drowner Brain (80%)                  ║
║  - Venom Extract (30%)                  ║
║  - Water Essence (10%)                  ║
║  - Drowner Tongue (5%, rare)            ║
║                                         ║
║ Investigate 1 nest to unlock secrets.   ║
║ Progress: [█░░] 0/1 nest                ║
╚═════════════════════════════════════════╝
```

#### **Level 3: Master Knowledge** (After investigating nest)
```
╔═════════════════════════════════════════╗
║ DROWNER                         [3/3] ✓ ║
╠═════════════════════════════════════════╣
║ Status: MASTER KNOWLEDGE                ║
║                                         ║
║ [All previous info + secrets]           ║
║                                         ║
║ 🔍 SECRETS DISCOVERED:                  ║
║                                         ║
║ Ecological Role:                        ║
║ Drowners spawn from Drowner Nests near  ║
║ water. They feed on corpses but will    ║
║ attack living prey if hungry. Often     ║
║ serve as minions to Water Hags.         ║
║                                         ║
║ Advanced Tactics:                       ║
║ - Yrden sign traps them (immobilize)    ║
║ - Thunderbolt potion increases crit vs  ║
║   necrophages by 15%                    ║
║ - Attacking during vomit animation      ║
║   prevents poison and staggers them     ║
║                                         ║
║ Mutation Potential:                     ║
║ Drowner pheromones can be extracted     ║
║ from tongue to craft "Lure" bomb.       ║
║                                         ║
║ BONUS: +5% damage vs all Drowners       ║
╚═════════════════════════════════════════╝
```

### **Unlock Conditions**

| Level | Requirement | Rewards |
|-------|-------------|---------|
| **1 - Basic** | First kill OR read bestiary book | Name, description, habitat, common loot |
| **2 - Detailed** | 5 kills (exact number varies by monster) | Weaknesses, vulnerabilities, full loot table, combat patterns |
| **3 - Master** | Investigate nest OR special quest | Secrets, advanced tactics, permanent +5% damage bonus |

### **Alternative Unlock Methods**

- **Books:** Find "Bestiary: Drowners" in libraries, loot, merchants
  - Instantly grants Level 1 (skip first kill requirement)
  - Rare books grant Level 2 immediately
  
- **NPC Conversations:** Talk to hunters, witchers, scholars
  - They share tips (adds 1-2 hints to entry without full unlock)
  - Example: "Drowners hate fire" → Igni weakness revealed
  
- **Witcher Senses Investigation:**
  - Examine corpses killed by monster
  - Track monster trails back to lair
  - Analyze claw marks, bite patterns
  - Each investigation = +1 kill toward unlock

### **Bestiary Completion Rewards**

**Per Monster:**
- Level 1: Basic knowledge
- Level 2: +5% damage vs that monster
- Level 3: +10% damage, special tactics unlocked

**Global Milestones:**
- **10 entries:** "Novice Hunter" title, +5% loot from monsters
- **25 entries:** "Seasoned Witcher" title, +10% loot, bestiary vendor unlocked
- **50 entries:** "Master Monster Slayer" title, +15% loot, unique mutation
- **All entries (80+):** "Scholar of Monsters" title, +25% loot, legendary reward

---

## 🌿 Herbarium System

### **Herb Entry Structure**

Each herb/ingredient has knowledge levels similar to bestiary:

#### **Level 0: Unidentified**
```
Player picks unknown herb:
"You found: [Mysterious Plant]"
- Appears as "???" in inventory
- Cannot be used in alchemy (don't know properties)
- Options: Taste (risky), show to herbalist, experiment
```

#### **Level 1: Identified**
```
After herbalist identification or alchemy experiment:

╔═════════════════════════════════════════╗
║ CELANDINE                       [1/2]   ║
╠═════════════════════════════════════════╣
║ Appearance: Small yellow flowers with   ║
║ divided leaves. Orange sap when broken. ║
║                                         ║
║ Habitat: Forests, shaded areas          ║
║ Season: Spring, Summer                  ║
║ Rarity: Common                          ║
║                                         ║
║ 🧪 Alchemy Properties:                  ║
║  - Increases potion duration            ║
║  - Base ingredient for healing potions  ║
║                                         ║
║ 💰 Value: 5 orens                       ║
║                                         ║
║ Use in 5 recipes to unlock mastery.     ║
║ Progress: [█░░░░] 1/5                   ║
╚═════════════════════════════════════════╝
```

#### **Level 2: Mastery**
```
After using in 5+ successful alchemy crafts:

╔═════════════════════════════════════════╗
║ CELANDINE                       [2/2] ✓ ║
╠═════════════════════════════════════════╣
║ [All previous info + advanced details]  ║
║                                         ║
║ 🔬 ADVANCED KNOWLEDGE:                  ║
║                                         ║
║ Optimal Harvesting:                     ║
║ - Dawn (5-7 AM): +20% potency           ║
║ - During rain: +10% yield               ║
║ - Use silver knife: Preserves essence   ║
║                                         ║
║ Synergies:                              ║
║ - Combined with Drowner Brain: Healing  ║
║   potency increased by 30%              ║
║ - Substitute for Berbercane Fruit in    ║
║   most recipes (80% effectiveness)      ║
║                                         ║
║ Growing Conditions:                     ║
║ Can be cultivated in garden if:         ║
║  - Shade >60%                           ║
║  - Near water source                    ║
║  - Spring planting                      ║
║                                         ║
║ BONUS: +10% chance of double harvest    ║
╚═════════════════════════════════════════╝
```

### **Herbarium Mechanics**

**Identification Methods:**
1. **Herbalist NPC:** Pay 10 gold per unknown herb
2. **Alchemy Experimentation:** Use in random recipe (50% success)
3. **Books:** "Flora of the North" series identifies multiple herbs
4. **Witcher Senses:** Examine herb closely (Intelligence check)

**Mastery Benefits:**
- Harvest speed +50%
- Double harvest chance +10%
- Can identify related species (e.g., know Celandine → auto-identify White Celandine)
- Unlock cultivation (grow in player-owned garden)

**Collection Rewards:**
- **20 herbs mastered:** Herbalist title, +20% sell price for herbs
- **50 herbs mastered:** Can teach others (NPC function), recipes cheaper
- **All herbs (100+):** Legendary alchemist title, permanent potion buff

---

## 🧪 Alchemy Recipe Discovery

### **Recipe Knowledge Levels**

#### **Unknown Recipe**
```
Player has Swallow diagram:
[SWALLOW POTION]
Status: Recipe Unknown

Ingredients: ??? (5 required)
Effect: ???

Find a diagram or experiment to learn.
```

#### **Diagram Found**
```
Player finds "Diagram: Swallow"

╔═════════════════════════════════════════╗
║ SWALLOW POTION                          ║
╠═════════════════════════════════════════╣
║ Type: Healing Potion                    ║
║ Tier: Basic                             ║
║                                         ║
║ Ingredients:                            ║
║  - Celandine (x2) ✓                     ║
║  - Drowner Brain (x1) ✓                 ║
║  - Crow's Eye (x1) ✓                    ║
║  - Vitriol (base) ✓                     ║
║  - Empty Bottle ✓                       ║
║                                         ║
║ Effect:                                 ║
║  Restores 50 HP over 10 seconds         ║
║  +20% health regen for 1 minute         ║
║                                         ║
║ Toxicity: 20 points                     ║
║                                         ║
║ Craft to unlock recipe permanently.     ║
║ [CRAFT] [CLOSE]                         ║
╚═════════════════════════════════════════╝
```

#### **Recipe Learned** (After first craft)
```
Recipe added to journal permanently.
Can now craft anytime at Alchemy Table.
Ingredients auto-highlighted in inventory.
```

#### **Recipe Mastery** (After 10 crafts)
```
╔═════════════════════════════════════════╗
║ SWALLOW POTION                 MASTERED ║
╠═════════════════════════════════════════╣
║ [All previous info]                     ║
║                                         ║
║ 🏆 MASTERY BONUSES:                     ║
║  ✓ Craft time reduced by 50%            ║
║  ✓ 10% chance to not consume ingredients ║
║  ✓ Potion duration +20% when you craft  ║
║  ✓ Can teach recipe to other players    ║
║                                         ║
║ Variants Unlocked:                      ║
║  - Enhanced Swallow (requires Superior  ║
║    ingredients, +100% effect)           ║
║  - Swallow Decoction (permanent low-    ║
║    level regen, high toxicity)          ║
║                                         ║
║ Times Crafted: 10 / 10                  ║
╚═════════════════════════════════════════╝
```

### **Experimentation System**

**How to Discover Recipes Without Diagrams:**

1. **Open Alchemy Table**
2. **Enter "Experiment Mode"**
3. **Add Ingredients** (up to 5 slots)
4. **Brew and Observe:**
   ```
   SUCCESS (30% base chance):
   - New recipe discovered!
   - Potion created
   - Recipe added to journal
   
   PARTIAL SUCCESS (40% chance):
   - Strange mixture...
   - Random minor effect (buff or debuff)
   - Gain 1 hint about what to add/remove
   
   FAILURE (30% chance):
   - Potion explodes! (5 damage)
   - All ingredients lost
   - Alchemy skill +1 (learning from failure)
   ```

5. **Factors Affecting Success:**
   - Alchemy Attribute: +5% per point
   - Herbarium Mastery: +10% if all ingredients mastered
   - Alchemy Table Tier: Basic (0%), Advanced (+10%), Master (+20%)
   - Books Read: Certain books grant +15% experiment chance

**Example Experiment:**
```
Player adds: Celandine, Drowner Brain, Crow's Eye, Vitriol, Bottle
Result: SUCCESS! Discovered "Swallow Potion"

Player adds: Wolfsbane, Nekker Heart, Rebis, Vermillion, Bottle
Result: PARTIAL SUCCESS
  Hint: "The mixture bubbles violently. Perhaps a stabilizer?"
  (Needs different base - Rubedo instead of Vermillion)
```

---

## 🗺️ Map & Exploration System

### **Fog of War**

**Unexplored Areas:**
- Map is black/gray until player visits
- Reveal radius: 128 blocks around player
- Persists across sessions (saved data)

**Map Tiers:**

| Tier | Crafting | Zoom | Features |
|------|----------|------|----------|
| **Crude Map** | Paper + Charcoal | 1:2000 | Fog of war, no icons |
| **Surveyor's Map** | Paper + Ink + Compass | 1:1000 | Icons for settlements |
| **Witcher's Map** | Parchment + Ink + Compass + Silver | 1:500 | All icons, markers, routes |

### **Points of Interest (POI) Discovery**

**POI Types:**
- 🏘️ Settlements (auto-discovered when entering)
- 🗿 Places of Power (Witcher Senses from 100 blocks)
- 🕳️ Caves (visible entrance or NPC hint)
- ⚠️ Monster Nests (Witcher Senses from 50 blocks)
- 🏚️ Ruins (exploration or map loot)
- 💎 Treasure (treasure maps or random discovery)
- 🧙 Hidden Hermits (rumors or secret quest)

**Discovery Rewards:**
- XP: 50-200 based on POI importance
- Map Marker: Fast travel (if signpost present)
- Journal Entry: Lore snippet or quest lead
- Milestone Bonuses:
  - 10 POI: "Explorer" title
  - 50 POI: +10% movement speed
  - 100 POI: "Cartographer" title, treasure map skill

### **Treasure Maps**

**Finding Maps:**
- Loot from chests, corpses, boss monsters
- Purchase from merchants (expensive)
- Quest rewards

**Map Example:**
```
╔═════════════════════════════════════════╗
║ TREASURE MAP: "The Hanged Man's Cache" ║
╠═════════════════════════════════════════╣
║ [Crude drawing of tree with noose]     ║
║                                         ║
║ "Three paces north of the old oak,      ║
║  where the deserter swings eternal.     ║
║  Bury your blade where crows feast."    ║
║                                         ║
║ Clues:                                  ║
║  - Old oak tree (look for gibbets)      ║
║  - North direction (use compass)        ║
║  - Dig 3 blocks north                   ║
║                                         ║
║ Estimated Region: Temeria, No Man's Land║
║                                         ║
║ [TRACK] (adds waypoint to map)          ║
╚═════════════════════════════════════════╝
```

**Solving Process:**
1. Read map → Journal entry created
2. Travel to estimated region
3. Use Witcher Senses to find clues (old oak, gibbet)
4. Measure distance (paces = blocks)
5. Dig or interact at location
6. Loot treasure (gold, gear, rare items)

**Rewards:**
- 500-5000 gold value
- Witcher gear diagrams (high chance)
- Rare alchemy ingredients
- Unique weapons/armor

---

## 📖 Codex System

### **Lore Entries**

**Categories:**

#### **1. History**
```
Entry: "The First Conjunction of the Spheres"

Unlocked by: Reading book, completing quest, NPC dialogue

Content:
1,500 years ago, the Conjunction brought monsters
and magic to this world. Humans arrived, displacing
the Elder Races (elves, dwarves). The chaos birthed
the first witchers, created to combat the monsters.

Related Entries:
- Elder Races
- Witcher Creation
- Age of Monsters

Completion: 12 / 45 History entries
```

#### **2. Factions**
```
Entry: "The Nilfgaardian Empire"

Unlocked by: Entering Nilfgaard territory, reputation events

Content:
Southern empire known for military prowess and
centralized government. Led by Emperor Emhyr var
Emreis. Currently at war with Northern Kingdoms.
Symbol: Golden sun on black background.

Political Stance: Expansionist, authoritarian
Military: Largest standing army on the Continent
Economy: Florens (gold currency), slave labor

Related Entries:
- Emperor Emhyr var Emreis
- The Great War
- Northern Kingdoms Alliance

Completion: 8 / 20 Faction entries
```

#### **3. Locations**
```
Entry: "Kaer Morhen"

Unlocked by: Visiting location OR School of the Wolf quest

Content:
Ancient witcher stronghold in the Kaedwen mountains.
Home of the School of the Wolf. Mostly ruined after
the pogrom of 1160, but still used for training and
winter refuge by surviving Wolf School witchers.

Inhabitants: Vesemir (oldest living witcher), trainees
Threats: Occasional monster incursions, harsh weather
Significance: Birthplace of witcher tradition

Related Entries:
- School of the Wolf
- Witcher Training
- The Pogrom

Completion: 15 / 35 Location entries
```

### **Codex Unlocking**

**Methods:**
- 📚 **Books:** Read in-game books (auto-adds entries)
- 💬 **Dialogue:** Talk to lore-knowledgeable NPCs
- 🗺️ **Exploration:** Discover locations (auto-unlock entry)
- 📜 **Quests:** Completing lore quests unlocks related entries
- 🎓 **Schools:** Study at universities (Oxenfurt), temples

**Codex Rewards:**
- Flavor text (immersion)
- Hints for quests (some quests require codex knowledge)
- Dialogue options (reference history in conversations)
- Completionist achievement

---

## 🏆 Trophy & Achievement System

### **Trophy Types**

#### **1. Monster Trophies** (Equipped items)
```
[GRIFFIN HEAD]
Rarity: Rare
Source: Killed Royal Griffin (boss)

Effect when equipped:
- +5% damage vs all flying monsters
- +2 Armor
- Intimidation +10%

Cosmetic: Hangs from player's belt
Can be displayed in player home

Trophy Collection: 5 / 50 unique bosses
```

#### **2. Achievement Milestones**
```
╔═════════════════════════════════════════╗
║ ACHIEVEMENTS                            ║
╠═════════════════════════════════════════╣
║ 🗡️ COMBAT                               ║
║  ✓ First Blood - Kill your first monster║
║  ✓ Monster Slayer - Kill 100 monsters   ║
║  ⬜ Legendary Hunter - Kill all bosses   ║
║                                         ║
║ 🌿 ALCHEMY                              ║
║  ✓ Novice Alchemist - Craft 10 potions  ║
║  ⬜ Master Brewer - Master 20 recipes    ║
║  ⬜ Philosopher's Stone - Discover all   ║
║     recipes through experimentation     ║
║                                         ║
║ 🗺️ EXPLORATION                          ║
║  ✓ Wanderer - Discover 10 locations     ║
║  ⬜ Cartographer - Discover 100 locations║
║  ⬜ The Continent Awaits - Visit every   ║
║     region                              ║
║                                         ║
║ Progress: 15 / 80 achievements          ║
║ Completion: 19%                         ║
╚═════════════════════════════════════════╝
```

### **Secret Achievements** (Hidden until unlocked)
```
🔒 ???
Hint: "The dead have stories to tell."
(Unlock: Complete all wraith contracts)

🔒 ???
Hint: "Magic has a price."
(Unlock: Overdose on potions 3 times)

🔒 ???
Hint: "Not all monsters are beasts."
(Unlock: Spare a sentient monster)
```

---

## ⚙️ Configuration

```json5
// config/witchercraft/knowledge_discovery.json
{
  "bestiary": {
    "enabled": true,
    "progressive_unlocking": true,
    "kills_for_detailed": 5, // per monster type
    "nest_investigation_required": true,
    "damage_bonus_on_mastery": 0.10, // 10%
    "books_grant_instant_knowledge": true
  },
  
  "herbarium": {
    "enabled": true,
    "unknown_herbs_on_pickup": true,
    "experimentation_allowed": true,
    "mastery_bonus_double_harvest": 0.10,
    "herbalist_identification_cost": 10 // gold per herb
  },
  
  "alchemy": {
    "recipe_experimentation": true,
    "experiment_base_success_chance": 0.30,
    "failure_explosion_damage": 5,
    "mastery_bonus_no_consume_chance": 0.10,
    "teach_recipes_to_players": true // multiplayer
  },
  
  "maps": {
    "fog_of_war": true,
    "reveal_radius_blocks": 128,
    "poi_discovery_xp": true,
    "treasure_maps_enabled": true,
    "treasure_map_difficulty": "medium" // "easy", "medium", "hard"
  },
  
  "codex": {
    "enabled": true,
    "lore_entries": true,
    "books_add_entries": true,
    "completion_rewards": true
  },
  
  "trophies": {
    "monster_trophies_drop": true,
    "boss_trophy_drop_chance": 1.0, // 100% from bosses
    "trophy_effects_enabled": true,
    "display_in_home": true
  },
  
  "achievements": {
    "enabled": true,
    "secret_achievements": true,
    "achievement_rewards": true, // titles, cosmetics
    "global_leaderboard": false // multiplayer
  }
}
```

---

## 🎯 Integration with Other Systems

| System | Integration |
|--------|-------------|
| **Combat** | Bestiary knowledge grants damage bonuses, reveals weaknesses |
| **Alchemy** | Herbarium mastery improves harvesting, recipe experimentation |
| **Quest System** | Codex entries unlock dialogue, bestiary required for contracts |
| **World Generation** | Map fog of war, POI discovery, treasure locations |
| **Progression** | Achievements grant titles, knowledge = meta-progression |

---

## 🛠️ Implementation Phases

### **Phase 1: Bestiary (4-5 weeks)**
- [ ] Monster entry data structure
- [ ] Progressive unlock system (kills → knowledge)
- [ ] Bestiary UI (journal tab)
- [ ] Damage bonus application
- [ ] Book reading integration

### **Phase 2: Herbarium (3-4 weeks)**
- [ ] Herb identification system
- [ ] Unknown herb mechanic
- [ ] Herbalist NPC interactions
- [ ] Mastery tracking and bonuses

### **Phase 3: Alchemy Discovery (4-5 weeks)**
- [ ] Recipe diagram items
- [ ] Experimentation mode (alchemy table)
- [ ] Success/failure mechanics
- [ ] Recipe mastery system

### **Phase 4: Maps & Exploration (5-6 weeks)**
- [ ] Fog of war rendering
- [ ] POI discovery and marking
- [ ] Treasure map items and solving
- [ ] Fast travel integration

### **Phase 5: Codex & Trophies (3-4 weeks)**
- [ ] Codex entry database
- [ ] Book reading triggers entries
- [ ] Trophy drop system
- [ ] Trophy effects and display

### **Phase 6: Achievements (2-3 weeks)**
- [ ] Achievement tracking
- [ ] Secret achievements
- [ ] Reward system (titles, cosmetics)
- [ ] UI integration

**Total Estimated Time:** 21-27 weeks (5-7 months)

---

## 🎯 Success Criteria

✅ **Rewarding Curiosity**
- Players want to explore to discover new entries
- "Eureka!" moments when learning secrets
- Knowledge feels earned, not given

✅ **Practical Benefits**
- Bestiary knowledge measurably improves combat
- Herbarium mastery saves time and increases yield
- Alchemy experimentation enables creative builds

✅ **Long-term Engagement**
- Meta-progression extends beyond max level
- Completionists have clear goals (100% bestiary)
- Each playthrough discovers something new

✅ **Accessibility**
- Casual players can ignore (not required for main content)
- Hardcore players rewarded for deep dives
- Multiple paths to knowledge (books, combat, NPCs)

---

**Status:** 📋 Design Phase  
**Priority:** 🟡 High (Enhances depth and replayability)  
**Dependencies:** Monster System (bestiary), Alchemy System (recipes), World Generation (maps)  
**Related Files:** QUEST_REPUTATION_SYSTEM_UPDATE.md, WORLDGEN_SYSTEM.md, DEVELOPMENT_ROADMAP.md
