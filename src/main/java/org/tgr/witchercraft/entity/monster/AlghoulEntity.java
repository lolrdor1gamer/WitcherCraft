package org.tgr.witchercraft.entity.monster;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Alghoul - stronger variant of the Ghoul, pack leader.
 * Has spines that extend when enraged, dealing more damage.
 * Weak to necrophage oil.
 */
public class AlghoulEntity extends AbstractWitcherMonster {
    
    private boolean enraged = false;
    
    public AlghoulEntity(EntityType<? extends AlghoulEntity> type, Level level) {
        super(type, level, MonsterCategory.NECROPHAGE, false);
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, GhoulEntity.class, AlghoulEntity.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 50.0D) // Tougher than regular ghouls
            .add(Attributes.MOVEMENT_SPEED, 0.34D)
            .add(Attributes.ATTACK_DAMAGE, 7.0D)
            .add(Attributes.FOLLOW_RANGE, 40.0D)
            .add(Attributes.ARMOR, 4.0D);
    }
    
    @Override
    public void tick() {
        super.tick();
        
        // Become enraged when damaged below 50% health
        if (!enraged && this.getHealth() < this.getMaxHealth() * 0.5f) {
            enraged = true;
            // Increase damage when enraged
            var attackDamage = this.getAttribute(Attributes.ATTACK_DAMAGE);
            var movementSpeed = this.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attackDamage != null) attackDamage.setBaseValue(10.0D);
            if (movementSpeed != null) movementSpeed.setBaseValue(0.4D);
        }
    }

    public boolean isEnraged() {
        return enraged;
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
        return "witchercraft:entities/alghoul";
    }
    
    @Override
    public String getBestiaryId() {
        return "alghoul";
    }
}
