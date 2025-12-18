package org.tgr.witchercraft.block.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
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
import org.tgr.witchercraft.entity.monster.WraithEntity;

/**
 * Wraith cursed stone - spawns Wraiths only at night near haunted locations
 */
public class WraithCursedStoneBlock extends Block {
    
    private static final int MAX_WRAITHS_NEARBY = 6;
    private static final int SPAWN_RADIUS = 20;
    
    public WraithCursedStoneBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
    
    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }
    
    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // Only spawn at night
        long dayTime = level.getDayTime() % 24000;
        boolean isNight = dayTime >= 13000 && dayTime < 23000;
        
        if (!isNight) {
            return;
        }
        
        AABB searchBox = new AABB(pos).inflate(SPAWN_RADIUS);
        long nearbyWraiths = level.getEntitiesOfClass(WraithEntity.class, searchBox).size();
        
        if (nearbyWraiths >= MAX_WRAITHS_NEARBY) {
            return;
        }
        
        // Lower spawn chance for wraiths - they are rarer
        if (random.nextFloat() > 0.1f) {
            return;
        }
        
        int spawnCount = Math.min(1 + random.nextInt(3), (int)(MAX_WRAITHS_NEARBY - nearbyWraiths));
        
        for (int i = 0; i < spawnCount; i++) {
            WraithEntity wraith = ModEntities.WRAITH.create(level, EntitySpawnReason.SPAWNER);
            if (wraith != null) {
                double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 8;
                double y = pos.getY() + 1;
                double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 8;
                
                wraith.setPos(x, y, z);
                wraith.setYRot(random.nextFloat() * 360.0f);
                level.addFreshEntity(wraith);
            }
        }
        
        // Eerie sound
        level.playSound(null, pos, SoundEvents.AMBIENT_CAVE.value(), SoundSource.HOSTILE, 1.0f, 0.5f);
    }
    
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        // Ghostly particles
        if (random.nextInt(5) == 0) {
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5);
            double y = pos.getY() + 1.0 + random.nextDouble();
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5);
            
            level.addParticle(ParticleTypes.SOUL, x, y, z, 0, 0.05, 0);
        }
        
        // Occasional ambient sound
        if (random.nextInt(100) == 0) {
            level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(),
                    SoundEvents.AMBIENT_CAVE.value(), SoundSource.AMBIENT,
                    0.3f, random.nextFloat() * 0.4f + 0.8f, false);
        }
    }
    
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, net.minecraft.world.entity.player.Player player) {
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            AABB searchBox = new AABB(pos).inflate(SPAWN_RADIUS);
            for (WraithEntity wraith : serverLevel.getEntitiesOfClass(WraithEntity.class, searchBox)) {
                wraith.setTarget(player);
            }
            
            RandomSource random = level.getRandom();
            if (random.nextFloat() < 0.3f) {
                level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        new ItemStack(Items.ECHO_SHARD, 1)));
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
}
