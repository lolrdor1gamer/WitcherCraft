package org.tgr.witchercraft.registry.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import org.tgr.witchercraft.Witchercraft;

public final class WitcherEntityTypeTags {

    public static final TagKey<EntityType<?>> HUMANOIDS =
        TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "humanoids"));
    public static final TagKey<EntityType<?>> MONSTERS =
        TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "monsters"));

    private WitcherEntityTypeTags() {
    }
}
