package org.tgr.witchercraft.block.kaedwen;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Simple placeholder interactive block for the Kaedweni notice board. It currently only displays a
 * message, but will later be replaced with a screen that surfaces contracts.
 */
public class NoticeBoardBlock extends HorizontalDirectionalBlock {

    private static final MapCodec<NoticeBoardBlock> CODEC = simpleCodec(NoticeBoardBlock::new);
    private static final VoxelShape SHAPE_NORTH = Block.box(1.0D, 0.0D, 6.0D, 15.0D, 16.0D, 10.0D);
    private static final VoxelShape SHAPE_SOUTH = Block.box(1.0D, 0.0D, 6.0D, 15.0D, 16.0D, 10.0D);
    private static final VoxelShape SHAPE_EAST = Block.box(6.0D, 0.0D, 1.0D, 10.0D, 16.0D, 15.0D);
    private static final VoxelShape SHAPE_WEST = Block.box(6.0D, 0.0D, 1.0D, 10.0D, 16.0D, 15.0D);

    public NoticeBoardBlock(Properties properties) {
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
        return switch (direction) {
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            default -> Shapes.block();
        };
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.isClientSide()) {
            player.displayClientMessage(player.getName().copy().append(" studies the Kaedweni contracts..."), true);
        }
        return InteractionResult.SUCCESS;
    }
}