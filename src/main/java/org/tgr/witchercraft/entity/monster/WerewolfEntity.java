package org.tgr.witchercraft.entity.monster;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Werewolf - cursed creature that transforms during combat.
 * Extremely dangerous, regenerates health, and can inflict bleeding.
 * Weak to cursed oil and Devil's Puffball bombs.
 */
public class WerewolfEntity extends AbstractWitcherMonster {
    
    private boolean transformed = false;
    private int healCooldown = 0;
    
    public WerewolfEntity(EntityType<? extends WerewolfEntity> type, Level level) {
        super(type, level, MonsterCategory.CURSED, true);
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LeapAtTargetGoal(this, 0.5F));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.3D, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 80.0D) // Very tough
            .add(Attributes.MOVEMENT_SPEED, 0.38D) // Fast
            .add(Attributes.ATTACK_DAMAGE, 10.0D) // High damage
            .add(Attributes.FOLLOW_RANGE, 50.0D)
            .add(Attributes.ARMOR, 5.0D)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.6D);
    }
    
    @Override
    public void tick() {
        super.tick();
        
        // Health regeneration
        if (!this.level().isClientSide()) {
            if (healCooldown > 0) {
                healCooldown--;
            } else {
                // Regenerate 1 HP every 2 seconds
                if (this.getHealth() < this.getMaxHealth()) {
                    this.heal(1.0f);
                }
                healCooldown = 40;
            }
        }
        
        // Transform when targeting a player
        if (this.getTarget() != null && !transformed) {
            transform();
        }
    }
    
    private void transform() {
        transformed = true;
        // Boost stats when transformed
        var attackDamage = this.getAttribute(Attributes.ATTACK_DAMAGE);
        var movementSpeed = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attackDamage != null) attackDamage.setBaseValue(14.0D);
        if (movementSpeed != null) movementSpeed.setBaseValue(0.45D);
    }
    
    public boolean isTransformed() {
        return transformed;
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.RAVAGER_AMBIENT;
    }
    
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.RAVAGER_HURT;
    }
    
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.RAVAGER_DEATH;
    }
    
    @Override
    public String getMonsterLootTable() {
        return "witchercraft:entities/werewolf";
    }
    
    @Override
    public String getBestiaryId() {
        return "werewolf";
    }
}
