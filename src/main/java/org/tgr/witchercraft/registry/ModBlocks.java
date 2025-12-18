package org.tgr.witchercraft.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.block.QuestBoardBlock;
import org.tgr.witchercraft.block.WitcherForge.WitcherForgeBlock;
import org.tgr.witchercraft.block.alchemy.AlchemyTableBlock;
import org.tgr.witchercraft.block.kaedwen.FrostglassLanternBlock;
import org.tgr.witchercraft.block.kaedwen.FrozenWellCoreBlock;
import org.tgr.witchercraft.block.kaedwen.HerbalDryingRackBlock;
import org.tgr.witchercraft.block.kaedwen.KaedweniPlasterWallBlock;
import org.tgr.witchercraft.block.kaedwen.KaedweniRoofShingleBlock;
import org.tgr.witchercraft.block.kaedwen.KaedweniRoofShingleSlabBlock;
import org.tgr.witchercraft.block.kaedwen.KaedweniRoofShingleStairBlock;
import org.tgr.witchercraft.block.kaedwen.KaedweniStoneFoundationBlock;
import org.tgr.witchercraft.block.kaedwen.KaedweniStoneFoundationSlabBlock;
import org.tgr.witchercraft.block.kaedwen.KaedweniTimberBeamBlock;
import org.tgr.witchercraft.block.kaedwen.KaedweniVerticalSlabBlock;
import org.tgr.witchercraft.block.kaedwen.NoticeBoardBlock;
import org.tgr.witchercraft.block.kaedwen.PortcullisBlock;
import org.tgr.witchercraft.block.kaedwen.ReinforcedPalisadeBlock;
import org.tgr.witchercraft.block.kaedwen.SupplyCrateBlock;
import org.tgr.witchercraft.faction.Faction;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Block registry for WitcherCraft mod following the Fabric 1.21.1+ registration rules.
 */
public final class ModBlocks {

    public static final Block ALCHEMY_TABLE = register(
        "alchemy_table",
        properties -> new AlchemyTableBlock(properties.strength(2.5F).requiresCorrectToolForDrops()),
        BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE)
    );

    // Quest Boards for each faction
    public static final Block QUEST_BOARD_KAEDWEN = registerBlock(
        "quest_board_kaedwen",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .sound(SoundType.WOOD)
            .noOcclusion()
            .strength(2.0F),
        properties -> new QuestBoardBlock(properties, Faction.KAEDWEN)
    );

    public static final Block QUEST_BOARD_REDANIA = registerBlock(
        "quest_board_redania",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .sound(SoundType.WOOD)
            .noOcclusion()
            .strength(2.0F),
        properties -> new QuestBoardBlock(properties, Faction.REDANIA)
    );

    public static final Block QUEST_BOARD_TEMERIA = registerBlock(
        "quest_board_temeria",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .sound(SoundType.WOOD)
            .noOcclusion()
            .strength(2.0F),
        properties -> new QuestBoardBlock(properties, Faction.TEMERIA)
    );

    public static final Block QUEST_BOARD_NILFGAARD = registerBlock(
        "quest_board_nilfgaard",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .sound(SoundType.WOOD)
            .noOcclusion()
            .strength(2.0F),
        properties -> new QuestBoardBlock(properties, Faction.NILFGAARD)
    );

    public static final Block QUEST_BOARD_SKELLIGE = registerBlock(
        "quest_board_skellige",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .sound(SoundType.WOOD)
            .noOcclusion()
            .strength(2.0F),
        properties -> new QuestBoardBlock(properties, Faction.SKELLIGE)
    );

    public static final Block QUEST_BOARD_WITCHERS = registerBlock(
        "quest_board_witchers",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .sound(SoundType.WOOD)
            .noOcclusion()
            .strength(2.0F),
        properties -> new QuestBoardBlock(properties, Faction.WITCHERS)
    );

    public static final Block QUEST_BOARD_SCOIA_TAEL = registerBlock(
        "quest_board_scoia_tael",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .sound(SoundType.WOOD)
            .noOcclusion()
            .strength(2.0F),
        properties -> new QuestBoardBlock(properties, Faction.SCOIA_TAEL)
    );

    public static final Block WITCHER_FORGE = register(
            "witcher_forge_block",
            WitcherForgeBlock::new,
            BlockBehaviour.Properties.of()
    );


    public static final Block GRINDSTONE_WITCHER = registerBlock(
            "grindstone_witcher",
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.GRINDSTONE),
            properties -> new Block(properties.strength(3.5F).requiresCorrectToolForDrops())
    );

    public static final Block KAEDWENI_NOTICE_BOARD = registerBlock(
        "kaedweni_notice_board",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .sound(SoundType.WOOD)
            .noOcclusion()
            .strength(1.5F),
        NoticeBoardBlock::new
    );

    public static final Block HERBAL_DRYING_RACK = registerBlock(
        "herbal_drying_rack",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .sound(SoundType.WOOD)
            .noOcclusion()
            .strength(1.0F),
        HerbalDryingRackBlock::new
    );

    public static final Block FROZEN_WELL_CORE = registerBlock(
        "frozen_well_core",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_LIGHT_BLUE)
            .sound(SoundType.GLASS)
            .noOcclusion()
            .strength(2.0F)
            .friction(0.7F),
        FrozenWellCoreBlock::new
    );

    public static final Block GATEHOUSE_PORTCULLIS = registerBlock(
        "gatehouse_portcullis",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .sound(SoundType.METAL)
            .noOcclusion()
            .strength(4.0F, 12.0F)
            .requiresCorrectToolForDrops(),
        PortcullisBlock::new
    );

    public static final Block KAEDWENI_TIMBER_BEAM = registerBlock(
        "kaedweni_timber_beam",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .sound(SoundType.WOOD)
            .strength(2.5F),
        KaedweniTimberBeamBlock::new
    );

    public static final Block KAEDWENI_PLASTER_WALL = registerBlock(
        "kaedweni_plaster_wall",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.SNOW)
            .sound(SoundType.DECORATED_POT)
            .noOcclusion()
            .strength(1.5F),
        KaedweniPlasterWallBlock::new
    );

    public static final Block KAEDWENI_STONE_FOUNDATION = registerBlock(
        "kaedweni_stone_foundation",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.DEEPSLATE_TILES)
            .strength(4.0F, 12.0F)
            .requiresCorrectToolForDrops(),
        KaedweniStoneFoundationBlock::new
    );

    public static final Block KAEDWENI_STONE_FOUNDATION_MOSSY = registerBlock(
        "kaedweni_stone_foundation_mossy",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_GREEN)
            .sound(SoundType.DEEPSLATE_TILES)
            .strength(4.0F, 12.0F)
            .requiresCorrectToolForDrops(),
        KaedweniStoneFoundationBlock::new
    );

    public static final Block KAEDWENI_STONE_FOUNDATION_SLAB = registerBlock(
        "kaedweni_stone_foundation_slab",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.DEEPSLATE_TILES)
            .strength(4.0F, 12.0F)
            .requiresCorrectToolForDrops(),
        KaedweniStoneFoundationSlabBlock::new
    );

    public static final Block KAEDWENI_STONE_FOUNDATION_SLAB_MOSSY = registerBlock(
        "kaedweni_stone_foundation_slab_mossy",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_GREEN)
            .sound(SoundType.DEEPSLATE_TILES)
            .strength(4.0F, 12.0F)
            .requiresCorrectToolForDrops(),
        KaedweniStoneFoundationSlabBlock::new
    );

    public static final Block KAEDWENI_ROOF_SHINGLES = registerBlock(
        "kaedweni_roof_shingles",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .sound(SoundType.WOOD)
            .strength(2.0F),
        KaedweniRoofShingleBlock::new
    );

    public static final Block KAEDWENI_ROOF_SHINGLE_STAIRS = registerBlock(
        "kaedweni_roof_shingle_stairs",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .sound(SoundType.WOOD)
            .strength(2.0F),
        properties -> new KaedweniRoofShingleStairBlock(KAEDWENI_ROOF_SHINGLES.defaultBlockState(), properties)
    );

    public static final Block KAEDWENI_ROOF_SHINGLE_SLAB = registerBlock(
        "kaedweni_roof_shingle_slab",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .sound(SoundType.WOOD)
            .strength(2.0F),
        KaedweniRoofShingleSlabBlock::new
    );

    public static final Block KAEDWENI_ROOF_SHINGLE_VERTICAL_SLAB = registerBlock(
        "kaedweni_roof_shingle_vertical_slab",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .sound(SoundType.WOOD)
            .noOcclusion()
            .strength(2.0F),
        KaedweniVerticalSlabBlock::new
    );

    public static final Block REINFORCED_PALISADE_LOG = registerBlock(
        "reinforced_palisade_log",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .sound(SoundType.WOOD)
            .noOcclusion()
            .strength(3.0F),
        ReinforcedPalisadeBlock::new
    );

    public static final Block SUPPLY_CRATE = registerBlock(
        "supply_crate_kaedwen",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .sound(SoundType.WOOD)
            .strength(2.0F),
        SupplyCrateBlock::new
    );

    public static final Block FROSTGLASS_LANTERN = registerBlock(
        "frostglass_lantern",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_LIGHT_BLUE)
            .sound(SoundType.LANTERN)
            .noOcclusion()
            .lightLevel(state -> 15)
            .strength(1.0F),
        FrostglassLanternBlock::new
    );

    // ========================================
    // CUSTOM ORES (Silver, Meteorite, Dark Iron, Dimeritium)
    // ========================================
    
    public static final Block SILVER_ORE = registerBlock(
        "silver_ore",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .strength(3.0F, 3.0F),
        Block::new
    );
    
    public static final Block DEEPSLATE_SILVER_ORE = registerBlock(
        "deepslate_silver_ore",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE)
            .mapColor(MapColor.DEEPSLATE)
            .requiresCorrectToolForDrops()
            .strength(4.5F, 3.0F)
            .sound(SoundType.DEEPSLATE),
        Block::new
    );
    
    public static final Block SILVER_BLOCK = registerBlock(
        "silver_block",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
            .mapColor(MapColor.METAL)
            .requiresCorrectToolForDrops()
            .strength(5.0F, 6.0F)
            .sound(SoundType.METAL),
        Block::new
    );
    
    public static final Block RAW_SILVER_BLOCK = registerBlock(
        "raw_silver_block",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK)
            .mapColor(MapColor.RAW_IRON)
            .requiresCorrectToolForDrops()
            .strength(5.0F, 6.0F),
        Block::new
    );
    
    public static final Block METEORITE_ORE = registerBlock(
        "meteorite_ore",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.ANCIENT_DEBRIS)
            .mapColor(MapColor.COLOR_BLACK)
            .requiresCorrectToolForDrops()
            .strength(30.0F, 1200.0F)
            .sound(SoundType.ANCIENT_DEBRIS),
        Block::new
    );
    
    public static final Block METEORITE_BLOCK = registerBlock(
        "meteorite_block",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK)
            .mapColor(MapColor.COLOR_BLACK)
            .requiresCorrectToolForDrops()
            .strength(50.0F, 1200.0F)
            .sound(SoundType.NETHERITE_BLOCK),
        Block::new
    );
    
    public static final Block RAW_METEORITE_BLOCK = registerBlock(
        "raw_meteorite_block",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.ANCIENT_DEBRIS)
            .mapColor(MapColor.COLOR_BLACK)
            .requiresCorrectToolForDrops()
            .strength(40.0F, 1200.0F),
        Block::new
    );
    
    public static final Block DARK_IRON_ORE = registerBlock(
        "dark_iron_ore",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE)
            .mapColor(MapColor.COLOR_GRAY)
            .requiresCorrectToolForDrops()
            .strength(3.0F, 3.0F),
        Block::new
    );
    
    public static final Block DEEPSLATE_DARK_IRON_ORE = registerBlock(
        "deepslate_dark_iron_ore",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_IRON_ORE)
            .mapColor(MapColor.DEEPSLATE)
            .requiresCorrectToolForDrops()
            .strength(4.5F, 3.0F)
            .sound(SoundType.DEEPSLATE),
        Block::new
    );
    
    public static final Block DARK_IRON_BLOCK = registerBlock(
        "dark_iron_block",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
            .mapColor(MapColor.COLOR_BLACK)
            .requiresCorrectToolForDrops()
            .strength(5.0F, 6.0F)
            .sound(SoundType.METAL),
        Block::new
    );
    
    public static final Block RAW_DARK_IRON_BLOCK = registerBlock(
        "raw_dark_iron_block",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK)
            .mapColor(MapColor.COLOR_GRAY)
            .requiresCorrectToolForDrops()
            .strength(5.0F, 6.0F),
        Block::new
    );
    
    public static final Block DIMERITIUM_ORE = registerBlock(
        "dimeritium_ore",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_ORE)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .strength(3.0F, 3.0F),
        Block::new
    );
    
    public static final Block DEEPSLATE_DIMERITIUM_ORE = registerBlock(
        "deepslate_dimeritium_ore",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_DIAMOND_ORE)
            .mapColor(MapColor.DEEPSLATE)
            .requiresCorrectToolForDrops()
            .strength(4.5F, 3.0F)
            .sound(SoundType.DEEPSLATE),
        Block::new
    );
    
    public static final Block DIMERITIUM_BLOCK = registerBlock(
        "dimeritium_block",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
            .mapColor(MapColor.TERRACOTTA_BLUE)
            .requiresCorrectToolForDrops()
            .strength(5.0F, 6.0F)
            .sound(SoundType.METAL),
        Block::new
    );
    
    public static final Block RAW_DIMERITIUM_BLOCK = registerBlock(
        "raw_dimeritium_block",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK)
            .mapColor(MapColor.TERRACOTTA_BLUE)
            .requiresCorrectToolForDrops()
            .strength(5.0F, 6.0F),
        Block::new
    );

    // ========================================
    // ALCHEMY HERBS
    // ========================================
    
    public static final Block CROWS_EYE_BLOCK = registerBlock(
        "crows_eye",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .randomTicks()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY),
        org.tgr.witchercraft.block.herb.CrowsEyeBlock::new
    );
    
    public static final Block BLOWBALL_BLOCK = registerBlock(
        "blowball",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .randomTicks()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY),
        org.tgr.witchercraft.block.herb.BlowballBlock::new
    );
    
    public static final Block WHITE_MYRTLE_BLOCK = registerBlock(
        "white_myrtle",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .randomTicks()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY),
        org.tgr.witchercraft.block.herb.WhiteMyrtleBlock::new
    );
    
    public static final Block WOLFSBANE_BLOCK = registerBlock(
        "wolfsbane",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .randomTicks()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY),
        org.tgr.witchercraft.block.herb.WolfsbaneBlock::new
    );
    
    public static final Block MANDRAKE_ROOT_BLOCK = registerBlock(
        "mandrake_root",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .randomTicks()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY),
        org.tgr.witchercraft.block.herb.MandrakeRootBlock::new
    );
    
    public static final Block SEWANT_MUSHROOM_BLOCK = registerBlock(
        "sewant_mushroom",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .randomTicks()
            .instabreak()
            .sound(SoundType.GRASS)
            .lightLevel(state -> 3)
            .pushReaction(PushReaction.DESTROY),
        org.tgr.witchercraft.block.herb.SewantMushroomBlock::new
    );
    
    public static final Block VERBENA_BLOCK = registerBlock(
        "verbena",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .randomTicks()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY),
        org.tgr.witchercraft.block.herb.VerbenaBlock::new
    );
    
    public static final Block FOOLS_PARSLEY_BLOCK = registerBlock(
        "fools_parsley",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .randomTicks()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY),
        org.tgr.witchercraft.block.herb.FoolsParsleyBlock::new
    );
    
    public static final Block BERBERCANE_FRUIT_BLOCK = registerBlock(
        "berbercane_fruit",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .randomTicks()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY),
        org.tgr.witchercraft.block.herb.BerbercaneFruitBlock::new
    );
    
    public static final Block WORMWOOD_BLOCK = registerBlock(
        "wormwood",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .randomTicks()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY),
        org.tgr.witchercraft.block.herb.WormwoodBlock::new
    );
    
    public static final Block HAN_BLOCK = registerBlock(
        "han",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .randomTicks()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY),
        org.tgr.witchercraft.block.herb.HanBlock::new
    );

    // ========================================
    // REGIONAL ARCHITECTURE - TEMERIA
    // ========================================
    
    public static final Block TEMERI_STONE_BRICKS = registerBlock(
        "temeri_stone_bricks",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .strength(1.5F, 6.0F),
        org.tgr.witchercraft.block.architecture.TemeriStoneBricksBlock::new
    );
    
    public static final Block TEMERIAN_COBBLESTONE = registerBlock(
        "temerian_cobblestone",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .strength(2.0F, 6.0F),
        org.tgr.witchercraft.block.architecture.TemerianCobblestoneBlock::new
    );
    
    public static final Block TEMERIAN_ROOF_TILE = registerBlock(
        "temerian_roof_tile",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS)
            .mapColor(MapColor.COLOR_RED)
            .requiresCorrectToolForDrops()
            .strength(2.0F, 6.0F),
        org.tgr.witchercraft.block.architecture.TemerianRoofTileBlock::new
    );
    
    public static final Block TEMERIAN_TIMBER_FRAME = registerBlock(
        "temerian_timber_frame",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
            .mapColor(MapColor.WOOD)
            .strength(2.0F, 3.0F)
            .sound(SoundType.WOOD),
        org.tgr.witchercraft.block.architecture.TemerianTimberFrameBlock::new
    );
    
    public static final Block TEMERIAN_PLASTER = registerBlock(
        "temerian_plaster",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE)
            .mapColor(MapColor.QUARTZ)
            .strength(1.8F, 1.8F),
        org.tgr.witchercraft.block.architecture.TemerianPlasterBlock::new
    );

    // ========================================
    // REGIONAL ARCHITECTURE - NILFGAARD
    // ========================================
    
    public static final Block NILFGAARDIAN_BLACK_STONE = registerBlock(
        "nilfgaardian_black_stone",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BLACKSTONE)
            .mapColor(MapColor.COLOR_BLACK)
            .requiresCorrectToolForDrops()
            .strength(1.5F, 6.0F),
        org.tgr.witchercraft.block.architecture.NilfgaardianBlackStoneBlock::new
    );
    
    public static final Block NILFGAARDIAN_MARBLE = registerBlock(
        "nilfgaardian_marble",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK)
            .mapColor(MapColor.QUARTZ)
            .requiresCorrectToolForDrops()
            .strength(0.8F, 0.8F),
        org.tgr.witchercraft.block.architecture.NilfgaardianMarbleBlock::new
    );
    
    public static final Block NILFGAARDIAN_PILLAR = registerBlock(
        "nilfgaardian_pillar",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_PILLAR)
            .mapColor(MapColor.QUARTZ)
            .requiresCorrectToolForDrops()
            .strength(0.8F, 0.8F),
        org.tgr.witchercraft.block.architecture.NilfgaardianPillarBlock::new
    );
    
    public static final Block NILFGAARDIAN_BANNER = registerBlock(
        "nilfgaardian_banner",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BLACK_BANNER)
            .mapColor(MapColor.COLOR_BLACK)
            .strength(1.0F)
            .sound(SoundType.WOOL),
        org.tgr.witchercraft.block.architecture.NilfgaardianBannerBlock::new
    );
    
    public static final Block NILFGAARDIAN_PAVED_STONE = registerBlock(
        "nilfgaardian_paved_stone",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .strength(1.5F, 6.0F),
        org.tgr.witchercraft.block.architecture.NilfgaardianPavedStoneBlock::new
    );

    // ========================================
    // REGIONAL ARCHITECTURE - SKELLIGE
    // ========================================
    
    public static final Block SKELLIGE_CARVED_STONE = registerBlock(
        "skellige_carved_stone",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_STONE_BRICKS)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .strength(1.5F, 6.0F),
        org.tgr.witchercraft.block.architecture.SkelligeCarvedStoneBlock::new
    );
    
    public static final Block SKELLIGE_DRIFTWOOD = registerBlock(
        "skellige_driftwood",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)
            .mapColor(MapColor.COLOR_GRAY)
            .strength(2.0F)
            .sound(SoundType.WOOD),
        org.tgr.witchercraft.block.architecture.SkelligeDriftwoodBlock::new
    );
    
    public static final Block SKELLIGE_STONE_SHINGLE = registerBlock(
        "skellige_stone_shingle",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .strength(1.5F, 6.0F),
        org.tgr.witchercraft.block.architecture.SkelligeStoneShingleBlock::new
    );
    
    public static final Block SKELLIGE_LONGHOUSE_BEAM = registerBlock(
        "skellige_longhouse_beam",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WOOD)
            .mapColor(MapColor.WOOD)
            .strength(2.0F)
            .sound(SoundType.WOOD),
        org.tgr.witchercraft.block.architecture.SkelligeLonghouseBeamBlock::new
    );

    // ========================================
    // ANCIENT ARCHITECTURE - DWARVEN RUINS
    // ========================================
    
    public static final Block DWARVEN_CARVED_STONE = registerBlock(
        "dwarven_carved_stone",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .strength(2.0F, 8.0F),
        org.tgr.witchercraft.block.architecture.DwarvenCarvedStoneBlock::new
    );
    
    public static final Block DWARVEN_BRONZE_TRIM = registerBlock(
        "dwarven_bronze_trim",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK)
            .mapColor(MapColor.COLOR_ORANGE)
            .requiresCorrectToolForDrops()
            .strength(3.0F, 6.0F)
            .sound(SoundType.COPPER),
        org.tgr.witchercraft.block.architecture.DwarvenBronzeTrimBlock::new
    );
    
    public static final Block DWARVEN_MOSSY_STONE = registerBlock(
        "dwarven_mossy_stone",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MOSSY_STONE_BRICKS)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .strength(1.5F, 6.0F),
        org.tgr.witchercraft.block.architecture.DwarvenMossyStoneBlock::new
    );
    
    public static final Block DWARVEN_PILLAR = registerBlock(
        "dwarven_pillar",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .strength(2.0F, 8.0F),
        org.tgr.witchercraft.block.architecture.DwarvenPillarBlock::new
    );

    // ========================================
    // ANCIENT ARCHITECTURE - ELVEN RUINS
    // ========================================
    
    public static final Block ELVEN_WHITE_STONE = registerBlock(
        "elven_white_stone",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK)
            .mapColor(MapColor.QUARTZ)
            .requiresCorrectToolForDrops()
            .strength(1.0F, 6.0F),
        org.tgr.witchercraft.block.architecture.ElvenWhiteStoneBlock::new
    );
    
    public static final Block ELVEN_VINE_COVERED_STONE = registerBlock(
        "elven_vine_covered_stone",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MOSSY_STONE_BRICKS)
            .mapColor(MapColor.PLANT)
            .requiresCorrectToolForDrops()
            .strength(1.0F, 6.0F),
        org.tgr.witchercraft.block.architecture.ElvenVineCoveredStoneBlock::new
    );
    
    public static final Block ELVEN_GLOWING_RUNE = registerBlock(
        "elven_glowing_rune",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.GLOWSTONE)
            .mapColor(MapColor.COLOR_LIGHT_BLUE)
            .strength(0.3F)
            .lightLevel(state -> 10)
            .sound(SoundType.GLASS),
        org.tgr.witchercraft.block.architecture.ElvenGlowingRuneBlock::new
    );
    
    public static final Block ELVEN_ARCHWAY = registerBlock(
        "elven_archway",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK)
            .mapColor(MapColor.QUARTZ)
            .requiresCorrectToolForDrops()
            .strength(1.0F, 6.0F),
        org.tgr.witchercraft.block.architecture.ElvenArchwayBlock::new
    );

    // ========================================
    // LANDMARKS
    // ========================================
    
    public static final Block PLACE_OF_POWER_STONE = registerBlock(
        "place_of_power_stone",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)
            .mapColor(MapColor.COLOR_BLACK)
            .requiresCorrectToolForDrops()
            .strength(50.0F, 1200.0F)
            .lightLevel(state -> 7),
        org.tgr.witchercraft.block.landmark.PlaceOfPowerStoneBlock::new
    );
    
    public static final Block PLACE_OF_POWER_RUNE_STONE = registerBlock(
        "place_of_power_rune_stone",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .strength(5.0F, 10.0F)
            .lightLevel(state -> 5),
        org.tgr.witchercraft.block.landmark.PlaceOfPowerRuneStoneBlock::new
    );
    
    public static final Block PLACE_OF_POWER_PEDESTAL = registerBlock(
        "place_of_power_pedestal",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .strength(5.0F, 10.0F)
            .lightLevel(state -> 3),
        org.tgr.witchercraft.block.landmark.PlaceOfPowerPedestalBlock::new
    );
    
    public static final Block ANCIENT_OAK_LOG = registerBlock(
        "ancient_oak_log",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)
            .mapColor(MapColor.WOOD)
            .strength(3.0F)
            .sound(SoundType.WOOD),
        org.tgr.witchercraft.block.landmark.AncientOakLogBlock::new
    );
    
    public static final Block ANCIENT_OAK_LEAVES = registerBlock(
        "ancient_oak_leaves",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
            .mapColor(MapColor.PLANT)
            .strength(0.2F)
            .randomTicks()
            .sound(SoundType.GRASS)
            .noOcclusion()
            .isValidSpawn((state, level, pos, type) -> false)
            .isSuffocating((state, level, pos) -> false)
            .isViewBlocking((state, level, pos) -> false),
        org.tgr.witchercraft.block.landmark.AncientOakLeavesBlock::new
    );
    
    public static final Block ANCIENT_TREE_ROOTS = registerBlock(
        "ancient_tree_roots",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)
            .mapColor(MapColor.WOOD)
            .strength(2.0F)
            .sound(SoundType.WOOD),
        org.tgr.witchercraft.block.landmark.AncientTreeRootsBlock::new
    );

    // ========================================
    // ENVIRONMENTAL STORYTELLING - BATTLEFIELD
    // ========================================
    
    public static final Block RUSTED_SWORD = registerBlock(
        "rusted_sword",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .strength(1.0F)
            .sound(SoundType.METAL)
            .noOcclusion(),
        org.tgr.witchercraft.block.environmental.RustedSwordBlock::new
    );
    
    public static final Block RUSTED_ARMOR_STAND = registerBlock(
        "rusted_armor_stand",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .strength(1.0F)
            .sound(SoundType.METAL)
            .noOcclusion(),
        org.tgr.witchercraft.block.environmental.RustedArmorStandBlock::new
    );
    
    public static final Block MASS_GRAVE_MARKER = registerBlock(
        "mass_grave_marker",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK)
            .mapColor(MapColor.SAND)
            .strength(2.0F)
            .sound(SoundType.BONE_BLOCK),
        org.tgr.witchercraft.block.environmental.MassGraveMarkerBlock::new
    );
    
    public static final Block BROKEN_BANNER_POLE = registerBlock(
        "broken_banner_pole",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .strength(1.0F)
            .sound(SoundType.WOOD)
            .noOcclusion(),
        org.tgr.witchercraft.block.environmental.BrokenBannerPoleBlock::new
    );
    
    public static final Block BATTLEFIELD_CRATER = registerBlock(
        "battlefield_crater",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)
            .mapColor(MapColor.COLOR_BLACK)
            .strength(0.5F),
        org.tgr.witchercraft.block.environmental.BattlefieldCraterBlock::new
    );
    
    public static final Block CORPSE_REMAINS = registerBlock(
        "corpse_remains",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK)
            .mapColor(MapColor.SAND)
            .strength(1.0F)
            .sound(SoundType.BONE_BLOCK)
            .noOcclusion(),
        org.tgr.witchercraft.block.environmental.CorpseRemainsBlock::new
    );

    // ========================================
    // ENVIRONMENTAL STORYTELLING - ABANDONED SETTLEMENTS
    // ========================================
    
    public static final Block CHARRED_LOG = registerBlock(
        "charred_log",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)
            .mapColor(MapColor.COLOR_BLACK)
            .strength(2.0F)
            .sound(SoundType.WOOD),
        org.tgr.witchercraft.block.environmental.CharredLogBlock::new
    );
    
    public static final Block BROKEN_COBBLESTONE = registerBlock(
        "broken_cobblestone",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE)
            .mapColor(MapColor.STONE)
            .strength(1.5F, 6.0F),
        org.tgr.witchercraft.block.environmental.BrokenCobblestoneBlock::new
    );
    
    public static final Block OVERGROWN_THATCH = registerBlock(
        "overgrown_thatch",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)
            .mapColor(MapColor.PLANT)
            .strength(0.5F)
            .sound(SoundType.GRASS),
        org.tgr.witchercraft.block.environmental.OvergrownThatchBlock::new
    );
    
    public static final Block REFUGEE_TENT = registerBlock(
        "refugee_tent",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL)
            .mapColor(MapColor.WOOL)
            .strength(0.8F)
            .sound(SoundType.WOOL)
            .noOcclusion(),
        org.tgr.witchercraft.block.environmental.RefugeeTentBlock::new
    );

    // ========================================
    // ENVIRONMENTAL STORYTELLING - ANCIENT RUINS
    // ========================================
    
    public static final Block CRACKED_STONE_BRICKS = registerBlock(
        "cracked_stone_bricks_witcher",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.CRACKED_STONE_BRICKS)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .strength(1.5F, 6.0F),
        org.tgr.witchercraft.block.environmental.CrackedStoneBricksBlock::new
    );
    
    public static final Block VINE_COVERED_PILLAR = registerBlock(
        "vine_covered_pillar",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MOSSY_STONE_BRICKS)
            .mapColor(MapColor.PLANT)
            .requiresCorrectToolForDrops()
            .strength(1.5F, 6.0F),
        org.tgr.witchercraft.block.environmental.VineCoveredPillarBlock::new
    );
    
    public static final Block COLLAPSED_ARCH = registerBlock(
        "collapsed_arch",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .strength(1.5F, 6.0F)
            .noOcclusion(),
        org.tgr.witchercraft.block.environmental.CollapsedArchBlock::new
    );
    
    public static final Block ARCHAEOLOGICAL_DIRT = registerBlock(
        "archaeological_dirt",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)
            .mapColor(MapColor.DIRT)
            .strength(0.5F),
        org.tgr.witchercraft.block.environmental.ArchaeologicalDirtBlock::new
    );

    // ========================================
    // FLORA - WATER & SWAMP
    // ========================================
    
    public static final Block DRIFTWOOD_LOG = registerBlock(
        "driftwood_log",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)
            .mapColor(MapColor.COLOR_GRAY)
            .strength(2.0F)
            .sound(SoundType.WOOD),
        org.tgr.witchercraft.block.flora.DriftwoodLogBlock::new
    );
    
    public static final Block WATER_REED = registerBlock(
        "water_reed",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY),
        org.tgr.witchercraft.block.flora.WaterReedBlock::new
    );
    
    public static final Block CATTAIL_PLANT = registerBlock(
        "cattail_plant",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY),
        org.tgr.witchercraft.block.flora.CattailPlantBlock::new
    );
    
    public static final Block SWAMP_VINE = registerBlock(
        "swamp_vine",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .instabreak()
            .sound(SoundType.VINE)
            .pushReaction(PushReaction.DESTROY),
        org.tgr.witchercraft.block.flora.SwampVineBlock::new
    );
    
    public static final Block TOXIC_MUSHROOM = registerBlock(
        "toxic_mushroom",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .instabreak()
            .sound(SoundType.GRASS)
            .lightLevel(state -> 6)
            .pushReaction(PushReaction.DESTROY),
        org.tgr.witchercraft.block.flora.ToxicMushroomBlock::new
    );

    // ========================================
    // FLORA - MOUNTAIN & ALPINE
    // ========================================
    
    public static final Block MOUNTAIN_LICHEN = registerBlock(
        "mountain_lichen",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .instabreak()
            .sound(SoundType.GRASS)
            .pushReaction(PushReaction.DESTROY),
        org.tgr.witchercraft.block.flora.MountainLichenBlock::new
    );
    
    public static final Block ALPINE_FLOWER = registerBlock(
        "alpine_flower",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY),
        org.tgr.witchercraft.block.flora.AlpineFlowerBlock::new
    );
    
    public static final Block SNOW_MOSS = registerBlock(
        "snow_moss",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.SNOW)
            .noCollision()
            .instabreak()
            .sound(SoundType.GRASS)
            .pushReaction(PushReaction.DESTROY),
        org.tgr.witchercraft.block.flora.SnowMossBlock::new
    );

    // ========================================
    // FLORA - TOUSSAINT MEDITERRANEAN
    // ========================================
    
    public static final Block GRAPE_VINE = registerBlock(
        "grape_vine",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .instabreak()
            .sound(SoundType.VINE)
            .pushReaction(PushReaction.DESTROY),
        org.tgr.witchercraft.block.flora.GrapeVineBlock::new
    );
    
    public static final Block LAVENDER_PLANT = registerBlock(
        "lavender_plant",
        () -> BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollision()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(BlockBehaviour.OffsetType.XZ)
            .pushReaction(PushReaction.DESTROY),
        org.tgr.witchercraft.block.flora.LavenderPlantBlock::new
    );
    
    public static final Block OLIVE_TREE_LOG = registerBlock(
        "olive_tree_log",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)
            .mapColor(MapColor.WOOD)
            .strength(2.0F)
            .sound(SoundType.WOOD),
        org.tgr.witchercraft.block.flora.OliveTreeLogBlock::new
    );
    
    public static final Block CYPRESS_LOG = registerBlock(
        "cypress_log",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LOG)
            .mapColor(MapColor.WOOD)
            .strength(2.0F)
            .sound(SoundType.WOOD),
        org.tgr.witchercraft.block.flora.CypressLogBlock::new
    );

    // ========================================
    // MONSTER NESTS
    // ========================================
    
    public static final Block NEKKER_NEST = registerBlock(
        "nekker_nest",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)
            .mapColor(MapColor.DIRT)
            .strength(2.0F)
            .sound(SoundType.GRASS)
            .randomTicks(),
        org.tgr.witchercraft.block.monster.NekkerNestBlock::new
    );
    
    public static final Block DROWNED_NEST = registerBlock(
        "drowned_nest",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK)
            .mapColor(MapColor.PLANT)
            .strength(2.0F)
            .sound(SoundType.GRASS)
            .randomTicks(),
        org.tgr.witchercraft.block.monster.DrownedNestBlock::new
    );
    
    public static final Block GHOUL_NEST = registerBlock(
        "ghoul_nest",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK)
            .mapColor(MapColor.SAND)
            .strength(2.0F)
            .sound(SoundType.BONE_BLOCK)
            .randomTicks(),
        org.tgr.witchercraft.block.monster.GhoulNestBlock::new
    );
    
    public static final Block WRAITH_CURSED_STONE = registerBlock(
        "wraith_cursed_stone",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
            .mapColor(MapColor.COLOR_BLACK)
            .requiresCorrectToolForDrops()
            .strength(5.0F, 10.0F)
            .lightLevel(state -> 3)
            .randomTicks(),
        org.tgr.witchercraft.block.monster.WraithCursedStoneBlock::new
    );
    
    // ========================================
    // PLACES OF POWER
    // ========================================
    
    public static final Block PLACE_OF_POWER_IGNI = registerBlock(
        "place_of_power_igni",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
            .mapColor(MapColor.FIRE)
            .requiresCorrectToolForDrops()
            .strength(50.0F, 1200.0F) // Very hard to break
            .noOcclusion(),
        props -> new org.tgr.witchercraft.block.PlaceOfPowerBlock(props, org.tgr.witchercraft.block.PlaceOfPowerBlock.SignType.IGNI)
    );
    
    public static final Block PLACE_OF_POWER_AARD = registerBlock(
        "place_of_power_aard",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
            .mapColor(MapColor.ICE)
            .requiresCorrectToolForDrops()
            .strength(50.0F, 1200.0F)
            .noOcclusion(),
        props -> new org.tgr.witchercraft.block.PlaceOfPowerBlock(props, org.tgr.witchercraft.block.PlaceOfPowerBlock.SignType.AARD)
    );
    
    public static final Block PLACE_OF_POWER_QUEN = registerBlock(
        "place_of_power_quen",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
            .mapColor(MapColor.GOLD)
            .requiresCorrectToolForDrops()
            .strength(50.0F, 1200.0F)
            .noOcclusion(),
        props -> new org.tgr.witchercraft.block.PlaceOfPowerBlock(props, org.tgr.witchercraft.block.PlaceOfPowerBlock.SignType.QUEN)
    );
    
    public static final Block PLACE_OF_POWER_AXII = registerBlock(
        "place_of_power_axii",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
            .mapColor(MapColor.COLOR_PURPLE)
            .requiresCorrectToolForDrops()
            .strength(50.0F, 1200.0F)
            .noOcclusion(),
        props -> new org.tgr.witchercraft.block.PlaceOfPowerBlock(props, org.tgr.witchercraft.block.PlaceOfPowerBlock.SignType.AXII)
    );
    
    public static final Block PLACE_OF_POWER_YRDEN = registerBlock(
        "place_of_power_yrden",
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
            .mapColor(MapColor.COLOR_MAGENTA)
            .requiresCorrectToolForDrops()
            .strength(50.0F, 1200.0F)
            .noOcclusion(),
        props -> new org.tgr.witchercraft.block.PlaceOfPowerBlock(props, org.tgr.witchercraft.block.PlaceOfPowerBlock.SignType.YRDEN)
    );

    public static final Item ALCHEMY_TABLE_ITEM = registerBlockItem("alchemy_table", ALCHEMY_TABLE);
    public static final Item GRINDSTONE_WITCHER_ITEM = registerBlockItem("grindstone_witcher", GRINDSTONE_WITCHER);
    public static final Item WITCHER_FORGE_ITEM = registerBlockItem("witcher_forge_block", WITCHER_FORGE);
    
    // Quest Board Items
    public static final Item QUEST_BOARD_KAEDWEN_ITEM = registerBlockItem("quest_board_kaedwen", QUEST_BOARD_KAEDWEN);
    public static final Item QUEST_BOARD_REDANIA_ITEM = registerBlockItem("quest_board_redania", QUEST_BOARD_REDANIA);
    public static final Item QUEST_BOARD_TEMERIA_ITEM = registerBlockItem("quest_board_temeria", QUEST_BOARD_TEMERIA);
    public static final Item QUEST_BOARD_NILFGAARD_ITEM = registerBlockItem("quest_board_nilfgaard", QUEST_BOARD_NILFGAARD);
    public static final Item QUEST_BOARD_SKELLIGE_ITEM = registerBlockItem("quest_board_skellige", QUEST_BOARD_SKELLIGE);
    public static final Item QUEST_BOARD_WITCHERS_ITEM = registerBlockItem("quest_board_witchers", QUEST_BOARD_WITCHERS);
    public static final Item QUEST_BOARD_SCOIA_TAEL_ITEM = registerBlockItem("quest_board_scoia_tael", QUEST_BOARD_SCOIA_TAEL);
    
    public static final Item KAEDWENI_NOTICE_BOARD_ITEM = registerBlockItem("kaedweni_notice_board", KAEDWENI_NOTICE_BOARD);
    public static final Item HERBAL_DRYING_RACK_ITEM = registerBlockItem("herbal_drying_rack", HERBAL_DRYING_RACK);
    public static final Item FROZEN_WELL_CORE_ITEM = registerBlockItem("frozen_well_core", FROZEN_WELL_CORE);
    public static final Item GATEHOUSE_PORTCULLIS_ITEM = registerBlockItem("gatehouse_portcullis", GATEHOUSE_PORTCULLIS);
    public static final Item KAEDWENI_TIMBER_BEAM_ITEM = registerBlockItem("kaedweni_timber_beam", KAEDWENI_TIMBER_BEAM);
    public static final Item KAEDWENI_PLASTER_WALL_ITEM = registerBlockItem("kaedweni_plaster_wall", KAEDWENI_PLASTER_WALL);
    public static final Item KAEDWENI_STONE_FOUNDATION_ITEM = registerBlockItem("kaedweni_stone_foundation", KAEDWENI_STONE_FOUNDATION);
    public static final Item KAEDWENI_STONE_FOUNDATION_MOSSY_ITEM = registerBlockItem("kaedweni_stone_foundation_mossy", KAEDWENI_STONE_FOUNDATION_MOSSY);
    public static final Item KAEDWENI_STONE_FOUNDATION_SLAB_ITEM = registerBlockItem("kaedweni_stone_foundation_slab", KAEDWENI_STONE_FOUNDATION_SLAB);
    public static final Item KAEDWENI_STONE_FOUNDATION_SLAB_MOSSY_ITEM = registerBlockItem("kaedweni_stone_foundation_slab_mossy", KAEDWENI_STONE_FOUNDATION_SLAB_MOSSY);
    public static final Item KAEDWENI_ROOF_SHINGLES_ITEM = registerBlockItem("kaedweni_roof_shingles", KAEDWENI_ROOF_SHINGLES);
    public static final Item KAEDWENI_ROOF_SHINGLE_STAIRS_ITEM = registerBlockItem("kaedweni_roof_shingle_stairs", KAEDWENI_ROOF_SHINGLE_STAIRS);
    public static final Item KAEDWENI_ROOF_SHINGLE_SLAB_ITEM = registerBlockItem("kaedweni_roof_shingle_slab", KAEDWENI_ROOF_SHINGLE_SLAB);
    public static final Item KAEDWENI_ROOF_SHINGLE_VERTICAL_SLAB_ITEM = registerBlockItem("kaedweni_roof_shingle_vertical_slab", KAEDWENI_ROOF_SHINGLE_VERTICAL_SLAB);
    public static final Item REINFORCED_PALISADE_LOG_ITEM = registerBlockItem("reinforced_palisade_log", REINFORCED_PALISADE_LOG);
    public static final Item SUPPLY_CRATE_ITEM = registerBlockItem("supply_crate_kaedwen", SUPPLY_CRATE);
    public static final Item FROSTGLASS_LANTERN_ITEM = registerBlockItem("frostglass_lantern", FROSTGLASS_LANTERN);

    // Custom Ore Block Items
    public static final Item SILVER_ORE_ITEM = registerBlockItem("silver_ore", SILVER_ORE);
    public static final Item DEEPSLATE_SILVER_ORE_ITEM = registerBlockItem("deepslate_silver_ore", DEEPSLATE_SILVER_ORE);
    public static final Item SILVER_BLOCK_ITEM = registerBlockItem("silver_block", SILVER_BLOCK);
    public static final Item RAW_SILVER_BLOCK_ITEM = registerBlockItem("raw_silver_block", RAW_SILVER_BLOCK);
    
    public static final Item METEORITE_ORE_ITEM = registerBlockItem("meteorite_ore", METEORITE_ORE);
    public static final Item METEORITE_BLOCK_ITEM = registerBlockItem("meteorite_block", METEORITE_BLOCK);
    public static final Item RAW_METEORITE_BLOCK_ITEM = registerBlockItem("raw_meteorite_block", RAW_METEORITE_BLOCK);
    
    public static final Item DARK_IRON_ORE_ITEM = registerBlockItem("dark_iron_ore", DARK_IRON_ORE);
    public static final Item DEEPSLATE_DARK_IRON_ORE_ITEM = registerBlockItem("deepslate_dark_iron_ore", DEEPSLATE_DARK_IRON_ORE);
    public static final Item DARK_IRON_BLOCK_ITEM = registerBlockItem("dark_iron_block", DARK_IRON_BLOCK);
    public static final Item RAW_DARK_IRON_BLOCK_ITEM = registerBlockItem("raw_dark_iron_block", RAW_DARK_IRON_BLOCK);
    
    public static final Item DIMERITIUM_ORE_ITEM = registerBlockItem("dimeritium_ore", DIMERITIUM_ORE);
    public static final Item DEEPSLATE_DIMERITIUM_ORE_ITEM = registerBlockItem("deepslate_dimeritium_ore", DEEPSLATE_DIMERITIUM_ORE);
    public static final Item DIMERITIUM_BLOCK_ITEM = registerBlockItem("dimeritium_block", DIMERITIUM_BLOCK);
    public static final Item RAW_DIMERITIUM_BLOCK_ITEM = registerBlockItem("raw_dimeritium_block", RAW_DIMERITIUM_BLOCK);

    // Herb Block Items (herbs don't need block items - they drop herb items directly)
    
    // Temerian Architecture Block Items
    public static final Item TEMERI_STONE_BRICKS_ITEM = registerBlockItem("temeri_stone_bricks", TEMERI_STONE_BRICKS);
    public static final Item TEMERIAN_COBBLESTONE_ITEM = registerBlockItem("temerian_cobblestone", TEMERIAN_COBBLESTONE);
    public static final Item TEMERIAN_ROOF_TILE_ITEM = registerBlockItem("temerian_roof_tile", TEMERIAN_ROOF_TILE);
    public static final Item TEMERIAN_TIMBER_FRAME_ITEM = registerBlockItem("temerian_timber_frame", TEMERIAN_TIMBER_FRAME);
    public static final Item TEMERIAN_PLASTER_ITEM = registerBlockItem("temerian_plaster", TEMERIAN_PLASTER);
    
    // Nilfgaardian Architecture Block Items
    public static final Item NILFGAARDIAN_BLACK_STONE_ITEM = registerBlockItem("nilfgaardian_black_stone", NILFGAARDIAN_BLACK_STONE);
    public static final Item NILFGAARDIAN_MARBLE_ITEM = registerBlockItem("nilfgaardian_marble", NILFGAARDIAN_MARBLE);
    public static final Item NILFGAARDIAN_PILLAR_ITEM = registerBlockItem("nilfgaardian_pillar", NILFGAARDIAN_PILLAR);
    public static final Item NILFGAARDIAN_BANNER_ITEM = registerBlockItem("nilfgaardian_banner", NILFGAARDIAN_BANNER);
    public static final Item NILFGAARDIAN_PAVED_STONE_ITEM = registerBlockItem("nilfgaardian_paved_stone", NILFGAARDIAN_PAVED_STONE);
    
    // Skellige Architecture Block Items
    public static final Item SKELLIGE_CARVED_STONE_ITEM = registerBlockItem("skellige_carved_stone", SKELLIGE_CARVED_STONE);
    public static final Item SKELLIGE_DRIFTWOOD_ITEM = registerBlockItem("skellige_driftwood", SKELLIGE_DRIFTWOOD);
    public static final Item SKELLIGE_STONE_SHINGLE_ITEM = registerBlockItem("skellige_stone_shingle", SKELLIGE_STONE_SHINGLE);
    public static final Item SKELLIGE_LONGHOUSE_BEAM_ITEM = registerBlockItem("skellige_longhouse_beam", SKELLIGE_LONGHOUSE_BEAM);
    
    // Dwarven Ruins Block Items
    public static final Item DWARVEN_CARVED_STONE_ITEM = registerBlockItem("dwarven_carved_stone", DWARVEN_CARVED_STONE);
    public static final Item DWARVEN_BRONZE_TRIM_ITEM = registerBlockItem("dwarven_bronze_trim", DWARVEN_BRONZE_TRIM);
    public static final Item DWARVEN_MOSSY_STONE_ITEM = registerBlockItem("dwarven_mossy_stone", DWARVEN_MOSSY_STONE);
    public static final Item DWARVEN_PILLAR_ITEM = registerBlockItem("dwarven_pillar", DWARVEN_PILLAR);
    
    // Elven Ruins Block Items
    public static final Item ELVEN_WHITE_STONE_ITEM = registerBlockItem("elven_white_stone", ELVEN_WHITE_STONE);
    public static final Item ELVEN_VINE_COVERED_STONE_ITEM = registerBlockItem("elven_vine_covered_stone", ELVEN_VINE_COVERED_STONE);
    public static final Item ELVEN_GLOWING_RUNE_ITEM = registerBlockItem("elven_glowing_rune", ELVEN_GLOWING_RUNE);
    public static final Item ELVEN_ARCHWAY_ITEM = registerBlockItem("elven_archway", ELVEN_ARCHWAY);
    
    // Landmark Block Items
    public static final Item PLACE_OF_POWER_STONE_ITEM = registerBlockItem("place_of_power_stone", PLACE_OF_POWER_STONE);
    public static final Item PLACE_OF_POWER_RUNE_STONE_ITEM = registerBlockItem("place_of_power_rune_stone", PLACE_OF_POWER_RUNE_STONE);
    public static final Item PLACE_OF_POWER_PEDESTAL_ITEM = registerBlockItem("place_of_power_pedestal", PLACE_OF_POWER_PEDESTAL);
    public static final Item ANCIENT_OAK_LOG_ITEM = registerBlockItem("ancient_oak_log", ANCIENT_OAK_LOG);
    public static final Item ANCIENT_OAK_LEAVES_ITEM = registerBlockItem("ancient_oak_leaves", ANCIENT_OAK_LEAVES);
    public static final Item ANCIENT_TREE_ROOTS_ITEM = registerBlockItem("ancient_tree_roots", ANCIENT_TREE_ROOTS);
    
    // Environmental Storytelling Block Items
    public static final Item RUSTED_SWORD_ITEM = registerBlockItem("rusted_sword", RUSTED_SWORD);
    public static final Item RUSTED_ARMOR_STAND_ITEM = registerBlockItem("rusted_armor_stand", RUSTED_ARMOR_STAND);
    public static final Item MASS_GRAVE_MARKER_ITEM = registerBlockItem("mass_grave_marker", MASS_GRAVE_MARKER);
    public static final Item BROKEN_BANNER_POLE_ITEM = registerBlockItem("broken_banner_pole", BROKEN_BANNER_POLE);
    public static final Item BATTLEFIELD_CRATER_ITEM = registerBlockItem("battlefield_crater", BATTLEFIELD_CRATER);
    public static final Item CORPSE_REMAINS_ITEM = registerBlockItem("corpse_remains", CORPSE_REMAINS);
    public static final Item CHARRED_LOG_ITEM = registerBlockItem("charred_log", CHARRED_LOG);
    public static final Item BROKEN_COBBLESTONE_ITEM = registerBlockItem("broken_cobblestone", BROKEN_COBBLESTONE);
    public static final Item OVERGROWN_THATCH_ITEM = registerBlockItem("overgrown_thatch", OVERGROWN_THATCH);
    public static final Item REFUGEE_TENT_ITEM = registerBlockItem("refugee_tent", REFUGEE_TENT);
    public static final Item CRACKED_STONE_BRICKS_ITEM = registerBlockItem("cracked_stone_bricks_witcher", CRACKED_STONE_BRICKS);
    public static final Item VINE_COVERED_PILLAR_ITEM = registerBlockItem("vine_covered_pillar", VINE_COVERED_PILLAR);
    public static final Item COLLAPSED_ARCH_ITEM = registerBlockItem("collapsed_arch", COLLAPSED_ARCH);
    public static final Item ARCHAEOLOGICAL_DIRT_ITEM = registerBlockItem("archaeological_dirt", ARCHAEOLOGICAL_DIRT);
    
    // Flora Block Items
    public static final Item DRIFTWOOD_LOG_ITEM = registerBlockItem("driftwood_log", DRIFTWOOD_LOG);
    public static final Item WATER_REED_ITEM = registerBlockItem("water_reed", WATER_REED);
    public static final Item CATTAIL_PLANT_ITEM = registerBlockItem("cattail_plant", CATTAIL_PLANT);
    public static final Item SWAMP_VINE_ITEM = registerBlockItem("swamp_vine", SWAMP_VINE);
    public static final Item TOXIC_MUSHROOM_ITEM = registerBlockItem("toxic_mushroom", TOXIC_MUSHROOM);
    public static final Item MOUNTAIN_LICHEN_ITEM = registerBlockItem("mountain_lichen", MOUNTAIN_LICHEN);
    public static final Item ALPINE_FLOWER_ITEM = registerBlockItem("alpine_flower", ALPINE_FLOWER);
    public static final Item SNOW_MOSS_ITEM = registerBlockItem("snow_moss", SNOW_MOSS);
    public static final Item GRAPE_VINE_ITEM = registerBlockItem("grape_vine", GRAPE_VINE);
    public static final Item LAVENDER_PLANT_ITEM = registerBlockItem("lavender_plant", LAVENDER_PLANT);
    public static final Item OLIVE_TREE_LOG_ITEM = registerBlockItem("olive_tree_log", OLIVE_TREE_LOG);
    public static final Item CYPRESS_LOG_ITEM = registerBlockItem("cypress_log", CYPRESS_LOG);
    
    // Monster Nest Block Items
    public static final Item NEKKER_NEST_ITEM = registerBlockItem("nekker_nest", NEKKER_NEST);
    public static final Item DROWNED_NEST_ITEM = registerBlockItem("drowned_nest", DROWNED_NEST);
    public static final Item GHOUL_NEST_ITEM = registerBlockItem("ghoul_nest", GHOUL_NEST);
    public static final Item WRAITH_CURSED_STONE_ITEM = registerBlockItem("wraith_cursed_stone", WRAITH_CURSED_STONE);
    
    // Places of Power Block Items
    public static final Item PLACE_OF_POWER_IGNI_ITEM = registerBlockItem("place_of_power_igni", PLACE_OF_POWER_IGNI);
    public static final Item PLACE_OF_POWER_AARD_ITEM = registerBlockItem("place_of_power_aard", PLACE_OF_POWER_AARD);
    public static final Item PLACE_OF_POWER_QUEN_ITEM = registerBlockItem("place_of_power_quen", PLACE_OF_POWER_QUEN);
    public static final Item PLACE_OF_POWER_AXII_ITEM = registerBlockItem("place_of_power_axii", PLACE_OF_POWER_AXII);
    public static final Item PLACE_OF_POWER_YRDEN_ITEM = registerBlockItem("place_of_power_yrden", PLACE_OF_POWER_YRDEN);

    private ModBlocks() {
    }

    private static <T extends Block> T registerBlock(String name,
                                                      Supplier<BlockBehaviour.Properties> propertiesSupplier,
                                                      BlockFactory<T> factory) {
        Objects.requireNonNull(propertiesSupplier, "Block property supplier cannot be null");
        Objects.requireNonNull(factory, "Block factory cannot be null");

        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, name);
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, id);
        BlockBehaviour.Properties properties = propertiesSupplier.get().setId(key);
        T block = factory.create(properties);
        return Registry.register(BuiltInRegistries.BLOCK, key, block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return ModItems.register(name, properties -> new BlockItem(block, properties.useBlockDescriptionPrefix()));
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> {
            entries.accept(ALCHEMY_TABLE_ITEM);
            entries.accept(GRINDSTONE_WITCHER_ITEM);
            entries.accept(WITCHER_FORGE_ITEM);
            
            // Quest Boards
            entries.accept(QUEST_BOARD_KAEDWEN_ITEM);
            entries.accept(QUEST_BOARD_REDANIA_ITEM);
            entries.accept(QUEST_BOARD_TEMERIA_ITEM);
            entries.accept(QUEST_BOARD_NILFGAARD_ITEM);
            entries.accept(QUEST_BOARD_SKELLIGE_ITEM);
            entries.accept(QUEST_BOARD_WITCHERS_ITEM);
            entries.accept(QUEST_BOARD_SCOIA_TAEL_ITEM);
            
            entries.accept(KAEDWENI_NOTICE_BOARD_ITEM);
            entries.accept(HERBAL_DRYING_RACK_ITEM);
            entries.accept(GATEHOUSE_PORTCULLIS_ITEM);
            entries.accept(SUPPLY_CRATE_ITEM);
            entries.accept(FROSTGLASS_LANTERN_ITEM);
            entries.accept(FROZEN_WELL_CORE_ITEM);
            
            // Monster Nests
            entries.accept(NEKKER_NEST_ITEM);
            entries.accept(DROWNED_NEST_ITEM);
            entries.accept(GHOUL_NEST_ITEM);
            entries.accept(WRAITH_CURSED_STONE_ITEM);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
            // Custom Ore Blocks
            entries.accept(SILVER_ORE_ITEM);
            entries.accept(DEEPSLATE_SILVER_ORE_ITEM);
            entries.accept(SILVER_BLOCK_ITEM);
            entries.accept(RAW_SILVER_BLOCK_ITEM);
            
            entries.accept(METEORITE_ORE_ITEM);
            entries.accept(METEORITE_BLOCK_ITEM);
            entries.accept(RAW_METEORITE_BLOCK_ITEM);
            
            entries.accept(DARK_IRON_ORE_ITEM);
            entries.accept(DEEPSLATE_DARK_IRON_ORE_ITEM);
            entries.accept(DARK_IRON_BLOCK_ITEM);
            entries.accept(RAW_DARK_IRON_BLOCK_ITEM);
            
            entries.accept(DIMERITIUM_ORE_ITEM);
            entries.accept(DEEPSLATE_DIMERITIUM_ORE_ITEM);
            entries.accept(DIMERITIUM_BLOCK_ITEM);
            entries.accept(RAW_DIMERITIUM_BLOCK_ITEM);
            
            // Temerian Architecture
            entries.accept(TEMERI_STONE_BRICKS_ITEM);
            entries.accept(TEMERIAN_COBBLESTONE_ITEM);
            entries.accept(TEMERIAN_ROOF_TILE_ITEM);
            entries.accept(TEMERIAN_TIMBER_FRAME_ITEM);
            entries.accept(TEMERIAN_PLASTER_ITEM);
            
            // Nilfgaardian Architecture
            entries.accept(NILFGAARDIAN_BLACK_STONE_ITEM);
            entries.accept(NILFGAARDIAN_MARBLE_ITEM);
            entries.accept(NILFGAARDIAN_PILLAR_ITEM);
            entries.accept(NILFGAARDIAN_BANNER_ITEM);
            entries.accept(NILFGAARDIAN_PAVED_STONE_ITEM);
            
            // Skellige Architecture
            entries.accept(SKELLIGE_CARVED_STONE_ITEM);
            entries.accept(SKELLIGE_DRIFTWOOD_ITEM);
            entries.accept(SKELLIGE_STONE_SHINGLE_ITEM);
            entries.accept(SKELLIGE_LONGHOUSE_BEAM_ITEM);
            
            // Dwarven Ruins
            entries.accept(DWARVEN_CARVED_STONE_ITEM);
            entries.accept(DWARVEN_BRONZE_TRIM_ITEM);
            entries.accept(DWARVEN_MOSSY_STONE_ITEM);
            entries.accept(DWARVEN_PILLAR_ITEM);
            
            // Elven Ruins
            entries.accept(ELVEN_WHITE_STONE_ITEM);
            entries.accept(ELVEN_VINE_COVERED_STONE_ITEM);
            entries.accept(ELVEN_GLOWING_RUNE_ITEM);
            entries.accept(ELVEN_ARCHWAY_ITEM);
            
            // Kaedwen Architecture (existing)
            entries.accept(KAEDWENI_TIMBER_BEAM_ITEM);
            entries.accept(KAEDWENI_PLASTER_WALL_ITEM);
            entries.accept(KAEDWENI_STONE_FOUNDATION_ITEM);
            entries.accept(KAEDWENI_STONE_FOUNDATION_MOSSY_ITEM);
            entries.accept(KAEDWENI_STONE_FOUNDATION_SLAB_ITEM);
            entries.accept(KAEDWENI_STONE_FOUNDATION_SLAB_MOSSY_ITEM);
            entries.accept(KAEDWENI_ROOF_SHINGLES_ITEM);
            entries.accept(KAEDWENI_ROOF_SHINGLE_STAIRS_ITEM);
            entries.accept(KAEDWENI_ROOF_SHINGLE_SLAB_ITEM);
            entries.accept(KAEDWENI_ROOF_SHINGLE_VERTICAL_SLAB_ITEM);
            entries.accept(REINFORCED_PALISADE_LOG_ITEM);
            
            // Landmarks
            entries.accept(PLACE_OF_POWER_STONE_ITEM);
            entries.accept(PLACE_OF_POWER_RUNE_STONE_ITEM);
            entries.accept(PLACE_OF_POWER_PEDESTAL_ITEM);
            entries.accept(ANCIENT_OAK_LOG_ITEM);
            entries.accept(ANCIENT_OAK_LEAVES_ITEM);
            entries.accept(ANCIENT_TREE_ROOTS_ITEM);
            
            // Environmental Storytelling
            entries.accept(RUSTED_SWORD_ITEM);
            entries.accept(RUSTED_ARMOR_STAND_ITEM);
            entries.accept(MASS_GRAVE_MARKER_ITEM);
            entries.accept(BROKEN_BANNER_POLE_ITEM);
            entries.accept(BATTLEFIELD_CRATER_ITEM);
            entries.accept(CORPSE_REMAINS_ITEM);
            entries.accept(CHARRED_LOG_ITEM);
            entries.accept(BROKEN_COBBLESTONE_ITEM);
            entries.accept(OVERGROWN_THATCH_ITEM);
            entries.accept(REFUGEE_TENT_ITEM);
            entries.accept(CRACKED_STONE_BRICKS_ITEM);
            entries.accept(VINE_COVERED_PILLAR_ITEM);
            entries.accept(COLLAPSED_ARCH_ITEM);
            entries.accept(ARCHAEOLOGICAL_DIRT_ITEM);
            
            // Flora & Nature
            entries.accept(DRIFTWOOD_LOG_ITEM);
            entries.accept(WATER_REED_ITEM);
            entries.accept(CATTAIL_PLANT_ITEM);
            entries.accept(SWAMP_VINE_ITEM);
            entries.accept(TOXIC_MUSHROOM_ITEM);
            entries.accept(MOUNTAIN_LICHEN_ITEM);
            entries.accept(ALPINE_FLOWER_ITEM);
            entries.accept(SNOW_MOSS_ITEM);
            entries.accept(GRAPE_VINE_ITEM);
            entries.accept(LAVENDER_PLANT_ITEM);
            entries.accept(OLIVE_TREE_LOG_ITEM);
            entries.accept(CYPRESS_LOG_ITEM);
        });

        Witchercraft.LOGGER.info("Registered WitcherCraft blocks");
    }
    private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties settings) {

        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, name);
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, id);
        Block block = blockFactory.apply(settings.setId(key));

        return Registry.register(BuiltInRegistries.BLOCK, key, block);
    }

    @FunctionalInterface
    interface BlockFactory<T extends Block> extends Function<BlockBehaviour.Properties, T> {
        @Override
        T apply(BlockBehaviour.Properties properties);

        default T create(BlockBehaviour.Properties properties) {
            return apply(properties);
        }
    }
}
