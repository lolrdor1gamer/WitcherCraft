# WitcherCraft - Companion & Relationship System

## Overview
A comprehensive NPC companion and relationship system featuring recruitable followers, romance options, friendship mechanics, rivalries, and dynamic character interactions. Build meaningful relationships that affect gameplay, unlock unique content, and create memorable stories.

---

## 👥 Companion System

### **Companion Types**

```
╔═══════════════════════════════════════╗
║ WITCHER COMPANION                     ║
╠═══════════════════════════════════════╣
║ Examples: Lambert, Eskel, Letho       ║
║                                       ║
║ Abilities:                            ║
║  - Combat expertise (high damage)     ║
║  - Can use Signs (Igni, Quen, Aard)   ║
║  - Monster knowledge (bonus damage)   ║
║  - Potion brewing (shares potions)    ║
║                                       ║
║ Recruitment:                          ║
║  - Complete Witcher questline         ║
║  - Prove skill in Trial (combat test) ║
║  - Requires "Trusted" relationship    ║
║                                       ║
║ Special Perks:                        ║
║  - Tandem Signs (combo attacks)       ║
║  - Training sessions (+skill XP)      ║
║  - Access to Witcher caches (loot)    ║
╚═══════════════════════════════════════╝

╔═══════════════════════════════════════╗
║ MAGE COMPANION                        ║
╠═══════════════════════════════════════╣
║ Examples: Triss, Yennefer, Keira      ║
║                                       ║
║ Abilities:                            ║
║  - Powerful ranged magic              ║
║  - Healing spells (restore HP)        ║
║  - Crowd control (stun, freeze)       ║
║  - Teleportation (fast travel bonus)  ║
║                                       ║
║ Recruitment:                          ║
║  - Complete mage storyline            ║
║  - High Scholar Fame (1000+)          ║
║  - Solve magical puzzles together     ║
║                                       ║
║ Special Perks:                        ║
║  - Enchanting bonus (+10% rune power) ║
║  - Spell research (unlock new Signs)  ║
║  - Portal creation (custom fast travel║
║    points)                            ║
╚═══════════════════════════════════════╝

╔═══════════════════════════════════════╗
║ WARRIOR COMPANION                     ║
╠═══════════════════════════════════════╣
║ Examples: Roche, Iorveth, Zoltan      ║
║                                       ║
║ Abilities:                            ║
║  - High HP and armor (tank)           ║
║  - Shield bash (stun enemies)         ║
║  - War cry (buff allies)              ║
║  - Hold the line (protect player)     ║
║                                       ║
║ Recruitment:                          ║
║  - Complete faction quests            ║
║  - High Warrior Fame (500+)           ║
║  - Win tournament together            ║
║                                       ║
║ Special Perks:                        ║
║  - Bodyguard (takes 50% damage for you║
║    when below 30% HP)                 ║
║  - Equipment repair (fixes your gear) ║
║  - Intimidation bonus (dialogue)      ║
╚═══════════════════════════════════════╝

╔═══════════════════════════════════════╗
║ ROGUE COMPANION                       ║
╠═══════════════════════════════════════╣
║ Examples: Dijkstra's Agents, Thieves  ║
║                                       ║
║ Abilities:                            ║
║  - Stealth and assassination          ║
║  - Lockpicking (open doors for you)   ║
║  - Poison attacks (DOT damage)        ║
║  - Distraction (lure enemies away)    ║
║                                       ║
║ Recruitment:                          ║
║  - Complete criminal quests           ║
║  - High Crime Fame (1000+)            ║
║  - Impress with heist skills          ║
║                                       ║
║ Special Perks:                        ║
║  - Treasure hunter (reveal loot)      ║
║  - Black market access (better prices)║
║  - Sneak attack bonus (+50% backstab) ║
╚═══════════════════════════════════════╝

╔═══════════════════════════════════════╗
║ BEAST COMPANION (Unique)              ║
╠═══════════════════════════════════════╣
║ Examples: Trained Griffin, Wolf, Horse║
║                                       ║
║ Abilities:                            ║
║  - Mount (ride for travel)            ║
║  - Combat assist (bite, claw attacks) ║
║  - Tracking (find hidden items)       ║
║  - Loyalty (never betrays)            ║
║                                       ║
║ Recruitment:                          ║
║  - Tame wild beast (quest)            ║
║  - Raise from egg/pup                 ║
║  - Rescue from captivity              ║
║                                       ║
║ Special Perks:                        ║
║  - No upkeep cost (doesn't eat/sleep) ║
║  - Immune to persuasion/betrayal      ║
║  - Customizable (name, appearance)    ║
╚═══════════════════════════════════════╝
```

---

### **Companion Mechanics**

**Recruitment Process:**
```
1. MEET COMPANION:
   - Encounter in quest or world
   - Initial dialogue (learn background)
   - Relationship starts at "Stranger"

2. BUILD RELATIONSHIP:
   - Complete quests together
   - Give gifts (items they like)
   - Make dialogue choices they approve
   - Reach "Friendly" (50+ points)

3. RECRUITMENT QUEST:
   - Unique questline per companion
   - Prove loyalty/skill/trust
   - Final choice: Join or refuse

4. ACTIVE COMPANION:
   - Unlocked for recruitment
   - Can summon/dismiss at will
   - Relationship continues to grow
```

**Companion Slots:**
```
Default: 1 active companion at a time

Expanded Slots (Perks):
- Leadership Skill Lvl 25: 2 companions
- Leadership Skill Lvl 50: 3 companions
- Unique Item "Banner of Command": +1 slot

Managing Companions:
- Open Companion UI (keybind)
- Select active companion(s)
- Dismiss to send back to home
- Track relationship status
```

**Companion AI:**
```
COMBAT BEHAVIOR:

Aggressive:
- Charges enemies
- Focuses targets
- Uses all abilities on cooldown

Defensive:
- Stays near player
- Protects from flanks
- Only attacks when threatened

Ranged:
- Keeps distance
- Shoots/casts from afar
- Kites enemies

Custom:
- Player sets priorities
- "Focus archers" or "Tank damage"
- Advanced tactics unlock at high relationship
```

**Companion Upkeep:**
```
Companions require maintenance:

FOOD:
- Eat 1 meal per day (in-game)
- Player provides or they buy (cost: 5 gold/day)
- Hungry companions = -10% combat effectiveness

PAYMENT (Mercenary types only):
- Pay 50-200 gold/week
- Witchers/Warriors expect payment
- Mages/Rogues optional (relationship-based)
- Beasts are free

EQUIPMENT:
- Companions can wear armor/weapons
- Give them gear (equip in companion UI)
- Damaged gear = less effective
- Upgrade their equipment for better performance
```

---

## 💕 Relationship System

### **Relationship Levels**

```
STRANGER (0 points):
- Just met
- No bonuses
- Basic dialogue only

ACQUAINTANCE (1-49 points):
- Know each other
- +5% XP when nearby
- Casual conversations

FRIENDLY (50-149 points):
- Good friends
- +10% XP, +5% combat damage
- Personal dialogue unlocks
- Can recruit as companion

CLOSE FRIEND (150-299 points):
- Deep friendship
- +15% XP, +10% combat damage
- Share personal stories
- Companion loyalty quests unlock

TRUSTED (300-499 points):
- Absolute trust
- +20% XP, +15% combat damage
- Will risk life for you
- Unique companion abilities unlock
- Romance option available (if applicable)

SOULMATE (500+ points):
- Deepest bond possible
- +30% XP, +25% combat damage
- Companion becomes "Unbreakable" (cannot die)
- Special ending slides (story impact)
- Exclusive dialogue and scenes
```

---

### **Gaining Relationship Points**

**Positive Actions (+Points):**
```
DIALOGUE CHOICES:
- Support their beliefs: +5 points
- Agree with their decisions: +3 points
- Compliment them: +2 points
- Defend them in arguments: +10 points

GIFTS:
- Common gift (they like): +5 points
- Rare gift (they love): +15 points
- Legendary gift (perfect): +50 points
- Wrong gift (they hate): -10 points

QUESTS:
- Complete personal quest: +50 points
- Help their faction: +20 points
- Resolve their problem: +30 points

COMBAT:
- Fight alongside them: +2 points per battle
- Save them from death: +25 points
- Revive them: +10 points

EVENTS:
- Celebrate festival together: +15 points
- Share camp (rest together): +5 points
- Win tournament as team: +30 points
```

**Negative Actions (-Points):**
```
DIALOGUE CHOICES:
- Insult them: -10 points
- Disagree rudely: -5 points
- Lie to them (discovered): -20 points

ACTIONS:
- Attack their faction: -50 points
- Kill their friends: -100 points
- Betray them in quest: -150 points
- Romance rival simultaneously: -50 points

NEGLECT:
- Don't talk for 7 days: -5 points/day
- Dismiss as companion often: -10 points
- Ignore personal quests: -20 points
```

---

### **Companion Preferences**

**Example: Lambert (Witcher Companion)**
```
╔═══════════════════════════════════════╗
║ LAMBERT - WITCHER                     ║
╠═══════════════════════════════════════╣
║ Personality: Sarcastic, Rebellious    ║
║                                       ║
║ LOVES:                                ║
║  - Alcohol (Vodka, Whiskey) +15       ║
║  - Sarcastic dialogue choices +5      ║
║  - Fighting authority +10             ║
║  - Monster trophies (Griffins) +20    ║
║                                       ║
║ LIKES:                                ║
║  - Witcher contracts +5               ║
║  - Dark humor +3                      ║
║  - Independence +5                    ║
║                                       ║
║ DISLIKES:                             ║
║  - Nobility (helping kings) -10       ║
║  - Following orders -5                ║
║  - Flowers (romantic gifts) -5        ║
║                                       ║
║ HATES:                                ║
║  - Killing Witchers -100              ║
║  - Siding with Witch Hunters -50      ║
║  - Overly formal behavior -10         ║
║                                       ║
║ Romance: No (not interested)          ║
╚═══════════════════════════════════════╝
```

**Example: Triss (Mage Companion)**
```
╔═══════════════════════════════════════╗
║ TRISS MERIGOLD - SORCERESS            ║
╠═══════════════════════════════════════╣
║ Personality: Compassionate, Idealistic║
║                                       ║
║ LOVES:                                ║
║  - Roses (red) +20                    ║
║  - Helping people +15                 ║
║  - Magic research +10                 ║
║  - Romantic dialogue +10              ║
║                                       ║
║ LIKES:                                ║
║  - Books (alchemy) +8                 ║
║  - Solving problems peacefully +5     ║
║  - Fashion (dresses) +5               ║
║                                       ║
║ DISLIKES:                             ║
║  - Violence without reason -10        ║
║  - Cruelty to animals -15             ║
║  - Mentioning Yennefer romantically -5║
║                                       ║
║ HATES:                                ║
║  - Betraying mages -100               ║
║  - Siding with Witch Hunters -150     ║
║  - Romance Yennefer (if romancing     ║
║    Triss) -200                        ║
║                                       ║
║ Romance: YES (exclusive)              ║
╚═══════════════════════════════════════╝
```

---

## 💑 Romance System

### **Romanceable Companions**
```
ROMANCE OPTIONS:

Female:
- Triss Merigold (Sorceress)
- Yennefer of Vengerberg (Sorceress)
- Keira Metz (Sorceress)
- Shani (Medic)
- Custom female NPCs (5+ options)

Male:
- Custom male NPCs (3+ options)
- Jaskier/Dandelion (Bard, platonic/romantic)

Non-binary:
- Magical beings (Dryads, Elves)

Requirements:
- Reach "Trusted" relationship (300+ points)
- Complete romance questline
- Choose romantic dialogue options
- Give romantic gifts (flowers, jewelry)
```

**Romance Stages:**
```
STAGE 1: FLIRTING (300-399 points)
- Romantic dialogue unlocks
- Special looks, hints
- "I like spending time with you"

STAGE 2: INTERESTED (400-499 points)
- Romance quest unlocks
- Private conversations
- "There's something between us"
- First kiss cutscene

STAGE 3: RELATIONSHIP (500+ points)
- Officially together
- Exclusive romance (choose one)
- Special perks (see below)
- "I love you" dialogue

STAGE 4: COMMITMENT (1000+ points)
- Marriage proposal (optional)
- Shared home (settlement)
- Children (adoption, endgame)
- Unique ending slides
```

**Romance Perks:**
```
COMBAT BONUSES:
- "Fighting for Love": +10% damage when near partner
- "Protective Instinct": Take 30% less damage near partner
- "Lover's Fury": If partner falls, +50% damage for 30 sec

LIFE BONUSES:
- Free lodging (stay at partner's home)
- Shared income (partner contributes 100 gold/week)
- Discount at merchants (partner's connections)
- Fast travel to partner (teleport to their location)

UNIQUE CONTENT:
- Romance-specific quests (anniversaries, gifts)
- Couple activities (festivals, duels, hunts)
- Private cutscenes (intimate moments)
- Wedding event (player-planned ceremony)
```

**Love Triangle Drama:**
```
If romance multiple companions:

JEALOUSY SYSTEM:
- Companion A finds out about Companion B
- Confrontation scene (choose one)
- Both companions may leave (-500 points each)
- "You have to choose" ultimatum

CONSEQUENCES:
- Lose both if can't decide
- Reputation hit (-100 Heroic Fame)
- Unique "Heartbreaker" achievement
- Bards sing mocking songs about you

RESOLUTION:
- Choose one (other leaves forever)
- Polyamory (very rare, specific companions only)
- Stay single (apologize, reset to Friendly)
```

---

## ⚔️ Rivalry System

### **Creating Rivals**

**How Rivalries Form:**
```
Rivals are companions or NPCs who DISLIKE you:

RELATIONSHIP BELOW -50:
- Negative dialogue
- Refuse to help
- May sabotage you

RELATIONSHIP BELOW -150 (RIVAL):
- Active opposition
- Spread rumors (-Fame)
- Send bounty hunters
- Appear as boss fights in quests

RELATIONSHIP BELOW -300 (NEMESIS):
- Dedicated to your downfall
- Orchestrate elaborate traps
- Recruit others against you
- Final showdown quest
```

**Rival Encounters:**
```
Rivals actively interfere:

IN QUESTS:
- Arrive to compete with you
- "I'll get that contract, Witcher"
- Race to complete objective
- Steal quest rewards

IN COMBAT:
- Ambush you on roads
- Hire mercenaries to attack
- Poison your potions
- Sabotage your equipment

IN SOCIAL:
- Turn NPCs against you
- Spread lies (reputation loss)
- Sabotage romances
- Steal your companions (convince them to leave)
```

**Resolving Rivalries:**
```
Options to end rivalry:

1. DUEL:
   - Challenge to formal combat
   - Winner takes glory
   - Loser respects victor
   - Rivalry becomes "Begrudging Respect" (neutral)

2. RECONCILIATION:
   - Complete rival's personal quest
   - Apologize (dialogue)
   - Gift rare item
   - Slowly rebuild to Friendly

3. ELIMINATION:
   - Kill rival (permanent)
   - High Crime Fame gain
   - Faction consequences
   - Unique loot from rival's body

4. MUTUAL ENEMY:
   - Face common threat together
   - "Enemy of my enemy" truce
   - May become allies after
```

**Famous Rivalries:**
```
Example: Rival Witcher

╔═══════════════════════════════════════╗
║ GAETAN - RIVAL WITCHER                ║
╠═══════════════════════════════════════╣
║ Reason: Competing for contracts       ║
║                                       ║
║ Actions:                              ║
║  - Undercuts your prices              ║
║  - Completes contracts before you     ║
║  - Spreads rumors (you're incompetent)║
║  - Steals your monster trophies       ║
║                                       ║
║ Encounters:                           ║
║  - 3 scripted confrontations          ║
║  - Final duel at tournament           ║
║  - Can recruit if you win             ║
║                                       ║
║ Resolution:                           ║
║  - Defeat in duel → Becomes companion ║
║  - Reconcile → Partnership (shared    ║
║    contracts, split rewards)          ║
║  - Kill → Get his unique gear         ║
╚═══════════════════════════════════════╝
```

---

## 🏠 Companion Homes & Bases

### **Companion Housing**

**Companion Bases:**
```
Each companion has a home location:

WITCHER KEEP (Witchers):
- Kaer Morhen (Lambert, Eskel)
- Training grounds, alchemy lab
- Can visit to find companion

MAGE TOWER (Sorceresses):
- Novigrad apartments
- Library, enchanting room
- Fast travel point

MILITARY CAMP (Warriors):
- Faction outposts
- Armory, sparring ring
- Recruit soldiers here

HIDEOUT (Rogues):
- Sewers, abandoned buildings
- Black market access
- Secret tunnels

YOUR SETTLEMENT (All):
- Build companion quarters
- All companions can live with you
- Easier management
```

**Companion Quarters (Settlement Building):**
```
Build in your owned settlement:

BASIC QUARTERS (1000 gold):
- 1 companion can live here
- Bed, storage chest
- Companion idle here when not active

EXPANDED QUARTERS (5000 gold):
- 3 companions can live here
- Training room (+XP for companions)
- Companion vendor (sell crafts)

LUXURY ESTATE (20,000 gold):
- 6 companions
- All facilities (smithy, alchemy, enchanting)
- Passive income (companions work)
- Social events (dinners, parties)
```

---

## 🎭 Companion Personalities & Memory

### **Dynamic Dialogue**

**Companion Memory:**
```
Companions remember:

PAST CONVERSATIONS:
- "You said you'd help me find my sword"
- Reference earlier dialogue
- Continuity across sessions

PLAYER ACTIONS:
- "I saw you steal from that merchant"
- "Thank you for saving me in that fight"
- "You romance Triss AND Yennefer? Bold."

WORLD EVENTS:
- "The war with Nilfgaard is getting worse"
- "Did you hear about the dragon sighting?"
- React to seasonal festivals

RELATIONSHIP MILESTONES:
- "We've been friends for a year now"
- "Remember when we first met?"
- Anniversary dialogue (romance)
```

**Personality Traits:**
```
Each companion has traits affecting behavior:

BRAVE:
- Charges into combat fearlessly
- Approves of risky dialogue choices
- Never flees from battle

CAUTIOUS:
- Suggests stealth over combat
- Disapproves of reckless actions
- May flee if losing (saves own life)

GREEDY:
- Wants higher share of loot
- Approves of theft, smuggling
- Disapproves of charity

HONORABLE:
- Refuses crime-related quests
- Approves of helping innocents
- Will leave if you become villain

SARCASTIC:
- Witty dialogue
- Mocks enemies mid-combat
- Lighthearted banter with player

SERIOUS:
- Grim, focused
- No-nonsense approach
- Dislikes jokes in tense situations
```

---

## 🏆 Achievements

**Companion Achievements:**
```
👥 First Companion - Recruit your first companion
👥 Full Party - Have 3 active companions simultaneously
👥 Companion Master - Recruit all 15 companions

💕 First Love - Romance a companion
💕 Heartbreaker - Romance 3 companions in one playthrough
💕 Married Life - Marry a companion
💕 Happily Ever After - Reach 1000 relationship with partner

⚔️ Dynamic Duo - Win 50 battles with same companion
⚔️ Unbreakable Bond - Reach "Soulmate" relationship
⚔️ Saved by Love - Companion saves you from death

😠 Rival - Create a rivalry (relationship below -150)
😠 Nemesis - Have a nemesis (relationship below -300)
😠 Reconciliation - Turn rival into friend

🏠 Homeowner - Build companion quarters
🏠 Full House - Have 6 companions living in settlement
```

---

## ⚙️ Configuration

```json5
// config/witchercraft/companion_relationship.json
{
  "companions": {
    "enabled": true,
    "max_active_companions": 1,
    "companion_combat": true,
    "companion_dialogue": true,
    "companion_upkeep": true,
    "food_cost_per_day": 5,
    "payment_cost_per_week": 100
  },
  
  "relationships": {
    "enabled": true,
    "max_relationship": 1000,
    "min_relationship": -500,
    "decay_enabled": false, // relationships don't decay
    "memory_system": true
  },
  
  "romance": {
    "enabled": true,
    "multiple_romances_allowed": true,
    "jealousy_system": true,
    "marriage_enabled": true,
    "romance_perks": true
  },
  
  "rivalry": {
    "enabled": true,
    "rival_encounters": true,
    "rival_sabotage": true,
    "nemesis_system": true,
    "duel_resolution": true
  },
  
  "housing": {
    "companion_homes": true,
    "player_settlement_quarters": true,
    "luxury_estate": true
  }
}
```

---

## 🎯 Integration with Other Systems

| System | Integration |
|--------|-------------|
| **Combat** | Companions fight alongside, use abilities, can die |
| **Quest System** | Companion-specific quests, relationship affects quest outcomes |
| **Fame** | Companions react to your fame, brag about traveling with legend |
| **Economy** | Companions cost money (food, payment), sell crafted items |
| **Settlement** | Build companion housing, companions contribute to settlement |
| **Political** | Companions have faction loyalties, approve/disapprove of faction choices |

---

## 🛠️ Implementation Phases

### **Phase 1: Core Companion System (6-7 weeks)**
- [ ] Companion entity types (Witcher, Mage, Warrior, Rogue, Beast)
- [ ] Recruitment mechanics
- [ ] Companion AI (combat, following, idle)
- [ ] Companion UI (management, equipment)

### **Phase 2: Relationship System (5-6 weeks)**
- [ ] Relationship point tracking
- [ ] Relationship levels (Stranger to Soulmate)
- [ ] Dialogue choices affecting relationship
- [ ] Gift system (items companions like/hate)

### **Phase 3: Companion Personalities (4-5 weeks)**
- [ ] Memory system (remember conversations, actions)
- [ ] Personality traits (brave, cautious, etc.)
- [ ] Dynamic dialogue (react to world, player)
- [ ] Companion preferences (unique per NPC)

### **Phase 4: Romance System (5-6 weeks)**
- [ ] Romance stages (Flirting to Commitment)
- [ ] Romance quests (10+ unique questlines)
- [ ] Romance perks (combat, life bonuses)
- [ ] Marriage and commitment mechanics

### **Phase 5: Rivalry System (4-5 weeks)**
- [ ] Rivalry creation (-50 to -300 relationship)
- [ ] Rival encounters (quest interference, ambushes)
- [ ] Nemesis system (dedicated opposition)
- [ ] Resolution mechanics (duel, reconcile, eliminate)

### **Phase 6: Housing & Polish (3-4 weeks)**
- [ ] Companion home locations
- [ ] Settlement companion quarters (3 tiers)
- [ ] Companion upkeep (food, payment)
- [ ] Achievement integration

**Total Estimated Time:** 27-33 weeks (7-8 months)

---

**Status:** 📋 Design Phase  
**Priority:** 🟡 Medium-High (Adds depth to storytelling)  
**Dependencies:** Quest System, Combat, Settlement Governance  
**Related Files:** POLITICAL_SYSTEM.md, ECONOMY_TRADE_SYSTEM.md, FAME_REPUTATION_SYSTEM.md
