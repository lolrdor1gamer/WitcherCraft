package org.tgr.witchercraft.item;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public final class WitcherSwordHandler {

    private WitcherSwordHandler() {
    }

    public static void register() {
        AttackEntityCallback.EVENT.register(WitcherSwordHandler::handleAttack);
    }

    @SuppressWarnings("deprecation")
    private static InteractionResult handleAttack(Player player, Level level, InteractionHand hand, Entity target, EntityHitResult hitResult) {
        if (!(target instanceof LivingEntity livingTarget)) {
            return InteractionResult.PASS;
        }

        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() instanceof WitcherSwordItem sword) {
            boolean effective = sword.isEffectiveTarget(livingTarget);
            if (!effective) {
                sendFeedback(player, sword.swordType().feedbackKey());
                if (!level.isClientSide()) {
                    stack.hurtAndBreak(sword.ineffectiveDurabilityCost(), player, EquipmentSlot.MAINHAND);
                }
            } else if (!level.isClientSide()) {
                float bonus = sword.bonusDamage();
                if (bonus > 0 && player instanceof ServerPlayer serverPlayer) {
                    livingTarget.hurt(serverPlayer.damageSources().playerAttack(serverPlayer), bonus);
                }
            }
        }

        return InteractionResult.PASS;
    }


    private static void sendFeedback(Player player, String translationKey) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.displayClientMessage(Component.translatable(translationKey), true);
        }
    }
}
