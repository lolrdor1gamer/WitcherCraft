package org.tgr.witchercraft.block.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.entity.EntitySpawnReason;
import org.tgr.witchercraft.registry.ModEntities;
import org.tgr.witchercraft.entity.monster.NekkerEntity;

/**
 * Nekker nest block - spawns Nekkers in forests and caves
 * Has growth stages that increase spawn rate and quantity
 */
public class NekkerNestBlock extends Block {
    
    public static final IntegerProperty GROWTH = IntegerProperty.create("growth", 0, 3);
    private static final int MAX_NEKKERS = 15;
    private static final int SPAWN_RADIUS = 16;
    
    public NekkerNestBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(GROWTH, 0));
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(GROWTH);
    }
    
    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }
    
    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int growth = state.getValue(GROWTH);
        
        // Chance to grow
        if (growth < 3 && random.nextInt(10) == 0) {
            level.setBlock(pos, state.setValue(GROWTH, growth + 1), 3);
            return;
        }
        
        // Count existing nekkers
        AABB searchBox = new AABB(pos).inflate(SPAWN_RADIUS);
        int existingNekkers = level.getEntitiesOfClass(NekkerEntity.class, searchBox).size();
        
        if (existingNekkers >= MAX_NEKKERS) {
            return;
        }
        
        // Check if night
        long dayTime = level.getDayTime() % 24000;
        boolean isNight = dayTime >= 13000 && dayTime < 23000;
        
        int spawnChance = (isNight ? 15 : 8) + (growth * 3);
        
        if (random.nextInt(100) < spawnChance) {
            int toSpawn = Math.min(3 + random.nextInt(3 + growth * 2), MAX_NEKKERS - existingNekkers);
            
            for (int i = 0; i < toSpawn; i++) {
                spawnNekker(level, pos, random);
            }
        }
    }
    
    private void spawnNekker(ServerLevel level, BlockPos nestPos, RandomSource random) {
        BlockPos spawnPos = nestPos.offset(
            random.nextIntBetweenInclusive(-5, 5),
            0,
            random.nextIntBetweenInclusive(-5, 5)
        );
        
        // Find surface
        while (!level.getBlockState(spawnPos).isAir() && spawnPos.getY() < nestPos.getY() + 5) {
            spawnPos = spawnPos.above();
        }
        
        if (!level.getBlockState(spawnPos).isAir() || !level.getBlockState(spawnPos.below()).isSolid()) {
            return;
        }
        
        NekkerEntity nekker = ModEntities.NEKKER.create(level, EntitySpawnReason.SPAWNER);
        if (nekker != null) {
            nekker.setPos(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5);
            nekker.setYRot(random.nextFloat() * 360.0f);
            level.addFreshEntity(nekker);
        }
    }
    
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                                Player player, BlockHitResult hitResult) {
        if (!level.isClientSide() && isHoldingBomb(player)) {
            destroyNest(state, (ServerLevel) level, pos, player);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
    
    private boolean isHoldingBomb(Player player) {
        String mainItem = player.getMainHandItem().getItem().toString();
        String offItem = player.getOffhandItem().getItem().toString();
        return mainItem.contains("bomb") || offItem.contains("bomb");
    }
    
    private void destroyNest(BlockState state, ServerLevel level, BlockPos pos, Player player) {
        int growth = state.getValue(GROWTH);
        
        level.playSound(null, pos, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 1.0F, 1.0F);
        level.destroyBlock(pos, false);
        
        // Drop loot based on growth
        RandomSource random = level.getRandom();
        int lootMultiplier = 1 + growth;
        
        level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                new ItemStack(Items.STICK, random.nextInt(3) * lootMultiplier)));
        
        if (random.nextFloat() < 0.3f * lootMultiplier) {
            level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    new ItemStack(Items.FLINT, 1 + random.nextInt(2))));
        }
        
        // Anger nearby nekkers
        AABB searchBox = new AABB(pos).inflate(SPAWN_RADIUS);
        for (NekkerEntity nekker : level.getEntitiesOfClass(NekkerEntity.class, searchBox)) {
            nekker.setTarget(player);
        }
    }
    
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            AABB searchBox = new AABB(pos).inflate(SPAWN_RADIUS);
            for (NekkerEntity nekker : serverLevel.getEntitiesOfClass(NekkerEntity.class, searchBox)) {
                nekker.setTarget(player);
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
}
