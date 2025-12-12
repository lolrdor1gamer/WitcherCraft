package org.tgr.witchercraft.signs;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

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
                    target.setRemainingFireTicks(100); // 5 seconds = 100 ticks
                    target.hurt(target.damageSources().inFire(), 4.0f);
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
