package org.tgr.witchercraft.quest;

import net.minecraft.world.entity.player.Player;
import org.tgr.witchercraft.faction.Faction;
import org.tgr.witchercraft.faction.ReputationLevel;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages quests for all players.
 * Works in both singleplayer and multiplayer.
 */
public final class QuestManager {

    private static final Map<UUID, PlayerQuests> PLAYER_QUESTS = new ConcurrentHashMap<>();
    private static final List<Quest> ALL_QUESTS = new ArrayList<>();

    static {
        // Register initial quests
        registerDefaultQuests();
    }

    private QuestManager() {
    }

    /**
     * Get all available quests for a player.
     */
    public static List<Quest> getAvailableQuests(Player player) {
        PlayerQuests quests = playerQuests(player);
        return quests.getAvailableQuests();
    }

    /**
     * Get all active quests for a player.
     */
    public static List<Quest> getActiveQuests(Player player) {
        PlayerQuests quests = playerQuests(player);
        return quests.getActiveQuests();
    }

    /**
     * Get all completed quests for a player.
     */
    public static List<Quest> getCompletedQuests(Player player) {
        PlayerQuests quests = playerQuests(player);
        return quests.getCompletedQuests();
    }

    /**
     * Get a quest by ID for a player.
     */
    public static Quest getQuest(Player player, String questId) {
        PlayerQuests quests = playerQuests(player);
        return quests.getQuest(questId);
    }

    /**
     * Start a quest for a player.
     */
    public static boolean startQuest(Player player, String questId) {
        PlayerQuests quests = playerQuests(player);
        Quest quest = quests.getQuest(questId);
        if (quest != null && quest.getStatus() == QuestStatus.AVAILABLE) {
            quest.setStatus(QuestStatus.ACTIVE);
            return true;
        }
        return false;
    }

    /**
     * Complete a quest for a player.
     */
    public static boolean completeQuest(Player player, String questId) {
        PlayerQuests quests = playerQuests(player);
        Quest quest = quests.getQuest(questId);
        if (quest != null && quest.getStatus() == QuestStatus.ACTIVE) {
            quest.setStatus(QuestStatus.COMPLETED);
            // TODO: Award rewards
            return true;
        }
        return false;
    }

    /**
     * Update quest progress.
     */
    public static void updateProgress(Player player, String questId, int progress) {
        PlayerQuests quests = playerQuests(player);
        Quest quest = quests.getQuest(questId);
        if (quest != null && quest.getStatus() == QuestStatus.ACTIVE) {
            quest.setProgress(progress);
            if (progress >= quest.getMaxProgress()) {
                completeQuest(player, questId);
            }
        }
    }

    /**
     * Remove quests for a player.
     */
    public static void remove(Player player) {
        PLAYER_QUESTS.remove(player.getUUID());
    }

    private static PlayerQuests playerQuests(Player player) {
        return PLAYER_QUESTS.computeIfAbsent(player.getUUID(), id -> new PlayerQuests());
    }

    private static void registerDefaultQuests() {
        ALL_QUESTS.add(new Quest(
            "drowner_contract",
            "Contract: Drowner Infestation",
            "A group of drowners has been terrorizing the local fishermen. Eliminate them.",
            QuestType.CONTRACT,
            5,
            100,
            50,
            null,
            null
        ));

        ALL_QUESTS.add(new Quest(
            "kaedwen_patrol",
            "Kaedwen Patrol Duty",
            "Join a Kaedweni patrol to protect the borders from monsters.",
            QuestType.FACTION,
            10,
            200,
            100,
            Faction.KAEDWEN,
            ReputationLevel.FRIENDLY
        ));

        ALL_QUESTS.add(new Quest(
            "ancient_treasure",
            "Treasure Hunt: Elder Relics",
            "Find the lost relics of the Elder Races hidden in the forest.",
            QuestType.TREASURE,
            15,
            500,
            0,
            null,
            null
        ));

        ALL_QUESTS.add(new Quest(
            "griffin_hunt",
            "Contract: Griffin Menace",
            "A griffin has been attacking travelers on the mountain pass.",
            QuestType.CONTRACT,
            20,
            300,
            75,
            null,
            null
        ));

        ALL_QUESTS.add(new Quest(
            "redanian_alliance",
            "Redanian Alliance",
            "Prove your loyalty to Redania by completing their trials.",
            QuestType.FACTION,
            25,
            1000,
            200,
            Faction.REDANIA,
            ReputationLevel.HONORED
        ));
    }

    private static final class PlayerQuests {
        private final Map<String, Quest> quests = new HashMap<>();

        PlayerQuests() {
            // Copy all registered quests for this player
            for (Quest template : ALL_QUESTS) {
                Quest copy = new Quest(
                    template.getId(),
                    template.getTitle(),
                    template.getDescription(),
                    template.getType(),
                    template.getRecommendedLevel(),
                    template.getRewardGold(),
                    template.getRewardReputation(),
                    template.getRequiredFaction(),
                    template.getRequiredLevel()
                );
                copy.setMaxProgress(template.getMaxProgress());
                quests.put(copy.getId(), copy);
            }
        }

        Quest getQuest(String id) {
            return quests.get(id);
        }

        List<Quest> getAvailableQuests() {
            return quests.values().stream()
                .filter(q -> q.getStatus() == QuestStatus.AVAILABLE)
                .sorted(Comparator.comparingInt(Quest::getRecommendedLevel))
                .toList();
        }

        List<Quest> getActiveQuests() {
            return quests.values().stream()
                .filter(q -> q.getStatus() == QuestStatus.ACTIVE)
                .sorted(Comparator.comparingInt(Quest::getRecommendedLevel))
                .toList();
        }

        List<Quest> getCompletedQuests() {
            return quests.values().stream()
                .filter(q -> q.getStatus() == QuestStatus.COMPLETED)
                .sorted(Comparator.comparingInt(Quest::getRecommendedLevel))
                .toList();
        }
    }
}
