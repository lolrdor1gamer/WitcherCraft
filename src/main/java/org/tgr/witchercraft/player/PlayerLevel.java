package org.tgr.witchercraft.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

/**
 * Player level and experience system.
 * Handles XP gain, level progression, and attribute points.
 */
public class PlayerLevel {
    
    private static final int MAX_LEVEL = 70;
    private static final int BASE_XP = 1000;
    private static final float XP_MULTIPLIER = 1.5f;
    
    private int level;
    private int experience;
    private int attributePoints;
    private int skillPoints;
    
    public PlayerLevel() {
        this.level = 1;
        this.experience = 0;
        this.attributePoints = 0;
        this.skillPoints = 0;
    }
    
    /**
     * Calculate XP required for next level
     */
    public int getXpForNextLevel() {
        if (level >= MAX_LEVEL) return Integer.MAX_VALUE;
        return (int) (BASE_XP * Math.pow(XP_MULTIPLIER, level - 1));
    }
    
    /**
     * Add experience and handle level ups
     */
    public boolean addExperience(Player player, int amount) {
        if (level >= MAX_LEVEL) return false;
        
        experience += amount;
        boolean leveled = false;
        
        while (experience >= getXpForNextLevel() && level < MAX_LEVEL) {
            experience -= getXpForNextLevel();
            level++;
            attributePoints += 5; // 5 attribute points per level
            skillPoints += 1; // 1 skill point per level
            leveled = true;
            
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.displayClientMessage(
                    Component.literal("Level Up! You are now level " + level).withColor(0xFFD700),
                    false
                );
                serverPlayer.displayClientMessage(
                    Component.literal("+5 Attribute Points, +1 Skill Point").withColor(0x00FF00),
                    false
                );
            }
        }
        
        return leveled;
    }
    
    /**
     * Get XP from killing a mob based on its difficulty
     */
    public static int getXpForKill(int mobLevel, int playerLevel) {
        int levelDiff = mobLevel - playerLevel;
        int baseXp = mobLevel * 50;
        
        // Reduced XP for lower level mobs, increased for higher
        if (levelDiff < -5) {
            baseXp = baseXp / 4;
        } else if (levelDiff < 0) {
            baseXp = baseXp / 2;
        } else if (levelDiff > 5) {
            baseXp = baseXp * 2;
        }
        
        return Math.max(10, baseXp);
    }
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = Math.max(1, Math.min(MAX_LEVEL, level));
    }
    
    public int getExperience() {
        return experience;
    }
    
    public void setExperience(int experience) {
        this.experience = Math.max(0, experience);
    }
    
    public int getAttributePoints() {
        return attributePoints;
    }
    
    public void setAttributePoints(int points) {
        this.attributePoints = Math.max(0, points);
    }
    
    public int getSkillPoints() {
        return skillPoints;
    }
    
    public void setSkillPoints(int points) {
        this.skillPoints = Math.max(0, points);
    }
    
    public void spendAttributePoint() {
        if (attributePoints > 0) {
            attributePoints--;
        }
    }
    
    public void spendSkillPoint() {
        if (skillPoints > 0) {
            skillPoints--;
        }
    }
    
    public float getXpProgress() {
        if (level >= MAX_LEVEL) return 1.0f;
        return (float) experience / getXpForNextLevel();
    }
    
    public CompoundTag save(CompoundTag tag) {
        tag.putInt("level", level);
        tag.putInt("experience", experience);
        tag.putInt("attributePoints", attributePoints);
        tag.putInt("skillPoints", skillPoints);
        return tag;
    }
    
    public void load(CompoundTag tag) {
        level = tag.getInt("level").orElse(1);
        experience = tag.getInt("experience").orElse(0);
        attributePoints = tag.getInt("attributePoints").orElse(0);
        skillPoints = tag.getInt("skillPoints").orElse(0);
    }
}
