package org.tgr.witchercraft.quest;

/**
 * Types of quests available in the Witcher world.
 */
public enum QuestType {
    CONTRACT("Contract", 0xFFCC6600),      // Monster hunting contracts
    MAIN("Main Quest", 0xFFFFD700),        // Story quests
    SIDE("Side Quest", 0xFF66CCFF),        // Optional side quests
    TREASURE("Treasure Hunt", 0xFFFFAA00), // Treasure hunting
    FACTION("Faction Quest", 0xFF9966FF);  // Faction-specific quests

    private final String displayName;
    private final int color;

    QuestType(String displayName, int color) {
        this.displayName = displayName;
        this.color = color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getColor() {
        return color;
    }
}
