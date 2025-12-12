package org.tgr.witchercraft.item.component;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.tgr.witchercraft.item.WitcherItem;

import java.util.function.Consumer;

/**
 * Base class for descriptive Witcher items organized by category.
 */
public abstract class LoreItem extends WitcherItem {

    private final ItemCategory category;
    private final ItemLore lore;

    protected LoreItem(Properties properties, ItemCategory category, ItemLore lore) {
        super(properties);
        this.category = category;
        this.lore = lore;
    }

    public ItemCategory category() {
        return category;
    }

    public ItemLore lore() {
        return lore;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        if (lore == null) {
            return;
        }

        if (!lore.category().isBlank()) {
            tooltip.accept(Component.literal(lore.category()).withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD));
        }
        if (!lore.description().isBlank()) {
            tooltip.accept(Component.literal(lore.description()).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        }
        if (!lore.stats().isBlank()) {
            tooltip.accept(Component.literal(lore.stats()).withStyle(ChatFormatting.AQUA));
        }
        if (!lore.useCase().isBlank()) {
            tooltip.accept(Component.literal("Use: " + lore.useCase()).withStyle(ChatFormatting.DARK_GREEN));
        }
    }
}
