package org.tgr.witchercraft.faction;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tracks faction reputation for each player.
 * Reputation affects NPC interactions, quest availability, and prices.
 */
public final class FactionReputation {

    private static final Map<UUID, PlayerReputation> REPUTATIONS = new ConcurrentHashMap<>();

    private FactionReputation() {
    }

    /**
     * Get the player's reputation score with a faction.
     */
    public static int getReputation(Player player, Faction faction) {
        return reputation(player).getScore(faction);
    }

    /**
     * Get the player's reputation level with a faction.
     */
    public static ReputationLevel getLevel(Player player, Faction faction) {
        int score = getReputation(player, faction);
        return ReputationLevel.fromScore(score);
    }

    /**
     * Modify a player's reputation with a faction.
     * 
     * @param delta Amount to change (can be negative)
     */
    public static void modifyReputation(Player player, Faction faction, int delta) {
        if (delta == 0) {
            return;
        }
        
        PlayerReputation rep = reputation(player);
        int oldScore = rep.getScore(faction);
        int newScore = clamp(oldScore + delta, -2000, 5000);
        
        if (newScore != oldScore) {
            rep.setScore(faction, newScore);
            
            // Check for level change
            ReputationLevel oldLevel = ReputationLevel.fromScore(oldScore);
            ReputationLevel newLevel = ReputationLevel.fromScore(newScore);
            
            if (oldLevel != newLevel && player instanceof ServerPlayer) {
                // TODO: Send notification to player about reputation change
                // ServerNetworking.sendReputationUpdate((ServerPlayer)player, faction, newScore, newLevel);
            }
        }
    }

    /**
     * Set a player's reputation to a specific value.
     */
    public static void setReputation(Player player, Faction faction, int score) {
        PlayerReputation rep = reputation(player);
        rep.setScore(faction, clamp(score, -2000, 5000));
    }

    /**
     * Get all faction reputations for a player.
     */
    public static Map<Faction, Integer> getAllReputations(Player player) {
        return reputation(player).getAllScores();
    }

    /**
     * Get the player's global reputation (average across all factions).
     */
    public static int getGlobalReputation(Player player) {
        Map<Faction, Integer> allReps = getAllReputations(player);
        if (allReps.isEmpty()) {
            return 0;
        }
        
        int total = 0;
        for (int score : allReps.values()) {
            total += score;
        }
        return total / allReps.size();
    }

    /**
     * Get the player's global reputation level.
     */
    public static ReputationLevel getGlobalLevel(Player player) {
        int globalScore = getGlobalReputation(player);
        return ReputationLevel.fromScore(globalScore);
    }

    /**
     * Copy reputation data when a player respawns.
     */
    public static void copy(ServerPlayer oldPlayer, ServerPlayer newPlayer) {
        PlayerReputation oldRep = REPUTATIONS.get(oldPlayer.getUUID());
        if (oldRep != null) {
            REPUTATIONS.put(newPlayer.getUUID(), oldRep.copy());
        }
    }

    /**
     * Remove reputation data for a player.
     */
    public static void remove(Player player) {
        REPUTATIONS.remove(player.getUUID());
    }

    private static PlayerReputation reputation(Player player) {
        return REPUTATIONS.computeIfAbsent(player.getUUID(), id -> new PlayerReputation());
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private static final class PlayerReputation {
        private final Object2IntOpenHashMap<Faction> scores = new Object2IntOpenHashMap<>();

        PlayerReputation() {
            scores.defaultReturnValue(0);
            // Initialize all factions to neutral
            for (Faction faction : Faction.values()) {
                scores.put(faction, 0);
            }
        }

        int getScore(Faction faction) {
            return scores.getInt(faction);
        }

        void setScore(Faction faction, int score) {
            scores.put(faction, score);
        }

        Map<Faction, Integer> getAllScores() {
            return new Object2IntOpenHashMap<>(scores);
        }

        PlayerReputation copy() {
            PlayerReputation copy = new PlayerReputation();
            copy.scores.putAll(this.scores);
            return copy;
        }
    }
}
