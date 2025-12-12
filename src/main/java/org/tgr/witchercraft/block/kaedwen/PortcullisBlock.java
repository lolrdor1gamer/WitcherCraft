package org.tgr.witchercraft.block.kaedwen;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Thin decorative block acting as a placeholder for the Kaedweni gatehouse portcullis. It behaves
 * like a solid barrier but occupies only a fraction of the block space for aesthetics. Future
 * iterations can add redstone-driven open/close logic.
 */
public class PortcullisBlock extends HorizontalDirectionalBlock {

    private static final MapCodec<PortcullisBlock> CODEC = simpleCodec(PortcullisBlock::new);
    private static final VoxelShape NORTH_SOUTH = Block.box(2.0D, 0.0D, 6.0D, 14.0D, 16.0D, 10.0D);
    private static final VoxelShape EAST_WEST = Block.box(6.0D, 0.0D, 2.0D, 10.0D, 16.0D, 14.0D);

    public PortcullisBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        return facing.getAxis() == Direction.Axis.X ? EAST_WEST : NORTH_SOUTH;
    }
}