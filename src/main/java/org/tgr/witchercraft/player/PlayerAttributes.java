package org.tgr.witchercraft.player;

import net.minecraft.nbt.CompoundTag;

/**
 * Player attributes that affect combat, signs, and survival.
 * Witcher-style character development.
 */
public class PlayerAttributes {
    
    // Core attributes
    private int strength;      // Increases melee damage, carry weight
    private int vitality;      // Increases health, toxicity tolerance
    private int combat;        // Increases critical chance, attack speed
    private int signs;         // Increases sign intensity, reduces stamina cost
    private int alchemy;       // Increases potion duration, reduces toxicity
    
    public PlayerAttributes() {
        // Start with base values
        this.strength = 1;
        this.vitality = 1;
        this.combat = 1;
        this.signs = 1;
        this.alchemy = 1;
    }
    
    // Getters
    public int getStrength() { return strength; }
    public int getVitality() { return vitality; }
    public int getCombat() { return combat; }
    public int getSigns() { return signs; }
    public int getAlchemy() { return alchemy; }
    
    // Setters
    public void setStrength(int value) { this.strength = Math.max(1, value); }
    public void setVitality(int value) { this.vitality = Math.max(1, value); }
    public void setCombat(int value) { this.combat = Math.max(1, value); }
    public void setSigns(int value) { this.signs = Math.max(1, value); }
    public void setAlchemy(int value) { this.alchemy = Math.max(1, value); }
    
    // Attribute upgrades (cost 1 attribute point each)
    public void increaseStrength() { strength++; }
    public void increaseVitality() { vitality++; }
    public void increaseCombat() { combat++; }
    public void increaseSigns() { signs++; }
    public void increaseAlchemy() { alchemy++; }
    
    /**
     * Calculate bonus health from vitality
     */
    public float getHealthBonus() {
        return vitality * 2.0f; // +2 HP per vitality point
    }
    
    /**
     * Calculate melee damage bonus from strength
     */
    public float getDamageBonus() {
        return 1.0f + (strength * 0.05f); // +5% damage per strength point
    }
    
    /**
     * Calculate critical chance from combat
     */
    public float getCriticalChance() {
        return combat * 0.02f; // +2% crit per combat point
    }
    
    /**
     * Calculate attack speed bonus from combat
     */
    public float getAttackSpeed() {
        return 1.0f + (combat * 0.03f); // +3% attack speed per combat point
    }
    
    /**
     * Calculate sign intensity from signs attribute
     */
    public float getSignIntensity() {
        return 1.0f + (signs * 0.1f); // +10% sign power per signs point
    }
    
    /**
     * Calculate stamina cost reduction for signs
     */
    public float getSignStaminaReduction() {
        return signs * 0.02f; // -2% stamina cost per signs point
    }
    
    /**
     * Calculate maximum toxicity from vitality and alchemy
     */
    public float getMaxToxicity() {
        return 100.0f + (vitality * 5.0f) + (alchemy * 10.0f);
    }
    
    /**
     * Calculate potion duration bonus from alchemy
     */
    public float getPotionDurationBonus() {
        return 1.0f + (alchemy * 0.1f); // +10% duration per alchemy point
    }
    
    /**
     * Calculate toxicity reduction from alchemy
     */
    public float getToxicityReduction() {
        return alchemy * 0.05f; // -5% toxicity per alchemy point
    }
    
    /**
     * Get total attribute points spent
     */
    public int getTotalPoints() {
        return strength + vitality + combat + signs + alchemy - 5; // -5 for base values
    }
    
    public CompoundTag save(CompoundTag tag) {
        tag.putInt("strength", strength);
        tag.putInt("vitality", vitality);
        tag.putInt("combat", combat);
        tag.putInt("signs", signs);
        tag.putInt("alchemy", alchemy);
        return tag;
    }
    
    public void load(CompoundTag tag) {
        strength = tag.getInt("strength").orElse(0);
        vitality = tag.getInt("vitality").orElse(0);
        combat = tag.getInt("combat").orElse(0);
        signs = tag.getInt("signs").orElse(0);
        alchemy = tag.getInt("alchemy").orElse(0);
    }
}
