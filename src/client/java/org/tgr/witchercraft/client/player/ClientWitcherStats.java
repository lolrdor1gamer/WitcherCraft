package org.tgr.witchercraft.client.player;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.jetbrains.annotations.Nullable;
import org.tgr.witchercraft.network.SyncWitcherStatsPayload;
import org.tgr.witchercraft.player.WitcherPlayerData;
import org.tgr.witchercraft.signs.WitcherSign;

import java.util.Locale;
import java.util.Map;

/**
 * Lightweight client-side cache of the witcher stamina/mana/cooldown state.
 */
public final class ClientWitcherStats {

    public enum CastStatus {
        OK,
        LOW_MANA,
        LOW_STAMINA,
        ON_COOLDOWN
    }

    private static final ClientWitcherStats INSTANCE = new ClientWitcherStats();

    private int stamina = WitcherPlayerData.getMaxStamina();
    private int mana = WitcherPlayerData.getMaxMana();
    private int toxicity = 0;
    private final Object2IntMap<String> cooldowns = new Object2IntOpenHashMap<>();

    private ClientWitcherStats() {
    }

    public static ClientWitcherStats get() {
        return INSTANCE;
    }

    public void update(SyncWitcherStatsPayload payload) {
        this.stamina = payload.stamina();
        this.mana = payload.mana();
        this.toxicity = payload.toxicity();
        cooldowns.clear();
        for (Map.Entry<String, Integer> entry : payload.cooldowns().entrySet()) {
            cooldowns.put(entry.getKey().toLowerCase(Locale.ROOT), entry.getValue().intValue());
        }
    }

    public CastStatus canCast(@Nullable WitcherSign sign) {
        if (sign == null) {
            return CastStatus.OK;
        }
        if (getCooldown(sign.getName()) > 0) {
            return CastStatus.ON_COOLDOWN;
        }
        if (mana < sign.getManaCost()) {
            return CastStatus.LOW_MANA;
        }
        if (stamina < Math.max(1, sign.getManaCost() / 2)) {
            return CastStatus.LOW_STAMINA;
        }
        return CastStatus.OK;
    }

    public int getStamina() {
        return stamina;
    }

    public int getMana() {
        return mana;
    }

    public int getToxicity() {
        return toxicity;
    }

    public int getCooldown(String name) {
        return cooldowns.getOrDefault(name.toLowerCase(Locale.ROOT), 0);
    }

    public float getStaminaRatio() {
        return stamina / (float) WitcherPlayerData.getMaxStamina();
    }

    public float getManaRatio() {
        return mana / (float) WitcherPlayerData.getMaxMana();
    }

    public float getToxicityRatio() {
        return toxicity / (float) WitcherPlayerData.getMaxToxicity();
    }

    public Object2IntMap<String> cooldowns() {
        return cooldowns;
    }
}
