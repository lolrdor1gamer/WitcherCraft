#!/usr/bin/env python3
"""Add missing translation keys to en_us.json."""

import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
LANG_FILE = ROOT / "src/main/resources/assets/witchercraft/lang/en_us.json"

# Translation mappings (item_id: display_name)
NEW_TRANSLATIONS = {
    # Monster parts
    "item.witchercraft.noonwraith_essence_threads": "Noonwraith Essence Threads",
    "item.witchercraft.fiend_eye_lens": "Fiend Eye Lens",
    "item.witchercraft.foglet_condensed_mist": "Foglet Condensed Mist",
    "item.witchercraft.gargoyle_heartstone": "Gargoyle Heartstone",
    
    # Alchemy
    "item.witchercraft.aether_extract": "Aether Extract",
    
    # Upgrade components
    "item.witchercraft.runestone_veles": "Runestone of Veles",
    "item.witchercraft.runestone_perun": "Runestone of Perun",
    "item.witchercraft.witcher_alloy_plate": "Witcher Alloy Plate",
    "item.witchercraft.runestone_uroboros": "Runestone of Uroboros",
    "item.witchercraft.runestone_aerondight": "Runestone of Aerondight",
    "item.witchercraft.runestone_drakon": "Runestone of Drakon",
    
    # Metals and materials
    "item.witchercraft.elder_silver_plate": "Elder Silver Plate",
    "item.witchercraft.monster_trophy": "Monster Trophy",
    "item.witchercraft.arachas_hardened_chitin_plate": "Arachas Hardened Chitin Plate",
    "item.witchercraft.moonsteel_ingot": "Moonsteel Ingot",
    
    # Magical composites
    "item.witchercraft.etherglass_shard": "Etherglass Shard",
    "item.witchercraft.spiritbound_alloy": "Spiritbound Alloy",
    "item.witchercraft.volatile_glowstone_core": "Volatile Glowstone Core",
    
    # Woods
    "item.witchercraft.ancient_yew_timber": "Ancient Yew Timber",
    "item.witchercraft.petrified_spriggan_root": "Petrified Spriggan Root",
    "item.witchercraft.wolfsbane_treated_oak": "Wolfsbane Treated Oak",
    
    # Witcher reagents
    "item.witchercraft.stammelfords_dust": "Stammelford's Dust",
    "item.witchercraft.mandrake_distillate": "Mandrake Distillate",
    "item.witchercraft.rebis_crystal": "Rebis Crystal",
    "item.witchercraft.vitriol_resin": "Vitriol Resin",
    
    # Tools
    "item.witchercraft.precision_tempering_hammer": "Precision Tempering Hammer",
    "item.witchercraft.runic_engraving_chisel": "Runic Engraving Chisel",
    "item.witchercraft.cold_quench_flask": "Cold Quench Flask",
    
    # Armor components
    "item.witchercraft.laminated_witcher_leather": "Laminated Witcher Leather",
    "item.witchercraft.scaled_griffin_padding": "Scaled Griffin Padding",
    "item.witchercraft.chromatic_chain_links": "Chromatic Chain Links",
    
    # Powders
    "item.witchercraft.bonegrind_powder": "Bonegrind Powder",
    "item.witchercraft.spectral_ash": "Spectral Ash",
    "item.witchercraft.fulminating_ember_dust": "Fulminating Ember Dust",
    
    # Infusions
    "item.witchercraft.igni_fireblood_infusion": "Igni Fireblood Infusion",
    "item.witchercraft.yrden_geomantic_infusion": "Yrden Geomantic Infusion",
    "item.witchercraft.quen_auric_coating": "Quen Auric Coating",
    
    # Relic components
    "item.witchercraft.shattered_relic_sigil": "Shattered Relic Sigil",
    "item.witchercraft.elder_blood_trace_sample": "Elder Blood Trace Sample",
    "item.witchercraft.ancient_draconid_bone": "Ancient Draconid Bone",
    
    # Weapons
    "item.witchercraft.steel_bastard_sword": "Steel Bastard Sword",
    "item.witchercraft.wolfsteel_longsword": "Wolfsteel Longsword",
    "item.witchercraft.tempered_silver_saber": "Tempered Silver Saber",
    "item.witchercraft.griffin_casting_silverblade": "Griffin Casting Silverblade",
    "item.witchercraft.skellige_heavy_axe": "Skellige Heavy Axe",
    "item.witchercraft.dwarven_carbon_steel_hammer": "Dwarven Carbon Steel Hammer",
    "item.witchercraft.stygga_curved_blade": "Stygga Curved Blade",
    "item.witchercraft.runic_shortspear": "Runic Shortspear",
    "item.witchercraft.viper_duelist_blade": "Viper Duelist Blade",
    "item.witchercraft.northern_reaver_halberd": "Northern Reaver Halberd",
    "item.witchercraft.blade_of_the_leshen_thorn": "Blade of the Leshen Thorn",
    "item.witchercraft.moonlit_veil": "Moonlit Veil",
    "item.witchercraft.fenrirs_hunger": "Fenrir's Hunger",
    "item.witchercraft.stormcallers_pike": "Stormcaller's Pike",
    "item.witchercraft.heartfire_cleaver": "Heartfire Cleaver",
    
    # Armor
    "item.witchercraft.witcher_leather_vest": "Witcher Leather Vest",
    "item.witchercraft.wolf_school_gambeson": "Wolf School Gambeson",
    "item.witchercraft.griffin_battlemage_coat": "Griffin Battlemage Coat",
    "item.witchercraft.bear_reinforced_plate": "Bear Reinforced Plate",
    "item.witchercraft.skellige_raider_jacket": "Skellige Raider Jacket",
    "item.witchercraft.viper_duelist_jacket": "Viper Duelist Jacket",
    "item.witchercraft.nilfgaardian_scout_brigandine": "Nilfgaardian Scout Brigandine",
    "item.witchercraft.redanian_guard_cuirass": "Redanian Guard Cuirass",
    "item.witchercraft.runic_lamellar_harness": "Runic Lamellar Harness",
    "item.witchercraft.arachas_chitin_breastplate": "Arachas Chitin Breastplate",
    "item.witchercraft.eternal_oak_carapace": "Eternal Oak Carapace",
    "item.witchercraft.spectral_warden_vestments": "Spectral Warden Vestments",
    "item.witchercraft.draconid_scale_mantle": "Draconid Scale Mantle",
    "item.witchercraft.witcher_kingslayer_mail": "Witcher Kingslayer Mail",
}

def main():
    # Load existing translations
    with LANG_FILE.open('r', encoding='utf-8') as f:
        translations = json.load(f)
    
    # Add new translations
    added = 0
    for key, value in NEW_TRANSLATIONS.items():
        if key not in translations:
            translations[key] = value
            added += 1
            print(f"Added: {key}")
        else:
            print(f"Skipped (exists): {key}")
    
    # Save with sorted keys
    with LANG_FILE.open('w', encoding='utf-8') as f:
        json.dump(translations, f, indent=2, ensure_ascii=False, sort_keys=True)
        f.write('\n')  # Add newline at end
    
    print(f"\nAdded {added} new translations")

if __name__ == '__main__':
    main()
