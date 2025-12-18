package org.tgr.witchercraft.combat;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.tgr.witchercraft.Witchercraft;

import java.util.WeakHashMap;

/**
 * Stores dodge state and invincibility frame data for players.
 * Uses a weak hash map to avoid memory leaks.
 */
public class DodgeData {
    
    private static final WeakHashMap<LivingEntity, DodgeData> PLAYER_DATA = new WeakHashMap<>();
    
    // Timing constants (in ticks)
    public static final int COOLDOWN_TICKS = 20; // 1 second
    public static final int IFRAME_TICKS = 8;    // 0.4 seconds of invincibility
    
    private int cooldownRemaining = 0;
    private int iframesRemaining = 0;
    private boolean isDodging = false;
    
    public static DodgeData get(LivingEntity entity) {
        return PLAYER_DATA.computeIfAbsent(entity, e -> new DodgeData());
    }
    
    /**
     * Call this every tick to update dodge state
     */
    public void tick() {
        if (cooldownRemaining > 0) {
            cooldownRemaining--;
        }
        
        if (iframesRemaining > 0) {
            iframesRemaining--;
            if (iframesRemaining == 0) {
                isDodging = false;
            }
        }
    }
    
    /**
     * Start a dodge - sets cooldown and iframes
     */
    public void startDodge() {
        cooldownRemaining = COOLDOWN_TICKS;
        iframesRemaining = IFRAME_TICKS;
        isDodging = true;
    }
    
    /**
     * Check if player is currently on cooldown
     */
    public boolean isOnCooldown() {
        return cooldownRemaining > 0;
    }
    
    /**
     * Check if player currently has invincibility frames
     */
    public boolean hasIframes() {
        return iframesRemaining > 0;
    }
    
    /**
     * Check if player is in the middle of a dodge animation
     */
    public boolean isDodging() {
        return isDodging;
    }
    
    public int getCooldownRemaining() {
        return cooldownRemaining;
    }
    
    public int getIframesRemaining() {
        return iframesRemaining;
    }
    
    public float getCooldownProgress() {
        return (float) cooldownRemaining / COOLDOWN_TICKS;
    }
}
