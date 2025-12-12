package org.tgr.witchercraft.block.kaedwen;

import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Stair variant for Kaedweni roof shingles so we can keep the same acoustic properties and
 * customize future particle effects in one spot if needed.
 */
public class KaedweniRoofShingleStairBlock extends StairBlock {

    public KaedweniRoofShingleStairBlock(BlockState baseState, BlockBehaviour.Properties properties) {
        super(baseState, properties);
    }
}