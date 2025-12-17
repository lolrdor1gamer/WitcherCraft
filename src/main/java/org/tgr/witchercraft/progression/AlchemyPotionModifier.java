package org.tgr.witchercraft.progression;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.Holder;
import org.tgr.witchercraft.player.PlayerAttributes;

/**
 * Applies Alchemy attribute bonuses to potion effects
 */
public class AlchemyPotionModifier {
    
    /**
     * Applies a potion effect with duration scaled by Alchemy attribute
     * Each Alchemy point adds 10% to duration
     */
    public static void applyScaledEffect(ServerPlayer player, Holder<MobEffect> effect, int baseDuration, int amplifier) {
        // Get player's Alchemy attribute (temporary until SavedData is fixed)
        PlayerAttributes tempAttributes = new PlayerAttributes();
        int alchemy = tempAttributes.getAlchemy();
        
        // Each Alchemy point adds 10% duration
        float durationMultiplier = 1.0f + (alchemy * 0.1f);
        int scaledDuration = (int)(baseDuration * durationMultiplier);
        
        // Apply the effect with scaled duration
        player.addEffect(new MobEffectInstance(effect, scaledDuration, amplifier, false, true));
    }
    
    /**
     * Scales an existing MobEffectInstance's duration by Alchemy attribute
     */
    public static MobEffectInstance scaleEffectDuration(PlayerAttributes attributes, MobEffectInstance effect) {
        int alchemy = attributes.getAlchemy();
        
        // Each Alchemy point adds 10% duration
        float durationMultiplier = 1.0f + (alchemy * 0.1f);
        int scaledDuration = (int)(effect.getDuration() * durationMultiplier);
        
        return new MobEffectInstance(
            effect.getEffect(),
            scaledDuration,
            effect.getAmplifier(),
            effect.isAmbient(),
            effect.isVisible()
        );
    }
}
