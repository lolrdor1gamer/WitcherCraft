package org.tgr.witchercraft.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.block.WitcherForge.WitcherForgeEntity;
import org.tgr.witchercraft.block.alchemy.AlchemyTableBlockEntity;
import org.tgr.witchercraft.block.kaedwen.SupplyCrateBlockEntity;

public class ModBlockEntities {
    public static final BlockEntityType<WitcherForgeEntity> WITCHER_FORGE_ENTITY =
            register("witcher_forge", WitcherForgeEntity::new, ModBlocks.WITCHER_FORGE);
    public static final BlockEntityType<AlchemyTableBlockEntity> ALCHEMY_TABLE_ENTITY =
        register("alchemy_table", AlchemyTableBlockEntity::new, ModBlocks.ALCHEMY_TABLE);
    public static final BlockEntityType<SupplyCrateBlockEntity> SUPPLY_CRATE_ENTITY =
        register("supply_crate", SupplyCrateBlockEntity::new, ModBlocks.SUPPLY_CRATE);

    public static void initialize() {
        Witchercraft.LOGGER.info("Registered block entities");
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, name);
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }
}
