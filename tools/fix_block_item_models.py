"""
Fix block item models to use minecraft:item/generated with layer0 texture
"""

import json
from pathlib import Path

# Base paths
MODELS_ITEM_DIR = Path("src/main/resources/assets/witchercraft/models/item")

# Block items that need fixing
BLOCK_ITEMS = [
    'alchemy_table',
    'grindstone_witcher',
    'kaedweni_notice_board',
    'kaedweni_stone_foundation',
    'reinforced_palisade_log',
    'frostglass_lantern',
    'supply_crate_kaedwen',
    'kaedweni_stone_foundation_slab_mossy',
    'kaedweni_stone_foundation_slab',
    'kaedweni_stone_foundation_mossy',
    'kaedweni_roof_shingles',
    'herbal_drying_rack',
    'gatehouse_portcullis',
    'kaedweni_roof_shingle_stairs',
    'kaedweni_plaster_wall',
    'kaedweni_roof_shingle_vertical_slab',
    'kaedweni_roof_shingle_slab',
    'kaedweni_timber_beam'
]

fixed_count = 0

for block_name in BLOCK_ITEMS:
    model_file = MODELS_ITEM_DIR / f"{block_name}.json"
    
    if not model_file.exists():
        print(f"⚠️  Skipped (doesn't exist): {block_name}")
        continue
    
    # Create the correct model
    model_data = {
        "parent": "minecraft:item/generated",
        "textures": {
            "layer0": f"witchercraft:block/{block_name}"
        }
    }
    
    # Write the file
    with open(model_file, 'w') as f:
        json.dump(model_data, f, indent=2)
    
    print(f"Fixed: {block_name}")
    fixed_count += 1

print(f"\n✅ Fixed {fixed_count} block item models")
