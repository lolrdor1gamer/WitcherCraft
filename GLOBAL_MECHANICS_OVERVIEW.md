# WitcherCraft - Global Mechanics Overview

## 🌍 Introduction

This document provides an overview of WitcherCraft's **global mechanics** - foundational systems that affect the entire game world and player experience. These are not isolated features but interconnected frameworks that create a living, reactive Witcher universe.

---

## 📚 Core Global Systems

### **1. World Generation System** 📄 [WORLDGEN_SYSTEM.md](./WORLDGEN_SYSTEM.md)

**What it does:**
- Generates "The Continent" with 6-8 distinct regions (Temeria, Nilfgaard, Kaedwen, Skellige, etc.)
- Creates region-specific biomes, terrain, and natural landmarks
- Places settlements (hamlets, villages, towns, cities) based on regional logic
- Distributes resources (ores, herbs, monster nests) strategically
- Supports separate dimension OR overworld replacement

**Key Features:**
- Layered generation: Continental → Regional → Biome → Structure → Resource
- Environmental storytelling (battlefields, ruins, destroyed caravans)
- River systems, cave networks, Places of Power
- Dynamic world with meaningful geography

**Integration:**
- Foundation for ALL other systems
- Determines where factions exist, what monsters spawn, which herbs grow
- Creates exploration goals and fast travel network

---

### **2. Political & Reputation System** 📄 [POLITICAL_SYSTEM.md](./POLITICAL_SYSTEM.md)

**What it does:**
- Tracks player reputation with 10+ factions (kingdoms, guilds, races)
- Manages dynamic borders that shift based on wars and events
- Controls NPC behavior (merchants, guards, quest givers)
- Enables diplomatic gameplay (alliances, peace treaties, inciting wars)
- Allows player-owned settlement governance (endgame)

**Key Features:**
- 7 reputation tiers: Revered → Honored → Friendly → Neutral → Unfriendly → Hostile → Hated
- Allied/enemy faction reputation propagation
- Border checkpoints, travel permits, contraband searches
- War events (Nilfgaardian invasion, peasant uprisings, monster plagues)
- Settlement sieges with player choice (defend, attack, neutral)

**Integration:**
- Affects economy (prices, trade access)
- Gates quests (reputation requirements)
- Determines combat encounters (guards attack/assist)
- Influences world events (wars, festivals, assassinations)

---

### **3. Economy & Trade System** 📄 [ECONOMY_TRADE_SYSTEM.md](./ECONOMY_TRADE_SYSTEM.md)

**What it does:**
- Multi-currency system (Orens, Florens, Crowns, Bizants)
- Dynamic pricing based on supply/demand, reputation, region, rarity
- NPC trade caravans traveling between settlements
- Banking (deposits, loans, investments)
- Black markets and smuggling mechanics
- Bartering system (Witcher tradition)

**Key Features:**
- Realistic economy simulation (production, consumption, scarcity)
- Trade routes impact settlement prosperity
- War embargoes, market crashes, inflation events
- Merchant personalities (greedy, fair, desperate, shrewd)
- Investment opportunities (merchant shares, trade bonds)

**Integration:**
- Tied to political reputation (prices vary)
- Seasonal economy (TIME_CALENDAR_SYSTEM.md)
- Regional goods based on world generation
- Quest rewards and contract payments

---

### **4. Time & Calendar System** 📄 [TIME_CALENDAR_SYSTEM.md](./TIME_CALENDAR_SYSTEM.md)

**What it does:**
- 12-month Witcher calendar with named months and seasons
- Day/night cycle (24 minutes real-time = 24 hours in-game, configurable)
- Lunar phases affecting monsters and magic
- Cultural festivals (Belleteyn, Saovine, Lammas, Midinváerne)
- NPC schedules (merchants open/close, sleep cycles)
- Meditation system (heal, skip time, replenish potions)

**Key Features:**
- Seasonal weather and gameplay effects (winter cold, summer heat)
- Full moon werewolf spawns, new moon wraith bonuses
- Time-based quests with deadlines
- Environmental changes (snow in winter, flowers in spring)
- Blood moon events, midnight hauntings, market days

**Integration:**
- Seasonal economy (herb availability, food prices)
- Monster behavior changes (hibernation, mating season)
- Festival integration with political factions
- Meditation at Places of Power grants skill points

---

### **5. Knowledge & Discovery System** 📄 [KNOWLEDGE_DISCOVERY_SYSTEM.md](./KNOWLEDGE_DISCOVERY_SYSTEM.md)

**What it does:**
- Bestiary: Progressive monster knowledge (unknown → basic → detailed → master)
- Herbarium: Herb identification and mastery system
- Alchemy: Recipe discovery through diagrams or experimentation
- Maps: Fog of war, POI discovery, treasure hunting
- Codex: Lore entries for history, factions, locations
- Achievements & trophies

**Key Features:**
- Meta-progression separate from levels (knowledge is power)
- Bestiary mastery grants +10% damage vs specific monsters
- Alchemy experimentation (mix ingredients, discover recipes)
- Treasure maps with cryptic clues to solve
- Monster trophies with passive bonuses

**Integration:**
- Bestiary reveals monster weaknesses (combat advantage)
- Herbarium improves alchemy efficiency
- Map exploration rewards (fast travel unlocks)
- Codex entries unlock dialogue options in quests

---

## 🔗 System Interconnections

These systems don't exist in isolation - they form a web of interactions:

```
WORLD GENERATION
  ↓ Defines regions, biomes, settlements
  ↓
POLITICAL SYSTEM
  ↓ Factions control territories
  ↓ Reputation affects...
  ↓
ECONOMY SYSTEM
  ↓ Prices, trade routes, merchants
  ↓ Seasonal changes from...
  ↓
TIME & CALENDAR
  ↓ Festivals, weather, day/night
  ↓ Affects monster spawns...
  ↓
KNOWLEDGE SYSTEM
  ↓ Bestiary reveals weaknesses
  ↓ Feeds back to combat effectiveness
```

**Example Player Journey:**

1. **World Generation:** Player spawns in Temeria (temperate farmlands biome)
2. **Political:** Neutral reputation with Temeria, can trade and accept quests
3. **Economy:** Buys silver sword from blacksmith at standard price (neutral rep)
4. **Time:** Spring season, herbs abundant, drowner mating season (more spawns)
5. **Knowledge:** Kills first drowner → Bestiary Level 1 unlocked
6. **Integration Loop:**
   - Drowner kills → Bestiary Level 2 → Learn weakness (Necrophage Oil)
   - Buy oil ingredients → Prices affected by season (spring = cheap herbs)
   - Apply oil → +50% damage → Earn more contract gold
   - Spend gold → Boost reputation with Temeria → Get better prices
   - Complete quests → Unlock Honored tier → Gain access to elite contracts
   - Nilfgaard invades (time event) → Temeria at war → Border closes
   - Must choose side → Reputation consequences → Economy disrupted

This creates a **living, reactive world** where everything matters.

---

## ⚙️ Global Configuration

All systems share a master config structure:

```json5
// config/witchercraft/global_systems.json
{
  "meta": {
    "systems_enabled": {
      "world_generation": true,
      "political_system": true,
      "economy": true,
      "time_calendar": true,
      "knowledge_discovery": true
    },
    "integration_level": "full", // "minimal", "standard", "full"
    "complexity": "medium" // "simple", "medium", "complex"
  },
  
  "world": {
    "dimension_mode": "separate_dimension", // or "overworld_replacement", "hybrid"
    "world_size": 10000,
    "region_count": 8,
    "settlement_density": "normal"
  },
  
  "political": {
    "reputation_affects_gameplay": true,
    "dynamic_borders": true,
    "war_events": true
  },
  
  "economy": {
    "multi_currency": true,
    "dynamic_pricing": true,
    "npc_caravans": true,
    "black_markets": true
  },
  
  "time": {
    "day_length_minutes": 24,
    "seasonal_changes": true,
    "festivals_enabled": true,
    "lunar_effects": true
  },
  
  "knowledge": {
    "progressive_bestiary": true,
    "alchemy_experimentation": true,
    "fog_of_war": true
  }
}
```

**Preset Modes:**

| Preset | Description | Target Audience |
|--------|-------------|----------------|
| **Casual** | Simplified systems, less complexity | New players, relaxed gameplay |
| **Standard** | Balanced depth and accessibility | Most players |
| **Hardcore** | All features enabled, maximum depth | Witcher fans, min-maxers |
| **Story Focus** | Political/quest emphasis, less economy | RPG storytellers |
| **Survival** | Economy/time critical, harsh world | Survival mode enthusiasts |

---

## 🎯 Design Philosophy

### **1. Interconnected, Not Isolated**
- No system exists in a vacuum
- Actions ripple through multiple systems
- Creates emergent gameplay (unscripted stories)

### **2. Depth Without Mandatory Complexity**
- Casual players can ignore advanced features (still fun)
- Hardcore players rewarded for engaging deeply
- Progressive complexity (learn as you play)

### **3. Player Agency Matters**
- Choices have consequences across systems
- Multiple paths to goals (combat, diplomacy, stealth, economy)
- Can specialize or generalize

### **4. Authentic Witcher Fantasy**
- Grounded in lore (books, games)
- Feels like living in The Witcher universe
- Not just mechanics, but atmosphere and narrative

### **5. Configurable & Moddable**
- All systems have config options
- Can be individually enabled/disabled
- Datapack support for custom content

---

## 🛠️ Development Timeline

**Estimated Total Development Time:** 2-3 years for full implementation

### **Phase Order (Recommended):**

1. **World Generation** (6-9 months) - FOUNDATION
   - Must exist first, everything depends on it
   
2. **Time & Calendar** (4-6 months) - FRAMEWORK
   - Needed for seasonal effects, festivals, NPC schedules
   
3. **Political System** (4-6 months) - PLAYER INTERACTION
   - Enables faction gameplay, reputation consequences
   
4. **Economy** (5-6 months) - PLAYER PROGRESSION
   - Meaningful use of loot, economic choices
   
5. **Knowledge & Discovery** (5-7 months) - META-PROGRESSION
   - Long-term engagement, completionist content

### **Parallel Development Opportunities:**

Some systems can be built simultaneously:
- **Political + Economy** (share reputation/pricing code)
- **Time + Knowledge** (bestiary unlocks during quests)
- **World Gen + Political** (region boundaries)

---

## 📊 Success Metrics

How to know if these systems are working:

✅ **Player Engagement**
- Average session length >1 hour
- Return rate >70% (players come back)
- Completion rates for quests/contracts

✅ **System Utilization**
- >50% players engage with political system (check reputation)
- >70% use economy features (banking, trade)
- >60% unlock bestiary entries

✅ **World Believability**
- Players reference in-game events in discussions
- "Emergent storytelling" shared on forums/videos
- NPCs feel alive (schedules, reactions)

✅ **Technical Performance**
- <2 second chunk generation time
- >60 FPS in settlements (1080p, medium settings)
- No memory leaks over 4+ hour sessions

✅ **Mod Compatibility**
- Works with 80%+ of popular Fabric mods
- Datapack system used by community
- Config options prevent conflicts

---

## 📝 Related Documentation

**Core Systems:**
- [WORLDGEN_SYSTEM.md](./WORLDGEN_SYSTEM.md) - World generation details
- [POLITICAL_SYSTEM.md](./POLITICAL_SYSTEM.md) - Factions and reputation
- [ECONOMY_TRADE_SYSTEM.md](./ECONOMY_TRADE_SYSTEM.md) - Currency and trade
- [TIME_CALENDAR_SYSTEM.md](./TIME_CALENDAR_SYSTEM.md) - Seasons and festivals
- [KNOWLEDGE_DISCOVERY_SYSTEM.md](./KNOWLEDGE_DISCOVERY_SYSTEM.md) - Bestiary and learning

**Gameplay Systems:**
- [QUEST_REPUTATION_SYSTEM_UPDATE.md](./QUEST_REPUTATION_SYSTEM_UPDATE.md) - Quest mechanics
- [FACTION_REPUTATION_SYSTEM.md](./FACTION_REPUTATION_SYSTEM.md) - Reputation details
- [DEVELOPMENT_ROADMAP.md](./DEVELOPMENT_ROADMAP.md) - Full feature roadmap

**Technical:**
- [PROJECT_STATUS.md](./PROJECT_STATUS.md) - Current implementation status
- [BUILD_JAR_GUIDE.md](./BUILD_JAR_GUIDE.md) - Building the mod

---

## 🎮 For Players

**What This Means for Your Experience:**

Instead of "Minecraft with Witcher items," you get:
- A living world that reacts to your actions
- Choices that matter (help Temeria or Nilfgaard?)
- Economic strategy (trade routes, investments, smuggling)
- Seasonal planning (prepare for winter, festivals to attend)
- Knowledge progression (become a true monster expert)

Every playthrough will be different based on:
- Which faction you align with
- How you manage your economy
- What knowledge you prioritize
- When and where you explore

This is **The Witcher experience in Minecraft** - not just combat and crafting, but politics, intrigue, preparation, and consequence.

---

**Status:** 📋 Design Complete  
**Next Steps:** Begin Phase 1 (World Generation) implementation  
**Estimated Completion:** 2025-2027 (full release)  
**Current Focus:** Establish foundation systems before content expansion
