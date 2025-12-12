package org.tgr.witchercraft.block.kaedwen;

import net.minecraft.world.level.block.IronBarsBlock;

/**
 * Thin plaster infill that connects like panes, allowing timber-frame spacing while keeping
 * collision forgiving. Extends {@link IronBarsBlock} so it inherits connection logic.
 */
public class KaedweniPlasterWallBlock extends IronBarsBlock {

    public KaedweniPlasterWallBlock(Properties properties) {
        super(properties);
    }
}