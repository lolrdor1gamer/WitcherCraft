package org.tgr.witchercraft.signs;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.tgr.witchercraft.player.PlayerAttributes;

import java.util.List;

/**
 * Igni - Fire Sign
 * Creates a cone of fire in front of the player
 */
public class IgniSign extends WitcherSign {
    
    private static final double RANGE = 5.0;
    private static final double CONE_ANGLE = 45.0;
    
    public IgniSign() {
        super("igni", 100, 20); // 5 second cooldown (100 ticks), 20 mana cost
    }
    
    @Override
    public boolean cast(Player player, Level level) {
        if (!canCast(player)) {
            return false;
        }
        
        // Calculate sign intensity based on Signs attribute (server-side only)
        float damageMultiplier = 1.0f;
        int burnTicks = 100; // Base 5 seconds
        
        if (player instanceof ServerPlayer && level instanceof ServerLevel) {
            // Get player Signs attribute (temporary until SavedData is fixed)
            PlayerAttributes tempAttributes = new PlayerAttributes();
            int signsAttribute = tempAttributes.getSigns();
            
            // Each Signs point adds 5% intensity
            damageMultiplier = 1.0f + (signsAttribute * 0.05f);
            // Each Signs point adds 0.5 seconds (10 ticks) to burn duration
            burnTicks = 100 + (signsAttribute * 10);
        }
        
        final float finalDamage = 4.0f * damageMultiplier;
        final int finalBurnTicks = burnTicks;
        
        Vec3 lookVec = player.getLookAngle();
        Vec3 playerPos = player.getEyePosition();
        
        // Create a cone area in front of the player
        AABB searchBox = new AABB(playerPos, playerPos).inflate(RANGE);
        List<Entity> entities = level.getEntities(player, searchBox);
        
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity target && target != player) {
                Vec3 toEntity = entity.position().subtract(playerPos).normalize();
                double angle = Math.acos(Math.min(1.0, Math.max(-1.0, lookVec.dot(toEntity)))) * (180.0 / Math.PI);
                
                // Check if entity is within cone
                if (angle <= CONE_ANGLE / 2.0) {
                    target.setRemainingFireTicks(finalBurnTicks);
                    target.hurt(target.damageSources().inFire(), finalDamage);
                }
            }
        }
        
        // Spawn fire particles
        if (level instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 20; i++) {
                double distance = RANGE * (i / 20.0);
                Vec3 particlePos = playerPos.add(lookVec.scale(distance));
                serverLevel.sendParticles(ParticleTypes.FLAME, 
                    particlePos.x, particlePos.y, particlePos.z, 
                    3, 0.3, 0.3, 0.3, 0.02);
            }
        }
        
        return true;
    }
}
