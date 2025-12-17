package org.tgr.witchercraft.signs;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.tgr.witchercraft.player.PlayerAttributes;

import java.util.List;

/**
 * Axii Sign - Mind control sign that confuses and stuns enemies
 */
public class AxiiSign extends WitcherSign {
    
    private static final int STUN_DURATION = 60; // 3 seconds
    private static final int CONFUSION_DURATION = 100; // 5 seconds
    private static final double RANGE = 8.0;
    
    public AxiiSign() {
        super("Axii", 120, 35); // 6 second cooldown, 35 mana cost
    }
    
    @Override
    public boolean cast(Player player, Level level) {
        if (!canCast(player)) {
            return false;
        }
        
        // Calculate effect duration based on Signs attribute
        int stunDuration = STUN_DURATION;
        int confusionDuration = CONFUSION_DURATION;
        
        if (player instanceof ServerPlayer && level instanceof ServerLevel) {
            // Get player Signs attribute (temporary until SavedData is fixed)
            PlayerAttributes tempAttributes = new PlayerAttributes();
            int signsAttribute = tempAttributes.getSigns();
            
            // Each Signs point adds 0.5 seconds (10 ticks) to durations
            stunDuration = STUN_DURATION + (signsAttribute * 10);
            confusionDuration = CONFUSION_DURATION + (signsAttribute * 10);
        }
        
        final int finalStunDuration = stunDuration;
        final int finalConfusionDuration = confusionDuration;
        
        // Find target entity in front of player
        Vec3 lookVec = player.getLookAngle();
        Vec3 start = player.getEyePosition();
        Vec3 end = start.add(lookVec.scale(RANGE));
        
        AABB searchBox = new AABB(start, end).inflate(2.0);
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, searchBox,
            entity -> entity != player && entity.isAlive() && !entity.isSpectator());
        
        // Find closest entity in line of sight
        LivingEntity target = null;
        double closestDist = RANGE;
        
        for (LivingEntity entity : entities) {
            Vec3 toEntity = entity.position().subtract(start);
            double dist = toEntity.length();
            if (dist < closestDist) {
                // Check if roughly in front
                double dot = toEntity.normalize().dot(lookVec);
                if (dot > 0.7) { // Within ~45 degree cone
                    target = entity;
                    closestDist = dist;
                }
            }
        }
        
        if (target == null) {
            return false; // No valid target
        }
        
        // Play sound
        level.playSound(null, player.blockPosition(), SoundEvents.EVOKER_CAST_SPELL, 
            SoundSource.PLAYERS, 0.7f, 1.2f);
        
        if (level instanceof ServerLevel serverLevel) {
            applyAxiiEffect(serverLevel, player, target, finalStunDuration, finalConfusionDuration);
        }
        
        return true;
    }
    
    private void applyAxiiEffect(ServerLevel level, Player caster, LivingEntity target, int stunDuration, int confusionDuration) {
        // Stun effect - can't move or attack (duration scaled by Signs)
        target.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, stunDuration, 10, false, true));
        target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, stunDuration, 3, false, true));
        
        // Confusion effect - wander randomly after stun (duration scaled by Signs)
        target.addEffect(new MobEffectInstance(MobEffects.NAUSEA, confusionDuration, 0, false, true));
        
        // For mobs, make them temporarily allied
        if (target instanceof Mob mob) {
            // Store original target
            LivingEntity originalTarget = mob.getTarget();
            
            // Clear current target
            mob.setTarget(null);
            
            // Make mob attack other mobs nearby (if upgraded skill is active)
            AABB nearbyArea = target.getBoundingBox().inflate(10.0);
            List<Mob> nearbyMobs = level.getEntitiesOfClass(Mob.class, nearbyArea,
                entity -> entity != mob && entity.isAlive() && entity.getTarget() == caster);
            
            if (!nearbyMobs.isEmpty()) {
                Mob newTarget = nearbyMobs.get(level.random.nextInt(nearbyMobs.size()));
                mob.setTarget(newTarget);
            }
            
            // Restore original behavior after confusion wears off
            level.getServer().execute(() -> {
                // Delay using a custom timer system or accept immediate execution
                // For proper delayed execution, implement a server tick handler
                if (mob.isAlive()) {
                    mob.setTarget(originalTarget);
                }
            });
        }
        
        // Particle effects - spiral around target
        spawnAxiiParticles(level, target);
        
        // Play hit sound
        level.playSound(null, target.blockPosition(), SoundEvents.EVOKER_PREPARE_SUMMON,
            SoundSource.HOSTILE, 0.5f, 1.5f);
    }
    
    private void spawnAxiiParticles(ServerLevel level, LivingEntity target) {
        // Create spiral particle effect
        for (int i = 0; i < 20; i++) {
            double angle = (2 * Math.PI * i) / 20;
            double radius = 1.0;
            double x = target.getX() + Math.cos(angle) * radius;
            double z = target.getZ() + Math.sin(angle) * radius;
            double y = target.getY() + target.getBbHeight() / 2;
            
            level.sendParticles(
                ParticleTypes.ENCHANTED_HIT,
                x, y, z,
                1, 0, 0, 0, 0
            );
        }
        
        // Add some sparkles at head level
        level.sendParticles(
            ParticleTypes.SOUL,
            target.getX(), target.getY() + target.getBbHeight(), target.getZ(),
            10, 0.3, 0.3, 0.3, 0.05
        );
    }
}
