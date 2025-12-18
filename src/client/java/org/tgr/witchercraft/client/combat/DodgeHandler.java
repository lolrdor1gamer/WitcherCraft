package org.tgr.witchercraft.client.combat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.tgr.witchercraft.client.ui.UIKeyBindings;
import org.tgr.witchercraft.network.ClientNetworking;

/**
 * Handles the dodge/roll mechanic on the client side.
 */
@Environment(EnvType.CLIENT)
public class DodgeHandler {
    
    private static int dodgeCooldown = 0;
    private static final int COOLDOWN_TICKS = 20; // 1 second cooldown
    
    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> tick());
    }
    
    private static void tick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.screen != null) {
            return;
        }
        
        // Reduce cooldown
        if (dodgeCooldown > 0) {
            dodgeCooldown--;
        }
        
        // Check for dodge key press
        if (UIKeyBindings.DodgeKey.consumeClick() && dodgeCooldown <= 0) {
            performDodge(mc.player);
        }
    }
    
    private static void performDodge(LocalPlayer player) {
        // Determine dodge direction based on movement keys
        double forwardInput = 0;
        double strafeInput = 0;
        
        if (Minecraft.getInstance().options.keyUp.isDown()) forwardInput = 1;
        if (Minecraft.getInstance().options.keyDown.isDown()) forwardInput = -1;
        if (Minecraft.getInstance().options.keyLeft.isDown()) strafeInput = 1;
        if (Minecraft.getInstance().options.keyRight.isDown()) strafeInput = -1;
        
        // Default to backward dodge if no direction
        if (forwardInput == 0 && strafeInput == 0) {
            forwardInput = -1;
        }
        
        // Send dodge packet to server
        ClientNetworking.sendDodgeRequest(forwardInput, strafeInput);
        
        // Set cooldown
        dodgeCooldown = COOLDOWN_TICKS;
    }
    
    public static boolean isOnCooldown() {
        return dodgeCooldown > 0;
    }
    
    public static float getCooldownProgress() {
        return (float) dodgeCooldown / COOLDOWN_TICKS;
    }
}
