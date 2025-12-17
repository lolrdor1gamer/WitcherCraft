package org.tgr.witchercraft.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.player.PlayerAttributes;
import org.tgr.witchercraft.player.PlayerLevel;
import org.tgr.witchercraft.player.PlayerSkills;
import org.tgr.witchercraft.player.WitcherPlayerProgress;

/**
 * Client requests player progression data from server
 * Server responds with SyncPlayerDataPacket
 */
public class RequestPlayerDataPacket implements CustomPacketPayload {
    
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "request_player_data");
    
    public static final StreamCodec<FriendlyByteBuf, RequestPlayerDataPacket> CODEC = 
        CustomPacketPayload.codec(RequestPlayerDataPacket::write, RequestPlayerDataPacket::new);
    
    public static final CustomPacketPayload.Type<RequestPlayerDataPacket> TYPE = 
        new CustomPacketPayload.Type<>(ID);
    
    public RequestPlayerDataPacket() {
    }
    
    public RequestPlayerDataPacket(FriendlyByteBuf buf) {
        // No data needed
    }
    
    private void write(FriendlyByteBuf buf) {
        // No data to write
    }
    
    @Override
    public CustomPacketPayload.Type<RequestPlayerDataPacket> type() {
        return TYPE;
    }
    
    public static void register() {
        PayloadTypeRegistry.playC2S().register(TYPE, CODEC);
        
        ServerPlayNetworking.registerGlobalReceiver(TYPE, (payload, context) -> {
            context.server().submit(() -> {
                ServerPlayer player = context.player();
                
                // Get player data from server-side storage
                // Note: WitcherPlayerProgress.get() needs ServerLevel, but for now we'll use temporary instances
                // TODO: Fix SavedData API to properly persist
                PlayerLevel level = new PlayerLevel();
                PlayerAttributes attributes = new PlayerAttributes();
                PlayerSkills skills = new PlayerSkills();
                
                // Send data back to client
                ServerPlayNetworking.send(player, new SyncPlayerDataPacket(
                    level.getLevel(),
                    level.getExperience(),
                    level.getAttributePoints(),
                    level.getSkillPoints(),
                    attributes.getStrength(),
                    attributes.getVitality(),
                    attributes.getCombat(),
                    attributes.getSigns(),
                    attributes.getAlchemy(),
                    skills.getAllSkills()
                ));
            });
        });
    }
}
