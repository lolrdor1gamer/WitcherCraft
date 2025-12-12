package org.tgr.witchercraft.player;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import org.tgr.witchercraft.network.SyncWitcherStatsPayload;
import org.tgr.witchercraft.signs.WitcherSign;

import java.util.Map;
import java.util.UUID;

/**
 * Tracks custom Witcher stats such as stamina, mana, toxicity, and sign cooldowns for each player.
 * Data currently lives in memory and resets on logout, which is sufficient for prototype gameplay loops.
 */
public final class WitcherPlayerData {

    private static final int MAX_STAMINA = 100;
    private static final int MAX_MANA = 100;
    private static final int MAX_TOXICITY = 100;

    private static final int STAMINA_REGEN_INTERVAL = 5;
    private static final int MANA_REGEN_INTERVAL = 4;
    private static final int STAMINA_REGEN_RATE = 1;
    private static final int MANA_REGEN_RATE = 1;
    private static final int TOXICITY_DECAY_INTERVAL = 40;
    private static final int SYNC_INTERVAL = 10;

    private static final Map<UUID, Stats> STATS = new Object2ObjectOpenHashMap<>();

    private WitcherPlayerData() {
    }

    private static Stats stats(Player player) {
        return STATS.computeIfAbsent(player.getUUID(), id -> new Stats());
    }

    public static int getStamina(Player player) {
        return stats(player).stamina;
    }

    public static int getMaxStamina() {
        return MAX_STAMINA;
    }

    public static int getMana(Player player) {
        return stats(player).mana;
    }

    public static int getMaxMana() {
        return MAX_MANA;
    }

    public static int getToxicity(Player player) {
        return stats(player).toxicity;
    }

    public static int getMaxToxicity() {
        return MAX_TOXICITY;
    }

    public static int getSignCooldown(Player player, String signName) {
        return stats(player).cooldowns.getOrDefault(signName, 0);
    }

    public static void setSignCooldown(Player player, String signName, int ticks) {
        Stats stats = stats(player);
        int value = Math.max(0, ticks);
        Integer previous = stats.cooldowns.put(signName, value);
        if (previous == null || previous != value) {
            stats.markDirty();
        }
    }

    public static void addStamina(Player player, int delta) {
        Stats stats = stats(player);
        int clamped = clamp(stats.stamina + delta, 0, MAX_STAMINA);
        if (clamped != stats.stamina) {
            stats.stamina = clamped;
            stats.markDirty();
        }
    }

    public static void addMana(Player player, int delta) {
        Stats stats = stats(player);
        int clamped = clamp(stats.mana + delta, 0, MAX_MANA);
        if (clamped != stats.mana) {
            stats.mana = clamped;
            stats.markDirty();
        }
    }

    public static void addToxicity(Player player, int delta) {
        Stats stats = stats(player);
        int clamped = clamp(stats.toxicity + delta, 0, MAX_TOXICITY);
        if (clamped != stats.toxicity) {
            stats.toxicity = clamped;
            stats.markDirty();
        }
    }

    public static void applyStaminaRegenBoost(Player player, int ticks, int bonus) {
        Stats stats = stats(player);
        if (ticks <= 0 || bonus <= 0) {
            return;
        }
        stats.staminaRegenBonusTicks = Math.max(stats.staminaRegenBonusTicks, ticks);
        stats.staminaRegenBonusAmount = Math.max(stats.staminaRegenBonusAmount, bonus);
        stats.markDirty();
    }

    public static void applyManaRegenBoost(Player player, int ticks, int bonus) {
        Stats stats = stats(player);
        if (ticks <= 0 || bonus <= 0) {
            return;
        }
        stats.manaRegenBonusTicks = Math.max(stats.manaRegenBonusTicks, ticks);
        stats.manaRegenBonusAmount = Math.max(stats.manaRegenBonusAmount, bonus);
        stats.markDirty();
    }

    public static boolean hasResourcesForSign(Player player, WitcherSign sign) {
        if (player.getAbilities().instabuild) {
            return getSignCooldown(player, sign.getName()) <= 0;
        }
        return getMana(player) >= sign.getManaCost()
            && getStamina(player) >= Math.max(1, sign.getManaCost() / 2)
            && getSignCooldown(player, sign.getName()) <= 0;
    }

    public static void commitSignCast(ServerPlayer player, WitcherSign sign) {
        if (!player.getAbilities().instabuild) {
            addMana(player, -sign.getManaCost());
            addStamina(player, -(Math.max(1, sign.getManaCost() / 2)));
            addToxicity(player, 1);
        }
        setSignCooldown(player, sign.getName(), sign.getCooldown());
    }

    public static void tick(ServerPlayer player) {
        Stats stats = stats(player);
        int ticks = player.tickCount;

        if (ticks % STAMINA_REGEN_INTERVAL == 0) {
            int regen = STAMINA_REGEN_RATE
                + (stats.staminaRegenBonusTicks > 0 ? stats.staminaRegenBonusAmount : 0)
                - (stats.toxicity > 60 ? 1 : 0);
            if (regen > 0) {
                int clamped = clamp(stats.stamina + regen, 0, MAX_STAMINA);
                if (clamped != stats.stamina) {
                    stats.stamina = clamped;
                    stats.markDirty();
                }
            }
        }

        if (ticks % MANA_REGEN_INTERVAL == 0) {
            int regen = MANA_REGEN_RATE
                + (stats.manaRegenBonusTicks > 0 ? stats.manaRegenBonusAmount : 0)
                - (stats.toxicity > 75 ? 1 : 0);
            if (regen > 0) {
                int clamped = clamp(stats.mana + regen, 0, MAX_MANA);
                if (clamped != stats.mana) {
                    stats.mana = clamped;
                    stats.markDirty();
                }
            }
        }

        if (ticks % TOXICITY_DECAY_INTERVAL == 0 && stats.toxicity > 0) {
            stats.toxicity--;
            stats.markDirty();
        }

        stats.cooldowns.replaceAll((sign, value) -> {
            int next = Math.max(0, value - 1);
            if (next != value) {
                stats.markDirty();
            }
            return next;
        });

        applyToxicityPenalties(player, stats, ticks);
        tickPotionBuffs(stats);
        maybeSync(player, stats);
    }

    public static void copy(ServerPlayer oldPlayer, ServerPlayer newPlayer) {
        Stats snapshot = stats(oldPlayer).copy();
        STATS.put(newPlayer.getUUID(), snapshot);
    }

    public static void remove(Player player) {
        remove(player.getUUID());
    }

    public static void remove(UUID playerId) {
        STATS.remove(playerId);
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private static void applyToxicityPenalties(ServerPlayer player, Stats stats, int ticks) {
        if (stats.toxicity >= 70 && ticks % 100 == 0) {
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0, false, false));
        }

        if (stats.toxicity >= 85 && ticks % 60 == 0) {
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 0, false, false));
        }

        if (stats.toxicity >= 90 && ticks % 40 == 0) {
            player.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0, false, false));
        }
    }

    private static void tickPotionBuffs(Stats stats) {
        boolean changed = false;

        if (stats.staminaRegenBonusTicks > 0) {
            stats.staminaRegenBonusTicks--;
            if (stats.staminaRegenBonusTicks == 0 && stats.staminaRegenBonusAmount > 0) {
                stats.staminaRegenBonusAmount = 0;
                changed = true;
            }
        }

        if (stats.manaRegenBonusTicks > 0) {
            stats.manaRegenBonusTicks--;
            if (stats.manaRegenBonusTicks == 0 && stats.manaRegenBonusAmount > 0) {
                stats.manaRegenBonusAmount = 0;
                changed = true;
            }
        }

        if (changed) {
            stats.markDirty();
        }
    }

    private static void maybeSync(ServerPlayer player, Stats stats) {
        stats.ticksUntilSync--;
        if (!stats.dirty && stats.ticksUntilSync > 0) {
            return;
        }

        SyncWitcherStatsPayload.send(player, stats.toSnapshot());
        stats.dirty = false;
        stats.ticksUntilSync = SYNC_INTERVAL;
    }

    public static StatsSnapshot snapshot(Player player) {
        return stats(player).toSnapshot();
    }

    public record StatsSnapshot(int stamina, int mana, int toxicity, Map<String, Integer> cooldowns) {
    }

    private static final class Stats {
        private int stamina = MAX_STAMINA;
        private int mana = MAX_MANA;
        private int toxicity = 0;
        private final Map<String, Integer> cooldowns = new Object2ObjectOpenHashMap<>();
        private int staminaRegenBonusTicks;
        private int staminaRegenBonusAmount;
        private int manaRegenBonusTicks;
        private int manaRegenBonusAmount;
        private int ticksUntilSync = SYNC_INTERVAL;
        private boolean dirty = true;

        private void markDirty() {
            this.dirty = true;
        }

        private Stats copy() {
            Stats stats = new Stats();
            stats.stamina = this.stamina;
            stats.mana = this.mana;
            stats.toxicity = this.toxicity;
            stats.cooldowns.putAll(this.cooldowns);
            stats.staminaRegenBonusTicks = this.staminaRegenBonusTicks;
            stats.staminaRegenBonusAmount = this.staminaRegenBonusAmount;
            stats.manaRegenBonusTicks = this.manaRegenBonusTicks;
            stats.manaRegenBonusAmount = this.manaRegenBonusAmount;
            stats.ticksUntilSync = SYNC_INTERVAL;
            stats.dirty = true;
            return stats;
        }

        private StatsSnapshot toSnapshot() {
            return new StatsSnapshot(stamina, mana, toxicity, new Object2ObjectOpenHashMap<>(cooldowns));
        }
    }
}
