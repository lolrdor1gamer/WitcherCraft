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
    
    /**
     * Send a meditation request to the server
     * @param hours Number of in-game hours to skip (1-12)
     */
    public static void sendMeditationRequest(int hours) {
        if (ClientPlayNetworking.canSend(MeditationRequestPacket.TYPE)) {
            ClientPlayNetworking.send(new MeditationRequestPacket(hours));
        }
    }
    
    /**
     * Send a dodge request to the server
     * @param forward Forward movement direction (-1, 0, 1)
     * @param strafe Strafe movement direction (-1, 0, 1)
     */
    public static void sendDodgeRequest(double forward, double strafe) {
        if (ClientPlayNetworking.canSend(DodgeRequestPacket.TYPE)) {
            ClientPlayNetworking.send(new DodgeRequestPacket(forward, strafe));
        }
    }
}
