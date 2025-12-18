package org.tgr.witchercraft.entity.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Base class for all Witcher monsters.
 * Provides common behavior and attributes for necrophages, specters, etc.
 */
public abstract class AbstractWitcherMonster extends Monster {
    
    // Monster category for silver/steel effectiveness
    public enum MonsterCategory {
        NECROPHAGE,     // Drowner, Ghoul, etc. - weak to necrophage oil
        SPECTER,        // Wraith, Noonwraith - weak to specter oil, needs silver
        BEAST,          // Wolves, Bears - weak to beast oil
        CURSED,         // Werewolf, Striga - weak to cursed oil
        INSECTOID,      // Endrega - weak to insectoid oil
        HYBRID,         // Griffin, Siren - weak to hybrid oil
        ELEMENTA,       // Golems, Elementals - weak to elementa oil
        RELICT,         // Leshen, Fiend - weak to relict oil
        VAMPIRE,        // Katakan, Ekimmara - weak to vampire oil
        DRACONID,       // Wyvern, Forktail - weak to draconid oil
        OGROID          // Troll, Cyclops - weak to ogroid oil
    }
    
    protected final MonsterCategory category;
    protected final boolean requiresSilver; // True if silver is required for full damage
    
    protected AbstractWitcherMonster(EntityType<? extends Monster> type, Level level, 
                                      MonsterCategory category, boolean requiresSilver) {
        super(type, level);
        this.category = category;
        this.requiresSilver = requiresSilver;
    }
    
    public MonsterCategory getMonsterCategory() {
        return category;
    }
    
    public boolean requiresSilverWeapon() {
        return requiresSilver;
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
    
    public static AttributeSupplier.Builder createMonsterAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 30.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.3D)
            .add(Attributes.ATTACK_DAMAGE, 5.0D)
            .add(Attributes.FOLLOW_RANGE, 35.0D)
            .add(Attributes.ARMOR, 2.0D);
    }
    
    /**
     * Get the loot table for this monster.
     * Override in subclasses for specific drops.
     */
    public abstract String getMonsterLootTable();
    
    /**
     * Get the bestiary entry ID for this monster.
     */
    public abstract String getBestiaryId();
}
