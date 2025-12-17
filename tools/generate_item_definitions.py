"""
Generate item definition files (items/*.json) for Minecraft 1.21+
This creates the new model format files that reference the classic model files.
"""

import json
from pathlib import Path

# Base paths
ASSETS_DIR = Path("src/main/resources/assets/witchercraft")
ITEMS_DIR = ASSETS_DIR / "items"
MODELS_ITEM_DIR = ASSETS_DIR / "models" / "item"

# Create items directory if it doesn't exist
ITEMS_DIR.mkdir(parents=True, exist_ok=True)

# Get all item model files
model_files = list(MODELS_ITEM_DIR.glob("*.json"))

print(f"Found {len(model_files)} item model files")

# Items to skip (these already exist or are base models)
skip_items = {
    "generated.json",
    "handheld.json",
    # Existing items that already have definitions
    "drowner_brain.json",
    "frostglass_lantern.json",
    "frozen_well_core.json",
    "gatehouse_portcullis.json",
    "herbal_drying_rack.json",
    "kaedweni_notice_board.json",
    "kaedweni_plaster_wall.json",
    "kaedweni_roof_shingles.json",
    "kaedweni_roof_shingle_slab.json",
    "kaedweni_roof_shingle_stairs.json",
    "kaedweni_roof_shingle_vertical_slab.json",
    "kaedweni_soldier_spawn_egg.json",
    "kaedweni_stone_foundation.json",
    "kaedweni_stone_foundation_mossy.json",
    "kaedweni_stone_foundation_slab.json",
    "kaedweni_stone_foundation_slab_mossy.json",
    "kaedweni_timber_beam.json",
    "nilfgaardian_scout_spawn_egg.json",
    "redanian_guard_spawn_egg.json",
    "reinforced_palisade_log.json",
    "supply_crate_kaedwen.json",
    "witcher_forge_block.json",
    # Block items
    "alchemy_table.json",
    "grindstone_witcher.json"
}

created_count = 0

for model_file in model_files:
    item_name = model_file.name
    
    # Skip if in skip list
    if item_name in skip_items:
        continue
    
    # Create the new format item definition
    item_def = {
        "model": {
            "type": "minecraft:model",
            "model": f"witchercraft:item/{model_file.stem}"
        }
    }
    
    # Write the file
    output_file = ITEMS_DIR / item_name
    with open(output_file, 'w') as f:
        json.dump(item_def, f, indent=2)
    
    print(f"Created: {output_file}")
    created_count += 1

print(f"\n✅ Created {created_count} item definition files")
print(f"📁 Location: {ITEMS_DIR}")
