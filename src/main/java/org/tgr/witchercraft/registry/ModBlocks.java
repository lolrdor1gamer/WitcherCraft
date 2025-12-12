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
import net.minecraft.world.level.material.MapColor;
import org.tgr.witchercraft.Witchercraft;
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

    public static final Item ALCHEMY_TABLE_ITEM = registerBlockItem("alchemy_table", ALCHEMY_TABLE);
    public static final Item GRINDSTONE_WITCHER_ITEM = registerBlockItem("grindstone_witcher", GRINDSTONE_WITCHER);
    public static final Item WITCHER_FORGE_ITEM = registerBlockItem("witcher_forge_block", WITCHER_FORGE);
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
            entries.accept(KAEDWENI_NOTICE_BOARD_ITEM);
            entries.accept(HERBAL_DRYING_RACK_ITEM);
            entries.accept(GATEHOUSE_PORTCULLIS_ITEM);
            entries.accept(SUPPLY_CRATE_ITEM);
            entries.accept(FROSTGLASS_LANTERN_ITEM);
            entries.accept(FROZEN_WELL_CORE_ITEM);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
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
