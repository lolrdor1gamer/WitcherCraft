package org.tgr.witchercraft.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

public class KaedweniSoldierEntity extends AbstractHumanSoldierEntity {

    public KaedweniSoldierEntity(EntityType<? extends KaedweniSoldierEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return baseAttributes();
    }
}
