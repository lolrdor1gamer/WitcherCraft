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
 * Nekker - small, aggressive pack creature found in forests and caves.
 * Attacks in groups and can overwhelm unprepared players.
 * Weak to necrophage oil.
 */
public class NekkerEntity extends AbstractWitcherMonster {
    
    public NekkerEntity(EntityType<? extends NekkerEntity> type, Level level) {
        super(type, level, MonsterCategory.NECROPHAGE, false);
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LeapAtTargetGoal(this, 0.4F)); // Nekkers leap at targets
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, NekkerEntity.class)); // Help other Nekkers
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 15.0D) // Small but numerous
            .add(Attributes.MOVEMENT_SPEED, 0.35D) // Fast
            .add(Attributes.ATTACK_DAMAGE, 3.0D) // Low damage individually
            .add(Attributes.FOLLOW_RANGE, 25.0D)
            .add(Attributes.ARMOR, 0.0D); // Unarmored
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SPIDER_AMBIENT;
    }
    
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.SPIDER_HURT;
    }
    
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SPIDER_DEATH;
    }
    
    @Override
    public String getMonsterLootTable() {
        return "witchercraft:entities/nekker";
    }
    
    @Override
    public String getBestiaryId() {
        return "nekker";
    }
}
