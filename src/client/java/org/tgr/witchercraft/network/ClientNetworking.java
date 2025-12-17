package org.tgr.witchercraft.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import org.tgr.witchercraft.client.player.ClientPlayerData;
import org.tgr.witchercraft.client.player.ClientWitcherStats;

/**
 * Client-side networking utilities for sending packets to server
 */
@Environment(EnvType.CLIENT)
public class ClientNetworking {

    public static void initialize() {
        ClientPlayNetworking.registerGlobalReceiver(SyncWitcherStatsPayload.TYPE, (payload, context) ->
            context.client().execute(() -> ClientWitcherStats.get().update(payload))
        );
        
        ClientPlayNetworking.registerGlobalReceiver(SyncPlayerDataPacket.TYPE, (payload, context) ->
            context.client().execute(() -> ClientPlayerData.update(payload))
        );
    }
    
    /**
     * Send a sign casting request to the server
     */
    public static void sendSignCast(String signName) {
        if (ClientPlayNetworking.canSend(CastSignPacket.TYPE)) {
            ClientPlayNetworking.send(new CastSignPacket(signName));
        }
    }
    
    /**
     * Request player progression data from server
     */
    public static void requestPlayerData() {
        if (ClientPlayNetworking.canSend(RequestPlayerDataPacket.TYPE)) {
            ClientPlayNetworking.send(new RequestPlayerDataPacket());
        }
    }
}
