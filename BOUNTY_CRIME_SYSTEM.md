# WitcherCraft - Bounty & Crime System

## Overview
A comprehensive crime and punishment system where illegal actions result in bounties, wanted levels, bounty hunters, and law enforcement responses. Players must navigate a world where actions have consequences, from petty theft to mass murder.

---

## ⚖️ Crime Categories

### **Crime Types & Penalties**
```
╔═══════════════════════════════════════╗
║ MINOR CRIMES (Tier 1)                 ║
╠═══════════════════════════════════════╣
║                                       ║
║ Trespassing:                          ║
║  - Bounty: 10 gold                    ║
║  - Witness: Guards warn first time    ║
║  - Repeat: Arrest and fine            ║
║                                       ║
║ Theft (under 50 gold value):          ║
║  - Bounty: 2x item value              ║
║  - Witness: Guards search inventory   ║
║  - Confiscate stolen goods            ║
║                                       ║
║ Assault (hit NPC, no kill):           ║
║  - Bounty: 25 gold                    ║
║  - Witness: Guards intervene          ║
║  - Escalates to combat if resist      ║
║                                       ║
║ Disturbing Peace:                     ║
║  - Bounty: 5 gold                     ║
║  - Examples: Igni in town, Aard NPCs  ║
║  - Warning first, fine second         ║
╚═══════════════════════════════════════╝

╔═══════════════════════════════════════╗
║ MODERATE CRIMES (Tier 2)              ║
╠═══════════════════════════════════════╣
║                                       ║
║ Grand Theft (50+ gold value):         ║
║  - Bounty: 3x item value              ║
║  - Wanted Level: 1 star               ║
║  - Guards actively search for you     ║
║                                       ║
║ Assault on Guard:                     ║
║  - Bounty: 100 gold                   ║
║  - Wanted Level: 2 stars              ║
║  - All guards in city hostile         ║
║                                       ║
║ Breaking & Entering:                  ║
║  - Bounty: 50 gold                    ║
║  - If steal inside: +theft bounty     ║
║  - Locked doors = illegal entry       ║
║                                       ║
║ Smuggling (contraband):               ║
║  - Bounty: 200 gold                   ║
║  - Confiscate ALL contraband          ║
║  - Border guards check cargo          ║
╚═══════════════════════════════════════╝

╔═══════════════════════════════════════╗
║ MAJOR CRIMES (Tier 3)                 ║
╠═══════════════════════════════════════╣
║                                       ║
║ Murder (civilian):                    ║
║  - Bounty: 1000 gold                  ║
║  - Wanted Level: 3 stars              ║
║  - Bounty hunters dispatched          ║
║  - Faction reputation: -100           ║
║                                       ║
║ Murder (guard):                       ║
║  - Bounty: 2500 gold                  ║
║  - Wanted Level: 4 stars              ║
║  - Entire faction turns hostile       ║
║  - Faction reputation: -500           ║
║                                       ║
║ Mass Murder (5+ NPCs):                ║
║  - Bounty: 10,000 gold                ║
║  - Wanted Level: 5 stars (MAX)        ║
║  - Legendary bounty hunters           ║
║  - Continental-wide manhunt           ║
║  - Faction reputation: Hated          ║
║                                       ║
║ Regicide (kill noble/king):           ║
║  - Bounty: 50,000 gold                ║
║  - Wanted Level: 5 stars              ║
║  - Entire kingdom hostile             ║
║  - Cannot enter any faction territory ║
║  - Execution on sight                 ║
╚═══════════════════════════════════════╝
```

---

## ⭐ Wanted Level System

### **Star Levels (1-5)**
```
★☆☆☆☆ WANTED LEVEL 1 - PETTY CRIMINAL
Bounty: 50-200 gold
Response:
- City guards search for you
- 2 guards patrol looking
- Can bribe (50 gold) to dismiss
- Leave city = wanted level fades after 10 min

★★☆☆☆ WANTED LEVEL 2 - CRIMINAL
Bounty: 200-500 gold
Response:
- 4-6 guards actively hunt you
- Gates closed (cannot fast travel out)
- Wanted posters appear in city
- Bribing costs 200 gold

★★★☆☆ WANTED LEVEL 3 - OUTLAW
Bounty: 500-2000 gold
Response:
- 8-10 guards + 2 guard captains
- Bounty hunters spawn (1-2 novice)
- All city entrances guarded
- Bounty spreads to neighboring settlements
- Bribing costs 500 gold (must find corrupt guard)

★★★★☆ WANTED LEVEL 4 - DANGEROUS CRIMINAL
Bounty: 2000-10,000 gold
Response:
- 10+ guards + 4 captains + elite guard squad
- 3-5 experienced bounty hunters
- Regional manhunt (entire faction searches)
- NPCs report sightings (reveal location)
- Cannot bribe (too serious)
- Must pay bounty or serve time

★★★★★ WANTED LEVEL 5 - CONTINENTAL THREAT
Bounty: 10,000+ gold
Response:
- Entire factions mobilize
- 5+ legendary bounty hunters (boss-level)
- Military squads (10+ soldiers each)
- Checkpoints on all roads
- Settlements evacuate when you approach
- Shoot on sight orders
- Only escape: Flee to neutral/enemy territory
```

---

## 👮 Law Enforcement Response

### **Guard AI Behavior**

**Detection System:**
```
How Guards Detect Crimes:

1. LINE OF SIGHT:
   - Guards have 30 block vision cone
   - See crime = instant wanted level
   - "Stop right there, criminal scum!"

2. CIVILIAN WITNESSES:
   - NPCs witness crime
   - Run to nearest guard
   - Report your description
   - Guards search area (5 min)

3. CRIME SCENE:
   - Dead bodies alert guards
   - Stolen goods noticed (merchant checks)
   - Broken locks found
   - Investigation starts (guards search)

4. DISGUISE & STEALTH:
   - Wearing faction armor = disguise (50% avoid detection)
   - Nighttime crimes = -50% witness chance
   - Crowded areas = easier to blend in
   - Witcher Senses: See guard patrol routes
```

**Guard Combat:**
```
Guard Tiers:

REGULAR GUARD (Common):
- HP: 100
- Damage: 15-20
- Equipment: Iron sword, chainmail
- Behavior: Surround player, call for backup

GUARD CAPTAIN (Uncommon):
- HP: 200
- Damage: 25-30
- Equipment: Steel sword, plate armor
- Behavior: Coordinate guards, use tactics

ELITE GUARD (Rare):
- HP: 350
- Damage: 40-50
- Equipment: Mastercrafted armor, shield
- Behavior: Tank, protect weaker guards

ROYAL GUARD (Legendary):
- HP: 500
- Damage: 60-75
- Equipment: Witcher-tier gear
- Behavior: Boss-level AI, use potions
```

---

## 🎯 Bounty Hunters

### **Bounty Hunter Types**

**Novice Hunter (Wanted Level 3):**
```
╔═══════════════════════════════════════╗
║ NOVICE BOUNTY HUNTER                  ║
╠═══════════════════════════════════════╣
║ HP: 150                               ║
║ Damage: 20-30                         ║
║ Equipment: Steel sword, leather armor ║
║                                       ║
║ Tactics:                              ║
║  - Solo or duo (1-2 hunters)          ║
║  - Direct combat (no strategy)        ║
║  - Flee if losing (below 30% HP)      ║
║                                       ║
║ Loot:                                 ║
║  - 50-100 gold                        ║
║  - Basic crafting materials           ║
║  - Bounty contract (readable lore)    ║
╚═══════════════════════════════════════╝
```

**Experienced Hunter (Wanted Level 4):**
```
╔═══════════════════════════════════════╗
║ EXPERIENCED BOUNTY HUNTER             ║
╠═══════════════════════════════════════╣
║ HP: 300                               ║
║ Damage: 40-55                         ║
║ Equipment: Silver sword, chainmail    ║
║                                       ║
║ Tactics:                              ║
║  - Groups (3-4 hunters)               ║
║  - Use bombs and crossbows            ║
║  - Set traps along roads              ║
║  - Flank and surround                 ║
║                                       ║
║ Special:                              ║
║  - Drink potions (Swallow, Thunderbolt)║
║  - Net traps (immobilize player)      ║
║  - Fight to the death (no fleeing)    ║
║                                       ║
║ Loot:                                 ║
║  - 200-400 gold                       ║
║  - Enhanced weapons                   ║
║  - Potions/bombs                      ║
║  - Rare crafting materials            ║
╚═══════════════════════════════════════╝
```

**Legendary Hunter (Wanted Level 5):**
```
╔═══════════════════════════════════════╗
║ LEGENDARY BOUNTY HUNTER         [BOSS]║
╠═══════════════════════════════════════╣
║ HP: 600                               ║
║ Damage: 70-90                         ║
║ Equipment: Witcher-tier gear          ║
║                                       ║
║ Named Characters:                     ║
║  - "The Viper" (poison specialist)    ║
║  - "Ironhide" (tank build)            ║
║  - "Shadowblade" (stealth assassin)   ║
║  - "The Huntmaster" (tactician)       ║
║                                       ║
║ Tactics:                              ║
║  - Solo but extremely dangerous       ║
║  - Uses Signs (Aard, Igni, Quen)      ║
║  - Advanced combat (dodges, counters) ║
║  - Hires mercenaries (5+ backup)      ║
║  - Ambushes at night                  ║
║  - Sets elaborate traps               ║
║                                       ║
║ Special Abilities:                    ║
║  - "Hunter's Mark": Reveals your      ║
║    location every 30 seconds          ║
║  - "Relentless Pursuit": Tracks you   ║
║    across map (no escape)             ║
║  - "Last Stand": Becomes invulnerable ║
║    for 10 sec at 20% HP               ║
║                                       ║
║ Loot:                                 ║
║  - 1000-2000 gold                     ║
║  - Legendary weapons/armor            ║
║  - Unique trophy (display item)       ║
║  - Hunter's Codex (lore book)         ║
║                                       ║
║ Fame Reward: +100 (if defeated)       ║
╚═══════════════════════════════════════╝
```

**Spawn Behavior:**
```
Bounty hunters spawn:
- 5-15 minutes after reaching wanted level
- Random locations (roads, camps, cities)
- Track player location (move toward you)
- Ambush preferred (nighttime, forests)
- Never stop until you're dead or bounty cleared

Hunter Count by Wanted Level:
- 3★: 1-2 novice hunters
- 4★: 3-5 experienced hunters
- 5★: 1 legendary + 5 mercenaries

Respawn: If killed, new hunters spawn after 10 min
```

---

## 🛡️ Avoiding Capture

### **Stealth & Disguises**

**Disguise System:**
```
Wearing faction armor reduces detection:

GUARD ARMOR (faction-specific):
- 70% chance guards don't recognize you
- Walk slowly (running = suspicious)
- Cannot attack (breaks disguise)
- Limited duration (10 min before spotted)

CIVILIAN CLOTHES:
- 30% chance to blend in crowds
- Only works in populated areas
- Guards scan faces (Witcher eyes = giveaway)

HOODED CLOAK:
- 50% chance to avoid recognition
- Works at night or in rain/fog
- Guards more suspicious (ask to remove hood)
```

**Stealth Mechanics:**
```
Nighttime Crimes:
- -50% witness chance (fewer NPCs)
- Guards have torches (30 block vision instead of 50)
- Can sneak past patrols (crouch = silent)

Environmental Cover:
- Crowds: Blend in (stand still = undetectable)
- Alleys: Hide in shadows (Witcher Senses shows safe spots)
- Rooftops: Escape route (guards can't climb)
- Sewers: Underground network (no guards, but monsters)
```

**Escape Tactics:**
```
If caught committing crime:

1. RUN:
   - Sprint to city limits
   - Wanted level fades if escape for 5 min
   - Works for 1-2★ only

2. BRIBE:
   - Pay guard (2x bounty amount)
   - Only works if no violence
   - Max 3★ wanted level

3. INTIMIDATE:
   - Skill check (based on Fame/Level)
   - 20% success rate
   - Failure = guards attack

4. SURRENDER:
   - Serve jail time (see below)
   - Pay fine (bounty amount)
   - Confiscate stolen goods

5. FIGHT:
   - Kill all witnesses (escalates bounty)
   - Flee after combat
   - High risk, high wanted level
```

---

## 🔒 Imprisonment System

### **Jail Time**

**Arrest Process:**
```
If you surrender or get defeated:

1. CONFISCATION:
   - Stolen goods removed
   - Illegal items (contraband) destroyed
   - Weapons stored (returned after)

2. SENTENCING:
   - Minor crimes: 1 day jail (24 min real-time)
   - Moderate crimes: 3 days (72 min)
   - Major crimes: 7 days (168 min)
   - Can pay fine to skip (bounty × 2)

3. JAIL CELL:
   - Locked in cell (3x3 room)
   - Can sleep to skip time
   - Can attempt escape (see below)

4. RELEASE:
   - Bounty cleared
   - Wanted level reset
   - Faction reputation restored (50% of loss)
```

**Prison Break:**
```
Escaping jail:

LOCKPICK (Requires lockpick in inventory):
- 30% success chance
- Failure = +3 days sentence
- Guards patrol every 2 min (must time it)

BRIBE GUARD:
- Pay 3x bounty amount
- Guard unlocks cell at night
- Must sneak out (if caught, +7 days)

BREAKOUT (Combat):
- Attack guard when they open door
- Fight through prison (10+ guards)
- Success = escape but 5★ wanted level
- Failure = +14 days sentence

RESCUED:
- Friends can break you out (multiplayer)
- NPC allies (high relationship) may try
- Siege prison (requires army, endgame)
```

---

## 💰 Clearing Bounties

### **Methods to Remove Bounty**

**1. Pay Fine:**
```
Go to any guard/courthouse:
- Pay exact bounty amount
- Wanted level cleared
- Faction reputation: Lose 50% of crime penalty
- Instant resolution

Cost Example:
- 1000 gold bounty = 1000 gold fine
- No negotiation (fixed price)
```

**2. Serve Time:**
```
Surrender to guards:
- Serve jail sentence (time-based)
- No gold cost
- Wanted level cleared
- Faction reputation: Lose 25% of crime penalty

Time Example:
- 1000 gold bounty = 3 days jail (72 min)
- Can sleep through it
```

**3. Bribery (Corrupt Officials):**
```
Find corrupt guard/official:
- Pay 1.5x bounty amount
- Bounty cleared quietly (no record)
- Faction reputation: No loss
- Only available in certain cities (Novigrad, Oxenfurt)

How to Find:
- Ask in taverns (50 gold for info)
- Look for "Shadowy Figure" NPC
- Requires 500+ Crime Fame (career criminal)
```

**4. Faction Pardon:**
```
Reach "Honored" reputation with faction:
- Request pardon from faction leader
- Bounty cleared (one-time use)
- Costs 0 gold but uses reputation favor
- Can only pardon up to 5000 gold bounty

Requirements:
- Honored+ reputation
- Complete 5+ faction quests
- No crimes against that faction for 7 days
```

**5. Fleeing to Enemy Territory:**
```
Escape to rival faction:
- Bounty only applies in original faction
- Example: Temeria bounty, flee to Nilfgaard
- Nilfgaard doesn't care (different jurisdiction)
- Can't return to Temeria until bounty cleared

Trade-off:
- Safe from original faction
- But now in enemy territory (different risks)
```

**6. Time Decay:**
```
Bounties decay over time if evade capture:
- Minor crimes: 7 days (clear automatically)
- Moderate crimes: 30 days
- Major crimes: 90 days
- Regicide: Never (permanent until cleared)

Decay Rate: -10 gold/day
```

---

## 🏴‍☠️ Criminal Reputation

### **Crime Fame System**
Separate from regular fame - tracks your notoriety as a criminal.

**Crime Fame Tiers:**
```
0-99: CLEAN RECORD
- No criminal history
- Normal NPC interactions

100-499: PETTY CROOK
- Guards more suspicious
- +10% bounty on all crimes

500-1499: CAREER CRIMINAL
- Black market access unlocked
- Can hire thugs as backup
- +25% bounty on all crimes

1500-4999: NOTORIOUS OUTLAW
- Bounty hunters track you passively
- Cannot enter some cities
- Criminal guilds offer quests
- +50% bounty on all crimes

5000+: LEGEND OF CRIME
- Entire Continent knows your name
- "Most Wanted" poster everywhere
- Criminal underground respects you
- Can form bandit gang (recruit NPCs)
- +100% bounty on all crimes
```

**Crime Fame Gains:**
```
Actions that increase Crime Fame:

- Theft: +1 per 10 gold stolen
- Murder: +50 per NPC
- Escape capture: +100
- Defeat bounty hunter: +25
- Prison break: +200
- Mass murder (10+ NPCs): +500
```

**Benefits of Crime Fame:**
```
Black Market Access:
- Unlock at 500 Crime Fame
- Sell stolen goods (no questions asked)
- Buy illegal items (poisons, lock picks)
- Hire mercenaries for crimes

Criminal Quests:
- Heists (rob nobles)
- Assassinations (kill targets)
- Smuggling runs (contraband)
- Gang wars (fight rival criminals)

Criminal Perks:
- Faster lockpicking (1500+ Crime Fame)
- Stealth bonus (+20% sneak, 3000+ Fame)
- Intimidation always works (5000+ Fame)
```

---

## 🎭 Witness Mechanics

### **NPC Witness Behavior**

**Witness Actions:**
```
When NPC sees crime:

1. ALERT (50% chance):
   - "Guards! Help!"
   - Run to nearest guard
   - Report your description
   - Guards search area

2. FLEE (30% chance):
   - Run away screaming
   - Hide indoors
   - Don't report (too scared)

3. FIGHT (10% chance):
   - Brave NPCs attack
   - Low damage (5-10)
   - Delay you until guards arrive

4. IGNORE (10% chance):
   - Corrupt/apathetic NPCs
   - Turn blind eye
   - No report filed
```

**Silencing Witnesses:**
```
Options to prevent reporting:

1. INTIMIDATE:
   - Threaten NPC (no violence)
   - 60% success rate
   - Failure = they scream louder

2. BRIBE:
   - Pay 50 gold
   - 80% success rate
   - They forget what they saw

3. KILL:
   - Silence permanently
   - BUT adds murder charge
   - Higher bounty than original crime

4. AXII SIGN:
   - Mind control (confuse witness)
   - 90% success rate
   - Lasts 60 seconds (report delayed)
```

---

## 🏆 Achievements

**Crime Achievements:**
```
🚔 First Offense - Commit your first crime
🚔 Repeat Offender - Accumulate 1000 gold in bounties
🚔 Public Enemy - Reach 5★ wanted level
🚔 Smooth Criminal - Escape 10 bounty hunters
🚔 Prison Break - Successfully escape from jail
🚔 Most Wanted - Have 10,000 gold bounty
🚔 Criminal Mastermind - Reach 5000 Crime Fame

💰 Master Thief - Steal 10,000 gold worth of items
💰 Cat Burglar - Break into 50 locked buildings
💰 Grand Heist - Steal item worth 1000+ gold

😈 Murderer - Kill 10 innocent NPCs
😈 Mass Murderer - Kill 50 innocent NPCs
😈 Genocide - Kill 200 innocent NPCs

🎭 Clean Hands - Clear 1000+ bounty without jail time
🎭 Pardoned - Receive faction pardon
🎭 Redemption - Reduce Crime Fame from 1000 to 0
```

---

## ⚙️ Configuration

```json5
// config/witchercraft/bounty_crime.json
{
  "crime_system": {
    "enabled": true,
    "wanted_levels": true,
    "max_wanted_level": 5,
    "bounty_decay": true,
    "decay_rate_gold_per_day": -10
  },
  
  "law_enforcement": {
    "guards_enabled": true,
    "guard_detection_range": 30,
    "civilian_witnesses": true,
    "witness_report_chance": 0.50,
    "guard_combat": true
  },
  
  "bounty_hunters": {
    "enabled": true,
    "spawn_delay_minutes": 5,
    "legendary_hunters": true,
    "hunter_respawn": true,
    "hunter_tracking": true
  },
  
  "imprisonment": {
    "jail_system": true,
    "can_escape": true,
    "lockpick_escape_chance": 0.30,
    "bribe_escape_multiplier": 3.0,
    "combat_escape": true
  },
  
  "stealth": {
    "disguises": true,
    "nighttime_bonus": true,
    "crowd_blending": true,
    "environmental_cover": true
  },
  
  "crime_fame": {
    "enabled": true,
    "black_market_unlocks": true,
    "criminal_quests": true,
    "criminal_perks": true
  }
}
```

---

## 🎯 Integration with Other Systems

| System | Integration |
|--------|-------------|
| **Faction Reputation** | Crimes reduce faction rep, pardons require high rep |
| **Economy** | Stolen goods, fines, bribes, black market trading |
| **Fame** | Infamy increases with Crime Fame, separate from heroic fame |
| **Combat** | Guard and bounty hunter fights, prison breaks |
| **Quest System** | Criminal quests unlock, some quests require clean record |
| **Settlement** | Can't enter cities with high wanted level, safe havens |

---

## 🛠️ Implementation Phases

### **Phase 1: Core Crime System (4-5 weeks)**
- [ ] Crime detection (witness AI, guard vision)
- [ ] Bounty system (track per faction)
- [ ] Wanted levels (1-5 stars)
- [ ] Crime categories (minor, moderate, major)

### **Phase 2: Law Enforcement (5-6 weeks)**
- [ ] Guard AI (patrol, search, combat)
- [ ] Guard tiers (regular, captain, elite, royal)
- [ ] Witness mechanics (report, flee, fight)
- [ ] Crime scene investigation

### **Phase 3: Bounty Hunters (5-6 weeks)**
- [ ] Bounty hunter spawning
- [ ] Hunter AI (tracking, ambush, combat)
- [ ] Hunter tiers (novice, experienced, legendary)
- [ ] Legendary named hunters (boss fights)

### **Phase 4: Stealth & Escape (4-5 weeks)**
- [ ] Disguise system (faction armor, cloaks)
- [ ] Stealth mechanics (nighttime, crowds, cover)
- [ ] Escape tactics (intimidate, bribe, Axii)
- [ ] Wanted level decay

### **Phase 5: Imprisonment (3-4 weeks)**
- [ ] Jail system (arrest, sentencing, time served)
- [ ] Prison break mechanics (lockpick, bribe, combat)
- [ ] Confiscation (stolen goods, illegal items)
- [ ] Release and bounty clearing

### **Phase 6: Criminal Content (4-5 weeks)**
- [ ] Crime Fame system
- [ ] Black market access
- [ ] Criminal quests (heists, assassinations)
- [ ] Criminal perks and unlocks

**Total Estimated Time:** 25-31 weeks (6-8 months)

---

**Status:** 📋 Design Phase  
**Priority:** 🟡 Medium-High (Adds consequences to actions)  
**Dependencies:** Faction Reputation, Economy, Combat System  
**Related Files:** POLITICAL_SYSTEM.md, ECONOMY_TRADE_SYSTEM.md, FAME_REPUTATION_SYSTEM.md
