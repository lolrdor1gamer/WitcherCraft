# Kaedwen Village Structure Design

_Status: concept approved, ready for block-out_

## 1. Narrative & Gameplay Goals
- **Witcher fantasy:** Weathered northern frontier settlement that feels defensible yet lived-in.
- **Player value:** Early-game refuge featuring alchemy supplies, a Witcher contract board, forageable herbs, and a forge.
- **Encounter hooks:** Spawns low-tier monsters (Nekkers, Drowners) nearby, offers contracts and side quests via notice board.
- **Progression tie-ins:** Provides access to unique merchants (armor smith, herbalist), Witcher Forge crafting station, and Places of Power teaser.

## 2. Worldgen & Placement Constraints
| Aspect | Plan |
| --- | --- |
| **Structure type** | `minecraft:jigsaw` structure with modular pools |
| **Recommended biomes** | Taiga, snowy taiga, grove, windswept forest (cool climates reminiscent of Kaedwen) |
| **Footprint** | ~120×120 blocks, gently sloped terrain, heightmap aligned to `WORLD_SURFACE_WG` |
| **Spacing** | 35 chunks spacing, 20 chunk separation (rare enough to feel special) |
| **Chunk offset** | Snap to chunk center to reduce terrain tearing |

## 3. Layout Overview
```
             [Watchtower]
                 |
   [Farm]--[House]--[Town Square]--[Stable]
      |         |         |             \
 [Herbalist] [Forge]  [Notice Board]   [Dock]
                 |           |
              [Inn]      [Well]
```
- **Town Square (anchor):** Kaedweni banner, notice board, market stalls, well.
- **Radial streets:** Compressed gravel/cobblestone paths, lantern posts.
- **Perimeter:** Wooden palisade with two gates plus a small dock if near water.

## 4. Building Roster & Purpose
| Structure | Description | Gameplay |
| --- | --- | --- |
| Town Hall / Contract Board | Two-storey timber hall with Kaedwen heraldry | Notice board UI, contract NPC |
| Witcher Forge | Semi-open forge with custom block, anvils, grindstone | Forge recipes, merchant |
| Herbalist Hut | Cottage with drying herbs, brewing table | Potion ingredients, mini quest |
| General Homes (3 variants) | Mix of single/two floor homes | Loot chests, lore notes |
| Tavern/Inn | Communal hearth, beds, hidden cellar | Rest bonuses, rumor leads |
| Watchtower | 3-storey stone/spruce tower | Archer NPC, chest w/ bolts |
| Stable & Pen | Horses, hay, storage loft | Saddles, horse gear |
| Fields & Gardens | Potato, wheat, flax, herb patches | Harvestable crops |
| Small Dock (optional) | Jetty w/ fishing hut | Fishing loot, boat |

## 5. Block Palette & Mood
- **Timber:** Spruce logs/planks, stripped dark oak for accents.
- **Stone:** Cobbled deepslate foundations, stone brick chimneys, mossy variants.
- **Roofing:** Dark oak stairs/slabs, occasional oxidized copper trim.
- **Details:** Fletching tables, barrels, lanterns, armor stands with Kaedwen banners, goat horns for sound cues.

### 5a. Kaedwen-Specific Blocks To Author First
| Block | Type | Primary Use | Notes |
| --- | --- | --- | --- |
| **Kaedweni Timber Beam** | Log/Pillar (axis aware) | Structural corners, exposed rafters | Textured to look resin-soaked; variants for post caps. |
| **Kaedweni Plaster Wall** | Thin wall/panel block | Fills between beams on homes & tavern | Light stucco with weathering overlay; connects like copper grates. |
| **Kaedweni Stone Foundation** | Trim block + slab | Base course for every building | Two tint variants (dry, mossy) for processor rules. |
| **Kaedweni Roof Shingles** | Stair, slab, vertical slab | Distinct dark shingle roofline | Slight blue tint to stand apart from vanilla dark oak. |
| **Reinforced Palisade Log** | Fence/Wall hybrid | Village perimeter + towers | Connects both like fences and walls; taller hitbox for siege look. |
| **Gatehouse Portcullis** | Tall door block (2×3) | Gate towers & dock entries | Can be toggled via redstone or custom lever block. |
| **Witcher Forge Hearth** | Functional block | Unique crafting station | Emits particles/sound; hooks into existing Witcher forge logic. |
| **Kaedweni Notice Board** | Interactive block entity | Contracts + quest UI | Stores JSON entries, holds map pins. |
| **Herbal Drying Rack** | Decor block (multi-model) | Herbalist hut interior/exterior | Accepts herb items to display, boosts brewing speed nearby. |
| **Supply Crate (Kaedwen)** | Container variant | Market stalls, docks, barracks | Custom loot table entries, locked variant for quests. |
| **Frostglass Lantern** | Light source | Streets, docks, watchtower | Cold cyan light, snow particle drift, resists powder snow. |
| **Frozen Well Core** | Fluid-adjacent block | Unique well centerpiece | Slight translucency, emits dripping water sound, slows players. |

**Asset Prep Order:**
1. Author PBR-ready textures for timber, plaster, stone foundation, and shingles (recolor vanilla atlases for speed).
2. Implement block families in `ModBlocks` with blockstate/models referencing new textures.
3. Add basic loot tables/recipes (e.g., timber beam from spruce logs + resin).
4. Stub interactive logic (notice board, forge, drying rack) but block out geometry immediately so NBT exports remain stable.
5. Once these blocks exist, begin capturing structures—the palette swap afterwards would be expensive.

## 6. Points of Interest & Loot
| POI | Contents |
| --- | --- |
| Notice Board | Data-driven contract listings, points to nearby monster nests |
| Witcher Forge | Custom container w/ upgrade recipes, vendor sells diagrams |
| Herbalist Chest | Herbs, potions, decoction bases, lore book |
| Inn Cellar | Randomized brew kegs, hidden silver ingots |
| Watchtower Chest | Bolts, arrows, map fragment |
| Dock Crate | Fish, rope, chance for treasure map |

## 7. NPC & Mob Hooks
- **Villagers:** Custom professions (Kaedweni guard, herbalist, armorer) mapped to POIs.
- **Guards:** Patrolling Iron Golem + crossbow villager duo near gates.
- **Ambient animals:** Horses, goats, cats.
- **Monster spawners:** Optional hidden Nekker nest outside palisade.

## 8. Technical Breakdown
1. **Structure Definition** (`data/witchercraft/worldgen/structure/kaedwen_village.json`)
   - `type`: `minecraft:jigsaw`
   - `start_pool`: `witchercraft:kaedwen_village/center`
   - `size`: 7 (supports 3 rings of streets)
   - `terrain_adaptation`: `beard_box`
   - `biomes`: tag `witchercraft:has_structure/kaedwen_village`
2. **Structure Set** (`worldgen/structure_set/kaedwen_village.json`)
   - Random spread placement (spacing 35, separation 20, salt 147325123)
3. **Template Pools** (`worldgen/template_pool/kaedwen_village/*.json`)
   - `center`: town square, inn, forge connectors
   - `streets`: L/T intersections, plazas
   - `buildings`: Weighted list of houses, tavern, herbalist, etc.
   - `perimeter`: palisade walls, gates, towers, dock adapters
4. **Processors**
   - Snow layering on roofs in cold biomes
   - Mossy conversion near water
   - Random barrel/crate loot decorators
5. **Loot Tables** (`data/witchercraft/loot_table/structures/kaedwen_village/*.json`)
   - Tailor per-building chest contents, include Witcher diagrams & mutagens.
6. **Tags**
   - `tags/worldgen/biome/has_structure/kaedwen_village.json`
   - `tags/worldgen/structure/kaedwen_spawns_in_villages.json` for events/AI.

## 9. Implementation Roadmap
1. **Block-out (This task)**
   - Use structure blocks to capture center plaza + first ring modules.
   - Blueprint references now live under `data/witchercraft/structures/kaedwen/`:
     - Civic & profession: `town_square_core.json`, `town_hall_contracts.json`, `witcher_forge.json`, `herbalist_hut.json`.
     - Housing: `house_small_a.json`, `house_small_b.json`, `house_lofted.json`.
     - Support modules: `palisade_gatehouse.json`, `watchtower.json`, `stable_pen.json`, `fields_garden.json`, `dock_jetty.json`, `farmstead_barn.json`, `tavern_inn.json`.
   - Export to `data/witchercraft/structures/kaedwen_village/*.nbt` with matching names after recreating them in-world.
2. **Template Wiring**
   - JSON scaffolding now lives under `data/witchercraft/worldgen/`:
     - `structure/kaedwen_village.json` – jigsaw definition (size 7, beard-box terrain adaptation).
     - `structure_set/kaedwen_village.json` – random spread placement (spacing 35, separation 20, salt 147325123).
     - `template_pool/kaedwen_village/{center,housing,profession,perimeter}.json` – references every exported `.nbt` with weighted entries.
     - `processor_list/kaedwen_village/base.json` – baseline ignore list for structure/jigsaw blocks.
   - Add specialized processors (snow/moss/loot decorators) once the art pass is finalized.
3. **Integration**
   - Add structure set + biome tags.
   - Hook notice board UI & NPC spawns.
4. **Playtesting**
   - Validate pathing, loot, spawn frequency.
   - Adjust spacing/weights to avoid overlap.

## 10. Open Questions / Next Steps
- Finalize NPC profession logic (Fabric villager mixins vs. custom entities).
- Determine contract board UX (book GUI vs. custom screen).
- Decide if Place of Power spawns within village or nearby clearing.
- Produce unique textures for Kaedwen banners, notice board, signage.

---
_Use this document as the single source of truth while we capture the structure in NBT and wire the worldgen JSONs._
