package org.tgr.witchercraft.item.component;

import net.minecraft.core.component.DataComponents;

public class ForgedArmorItem extends LoreItem {

    public ForgedArmorItem(Properties properties, ItemLore lore, int durability) {
        super(applyArmorComponents(properties, durability), ItemCategory.ARMOR, lore);
    }

    private static Properties applyArmorComponents(Properties properties, int durability) {
        return properties
            .stacksTo(1)
            .component(DataComponents.MAX_DAMAGE, durability);
    }
}
