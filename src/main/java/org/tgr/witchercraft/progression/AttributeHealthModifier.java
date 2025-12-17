package org.tgr.witchercraft.progression;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.player.PlayerAttributes;

/**
 * Applies Vitality-based health bonuses to players
 */
public class AttributeHealthModifier {
    
    private static final ResourceLocation WITCHER_HEALTH_ID = 
        ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "vitality_health");
    
    /**
     * Updates player's max health based on Vitality attribute
     * +2 HP (1 heart) per Vitality point
     */
    public static void updatePlayerHealth(ServerPlayer player) {
        // Get player's Vitality attribute (temporary until SavedData is fixed)
        PlayerAttributes tempAttributes = new PlayerAttributes();
        int vitality = tempAttributes.getVitality();
        
        // Calculate health bonus: +2 HP per Vitality point
        double healthBonus = vitality * 2.0;
        
        // Get health attribute
        AttributeInstance healthAttr = player.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttr == null) {
            return;
        }
        
        // Remove old modifier if exists
        healthAttr.removeModifier(WITCHER_HEALTH_ID);
        
        // Add new modifier
        if (healthBonus > 0) {
            AttributeModifier modifier = new AttributeModifier(
                WITCHER_HEALTH_ID,
                healthBonus,
                AttributeModifier.Operation.ADD_VALUE
            );
            healthAttr.addPermanentModifier(modifier);
        }
        
        // Heal player to prevent death from reduced max health
        if (player.getHealth() > player.getMaxHealth()) {
            player.setHealth(player.getMaxHealth());
        }
    }
}
