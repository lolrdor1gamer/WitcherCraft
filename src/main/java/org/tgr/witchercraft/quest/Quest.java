package org.tgr.witchercraft.quest;

import org.tgr.witchercraft.faction.Faction;
import org.tgr.witchercraft.faction.ReputationLevel;

import org.jetbrains.annotations.Nullable;

/**
 * Represents a quest that a player can undertake.
 */
public class Quest {
    private final String id;
    private final String title;
    private final String description;
    private final QuestType type;
    private final int recommendedLevel;
    private final int rewardGold;
    private final int rewardReputation;
    
    @Nullable
    private final Faction requiredFaction;
    @Nullable
    private final ReputationLevel requiredLevel;
    
    private QuestStatus status;
    private int progress;
    private int maxProgress;

    public Quest(String id, String title, String description, QuestType type, 
                 int recommendedLevel, int rewardGold, int rewardReputation,
                 @Nullable Faction requiredFaction, @Nullable ReputationLevel requiredLevel) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.recommendedLevel = recommendedLevel;
        this.rewardGold = rewardGold;
        this.rewardReputation = rewardReputation;
        this.requiredFaction = requiredFaction;
        this.requiredLevel = requiredLevel;
        this.status = QuestStatus.AVAILABLE;
        this.progress = 0;
        this.maxProgress = 1;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public QuestType getType() {
        return type;
    }

    public int getRecommendedLevel() {
        return recommendedLevel;
    }

    public int getRewardGold() {
        return rewardGold;
    }

    public int getRewardReputation() {
        return rewardReputation;
    }

    @Nullable
    public Faction getRequiredFaction() {
        return requiredFaction;
    }

    @Nullable
    public ReputationLevel getRequiredLevel() {
        return requiredLevel;
    }

    public QuestStatus getStatus() {
        return status;
    }

    public void setStatus(QuestStatus status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = Math.min(progress, maxProgress);
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public int getProgressPercent() {
        if (maxProgress == 0) return 0;
        return (progress * 100) / maxProgress;
    }

    public boolean isAvailable() {
        return status == QuestStatus.AVAILABLE || status == QuestStatus.ACTIVE;
    }

    public boolean isCompleted() {
        return status == QuestStatus.COMPLETED;
    }
}
