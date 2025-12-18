package org.tgr.witchercraft.block.ore;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Dimeritium Ore block - generates Y=20-40 in Nilfgaard only.
 * Used for anti-magic items, bombs, and shackles (lore-accurate).
 */
public class DimeritiumOreBlock extends Block {
    public DimeritiumOreBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
