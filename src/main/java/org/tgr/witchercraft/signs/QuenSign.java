package org.tgr.witchercraft.signs;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.tgr.witchercraft.player.PlayerAttributes;

/**
 * Quen - Defensive shield providing temporary absorption and toughness.
 */
public class QuenSign extends WitcherSign {

    private static final int DURATION_TICKS = 200; // 10 seconds
    private static final int ABSORPTION_LEVEL = 1; // 4 extra hearts
    private static final int RESISTANCE_LEVEL = 1; // 20% damage reduction

    public QuenSign() {
        super("quen", 160, 25); // 8 second cooldown
    }

    @Override
    public boolean cast(Player player, Level level) {
        if (!canCast(player)) {
            return false;
        }
        
        // Calculate shield strength based on Signs attribute
        int duration = DURATION_TICKS;
        int absorptionLevel = ABSORPTION_LEVEL;
        int resistanceLevel = RESISTANCE_LEVEL;
        
        if (player instanceof ServerPlayer && level instanceof ServerLevel) {
            // Get player Signs attribute (temporary until SavedData is fixed)
            PlayerAttributes tempAttributes = new PlayerAttributes();
            int signsAttribute = tempAttributes.getSigns();
            
            // Each Signs point adds 1 second (20 ticks) to duration
            duration = DURATION_TICKS + (signsAttribute * 20);
            // Each 2 Signs points adds 1 level to absorption (4 hearts per level)
            absorptionLevel = ABSORPTION_LEVEL + (signsAttribute / 2);
            // Each 3 Signs points adds 1 level to resistance (20% per level)
            resistanceLevel = RESISTANCE_LEVEL + (signsAttribute / 3);
        }

        player.addEffect(new MobEffectInstance(
                MobEffects.ABSORPTION,
                duration,
                absorptionLevel,
                false,
                true
        ));

        player.addEffect(new MobEffectInstance(
                MobEffects.RESISTANCE,
                duration,
                resistanceLevel,
                false,
                true
        ));

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.broadcastEntityEvent(player, (byte) 45); // Absorption-like particles
        }

        return true;
    }
}
