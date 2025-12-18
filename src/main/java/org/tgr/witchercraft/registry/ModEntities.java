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
import org.tgr.witchercraft.entity.monster.*;
import org.tgr.witchercraft.item.bomb.WitcherBombProjectile;

public final class ModEntities {

    // Human soldiers
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

    // Witcher Monsters - Necrophages
    public static final EntityType<DrownedCorpseEntity> DROWNED_CORPSE = register("drowned_corpse",
        FabricEntityTypeBuilder.create(MobCategory.MONSTER, DrownedCorpseEntity::new)
            .dimensions(EntityDimensions.scalable(0.6F, 1.95F))
            .trackRangeBlocks(8));

    public static final EntityType<NekkerEntity> NEKKER = register("nekker",
        FabricEntityTypeBuilder.create(MobCategory.MONSTER, NekkerEntity::new)
            .dimensions(EntityDimensions.scalable(0.5F, 0.9F)) // Small creature
            .trackRangeBlocks(8));

    public static final EntityType<GhoulEntity> GHOUL = register("ghoul",
        FabricEntityTypeBuilder.create(MobCategory.MONSTER, GhoulEntity::new)
            .dimensions(EntityDimensions.scalable(0.7F, 1.7F))
            .trackRangeBlocks(8));

    public static final EntityType<AlghoulEntity> ALGHOUL = register("alghoul",
        FabricEntityTypeBuilder.create(MobCategory.MONSTER, AlghoulEntity::new)
            .dimensions(EntityDimensions.scalable(0.8F, 1.9F))
            .trackRangeBlocks(8));

    // Witcher Monsters - Specters
    public static final EntityType<WraithEntity> WRAITH = register("wraith",
        FabricEntityTypeBuilder.create(MobCategory.MONSTER, WraithEntity::new)
            .dimensions(EntityDimensions.scalable(0.6F, 1.8F))
            .trackRangeBlocks(10));

    // Witcher Monsters - Cursed
    public static final EntityType<WerewolfEntity> WEREWOLF = register("werewolf",
        FabricEntityTypeBuilder.create(MobCategory.MONSTER, WerewolfEntity::new)
            .dimensions(EntityDimensions.scalable(0.9F, 2.2F)) // Larger than humans
            .trackRangeBlocks(12));

    // Projectiles
    public static final EntityType<WitcherBombProjectile> WITCHER_BOMB_PROJECTILE = register("witcher_bomb_projectile",
        FabricEntityTypeBuilder.<WitcherBombProjectile>create(MobCategory.MISC, WitcherBombProjectile::new)
            .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
            .trackRangeBlocks(4));

    private ModEntities() {
    }

    public static void initialize() {
        // Human soldiers
        FabricDefaultAttributeRegistry.register(KAEDWENI_SOLDIER, KaedweniSoldierEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(REDANIAN_GUARD, RedanianGuardEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(NILFGAARDIAN_SCOUT, NilfgaardianScoutEntity.createAttributes());
        
        // Monsters
        FabricDefaultAttributeRegistry.register(DROWNED_CORPSE, DrownedCorpseEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(NEKKER, NekkerEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(GHOUL, GhoulEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ALGHOUL, AlghoulEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WRAITH, WraithEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WEREWOLF, WerewolfEntity.createAttributes());
    }

    private static <T extends Entity> EntityType<T> register(String name, FabricEntityTypeBuilder<T> builder) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, name);
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, id);
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }
}
