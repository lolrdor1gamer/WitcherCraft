# WitcherCraft - Reputation & Fame System

## Overview
A dual-layer reputation system separating **Faction Reputation** (political standing with organizations) from **Personal Fame** (your legend across the Continent). While faction rep determines NPC interactions within groups, fame builds your personal renown, unlocking unique opportunities, contracts, and social recognition.

---

## 🎭 Dual Reputation Model

### **Layer 1: Faction Reputation** (Political System)
Covered in POLITICAL_SYSTEM.md - This tracks your standing with organizations:
- Temeria, Nilfgaard, Skellige, etc.
- Affects prices, guards, quest availability
- Faction-specific mechanics

### **Layer 2: Personal Fame** (This System)
Separate from factions - tracks YOUR legend as an individual:
- How well-known you are across the Continent
- What you're famous (or infamous) FOR
- Social recognition, unique opportunities, bard songs

---

## ⭐ Fame Point System

### **Fame Categories**
Your fame is tracked across **6 categories**, each affecting different aspects of the world:

```
╔═══════════════════════════════════════╗
║ FAME CATEGORIES                       ║
╠═══════════════════════════════════════╣
║                                       ║
║ 🗡️  MONSTER SLAYER FAME               ║
║    How feared you are by monsters     ║
║    Range: 0-10,000 points             ║
║                                       ║
║ ⚔️  WARRIOR FAME                      ║
║    Reputation as a combatant          ║
║    Range: 0-10,000 points             ║
║                                       ║
║ 📜 SCHOLAR FAME                       ║
║    Known for knowledge and wisdom     ║
║    Range: 0-10,000 points             ║
║                                       ║
║ 💰 WEALTH FAME                        ║
║    Reputation for riches and success  ║
║    Range: 0-10,000 points             ║
║                                       ║
║ 🎭 HEROIC FAME                        ║
║    Good deeds and heroism             ║
║    Range: -10,000 to +10,000          ║
║    (can be negative = infamy)         ║
║                                       ║
║ 🌍 CONTINENTAL FAME (Overall)         ║
║    Sum of all categories              ║
║    Range: 0-50,000+ points            ║
╚═══════════════════════════════════════╝
```

---

### **Gaining Fame**

**Monster Slayer Fame:**
```
Actions that grant fame:

- Kill common monster: +1 point
- Kill rare monster: +5 points
- Kill legendary monster: +50 points
- Kill boss monster (Kraken, etc.): +200 points
- Complete monster contract: +10 points
- Complete legendary contract: +100 points
- Display monster trophy: +1 point/day (passive)

Special Bonuses:
- First to kill rare monster type: +50 bonus
- Kill 100 of same monster type: +100 bonus
- Kill monster while witnessed by 5+ NPCs: 2x fame
```

**Warrior Fame:**
```
Actions that grant fame:

- Win duel: +10 points
- Win tournament: +100 points
- Kill bandit: +2 points
- Clear bandit camp: +25 points
- Win 10 consecutive fights: +50 points
- Defeat legendary warrior (NPC): +200 points
- Win arena championship: +500 points

Special Bonuses:
- Win fight without taking damage: 2x fame
- Win fight with only fists: 3x fame
- Defeat opponent 10+ levels higher: 5x fame
```

**Scholar Fame:**
```
Actions that grant fame:

- Discover new location: +5 points
- Complete bestiary entry: +10 points
- Translate ancient text: +25 points
- Solve puzzle: +15 points
- Discover recipe through experimentation: +30 points
- Write lore entry (Codex): +20 points
- Find legendary book: +100 points

Special Bonuses:
- Complete full bestiary (all monsters): +500 bonus
- Discover all POIs in region: +200 bonus
- Translate Elder Speech scroll: +50 bonus
```

**Wealth Fame:**
```
Actions that grant fame:

- Earn 10,000 gold: +10 points
- Own settlement: +500 points
- Complete high-value trade: +25 points
- Invest in business: +50 points
- Own Tier 4 Workshop: +300 points
- Hoard 100,000 gold: +1000 points

Special Bonuses:
- Display wealth (gold blocks in settlement): +1 point/day
- Donate to charity (1000+ gold): +50 fame
- Buy round at tavern (50+ gold): +5 fame
```

**Heroic Fame (Good Deeds):**
```
Actions that grant fame:

POSITIVE (Hero):
- Complete quest for free: +25 points
- Save NPC from monster: +50 points
- Donate to beggar: +5 points
- Free prisoner: +30 points
- Resolve conflict peacefully: +40 points
- Rebuild destroyed village: +500 points

NEGATIVE (Villain):
- Kill innocent NPC: -100 points
- Steal from NPC: -25 points
- Destroy property: -50 points
- Betray quest giver: -200 points
- Massacre village: -1000 points

Consequences:
Positive (Hero): NPCs give discounts, free lodging, quests
Negative (Villain): Bounties, guards hostile, higher prices
```

---

## 🏆 Fame Tiers

### **Continental Fame Levels**
```
UNKNOWN (0-99):
- No one knows who you are
- Normal NPC interactions
- No special dialogue

LOCAL HERO (100-499):
- Known in current region
- "I've heard of you!" dialogue
- +5% quest rewards in region

RISING STAR (500-1499):
- Known across 2-3 regions
- NPCs mention your deeds
- +10% quest rewards
- Occasional fan encounters

RENOWNED (1500-4999):
- Famous across Northern Kingdoms
- Guards recognize you (salute)
- Merchants give 10% discount
- +20% quest rewards
- Bards sing songs about you (see below)

LEGENDARY (5000-14999):
- Continental-wide recognition
- Special dialogue everywhere
- +30% quest rewards
- Free lodging at inns
- NPCs ask for autographs
- Children follow you in streets
- Unique contracts only for you

LIVING LEGEND (15000-29999):
- One of the most famous people alive
- Royalty requests audience
- +50% quest rewards
- Free services (merchants, blacksmiths)
- Personal bodyguards offer services
- Statues erected in your honor (settlements)
- Legendary contracts unlock

MYTHICAL (30000+):
- Transcended into myth and legend
- NPCs don't believe you're real
- +100% quest rewards
- Everything free (out of respect/awe)
- Special "Continental Legend" title
- Your deeds taught in schools
- Unique dialogue with EVERY NPC
- World events reference you
```

---

## 📣 Fame Consequences

### **Social Recognition**

**NPC Dialogue Changes:**
```
LOW FAME (0-499):
- "Who are you?"
- "What do you want?"
- Generic greetings

MEDIUM FAME (500-4999):
- "You're that Witcher, right?"
- "I've heard stories about you!"
- "Is it true you killed the [monster]?"

HIGH FAME (5000-14999):
- "I can't believe it's really you!"
- "Wait until I tell my friends I met [you]!"
- "Can I have your autograph?"

LEGENDARY FAME (15000+):
- "By the gods, it's [Player Name]!"
- "I thought you were just a legend!"
- "My grandchildren won't believe this!"
- *NPCs bow, kneel, or salute*
```

**Crowd Reactions:**
```
RENOWNED+ (1500+ Fame):
- NPCs gather when you enter towns
- Children run alongside you
- Guards stand at attention
- Merchants call out special offers

LEGENDARY+ (5000+ Fame):
- Crowds part for you (clear path)
- NPCs take screenshots (in-universe: "artists sketch you")
- Bards follow you around (play your theme)
- Parades organized in your honor (festivals)
```

---

### **Bard Songs & Legends**

When you reach **RENOWNED (1500+ Fame)**, bards begin singing songs about your exploits.

**Song Generation:**
```
Songs are dynamically created based on your achievements:

Example Song: "The Kraken Slayer's Ballad"
(If you killed a Kraken)

"Oh gather 'round and hear the tale,
Of [Player Name] who did not fail!
The Kraken rose from depths below,
But our hero struck the final blow!

With sword in hand and courage true,
Through storm and wave, the hero flew!
The beast did fall, the sea turned red,
Now tales of [Player Name] are widely spread!"

NPCs react:
- "Have you heard the song about you?"
- Taverns play your song on loop
- Bards request payment (50 gold) to stop playing it
```

**Song Types by Category:**
```
Monster Slayer Fame → "Ballad of the Beast Hunter"
Warrior Fame → "The Champion's March"
Scholar Fame → "Ode to the Wise One"
Wealth Fame → "The Golden Legend"
Heroic Fame (Positive) → "Song of the Savior"
Heroic Fame (Negative) → "The Villain's Dirge" (ominous)
```

**Bard Interactions:**
```
You can:
- Request custom song (500 gold, promote specific deed)
- Pay to suppress song (1000 gold, remove embarrassing tale)
- Commission epic (5000 gold, legendary song played at festivals)
- Threaten bard (Intimidation check, stops playing, -50 Heroic Fame)
```

---

### **Unique Opportunities**

**Contracts & Quests:**
```
Fame-Locked Content:

1000+ Fame:
- "The Silver Contract" (rare monster)
- "Duel the Champion" (warrior quest)

3000+ Fame:
- "Royal Summons" (king requests audience)
- "The Legendary Beast" (unique boss)

5000+ Fame:
- "Continental Tournament" (arena)
- "Heir to Rivia" (land ownership)

10000+ Fame:
- "Slay the Dragon" (dragon boss fight)
- "The Elder's Request" (Elder Blood quest)

20000+ Fame:
- "Defend the Realm" (army battle)
- "Forge of the Gods" (legendary crafting)
```

**Social Perks:**
```
RENOWNED (1500+):
- Free lodging at any inn
- 10% merchant discount (all vendors)
- Guards won't fine you for minor crimes
- NPCs offer free gifts (food, arrows)

LEGENDARY (5000+):
- Free fast travel (merchants pay your way)
- Personal merchant (follows you, mobile shop)
- VIP access (restricted areas open)
- NPCs share rumors (intel on quests)

LIVING LEGEND (15000+):
- Personal bodyguards (hire for free)
- Settlement gifted to you (endgame)
- Custom armor crafted (master smiths offer)
- Diplomatic immunity (no faction penalties)

MYTHICAL (30000+):
- Everything free (NPCs honored to serve)
- Monsters flee from you (intimidation aura)
- World events named after you
- Permanent place in history (lore entries)
```

---

## 🎖️ Titles & Honorifics

As fame increases, you earn titles displayed in your name:

```
╔═══════════════════════════════════════╗
║ TITLE PROGRESSION                     ║
╠═══════════════════════════════════════╣
║                                       ║
║ FAME 0-99:                            ║
║ → [Player Name] (no title)            ║
║                                       ║
║ FAME 100-499:                         ║
║ → [Player Name] the Wanderer          ║
║                                       ║
║ FAME 500-1499:                        ║
║ → [Player Name] the Known             ║
║                                       ║
║ FAME 1500-4999:                       ║
║ → [Player Name] the Renowned          ║
║                                       ║
║ FAME 5000-14999:                      ║
║ → [Player Name] the Legendary         ║
║                                       ║
║ FAME 15000-29999:                     ║
║ → [Player Name] the Living Legend     ║
║                                       ║
║ FAME 30000+:                          ║
║ → [Player Name] of Continental Renown ║
║                                       ║
╚═══════════════════════════════════════╝
```

**Category-Specific Titles:**
```
Monster Slayer Fame 5000+:
- "Monster Hunter of the North"
- "Scourge of Beasts"
- "The Beast Slayer"

Warrior Fame 5000+:
- "Champion of the Arena"
- "Undefeated Swordsman"
- "Master of Arms"

Scholar Fame 5000+:
- "Sage of the Continent"
- "The Learned One"
- "Master of Knowledge"

Wealth Fame 5000+:
- "The Wealthy"
- "Lord/Lady of Coin"
- "Master of Trade"

Heroic Fame +5000:
- "The Savior"
- "Protector of the Realm"
- "Champion of the People"

Heroic Fame -5000:
- "The Villainous"
- "Terror of the North"
- "The Dreaded"
```

**Title Display:**
```
NPCs address you by title:
- "Good day, Monster Hunter of the North!"
- "It's an honor, Champion of the Arena!"
- "Greetings, Sage of the Continent!"

Multiplayer:
- Title shows above your head (nameplate)
- Color-coded by tier (Renowned = Blue, Legendary = Purple, Mythical = Gold)
```

---

## 📊 Fame Decay & Maintenance

### **Passive Fame Decay**
```
Fame naturally decays over time if inactive:

Rate: -1 fame per in-game day (24 min real-time) per category
Minimum: Decays to 50% of peak fame (won't go to 0)

Example:
- Reach 5000 Monster Slayer Fame
- Stop killing monsters
- After 2500 days (41.6 hours real-time), decays to 2500
- Stops decaying at 2500 (50% of peak)

Reasoning: Legends fade if you stop making new ones.
```

**Preventing Decay:**
```
Stay Active:
- Kill monsters → maintains Monster Slayer Fame
- Win fights → maintains Warrior Fame
- Discover lore → maintains Scholar Fame
- Earn gold → maintains Wealth Fame
- Do good deeds → maintains Heroic Fame

Passive Maintenance:
- Display trophies in settlement (prevents decay)
- Commission bard songs (slows decay by 50%)
- Build statues (halts decay in that category)
```

---

## 🗿 Fame Monuments

### **Personal Monuments**
At **LEGENDARY (5000+) Fame**, you can commission monuments in settlements:

**Monument Types:**
```
STATUE (10,000 gold):
- Bronze statue of you in town square
- +100 fame/week (passive)
- NPCs comment on it
- Tourists visit (economy boost)

HALL OF FAME (50,000 gold):
- Building dedicated to your achievements
- Displays trophies, weapons, armor
- +500 fame/week (passive)
- Museum visitors (250 gold/week income)

LEGEND'S TOMB (100,000 gold):
- Grand mausoleum (for when you "die")
- +1000 fame/week (passive)
- Pilgrimage site (NPCs visit, pray)
- Legendary loot spawns inside (endgame)
```

---

## 🌟 Infamy System (Negative Fame)

### **How Infamy Works**
If Heroic Fame goes negative, you become **INFAMOUS** (feared, not respected):

```
INFAMOUS TIERS:

MINOR TROUBLEMAKER (-100 to -499):
- Guards watch you closely
- +10% prices
- Some NPCs refuse service

OUTLAW (-500 to -1499):
- Bounty hunters spawn
- Guards attack on sight in some regions
- +30% prices
- Villagers flee from you

VILLAIN (-1500 to -4999):
- Major bounty (5000+ gold on your head)
- Factions declare you enemy
- Merchants refuse service
- Children cry when you approach

TERROR OF THE NORTH (-5000 to -9999):
- Entire armies hunt you
- Settlements evacuate when you arrive
- Impossible to buy/sell (outlawed)
- NPCs attack on sight

CONTINENTAL SCOURGE (-10000+):
- Every faction hostile
- Legendary bounty hunters (boss-level)
- Cannot enter cities (barred gates)
- Only outlaws and criminals tolerate you
- Unique "villain" contracts (black market)
```

**Infamy Benefits:**
```
Yes, there are benefits to being evil:

- Lower-level NPCs flee (no combat)
- Black market gives massive discounts
- Bandits offer to join you
- Intimidation always succeeds
- Villain-only quests unlock
- Can extort NPCs for gold
- "Fear Aura" passive (enemies hesitate)

But:
- Cannot access 90% of content
- Extremely difficult to travel
- Constant combat with authorities
- No faction support
```

**Redemption:**
```
To remove infamy:
- Pay massive fine (10x bounty amount)
- Complete redemption quests (10+ good deeds)
- Donate to charity (50,000+ gold)
- Slay legendary monster (prove worth)
- Time served (jail time reduces infamy)

Redemption is HARD but possible.
```

---

## 🏆 Achievements

**Fame Achievements:**
```
⭐ First Steps - Reach 100 Continental Fame
⭐ Rising Star - Reach 500 Continental Fame
⭐ Renowned - Reach 1500 Continental Fame
⭐ Legendary - Reach 5000 Continental Fame
⭐ Living Legend - Reach 15000 Continental Fame
⭐ Mythical Status - Reach 30000 Continental Fame

🗡️ Monster Hunter - Reach 1000 Monster Slayer Fame
⚔️ Arena Champion - Reach 1000 Warrior Fame
📜 Sage - Reach 1000 Scholar Fame
💰 Tycoon - Reach 1000 Wealth Fame
🎭 Hero of the Realm - Reach 1000 Heroic Fame

😈 Infamous - Reach -1000 Heroic Fame
😈 Continental Terror - Reach -5000 Heroic Fame

🎵 Bard's Muse - Have a song written about you
🗿 Monument Erected - Build a statue
📖 History Books - Appear in 10 lore entries
```

---

## ⚙️ Configuration

```json5
// config/witchercraft/fame_system.json
{
  "fame": {
    "enabled": true,
    "separate_from_faction_rep": true,
    "fame_multiplier": 1.0,
    "max_fame_per_category": 10000
  },
  
  "categories": {
    "monster_slayer": true,
    "warrior": true,
    "scholar": true,
    "wealth": true,
    "heroic": true // can go negative
  },
  
  "decay": {
    "enabled": true,
    "decay_rate_per_day": -1,
    "minimum_decay_percent": 0.50, // decays to 50% of peak
    "monuments_prevent_decay": true
  },
  
  "social": {
    "npc_dialogue_changes": true,
    "crowd_reactions": true,
    "bard_songs": true,
    "autograph_requests": true,
    "titles_enabled": true
  },
  
  "infamy": {
    "enabled": true,
    "bounty_hunters": true,
    "villain_quests": true,
    "redemption_allowed": true,
    "redemption_difficulty": "hard" // easy/normal/hard/impossible
  },
  
  "monuments": {
    "enabled": true,
    "statue_cost": 10000,
    "hall_of_fame_cost": 50000,
    "tomb_cost": 100000,
    "passive_fame_gain": true
  }
}
```

---

## 🎯 Integration with Other Systems

| System | Integration |
|--------|-------------|
| **Faction Reputation** | Separate but parallel - can be Temerian hero but Nilfgaard villain |
| **Quest System** | Fame unlocks unique contracts, NPCs react based on fame level |
| **Economy** | High fame = discounts, free services; infamy = higher prices, refusal |
| **Combat** | Fame affects NPC behavior (flee, surrender, call for help) |
| **World Events** | Famous players trigger special events (parades, tournaments) |
| **Settlement** | Monuments require owned settlement, boost tourism income |

---

## 🛠️ Implementation Phases

### **Phase 1: Core Fame System (4-5 weeks)**
- [ ] Fame category tracking (6 categories)
- [ ] Fame point gain from actions
- [ ] Continental fame calculation
- [ ] Fame UI (display current fame, tiers)

### **Phase 2: Social Recognition (4-5 weeks)**
- [ ] NPC dialogue changes by fame tier
- [ ] Crowd reactions (gather, bow, salute)
- [ ] Title system (10+ titles)
- [ ] Special NPC interactions

### **Phase 3: Bards & Songs (3-4 weeks)**
- [ ] Dynamic song generation
- [ ] Bard NPCs (follow player, perform)
- [ ] Song types by fame category
- [ ] Player song customization

### **Phase 4: Fame Rewards (4-5 weeks)**
- [ ] Fame-locked contracts (10+ quests)
- [ ] Merchant discounts (sliding scale)
- [ ] Free services (lodging, travel)
- [ ] Unique opportunities

### **Phase 5: Infamy System (3-4 weeks)**
- [ ] Negative heroic fame (infamy)
- [ ] Bounty hunters (spawn based on infamy)
- [ ] Villain-only content
- [ ] Redemption mechanics

### **Phase 6: Monuments & Decay (3-4 weeks)**
- [ ] Fame decay system
- [ ] Monument construction
- [ ] Passive fame gain from monuments
- [ ] Achievement integration

**Total Estimated Time:** 21-27 weeks (5-7 months)

---

**Status:** 📋 Design Phase  
**Priority:** 🟢 Medium (Enhances immersion, not critical path)  
**Dependencies:** Political System (faction rep separate), Economy, Quest System  
**Related Files:** POLITICAL_SYSTEM.md, ECONOMY_TRADE_SYSTEM.md, QUEST_REPUTATION_SYSTEM_UPDATE.md
