package org.tgr.witchercraft.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.tgr.witchercraft.player.WitcherPlayerData;

/**
 * Base class for consumable Witcher potions that apply toxicity and invoke custom handlers when used.
 */
public class WitcherPotionItem extends TranslatableTooltipItem {

    private final int toxicityGain;
    private final PotionEffectHandler handler;

    public WitcherPotionItem(Properties properties, String tooltipKey, int toxicityGain, PotionEffectHandler handler) {
        super(properties, tooltipKey);
        this.toxicityGain = Math.max(0, toxicityGain);
        this.handler = handler;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);
        if (!level.isClientSide() && entity instanceof ServerPlayer player) {
            if (toxicityGain > 0) {
                WitcherPlayerData.addToxicity(player, toxicityGain);
            }
            if (handler != null) {
                handler.onDrink(player, level);
            }
        }
        return result;
    }

    @FunctionalInterface
    public interface PotionEffectHandler {
        void onDrink(ServerPlayer player, Level level);
    }
}
