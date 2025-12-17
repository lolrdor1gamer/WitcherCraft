# WitcherCraft - Economy & Trade System

## Overview
A dynamic economy system featuring regional currencies, supply/demand mechanics, trade routes, merchant relationships, and the Witcher tradition of bartering. Money has weight, choices matter, and the economy reacts to world events.

---

## 💰 Currency System

### **Regional Currencies**

| Currency | Region | Base Value | Material | Rarity |
|----------|--------|------------|----------|--------|
| **Oren** | Temeria, Redania | 1.0x | Silver coin | Common |
| **Floren** | Nilfgaard | 1.2x | Gold coin | Common in south |
| **Crown** | Skellige | 0.9x | Bronze coin | Common in isles |
| **Bizant** | Novigrad, Kovir | 1.5x | Gold coin | Rare |
| **Ancient Coin** | Ruins, treasure | 2.0x | Various | Very rare |

**Exchange System:**
- Merchants accept all currencies but apply exchange rates
- Banks in major cities offer currency exchange (2% fee)
- Exchange rates fluctuate based on war and trade routes
- Example: 100 Orens = 83 Florens = 111 Crowns = 67 Bizants

**Physical Currency:**
- Coins have weight (100 coins = 1kg)
- Can be deposited in banks (withdraw at any branch)
- Risk: Carrying large sums attracts bandits (see Dynamic Events)

---

## 🏪 Merchant System

### **Merchant Types**

#### **1. General Trader** (Every settlement)
- **Stock:** Food, basic tools, crafting materials
- **Buys:** Everything (low prices)
- **Sells:** Common items
- **Capital:** 100-500 gold
- **Refresh:** Every 24 hours

#### **2. Blacksmith** (Villages+)
- **Stock:** Weapons, armor, repair kits
- **Buys:** Ores, monster parts, damaged equipment
- **Sells:** Crafted weapons/armor (regional styles)
- **Capital:** 500-2000 gold
- **Refresh:** Every 48 hours
- **Special:** Can craft custom items from diagrams

#### **3. Alchemist/Herbalist** (Villages+)
- **Stock:** Potions, herbs, alchemy ingredients
- **Buys:** Monster parts, herbs, empty bottles
- **Sells:** Recipes, rare ingredients
- **Capital:** 300-1500 gold
- **Refresh:** Every 24 hours
- **Special:** Teaches alchemy recipes at high reputation

#### **4. Innkeeper** (All settlements)
- **Stock:** Food, drink, room for rent
- **Buys:** Food items, alcohol
- **Sells:** Meals (buffs), ale, rumors (quest leads)
- **Capital:** 200-800 gold
- **Refresh:** Every 12 hours
- **Special:** Notice board for quests

#### **5. Black Market Dealer** (Hidden locations)
- **Stock:** Stolen goods, illegal items, rare artifacts
- **Buys:** Everything, no questions asked
- **Sells:** Contraband, enchanted items, poisons
- **Capital:** 2000-10000 gold
- **Refresh:** Every 72 hours
- **Special:** Only appears at night, requires discovery

#### **6. Master Craftsman** (Cities only)
- **Stock:** Witcher gear diagrams, masterwork items
- **Buys:** Rare materials only
- **Sells:** Legendary equipment (very expensive)
- **Capital:** 5000-20000 gold
- **Refresh:** Every 7 days
- **Special:** Requires Honored+ reputation with faction

---

### **Dynamic Pricing**

**Base Price Modifiers:**

```java
final_price = base_price × region_modifier × reputation_modifier × supply_demand × rarity

Region Modifier:
- Item's native region: 1.0x
- Adjacent region: 1.2x
- Distant region: 1.5x
- Enemy region: 2.0x (if trade allowed)

Reputation Modifier:
- Revered: 0.7x
- Honored: 0.8x
- Friendly: 0.9x
- Neutral: 1.0x
- Unfriendly: 1.2x
- Hostile: 1.5x

Supply & Demand:
- Abundant: 0.8x
- Normal: 1.0x
- Scarce: 1.5x
- Desperate: 3.0x (war, siege, plague)

Rarity:
- Common: 1.0x
- Uncommon: 2.0x
- Rare: 5.0x
- Legendary: 20.0x
```

**Examples:**
```
Silver Sword (base: 500 orens)
- In Temeria, Friendly rep, Normal supply, Uncommon rarity
- Price: 500 × 1.0 × 0.9 × 1.0 × 2.0 = 900 orens

Monster Brain (base: 20 orens)
- In Kaedwen, Neutral rep, Abundant supply (player sold 50), Common rarity
- Price: 20 × 1.2 × 1.0 × 0.8 × 1.0 = 19 orens (merchant won't buy more)

Witcher Diagram (base: 2000 orens)
- In Nilfgaard, Hostile rep, Scarce, Legendary
- Price: 2000 × 2.0 × 1.5 × 1.5 × 20.0 = 180,000 orens (effectively unavailable)
```

---

### **Bartering System (Witcher Tradition)**

**How Bartering Works:**

1. **Initiate Trade** - Player opens merchant GUI
2. **Make Offer** - Drag items into "Offer" slots (both sides)
3. **Negotiation Phase:**
   ```
   Merchant evaluates offer:
   - Calculate total value (player offer vs merchant offer)
   - Apply modifiers (reputation, relationship, desperation)
   - Decision: Accept, Counter-offer, Reject
   ```

4. **Player Actions:**
   - **Accept:** Complete trade
   - **Counter:** Adjust offer (add/remove items)
   - **Persuade:** Charisma check (Signs attribute) - improve merchant's mood
   - **Intimidate:** Strength check (Combat attribute) - risky, may refuse future trades
   - **Walk Away:** Cancel trade

**Merchant Personality Types:**
- **Greedy:** Demands 120% value, but accepts rare items at premium
- **Fair:** Accepts 100% value trades, reliable
- **Desperate:** Accepts 80% value (settlement under siege, needs supplies)
- **Shrewd:** Starts at 130% value, but can be negotiated down (enjoys haggling)

**Special Barter Options:**
- **Information for Goods:** Rumors, quest leads traded for items
- **Future Favors:** "I'll owe you" - unlocks special quest later
- **Bulk Discounts:** Sell 50+ of same item → 10% bonus value

---

## 🚚 Trade Routes & Caravans

### **NPC Caravan System**

**Route Generation:**
```
Every settlement spawns 1-3 caravans per week
Route: Settlement A → Settlement B (2000-5000 blocks apart)
Travel Time: 1 block per 2 seconds (real-time)
Cargo: Regional goods (40% stock of Settlement A exported to B)

Caravan Composition:
- 1 Merchant (leader)
- 2-4 Guards (regional soldiers)
- 1-2 Pack horses/carts (storage entities)
- Total value: 500-5000 gold in goods
```

**Player Interactions:**

1. **Escort Quest:**
   - Accept from settlement notice board
   - Follow caravan, protect from bandits/monsters
   - Reward: 10% of cargo value + reputation

2. **Trade on the Road:**
   - Caravan stops if player approaches peacefully
   - Can buy/sell items at 10% markup (convenience fee)
   - Limited stock (whatever they're transporting)

3. **Ambush (Bandit Path):**
   - Attack caravan (requires low morality or Scoia'tael alignment)
   - Guards fight back, may call for help
   - Loot: Entire cargo + merchant gold
   - Consequence: -500 rep with faction, bounty placed

4. **Rescue:**
   - Find caravan under attack by monsters/bandits
   - Aid them → +100 rep, 20% of cargo value reward
   - Ignore → Caravan destroyed, settlement suffers (prices increase)

**Trade Route Impact:**
```
Active Route (success rate >70%):
- Destination settlement: Abundant supply (prices drop 20%)
- Source settlement: Increased demand (prices rise 10%)
- Economic growth: Both settlements gain population

Disrupted Route (success rate <30%):
- Both settlements: Scarce supply (prices rise 50%)
- Quest availability: "Clear the Roads" contracts spawn
- Economic decline: Settlements shrink, services limited
```

---

## 🏦 Banking System

### **Bank Services** (Cities only)

**1. Deposit/Withdrawal**
- Store gold safely (no weight limit)
- Access from any bank branch (networked)
- No fees for storage
- Interest: 0.1% per day (passive income)

**2. Loans**
```
Loan Amount: 100 - 10,000 gold
Requirement: Friendly+ reputation with faction
Interest Rate: 5-15% (based on reputation)
Term: 30 days
Consequence of Default: -1000 rep, bounty hunters, cannot borrow again

Use Cases:
- Purchase expensive equipment early
- Invest in settlement (player governance)
- Emergency funds during contract
```

**3. Investment Opportunities**
```
Merchant Ventures:
- Buy shares in NPC merchant (100-1000 gold per share)
- Passive income: 10 gold/day per share
- Risk: Merchant can be killed/robbed (lose investment)
- Max investment: 50 shares per merchant

Trade Company Bonds:
- Long-term investment (90 days)
- Return: 20-50% based on trade route success
- Can sell early at 80% current value
```

**4. Currency Exchange**
- Convert between regional currencies
- Fee: 2% of transaction
- Rates fluctuate based on war/trade (check daily)

---

## 📊 Supply & Demand Simulation

### **Settlement Resource Tracking**

Each settlement tracks 20 resource categories:

| Category | Production Sources | Consumption |
|----------|-------------------|-------------|
| Food | Farms, hunters | Daily (2/NPC) |
| Ore | Mines | Blacksmiths, exports |
| Herbs | Herbalists, wild gathering | Alchemists, healing |
| Leather | Hunters, tanners | Armor crafting |
| Wood | Lumberjacks, forests | Construction, fuel |
| Weapons | Blacksmiths | Guards, adventurers |
| Monster Parts | Contracts, player sales | Alchemy, exports |
| Luxury Goods | Imports | Wealthy NPCs |

**Production & Consumption Formula:**
```java
Daily Production = (Producers × Efficiency) + Imports
Daily Consumption = (Population × NeedRate) + Exports

Net Balance = Production - Consumption

If Net > 0:
  Supply increases → Prices drop → Exports increase
  
If Net < 0:
  Supply decreases → Prices rise → Imports increase
  
If Net = 0:
  Equilibrium (stable economy)
```

**Scarcity Events:**
```
When resource drops below 20% of demand for 3+ days:
- Price multiplier: 3.0x
- Quest spawns: "Urgently Needed: [Resource]"
- Merchant refuses to sell (keeps for locals)
- Settlement sends caravan to import (expensive)
```

**Abundance Events:**
```
When resource exceeds 200% of demand:
- Price multiplier: 0.5x
- Merchants eager to buy player's stock
- Exports increase (caravans leave more frequently)
- Crafting becomes cheaper
```

---

## 💼 Black Market Economy

### **Contraband System**

**Illegal Items:**
| Item Type | Illegal In | Detection Method | Penalty |
|-----------|------------|------------------|---------|
| Nilfgaardian Weapons | Northern Kingdoms | Guard search | -200 rep, confiscation |
| Northern Weapons | Nilfgaard | Guard search | -200 rep, confiscation |
| Magic Items | Redania | Witch hunter patrols | -500 rep, trial |
| Stolen Goods | All regions | Special "Stolen" tag | -100 rep, jail |
| Rare Herbs (drug equivalent) | Varies | Random search | Fine (100g), confiscation |
| Forbidden Books | Redania | Inspection | -300 rep, burning |

**Smuggling Mechanics:**
1. **Acquire Contraband** - Steal, loot, or buy from black market
2. **Item is Tagged** - "Stolen" or "Contraband: [Region]"
3. **Transport:**
   - Risk: Border checkpoint search (30% chance)
   - Mitigation: Hide in false-bottom bag (Alchemy craft, 50% detection reduction)
   - Alternative: Hire smuggler NPC (200g fee, 90% success)

4. **Sell to Black Market:**
   - Pay 150% of normal merchant price
   - No reputation penalty
   - Quest leads: Fence knows criminal underworld

**Black Market Locations:**
- 🌙 **Night Market Alleys** (Cities, 8pm-4am only)
- 🏚️ **Abandoned Buildings** (Frontier zones)
- 🌲 **Forest Camps** (Scoia'tael territory)
- 🕳️ **Sewer Entrances** (Novigrad, major cities)

**Discovery Method:**
- Rumor from innkeeper (low chance)
- Quest reward (criminal contact gives location)
- Witcher Senses (track suspicious NPCs at night)

---

## 🎯 Economic Events

### **1. Market Crash**
```
Trigger: War, plague, or settlement destruction nearby
Duration: 7-14 days
Effect:
- All prices drop 40%
- Merchants have 50% less gold
- Quest rewards reduced
- Opportunity: Buy rare items cheap
```

### **2. Inflation Spiral**
```
Trigger: Too much currency in circulation (player exploits)
Duration: Until corrected (player spends or banks gold)
Effect:
- Prices increase 2% per day (compounding)
- Merchants refuse large transactions
- Economic quest: "Stabilize the Economy"
```

### **3. Trade Embargo**
```
Trigger: Faction war (Nilfgaard vs North)
Duration: While war active
Effect:
- Cross-faction trade illegal
- Smuggling prices skyrocket (5x normal)
- Black markets flourish
- Scarcity of enemy region goods
```

### **4. Harvest Festival**
```
Trigger: Seasonal event (every 90 days)
Duration: 3 days
Effect:
- Food prices drop 60%
- Merchants have 200% more stock
- Special festival items available
- Mini-games for prizes (gold, items)
```

### **5. Monster Bounty Boom**
```
Trigger: Monster plague event active
Duration: While plague lasts
Effect:
- Contract rewards increased 300%
- Monster parts sell for 200% value
- Alchemists restock constantly
- Competition: Other witchers/hunters appear
```

---

## 🛠️ Player Economic Strategies

### **1. Arbitrage Trading**
- Buy cheap in one region, sell high in another
- Example: Kaedwen furs (cheap) → Nilfgaard (luxury, 3x price)
- Risk: Caravan attacks, border seizure, currency exchange loss

### **2. Monopoly Creation**
- Buy entire stock of rare item from all merchants
- Wait for scarcity event (prices rise)
- Sell at profit to desperate buyers
- Risk: Economic event (crash, embargo) ruins plan

### **3. Investment Portfolio**
- Diversify: Banks (safe, low return) + Merchants (risky, high return)
- Passive income funds expensive purchases
- Long-term strategy (90+ days to see major returns)

### **4. War Profiteering**
- Sell weapons/armor to both sides (if neutral)
- Buy cheap from losing side, sell to winning side
- Risk: Both sides discover duplicity (-500 all factions)

### **5. Crafting Economy**
- Gather raw materials (free via exploration)
- Craft items (add value)
- Sell crafted items (profit = added value - time)
- Best margins: Witcher gear, potions, bombs

---

## ⚙️ Configuration

```json5
// config/witchercraft/economy.json
{
  "currency": {
    "coins_have_weight": true,
    "weight_per_100_coins": 1.0, // kg
    "exchange_rate_fluctuation": true,
    "bank_interest_rate": 0.001 // 0.1% daily
  },
  
  "merchants": {
    "dynamic_pricing": true,
    "supply_demand_enabled": true,
    "reputation_affects_prices": true,
    "barter_system": true,
    "merchant_gold_limit_multiplier": 1.0,
    "stock_refresh_speed": 1.0
  },
  
  "trade_routes": {
    "npc_caravans": true,
    "caravan_spawn_rate": 1.0,
    "caravan_attack_chance": 0.3, // 30% chance of ambush
    "route_impact_on_prices": true
  },
  
  "banking": {
    "banks_enabled": true,
    "loans_available": true,
    "loan_interest_rate": 0.10, // 10% over 30 days
    "investment_returns": true,
    "cross_region_access": true
  },
  
  "black_market": {
    "enabled": true,
    "contraband_detection_chance": 0.3,
    "smuggling_risk": "medium", // "low", "medium", "high"
    "black_market_price_multiplier": 1.5
  },
  
  "events": {
    "economic_events_enabled": true,
    "event_frequency": "normal", // "rare", "normal", "frequent"
    "war_trade_embargo": true,
    "seasonal_festivals": true
  }
}
```

---

## 🎯 Integration with Other Systems

| System | Integration |
|--------|-------------|
| **Political System** | Reputation affects prices, wars cause embargoes, faction quests reward currency |
| **World Generation** | Regions determine currency type, settlements spawn merchants, trade routes connect cities |
| **Quest System** | Contract rewards in gold, quest items can be sold, economic quests (stabilize markets) |
| **Crafting System** | Materials bought from merchants, crafted items sold for profit, diagrams purchased |
| **Monster System** | Monster parts sold to merchants, scarcity if player over-hunts, bounties posted |

---

## 🛠️ Implementation Phases

### **Phase 1: Core Economy (3-4 weeks)**
- [ ] Currency items (oren, floren, crown)
- [ ] Basic merchant types with stock
- [ ] Static pricing system
- [ ] Buy/sell GUI

### **Phase 2: Dynamic Pricing (4-5 weeks)**
- [ ] Reputation-based price modifiers
- [ ] Regional pricing differences
- [ ] Supply & demand tracking per settlement
- [ ] Scarcity/abundance events

### **Phase 3: Bartering & Banks (3-4 weeks)**
- [ ] Barter GUI and negotiation system
- [ ] Merchant personalities
- [ ] Banking services (deposit, withdraw, loans)
- [ ] Currency exchange

### **Phase 4: Trade Routes (5-6 weeks)**
- [ ] NPC caravan spawning and pathfinding
- [ ] Escort quests
- [ ] Caravan attacks (bandits, monsters)
- [ ] Trade route impact on settlement economy

### **Phase 5: Advanced Features (4-5 weeks)**
- [ ] Black market system
- [ ] Contraband and smuggling
- [ ] Economic events (crash, inflation, embargo)
- [ ] Investment system

**Total Estimated Time:** 19-24 weeks (5-6 months)

---

**Status:** 📋 Design Phase  
**Priority:** 🟡 High (Critical for progression and immersion)  
**Dependencies:** Political System (reputation), World Generation (settlements, regions)  
**Related Files:** POLITICAL_SYSTEM.md, WORLDGEN_SYSTEM.md, QUEST_REPUTATION_SYSTEM_UPDATE.md
