package org.tgr.witchercraft.signs;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

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

        player.addEffect(new MobEffectInstance(
                MobEffects.ABSORPTION,
                DURATION_TICKS,
                ABSORPTION_LEVEL,
                false,
                true
        ));

        player.addEffect(new MobEffectInstance(
                MobEffects.RESISTANCE,
                DURATION_TICKS,
                RESISTANCE_LEVEL,
                false,
                true
        ));

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.broadcastEntityEvent(player, (byte) 45); // Absorption-like particles
        }

        return true;
    }
}
