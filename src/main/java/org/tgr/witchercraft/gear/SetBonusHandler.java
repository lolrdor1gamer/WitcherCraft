package org.tgr.witchercraft.gear;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.tgr.witchercraft.Witchercraft;

import java.util.Map;

/**
 * Applies set bonus effects to players wearing Witcher armor sets.
 */
public class SetBonusHandler {

    // Attribute modifier ResourceLocations for set bonuses (1.21.1 API)
    private static final ResourceLocation WOLF_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath(Witchercraft.MOD_ID, "wolf_damage_bonus");
    private static final ResourceLocation WOLF_HEALTH_ID = ResourceLocation.fromNamespaceAndPath(Witchercraft.MOD_ID, "wolf_health_bonus");
    private static final ResourceLocation CAT_SPEED_ID = ResourceLocation.fromNamespaceAndPath(Witchercraft.MOD_ID, "cat_speed_bonus");
    private static final ResourceLocation BEAR_HEALTH_ID = ResourceLocation.fromNamespaceAndPath(Witchercraft.MOD_ID, "bear_health_bonus");
    private static final ResourceLocation BEAR_ARMOR_ID = ResourceLocation.fromNamespaceAndPath(Witchercraft.MOD_ID, "bear_armor_bonus");
    private static final ResourceLocation VIPER_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath(Witchercraft.MOD_ID, "viper_damage_bonus");

    private static int tickCounter = 0;
    private static final int UPDATE_INTERVAL = 20; // Update every second

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tickCounter++;
            if (tickCounter >= UPDATE_INTERVAL) {
                tickCounter = 0;
                for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                    updatePlayerSetBonuses(player);
                }
            }
        });
    }

    private static void updatePlayerSetBonuses(ServerPlayer player) {
        Map<WitcherSetBonuses.WitcherSchool, Integer> activeBonus = WitcherSetBonuses.getActiveSetBonuses(player);

        // Apply Wolf School bonuses
        applyWolfBonuses(player, activeBonus.getOrDefault(WitcherSetBonuses.WitcherSchool.WOLF, 0));

        // Apply Cat School bonuses
        applyCatBonuses(player, activeBonus.getOrDefault(WitcherSetBonuses.WitcherSchool.CAT, 0));

        // Apply Bear School bonuses
        applyBearBonuses(player, activeBonus.getOrDefault(WitcherSetBonuses.WitcherSchool.BEAR, 0));

        // Apply Griffin School bonuses
        applyGriffinBonuses(player, activeBonus.getOrDefault(WitcherSetBonuses.WitcherSchool.GRIFFIN, 0));

        // Apply Viper School bonuses
        applyViperBonuses(player, activeBonus.getOrDefault(WitcherSetBonuses.WitcherSchool.VIPER, 0));
    }

    private static void applyWolfBonuses(ServerPlayer player, int pieces) {
        AttributeInstance damage = player.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeInstance health = player.getAttribute(Attributes.MAX_HEALTH);

        if (damage != null) {
            damage.removeModifier(WOLF_DAMAGE_ID);
            if (pieces >= 2) {
                // +10% attack damage
                damage.addTransientModifier(new AttributeModifier(
                        WOLF_DAMAGE_ID, 0.10, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                ));
            }
        }

        if (health != null) {
            health.removeModifier(WOLF_HEALTH_ID);
            if (pieces >= 4) {
                // +15% health
                health.addTransientModifier(new AttributeModifier(
                        WOLF_HEALTH_ID, 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                ));
            }
        }
    }

    private static void applyCatBonuses(ServerPlayer player, int pieces) {
        AttributeInstance speed = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (speed != null) {
            speed.removeModifier(CAT_SPEED_ID);
            if (pieces >= 4) {
                // +10% movement speed
                speed.addTransientModifier(new AttributeModifier(
                        CAT_SPEED_ID, 0.10, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                ));
            }
        }

        // Critical hit bonus is handled in WitcherPlayerData
    }

    private static void applyBearBonuses(ServerPlayer player, int pieces) {
        AttributeInstance health = player.getAttribute(Attributes.MAX_HEALTH);
        AttributeInstance armor = player.getAttribute(Attributes.ARMOR);

        if (health != null) {
            health.removeModifier(BEAR_HEALTH_ID);
            if (pieces >= 2) {
                // +25% health
                health.addTransientModifier(new AttributeModifier(
                        BEAR_HEALTH_ID, 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                ));
            }
        }

        if (armor != null) {
            armor.removeModifier(BEAR_ARMOR_ID);
            if (pieces >= 4) {
                // +4 armor
                armor.addTransientModifier(new AttributeModifier(
                        BEAR_ARMOR_ID, 4.0, AttributeModifier.Operation.ADD_VALUE
                ));
            }
        }
    }

    private static void applyGriffinBonuses(ServerPlayer player, int pieces) {
        // Griffin bonuses affect signs - handled in sign casting code
        // For now, give a small health regen effect as visible bonus
        if (pieces >= 4) {
            // Regeneration effect while wearing 4+ pieces
            if (!player.hasEffect(MobEffects.REGENERATION)) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 0, true, false));
            }
        }
    }

    private static void applyViperBonuses(ServerPlayer player, int pieces) {
        AttributeInstance damage = player.getAttribute(Attributes.ATTACK_DAMAGE);

        if (damage != null) {
            damage.removeModifier(VIPER_DAMAGE_ID);
            if (pieces >= 2) {
                // Viper 2-piece is poison damage, but we add a small flat damage bonus
                damage.addTransientModifier(new AttributeModifier(
                        VIPER_DAMAGE_ID, 1.0, AttributeModifier.Operation.ADD_VALUE
                ));
            }
        }
    }

    /**
     * Get the sign intensity bonus from Griffin set
     */
    public static float getSignIntensityBonus(ServerPlayer player) {
        int pieces = WitcherSetBonuses.countSetPieces(player, WitcherSetBonuses.WitcherSchool.GRIFFIN);

        if (pieces >= 6) {
            return 0.50f; // +50% sign intensity
        } else if (pieces >= 2) {
            return 0.20f; // +20% sign intensity
        }
        return 0f;
    }

    /**
     * Get the critical hit chance bonus from Cat set
     */
    public static float getCritChanceBonus(ServerPlayer player) {
        int pieces = WitcherSetBonuses.countSetPieces(player, WitcherSetBonuses.WitcherSchool.CAT);

        if (pieces >= 2) {
            return 0.20f; // +20% crit chance
        }
        return 0f;
    }

    /**
     * Get the critical hit damage bonus from Cat set
     */
    public static float getCritDamageBonus(ServerPlayer player) {
        int pieces = WitcherSetBonuses.countSetPieces(player, WitcherSetBonuses.WitcherSchool.CAT);

        if (pieces >= 4) {
            return 0.30f; // +30% crit damage
        }
        return 0f;
    }
}