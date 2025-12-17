"""
Fix item models to use minecraft:item/generated and minecraft:item/handheld
instead of the witchercraft namespace (which was wrong).
"""

import json
from pathlib import Path

# Base paths
MODELS_ITEM_DIR = Path("src/main/resources/assets/witchercraft/models/item")

# Handheld items (tools/weapons)
HANDHELD_ITEMS = {
    'silver_sword', 'steel_sword', 'dwarven_carbon_steel_hammer', 'precision_tempering_hammer',
    'heartfire_cleaver', 'steel_bastard_sword', 'viper_duelist_blade', 'tempered_silver_saber',
    'wolfsteel_longsword', 'northern_reaver_halberd', 'skellige_heavy_axe', 'runic_shortspear',
    'stormcallers_pike', 'blade_of_the_leshen_thorn', 'stygga_curved_blade', 'fenrirs_hunger',
    'griffin_casting_silverblade', 'runic_engraving_chisel', 'forging_bill'
}

# Get all item model files
model_files = list(MODELS_ITEM_DIR.glob("*.json"))

# Skip base models
skip_items = {"generated.json", "handheld.json"}

updated_count = 0

for model_file in model_files:
    if model_file.name in skip_items:
        continue
    
    # Read the current model
    with open(model_file, 'r') as f:
        model_data = json.load(f)
    
    # Check if it has the wrong parent
    if model_data.get("parent", "").startswith("witchercraft:"):
        item_name = model_file.stem
        
        # Determine the correct parent
        if item_name in HANDHELD_ITEMS:
            correct_parent = "minecraft:item/handheld"
        else:
            correct_parent = "minecraft:item/generated"
        
        # Update the parent
        model_data["parent"] = correct_parent
        
        # Write back
        with open(model_file, 'w') as f:
            json.dump(model_data, f, indent=2)
        
        print(f"Fixed: {model_file.name} -> {correct_parent}")
        updated_count += 1

print(f"\n✅ Updated {updated_count} item models to use minecraft: namespace")

# Remove the incorrect base model files
base_model_files = [
    MODELS_ITEM_DIR / "generated.json",
    MODELS_ITEM_DIR / "handheld.json"
]

for base_file in base_model_files:
    if base_file.exists():
        base_file.unlink()
        print(f"Removed: {base_file}")
