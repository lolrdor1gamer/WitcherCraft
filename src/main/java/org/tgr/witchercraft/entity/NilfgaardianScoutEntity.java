package org.tgr.witchercraft.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

public class NilfgaardianScoutEntity extends AbstractHumanSoldierEntity {

    public NilfgaardianScoutEntity(EntityType<? extends NilfgaardianScoutEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return baseAttributes();
    }
}
