package org.tgr.witchercraft.sign;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;
import org.tgr.witchercraft.client.player.ClientWitcherStats;
import org.tgr.witchercraft.network.ClientNetworking;
import org.tgr.witchercraft.registry.ModSigns;
import org.tgr.witchercraft.signs.WitcherSign;

/**
 * Handles sign casting when keys are pressed
 */
@Environment(EnvType.CLIENT)
public class SignCastingHandler {

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) {
                return;
            }

            while (SignKeyBindings.IgniKey.consumeClick()) {
                tryCast("igni");
            }
            while (SignKeyBindings.AardKey.consumeClick()) {
                tryCast("aard");
            }
            while (SignKeyBindings.AxiiKey.consumeClick()) {
                tryCast("axii");
            }
            while (SignKeyBindings.QuenKey.consumeClick()) {
                tryCast("quen");
            }
            while (SignKeyBindings.YrdenKey.consumeClick()) {
                tryCast("yrden");
            }
        });
    }

    public static boolean tryCast(String signName) {
        Minecraft client = Minecraft.getInstance();
        Player player = client.player;

        if (player == null) {
            return false;
        }

        WitcherSign sign = ModSigns.get(signName);
        if (sign == null) {
            return false;
        }

        ClientWitcherStats.CastStatus status = ClientWitcherStats.get().canCast(sign);
        if (status != ClientWitcherStats.CastStatus.OK) {
            showFailureMessage(player, sign, status);
            return false;
        }

        ClientNetworking.sendSignCast(signName);
        player.displayClientMessage(Component.translatable("hud.witchercraft.feedback.casting", sign.getName().toUpperCase()), true);
        return true;
    }

    private static void showFailureMessage(Player player, WitcherSign sign, ClientWitcherStats.CastStatus status) {
        Component message;
        switch (status) {
            case LOW_MANA -> message = Component.translatable("hud.witchercraft.feedback.low_mana", sign.getName());
            case LOW_STAMINA -> message = Component.translatable("hud.witchercraft.feedback.low_stamina", sign.getName());
            case ON_COOLDOWN -> message = Component.translatable("hud.witchercraft.feedback.cooldown", sign.getName());
            default -> message = Component.translatable("hud.witchercraft.feedback.error");
        }
        player.displayClientMessage(message, true);
    }
}
