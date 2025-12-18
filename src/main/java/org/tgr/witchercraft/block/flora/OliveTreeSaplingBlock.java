package org.tgr.witchercraft.block.flora;

import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.tgr.witchercraft.worldgen.tree.OliveTreeGrower;

/**
 * Olive tree sapling - grows into olive trees
 */
public class OliveTreeSaplingBlock extends SaplingBlock {
    
    public OliveTreeSaplingBlock(BlockBehaviour.Properties properties) {
        super(OliveTreeGrower.OLIVE, properties);
    }
}
