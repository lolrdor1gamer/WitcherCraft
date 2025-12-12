package org.tgr.witchercraft.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

/**
 * Basic Witcher item that renders a localized tooltip line.
 */
public class TranslatableTooltipItem extends WitcherItem {

    private final String tooltipKey;
    private final ChatFormatting[] styles;

    public TranslatableTooltipItem(Properties properties, String tooltipKey, ChatFormatting... styles) {
        super(properties);
        this.tooltipKey = tooltipKey;
        this.styles = styles == null || styles.length == 0 ? new ChatFormatting[]{ChatFormatting.DARK_AQUA} : styles;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        if (tooltipKey == null || tooltipKey.isBlank()) {
            return;
        }
        tooltip.accept(Component.translatable(tooltipKey).withStyle(styles));
    }
}
