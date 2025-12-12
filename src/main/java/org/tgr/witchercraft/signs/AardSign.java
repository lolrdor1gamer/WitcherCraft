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
 * Aard - Kinetic blast that knocks enemies back within a cone in front of the player.
 */
public class AardSign extends WitcherSign {

    private static final double RANGE = 6.0D;
    private static final double CONE_ANGLE = 50.0D;
    private static final double KNOCKBACK_STRENGTH = 1.5D;
    private static final double VERTICAL_BOOST = 0.15D;

    public AardSign() {
        super("aard", 80, 15); // 4 second cooldown (80 ticks), modest stamina/mana cost
    }

    @Override
    public boolean cast(Player player, Level level) {
        if (!canCast(player)) {
            return false;
        }

        Vec3 lookVec = player.getLookAngle().normalize();
        Vec3 origin = player.getEyePosition();
        AABB searchBox = new AABB(origin, origin).inflate(RANGE);
        List<Entity> entities = level.getEntities(player, searchBox);
        boolean affected = false;

        for (Entity entity : entities) {
            if (entity instanceof LivingEntity target && target != player) {
                Vec3 toEntity = target.position().subtract(origin);
                double distance = toEntity.length();

                if (distance > RANGE || distance <= 0.0001D) {
                    continue;
                }

                Vec3 direction = toEntity.normalize();
                double dot = lookVec.dot(direction);
                dot = Math.max(-1.0D, Math.min(1.0D, dot));
                double angle = Math.toDegrees(Math.acos(dot));

                if (angle <= CONE_ANGLE / 2.0D) {
                    Vec3 knockback = direction.scale(KNOCKBACK_STRENGTH);
                    target.push(knockback.x, VERTICAL_BOOST, knockback.z);
                    target.hurtMarked = true; // Ensure movement updates on client
                    affected = true;
                }
            }
        }

        if (affected && level instanceof ServerLevel serverLevel) {
            spawnBlastParticles(serverLevel, origin, lookVec);
        }

        return affected;
    }

    private void spawnBlastParticles(ServerLevel level, Vec3 origin, Vec3 lookVec) {
        for (int i = 0; i < 16; i++) {
            double distance = (i / 16.0D) * RANGE;
            Vec3 particlePos = origin.add(lookVec.scale(distance));
            level.sendParticles(
                    ParticleTypes.CLOUD,
                    particlePos.x,
                    particlePos.y - 0.3D,
                    particlePos.z,
                    5,
                    0.2D,
                    0.2D,
                    0.2D,
                    0.01D
            );
        }
    }
}
