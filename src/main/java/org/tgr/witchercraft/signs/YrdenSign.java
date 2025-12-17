package org.tgr.witchercraft.signs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.tgr.witchercraft.player.PlayerAttributes;

import java.util.List;

/**
 * Yrden Sign - Creates a magical trap that slows and damages enemies
 */
public class YrdenSign extends WitcherSign {
    
    private static final int DURATION_TICKS = 200; // 10 seconds
    private static final int TRAP_RADIUS = 3;
    private static final float DAMAGE_PER_SECOND = 2.0f;
    
    public YrdenSign() {
        super("Yrden", 100, 30); // 5 second cooldown, 30 mana cost
    }
    
    @Override
    public boolean cast(Player player, Level level) {
        if (!canCast(player)) {
            return false;
        }
        
        // Calculate trap duration and damage based on Signs attribute
        int trapDuration = DURATION_TICKS;
        float damagePerSecond = DAMAGE_PER_SECOND;
        
        if (player instanceof ServerPlayer && level instanceof ServerLevel) {
            // Get player Signs attribute (temporary until SavedData is fixed)
            PlayerAttributes tempAttributes = new PlayerAttributes();
            int signsAttribute = tempAttributes.getSigns();
            
            // Each Signs point adds 10% duration (20 ticks = 1 second)
            trapDuration = DURATION_TICKS + (signsAttribute * 20);
            // Each Signs point adds 10% damage
            damagePerSecond = DAMAGE_PER_SECOND * (1.0f + (signsAttribute * 0.1f));
        }
        
        // Place trap at player's feet
        BlockPos trapPos = player.blockPosition();
        Vec3 trapCenter = new Vec3(trapPos.getX() + 0.5, trapPos.getY(), trapPos.getZ() + 0.5);
        
        // Play sound
        level.playSound(null, trapPos, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 0.5f, 1.5f);
        
        if (level instanceof ServerLevel serverLevel) {
            // Create the trap effect with scaled damage
            createYrdenTrap(serverLevel, player, trapCenter, trapDuration, damagePerSecond);
        }
        
        return true;
    }
    
    private void createYrdenTrap(ServerLevel level, Player caster, Vec3 center, int duration, float damagePerSecond) {
        // Schedule trap effects over time
        level.getServer().execute(() -> runTrapEffect(level, caster, center, duration, damagePerSecond));
    }
    
    private void runTrapEffect(ServerLevel level, Player caster, Vec3 center, int remainingTicks, float damagePerSecond) {
        if (remainingTicks <= 0) return;
        
        // Spawn particles in a circle
        double radius = TRAP_RADIUS;
        int particleCount = 32;
        for (int i = 0; i < particleCount; i++) {
            double angle = (2 * Math.PI * i) / particleCount;
            double x = center.x + Math.cos(angle) * radius;
            double z = center.z + Math.sin(angle) * radius;
            
            level.sendParticles(
                ParticleTypes.ELECTRIC_SPARK,
                x, center.y + 0.1, z,
                1, 0, 0, 0, 0
            );
            
            // Add some ambient particles
            if (i % 4 == 0) {
                level.sendParticles(
                    ParticleTypes.ENCHANT,
                    x, center.y + 0.1, z,
                    1, 0, 0.1, 0, 0.02
                );
            }
        }
        
        // Affect entities in the trap every 20 ticks (1 second)
        if (remainingTicks % 20 == 0) {
            AABB trapArea = new AABB(center.subtract(radius, 1, radius), center.add(radius, 3, radius));
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, trapArea,
                entity -> entity != caster && entity.isAlive());
            
            for (LivingEntity entity : entities) {
                // Apply slowness
                entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 30, 2, false, false));
                entity.addEffect(new MobEffectInstance(MobEffects.MINING_FATIGUE, 30, 1, false, false));
                
                // Deal damage (scaled by Signs attribute)
                entity.hurt(level.damageSources().magic(), damagePerSecond);
                
                // Visual feedback
                level.sendParticles(
                    ParticleTypes.WITCH,
                    entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ(),
                    5, 0.3, 0.3, 0.3, 0.1
                );
            }
        }
        
        // Continue trap effect
        if (remainingTicks > 1) {
            level.getServer().execute(() -> runTrapEffect(level, caster, center, remainingTicks - 1, damagePerSecond));
        }
    }
}
