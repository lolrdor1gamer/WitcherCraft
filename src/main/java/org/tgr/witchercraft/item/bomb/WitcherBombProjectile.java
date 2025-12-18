package org.tgr.witchercraft.item.bomb;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import org.tgr.witchercraft.registry.ModItems;

import java.util.List;

public class WitcherBombProjectile extends ThrowableItemProjectile {
    private BombType bombType = BombType.SAMUM;

    public WitcherBombProjectile(EntityType<? extends WitcherBombProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public WitcherBombProjectile(EntityType<? extends WitcherBombProjectile> entityType, LivingEntity shooter, Level level, BombType type) {
        super(entityType, level);
        this.setOwner(shooter);
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        this.bombType = type;
    }

    public void setBombType(BombType type) {
        this.bombType = type;
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.SAMUM_BOMB;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        Level level = level();
        if (!level.isClientSide()) {
            explode();
            discard();
        }
    }

    private void explode() {
        Level level = level();
        double radius = 4.0;
        AABB area = new AABB(getX() - radius, getY() - radius, getZ() - radius,
                             getX() + radius, getY() + radius, getZ() + radius);
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area);

        for (LivingEntity entity : entities) {
            if (entity == getOwner()) continue;

            float damage = bombType.getDamage();
            if (damage > 0) {
                entity.hurt(level.damageSources().explosion(null, getOwner()), damage);
            }

            MobEffect effect = bombType.getEffect();
            if (effect != null) {
                Holder<MobEffect> holder = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect);
                entity.addEffect(new MobEffectInstance(holder, bombType.getEffectDuration(), 0));
            }

            if (bombType.causesBlinds()) {
                entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0));
            }

            if (bombType == BombType.NORTHERN_WIND) {
                entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, bombType.getEffectDuration(), 4));
            }
        }

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.EXPLOSION, getX(), getY(), getZ(), 10, 1.0, 1.0, 1.0, 0.1);
        }
        level.playSound(null, getX(), getY(), getZ(), SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 1.0f, 1.0f);
    }
}