package org.tgr.witchercraft.block.kaedwen;

import net.minecraft.world.level.block.RotatedPillarBlock;

/**
 * Axis-aware beam used throughout Kaedweni structures. Extends {@link RotatedPillarBlock} so it
 * benefits from vanilla log-style rotation handling.
 */
public class KaedweniTimberBeamBlock extends RotatedPillarBlock {

    public KaedweniTimberBeamBlock(Properties properties) {
        super(properties);
    }
}