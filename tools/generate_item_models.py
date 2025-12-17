#!/usr/bin/env python3
"""Generate item model JSON files for all WitcherCraft items."""

import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
MODEL_ROOT = ROOT / "src/main/resources/assets/witchercraft/models/item"

# Items that should use "item/handheld" parent (weapons and tools)
HANDHELD_ITEMS = {
    'steel_bastard_sword', 'wolfsteel_longsword', 'tempered_silver_saber', 'griffin_casting_silverblade',
    'skellige_heavy_axe', 'dwarven_carbon_steel_hammer', 'stygga_curved_blade', 'runic_shortspear',
    'viper_duelist_blade', 'northern_reaver_halberd',
    'blade_of_the_leshen_thorn', 'moonlit_veil', 'fenrirs_hunger', 'stormcallers_pike', 'heartfire_cleaver',
    'precision_tempering_hammer', 'runic_engraving_chisel',
}

# All items that need model files
ITEMS = [
    # Potions
    'thunderbolt_potion', 'white_raffard_decoction', 'tawny_owl_potion',
    
    # Herbs
    'celandine',
    
    # Forge components - Pommels
    'dimeritium_pommel', 'meteorite_iron_pommel', 'runestone_encrusted_pommel',
    
    # Grips
    'leather_wrapped_grip', 'wyvern_hide_grip', 'elder_resin_bound_grip',
    
    # Guards
    'cross_steel_guard', 'temerian_forged_guard', 'runic_wolf_guard',
    
    # Blade materials
    'witcher_steel_ingot', 'meteorite_steel_ingot', 'purified_silver_ingot',
    
    # Blade cores
    'dimeritium_core_rod', 'griffin_school_core', 'bear_school_core',
    
    # Forge
    'forging_bill', 'forged_sword_output',
    
    # Monster parts
    'alghoul_bone_marrow', 'leshen_resin_core', 'griffin_feathers', 'basilisk_venom_gland',
    'noonwraith_essence_threads', 'fiend_eye_lens', 'foglet_condensed_mist', 'gargoyle_heartstone',
    
    # Alchemy reagents
    'white_gull', 'dwarven_spirit', 'aether_extract',
    
    # Upgrade components
    'runestone_veles', 'runestone_perun', 'witcher_alloy_plate',
    'runestone_uroboros', 'runestone_aerondight', 'runestone_drakon',
    
    # Metals
    'elder_silver_plate', 'monster_trophy',
    'arachas_hardened_chitin_plate', 'moonsteel_ingot',
    
    # Magical composites
    'etherglass_shard', 'spiritbound_alloy', 'volatile_glowstone_core',
    
    # Woods
    'ancient_yew_timber', 'petrified_spriggan_root', 'wolfsbane_treated_oak',
    
    # Witcher reagents
    'stammelfords_dust', 'mandrake_distillate', 'rebis_crystal', 'vitriol_resin',
    
    # Tools
    'precision_tempering_hammer', 'runic_engraving_chisel', 'cold_quench_flask',
    
    # Armor components
    'laminated_witcher_leather', 'scaled_griffin_padding', 'chromatic_chain_links',
    
    # Powders
    'bonegrind_powder', 'spectral_ash', 'fulminating_ember_dust',
    
    # Infusions
    'igni_fireblood_infusion', 'yrden_geomantic_infusion', 'quen_auric_coating',
    
    # Relic components
    'shattered_relic_sigil', 'elder_blood_trace_sample', 'ancient_draconid_bone',
    
    # Weapons
    'steel_bastard_sword', 'wolfsteel_longsword', 'tempered_silver_saber', 'griffin_casting_silverblade',
    'skellige_heavy_axe', 'dwarven_carbon_steel_hammer', 'stygga_curved_blade', 'runic_shortspear',
    'viper_duelist_blade', 'northern_reaver_halberd',
    'blade_of_the_leshen_thorn', 'moonlit_veil', 'fenrirs_hunger', 'stormcallers_pike', 'heartfire_cleaver',
    
    # Armor
    'witcher_leather_vest', 'wolf_school_gambeson', 'griffin_battlemage_coat', 'bear_reinforced_plate',
    'skellige_raider_jacket', 'viper_duelist_jacket', 'nilfgaardian_scout_brigandine', 'redanian_guard_cuirass',
    'runic_lamellar_harness', 'arachas_chitin_breastplate',
    'eternal_oak_carapace', 'spectral_warden_vestments', 'draconid_scale_mantle', 'witcher_kingslayer_mail',
]

def create_model_json(item_name: str):
    """Create a model JSON file for an item"""
    # Use handheld parent for weapons and tools
    parent = "witchercraft:item/handheld" if item_name in HANDHELD_ITEMS else "witchercraft:item/generated"
    
    model_data = {
        "parent": parent,
        "textures": {
            "layer0": f"witchercraft:item/{item_name}"
        }
    }
    
    model_path = MODEL_ROOT / f"{item_name}.json"
    
    # Skip if already exists
    if model_path.exists():
        print(f"Skipped (exists): {item_name}.json")
        return False
    
    MODEL_ROOT.mkdir(parents=True, exist_ok=True)
    with model_path.open('w', encoding='utf-8') as f:
        json.dump(model_data, f, indent=2)
    
    print(f"Created: {item_name}.json")
    return True

def main():
    created = 0
    skipped = 0
    
    for item in ITEMS:
        if create_model_json(item):
            created += 1
        else:
            skipped += 1
    
    print(f"\nSummary: {created} created, {skipped} skipped")

if __name__ == '__main__':
    main()
