package org.tgr.witchercraft.block.kaedwen;

import net.minecraft.world.level.block.Block;

/**
 * Simple structural block with rugged stone properties. Two registry variants (dry/mossy) reuse
 * this class so structure processors can swap them easily.
 */
public class KaedweniStoneFoundationBlock extends Block {

    public KaedweniStoneFoundationBlock(Properties properties) {
        super(properties);
    }
}