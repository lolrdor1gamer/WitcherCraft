package org.tgr.witchercraft.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.util.RandomSource;

/**
 * Drowner - aquatic necrophage that lurks near water.
 * Weak to necrophage oil, effective against with silver.
 */
public class DrownedCorpseEntity extends AbstractWitcherMonster {
    
    public DrownedCorpseEntity(EntityType<? extends DrownedCorpseEntity> type, Level level) {
        super(type, level, MonsterCategory.NECROPHAGE, false);
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 1.0D, 40));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 25.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.28D)
            .add(Attributes.ATTACK_DAMAGE, 4.0D)
            .add(Attributes.FOLLOW_RANGE, 30.0D)
            .add(Attributes.ARMOR, 1.0D);
    }
    
    /**
     * Drowners spawn near water.
     */
    public static boolean checkDrownedCorpseSpawnRules(EntityType<DrownedCorpseEntity> type, 
            ServerLevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        // Check for water nearby
        for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
                if (level.getBlockState(pos.offset(x, 0, z)).is(Blocks.WATER)) {
                    return Monster.checkMonsterSpawnRules(type, level, spawnReason, pos, random);
                }
            }
        }
        return false;
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.DROWNED_AMBIENT;
    }
    
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.DROWNED_HURT;
    }
    
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.DROWNED_DEATH;
    }
    
    @Override
    public String getMonsterLootTable() {
        return "witchercraft:entities/drowned_corpse";
    }
    
    @Override
    public String getBestiaryId() {
        return "drowned_corpse";
    }
}
