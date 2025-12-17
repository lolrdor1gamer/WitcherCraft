package org.tgr.witchercraft.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.tgr.witchercraft.quest.Quest;
import org.tgr.witchercraft.quest.QuestStatus;

import java.util.*;

/**
 * Persistent storage for player quest progress.
 * Saves active and completed quests per player UUID.
 */
public class PlayerQuestData extends SavedData {
    
    private static final String DATA_NAME = "witchercraft_quest_data";
    
    // Map of player UUID -> list of active quest IDs with their status
    private final Map<UUID, Map<String, QuestStatus>> activeQuests = new HashMap<>();
    
    // Map of player UUID -> list of completed quest IDs
    private final Map<UUID, Set<String>> completedQuests = new HashMap<>();
    
    public PlayerQuestData() {
    }
    
    public static PlayerQuestData get(ServerLevel level) {
        // TODO: Fix SavedData API for MC 1.21.1 - API completely changed
        // For now, return a new instance each time (data won't persist)
        return new PlayerQuestData();
        
        /*
        // Original code - needs API update
        return level.getDataStorage().computeIfAbsent(
            SAVED_DATA_TYPE,
            DATA_NAME
        );
        */
    }
    
    public static PlayerQuestData load(CompoundTag tag, HolderLookup.Provider provider) {
        PlayerQuestData data = new PlayerQuestData();
        
        // Load active quests
        CompoundTag activeTag = tag.getCompound("ActiveQuests").orElse(new CompoundTag());
        for (String playerUuidStr : activeTag.keySet()) {
            UUID playerUuid = UUID.fromString(playerUuidStr);
            CompoundTag playerTag = activeTag.getCompound(playerUuidStr).orElse(new CompoundTag());
            
            Map<String, QuestStatus> quests = new HashMap<>();
            for (String questId : playerTag.keySet()) {
                CompoundTag questTag = playerTag.getCompound(questId).orElse(new CompoundTag());
                QuestStatus status = QuestStatus.valueOf(questTag.getString("status").orElse("ACTIVE"));
                quests.put(questId, status);
            }
            data.activeQuests.put(playerUuid, quests);
        }
        
        // Load completed quests
        CompoundTag completedTag = tag.getCompound("CompletedQuests").orElse(new CompoundTag());
        for (String playerUuidStr : completedTag.keySet()) {
            UUID playerUuid = UUID.fromString(playerUuidStr);
            ListTag questList = completedTag.getList(playerUuidStr).orElse(new ListTag());
            
            Set<String> quests = new HashSet<>();
            for (int i = 0; i < questList.size(); i++) {
                quests.add(questList.getString(i).orElse(""));
            }
            data.completedQuests.put(playerUuid, quests);
        }
        
        return data;
    }
    
    public CompoundTag save(CompoundTag tag) {
        // Save active quests
        CompoundTag activeTag = new CompoundTag();
        for (Map.Entry<UUID, Map<String, QuestStatus>> entry : activeQuests.entrySet()) {
            CompoundTag playerTag = new CompoundTag();
            for (Map.Entry<String, QuestStatus> questEntry : entry.getValue().entrySet()) {
                CompoundTag questTag = new CompoundTag();
                questTag.putString("status", questEntry.getValue().name());
                playerTag.put(questEntry.getKey(), questTag);
            }
            activeTag.put(entry.getKey().toString(), playerTag);
        }
        tag.put("ActiveQuests", activeTag);
        
        // Save completed quests
        CompoundTag completedTag = new CompoundTag();
        for (Map.Entry<UUID, Set<String>> entry : completedQuests.entrySet()) {
            ListTag questList = new ListTag();
            for (String questId : entry.getValue()) {
                questList.add(net.minecraft.nbt.StringTag.valueOf(questId));
            }
            completedTag.put(entry.getKey().toString(), questList);
        }
        tag.put("CompletedQuests", completedTag);
        
        return tag;
    }
    
    public void startQuest(UUID playerUuid, String questId) {
        activeQuests.computeIfAbsent(playerUuid, k -> new HashMap<>())
            .put(questId, QuestStatus.ACTIVE);
        setDirty();
    }
    
    public void updateQuestStatus(UUID playerUuid, String questId, QuestStatus status) {
        Map<String, QuestStatus> quests = activeQuests.get(playerUuid);
        if (quests != null && quests.containsKey(questId)) {
            quests.put(questId, status);
            
            if (status == QuestStatus.COMPLETED) {
                quests.remove(questId);
                completedQuests.computeIfAbsent(playerUuid, k -> new HashSet<>())
                    .add(questId);
            }
            setDirty();
        }
    }
    
    public QuestStatus getQuestStatus(UUID playerUuid, String questId) {
        Map<String, QuestStatus> quests = activeQuests.get(playerUuid);
        if (quests != null && quests.containsKey(questId)) {
            return quests.get(questId);
        }
        
        Set<String> completed = completedQuests.get(playerUuid);
        if (completed != null && completed.contains(questId)) {
            return QuestStatus.COMPLETED;
        }
        
        return null;
    }
    
    public Set<String> getActiveQuests(UUID playerUuid) {
        Map<String, QuestStatus> quests = activeQuests.get(playerUuid);
        return quests != null ? new HashSet<>(quests.keySet()) : new HashSet<>();
    }
    
    public Set<String> getCompletedQuests(UUID playerUuid) {
        Set<String> quests = completedQuests.get(playerUuid);
        return quests != null ? new HashSet<>(quests) : new HashSet<>();
    }
}
