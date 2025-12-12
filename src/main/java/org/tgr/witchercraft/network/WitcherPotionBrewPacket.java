package org.tgr.witchercraft.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.tgr.witchercraft.Witchercraft;

/**
 * Placeholder payload for future Witcher brewing station interactions.
 */
public class WitcherPotionBrewPacket implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "brew_potion");
    public static final Type<WitcherPotionBrewPacket> TYPE = new Type<>(ID);
    public static final StreamCodec<FriendlyByteBuf, WitcherPotionBrewPacket> CODEC =
        CustomPacketPayload.codec((packet, buf) -> {
        }, WitcherPotionBrewPacket::new);

    public WitcherPotionBrewPacket() {
    }

    private WitcherPotionBrewPacket(FriendlyByteBuf buf) {
        this();
    }

    @Override
    public Type<WitcherPotionBrewPacket> type() {
        return TYPE;
    }

    public static void register() {
        PayloadTypeRegistry.playC2S().register(TYPE, CODEC);
        ServerPlayNetworking.registerGlobalReceiver(TYPE, (payload, context) -> {
            ServerPlayer player = context.player();
            if (player == null) {
                return;
            }
            // TODO hook into brewing block entity once implemented
        });
    }
}