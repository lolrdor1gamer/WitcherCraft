# Witchercraft Structure Blueprints

These JSON files describe **conceptual block blueprints** for Kaedwen settlement pieces. They do **not** use Minecraft's binary NBT structure format yet; instead, they provide human-readable layers you can rapidly iterate on, export to structure blocks, or feed into a future converter.

## Format
```jsonc
{
  "type": "witchercraft:structure_blueprint",
  "size": [width, height, depth],
  "origin": "bottom_south_west",
  "palette": {
    "S": "minecraft:spruce_planks",
    "L": "minecraft:spruce_log",
    "O": "minecraft:oak_planks",
    "#": "minecraft:air"
  },
  "layers": [
    {"y": 0, "rows": ["SSS", "S#S"]},
    {"y": 1, "rows": ["LLL", "L#L"]}
  ],
  "loot_table": "witchercraft:structures/kaedwen_house_small_1",
  "notes": "Optional freeform text"
}
```
- **size**: Axis-aligned bounding box in blocks.
- **origin**: Placement reference (currently always `bottom_south_west`).
- **palette**: Map single-character tokens → block/resource identifiers.
- **layers**: Each entry lists the Y level (`y`) and the south→north rows for that layer, with east-west columns left→right.
- **loot_table**: Optional resource location for the default chest/table loot.
- **notes**: Implementation tips.

## Usage Tips
1. When building in-world, reproduce each layer from the blueprint.
2. Once satisfied, capture the structure with a structure block and export to `.nbt` using the same name.
3. Update the template pools/structure definitions to point to the exported `.nbt` files.
4. Keep these JSONs as documentation and regression specs—diff-friendly and easy to tweak.

Future tooling can parse this format to auto-generate `.nbt` files or handle procedural placement.

## Current Kaedwen Set
- `kaedwen/town_square_core.json` – 7×7×4 plaza hub with a frozen well, notice board, lantern pylons, and crate staging. Use it as the anchor for contracts or to align roads across the settlement.
- `kaedwen/herbalist_hut.json` – 9×9×5 timber-and-plaster cottage containing drying racks, craft tables, and a chimneyed hearth suitable for profession houses.
- `kaedwen/palisade_gatehouse.json` – 7×5×5 palisade gate segment with a suspended portcullis, lantern hooks, and chain counterweights for perimeter stitching.
- `kaedwen/town_hall_contracts.json` – Two-storey contracts hall with interior notice boards, lectern dais, lantern spine, and attic storage above a tall shingle roof.
- `kaedwen/witcher_forge.json` – Open-sided forge yard packed with anvils, blast furnace, and crate stores around the Witcher forge hearth.
- `kaedwen/house_small_a.json` – Compact single-storey dwelling with a bed alcove, paired chests, and lantern clerestory windows.
- `kaedwen/house_small_b.json` – Variant cottage featuring an offset entry, lantern nook, and east-facing bed/wardrobe layout.
- `kaedwen/house_lofted.json` – Two-level residence with loft sleeping space, internal stairs, and steep shingle roof slices.
- `kaedwen/tavern_inn.json` – 11×11×7 inn core including bar, hearth, keg storage, and bunk rows for travelers.
- `kaedwen/watchtower.json` – 7×7×9 palisade tower with ladder shaft, grindstone chest level, and parapet lookout deck.
- `kaedwen/stable_pen.json` – 9×11×5 stable lane containing hay-packed stalls, troughs, fence pens, and gated exits.
- `kaedwen/fields_garden.json` – 11×11×3 agricultural plot with alternating herb/farmland rows, irrigation channels, and lantern posts.
- `kaedwen/dock_jetty.json` – 9×13×4 spruce pier module with beam supports, lantern pylons, crate stack, and fishing gear props.
- `kaedwen/farmstead_barn.json` – 11×11×6 double-door barn filled with hay bales, supply crates, and a tall gabled roofline.

Each blueprint includes:
- **Palette metadata** that maps every glyph to an explicit block or block state (e.g., `t`/`T` for the lower/upper spruce door halves).
- **Layered slices** sized to the `width × depth` grid, enumerated south → north per layer so you can reproduce them quickly in a creative test world.
- **Loot hooks** targeting `witchercraft:structures/*` tables so exported `.nbt` files can drop right into future jigsaw pools without extra bookkeeping.

When you export the finished builds with structure blocks, keep the same names so downstream worldgen JSON can reference them directly.

## Exporting Blueprints to `.nbt`
1. **Recreate the layers in a sandbox world.** Use the palette table for the correct blocks/states and build from `y=0` upward. Leave two blocks of buffer space around the footprint so bounding boxes stay clean.
2. **Mark the footprint with a Structure Block.**
  - Place a Structure Block in **Save** mode at the south‑west corner you used while building.
  - Set `X/Z size` to the blueprint `width/depth` and `Y size` to the listed height (include any lantern poles or roof ridges).
  - Give it a name like `kaedwen/town_square_core` – the namespace (`kaedwen`) must match the folder under `data/witchercraft/structures/`.
3. **Save the structure.** Click *Save* once the bounding box fits. Minecraft writes `structures/<namespace>/<name>.nbt` inside the world save (`.minecraft/saves/<world>/generated/`…).
4. **Copy the file into the mod.** Drop the `.nbt` into `src/main/resources/data/witchercraft/structures/kaedwen/` so the resource loader can find it. Keep the filename identical to the Structure Block entry.
5. **Verify jigsaw connectors.** While the build is still in-world, double‑check every road/palisade connection uses matching jigsaw target names (e.g., `witchercraft:kaedwen_village:street`, `...:perimeter`) so the template pools can stitch pieces together.
6. **Regenerate data if needed.** After copying the `.nbt`, rerun `./gradlew runDatagen` (or `build`) to make sure Loom picks up the new assets and that the pools/structure definitions see the files.

Tip: keep the blueprint JSONs alongside the final `.nbt` exports—they act as a textual diff for future art passes while the actual worldgen uses the binary files.
