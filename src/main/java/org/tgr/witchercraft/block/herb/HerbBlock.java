package org.tgr.witchercraft.block.herb;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

/**
 * Base class for alchemy herbs with quality system and respawn mechanics.
 * Herbs can be Normal (80%), Enhanced (15%), or Superior (5%) quality.
 * Respawns after 3 Minecraft days (72000 ticks = 1 hour real-time).
 */
public class HerbBlock extends BushBlock {
    
    public static final BooleanProperty HARVESTED = BooleanProperty.create("harvested");
    public static final IntegerProperty RESPAWN_TIMER = IntegerProperty.create("respawn_timer", 0, 7);
    
    protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);
    
    private final Supplier<Item> herbItem;
    private final int respawnTime; // in ticks (default: 72000 = 3 MC days)
    
    public HerbBlock(BlockBehaviour.Properties properties, Supplier<Item> herbItem) {
        this(properties, herbItem, 72000);
    }
    
    public HerbBlock(BlockBehaviour.Properties properties, Supplier<Item> herbItem, int respawnTime) {
        super(properties);
        this.herbItem = herbItem;
        this.respawnTime = respawnTime;
        registerDefaultState(stateDefinition.any()
            .setValue(HARVESTED, false)
            .setValue(RESPAWN_TIMER, 0));
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HARVESTED, RESPAWN_TIMER);
    }
    
    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    
    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, 
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide() && !state.getValue(HARVESTED)) {
            // Harvest the herb
            int quantity = 1 + level.random.nextInt(3); // 1-3 items
            
            for (int i = 0; i < quantity; i++) {
                ItemStack herbDrop = new ItemStack(herbItem.get());
                
                // Determine quality (stored in NBT)
                float roll = level.random.nextFloat();
                String quality;
                if (roll < 0.05f) {
                    quality = "superior";
                } else if (roll < 0.20f) {
                    quality = "enhanced";
                } else {
                    quality = "normal";
                }
                
                // TODO: Add quality as custom data component in 1.21
                // For now, just drop the item without quality modifier
                Block.popResource(level, pos, herbDrop);
            }
            
            // Mark as harvested and start respawn timer
            level.setBlock(pos, state.setValue(HARVESTED, true).setValue(RESPAWN_TIMER, 0), 3);
            level.scheduleTick(pos, this, 20); // Check every second
            
            return InteractionResult.SUCCESS;
        }
        
        return InteractionResult.PASS;
    }
    
    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(HARVESTED)) {
            int timer = state.getValue(RESPAWN_TIMER);
            int timerIncrement = 20; // 20 ticks per check = 1 second
            int maxTimerValue = 7; // Stores respawnTime / (72000 / 7) chunks
            
            if (timer < maxTimerValue) {
                level.setBlock(pos, state.setValue(RESPAWN_TIMER, timer + 1), 3);
                level.scheduleTick(pos, this, respawnTime / maxTimerValue);
            } else {
                // Fully respawned
                level.setBlock(pos, state.setValue(HARVESTED, false).setValue(RESPAWN_TIMER, 0), 3);
            }
        }
    }
}
