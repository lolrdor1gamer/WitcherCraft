# WitcherCraft - World Generation System

## Overview
Complete overhaul of Minecraft world generation to create **The Continent** - a Witcher-themed world with regions, custom biomes, strategic landmarks, and environmental storytelling.

---

## 🌍 Layered Generation Architecture

### Layer 1: Continental Layout (Macro-Scale)
```
World Size: 10,000 x 10,000 blocks (configurable)
Dimension: "witchercraft:the_continent" (separate dimension OR overworld replacement)

        SKELLIGE ISLES (NW)
              |
    [Mountain Range]
              |
    KAEDWEN (North) ← [Mountains] → REDANIA (NE)
              |
         TEMERIA (Central)
              |
      [Pontar River System]
              |
    NILFGAARD EMPIRE (South)
              |
        [Contested Border]
              |
      NO MAN'S LAND (War Zone)
```

**Features:**
- Finite world with natural borders (mountain ranges, seas)
- Major geographical features separate regions
- Rivers flow from mountains to sea (realistic hydrology)
- Coastlines only in Skellige and southern Nilfgaard
- World border represents edge of known civilization

---

### Layer 2: Regional Boundaries (Political)

Each region occupies ~2,000-3,000 block radius:

| Region | Climate | Terrain | Special Features |
|--------|---------|---------|-----------------|
| **Temeria** | Temperate | Plains, forests, rivers | Central hub, trade routes |
| **Redania** | Cold | Northern plains, lakes | Religious architecture, witch hunters |
| **Kaedwen** | Arctic | Mountains, taiga | Mining settlements, snow-covered |
| **Aedirn** | Temperate | Eastern plains, hills | Agricultural, dragon lore |
| **Nilfgaard** | Warm | Southern plains, scrubland | Imperial architecture, military |
| **Skellige** | Subarctic | Islands, coasts, tundra | Norse-inspired, naval focus |
| **No Man's Land** | Varies | War-torn, scorched | Ruins, battlefields, high danger |

**Zone Types Within Regions:**
- **Core Territory** (60%) - Safe, patrolled, many settlements
- **Border Zones** (30%) - Contested, mixed culture, fewer guards
- **Frontier** (10%) - Dangerous, monster-heavy, outposts only

**Region Detection:**
```java
// Coordinate-based noise function
Region getRegion(int x, int z) {
    double noise = continentalNoise.getValue(x / 8000.0, z / 8000.0);
    double temperature = temperatureNoise.getValue(x / 2000.0, z / 2000.0);
    
    // Northern regions (cold)
    if (noise > 0.4 && temperature < 0.3) return KAEDWEN;
    if (noise > 0.2 && temperature < 0.5) return REDANIA;
    
    // Central (moderate)
    if (Math.abs(noise) < 0.3) return TEMERIA;
    
    // Southern (warm)
    if (noise < -0.3 && temperature > 0.6) return NILFGAARD;
    
    // Islands (west coast)
    if (x < -4000 && isCoastal(x, z)) return SKELLIGE;
    
    // War zone (border areas)
    if (isBorderZone(noise, temperature)) return NO_MANS_LAND;
    
    return TEMERIA; // Default
}
```

---

### Layer 3: Biome System (Witcher-Themed)

**Temeria Biomes:**
- 🌾 `temerian_farmlands` - Flat plains, wheat/crops, villages
- 🌲 `mahakaman_foothills` - Rolling hills, oak/birch forests, dwarven ruins
- 🌊 `pontar_riverlands` - Rivers, marshes, drowner spawns
- 🏚️ `no_mans_land` - Scorched earth, ruins, high monster density

**Kaedwen Biomes:**
- ⛰️ `mountain_peaks` - Snow-capped, ice trolls, silver ore veins
- 🌲 `frozen_taiga` - Spruce forests, bears, harsh weather
- 🏔️ `mine_valleys` - Exposed ore, dwarven excavations, cave entrances
- ❄️ `glacial_lakes` - Frozen water, ice elementals, fishing holes

**Nilfgaard Biomes:**
- 🌅 `southern_plains` - Warm, golden grass, imperial estates
- 🫒 `olive_groves` - Mediterranean, unique crops, wealth
- 🏛️ `imperial_gardens` - Decorative flora, monuments, military camps
- 🏜️ `arid_scrubland` - Desert-like, scorpions, rare cacti herbs

**Skellige Biomes:**
- 🌊 `rocky_coastlines` - Cliffs, crashing waves, siren spawns
- ❄️ `tundra_plains` - Barren, windswept, bear dens, lichens
- 🧊 `glacier_fields` - Ice formations, frost giants, danger
- 🌊 `storm_seas` - Ocean biome, rough waters, shipwrecks

**Special Biomes (Cross-Regional):**
- ⚔️ `battlefield` - Craters, rusted weapons, wraith spawns, mass graves
- 🌫️ `cursed_swamp` - Foggy, foglets, water hags, rare herbs
- 🏛️ `ancient_ruins` - Elven/dwarven structures, artifacts, guardians
- 🌲 `brokilon_forest` - Dense, magical, dryad territory (hostile zone)

**Biome Selection Logic:**
```java
Biome getBiome(Region region, int x, int z) {
    double humidity = humidityNoise.getValue(x / 500.0, z / 500.0);
    double erosion = erosionNoise.getValue(x / 300.0, z / 300.0);
    
    switch(region) {
        case TEMERIA:
            if (humidity > 0.6) return PONTAR_RIVERLANDS;
            if (erosion > 0.4) return MAHAKAMAN_FOOTHILLS;
            return TEMERIAN_FARMLANDS;
            
        case KAEDWEN:
            if (erosion > 0.7) return MOUNTAIN_PEAKS;
            if (humidity < 0.3) return FROZEN_TAIGA;
            return MINE_VALLEYS;
            
        // ... similar logic for other regions
    }
    
    // Special biomes (rare spawns)
    if (isSpecialLocation(x, z)) {
        return getSpecialBiome(x, z);
    }
}
```

---

### Layer 4: Natural Landmarks (World Features)

#### **Major Landmarks**
| Type | Spawn Rate | Purpose |
|------|-----------|---------|
| 🗿 **Place of Power** | 1 per 2000 blocks | Skill points, monster guardians, meditation spots |
| 🌳 **Ancient Tree** | 1 per 1500 blocks | 30+ blocks tall, rare herbs, druid significance |
| 🏔️ **Mountain Pass** | Border regions | Only routes between kingdoms, can be blocked/controlled |
| 🌉 **Bridge** | River crossings | Troll toll-keepers, strategic choke points |
| 🏰 **Ruined Castle** | 1 per 3000 blocks | Boss monsters, loot, curse sites, reclaim quests |

#### **Environmental Features**

**1. River Systems**
```java
// Realistic water flow from mountains to sea
- Source: Mountain peaks (elevation 150+)
- Flow: Downhill pathfinding to ocean/lake
- Width: 5-20 blocks based on tributary count
- Depth: 2-6 blocks
- Spawns: Drowners (75%), Nekkers (25%), Water Hags (rare)
- Features: Fishing spots, underwater caves, hidden caches
- Trade: Caravans follow rivers (safe routes)
```

**2. Cave Networks**
```java
// Underground exploration system
Types:
- Natural Caves: Ore veins, spiders, bats, endregas
- Abandoned Mines: Rail tracks, silver/dark iron, ghosts
- Troll Dens: Large chambers, treasure hoards, negotiation
- Smuggler Hideouts: Black market, stolen goods, bandits
- Ancient Tunnels: Connect regions, fast travel when discovered

Generation:
- Entrance: 1 per 800 blocks (visible on surface)
- Depth: Y=10 to Y=60
- Connectivity: 30% chance to link to another cave system
- Loot: Tier scales with depth and distance from settlements
```

**3. Battlefields**
```java
// Historical war sites with environmental storytelling
Features:
- Crater terrain (explosions, siege weapons)
- Rusted weapons/armor scattered (low durability loot)
- Mass graves (10-30 skulls, wraith spawns at night)
- Broken banners (indicates which kingdoms fought)
- Journal/letter items (environmental narrative)
- Cursed zones (permanent wraith spawning)

Frequency: 1 per 2500 blocks in border regions
Night Danger: Very High (wraiths, ghouls, nightwraiths)
Loot Quality: Medium (historical weapons, old coins)
```

**4. Abandoned Settlements**
```java
// Ghost towns - former villages destroyed by monsters/war
States:
- Recently Abandoned (buildings intact, some loot, monster nest nearby)
- Partially Ruined (damaged structures, overgrown, ghosts)
- Ancient Ruins (foundation only, archaeological value, elven/dwarven)

Generation: 15% of settlement spawns become abandoned
Quests: "Reclaim Settlement" - clear nest, rebuild (becomes functional village)
Spawns: Ghouls, wraiths, foglets (depends on how long abandoned)
```

---

### Layer 5: Resource Distribution (Strategic)

#### **Alchemy Herbs (Biome-Specific)**

| Biome Type | Common Herbs | Rare Herbs | Special Nodes |
|------------|--------------|------------|---------------|
| Swamps | Crow's Eye, Blowball | White Myrtle | Water Hag Teeth (kill drops) |
| Forests | Celandine, Wolfsbane | Mandrake Root | Ancient tree groves |
| Mountains | Sewant Mushroom, Verbena | White Gull ingredients | Caves with fungi |
| Coasts | Saltpeter, Fool's Parsley | Pearls (underwater) | Siren gardens |
| Plains | Berbercane Fruit, Honeycomb | Verbena | Beehive structures |
| Battlefields | Wormwood, Han | Rebis | Corpse pile nodes |

**Herb Spawning:**
```java
// Time-based regeneration
- Herbs respawn after 3 Minecraft days (1 hour real-time)
- Witcher Senses highlights herbs within 15 blocks
- Quality varies: Normal (80%), Enhanced (15%), Superior (5%)
- Quality affects potion strength when crafted
```

#### **Ore & Crafting Materials**

| Material | Location | Uses | Rarity |
|----------|----------|------|--------|
| **Silver Ore** | Mountains (Y=10-50) | Silver swords, monster-slaying bonus | Uncommon |
| **Meteorite Ore** | Impact sites (special structure) | Witcher gear, magical properties | Rare |
| **Dark Iron** | Kaedwen mines (Y=5-30) | Durable weapons/armor | Uncommon |
| **Dimeritium** | Nilfgaard territory (Y=20-40) | Anti-magic items, bombs | Rare |
| **Monster Parts** | Kill drops, nests | Alchemy, crafting, quest items | Varies |

**Regional Wood Types:**
```java
- Kaedwen Spruce: Cold resistance bonus on armor
- Skellige Oak: Durability +20% on weapons
- Mahakaman Birch: Lightweight, speed bonus
- Nilfgaard Acacia: Fire resistance properties
- Brokilon Elder Wood: Magic conductivity (wands, staffs)
```

#### **Monster Nest Distribution**

**Placement Logic:**
```java
// Strategic monster ecology
Nest Type: Determined by biome + region
Frequency: 1 nest per 1200 blocks (adjustable in config)
Proximity Check: No nests within 300 blocks of settlements (core territory)
                  Can spawn 100-300 blocks away (border zones)
                  
Nest Mechanics:
- Spawns 3-8 monsters every 10 minutes (day) or 5 minutes (night)
- Max population: 12 monsters per nest
- Destruction: Requires bombs or fire (Igni sign weakens structure)
- Loot: Monster parts, eggs, mutagens (tier based on nest age)
- Respawn: Never (permanent clearance) unless "Dynamic World" config enabled
```

**Regional Monster Nests:**
```
Temeria: Drowner nests (rivers), Nekker warrens (forests), Ghoul dens (battlefields)
Kaedwen: Ice Troll caves, Endrega hives, Wolf packs
Nilfgaard: Scorpion burrows, Basilisk nests, Bandit camps (human "monsters")
Skellige: Siren coves, Bear dens, Ice Elemental spawns
No Man's Land: Mixed (all types), higher density, cursed variants
```

---

## 🎮 Dimension & World Options

### **Mode 1: Separate Dimension (RECOMMENDED)**
```json5
// Config: world_generation.json
{
  "mode": "dimension",
  "dimension_id": "witchercraft:the_continent",
  "access_method": "portal", // or "spawn_in_dimension"
  "portal_block": "witchercraft:witcher_portal_frame",
  "portal_activation": "flint_and_steel", // vanilla mechanic
  "world_size": 10000,
  "world_border_type": "natural" // mountains/sea vs hard border
}
```

**Pros:**
- No conflicts with other worldgen mods
- Controlled world size (performance optimization)
- Pure Witcher experience
- Can have multiple "continents" as separate dimensions

**Cons:**
- Requires portal travel
- Separate from vanilla Minecraft content

**Portal Structure:**
```
□ □ □ □ □
□ W W W □
□ W   W □
□ W W W □
□ □ □ □ □

W = Witcher Portal Frame (new block, crafted with silver + obsidian)
Activate with Flint & Steel
Portal destination: Random spawn point in The Continent
```

---

### **Mode 2: Overworld Replacement**
```json5
{
  "mode": "overworld_replacement",
  "replace_vanilla_biomes": true,
  "keep_vanilla_structures": false, // villages, temples, etc.
  "dimension_id": "minecraft:overworld"
}
```

**Pros:**
- Seamless integration
- No portal needed
- Feels like total conversion mod

**Cons:**
- Incompatible with other worldgen mods
- All vanilla biomes gone
- May break other mods expecting vanilla biomes

---

### **Mode 3: Hybrid (BEST FLEXIBILITY)**
```json5
{
  "mode": "hybrid",
  "vanilla_biomes_enabled": true,
  "witcher_biome_weight": 0.7, // 70% Witcher, 30% vanilla
  "enable_both_villages": false, // only Witcher settlements
  "biome_blend_zones": true // gradual transition
}
```

**Pros:**
- Compatible with most mods
- Players choose experience via config
- Can mix Witcher and Minecraft content

**Cons:**
- Less immersive (breaks lore)
- Harder to balance

---

## 🗺️ World Map System

### **In-Game Map Features**

**Map Item:** `witcher_map` (craftable: paper + ink + compass)

**Features:**
1. **Fog of War** - World reveals as you explore (128 block reveal radius)
2. **Region Colors** - Each kingdom has distinct color overlay
3. **Settlement Icons:**
   - 🏘️ Hamlet (white)
   - 🏛️ Village (yellow)
   - 🏰 Town (orange)
   - 👑 City (gold)
   - ⚔️ Outpost (red)
4. **Points of Interest:**
   - 🗿 Place of Power (purple)
   - 🏚️ Ruins (gray)
   - 🕳️ Cave entrance (brown)
   - 🌳 Ancient Tree (green)
   - ⚠️ Monster Nest (red pulsing)
5. **Dynamic Updates:**
   - War fronts move in real-time
   - Destroyed settlements marked with X
   - New discoveries appear immediately

**Navigation:**
- Zoom levels: 1:1000, 1:2000, 1:5000
- Rotation: North always up (or player-facing toggle)
- Markers: Players can place custom waypoints (up to 20)
- Notes: Click icon to add text note
- Sharing: Maps can be copied and traded

---

### **Fast Travel System**

**Signposts:** Wooden posts at settlements and crossroads

**Mechanics:**
- Must discover by physically visiting (Witcher Senses highlight from 50 blocks)
- Right-click signpost → Opens map with discovered locations
- Select destination → 3-second channel time → Teleport
- Cannot fast travel while:
  - In combat (enemies within 30 blocks)
  - Overencumbered (inventory >80% full)
  - In dangerous area (monster nest nearby)
  - At night (unless in safe settlement)

**Signpost Network:**
- Auto-generates at every settlement
- Can craft portable signpost (expensive: meteorite + dimeritium)
- Portable signpost is one-time use, creates permanent fast travel point

---

## 🌟 Environmental Storytelling

### **Procedural Narrative Elements**

The world generates stories without scripted quests:

**1. Burned Villages**
```
Generation: Near Nilfgaard borders (20% of settlements)
Features:
- Charred wood blocks replace normal logs
- Destroyed roofs, broken walls
- Corpses (armor stands) scattered
- Nilfgaardian banners planted (occupied)
- Loot: Low quality, picked clean
- Spawns: Ghouls feeding on corpses
Environmental Story: Recent Nilfgaardian raid/occupation
Player Action: Can liberate (kill occupiers) → settlement slowly rebuilds
```

**2. Destroyed Caravans**
```
Generation: Roads between settlements (15% of road segments)
Features:
- Overturned carts with scattered crates
- Dead horses (skeleton entities)
- Merchant corpses with journals ("We were attacked by...")
- Loot: Trade goods, letters, low-tier equipment
- Tracks: Witcher Senses reveals monster/bandit trail
Environmental Story: Trade route dangers
Player Action: Follow tracks → Find monster nest or bandit camp → Clear threat
```

**3. Refugee Camps**
```
Generation: Border zones, near war fronts
Features:
- Makeshift tents (wool + sticks)
- Campfires, food supplies (low quality)
- NPC refugees (generic villagers)
- Notice board with "Lost Family" quests
- Disease risk (debuff if player lingers)
Environmental Story: War displacement
Player Action: Donate food/gold, accept rescue quests, ignore and move on
```

**4. Mass Graves**
```
Generation: Battlefields, near destroyed settlements
Features:
- Dirt mounds with skull decorations
- Ghostly particles at night
- Wraith spawns (2-5) after sunset
- Flowers placed by mourners (randomized)
- Tombstones with procedural names
Environmental Story: Historical tragedy, unfinished business
Player Action: Slay wraiths → Curse lifts → Loot chest appears
```

**5. Abandoned Homesteads**
```
Generation: Frontier zones (scattered, 1 per 600 blocks)
Features:
- Single house, small garden (overgrown)
- Personal belongings still inside
- Journal/letter: "Monsters came at night..." (procedural text)
- Monster nest within 150 blocks (cause of abandonment)
- Loot: Family heirlooms, basic supplies
Environmental Story: Personal tragedy
Player Action: Destroy nest → Family may return (dynamic event)
```

---

## ⚙️ Performance Optimization

### **Generation Optimizations**
```java
// Techniques to maintain performance:

1. LOD (Level of Detail)
   - Distant structures (>500 blocks): Render as simple models
   - Close structures (<500 blocks): Full detail
   
2. Structure Pre-caching
   - Common buildings loaded into memory at startup
   - Reduces disk I/O during chunk generation
   
3. Lazy Loading
   - Settlements generate core buildings first
   - Detail structures (gardens, wells) load when player approaches
   
4. Chunk Prioritization
   - Settlements and roads generate before wilderness
   - Player proximity affects generation queue
   
5. Entity Limits
   - Max NPCs per chunk: 15 (configurable)
   - Max monsters per chunk: 8
   - Despawn distant entities (>64 blocks)
```

### **Configuration Options**
```json5
{
  "performance": {
    "settlement_density": "normal", // low, normal, high
    "structure_detail": "high", // low, medium, high
    "max_npcs_per_settlement": 30,
    "monster_nest_frequency": 1.0, // multiplier
    "enable_dynamic_world": true, // settlements grow/decline
    "render_distance_override": false, // respect client settings
    "pregenerate_spawn_chunks": true // 10x10 chunk radius
  }
}
```

---

## 📊 Integration with Other Systems

| System | Integration Point |
|--------|------------------|
| **Quest System** | Settlements spawn notice boards, NPCs offer region-appropriate quests |
| **Reputation** | Region detection affects NPC interactions, guard behavior |
| **Monster Spawns** | Biome + region determine which monsters appear |
| **Alchemy** | Herb spawns tied to biomes, regional recipes in settlements |
| **Economy** | Trade routes connect settlements, regional goods available |
| **Combat** | Environmental hazards (cliffs, water) affect battles |
| **Progression** | Places of Power grant skill points upon meditation |
| **Witcher Senses** | Highlights tracks, herbs, hidden caves in world |

---

## 🛠️ Implementation Phases

### **Phase 1: Foundation (4-6 weeks)**
- [ ] Create `the_continent` dimension
- [ ] Implement `RegionProvider` (coordinate-based region detection)
- [ ] Build Temeria and Nilfgaard biomes (2 contrasting examples)
- [ ] Basic terrain generation for test biomes
- [ ] Portal structure and activation

### **Phase 2: Biome Expansion (6-8 weeks)**
- [ ] All regional biomes (20+ total)
- [ ] Natural landmarks (Places of Power, ancient trees, bridges)
- [ ] River system generation with realistic flow
- [ ] Cave networks with connectivity

### **Phase 3: Structure Generation (8-10 weeks)**
- [ ] Settlement generation system (hamlets, villages, towns)
- [ ] Regional architecture templates (50+ buildings per region)
- [ ] Road networks connecting settlements
- [ ] Special structures (battlefields, ruins, camps)

### **Phase 4: Resource & Ecology (4-6 weeks)**
- [ ] Herb spawning system (biome-specific)
- [ ] Ore distribution (silver, meteorite, dark iron)
- [ ] Monster nest placement and mechanics
- [ ] Environmental storytelling elements

### **Phase 5: Polish & Integration (6-8 weeks)**
- [ ] World map system (fog of war, icons, markers)
- [ ] Fast travel signposts
- [ ] Dynamic events (wars, settlement growth)
- [ ] Performance optimization (LOD, caching)
- [ ] Config options for customization

**Total Estimated Time:** 28-38 weeks (6-9 months)

---

## 🎯 Success Criteria

A successful world generation system will:

✅ **Feel Like The Witcher Universe**
- Regions are recognizable from lore
- Architecture matches kingdom aesthetics
- Monster spawns make ecological sense

✅ **Encourage Exploration**
- Rewards for discovering landmarks
- Environmental storytelling engages players
- Each region offers unique content

✅ **Integrate Seamlessly**
- Quest system uses world features naturally
- Economy flows through trade routes
- Combat uses terrain strategically

✅ **Perform Well**
- No lag spikes during chunk generation
- Render distance doesn't tank FPS
- Supports multiplayer (10+ players)

✅ **Be Configurable**
- Players can adjust density, detail, difficulty
- Compatible with other mods (when possible)
- Datapack support for custom regions

---

## 📝 Configuration File Example

```json5
// config/witchercraft/world_generation.json
{
  "dimension": {
    "mode": "separate_dimension", // "separate_dimension", "overworld_replacement", "hybrid"
    "dimension_id": "witchercraft:the_continent",
    "world_size": 10000,
    "spawn_point": "auto" // or specific coordinates [x, z]
  },
  
  "regions": {
    "enabled": ["temeria", "nilfgaard", "kaedwen", "redania", "skellige"],
    "disabled": [], // can disable specific regions
    "border_type": "dynamic", // "dynamic" (wars change borders) or "static"
    "border_width": 500 // blocks of transition zone
  },
  
  "settlements": {
    "replace_vanilla_villages": true,
    "density": "normal", // "sparse" (50%), "normal" (100%), "dense" (150%)
    "minimum_distance": 800, // blocks between settlements
    "max_size_tier": "city", // "hamlet", "village", "town", "city"
    "enable_abandoned": true, // 15% of settlements spawn as ruins
    "enable_growth": true // settlements can upgrade over time
  },
  
  "landmarks": {
    "places_of_power": {
      "enabled": true,
      "frequency": 2000 // blocks between spawns
    },
    "ancient_trees": {
      "enabled": true,
      "frequency": 1500
    },
    "caves": {
      "enabled": true,
      "frequency": 800,
      "connectivity": 0.3 // 30% chance caves link together
    },
    "battlefields": {
      "enabled": true,
      "frequency": 2500,
      "only_border_zones": true
    }
  },
  
  "resources": {
    "herb_respawn_time": 72000, // ticks (1 hour real-time)
    "herb_quality_distribution": {
      "normal": 0.80,
      "enhanced": 0.15,
      "superior": 0.05
    },
    "ore_vein_size": {
      "silver": 8,
      "meteorite": 3,
      "dark_iron": 12
    },
    "monster_nests": {
      "enabled": true,
      "frequency": 1200,
      "min_distance_from_settlements": 300,
      "respawn_after_destruction": false
    }
  },
  
  "performance": {
    "structure_detail": "high", // "low", "medium", "high"
    "max_npcs_per_chunk": 15,
    "max_monsters_per_chunk": 8,
    "lod_distance": 500, // blocks (structures simplify beyond this)
    "pregenerate_radius": 10 // chunks around spawn
  },
  
  "features": {
    "environmental_storytelling": true,
    "dynamic_world_events": true,
    "weather_by_region": true,
    "seasonal_effects": true
  }
}
```

---

## 🔗 Related Systems

- **POLITICAL_SYSTEM.md** - Region reputation, factions, wars
- **ECONOMY_TRADE_SYSTEM.md** - Merchant networks, trade routes
- **QUEST_REPUTATION_SYSTEM_UPDATE.md** - Regional contracts, notice boards
- **TIME_CALENDAR_SYSTEM.md** - Seasonal changes, festivals
- **KNOWLEDGE_DISCOVERY_SYSTEM.md** - Map exploration, bestiary unlocks

---

**Status:** 📋 Design Phase  
**Priority:** 🔴 Critical (Foundation for entire mod experience)  
**Estimated Effort:** 6-9 months full implementation  
**Dependencies:** None (this IS the foundation)
