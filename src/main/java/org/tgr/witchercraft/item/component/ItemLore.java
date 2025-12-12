package org.tgr.witchercraft.item.component;

/**
 * Describes the metadata shown for forge-related items.
 */
public record ItemLore(String category, String description, String stats, String useCase) {

    public ItemLore {
        category = category == null ? "" : category;
        description = description == null ? "" : description;
        stats = stats == null ? "" : stats;
        useCase = useCase == null ? "" : useCase;
    }

    public static ItemLore of(String category, String description, String stats, String useCase) {
        return new ItemLore(category, description, stats, useCase);
    }
}
