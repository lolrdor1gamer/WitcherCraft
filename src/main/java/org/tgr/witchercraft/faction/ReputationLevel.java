package org.tgr.witchercraft.faction;

/**
 * Discrete reputation tiers that determine NPC behavior and quest availability.
 */
public enum ReputationLevel {
    HATED(-1000, "Hated", 0x8B0000),
    HOSTILE(-500, "Hostile", 0xCC3333),
    UNFRIENDLY(-100, "Unfriendly", 0xCC6633),
    NEUTRAL(0, "Neutral", 0xCCCCCC),
    FRIENDLY(100, "Friendly", 0x99CC66),
    HONORED(500, "Honored", 0x66CC99),
    REVERED(1000, "Revered", 0x3399FF),
    EXALTED(2500, "Exalted", 0xFFD700);

    private final int threshold;
    private final String displayName;
    private final int color;

    ReputationLevel(int threshold, String displayName, int color) {
        this.threshold = threshold;
        this.displayName = displayName;
        this.color = color;
    }

    public int getThreshold() {
        return threshold;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getColor() {
        return color;
    }

    /**
     * Calculate the reputation level for a given score.
     */
    public static ReputationLevel fromScore(int score) {
        ReputationLevel result = NEUTRAL;
        for (ReputationLevel level : values()) {
            if (score >= level.threshold) {
                result = level;
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * @return The next reputation level, or null if already at max
     */
    public ReputationLevel next() {
        int nextOrdinal = ordinal() + 1;
        return nextOrdinal < values().length ? values()[nextOrdinal] : null;
    }

    /**
     * Calculate progress percentage to the next level (0-100).
     */
    public int progressToNext(int currentScore) {
        ReputationLevel next = next();
        if (next == null) {
            return 100;
        }
        int range = next.threshold - threshold;
        int progress = currentScore - threshold;
        return Math.min(100, Math.max(0, (progress * 100) / range));
    }
}
