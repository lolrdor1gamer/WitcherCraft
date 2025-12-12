package org.tgr.witchercraft.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.player.WitcherPlayerData;
import org.tgr.witchercraft.registry.ModSigns;
import org.tgr.witchercraft.signs.WitcherSign;

/**
 * Centralized packet for sign casting (Client to Server)
 * Registration must only happen ONCE during mod initialization
 */
public class CastSignPacket implements CustomPacketPayload {
    
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "cast_sign");
    
    public static final StreamCodec<FriendlyByteBuf, CastSignPacket> CODEC = 
        CustomPacketPayload.codec(CastSignPacket::write, CastSignPacket::new);
    
    public static final CustomPacketPayload.Type<CastSignPacket> TYPE = 
        new CustomPacketPayload.Type<>(ID);

    private final String signName;
    
    public CastSignPacket(String signName) {
        this.signName = signName;
    }
    
    public CastSignPacket(FriendlyByteBuf buf) {
        this.signName = buf.readUtf();
    }
    
    private void write(FriendlyByteBuf buf) {
        buf.writeUtf(signName);
    }
    
    @Override
    public CustomPacketPayload.Type<CastSignPacket> type() {
        return TYPE;
    }
    
    /**
     * Register the packet ONLY ONCE during mod initialization
     * Call from Witchercraft.onInitialize() on the main thread
     */
    public static void register() {
        // Register payload type (only once!)
        PayloadTypeRegistry.playC2S().register(TYPE, CODEC);
        
        // Register server-side handler
        ServerPlayNetworking.registerGlobalReceiver(TYPE, (payload, context) -> {
            context.server().submit(() -> {
                ServerPlayer player = context.player();
                
                WitcherSign sign = ModSigns.get(payload.signName);
                if (sign == null) {
                    return;
                }

                if (!WitcherPlayerData.hasResourcesForSign(player, sign)) {
                    return;
                }

                if (sign.cast(player, player.level())) {
                    WitcherPlayerData.commitSignCast(player, sign);
                }
            });
        });
    }
}
