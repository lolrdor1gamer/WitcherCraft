package org.tgr.witchercraft.bestiary;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import org.tgr.witchercraft.entity.monster.AbstractWitcherMonster;

import java.util.*;

/**
 * Player's bestiary data - tracks discovered monsters and knowledge levels.
 */
public class BestiaryData {
    
    public enum KnowledgeLevel {
        UNKNOWN(0, "Unknown"),
        DISCOVERED(1, "Discovered"),      // Seen once
        FAMILIAR(2, "Familiar"),          // Killed 3+
        STUDIED(3, "Studied"),            // Killed 10+
        EXPERT(4, "Expert"),              // Killed 25+
        MASTER(5, "Master");              // Killed 50+
        
        private final int level;
        private final String displayName;
        
        KnowledgeLevel(int level, String displayName) {
            this.level = level;
            this.displayName = displayName;
        }
        
        public int getLevel() { return level; }
        public String getDisplayName() { return displayName; }
        
        public static KnowledgeLevel fromKillCount(int kills) {
            if (kills >= 50) return MASTER;
            if (kills >= 25) return EXPERT;
            if (kills >= 10) return STUDIED;
            if (kills >= 3) return FAMILIAR;
            if (kills >= 1) return DISCOVERED;
            return UNKNOWN;
        }
        
        /**
         * Bonus damage multiplier based on knowledge level.
         */
        public float getDamageBonus() {
            return switch (this) {
                case UNKNOWN -> 1.0f;
                case DISCOVERED -> 1.0f;
                case FAMILIAR -> 1.05f;
                case STUDIED -> 1.10f;
                case EXPERT -> 1.15f;
                case MASTER -> 1.25f;
            };
        }
    }
    
    private final Map<ResourceLocation, Integer> killCounts = new HashMap<>();
    private final Set<ResourceLocation> discoveredMonsters = new HashSet<>();
    private final Map<ResourceLocation, Long> firstEncounterTime = new HashMap<>();
    
    public BestiaryData() {}
    
    /**
     * Record seeing a monster for the first time.
     */
    public void discoverMonster(EntityType<?> entityType) {
        ResourceLocation id = EntityType.getKey(entityType);
        if (!discoveredMonsters.contains(id)) {
            discoveredMonsters.add(id);
            firstEncounterTime.put(id, System.currentTimeMillis());
        }
    }
    
    /**
     * Record a monster kill.
     */
    public void recordKill(EntityType<?> entityType) {
        ResourceLocation id = EntityType.getKey(entityType);
        discoverMonster(entityType);
        killCounts.merge(id, 1, Integer::sum);
    }
    
    /**
     * Get the knowledge level for a monster type.
     */
    public KnowledgeLevel getKnowledgeLevel(EntityType<?> entityType) {
        ResourceLocation id = EntityType.getKey(entityType);
        if (!discoveredMonsters.contains(id)) {
            return KnowledgeLevel.UNKNOWN;
        }
        int kills = killCounts.getOrDefault(id, 0);
        return KnowledgeLevel.fromKillCount(kills);
    }
    
    /**
     * Get kill count for a monster type.
     */
    public int getKillCount(EntityType<?> entityType) {
        return killCounts.getOrDefault(EntityType.getKey(entityType), 0);
    }
    
    /**
     * Check if a monster has been discovered.
     */
    public boolean isDiscovered(EntityType<?> entityType) {
        return discoveredMonsters.contains(EntityType.getKey(entityType));
    }
    
    /**
     * Get all discovered monster types.
     */
    public Set<ResourceLocation> getDiscoveredMonsters() {
        return Collections.unmodifiableSet(discoveredMonsters);
    }
    
    /**
     * Get damage bonus for a monster based on knowledge.
     */
    public float getDamageBonus(EntityType<?> entityType) {
        return getKnowledgeLevel(entityType).getDamageBonus();
    }
    
    // === Serialization ===
    
    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        
        // Save discovered monsters
        ListTag discoveredList = new ListTag();
        for (ResourceLocation id : discoveredMonsters) {
            discoveredList.add(StringTag.valueOf(id.toString()));
        }
        tag.put("Discovered", discoveredList);
        
        // Save kill counts
        CompoundTag killsTag = new CompoundTag();
        for (Map.Entry<ResourceLocation, Integer> entry : killCounts.entrySet()) {
            killsTag.putInt(entry.getKey().toString(), entry.getValue());
        }
        tag.put("Kills", killsTag);
        
        // Save first encounter times
        CompoundTag timesTag = new CompoundTag();
        for (Map.Entry<ResourceLocation, Long> entry : firstEncounterTime.entrySet()) {
            timesTag.putLong(entry.getKey().toString(), entry.getValue());
        }
        tag.put("FirstEncounter", timesTag);
        
        return tag;
    }
    
    public static BestiaryData load(CompoundTag tag) {
        BestiaryData data = new BestiaryData();
        
        // Load discovered monsters
        if (tag.contains("Discovered")) {
            tag.getList("Discovered").ifPresent(discoveredList -> {
                for (int i = 0; i < discoveredList.size(); i++) {
                    if (discoveredList.get(i) instanceof StringTag stringTag) {
                        ResourceLocation id = ResourceLocation.tryParse(stringTag.value());
                        if (id != null) {
                            data.discoveredMonsters.add(id);
                        }
                    }
                }
            });
        }
        
        // Load kill counts
        if (tag.contains("Kills")) {
            tag.getCompound("Kills").ifPresent(killsTag -> {
                for (String key : killsTag.keySet()) {
                    ResourceLocation id = ResourceLocation.tryParse(key);
                    if (id != null) {
                        killsTag.getInt(key).ifPresent(value -> data.killCounts.put(id, value));
                    }
                }
            });
        }
        
        // Load first encounter times
        if (tag.contains("FirstEncounter")) {
            tag.getCompound("FirstEncounter").ifPresent(timesTag -> {
                for (String key : timesTag.keySet()) {
                    ResourceLocation id = ResourceLocation.tryParse(key);
                    if (id != null) {
                        timesTag.getLong(key).ifPresent(value -> data.firstEncounterTime.put(id, value));
                    }
                }
            });
        }
        
        return data;
    }
}
