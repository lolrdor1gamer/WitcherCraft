package org.tgr.witchercraft.faction;

/**
 * Represents the major political factions in The Witcher universe.
 * Each faction has a distinct identity, territory, and agenda.
 */
public enum Faction {
    KAEDWEN("Kaedwen", 0x1E4A7C, "Northern kingdom known for harsh winters and disciplined soldiers"),
    REDANIA("Redania", 0x8B0000, "Powerful northern kingdom with extensive spy network"),
    TEMERIA("Temeria", 0x2C5F2D, "Prosperous kingdom famous for its cavalry"),
    NILFGAARD("Nilfgaard", 0x1A1A1A, "Southern empire seeking to conquer the North"),
    SKELLIGE("Skellige", 0x4A7BA7, "Island clans of fierce warriors and sailors"),
    WITCHERS("Witchers", 0x8B6914, "Monster hunters maintaining neutrality"),
    SCOIA_TAEL("Scoia'tael", 0x3A6B35, "Elven guerrilla fighters resisting human oppression");

    private final String displayName;
    private final int color;
    private final String description;

    Faction(String displayName, int color, String description) {
        this.displayName = displayName;
        this.color = color;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    /**
     * @return Localization key for this faction's name
     */
    public String getTranslationKey() {
        return "faction.witchercraft." + name().toLowerCase();
    }
}
