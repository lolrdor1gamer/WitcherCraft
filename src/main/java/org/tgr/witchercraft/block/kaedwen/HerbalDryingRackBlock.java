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
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Decorative drying rack that will later display herbs. Currently provides a thin collision shape so
 * players can walk around it comfortably.
 */
public class HerbalDryingRackBlock extends HorizontalDirectionalBlock {

    private static final MapCodec<HerbalDryingRackBlock> CODEC = simpleCodec(HerbalDryingRackBlock::new);
    private static final VoxelShape NORTH_SOUTH = Block.box(2.0D, 0.0D, 6.0D, 14.0D, 12.0D, 10.0D);
    private static final VoxelShape EAST_WEST = Block.box(6.0D, 0.0D, 2.0D, 10.0D, 12.0D, 14.0D);

    public HerbalDryingRackBlock(Properties properties) {
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
        Direction direction = state.getValue(FACING);
        if (direction == Direction.NORTH || direction == Direction.SOUTH) {
            return NORTH_SOUTH;
        }
        if (direction == Direction.EAST || direction == Direction.WEST) {
            return EAST_WEST;
        }
        return Shapes.block();
    }
}