package org.tgr.witchercraft.combat;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.tgr.witchercraft.Witchercraft;

/**
 * Handles combat-related events like dodge iframes.
 */
public class CombatEventHandler {
    
    public static void register() {
        // Tick all player dodge data
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                DodgeData.get(player).tick();
            }
        });
        
        // Prevent damage during iframes
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (entity instanceof ServerPlayer player) {
                DodgeData data = DodgeData.get(player);
                if (data.hasIframes()) {
                    // Cancel damage during dodge iframes
                    // Only cancel combat damage, not void/starve/etc.
                    if (isCombatDamage(source)) {
                        Witchercraft.LOGGER.debug("Dodge iframe blocked {} damage for {}", 
                            amount, player.getName().getString());
                        return false;
                    }
                }
            }
            return true;
        });
    }
    
    /**
     * Check if this is the type of damage that can be dodged
     */
    private static boolean isCombatDamage(DamageSource source) {
        // Can't dodge: void, starve, drown, suffocation, fall damage
        // Can dodge: melee, projectile, explosion, magic
        if (source.getDirectEntity() != source.getEntity() && source.getDirectEntity() != null) {
            return true; // Projectiles (indirect damage)
        }
        
        if (source.getEntity() != null) {
            return true; // Entity-caused damage
        }
        
        // Check for specific damage types that shouldn't be dodgeable
        String typeName = source.type().msgId();
        return !typeName.equals("outOfWorld") && 
               !typeName.equals("starve") && 
               !typeName.equals("drown") && 
               !typeName.equals("inWall") &&
               !typeName.equals("cramming") &&
               !typeName.equals("fall");
    }
}
