package org.tgr.witchercraft.player;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Persistent storage for extended player data (level, attributes, skills).
 * Complements the existing WitcherPlayerData which handles mana/stamina/toxicity.
 */
public class WitcherPlayerProgress extends SavedData {
    
    private static final String DATA_NAME = "witchercraft_player_progress";
    
    private final Map<UUID, PlayerLevel> levels = new HashMap<>();
    private final Map<UUID, PlayerAttributes> attributes = new HashMap<>();
    private final Map<UUID, PlayerSkills> skills = new HashMap<>();
    
    public WitcherPlayerProgress() {
    }
    
    public static WitcherPlayerProgress get(ServerLevel level) {
        // TODO: Fix SavedData API for MC 1.21.1 - API completely changed
        // For now, return a new instance each time (data won't persist)
        return new WitcherPlayerProgress();
        
        /*
        // Original code - needs API update
        return level.getDataStorage().computeIfAbsent(
            SAVED_DATA_TYPE,
            DATA_NAME
        );
        */
    }
    
    public static WitcherPlayerProgress load(CompoundTag tag, HolderLookup.Provider provider) {
        WitcherPlayerProgress data = new WitcherPlayerProgress();
        
        // Load levels
        CompoundTag levelsTag = tag.getCompound("Levels").orElse(new CompoundTag());
        for (String playerUuidStr : levelsTag.keySet()) {
            UUID playerUuid = UUID.fromString(playerUuidStr);
            PlayerLevel level = new PlayerLevel();
            level.load(levelsTag.getCompound(playerUuidStr).orElse(new CompoundTag()));
            data.levels.put(playerUuid, level);
        }
        
        // Load attributes
        CompoundTag attributesTag = tag.getCompound("Attributes").orElse(new CompoundTag());
        for (String playerUuidStr : attributesTag.keySet()) {
            UUID playerUuid = UUID.fromString(playerUuidStr);
            PlayerAttributes attrs = new PlayerAttributes();
            attrs.load(attributesTag.getCompound(playerUuidStr).orElse(new CompoundTag()));
            data.attributes.put(playerUuid, attrs);
        }
        
        // Load skills
        CompoundTag skillsTag = tag.getCompound("Skills").orElse(new CompoundTag());
        for (String playerUuidStr : skillsTag.keySet()) {
            UUID playerUuid = UUID.fromString(playerUuidStr);
            PlayerSkills playerSkills = new PlayerSkills();
            playerSkills.load(skillsTag.getCompound(playerUuidStr).orElse(new CompoundTag()));
            data.skills.put(playerUuid, playerSkills);
        }
        
        return data;
    }
    
    public CompoundTag save(CompoundTag tag) {
        // Save levels
        CompoundTag levelsTag = new CompoundTag();
        for (Map.Entry<UUID, PlayerLevel> entry : levels.entrySet()) {
            CompoundTag levelTag = new CompoundTag();
            entry.getValue().save(levelTag);
            levelsTag.put(entry.getKey().toString(), levelTag);
        }
        tag.put("Levels", levelsTag);
        
        // Save attributes
        CompoundTag attributesTag = new CompoundTag();
        for (Map.Entry<UUID, PlayerAttributes> entry : attributes.entrySet()) {
            CompoundTag attrTag = new CompoundTag();
            entry.getValue().save(attrTag);
            attributesTag.put(entry.getKey().toString(), attrTag);
        }
        tag.put("Attributes", attributesTag);
        
        // Save skills
        CompoundTag skillsTag = new CompoundTag();
        for (Map.Entry<UUID, PlayerSkills> entry : skills.entrySet()) {
            CompoundTag skillTag = new CompoundTag();
            entry.getValue().save(skillTag);
            skillsTag.put(entry.getKey().toString(), skillTag);
        }
        tag.put("Skills", skillsTag);
        
        return tag;
    }
    
    public PlayerLevel getLevel(Player player) {
        return levels.computeIfAbsent(player.getUUID(), k -> new PlayerLevel());
    }
    
    public PlayerAttributes getAttributes(Player player) {
        return attributes.computeIfAbsent(player.getUUID(), k -> new PlayerAttributes());
    }
    
    public PlayerSkills getSkills(Player player) {
        return skills.computeIfAbsent(player.getUUID(), k -> new PlayerSkills());
    }
}
