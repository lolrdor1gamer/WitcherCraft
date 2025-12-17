package org.tgr.witchercraft.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.entity.KaedweniSoldierEntity;
import org.tgr.witchercraft.entity.NilfgaardianScoutEntity;
import org.tgr.witchercraft.entity.RedanianGuardEntity;

public final class ModEntities {

    public static final EntityType<KaedweniSoldierEntity> KAEDWENI_SOLDIER = register("kaedweni_soldier",
        FabricEntityTypeBuilder.create(MobCategory.MONSTER, KaedweniSoldierEntity::new)
            .dimensions(EntityDimensions.scalable(0.6F, 1.95F))
            .trackRangeBlocks(8));

    public static final EntityType<RedanianGuardEntity> REDANIAN_GUARD = register("redanian_guard",
        FabricEntityTypeBuilder.create(MobCategory.MONSTER, RedanianGuardEntity::new)
            .dimensions(EntityDimensions.scalable(0.6F, 1.95F))
            .trackRangeBlocks(8));

    public static final EntityType<NilfgaardianScoutEntity> NILFGAARDIAN_SCOUT = register("nilfgaardian_scout",
        FabricEntityTypeBuilder.create(MobCategory.MONSTER, NilfgaardianScoutEntity::new)
            .dimensions(EntityDimensions.scalable(0.6F, 1.95F))
            .trackRangeBlocks(8));

    private ModEntities() {
    }

    public static void initialize() {
        FabricDefaultAttributeRegistry.register(KAEDWENI_SOLDIER, KaedweniSoldierEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(REDANIAN_GUARD, RedanianGuardEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(NILFGAARDIAN_SCOUT, NilfgaardianScoutEntity.createAttributes());
    }

    private static <T extends Entity> EntityType<T> register(String name, FabricEntityTypeBuilder<T> builder) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, name);
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, id);
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }
}
