# WitcherCraft - Naval & Exploration System

## Overview
A comprehensive maritime system for Skellige and coastal regions, featuring ship types, naval combat, underwater exploration, sea monsters, and trade routes. This system transforms ocean travel from obstacle to adventure.

---

## ⛵ Ship Types & Mechanics

### **1. Rowboat** (Early Game)
```
╔═══════════════════════════════════════╗
║ ROWBOAT                               ║
╠═══════════════════════════════════════╣
║ Size: 3x2 blocks                      ║
║ Capacity: 1 player                    ║
║ Storage: 9 slots (small chest)        ║
║ Speed: 4 blocks/second                ║
║ Durability: 100 HP                    ║
║ Weather Resistance: Poor              ║
║                                       ║
║ Crafting:                             ║
║  - Oak Planks x20                     ║
║  - Rope x4                            ║
║  - Iron Ingot x2 (oarlocks)           ║
║                                       ║
║ Best For: Coastal travel, fishing    ║
║ Weaknesses: Storms sink it, slow     ║
╚═══════════════════════════════════════╝
```

**Features:**
- Basic rowing mechanics (hold forward to row)
- Can't traverse deep ocean (depth limit: 30 blocks)
- Destroyed by large waves in storms
- Cheap and accessible

---

### **2. Longship** (Mid Game - Skellige Standard)
```
╔═══════════════════════════════════════╗
║ SKELLIGE LONGSHIP                     ║
╠═══════════════════════════════════════╣
║ Size: 12x4 blocks                     ║
║ Capacity: 4 players + 2 crew NPCs     ║
║ Storage: 27 slots (double chest)      ║
║ Speed: 8 blocks/second (sail)         ║
║        5 blocks/second (oars)         ║
║ Durability: 500 HP                    ║
║ Weather Resistance: Good              ║
║                                       ║
║ Crafting:                             ║
║  - Skellige Oak x60                   ║
║  - Canvas x10 (sail)                  ║
║  - Rope x20                           ║
║  - Iron Ingot x30                     ║
║  - Tar x5 (waterproofing)             ║
║                                       ║
║ Special Features:                     ║
║  - Dragon Head Prow (+10% speed)      ║
║  - Shield Rack (cosmetic)             ║
║  - Can ram smaller vessels            ║
║                                       ║
║ Best For: Raiding, fast travel        ║
╚═══════════════════════════════════════╝
```

**Features:**
- Wind-powered (sail mechanic - faster with wind)
- Oars for backup (slower but reliable)
- Cosmetic shields on sides (player banners)
- Can beach on shore (exit anywhere)
- Skellige reputation bonus when sailing

---

### **3. Merchant Vessel** (Trade Focus)
```
╔═══════════════════════════════════════╗
║ MERCHANT COG                          ║
╠═══════════════════════════════════════╗
║ Size: 16x6 blocks                     ║
║ Capacity: 2 players + 4 crew NPCs     ║
║ Storage: 54 slots (triple chest)      ║
║ Speed: 5 blocks/second                ║
║ Durability: 400 HP                    ║
║ Weather Resistance: Excellent         ║
║ Armor: Light (planks reinforced)      ║
║                                       ║
║ Crafting:                             ║
║  - Dark Oak Planks x100               ║
║  - Canvas x20                         ║
║  - Rope x30                           ║
║  - Iron Ingot x40                     ║
║  - Gold Ingot x10 (decorative)        ║
║                                       ║
║ Special Features:                     ║
║  - Cargo Hold (massive storage)       ║
║  - Slow but stable                    ║
║  - Merchant flag (reduces pirate      ║
║    attacks, increases bandit interest)║
║                                       ║
║ Best For: Trade routes, cargo         ║
╚═══════════════════════════════════════╝
```

**Features:**
- Built for carrying goods, not combat
- NPCs can crew it (hire sailors in port)
- Auto-trading routes (set destination, ship sails itself)
- Vulnerable to pirates (escort recommended)

---

### **4. Warship** (Combat Vessel)
```
╔═══════════════════════════════════════╗
║ NILFGAARDIAN WARSHIP                  ║
╠═══════════════════════════════════════╣
║ Size: 20x8 blocks                     ║
║ Capacity: 6 players + 8 crew NPCs     ║
║ Storage: 18 slots (small, combat gear)║
║ Speed: 6 blocks/second                ║
║ Durability: 800 HP                    ║
║ Weather Resistance: Excellent         ║
║ Armor: Heavy (iron plating)           ║
║                                       ║
║ Armament:                             ║
║  - 4x Cannons (port/starboard)        ║
║  - 2x Ballistae (fore/aft)            ║
║  - Flaming Arrows (anti-sail)         ║
║                                       ║
║ Crafting:                             ║
║  - Dark Iron Planks x150              ║
║  - Canvas x30                         ║
║  - Iron Blocks x50 (armor)            ║
║  - TNT x20 (cannon powder)            ║
║  - Gold Ingot x30 (mechanisms)        ║
║                                       ║
║ Requires: Honored+ Nilfgaard rep      ║
║ Best For: Naval combat, pirate hunting║
╚═══════════════════════════════════════╝
```

**Features:**
- Heavy armor (reduced damage from attacks)
- Cannons with aiming system
- Crew-operated (NPCs man stations)
- Faction-locked (Nilfgaard only for this variant)

---

## ⚔️ Naval Combat System

### **Combat Mechanics**

**1. Ship-to-Ship Combat:**
```
COMBAT PHASES:

Phase 1: APPROACH
- Maneuver into firing position
- Wind direction matters (faster with tailwind)
- Enemy AI tries to flank or flee

Phase 2: ENGAGEMENT
- Cannons: 50 block range, 10 second reload
  - Damage: 50 HP per hit
  - Aim: Lead target, account for movement
  - Ammo: Cannonballs (10 per stack)
  
- Ballistae: 80 block range, 5 second reload
  - Damage: 30 HP, pierces sails (slows enemy)
  - Ammo: Ballista Bolts (16 per stack)
  
- Fire Arrows: 40 block range, 3 second reload
  - Damage: 20 HP + burn (5 HP/sec for 10 sec)
  - Sets sails on fire (speed -50%)
  - Ammo: Flaming Arrows (64 per stack)

Phase 3: BOARDING
- Ram enemy ship (close distance)
- Grappling hooks deploy (automatic when <5 blocks)
- Melee combat on deck (player + crew vs enemy)
- Victory: Loot cargo, sink or capture ship

Phase 4: VICTORY/DEFEAT
- Victory: Loot, repair option, reputation gain
- Defeat: Ship sinks, respawn at nearest port, cargo lost
```

**2. Ramming Mechanics:**
```
Ramming Damage = (Ship Speed × Ship Mass) / Target Mass

Example:
Longship (speed 8, mass 100) rams Rowboat (mass 20)
Damage = (8 × 100) / 20 = 40 HP to rowboat
Recoil = (8 × 100) / 100 = 8 HP to longship

Heavy ships = better rams but slower
Light ships = agile but vulnerable
```

**3. Crew Combat Roles:**
```
Player assigns crew to stations:

- HELMSMAN: Steers ship (AI-controlled or player)
- CANNONEERS (2-4): Fire cannons when in range
- ARCHERS (2-4): Fire arrows at enemy sails/crew
- BOARDERS (2-4): Jump to enemy ship during boarding
- REPAIR CREW (1-2): Fix hull damage mid-combat

NPC Skill Affects Performance:
- Novice: 50% accuracy, slow reload
- Trained: 75% accuracy, normal reload
- Veteran: 90% accuracy, fast reload
- Master: 95% accuracy, +20% damage
```

---

## 🌊 Sea Monsters & Hazards

### **1. Sirens** (Common - Coastal Waters)
```
╔═══════════════════════════════════════╗
║ SIREN                                 ║
╠═══════════════════════════════════════╣
║ Threat Level: Medium                  ║
║ Spawn: Coastal areas, near rocks      ║
║ Behavior: Lure sailors with song      ║
║                                       ║
║ Abilities:                            ║
║  - Siren Song: Charm effect (30 sec)  ║
║    → Player/NPC steers toward rocks   ║
║  - Screech: Stun (5 sec), 15 block    ║
║  - Dive Attack: 20 damage, knockback  ║
║                                       ║
║ Weaknesses:                           ║
║  - Earplugs (alchemy craft, immunity) ║
║  - Aard Sign: Interrupt song          ║
║  - Crossbow: 2x damage vs flying      ║
║                                       ║
║ Loot:                                 ║
║  - Siren Vocal Cords (alchemy, rare)  ║
║  - Feathers (crafting)                ║
║  - Pearls (25% chance, valuable)      ║
║                                       ║
║ Tactics:                              ║
║ Sirens hunt in groups (3-5). Focus    ║
║ fire on one, use Aard to scatter.     ║
║ Don't approach nests (rocks) at night.║
╚═══════════════════════════════════════╝
```

---

### **2. Drowners (Aquatic Variant)**
```
╔═══════════════════════════════════════╗
║ DROWNER (SEA)                         ║
╠═══════════════════════════════════════╣
║ Threat Level: Low                     ║
║ Spawn: Shallow waters, shipwrecks     ║
║ Behavior: Ambush from below           ║
║                                       ║
║ Abilities:                            ║
║  - Underwater Lunge: 15 damage        ║
║  - Vomit (poison): 3 HP/sec, 10 sec   ║
║  - Swim Speed: Faster than player     ║
║                                       ║
║ Weaknesses:                           ║
║  - Necrophage Oil: +50% damage        ║
║  - Igni Sign: Fear effect (flee)      ║
║  - Silver Sword: +25% damage          ║
║                                       ║
║ Loot:                                 ║
║  - Drowner Brain (80%)                ║
║  - Venom Extract (30%)                ║
║  - Water Essence (10%)                ║
║                                       ║
║ Tactics:                              ║
║ Dive underwater to avoid surface      ║
║ attacks. Use crossbow while submerged.║
╚═══════════════════════════════════════╝
```

---

### **3. Kraken** (Rare - Deep Ocean Boss)
```
╔═══════════════════════════════════════╗
║ KRAKEN                          [BOSS]║
╠═══════════════════════════════════════╣
║ Threat Level: EXTREME                 ║
║ Spawn: Deep ocean (100+ blocks depth) ║
║ Behavior: Attacks ships, drags down   ║
║                                       ║
║ Stats:                                ║
║  - HP: 2000                           ║
║  - Armor: 20 (heavy resistance)       ║
║  - Size: 40x40 blocks (massive)       ║
║                                       ║
║ Abilities:                            ║
║  - Tentacle Slam: 50 damage, AOE      ║
║  - Ship Grab: Immobilize, 10 HP/sec   ║
║  - Whirlpool: Pulls ship toward mouth ║
║  - Ink Cloud: Blindness (30 sec)      ║
║  - Summon Drowners: 5 minions         ║
║                                       ║
║ Weaknesses:                           ║
║  - Hybrid Oil: +50% damage            ║
║  - Cannons: 2x damage (fire at head)  ║
║  - Yrden Sign: Slows tentacles        ║
║  - Bombs: Grapeshot damages all       ║
║                                       ║
║ Phases:                               ║
║ Phase 1: 4 tentacles attack ship      ║
║  → Destroy tentacles (200 HP each)    ║
║ Phase 2: Head emerges (500 HP)        ║
║  → Focus fire on weak spots           ║
║ Phase 3: Enraged (entire body, 800 HP)║
║  → Avoid whirlpool, sustained damage  ║
║                                       ║
║ Loot:                                 ║
║  - Kraken Trophy (legendary)          ║
║  - Tentacle (crafting material, rare) ║
║  - Ancient Pearls x10 (1000 gold each)║
║  - Kraken Ink (alchemy, unique)       ║
║  - Random Witcher Gear Diagram        ║
║                                       ║
║ Fame Reward: +500 (Continental Legend)║
╚═══════════════════════════════════════╝
```

**Kraken Fight Strategy:**
```
1. Bring Warship or Longship (rowboat = instant death)
2. Stock 200+ cannonballs
3. Hire veteran crew (4+ NPCs)
4. Craft Hybrid Oil beforehand
5. Fight in phases:
   - Destroy tentacles first (prevent grab)
   - When head emerges, focus all fire
   - Save Thunderbolt potion for final phase
6. Don't fight during storm (vision penalty)
```

---

### **4. Ice Giants** (Skellige North - Winter Only)
```
╔═══════════════════════════════════════╗
║ ICE GIANT                             ║
╠═══════════════════════════════════════╣
║ Threat Level: High                    ║
║ Spawn: Northern Skellige, icebergs    ║
║ Behavior: Throws ice chunks, freezes  ║
║                                       ║
║ Abilities:                            ║
║  - Ice Boulder: 40 damage, sinks small║
║    ships if direct hit                ║
║  - Frost Breath: Freeze water (blocks ║
║    ship movement for 20 seconds)      ║
║  - Iceberg Summon: Creates obstacles  ║
║                                       ║
║ Weaknesses:                           ║
║  - Igni Sign: 3x damage (fire vs ice) ║
║  - Fire Arrows: Melt armor (-5 defense║
║    per hit, stacks)                   ║
║  - Flaming Bombs: Massive damage      ║
║                                       ║
║ Loot:                                 ║
║  - Ice Giant Heart (alchemy, rare)    ║
║  - Frost Rune (enchanting)            ║
║  - Glacial Water (potion ingredient)  ║
╚═══════════════════════════════════════╝
```

---

### **5. Environmental Hazards**

**Storms:**
```
STORM SEVERITY LEVELS:

Light Storm (Common):
- Rain reduces visibility by 20%
- Waves cause ship to rock (nausea effect)
- Speed reduced by 10%
- No structural damage

Heavy Storm (Uncommon):
- Rain reduces visibility by 50%
- Large waves: -30% speed, 1 HP/sec damage
- Lightning strikes (rare, 50 damage)
- Rowboats have 50% sink chance

Hurricane (Rare):
- Visibility: 10% (near-blind)
- Massive waves: -60% speed, 5 HP/sec
- Frequent lightning (10 damage/strike)
- Rowboats/Longships: 80% sink chance
- Merchant Vessels: 30% sink chance
- Warships: 10% sink chance

Survival Tips:
- Lower sails (reduce speed = less damage)
- Anchor in safe harbor
- Use "Storm Resistance" potion (+50% durability)
- Pray to Melitele (luck-based protection)
```

**Icebergs (Skellige North):**
```
- Collision damage: 50-200 HP (based on speed)
- Hidden underwater (Witcher Senses detects)
- Can ground ships (stuck until melted/pushed)
- Loot: Frozen treasures inside (rare)
```

**Whirlpools (Deep Ocean):**
```
- Pulls ship in spiral (losing control)
- Escape: Full speed perpendicular to spin
- Failure: Ship dragged to center, 10 HP/sec
- Duration: 30 seconds, then disappears
- Sometimes spawns sea monsters
```

**Fog Banks:**
```
- Vision reduced to 10 blocks
- Easy to get lost (compass essential)
- Sirens hide in fog (ambush)
- Navigation: Follow stars (night) or use "True North" potion
```

---

## 🤿 Underwater Exploration

### **Diving Mechanics**

**Breathing System:**
```
Base Underwater Time: 30 seconds

Extensions:
- Killer Whale Potion: +5 minutes (alchemy)
- Enchanted Diving Helmet: Infinite (rare gear)
- Skellige Pearl Necklace: +2 minutes (craftable)

Drowning Damage: 2 HP/sec after oxygen depletes
Visual: Bubbles decrease, screen darkens
```

**Underwater Movement:**
```
Swimming Speed: 3 blocks/second (50% of land)
Diving: Hold crouch to descend
Surfacing: Hold jump to ascend
Combat: Reduced damage (-30%), slow attacks
Tools: Crossbow only (swords don't work well)
```

---

### **Underwater Locations**

**1. Shipwrecks:**
```
Types:
- Merchant Ship: Cargo hold with trade goods
- Warship: Armory with weapons/armor
- Ancient Vessel: Elven artifacts, rare loot

Loot Tables:
- Gold Coins (100-500)
- Weapons (rusted, -20% durability)
- Trade Goods (silks, spices, jewelry)
- Treasure Maps (15% chance)
- Witcher Gear Diagrams (5% chance)

Hazards:
- Drowners nest inside (3-5 spawn)
- Structural collapse (falling debris, 20 damage)
- Trapped chests (poison gas cloud)
```

**2. Underwater Caves:**
```
Features:
- Air pockets (can breathe inside)
- Glowing crystals (light source)
- Smuggler hideouts (black market cache)

Inhabitants:
- Drowners (common)
- Ekhidna (rare, water serpent)
- Smugglers (hostile if discovered)

Loot:
- Contraband goods (high value)
- Rare alchemy ingredients
- Ancient coins (sell to collectors)
```

**3. Sunken Ruins (Elven/Ancient):**
```
Description:
Massive stone structures on ocean floor.
Pre-human civilization, magical significance.

Features:
- Statues with glowing runes
- Treasure chambers (puzzle-locked doors)
- Magical barriers (dispel with Aard)
- Lore tablets (codex entries)

Guardians:
- Stone Golems (construct, immune to silver)
- Elemental Water Spirits
- Cursed Drowned Elves (wraith-like)

Rewards:
- Ancient Elven Weapons (legendary)
- Runestones (enchanting)
- Magic Focus Crystals (sign intensity +20%)
- Elder Speech scrolls (knowledge)
```

**4. Coral Reefs:**
```
Ecosystem:
- Colorful fish (decorative)
- Giant Crabs (hostile, armor)
- Sea Urchins (touch = poison)

Harvesting:
- Pearls (valuable, 50 gold each)
- Coral (alchemy ingredient)
- Seaweed (potion base)
- Rare fish (alchemy, cooking)

Dangers:
- Sirens nest nearby
- Strong currents (push player)
- Sharks (aggressive predator)
```

---

## 🗺️ Trade Routes & Economy

### **Maritime Trade System**

**Established Routes:**
```
Route 1: SKELLIGE → NOVIGRAD
- Distance: 3000 blocks
- Travel Time: 10 minutes (real-time)
- Cargo: Fish, furs, amber
- Profit: 500-800 gold (20% margin)
- Hazards: Pirates (30% encounter rate)

Route 2: NILFGAARD → SKELLIGE
- Distance: 4500 blocks
- Travel Time: 15 minutes
- Cargo: Wine, silk, spices
- Profit: 1000-1500 gold (40% margin)
- Hazards: Storms (40%), Ice Giants (20%)

Route 3: TEMERIA → KOVIR
- Distance: 2500 blocks
- Travel Time: 8 minutes
- Cargo: Grain, timber, iron
- Profit: 300-500 gold (15% margin)
- Hazards: Low (safe route)
```

**Player Trade Mechanics:**
```
1. Purchase Cargo (settlement dock)
2. Load onto Merchant Vessel
3. Sail to destination
4. Sell cargo at port
5. Profit = (Sell Price - Buy Price - Costs)

Costs:
- Crew wages: 10 gold/NPC/trip
- Repairs: Variable (combat/storm damage)
- Port fees: 2% of cargo value
- Bribes: 50 gold (if smuggling)

Risk vs Reward:
- Safe routes: Low profit, reliable
- Dangerous routes: High profit, risky
- Smuggling (contraband): 3x profit, illegal
```

---

### **Pirate Encounters**

**Pirate Types:**
```
1. OPPORTUNIST PIRATES (Common)
   - Small crew (3-5 NPCs)
   - Rowboats/Longships
   - Flee if losing
   - Loot: 50-200 gold

2. ORGANIZED GANGS (Uncommon)
   - Medium crew (6-10 NPCs)
   - Longships with rams
   - Fight to the death
   - Loot: 300-600 gold, rare items

3. LEGENDARY CAPTAINS (Rare, Boss)
   - Large crew (12-15 NPCs)
   - Warships with cannons
   - Unique abilities (captain buffs)
   - Loot: 1000+ gold, legendary gear
   - Fame reward: +200
```

**Pirate Hideouts:**
```
Hidden Coves (Skellige Islands):
- Pirate bases on remote islands
- Can attack to clear (quest)
- Loot: Stolen goods, captives to rescue
- Reputation: +100 Merchants Guild

After Clearing:
- Becomes safe harbor (free docking)
- Black market vendor spawns
- Can hire pirate NPCs as crew (cheaper, loyal)
```

---

## 🏆 Achievements & Progression

**Naval Achievements:**
```
⚓ Sea Legs - Sail 1000 blocks
⚓ Seasoned Sailor - Sail 10,000 blocks
⚓ Master Mariner - Sail 50,000 blocks

⚔️ First Blood - Sink your first enemy ship
⚔️ Pirate Hunter - Sink 10 pirate ships
⚔️ Terror of the Seas - Sink 50 ships

🐙 Kraken Slayer - Defeat a Kraken
🧜 Siren Song - Survive 10 Siren encounters
❄️ Icebreaker - Defeat an Ice Giant

💰 Profitable Voyage - Complete trade route with 1000+ gold profit
💰 Merchant Prince - Earn 10,000 gold from trade
💰 Tycoon of the Waves - Earn 100,000 gold from trade

🗺️ Deep Diver - Explore 10 shipwrecks
🗺️ Treasure Hunter - Find 5 underwater treasure caches
🗺️ Ancient Secrets - Discover sunken elven ruins
```

**Ship Upgrades:**
```
Cosmetic:
- Figureheads (dragon, wolf, eagle, etc.)
- Sail colors/designs
- Flag customization (player banner)

Functional:
- Reinforced Hull: +200 HP
- Enhanced Sails: +20% speed
- Expanded Cargo Hold: +18 storage slots
- Hidden Compartments: Smuggling (avoid detection)
- Iron Plating: +5 armor (slower speed)
```

---

## ⚙️ Configuration

```json5
// config/witchercraft/naval_system.json
{
  "ships": {
    "enabled": true,
    "rowboat_crafting": true,
    "longship_requires_skellige_rep": false,
    "warship_requires_faction_rep": true,
    "ship_durability_multiplier": 1.0,
    "ship_speed_multiplier": 1.0
  },
  
  "combat": {
    "naval_combat_enabled": true,
    "ramming_damage": true,
    "cannons_enabled": true,
    "boarding_mechanics": true,
    "crew_combat": true,
    "friendly_fire": false // crew can't damage own ship
  },
  
  "sea_monsters": {
    "sirens": true,
    "drowners_aquatic": true,
    "kraken": true,
    "ice_giants": true,
    "spawn_rate_multiplier": 1.0
  },
  
  "underwater": {
    "diving_enabled": true,
    "breathing_time_seconds": 30,
    "shipwrecks": true,
    "underwater_caves": true,
    "sunken_ruins": true,
    "coral_reefs": true
  },
  
  "trade": {
    "maritime_trade_routes": true,
    "npc_merchant_ships": true,
    "pirate_encounters": true,
    "pirate_encounter_chance": 0.30, // 30%
    "smuggling_allowed": true
  },
  
  "weather": {
    "storms": true,
    "storm_damage": true,
    "icebergs": true,
    "whirlpools": true,
    "fog_banks": true
  },
  
  "progression": {
    "achievements_enabled": true,
    "ship_upgrades": true,
    "cosmetic_customization": true
  }
}
```

---

## 🎯 Integration with Other Systems

| System | Integration |
|--------|-------------|
| **World Generation** | Skellige islands, coastlines, ocean depth, ports |
| **Political System** | Faction-specific ships, naval warfare, pirate reputation |
| **Economy** | Trade routes, cargo prices, port fees, smuggling |
| **Combat** | Naval battles, underwater combat, sea monster fights |
| **Quest System** | Escort missions, pirate contracts, treasure hunts |
| **Knowledge** | Bestiary entries for sea monsters, shipwreck lore |

---

## 🛠️ Implementation Phases

### **Phase 1: Basic Ships (4-5 weeks)**
- [ ] Rowboat entity and crafting
- [ ] Basic sailing mechanics (movement, steering)
- [ ] Ship durability and repair
- [ ] Docking at ports

### **Phase 2: Combat (5-6 weeks)**
- [ ] Longship and Warship types
- [ ] Cannon mechanics (aiming, firing, damage)
- [ ] Ramming collision system
- [ ] Boarding and crew combat

### **Phase 3: Sea Monsters (4-5 weeks)**
- [ ] Siren spawning and song mechanic
- [ ] Aquatic Drowners
- [ ] Kraken boss fight
- [ ] Ice Giant encounters

### **Phase 4: Underwater (5-6 weeks)**
- [ ] Diving and breathing system
- [ ] Shipwreck generation
- [ ] Underwater caves and ruins
- [ ] Coral reef ecosystems

### **Phase 5: Trade & Pirates (4-5 weeks)**
- [ ] Maritime trade routes
- [ ] NPC merchant ships
- [ ] Pirate spawning and AI
- [ ] Smuggling mechanics

### **Phase 6: Weather & Hazards (3-4 weeks)**
- [ ] Storm system (intensity levels)
- [ ] Icebergs (collision, grounding)
- [ ] Whirlpools and fog
- [ ] Environmental damage

**Total Estimated Time:** 25-31 weeks (6-8 months)

---

**Status:** 📋 Design Phase  
**Priority:** 🟡 Medium-High (Essential for Skellige content)  
**Dependencies:** World Generation (oceans, islands), Combat System  
**Related Files:** WORLDGEN_SYSTEM.md, ECONOMY_TRADE_SYSTEM.md, KNOWLEDGE_DISCOVERY_SYSTEM.md
