package org.tgr.witchercraft.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

public class RedanianGuardEntity extends AbstractHumanSoldierEntity {

    public RedanianGuardEntity(EntityType<? extends RedanianGuardEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return baseAttributes();
    }
}
