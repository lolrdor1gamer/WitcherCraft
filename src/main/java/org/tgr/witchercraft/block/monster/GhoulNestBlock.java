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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.tgr.witchercraft.registry.ModEntities;
import org.tgr.witchercraft.entity.monster.GhoulEntity;
import org.tgr.witchercraft.entity.monster.AlghoulEntity;

/**
 * Ghoul nest block - spawns Ghouls and Alghouls near graveyards and battlefields
 */
public class GhoulNestBlock extends Block {
    
    private static final int MAX_GHOULS_NEARBY = 12;
    private static final int SPAWN_RADIUS = 16;
    
    public GhoulNestBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
    
    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }
    
    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // Check if night
        long dayTime = level.getDayTime() % 24000;
        boolean isNight = dayTime >= 13000 && dayTime < 23000;
        
        if (!isNight && random.nextFloat() > 0.15f) {
            return;
        }
        
        AABB searchBox = new AABB(pos).inflate(SPAWN_RADIUS);
        long nearbyGhouls = level.getEntitiesOfClass(GhoulEntity.class, searchBox).size() +
                           level.getEntitiesOfClass(AlghoulEntity.class, searchBox).size();
        
        if (nearbyGhouls >= MAX_GHOULS_NEARBY) {
            return;
        }
        
        int spawnCount = Math.min(2 + random.nextInt(3), (int)(MAX_GHOULS_NEARBY - nearbyGhouls));
        
        for (int i = 0; i < spawnCount; i++) {
            BlockPos spawnPos = findSpawnLocation(level, pos, random);
            if (spawnPos == null) continue;
            
            // 15% chance for alghoul, otherwise ghoul
            if (random.nextFloat() < 0.15f) {
                AlghoulEntity alghoul = ModEntities.ALGHOUL.create(level, EntitySpawnReason.SPAWNER);
                if (alghoul != null) {
                    alghoul.setPos(spawnPos.getX() + 0.5, spawnPos.getY() + 0.1, spawnPos.getZ() + 0.5);
                    alghoul.setYRot(random.nextFloat() * 360.0f);
                    level.addFreshEntity(alghoul);
                }
            } else {
                GhoulEntity ghoul = ModEntities.GHOUL.create(level, EntitySpawnReason.SPAWNER);
                if (ghoul != null) {
                    ghoul.setPos(spawnPos.getX() + 0.5, spawnPos.getY() + 0.1, spawnPos.getZ() + 0.5);
                    ghoul.setYRot(random.nextFloat() * 360.0f);
                    level.addFreshEntity(ghoul);
                }
            }
        }
        
        level.playSound(null, pos, SoundEvents.ZOMBIE_AMBIENT, SoundSource.HOSTILE, 0.8f, 0.6f);
    }
    
    private BlockPos findSpawnLocation(ServerLevel level, BlockPos nestPos, RandomSource random) {
        for (int attempt = 0; attempt < 5; attempt++) {
            BlockPos checkPos = nestPos.offset(random.nextInt(10) - 5, 0, random.nextInt(10) - 5);
            
            while (!level.getBlockState(checkPos).isSolid() && checkPos.getY() > level.getMinY()) {
                checkPos = checkPos.below();
            }
            checkPos = checkPos.above();
            
            if (level.getBlockState(checkPos).isAir() && level.getBlockState(checkPos.below()).isSolid()) {
                return checkPos;
            }
        }
        return null;
    }
    
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, net.minecraft.world.entity.player.Player player) {
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            AABB searchBox = new AABB(pos).inflate(SPAWN_RADIUS);
            for (GhoulEntity ghoul : serverLevel.getEntitiesOfClass(GhoulEntity.class, searchBox)) {
                ghoul.setTarget(player);
            }
            for (AlghoulEntity alghoul : serverLevel.getEntitiesOfClass(AlghoulEntity.class, searchBox)) {
                alghoul.setTarget(player);
            }
            
            RandomSource random = level.getRandom();
            if (random.nextFloat() < 0.4f) {
                level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        new ItemStack(Items.BONE, 2 + random.nextInt(4))));
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
}
