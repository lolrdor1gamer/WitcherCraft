package org.tgr.witchercraft.block.flora;

import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.tgr.witchercraft.worldgen.tree.CypressTreeGrower;

/**
 * Cypress tree sapling - grows into tall cypress trees
 */
public class CypressTreeSaplingBlock extends SaplingBlock {
    
    public CypressTreeSaplingBlock(BlockBehaviour.Properties properties) {
        super(CypressTreeGrower.CYPRESS, properties);
    }
}
