package org.tgr.witchercraft.block.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.tgr.witchercraft.registry.ModEntities;
import org.tgr.witchercraft.entity.monster.DrownedCorpseEntity;

/**
 * Drowned nest block - spawns Drowners in rivers and swamps
 */
public class DrownedNestBlock extends Block {
    
    private static final int MAX_DROWNERS_NEARBY = 12;
    private static final int SPAWN_RADIUS = 16;
    
    public DrownedNestBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
    
    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }
    
    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // Check if night using day time (13000-23000 is night)
        long dayTime = level.getDayTime() % 24000;
        boolean isNight = dayTime >= 13000 && dayTime < 23000;
        
        if (!isNight && random.nextFloat() > 0.2f) {
            return;
        }
        
        AABB searchBox = new AABB(pos).inflate(SPAWN_RADIUS);
        long nearbyDrowners = level.getEntitiesOfClass(DrownedCorpseEntity.class, searchBox).size();
        
        if (nearbyDrowners >= MAX_DROWNERS_NEARBY) {
            return;
        }
        
        BlockPos spawnPos = findSpawnLocation(level, pos, random);
        if (spawnPos == null) return;
        
        int spawnCount = Math.min(3 + random.nextInt(3), (int)(MAX_DROWNERS_NEARBY - nearbyDrowners));
        
        for (int i = 0; i < spawnCount; i++) {
            DrownedCorpseEntity drowner = ModEntities.DROWNED_CORPSE.create(level, EntitySpawnReason.SPAWNER);
            if (drowner != null) {
                double x = spawnPos.getX() + 0.5 + (random.nextDouble() - 0.5) * 6;
                double y = spawnPos.getY() + 0.1;
                double z = spawnPos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 6;
                
                drowner.setPos(x, y, z);
                drowner.setYRot(random.nextFloat() * 360.0f);
                level.addFreshEntity(drowner);
            }
        }
        
        level.playSound(null, pos, SoundEvents.DROWNED_AMBIENT_WATER, SoundSource.HOSTILE, 1.0f, 0.8f);
    }
    
    private BlockPos findSpawnLocation(ServerLevel level, BlockPos nestPos, RandomSource random) {
        for (int attempt = 0; attempt < 10; attempt++) {
            int xOffset = random.nextInt(10) - 5;
            int zOffset = random.nextInt(10) - 5;
            
            for (int yOffset = -3; yOffset <= 3; yOffset++) {
                BlockPos checkPos = nestPos.offset(xOffset, yOffset, zOffset);
                if (level.getBlockState(checkPos).is(Blocks.WATER)) {
                    BlockPos surfacePos = checkPos;
                    while (level.getBlockState(surfacePos.above()).is(Blocks.WATER) && surfacePos.getY() < nestPos.getY() + 10) {
                        surfacePos = surfacePos.above();
                    }
                    return surfacePos;
                }
            }
        }
        // Fallback to land spawn
        for (int attempt = 0; attempt < 5; attempt++) {
            BlockPos checkPos = nestPos.offset(random.nextInt(8) - 4, 0, random.nextInt(8) - 4);
            while (!level.getBlockState(checkPos).isSolid() && checkPos.getY() > level.getMinY()) {
                checkPos = checkPos.below();
            }
            checkPos = checkPos.above();
            if (level.getBlockState(checkPos).isAir()) return checkPos;
        }
        return null;
    }
    
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, net.minecraft.world.entity.player.Player player) {
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            AABB searchBox = new AABB(pos).inflate(SPAWN_RADIUS);
            for (DrownedCorpseEntity drowner : serverLevel.getEntitiesOfClass(DrownedCorpseEntity.class, searchBox)) {
                drowner.setTarget(player);
            }
            
            RandomSource random = level.getRandom();
            if (random.nextFloat() < 0.5f) {
                level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        new ItemStack(Items.ROTTEN_FLESH, 2 + random.nextInt(3))));
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
}
