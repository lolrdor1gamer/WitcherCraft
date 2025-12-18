package org.tgr.witchercraft.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.item.WitcherItem;
import org.tgr.witchercraft.item.WitcherPotionItem;
import org.tgr.witchercraft.item.WitcherSwordItem;
import org.tgr.witchercraft.item.component.*;
import org.tgr.witchercraft.item.oil.BladeOilItem;
import org.tgr.witchercraft.item.bomb.BombType;
import org.tgr.witchercraft.item.bomb.WitcherBombItem;
import org.tgr.witchercraft.entity.monster.AbstractWitcherMonster;
import org.tgr.witchercraft.player.WitcherPlayerData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Item registry for WitcherCraft mod following the Fabric 1.21.1+ requirements.
 * Items must have their registry ID set on the {@link Item.Properties} before instantiation.
 */
public final class ModItems {

    private static final List<ResourceLocation> REGISTERED_IDS = new ArrayList<>();

    public static final Item SILVER_SWORD = register("silver_sword",
        properties -> new WitcherSwordItem(properties, WitcherSwordItem.SwordType.SILVER, 8.0f, -2.4f, 650));
    public static final Item STEEL_SWORD = register("steel_sword",
        properties -> new WitcherSwordItem(properties, WitcherSwordItem.SwordType.STEEL, 7.0f, -2.4f, 850));
    public static final Item SWALLOW_POTION = register("swallow_potion", properties -> new WitcherPotionItem(properties.stacksTo(16)
        .rarity(Rarity.UNCOMMON)
        .food(new FoodProperties(0, 0.0F, false), Consumable.builder()
            .consumeSeconds(2.4F)
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * 30, 1)))
            .build()), "tooltip.witchercraft.potion.swallow", 15, null));
    public static final Item CAT_POTION = register("cat_potion", properties -> new WitcherPotionItem(properties.stacksTo(16)
        .rarity(Rarity.UNCOMMON)
        .food(new FoodProperties(0, 0.0F, false), Consumable.builder()
            .consumeSeconds(2.4F)
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20 * 120, 0)))
            .build()), "tooltip.witchercraft.potion.cat", 10, null));
    public static final Item THUNDERBOLT_POTION = register("thunderbolt_potion", properties -> new WitcherPotionItem(properties.stacksTo(16)
        .rarity(Rarity.RARE)
        .food(new FoodProperties(0, 0.0F, false), Consumable.builder()
            .consumeSeconds(2.4F)
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.STRENGTH, 20 * 90, 1)))
            .build()), "tooltip.witchercraft.potion.thunderbolt", 25, null));
    public static final Item WHITE_RAFFARD_DECOCTION = register("white_raffard_decoction", properties -> new WitcherPotionItem(properties.stacksTo(16)
        .rarity(Rarity.RARE)
        .food(new FoodProperties(0, 0.0F, false), Consumable.builder()
            .consumeSeconds(2.4F)
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * 10, 2)))
            .build()), "tooltip.witchercraft.potion.white_raffard", 35,
        (player, level) -> {
            player.heal(player.getMaxHealth());
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 20 * 45, 1, false, true));
        }));
    public static final Item TAWNY_OWL_POTION = register("tawny_owl_potion", properties -> new WitcherPotionItem(properties.stacksTo(16)
        .rarity(Rarity.RARE)
        .food(new FoodProperties(0, 0.0F, false), Consumable.builder()
            .consumeSeconds(2.4F)
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 20 * 90, 0)))
            .build()), "tooltip.witchercraft.potion.tawny_owl", 20,
        (player, level) -> {
            WitcherPlayerData.applyStaminaRegenBoost(player, 20 * 120, 2);
            WitcherPlayerData.applyManaRegenBoost(player, 20 * 120, 1);
        }));
    // ========================================
    // ALCHEMY HERBS
    // ========================================
    public static final Item CELANDINE = register("celandine", WitcherItem::new);
    public static final Item CROWS_EYE = register("crows_eye", WitcherItem::new);
    public static final Item BLOWBALL = register("blowball", WitcherItem::new);
    public static final Item WHITE_MYRTLE = register("white_myrtle", properties -> new WitcherItem(properties.rarity(Rarity.UNCOMMON)));
    public static final Item WOLFSBANE = register("wolfsbane", WitcherItem::new);
    public static final Item MANDRAKE_ROOT = register("mandrake_root", properties -> new WitcherItem(properties.rarity(Rarity.UNCOMMON)));
    public static final Item SEWANT_MUSHROOM = register("sewant_mushroom", WitcherItem::new);
    public static final Item VERBENA = register("verbena", WitcherItem::new);
    public static final Item FOOLS_PARSLEY = register("fools_parsley", WitcherItem::new);
    public static final Item BERBERCANE_FRUIT = register("berbercane_fruit", WitcherItem::new);
    public static final Item WORMWOOD = register("wormwood", WitcherItem::new);
    public static final Item HAN = register("han", properties -> new WitcherItem(properties.rarity(Rarity.RARE)));
    
    // Monster Drops
    public static final Item DROWNER_BRAIN = register("drowner_brain", properties -> new WitcherItem(properties.stacksTo(64).food(new FoodProperties(-2, -2, true), Consumable.builder().consumeSeconds(2).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.POISON, 100, 1))).build()).rarity(Rarity.UNCOMMON)));

    // ========================================
    // CUSTOM ORE MATERIALS (Raw Ores & Ingots)
    // ========================================
    
    // Silver
    public static final Item RAW_SILVER = register("raw_silver", WitcherItem::new);
    public static final Item SILVER_INGOT = register("silver_ingot", WitcherItem::new);
    
    // Meteorite
    public static final Item RAW_METEORITE = register("raw_meteorite", properties -> new WitcherItem(properties.rarity(Rarity.RARE)));
    public static final Item METEORITE_INGOT = register("meteorite_ingot", properties -> new WitcherItem(properties.rarity(Rarity.RARE)));
    
    // Dark Iron
    public static final Item RAW_DARK_IRON = register("raw_dark_iron", WitcherItem::new);
    public static final Item DARK_IRON_INGOT = register("dark_iron_ingot", WitcherItem::new);
    
    // Dimeritium
    public static final Item RAW_DIMERITIUM = register("raw_dimeritium", properties -> new WitcherItem(properties.rarity(Rarity.UNCOMMON)));
    public static final Item DIMERITIUM_INGOT = register("dimeritium_ingot", properties -> new WitcherItem(properties.rarity(Rarity.UNCOMMON)));

    // Humanoid spawn eggs
    public static final Item KAEDWENI_SOLDIER_SPAWN_EGG = registerSpawnEgg(
        "kaedweni_soldier_spawn_egg", ModEntities.KAEDWENI_SOLDIER, Rarity.COMMON);
    public static final Item REDANIAN_GUARD_SPAWN_EGG = registerSpawnEgg(
        "redanian_guard_spawn_egg", ModEntities.REDANIAN_GUARD, Rarity.COMMON);
    public static final Item NILFGAARDIAN_SCOUT_SPAWN_EGG = registerSpawnEgg(
        "nilfgaardian_scout_spawn_egg", ModEntities.NILFGAARDIAN_SCOUT, Rarity.COMMON);

    // Monster spawn eggs
    public static final Item DROWNED_CORPSE_SPAWN_EGG = registerSpawnEgg(
        "drowned_corpse_spawn_egg", ModEntities.DROWNED_CORPSE, Rarity.COMMON);
    public static final Item NEKKER_SPAWN_EGG = registerSpawnEgg(
        "nekker_spawn_egg", ModEntities.NEKKER, Rarity.COMMON);
    public static final Item GHOUL_SPAWN_EGG = registerSpawnEgg(
        "ghoul_spawn_egg", ModEntities.GHOUL, Rarity.COMMON);
    public static final Item ALGHOUL_SPAWN_EGG = registerSpawnEgg(
        "alghoul_spawn_egg", ModEntities.ALGHOUL, Rarity.UNCOMMON);
    public static final Item WRAITH_SPAWN_EGG = registerSpawnEgg(
        "wraith_spawn_egg", ModEntities.WRAITH, Rarity.UNCOMMON);
    public static final Item WEREWOLF_SPAWN_EGG = registerSpawnEgg(
        "werewolf_spawn_egg", ModEntities.WEREWOLF, Rarity.RARE);

    // Blade Oils
    public static final Item NECROPHAGE_OIL = register("necrophage_oil", properties ->
        new BladeOilItem(properties.stacksTo(16).rarity(Rarity.UNCOMMON), 
            AbstractWitcherMonster.MonsterCategory.NECROPHAGE, 1.5f, 20));
    public static final Item SPECTER_OIL = register("specter_oil", properties ->
        new BladeOilItem(properties.stacksTo(16).rarity(Rarity.UNCOMMON), 
            AbstractWitcherMonster.MonsterCategory.SPECTER, 1.5f, 20));
    public static final Item CURSED_OIL = register("cursed_oil", properties ->
        new BladeOilItem(properties.stacksTo(16).rarity(Rarity.UNCOMMON), 
            AbstractWitcherMonster.MonsterCategory.CURSED, 1.5f, 20));
    public static final Item BEAST_OIL = register("beast_oil", properties ->
        new BladeOilItem(properties.stacksTo(16).rarity(Rarity.UNCOMMON), 
            AbstractWitcherMonster.MonsterCategory.BEAST, 1.5f, 20));
    public static final Item HYBRID_OIL = register("hybrid_oil", properties ->
        new BladeOilItem(properties.stacksTo(16).rarity(Rarity.UNCOMMON), 
            AbstractWitcherMonster.MonsterCategory.HYBRID, 1.5f, 20));
    public static final Item INSECTOID_OIL = register("insectoid_oil", properties ->
        new BladeOilItem(properties.stacksTo(16).rarity(Rarity.UNCOMMON), 
            AbstractWitcherMonster.MonsterCategory.INSECTOID, 1.5f, 20));
    public static final Item VAMPIRE_OIL = register("vampire_oil", properties ->
        new BladeOilItem(properties.stacksTo(16).rarity(Rarity.RARE), 
            AbstractWitcherMonster.MonsterCategory.VAMPIRE, 1.5f, 20));
    public static final Item RELICT_OIL = register("relict_oil", properties ->
        new BladeOilItem(properties.stacksTo(16).rarity(Rarity.RARE), 
            AbstractWitcherMonster.MonsterCategory.RELICT, 1.5f, 20));

    // Witcher Bombs
    public static final Item GRAPESHOT_BOMB = register("grapeshot_bomb", properties ->
        new WitcherBombItem(properties.stacksTo(10).rarity(Rarity.UNCOMMON), BombType.GRAPESHOT));
    public static final Item DANCING_STAR_BOMB = register("dancing_star_bomb", properties ->
        new WitcherBombItem(properties.stacksTo(10).rarity(Rarity.UNCOMMON), BombType.DANCING_STAR));
    public static final Item DEVILS_PUFFBALL_BOMB = register("devils_puffball_bomb", properties ->
        new WitcherBombItem(properties.stacksTo(10).rarity(Rarity.UNCOMMON), BombType.DEVILS_PUFFBALL));
    public static final Item SAMUM_BOMB = register("samum_bomb", properties ->
        new WitcherBombItem(properties.stacksTo(10).rarity(Rarity.UNCOMMON), BombType.SAMUM));
    public static final Item NORTHERN_WIND_BOMB = register("northern_wind_bomb", properties ->
        new WitcherBombItem(properties.stacksTo(10).rarity(Rarity.RARE), BombType.NORTHERN_WIND));
    public static final Item DIMERITIUM_BOMB = register("dimeritium_bomb", properties ->
        new WitcherBombItem(properties.stacksTo(10).rarity(Rarity.RARE), BombType.DIMERITIUM));
    public static final Item DRAGONS_DREAM_BOMB = register("dragons_dream_bomb", properties ->
        new WitcherBombItem(properties.stacksTo(10).rarity(Rarity.RARE), BombType.DRAGONS_DREAM));
    public static final Item MOON_DUST_BOMB = register("moon_dust_bomb", properties ->
        new WitcherBombItem(properties.stacksTo(10).rarity(Rarity.UNCOMMON), BombType.MOON_DUST));

    // Forge components - Pommel
    public static final Item DIMERITIUM_POMMEL = registerComponent("dimeritium_pommel", Rarity.RARE, PommelItem::new,
        "Pommel", "Forged from dimeritium; cancels magical resonance.", "+5% durability, reduces magical damage taken by 10%.", "Steel/silver sword pommel slot.");
    public static final Item METEORITE_IRON_POMMEL = registerComponent("meteorite_iron_pommel", Rarity.UNCOMMON, PommelItem::new,
        "Pommel", "Pommel made of enchanted meteorite alloy.", "+5% crit chance.", "Steel sword forging.");
    public static final Item RUNESTONE_ENCRUSTED_POMMEL = registerComponent("runestone_encrusted_pommel", Rarity.EPIC, PommelItem::new,
        "Pommel", "Holds runic channels for enhanced binding.", "Adds runic slot.", "High-tier swords.");

    // Grip
    public static final Item LEATHER_WRAPPED_GRIP = registerComponent("leather_wrapped_grip", Rarity.COMMON, GripItem::new,
        "Grip", "Traditional grip wrapped in cured leather strips.", "+3% durability.", "Base grip for all swords.");
    public static final Item WYVERN_HIDE_GRIP = registerComponent("wyvern_hide_grip", Rarity.RARE, GripItem::new,
        "Grip", "Wrapped in wyvern hide; shock-absorbing.", "+10% stamina regeneration on hit.", "Improved grip component.");
    public static final Item ELDER_RESIN_BOUND_GRIP = registerComponent("elder_resin_bound_grip", Rarity.EPIC, GripItem::new,
        "Grip", "Infused with elder tree resin, extremely stable.", "+5% handling, +5% crit.", "Elite swords.");

    // Guard
    public static final Item CROSS_STEEL_GUARD = registerComponent("cross_steel_guard", Rarity.COMMON, GuardItem::new,
        "Guard", "Standard crossguard for most witcher swords.", "Slight parry bonus.", "Base guard.");
    public static final Item TEMERIAN_FORGED_GUARD = registerComponent("temerian_forged_guard", Rarity.UNCOMMON, GuardItem::new,
        "Guard", "Guard shaped by Temerian master smiths.", "+5% parry, +2% crit.", "Steel swords.");
    public static final Item RUNIC_WOLF_GUARD = registerComponent("runic_wolf_guard", Rarity.EPIC, GuardItem::new,
        "Guard", "Wolf school guard etched with runes.", "+10% parry, +5% attack speed.", "Wolf school weapons.");

    // Blade material
    public static final Item STEEL_INGOT = registerComponent("witcher_steel_ingot", Rarity.COMMON, BladeMaterialItem::new,
        "Blade Material", "Standard steel used for lightweight blades.", "Normal base damage.", "Steel sword forging.");
    public static final Item METEORITE_STEEL_INGOT = registerComponent("meteorite_steel_ingot", Rarity.RARE, BladeMaterialItem::new,
        "Blade Material", "Three-metal alloy used by witchers.", "+10% base damage.", "High-tier steel swords.");
    public static final Item PURIFIED_SILVER_INGOT = registerComponent("purified_silver_ingot", Rarity.RARE, BladeMaterialItem::new,
        "Blade Material", "Silver refined for slaying monsters.", "+20% monster damage.", "Silver sword forging.");

    // Blade core
    public static final Item DIMERITIUM_CORE_ROD = registerComponent("dimeritium_core_rod", Rarity.EPIC, BladeCoreItem::new,
        "Blade Core", "Core that provides anti-magic properties.", "Negates magical resistance, +10% armor penetration.", "Silver swords, anti-mage weapons.");
    public static final Item GRIFFIN_SCHOOL_CORE = registerComponent("griffin_school_core", Rarity.RARE, BladeCoreItem::new,
        "Blade Core", "Balanced, magic-conductive core.", "+10% sign intensity.", "Griffin school swords.");
    public static final Item BEAR_SCHOOL_CORE = registerComponent("bear_school_core", Rarity.RARE, BladeCoreItem::new,
        "Blade Core", "Heavy iron-based core.", "+15% stagger on heavy attacks.", "Bear school swords.");

    public static final Item FORGING_BILL = registerComponent("forging_bill", Rarity.COMMON, 32, BillItem::new,
        "Bill Slot", "A slip marking a work order; used to finalize forging in your UI.", "None", "Required to start crafting process.");
    public static final Item FORGED_SWORD_OUTPUT = registerComponent("forged_sword_output", Rarity.UNCOMMON, 1, ForgeOutputItem::new,
        "Output", "Final crafted weapon.", "Depends on inserted components.", "Result of crafting.");

    // Monster parts & alchemy reagents
    public static final Item ALGHOUL_BONE_MARROW = registerComponent("alghoul_bone_marrow", Rarity.UNCOMMON, MonsterPartItem::new,
        "Monster Part", "Extracted from enhanced ghouls.", "Used in armor reinforcement.", "Oils, armor upgrades.");
    public static final Item LESHEN_RESIN_CORE = registerComponent("leshen_resin_core", Rarity.RARE, MonsterPartItem::new,
        "Monster Part", "Semi-living wood from a leshen.", "+Magic damage, nature affinity.", "High-tier upgrades.");
    public static final Item GRIFFIN_FEATHERS = registerComponent("griffin_feathers", Rarity.UNCOMMON, MonsterPartItem::new,
        "Monster Part", "Magical feathers emitting static charge.", "+Sign power.", "Griffin school items.");
    public static final Item BASILISK_VENOM_GLAND = registerComponent("basilisk_venom_gland", Rarity.EPIC, MonsterPartItem::new,
        "Monster Part", "Deadliest venom known to witchers.", "25% poison effect on weapons.", "Potion brewing, blade oils.");

    public static final Item WHITE_GULL = registerComponent("white_gull", Rarity.RARE, AlchemyReagentItem::new,
        "Alchemy", "A clear alcohol required for strong potions.", "Alchemy catalyst.", "Crafting decoctions.");
    public static final Item DWARVEN_SPIRIT = registerComponent("dwarven_spirit", Rarity.COMMON, AlchemyReagentItem::new,
        "Alchemy", "Cheap alcohol for standard potions.", "Minor catalyst.", "Crafting lesser oils.");
    public static final Item AETHER_EXTRACT = registerComponent("aether_extract", Rarity.RARE, AlchemyReagentItem::new,
        "Alchemy", "Extracted from magical anomalies.", "Used in sign-boosting potions.", "Potions and runes.");

    public static final Item RUNESTONE_VELES = registerComponent("runestone_veles", Rarity.UNCOMMON, UpgradeComponentItem::new,
        "Upgrade Component", "Rune improving sign intensity.", "+10% sign intensity.", "Weapon/armor rune slot.");
    public static final Item RUNESTONE_PERUN = registerComponent("runestone_perun", Rarity.UNCOMMON, UpgradeComponentItem::new,
        "Upgrade Component", "Lightning rune.", "+10% crit damage.", "Weapon rune.");
    public static final Item WITCHER_ALLOY_PLATE = registerComponent("witcher_alloy_plate", Rarity.RARE, UpgradeComponentItem::new,
        "Upgrade Component", "Reinforced metal plate.", "+15% durability boost.", "Armor crafting.");

    public static final Item ELDER_SILVER_PLATE = registerComponent("elder_silver_plate", Rarity.EPIC, IngotItem::new,
        "Ingot", "Silver blessed by elder druids.", "+Monster damage, +magic damage.", "Silver swords, artifacts.");
    public static final Item MONSTER_TROPHY = registerComponent("monster_trophy", Rarity.RARE, MiscCurioItem::new,
        "Misc", "Trophy granting various bonuses.", "Depends on monster.", "Saddle bags, trinkets, upgrades.");

    // Advanced materials
    public static final Item ARACHAS_HARDENED_CHITIN_PLATE = registerComponent("arachas_hardened_chitin_plate", Rarity.RARE, RareMetalItem::new,
        "Rare Metal", "Ultra-rigid chitin plate melted and reforged with iron dust.", "+Armor penetration.", "Blade reinforcement, armor crafting.");
    public static final Item MOONSTEEL_INGOT = registerComponent("moonsteel_ingot", Rarity.EPIC, RareMetalItem::new,
        "Rare Metal", "Silver-steel hybrid forged under moonlight rituals.", "+15% damage vs spectral enemies.", "High-tier silver weapons.");

    public static final Item ETHERGLASS_SHARD = registerComponent("etherglass_shard", Rarity.RARE, MagicalCompositeItem::new,
        "Magical Composite", "Transparent crystalline substance made from condensed Aether.", "+Mana conductivity.", "Sign-focused upgrades.");
    public static final Item SPIRITBOUND_ALLOY = registerComponent("spiritbound_alloy", Rarity.EPIC, MagicalCompositeItem::new,
        "Magical Composite", "Metal fused with spirit essence during forging.", "+Life steal (5%).", "Relic weapons.");
    public static final Item VOLATILE_GLOWSTONE_CORE = registerComponent("volatile_glowstone_core", Rarity.RARE, MagicalCompositeItem::new,
        "Magical Composite", "Light-emitting mineral pulsating with heat.", "+Fire damage.", "Elemental weapons.");

    public static final Item NOONWRAITH_ESSENCE_THREADS = registerComponent("noonwraith_essence_threads", Rarity.RARE, MonsterPartItem::new,
        "Monster Part", "Wispy ghost threads gathered during the wraith’s dispersion.", "+Specter bonus damage.", "Oils, relic sword cores.");
    public static final Item FIEND_EYE_LENS = registerComponent("fiend_eye_lens", Rarity.RARE, MonsterPartItem::new,
        "Monster Part", "Reflective, dense eye lens used in magical optics.", "+Critical chance under low light.", "Alchemy, scopes, armor upgrades.");
    public static final Item FOGLET_CONDENSED_MIST = registerComponent("foglet_condensed_mist", Rarity.EPIC, MonsterPartItem::new,
        "Monster Part", "A jar of dense mist imbued with illusion magic.", "+Evasion, blink chance.", "Cloaks, stealth upgrades.");
    public static final Item GARGOYLE_HEARTSTONE = registerComponent("gargoyle_heartstone", Rarity.RARE, MonsterPartItem::new,
        "Monster Part", "Mineral heart filled with arcane energy.", "+Magic resistance.", "Runic infusions.");

    public static final Item ANCIENT_YEW_TIMBER = registerComponent("ancient_yew_timber", Rarity.UNCOMMON, SpecialWoodItem::new,
        "Special Wood", "Flexible but tough wood used by druids.", "Improving handling.", "Crafting grips/handles.");
    public static final Item PETRIFIED_SPRIGGAN_ROOT = registerComponent("petrified_spriggan_root", Rarity.RARE, SpecialWoodItem::new,
        "Special Wood", "Wood turned to stone by curse.", "+Armor & stun resistance.", "Armor reinforcements.");
    public static final Item WOLFSBANE_TREATED_OAK = registerComponent("wolfsbane_treated_oak", Rarity.UNCOMMON, SpecialWoodItem::new,
        "Special Wood", "Oak soaked in wolfsbane brew.", "+5% bonus damage vs cursed creatures.", "Weapon handles.");

    public static final Item STAMMELFORDS_DUST = registerComponent("stammelfords_dust", Rarity.RARE, WitcherReagentItem::new,
        "Witcher Reagent", "Magical dust used in advanced enchantments.", "+Sign intensity.", "Runes, alchemy.");
    public static final Item MANDRAKE_DISTILLATE = registerComponent("mandrake_distillate", Rarity.RARE, WitcherReagentItem::new,
        "Witcher Reagent", "Potent extract used by witchers to stabilize infusions.", "Enhances potion intensity.", "Alchemy.");
    public static final Item REBIS_CRYSTAL = registerComponent("rebis_crystal", Rarity.RARE, WitcherReagentItem::new,
        "Witcher Reagent", "Transmutation ingredient symbolizing unity.", "Boosts general weapon stats.", "Infusions.");
    public static final Item VITRIOL_RESIN = registerComponent("vitriol_resin", Rarity.UNCOMMON, WitcherReagentItem::new,
        "Witcher Reagent", "Resin infused with corrosive essence.", "+Armor shredding properties.", "Blade oils, metal treatment.");

    public static final Item PRECISION_TEMPERING_HAMMER = registerComponent("precision_tempering_hammer", Rarity.COMMON, AuxiliaryToolItem::new,
        "Auxiliary Tool", "Tool that allows precise shaping.", "Reduces forging time by 15% (mechanical effect).", "Crafting station accessory.");
    public static final Item RUNIC_ENGRAVING_CHISEL = registerComponent("runic_engraving_chisel", Rarity.UNCOMMON, AuxiliaryToolItem::new,
        "Auxiliary Tool", "Tool used to etch runes into metal.", "Unlocks rune integration.", "Forging process.");
    public static final Item COLD_QUENCH_FLASK = registerComponent("cold_quench_flask", Rarity.RARE, AuxiliaryToolItem::new,
        "Auxiliary Tool", "Flask with alchemical quenching fluid.", "+Durability bonus to forged weapons.", "Crafting catalyst.");

    public static final Item LAMINATED_WITCHER_LEATHER = registerComponent("laminated_witcher_leather", Rarity.UNCOMMON, ArmorComponentItem::new,
        "Armor Component", "Enhanced leather treated with rare oils.", "+Stamina regeneration.", "Armor crafting.");
    public static final Item SCALED_GRIFFIN_PADDING = registerComponent("scaled_griffin_padding", Rarity.RARE, ArmorComponentItem::new,
        "Armor Component", "Lightweight but resilient layered padding.", "+Spell resistance.", "Griffin school armor.");
    public static final Item CHROMATIC_CHAIN_LINKS = registerComponent("chromatic_chain_links", Rarity.RARE, ArmorComponentItem::new,
        "Armor Component", "Magical chainmail links shifting color.", "+Elemental resistance.", "High-tier armor.");

    public static final Item RUNESTONE_UROBOROS = registerComponent("runestone_uroboros", Rarity.EPIC, RuneItem::new,
        "Rune", "Symbol of eternal cycle, extremely rare.", "+Regeneration (health & stamina).", "Weapon/armor enchant.");
    public static final Item RUNESTONE_AERONDIGHT = registerComponent("runestone_aerondight", Rarity.EPIC, RuneItem::new,
        "Rune", "Fragment of the Aerondight’s runic signature.", "Increases damage with every hit landed.", "End-game weapons.");
    public static final Item RUNESTONE_DRAKON = registerComponent("runestone_drakon", Rarity.RARE, RuneItem::new,
        "Rune", "Dragon-themed rune emitting kinetic pulses.", "+Knockback & stagger chance.", "Heavy weapons.");

    public static final Item BONEGRIND_POWDER = registerComponent("bonegrind_powder", Rarity.COMMON, PowderItem::new,
        "Powder", "Crushed monster bones used for abrasives.", "Slight crit improvement.", "Weapon polishing.");
    public static final Item SPECTRAL_ASH = registerComponent("spectral_ash", Rarity.RARE, PowderItem::new,
        "Powder", "Ash remaining after spirit banishment.", "Increases spectral damage.", "Oils & enchantments.");
    public static final Item FULMINATING_EMBER_DUST = registerComponent("fulminating_ember_dust", Rarity.RARE, PowderItem::new,
        "Powder", "Ember powder used for volatile enchantments.", "Adds chance for explosive hit.", "Special weapons.");

    public static final Item IGNI_FIREBLOOD_INFUSION = registerComponent("igni_fireblood_infusion", Rarity.RARE, InfusionItem::new,
        "Infusion", "Mixture that grants weapons fire affinity.", "+Fire damage.", "Forge-based application.");
    public static final Item YRDEN_GEOMANTIC_INFUSION = registerComponent("yrden_geomantic_infusion", Rarity.RARE, InfusionItem::new,
        "Infusion", "Enhances spatial binding of metals.", "+Control effects (slow/stagger).", "Rune crafting.");
    public static final Item QUEN_AURIC_COATING = registerComponent("quen_auric_coating", Rarity.RARE, InfusionItem::new,
        "Infusion", "Golden alchemical layer reducing damage taken.", "+Flat armor.", "Armor upgrades.");

    public static final Item SHATTERED_RELIC_SIGIL = registerComponent("shattered_relic_sigil", Rarity.EPIC, RelicComponentItem::new,
        "Relic Component", "Fragment of a relic weapon’s binding seal.", "+Scaling based on player health.", "Relic-tier weapons.");
    public static final Item ELDER_BLOOD_TRACE_SAMPLE = registerComponent("elder_blood_trace_sample", Rarity.EPIC, RelicComponentItem::new,
        "Relic Component", "Extremely rare; sample of latent Elder Blood.", "Speeds up crafting, boosts all stats.", "Ultra-rare crafting.");
    public static final Item ANCIENT_DRACONID_BONE = registerComponent("ancient_draconid_bone", Rarity.EPIC, RelicComponentItem::new,
        "Relic Component", "Dense bone from primordial draconids.", "+Heavy attack bonus.", "High-tier forging.");

    // Weapons
    public static final Item STEEL_BASTARD_SWORD = registerWeapon("steel_bastard_sword", Rarity.COMMON, "Steel Sword",
        "A versatile long steel sword used by witchers-in-training.", "Damage 7 | +1 reach", "Starter tier",
        7.0f, -2.5f, 600);
    public static final Item WOLFSTEEL_LONGSWORD = registerWeapon("wolfsteel_longsword", Rarity.UNCOMMON, "Steel Sword",
        "Forged following Wolf School principles; balanced weight.", "Damage 9 | +5% crit", "Tier 1",
        9.0f, -2.4f, 750);
    public static final Item TEMPERED_SILVER_SABER = registerWeapon("tempered_silver_saber", Rarity.UNCOMMON, "Silver Sword",
        "Light silver blade crafted for faster spectral engagements.", "Damage 6 | +10% vs ghosts", "Tier 1",
        6.0f, -2.3f, 750);
    public static final Item GRIFFIN_CASTING_SILVERBLADE = registerWeapon("griffin_casting_silverblade", Rarity.RARE, "Silver Sword",
        "Reinforced with magical alloys from Griffin School designs.", "Damage 8 | +10% sign intensity", "Tier 2",
        8.0f, -2.3f, 900);
    public static final Item SKELLIGE_HEAVY_AXE = registerWeapon("skellige_heavy_axe", Rarity.COMMON, "Axe",
        "Brutal axe forged in northern island style.", "Damage 10 | +Knockback", "Non-witcher melee weapon",
        10.0f, -3.2f, 600);
    public static final Item DWARVEN_CARBON_STEEL_HAMMER = registerWeapon("dwarven_carbon_steel_hammer", Rarity.RARE, "Warhammer",
        "Dense hammerhead made by dwarven smiths.", "Damage 12 | +Armor shred", "Strength-focused tier",
        12.0f, -3.4f, 900);
    public static final Item STYGGA_CURVED_BLADE = registerWeapon("stygga_curved_blade", Rarity.RARE, "Exotic Sword",
        "Curved sword influenced by desert nomads.", "Damage 9 | +10% bleed", "Tier 2",
        9.0f, -2.2f, 900);
    public static final Item RUNIC_SHORTSPEAR = registerWeapon("runic_shortspear", Rarity.UNCOMMON, "Spear",
        "Lightweight spear reinforced with runic wood.", "Damage 8 | +Range", "Mobility tier",
        8.0f, -2.8f, 750);
    public static final Item VIPER_DUELIST_BLADE = registerWeapon("viper_duelist_blade", Rarity.UNCOMMON, "Steel Sword",
        "Thin, venom-coated blade for precise hits.", "Damage 7 | +Poison chance", "Viper school tier",
        7.0f, -2.5f, 750);
    public static final Item NORTHERN_REAVER_HALBERD = registerWeapon("northern_reaver_halberd", Rarity.RARE, "Halberd",
        "Hybrid polearm for heavy battlefield use.", "Damage 11 | +Stagger", "Heavy-tier weapons",
        11.0f, -3.1f, 900);

    public static final Item BLADE_OF_THE_LESHEN_THORN = registerWeapon("blade_of_the_leshen_thorn", Rarity.EPIC, "Silver Sword",
        "Bound with forest magic and grown through ritual sap.", "Damage 10 | +Nature damage, roots enemies (1s)", "Druid/forest-oriented",
        10.0f, -2.3f, 1200);
    public static final Item MOONLIT_VEIL = registerWeapon("moonlit_veil", Rarity.EPIC, "Silver Sword",
        "Materialized from moonsteel under a full lunar cycle.", "Damage 9 | +20% vs wraiths, +5% lifesteal", "Spectral-focused",
        9.0f, -2.3f, 1100);
    public static final Item FENRIRS_HUNGER = registerWeapon("fenrirs_hunger", Rarity.EPIC, "Steel Sword",
        "Wolf School lost relic forged for predator-style combat.", "Damage 12 | +Damage on consecutive hits", "Aggressive scaling weapon",
        12.0f, -2.4f, 1200);
    public static final Item STORMCALLERS_PIKE = registerWeapon("stormcallers_pike", Rarity.EPIC, "Spear",
        "Crackles with storm elemental energy.", "Damage 11 | +Lightning damage, chain hit", "Long-range weapon",
        11.0f, -2.7f, 1200);
    public static final Item HEARTFIRE_CLEAVER = registerWeapon("heartfire_cleaver", Rarity.EPIC, "Great Axe",
        "Forged around a living ember; the blade glows with heat.", "Damage 14 | +Burn, +Durability", "High-strength build",
        14.0f, -3.3f, 1100);

    // Armor concepts
    public static final Item WITCHER_LEATHER_VEST = registerArmor("witcher_leather_vest", Rarity.COMMON, "Medium Armor",
        "Basic witcher training armor.", "Armor 3 | +Stamina regen", "Starter", 640);
    public static final Item WOLF_SCHOOL_GAMBESON = registerArmor("wolf_school_gambeson", Rarity.UNCOMMON, "Light Armor",
        "Padded wolf-patterned underarmor.", "Armor 4 | +5% crit", "Light builds", 720);
    public static final Item GRIFFIN_BATTLEMAGE_COAT = registerArmor("griffin_battlemage_coat", Rarity.RARE, "Medium Armor",
        "Reinforced coat used by sign-focused witchers.", "Armor 5 | +Sign intensity", "Sign builds", 800);
    public static final Item BEAR_REINFORCED_PLATE = registerArmor("bear_reinforced_plate", Rarity.RARE, "Heavy Armor",
        "Thick metal plating typically used by Bear School.", "Armor 8 | +Knockback resist", "Heavy builds", 1040);
    public static final Item SKELLIGE_RAIDER_JACKET = registerArmor("skellige_raider_jacket", Rarity.COMMON, "Medium Armor",
        "Fur-lined raider armor for cold climates.", "Armor 4 | +Cold resist", "Survival gear", 720);
    public static final Item VIPER_DUELIST_JACKET = registerArmor("viper_duelist_jacket", Rarity.UNCOMMON, "Light Armor",
        "Sleek leather coat prioritizing mobility.", "Armor 3 | +Poison resist", "Rogue builds", 640);
    public static final Item NILFGAARDIAN_SCOUT_BRIGANDINE = registerArmor("nilfgaardian_scout_brigandine", Rarity.UNCOMMON, "Medium Armor",
        "Military brigandine with metal inserts.", "Armor 5 | +10% ranged resist", "Balanced tier", 800);
    public static final Item REDANIAN_GUARD_CUIRASS = registerArmor("redanian_guard_cuirass", Rarity.UNCOMMON, "Heavy Armor",
        "Guard-issued armor that is well-built and durable.", "Armor 6 | +Stagger resist", "Early defense", 880);
    public static final Item RUNIC_LAMELLAR_HARNESS = registerArmor("runic_lamellar_harness", Rarity.RARE, "Medium Armor",
        "Lamellar plated armor reinforced with runestones.", "Armor 6 | +Elemental resist", "Magic defenses", 880);
    public static final Item ARACHAS_CHITIN_BREASTPLATE = registerArmor("arachas_chitin_breastplate", Rarity.RARE, "Heavy Armor",
        "Crafted using hardened monster plates.", "Armor 7 | +Armor shred resist", "Anti-monster gear", 960);

    public static final Item ETERNAL_OAK_CARAPACE = registerArmor("eternal_oak_carapace", Rarity.EPIC, "Heavy Armor",
        "Born from petrified spriggan roots, infused with druid magic.", "Armor 9 | Regeneration + root resistance", "Defensive tank tier", 1120);
    public static final Item SPECTRAL_WARDEN_VESTMENTS = registerArmor("spectral_warden_vestments", Rarity.EPIC, "Medium Armor",
        "Half-material armor interwoven with spectral fibers.", "Armor 6 | +Dodge chance, +Specter resist", "Mobility mage armor", 880);
    public static final Item DRACONID_SCALE_MANTLE = registerArmor("draconid_scale_mantle", Rarity.EPIC, "Heavy Armor",
        "Made from ancient draconid armor plates.", "Armor 10 | +Fire resist, +Heavy attack dmg", "Fire-themed builds", 1200);
    public static final Item WITCHER_KINGSLAYER_MAIL = registerArmor("witcher_kingslayer_mail", Rarity.EPIC, "Medium Armor",
        "Forged by renegade witchers during a forgotten rebellion.", "Armor 7 | Damage bonus vs humanoids", "High-risk, high-reward", 960);

    private static final Item[] FORGE_COMPONENTS = {
        DIMERITIUM_POMMEL, METEORITE_IRON_POMMEL, RUNESTONE_ENCRUSTED_POMMEL,
        LEATHER_WRAPPED_GRIP, WYVERN_HIDE_GRIP, ELDER_RESIN_BOUND_GRIP,
        CROSS_STEEL_GUARD, TEMERIAN_FORGED_GUARD, RUNIC_WOLF_GUARD,
        STEEL_INGOT, METEORITE_STEEL_INGOT, PURIFIED_SILVER_INGOT,
        DIMERITIUM_CORE_ROD, GRIFFIN_SCHOOL_CORE, BEAR_SCHOOL_CORE,
        FORGING_BILL, FORGED_SWORD_OUTPUT,
        ALGHOUL_BONE_MARROW, LESHEN_RESIN_CORE, GRIFFIN_FEATHERS, BASILISK_VENOM_GLAND,
        WHITE_GULL, DWARVEN_SPIRIT, AETHER_EXTRACT,
        RUNESTONE_VELES, RUNESTONE_PERUN, WITCHER_ALLOY_PLATE,
        ELDER_SILVER_PLATE, MONSTER_TROPHY,
        ARACHAS_HARDENED_CHITIN_PLATE, MOONSTEEL_INGOT,
        ETHERGLASS_SHARD, SPIRITBOUND_ALLOY, VOLATILE_GLOWSTONE_CORE,
        NOONWRAITH_ESSENCE_THREADS, FIEND_EYE_LENS, FOGLET_CONDENSED_MIST, GARGOYLE_HEARTSTONE,
        ANCIENT_YEW_TIMBER, PETRIFIED_SPRIGGAN_ROOT, WOLFSBANE_TREATED_OAK,
        STAMMELFORDS_DUST, MANDRAKE_DISTILLATE, REBIS_CRYSTAL, VITRIOL_RESIN,
        PRECISION_TEMPERING_HAMMER, RUNIC_ENGRAVING_CHISEL, COLD_QUENCH_FLASK,
        LAMINATED_WITCHER_LEATHER, SCALED_GRIFFIN_PADDING, CHROMATIC_CHAIN_LINKS,
        RUNESTONE_UROBOROS, RUNESTONE_AERONDIGHT, RUNESTONE_DRAKON,
        BONEGRIND_POWDER, SPECTRAL_ASH, FULMINATING_EMBER_DUST,
        IGNI_FIREBLOOD_INFUSION, YRDEN_GEOMANTIC_INFUSION, QUEN_AURIC_COATING,
        SHATTERED_RELIC_SIGIL, ELDER_BLOOD_TRACE_SAMPLE, ANCIENT_DRACONID_BONE
    };

    private static final Item[] FORGED_WEAPONS = {
        STEEL_BASTARD_SWORD, WOLFSTEEL_LONGSWORD, TEMPERED_SILVER_SABER, GRIFFIN_CASTING_SILVERBLADE,
        SKELLIGE_HEAVY_AXE, DWARVEN_CARBON_STEEL_HAMMER, STYGGA_CURVED_BLADE, RUNIC_SHORTSPEAR,
        VIPER_DUELIST_BLADE, NORTHERN_REAVER_HALBERD,
        BLADE_OF_THE_LESHEN_THORN, MOONLIT_VEIL, FENRIRS_HUNGER, STORMCALLERS_PIKE, HEARTFIRE_CLEAVER
    };

    private static final Item[] FORGED_ARMOR = {
        WITCHER_LEATHER_VEST, WOLF_SCHOOL_GAMBESON, GRIFFIN_BATTLEMAGE_COAT, BEAR_REINFORCED_PLATE,
        SKELLIGE_RAIDER_JACKET, VIPER_DUELIST_JACKET, NILFGAARDIAN_SCOUT_BRIGANDINE, REDANIAN_GUARD_CUIRASS,
        RUNIC_LAMELLAR_HARNESS, ARACHAS_CHITIN_BREASTPLATE,
        ETERNAL_OAK_CARAPACE, SPECTRAL_WARDEN_VESTMENTS, DRACONID_SCALE_MANTLE, WITCHER_KINGSLAYER_MAIL
    };

    private ModItems() {
    }

    static <T extends Item> T register(String name, ItemFactory<T> factory) {
        Objects.requireNonNull(factory, "Item factory cannot be null");

        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, name);
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, id);
        Item.Properties properties = new Item.Properties().setId(key);
        T item = factory.create(properties);
        T registered = Registry.register(BuiltInRegistries.ITEM, key, item);
        REGISTERED_IDS.add(id);
        return registered;
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> {
            entries.accept(SILVER_SWORD);
            entries.accept(STEEL_SWORD);
            for (Item weapon : FORGED_WEAPONS) {
                entries.accept(weapon);
            }
            for (Item armor : FORGED_ARMOR) {
                entries.accept(armor);
            }
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(entries -> {
            entries.accept(SWALLOW_POTION);
            entries.accept(CAT_POTION);
            entries.accept(THUNDERBOLT_POTION);
            entries.accept(WHITE_RAFFARD_DECOCTION);
            entries.accept(TAWNY_OWL_POTION);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            // Blade Oils
            entries.accept(NECROPHAGE_OIL);
            entries.accept(SPECTER_OIL);
            entries.accept(CURSED_OIL);
            entries.accept(BEAST_OIL);
            entries.accept(HYBRID_OIL);
            entries.accept(INSECTOID_OIL);
            entries.accept(VAMPIRE_OIL);
            entries.accept(RELICT_OIL);
            // Witcher Bombs
            entries.accept(GRAPESHOT_BOMB);
            entries.accept(DANCING_STAR_BOMB);
            entries.accept(DEVILS_PUFFBALL_BOMB);
            entries.accept(SAMUM_BOMB);
            entries.accept(NORTHERN_WIND_BOMB);
            entries.accept(DIMERITIUM_BOMB);
            entries.accept(DRAGONS_DREAM_BOMB);
            entries.accept(MOON_DUST_BOMB);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(entries -> {
            // Alchemy Herbs
            entries.accept(CELANDINE);
            entries.accept(CROWS_EYE);
            entries.accept(BLOWBALL);
            entries.accept(WHITE_MYRTLE);
            entries.accept(WOLFSBANE);
            entries.accept(MANDRAKE_ROOT);
            entries.accept(SEWANT_MUSHROOM);
            entries.accept(VERBENA);
            entries.accept(FOOLS_PARSLEY);
            entries.accept(BERBERCANE_FRUIT);
            entries.accept(WORMWOOD);
            entries.accept(HAN);
            
            // Monster Drops
            entries.accept(DROWNER_BRAIN);
            
            // Custom Ore Materials
            entries.accept(RAW_SILVER);
            entries.accept(SILVER_INGOT);
            entries.accept(RAW_METEORITE);
            entries.accept(METEORITE_INGOT);
            entries.accept(RAW_DARK_IRON);
            entries.accept(DARK_IRON_INGOT);
            entries.accept(RAW_DIMERITIUM);
            entries.accept(DIMERITIUM_INGOT);
            for (Item component : FORGE_COMPONENTS) {
                entries.accept(component);
            }
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(entries -> {
            entries.accept(KAEDWENI_SOLDIER_SPAWN_EGG);
            entries.accept(REDANIAN_GUARD_SPAWN_EGG);
            entries.accept(NILFGAARDIAN_SCOUT_SPAWN_EGG);
            // Monster spawn eggs
            entries.accept(DROWNED_CORPSE_SPAWN_EGG);
            entries.accept(NEKKER_SPAWN_EGG);
            entries.accept(GHOUL_SPAWN_EGG);
            entries.accept(ALGHOUL_SPAWN_EGG);
            entries.accept(WRAITH_SPAWN_EGG);
            entries.accept(WEREWOLF_SPAWN_EGG);
        });

        Witchercraft.LOGGER.info("Registered {} items", REGISTERED_IDS.size());
    }

    private static Item registerSpawnEgg(String name, EntityType<?> entityType, Rarity rarity) {
        return register(name, properties -> new SpawnEggItem(
            properties.spawnEgg(entityType)
                .rarity(rarity)
                .stacksTo(64)
        ));
    }

    @FunctionalInterface
    interface ItemFactory<T extends Item> extends Function<Item.Properties, T> {
        @Override
        T apply(Item.Properties properties);

        default T create(Item.Properties properties) {
            return apply(properties);
        }
    }

    @FunctionalInterface
    interface LoreItemFactory<T extends Item> {
        T create(Item.Properties properties, ItemLore lore);
    }

    private static Item registerComponent(String name, Rarity rarity, LoreItemFactory<? extends Item> factory, ItemLore lore) {
        return registerComponent(name, rarity, 64, factory, lore);
    }

    private static Item registerComponent(String name, Rarity rarity, int maxStackSize, LoreItemFactory<? extends Item> factory, ItemLore lore) {
        return register(name, properties -> factory.create(properties.rarity(rarity).stacksTo(maxStackSize), lore));
    }

    private static Item registerComponent(String name, Rarity rarity, LoreItemFactory<? extends Item> factory, String category, String description, String stats, String useCase) {
        return registerComponent(name, rarity, factory, ItemLore.of(category, description, stats, useCase));
    }

    private static Item registerComponent(String name, Rarity rarity, int maxStackSize, LoreItemFactory<? extends Item> factory, String category, String description, String stats, String useCase) {
        return registerComponent(name, rarity, maxStackSize, factory, ItemLore.of(category, description, stats, useCase));
    }

    private static Item registerWeapon(String name, Rarity rarity, String type, String description, String stats, String useCase, float damage, float attackSpeed, int durability) {
        return register(name, properties -> new ForgedWeaponItem(properties.rarity(rarity), ItemLore.of(type, description, stats, useCase), damage, attackSpeed, durability));
    }

    private static Item registerArmor(String name, Rarity rarity, String armorType, String description, String stats, String useCase, int durability) {
        return register(name, properties -> new ForgedArmorItem(properties.rarity(rarity), ItemLore.of(armorType, description, stats, useCase), durability));
    }
}
