package org.tgr.witchercraft.player;

import net.minecraft.nbt.CompoundTag;
import java.util.*;

/**
 * Manages player's learned skills and their ranks
 */
public class PlayerSkills {
    
    // Map of skill ID -> current rank
    private final Map<String, Integer> learnedSkills = new HashMap<>();
    
    // Active skill slots (up to 12 skills can be active at once)
    private final String[] activeSkills = new String[12];
    
    public PlayerSkills() {
    }
    
    /**
     * Check if player can learn/upgrade a skill
     */
    public boolean canLearnSkill(String skillId, int playerLevel) {
        Skill skill = Skill.getSkill(skillId);
        if (skill == null) return false;
        
        // Check level requirement
        if (playerLevel < skill.getTierLevel()) return false;
        
        // Check if already at max rank
        int currentRank = learnedSkills.getOrDefault(skillId, 0);
        if (currentRank >= skill.getMaxRank()) return false;
        
        // Check requirements (must have at least 1 rank in required skills)
        for (String reqId : skill.getRequirements()) {
            if (!learnedSkills.containsKey(reqId) || learnedSkills.get(reqId) < 1) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Learn or upgrade a skill
     */
    public boolean learnSkill(String skillId) {
        Skill skill = Skill.getSkill(skillId);
        if (skill == null) return false;
        
        int currentRank = learnedSkills.getOrDefault(skillId, 0);
        if (currentRank >= skill.getMaxRank()) return false;
        
        learnedSkills.put(skillId, currentRank + 1);
        return true;
    }
    
    /**
     * Get current rank of a skill
     */
    public int getSkillRank(String skillId) {
        return learnedSkills.getOrDefault(skillId, 0);
    }
    
    /**
     * Check if skill is learned (at least rank 1)
     */
    public boolean hasSkill(String skillId) {
        return learnedSkills.containsKey(skillId) && learnedSkills.get(skillId) > 0;
    }
    
    /**
     * Get all learned skills
     */
    public Map<String, Integer> getLearnedSkills() {
        return new HashMap<>(learnedSkills);
    }
    
    /**
     * Get all skills (alias for network packet)
     */
    public Map<String, Integer> getAllSkills() {
        return getLearnedSkills();
    }
    
    /**
     * Set skill rank (for client sync)
     */
    public void setSkillRank(String skillId, int rank) {
        if (rank > 0) {
            learnedSkills.put(skillId, rank);
        } else {
            learnedSkills.remove(skillId);
        }
    }
    
    /**
     * Activate a skill in a slot
     */
    public void activateSkill(int slot, String skillId) {
        if (slot >= 0 && slot < activeSkills.length) {
            if (skillId == null || hasSkill(skillId)) {
                activeSkills[slot] = skillId;
            }
        }
    }
    
    /**
     * Get active skill in slot
     */
    public String getActiveSkill(int slot) {
        if (slot >= 0 && slot < activeSkills.length) {
            return activeSkills[slot];
        }
        return null;
    }
    
    /**
     * Check if skill is currently active
     */
    public boolean isSkillActive(String skillId) {
        for (String active : activeSkills) {
            if (skillId.equals(active)) return true;
        }
        return false;
    }
    
    /**
     * Get count of active skills for a tree
     */
    public int getActiveSkillsInTree(Skill.SkillTree tree) {
        int count = 0;
        for (String skillId : activeSkills) {
            if (skillId != null) {
                Skill skill = Skill.getSkill(skillId);
                if (skill != null && skill.getTree() == tree) {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     * Calculate bonus from active skills
     */
    public float getActiveSkillBonus(String skillId) {
        if (!isSkillActive(skillId)) return 0.0f;
        int rank = getSkillRank(skillId);
        return rank > 0 ? rank : 0.0f;
    }
    
    public CompoundTag save(CompoundTag tag) {
        // Save learned skills
        CompoundTag skillsTag = new CompoundTag();
        for (Map.Entry<String, Integer> entry : learnedSkills.entrySet()) {
            skillsTag.putInt(entry.getKey(), entry.getValue());
        }
        tag.put("learnedSkills", skillsTag);
        
        // Save active skills
        CompoundTag activeTag = new CompoundTag();
        for (int i = 0; i < activeSkills.length; i++) {
            if (activeSkills[i] != null) {
                activeTag.putString("slot_" + i, activeSkills[i]);
            }
        }
        tag.put("activeSkills", activeTag);
        
        return tag;
    }
    
    public void load(CompoundTag tag) {
        // Load learned skills
        learnedSkills.clear();
        CompoundTag skillsTag = tag.getCompound("learnedSkills").orElse(new CompoundTag());
        for (String key : skillsTag.keySet()) {
            learnedSkills.put(key, skillsTag.getInt(key).orElse(1));
        }
        
        // Load active skills
        Arrays.fill(activeSkills, null);
        CompoundTag activeTag = tag.getCompound("activeSkills").orElse(new CompoundTag());
        for (int i = 0; i < activeSkills.length; i++) {
            String key = "slot_" + i;
            if (activeTag.contains(key)) {
                activeSkills[i] = activeTag.getString(key).orElse(null);
            }
        }
    }
}
