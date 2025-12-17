# WitcherCraft - Time & Calendar System

## Overview
A comprehensive time management system featuring a Witcher-inspired calendar, seasonal cycles, day/night mechanics, lunar phases, cultural festivals, and time-based world events that affect gameplay and immersion.

---

## 📅 The Witcher Calendar

### **Calendar Structure**

**12 Months** (30 days each = 360 day year):

| # | Month Name | Season | Real-World Equivalent | Weather |
|---|------------|--------|----------------------|---------|
| 1 | **Velen** | Winter | January | Snow, blizzards, harsh cold |
| 2 | **Gostom** | Winter | February | Ice storms, freezing rain |
| 3 | **Aedd Gynvael** | Spring | March | Thawing, rain, mud |
| 4 | **Birke** | Spring | April | Mild, flowers bloom |
| 5 | **Saovine Eve** | Spring | May | Pleasant, clear skies |
| 6 | **Sieden** | Summer | June | Warm, thunderstorms |
| 7 | **Lammas** | Summer | July | Hot, dry, sunny |
| 8 | **Equinox** | Summer | August | Harvest season begins |
| 9 | **Feainn** | Autumn | September | Cooling, winds |
| 10 | **Midinváerne** | Autumn | October | Rain, fog |
| 11 | **Belleteyn** | Autumn | November | Cold rain, overcast |
| 12 | **Saovine** | Winter | December | First snows, holidays |

**Starting Date:** Player spawns on **1st of Birke** (spring, Year 1272 - after Witcher 3 timeline)

**Time Display:**
```
HUD Element (top-right corner):
┌─────────────────────────┐
│ 15th of Lammas, 1272    │
│ 14:32 (Afternoon)       │
│ 🌤️ Clear, Warm          │
│ 🌕 Full Moon (tonight)   │
└─────────────────────────┘
```

---

## ⏰ Day/Night Cycle

### **24-Hour Cycle** (Real-time conversion)

| Time Period | Game Time | Real-Time Duration | Sky | Effects |
|-------------|-----------|-------------------|-----|---------|
| **Dawn** | 05:00-07:00 | 2 minutes | Orange sunrise | Monsters flee, NPCs wake |
| **Morning** | 07:00-12:00 | 5 minutes | Bright, clear | Shops open, guards patrol |
| **Afternoon** | 12:00-17:00 | 5 minutes | Bright, warm | Peak activity, full services |
| **Dusk** | 17:00-19:00 | 2 minutes | Red/purple sunset | NPCs head home, monsters spawn |
| **Night** | 19:00-23:00 | 4 minutes | Dark, starry | Increased monster spawns, crime |
| **Midnight** | 23:00-05:00 | 6 minutes | Very dark, moon | Peak monster activity, wraiths |

**Total Real-Time:** 24 minutes = 1 Minecraft day (24 hours in-game)

**Time Acceleration:**
- Default: 24 min per day
- Config option: 12 min (fast), 48 min (slow), 1 hour (realistic)
- Meditation: Skip ahead instantly (see Meditation System)

---

## 🌙 Lunar Phases

### **Moon Cycle** (28 days)

| Phase | Days | Visual | Monster Effects | Magic Effects |
|-------|------|--------|----------------|---------------|
| 🌑 **New Moon** | 1-3 | Invisible moon | Wraiths stronger (+20% damage) | Witcher Senses range +50% |
| 🌒 **Waxing Crescent** | 4-9 | Thin crescent | Standard spawns | Standard |
| 🌓 **First Quarter** | 10-12 | Half moon | Standard spawns | Standard |
| 🌔 **Waxing Gibbous** | 13-17 | Nearly full | Werewolves appear (rare) | Sign intensity +10% |
| 🌕 **Full Moon** | 18-20 | Full bright moon | Werewolves +300%, vampires +50% | Sign intensity +20% |
| 🌖 **Waning Gibbous** | 21-24 | Shrinking | Werewolves decrease | Sign intensity +10% |
| 🌗 **Last Quarter** | 25-27 | Half moon | Standard spawns | Standard |
| 🌘 **Waning Crescent** | 28 | Thin sliver | Wraiths begin strengthening | Witcher Senses +25% |

**Full Moon Events:**
- Werewolf transformation: Friendly NPCs may become hostile (curse mechanic)
- "Hunter's Moon" quest chain unlocks
- Vampire nests become more active
- Night is 20% brighter (easier to see, but more dangerous)

**New Moon Events:**
- "The Darkest Night" - Increased wraith spawns (200%)
- Witcher Senses highlight range doubled
- Stealth easier (+30% detection radius reduction)
- Some quests only available during new moon (dark rituals, stealth missions)

---

## 🌦️ Seasonal Effects

### **Season-Based Changes**

#### **Winter** (Velen, Gostom, Saovine)
**Environment:**
- Snow accumulation on ground (1-3 block layer)
- Ice forms on water surfaces
- Visibility reduced in blizzards
- Trees lose leaves (deciduous)

**Gameplay:**
- Cold debuff: -10% movement speed without fur armor
- Food consumption +50% (body heat)
- Certain herbs unavailable (winter variants only)
- Monster behavior: Bears hibernate, ice trolls more active

**Economy:**
- Fur prices increase 100%
- Food prices increase 50% (scarcity)
- Firewood becomes valuable trade good

---

#### **Spring** (Aedd Gynvael, Birke, Saovine Eve)
**Environment:**
- Flowers bloom (decorative flora spawns)
- Rain more frequent (30% of days)
- Rivers swell (water level +1 block)
- Mud on roads (slows travel 10%)

**Gameplay:**
- Herb respawn rate +50%
- New plant types available
- Monster mating season (nests spawn more frequently)
- Diseases spread easier (sickness debuff chance +20%)

**Economy:**
- Herb prices drop 30% (abundance)
- Agricultural goods become available
- Trade routes resume after winter

---

#### **Summer** (Sieden, Lammas, Equinox)
**Environment:**
- Sunny skies (70% clear weather)
- Occasional thunderstorms (dramatic, dangerous)
- Heat waves (shimmer effect)
- Crops at full growth

**Gameplay:**
- Heat debuff: -5 max health without light armor
- Monsters seek shade (caves, forests denser spawns)
- Drowners active near water (drought drives them to rivers)
- Fire spreads faster (+50% ignition rate from Igni)

**Economy:**
- Food abundant (prices drop 40%)
- Harvest festivals (special markets)
- Travel easier (caravan frequency +50%)

---

#### **Autumn** (Feainn, Midinváerne, Belleteyn)
**Environment:**
- Leaves turn orange/red/brown
- Fog in mornings (visibility -30%)
- Frequent rain (40% of days)
- Pumpkins grow in fields

**Gameplay:**
- Foglets spawn in fog (obviously)
- Necrophages drawn to harvest (corpse piles from slaughter)
- Storms more violent (lightning strikes)
- Preparation season (NPCs stockpile for winter)

**Economy:**
- Harvest goods flood market (grain, vegetables cheap)
- Monster contracts spike (clearing fields)
- Merchants buy supplies (preparing for winter scarcity)

---

## 🎉 Cultural Festivals & Events

### **Major Festivals** (Calendar-Based)

#### **1. Belleteyn** (1st of Belleteyn - May 1st equivalent)
**Theme:** Spring fertility festival, celebrating life and renewal

**Activities:**
- Bonfires in every settlement (decorative)
- Flower wreaths sold by vendors
- Dancing mini-game (rhythm-based, rewards: +5 Charisma buff for 1 day)
- Couples pair off (NPC behavior change)
- Special quest: "The Last Wish" (romantic subplot)

**Rewards:**
- Belleteyn Wreath (cosmetic headgear, +1 Charisma)
- Festival foods (buffs: +20% health regen for 1 hour)
- Unique dialogue options with NPCs

---

#### **2. Saovine** (30th of Saovine - October 31st equivalent)
**Theme:** Night of the Dead, honoring ancestors, warding off evil

**Activities:**
- Jack-o'-lanterns placed outside homes
- NPCs wear masks (cosmetic)
- Wraith spawns triple (dangerous night)
- Graveyard vigils (quests to protect mourners)
- Fortune tellers appear (hint at future quest outcomes)

**Rewards:**
- Saovine Mask (cosmetic, +10% intimidation)
- "Spirits' Blessing" buff (+15% Spirit damage for 3 days)
- Rare spawn: Pesta (plague demon) boss fight

---

#### **3. Lammas** (15th of Lammas - August harvest)
**Theme:** Harvest celebration, abundance, thanksgiving

**Activities:**
- Harvest competitions (gather crops mini-game)
- Feast tables (free food in every settlement)
- Merchants offer 20% discounts
- Drinking contest (Constitution check, prize: 500 gold)

**Rewards:**
- Lammas Bread (permanent +5 max health consumable, once per year)
- Harvest Trophy (cosmetic display item)
- "Bountiful Year" buff (+10% loot from all sources for 7 days)

---

#### **4. Midinváerne** (20th of Midinváerne - Winter Solstice)
**Theme:** Longest night, survival, hope for spring

**Activities:**
- Storytelling around fires (lore snippets)
- Gift-giving between NPCs (player can participate)
- Monster siege event (settlements defend against horde)
- Light vigils (keep fires burning all night)

**Rewards:**
- Midinváerne Gift Box (random rare item)
- "Survivor's Resolve" buff (+20% cold resistance, 30 days)
- Hero status if settlement defended successfully

---

### **Regional Festivals** (Specific to factions)

| Region | Festival | Date | Description |
|--------|----------|------|-------------|
| **Skellige** | Long Night | 1st of Velen | 48-hour monster defense event |
| **Redania** | Kreve's Day | 10th of Sieden | Religious procession, healing buffs |
| **Temeria** | King's Parade | 5th of Birke | Royal celebration, quests from nobles |
| **Nilfgaard** | Imperial Triumph | 20th of Lammas | Military parade, recruitment bonuses |

---

## ⏳ Time-Based Mechanics

### **NPC Schedules**

NPCs follow daily routines:

```
MERCHANT (Example):
06:00 - Wake up, eat breakfast at home
07:00 - Open shop, begin trading
12:00 - Lunch break (shop closed for 1 hour)
13:00 - Resume trading
18:00 - Close shop, head to inn
19:00 - Dinner and socializing at inn
22:00 - Return home, sleep
```

**Player Benefits:**
- Burglary easier at night (NPCs sleeping)
- Information gathering at inn (evening, NPCs drunk and talkative)
- Shops closed at night (plan ahead or use black market)

---

### **Quest Time Limits**

Certain quests have deadlines:

**Types:**
1. **Urgent Contracts** - "Monster attacking village, hurry!" (2-5 days)
   - Failure: Village destroyed, quest fails, -200 reputation
   
2. **Time-Sensitive Clues** - Witcher Senses tracks fade over time
   - After 3 days: Tracks become faint, harder to follow
   - After 7 days: Tracks disappear, alternate solution needed

3. **Political Deadlines** - "Deliver message before summit"
   - Success: Bonus reward, prevent war
   - Failure: War breaks out, reputation loss

4. **Seasonal Quests** - Only available certain times of year
   - "Harvest Protection" (only Autumn)
   - "Ice Troll Negotiations" (only Winter)

---

### **Meditation System**

**How Meditation Works:**

1. **Initiate:** Find safe location (no enemies within 50 blocks), press M
2. **Choose Duration:**
   ```
   Meditate for:
   - 1 Hour
   - Until Dusk
   - Until Dawn
   - Until Next Day (24 hours)
   - Custom (slider)
   ```

3. **Effects of Meditation:**
   - **Heal:** Restore health based on food consumed (1 food = 20% health)
   - **Replenish:** Refill potion supplies using alcohol
   - **Advance Time:** Skip ahead to desired time
   - **Skill Points:** At Place of Power, gain skill point (once per location)
   - **Mental Clarity:** Remove debuffs (poison, curse, etc.)

4. **Requirements:**
   - Food in inventory (to heal)
   - Alcohol (to replenish potions)
   - Safe area (meditation interrupted by combat)

**Meditation GUI:**
```
╔═══════════════════════════════════════╗
║          MEDITATION                   ║
╠═══════════════════════════════════════╣
║ Current Time: 14:30, 5th of Birke    ║
║ Health: [████░░] 80/100               ║
║ Food Available: 3 items (60% heal)    ║
║ Alcohol: 2 bottles (refill potions)  ║
║                                       ║
║ [Meditate Until:]                     ║
║  ○ Dusk (17:00) - 2.5 hours           ║
║  ○ Dawn (05:00) - 14.5 hours          ║
║  ○ Custom: [______] hours             ║
║                                       ║
║ [Refill Potions] [Heal] [Cancel]      ║
╚═══════════════════════════════════════╝
```

---

## 🌍 Time-Based World Events

### **Dynamic Events by Time**

#### **1. Midnight Hauntings** (00:00-02:00)
- Wraiths appear at battlefields and graveyards
- Ghostly NPCs re-enact historical events (environmental storytelling)
- "Witching Hour" quests unlock (exorcism, curse-breaking)

#### **2. Dawn Ambushes** (05:00-06:00)
- Bandits attack travelers at road camps
- Nocturnal monsters flee to lairs (can track to nests)
- Fog dissipates, revealing hidden paths

#### **3. Market Days** (Every 7th day, 08:00-16:00)
- Traveling merchants arrive in settlements
- Rare items available for purchase
- Prices drop 15% due to competition
- Crowds (more NPCs, harder to steal)

#### **4. Blood Moon** (Random, 1-2 per year)
- Sky turns red for entire night
- All monster spawns doubled
- Boss monsters appear (unique loot)
- Alchemy at Place of Power grants enhanced potions
- "The Conjunction" quest chain triggers (multi-stage event)

---

## ⚙️ Configuration

```json5
// config/witchercraft/time_calendar.json
{
  "time": {
    "day_length_real_minutes": 24, // 12, 24, 48, 60
    "enable_time_acceleration": false, // sleeping in beds
    "meditation_enabled": true,
    "meditation_interrupts": true // combat stops meditation
  },
  
  "calendar": {
    "starting_date": "1st of Birke, 1272",
    "days_per_month": 30,
    "months_per_year": 12,
    "display_in_hud": true,
    "show_season": true
  },
  
  "lunar_phases": {
    "enabled": true,
    "cycle_length_days": 28,
    "full_moon_effects": true,
    "werewolf_transformations": true
  },
  
  "seasons": {
    "enabled": true,
    "seasonal_weather": true,
    "seasonal_spawns": true,
    "seasonal_economy": true,
    "snow_accumulation": true,
    "crop_growth_seasonal": true
  },
  
  "festivals": {
    "enabled": true,
    "belleteyn": true,
    "saovine": true,
    "lammas": true,
    "midinvaerne": true,
    "regional_festivals": true
  },
  
  "npc_schedules": {
    "enabled": true,
    "merchants_close_at_night": true,
    "npcs_sleep": true,
    "schedule_complexity": "medium" // "simple", "medium", "complex"
  },
  
  "events": {
    "time_based_events": true,
    "blood_moon": true,
    "midnight_hauntings": true,
    "market_days": true,
    "quest_time_limits": true
  }
}
```

---

## 🎯 Integration with Other Systems

| System | Integration |
|--------|-------------|
| **World Generation** | Seasonal terrain changes, weather patterns by region |
| **Monster System** | Lunar phases affect spawns, seasonal behavior changes |
| **Quest System** | Time limits, seasonal quests, festival events |
| **Economy** | Seasonal price fluctuations, festival markets, scarcity events |
| **Alchemy** | Herb availability by season, lunar potency bonuses |
| **Political System** | Festivals by faction, seasonal wars (campaigning season) |
| **NPC Behavior** | Daily schedules, festival participation, sleep cycles |

---

## 🛠️ Implementation Phases

### **Phase 1: Core Time System (2-3 weeks)**
- [ ] Calendar data structure (month, day, year tracking)
- [ ] Day/night cycle (24-hour clock)
- [ ] HUD time display
- [ ] Basic season tracking

### **Phase 2: Lunar & Seasonal (3-4 weeks)**
- [ ] Moon phase calculation and visuals
- [ ] Seasonal weather changes
- [ ] Snow/ice mechanics for winter
- [ ] Herb/crop seasonal availability

### **Phase 3: Meditation System (2-3 weeks)**
- [ ] Meditation GUI
- [ ] Time skipping mechanics
- [ ] Healing and potion refill
- [ ] Place of Power skill point integration

### **Phase 4: NPC Schedules (4-5 weeks)**
- [ ] Daily routine system for NPCs
- [ ] Shop open/close times
- [ ] NPC pathfinding to home/work/inn
- [ ] Sleep animations and mechanics

### **Phase 5: Festivals & Events (5-6 weeks)**
- [ ] Festival calendar system
- [ ] Belleteyn, Saovine, Lammas, Midinváerne content
- [ ] Festival decorations and NPCs
- [ ] Mini-games and special quests
- [ ] Time-based world events (blood moon, hauntings)

### **Phase 6: Quest Time Limits (2-3 weeks)**
- [ ] Quest deadline tracking
- [ ] Urgent contract mechanics
- [ ] Time-sensitive clue fading
- [ ] Failure states and consequences

**Total Estimated Time:** 18-24 weeks (4-6 months)

---

## 🎯 Success Criteria

✅ **Immersion**
- Time passage feels natural and noticeable
- Seasons create distinct atmospheres
- Festivals add cultural depth

✅ **Gameplay Impact**
- Time of day affects strategy (when to travel, meditate, quest)
- Seasonal changes require adaptation (gear, routes, planning)
- Lunar phases create memorable moments (full moon danger)

✅ **Player Agency**
- Meditation gives control over time
- Can plan around events (prepare for festivals, avoid full moons)
- Time limits add urgency without frustration

✅ **World Reactivity**
- NPCs follow believable routines
- Economy responds to seasons
- Events trigger at appropriate times

---

**Status:** 📋 Design Phase  
**Priority:** 🟡 High (Enhances immersion and world dynamism)  
**Dependencies:** World Generation (seasons, weather), Monster System (lunar spawns)  
**Related Files:** WORLDGEN_SYSTEM.md, QUEST_REPUTATION_SYSTEM_UPDATE.md, ECONOMY_TRADE_SYSTEM.md
