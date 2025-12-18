package org.tgr.witchercraft.entity.monster;

import net.minecraft.core.particles.ParticleTypes;
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
 * Wraith - spectral being that requires silver to damage effectively.
 * Can phase through attacks and become ethereal.
 * Weak to specter oil, requires silver.
 */
public class WraithEntity extends AbstractWitcherMonster {
    
    private int etherealCooldown = 0;
    private boolean ethereal = false;
    
    public WraithEntity(EntityType<? extends WraithEntity> type, Level level) {
        super(type, level, MonsterCategory.SPECTER, true); // Requires silver!
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.7D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 40.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.28D)
            .add(Attributes.ATTACK_DAMAGE, 6.0D)
            .add(Attributes.FOLLOW_RANGE, 40.0D)
            .add(Attributes.ARMOR, 0.0D) // No physical armor, but resistant to non-silver
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }
    
    @Override
    public void tick() {
        super.tick();
        
        // Spawn ghostly particles
        if (this.level().isClientSide()) {
            this.level().addParticle(ParticleTypes.SOUL,
                this.getRandomX(0.5), this.getRandomY() - 0.25, this.getRandomZ(0.5),
                0, 0.05, 0);
        }
        
        // Handle ethereal state
        if (etherealCooldown > 0) {
            etherealCooldown--;
        }
        
        // Chance to become ethereal when health is low
        if (!ethereal && etherealCooldown <= 0 && this.getHealth() < this.getMaxHealth() * 0.5f && this.random.nextFloat() < 0.02f) {
            becomeEthereal();
        }
    }
    
    private void becomeEthereal() {
        ethereal = true;
        etherealCooldown = 100; // 5 seconds cooldown after becoming solid
    }
    
    public boolean isEthereal() {
        return ethereal;
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.VEX_AMBIENT;
    }
    
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.VEX_HURT;
    }
    
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.VEX_DEATH;
    }
    
    @Override
    public String getMonsterLootTable() {
        return "witchercraft:entities/wraith";
    }
    
    @Override
    public String getBestiaryId() {
        return "wraith";
    }
}
