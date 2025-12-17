package org.tgr.witchercraft.quest;

/**
 * Current status of a quest.
 */
public enum QuestStatus {
    AVAILABLE("Available", 0xFFCCCCCC),
    ACTIVE("Active", 0xFFFFD700),
    COMPLETED("Completed", 0xFF00CC00),
    FAILED("Failed", 0xFFCC0000);

    private final String displayName;
    private final int color;

    QuestStatus(String displayName, int color) {
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
