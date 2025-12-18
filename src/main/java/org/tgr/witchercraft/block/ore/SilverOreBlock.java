package org.tgr.witchercraft.block.ore;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Silver Ore block - generates in mountains (Y=10-50).
 * Used to craft silver swords with monster-slaying bonus damage.
 */
public class SilverOreBlock extends Block {
    public SilverOreBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
}
