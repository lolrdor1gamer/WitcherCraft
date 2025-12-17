package org.tgr.witchercraft.player;

import java.util.*;

/**
 * Represents a single skill in the skill tree
 */
public class Skill {
    
    private final String id;
    private final String name;
    private final String description;
    private final SkillTree tree;
    private final int maxRank;
    private final List<String> requirements; // IDs of required skills
    private final int tierLevel; // Level requirement
    
    public Skill(String id, String name, String description, SkillTree tree, int maxRank, int tierLevel, String... requirements) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tree = tree;
        this.maxRank = maxRank;
        this.tierLevel = tierLevel;
        this.requirements = Arrays.asList(requirements);
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public SkillTree getTree() { return tree; }
    public int getMaxRank() { return maxRank; }
    public List<String> getRequirements() { return requirements; }
    public int getTierLevel() { return tierLevel; }
    
    public enum SkillTree {
        COMBAT("Combat", 0xFF4444),
        SIGNS("Signs", 0x4444FF),
        ALCHEMY("Alchemy", 0x44FF44);
        
        private final String displayName;
        private final int color;
        
        SkillTree(String displayName, int color) {
            this.displayName = displayName;
            this.color = color;
        }
        
        public String getDisplayName() { return displayName; }
        public int getColor() { return color; }
    }
    
    // Skill registry
    private static final Map<String, Skill> SKILLS = new HashMap<>();
    
    public static void registerSkill(Skill skill) {
        SKILLS.put(skill.getId(), skill);
    }
    
    public static Skill getSkill(String id) {
        return SKILLS.get(id);
    }
    
    public static Collection<Skill> getAllSkills() {
        return SKILLS.values();
    }
    
    public static Collection<Skill> getSkillsForTree(SkillTree tree) {
        return SKILLS.values().stream()
            .filter(s -> s.getTree() == tree)
            .toList();
    }
    
    // Initialize default skills
    static {
        // COMBAT TREE
        registerSkill(new Skill("muscle_memory", "Muscle Memory", "Fast attack damage +5% per rank", SkillTree.COMBAT, 5, 1));
        registerSkill(new Skill("precise_blows", "Precise Blows", "Critical hit chance +2% per rank", SkillTree.COMBAT, 5, 1));
        registerSkill(new Skill("strength_training", "Strength Training", "Strong attack damage +5% per rank", SkillTree.COMBAT, 5, 5, "muscle_memory"));
        registerSkill(new Skill("crushing_blows", "Crushing Blows", "Strong attack critical damage +10% per rank", SkillTree.COMBAT, 5, 10, "strength_training"));
        registerSkill(new Skill("fleet_footed", "Fleet Footed", "Dodge costs 10% less stamina per rank", SkillTree.COMBAT, 3, 5));
        registerSkill(new Skill("razor_focus", "Razor Focus", "Adrenaline points boost damage by 2% per rank", SkillTree.COMBAT, 3, 10, "fleet_footed"));
        registerSkill(new Skill("whirl", "Whirl", "Unlocks Whirl combat ability - sustained spinning attack", SkillTree.COMBAT, 1, 15, "crushing_blows"));
        registerSkill(new Skill("rend", "Rend", "Unlocks Rend combat ability - powerful charged strike", SkillTree.COMBAT, 1, 15, "razor_focus"));
        
        // SIGNS TREE
        registerSkill(new Skill("melt_armor", "Melt Armor", "Igni damage +5% per rank", SkillTree.SIGNS, 5, 1));
        registerSkill(new Skill("sustained_glyphs", "Sustained Glyphs", "Sign duration +5% per rank", SkillTree.SIGNS, 5, 1));
        registerSkill(new Skill("firestream", "Firestream", "Igni becomes flamethrower mode", SkillTree.SIGNS, 1, 10, "melt_armor"));
        registerSkill(new Skill("active_shield", "Active Shield", "Quen absorbs 5% more damage per rank", SkillTree.SIGNS, 5, 5));
        registerSkill(new Skill("exploding_shield", "Exploding Shield", "Quen explodes when destroyed, damaging nearby enemies", SkillTree.SIGNS, 1, 15, "active_shield"));
        registerSkill(new Skill("far_reaching_aard", "Far-Reaching Aard", "Aard range +20% per rank", SkillTree.SIGNS, 3, 5));
        registerSkill(new Skill("aard_sweep", "Aard Sweep", "Aard hits all enemies in front", SkillTree.SIGNS, 1, 10, "far_reaching_aard"));
        registerSkill(new Skill("puppet", "Puppet", "Axii makes enemies fight for you", SkillTree.SIGNS, 1, 15, "sustained_glyphs"));
        registerSkill(new Skill("supercharged_glyphs", "Supercharged Glyphs", "Yrden traps deal 10% more damage per rank", SkillTree.SIGNS, 5, 10));
        
        // ALCHEMY TREE
        registerSkill(new Skill("heightened_tolerance", "Heightened Tolerance", "Toxicity tolerance +5 per rank", SkillTree.ALCHEMY, 5, 1));
        registerSkill(new Skill("acquired_tolerance", "Acquired Tolerance", "Each alchemy formula learned increases toxicity by 1", SkillTree.ALCHEMY, 1, 5, "heightened_tolerance"));
        registerSkill(new Skill("side_effects", "Side Effects", "Potion overdose gives positive effects", SkillTree.ALCHEMY, 1, 10, "acquired_tolerance"));
        registerSkill(new Skill("delayed_recovery", "Delayed Recovery", "Potion duration +10% per rank", SkillTree.ALCHEMY, 5, 1));
        registerSkill(new Skill("tissue_transformation", "Tissue Transformation", "Drinking potions heals 10% vitality per rank", SkillTree.ALCHEMY, 3, 5, "delayed_recovery"));
        registerSkill(new Skill("synergy", "Synergy Bonus", "Increases mutagen bonuses by 10% per rank", SkillTree.ALCHEMY, 5, 5));
        registerSkill(new Skill("protective_coating", "Protective Coating", "Applied oils also grant 5% damage resistance per rank", SkillTree.ALCHEMY, 3, 10, "synergy"));
        registerSkill(new Skill("fixative", "Fixative", "Blade oils never wear off", SkillTree.ALCHEMY, 1, 15, "protective_coating"));
        registerSkill(new Skill("cluster_bombs", "Cluster Bombs", "Bombs have larger explosion radius", SkillTree.ALCHEMY, 1, 10));
    }
}
