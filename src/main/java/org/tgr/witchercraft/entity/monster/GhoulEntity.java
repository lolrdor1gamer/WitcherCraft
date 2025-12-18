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
 * Ghoul - necrophage that feeds on corpses and lurks in cemeteries.
 * More dangerous than Drowners, often found in packs.
 * Weak to necrophage oil.
 */
public class GhoulEntity extends AbstractWitcherMonster {
    
    public GhoulEntity(EntityType<? extends GhoulEntity> type, Level level) {
        super(type, level, MonsterCategory.NECROPHAGE, false);
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.1D, false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, GhoulEntity.class, AlghoulEntity.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 35.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.32D)
            .add(Attributes.ATTACK_DAMAGE, 5.0D)
            .add(Attributes.FOLLOW_RANGE, 35.0D)
            .add(Attributes.ARMOR, 2.0D);
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.HUSK_AMBIENT;
    }
    
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.HUSK_HURT;
    }
    
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.HUSK_DEATH;
    }
    
    @Override
    public String getMonsterLootTable() {
        return "witchercraft:entities/ghoul";
    }
    
    @Override
    public String getBestiaryId() {
        return "ghoul";
    }
}
