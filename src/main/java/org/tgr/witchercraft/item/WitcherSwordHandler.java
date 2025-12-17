package org.tgr.witchercraft.item;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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
import org.tgr.witchercraft.player.PlayerAttributes;

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
            
            // FIXED: Messages were inverted - only show warning if NOT effective
            if (!effective) {
                // Using wrong sword type - show feedback and extra durability damage
                sendFeedback(player, sword.swordType().feedbackKey());
                if (!level.isClientSide()) {
                    stack.hurtAndBreak(sword.ineffectiveDurabilityCost(), player, EquipmentSlot.MAINHAND);
                }
            } else if (!level.isClientSide()) {
                // Using correct sword type - apply bonus damage
                float bonus = sword.bonusDamage();
                
                // Apply Combat attribute bonus damage (server-side only)
                if (player instanceof ServerPlayer serverPlayer && level instanceof ServerLevel serverLevel) {
                    // Get player Combat attribute (temporary until SavedData is fixed)
                    PlayerAttributes tempAttributes = new PlayerAttributes();
                    int combatAttribute = tempAttributes.getCombat();
                    
                    // Each Combat point adds 2% damage bonus
                    float combatBonus = bonus * (combatAttribute * 0.02f);
                    bonus += combatBonus;
                    
                    // Critical hit chance: 5% base + 1% per Combat point
                    float critChance = 0.05f + (combatAttribute * 0.01f);
                    boolean isCritical = level.random.nextFloat() < critChance;
                    
                    if (isCritical) {
                        // Critical hit: 1.5x damage multiplier
                        bonus *= 1.5f;
                        // TODO: Add critical hit visual/sound effects
                        serverPlayer.displayClientMessage(
                            Component.literal("Critical Hit!").withStyle(net.minecraft.ChatFormatting.GOLD),
                            true
                        );
                    }
                }
                
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
