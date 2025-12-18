package org.tgr.witchercraft.gear;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.tgr.witchercraft.registry.ModItems;

import java.util.*;

/**
 * Defines set bonuses for Witcher armor sets.
 * Each set has 2-piece, 4-piece, and 6-piece bonuses.
 */
public class WitcherSetBonuses {
    
    private static final Map<WitcherSchool, SetBonus> SET_BONUSES = new EnumMap<>(WitcherSchool.class);
    
    public enum WitcherSchool {
        WOLF("Wolf School", 0xAAAAAA),
        CAT("Cat School", 0x44FF44),
        BEAR("Bear School", 0x8B4513),
        GRIFFIN("Griffin School", 0x4488FF),
        VIPER("Viper School", 0x00FF88);
        
        public final String displayName;
        public final int color;
        
        WitcherSchool(String name, int color) {
            this.displayName = name;
            this.color = color;
        }
    }
    
    public record SetBonus(
        WitcherSchool school,
        String twoBonus,
        String fourBonus,
        String sixBonus
    ) {}
    
    static {
        // Wolf School - Balanced combat
        SET_BONUSES.put(WitcherSchool.WOLF, new SetBonus(
            WitcherSchool.WOLF,
            "+10% Attack Damage",
            "+15% Health, Signs cost -20%",
            "Strong attacks apply bleeding, Fast attacks have +25% crit chance"
        ));
        
        // Cat School - Agility and critical hits
        SET_BONUSES.put(WitcherSchool.CAT, new SetBonus(
            WitcherSchool.CAT,
            "+20% Critical Hit Chance",
            "+30% Critical Hit Damage, +10% Movement Speed",
            "Backstabs deal double damage, Dodge grants 3s of +50% crit chance"
        ));
        
        // Bear School - Heavy armor and sustain
        SET_BONUSES.put(WitcherSchool.BEAR, new SetBonus(
            WitcherSchool.BEAR,
            "+25% Health",
            "Strong attacks deal +30% damage, +4 Armor",
            "Taking damage grants Adrenaline, Quen doesn't break from heavy attacks"
        ));
        
        // Griffin School - Sign intensity
        SET_BONUSES.put(WitcherSchool.GRIFFIN, new SetBonus(
            WitcherSchool.GRIFFIN,
            "+20% Sign Intensity",
            "Signs cost -30%, +5% Health Regen during signs",
            "Casting a sign buffs the next sign by 50%, Yrden affects flying enemies"
        ));
        
        // Viper School - Poison and alchemy
        SET_BONUSES.put(WitcherSchool.VIPER, new SetBonus(
            WitcherSchool.VIPER,
            "+25% Poison Damage",
            "+3 Maximum Toxicity, Oils last 50% longer",
            "Attacks have 20% to apply poison, Bombs have +50% effect radius"
        ));
    }
    
    /**
     * Get the set bonus definition for a school
     */
    public static SetBonus getSetBonus(WitcherSchool school) {
        return SET_BONUSES.get(school);
    }
    
    /**
     * Count how many pieces of a specific armor set the player is wearing
     */
    public static int countSetPieces(Player player, WitcherSchool school) {
        int count = 0;
        
        // Check all armor slots
        for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack stack = player.getItemBySlot(slot);
            if (!stack.isEmpty()) {
                WitcherSchool itemSchool = getItemSchool(stack.getItem());
                if (itemSchool == school) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Get which school an armor item belongs to
     */
    public static WitcherSchool getItemSchool(Item item) {
        // Check all armor items and map them to their schools
        // Using the item registry names to determine school
        String itemId = item.toString().toLowerCase();
        
        if (itemId.contains("feline") || itemId.contains("cat")) {
            return WitcherSchool.CAT;
        } else if (itemId.contains("ursine") || itemId.contains("bear")) {
            return WitcherSchool.BEAR;
        } else if (itemId.contains("griffin")) {
            return WitcherSchool.GRIFFIN;
        } else if (itemId.contains("viper")) {
            return WitcherSchool.VIPER;
        } else if (itemId.contains("witcher") || itemId.contains("wolf")) {
            return WitcherSchool.WOLF;
        }
        
        return null;
    }
    
    /**
     * Get the current active set bonuses for a player
     */
    public static Map<WitcherSchool, Integer> getActiveSetBonuses(Player player) {
        Map<WitcherSchool, Integer> bonuses = new EnumMap<>(WitcherSchool.class);
        
        for (WitcherSchool school : WitcherSchool.values()) {
            int count = countSetPieces(player, school);
            if (count >= 2) {
                bonuses.put(school, count);
            }
        }
        
        return bonuses;
    }
    
    /**
     * Get a formatted description of active set bonuses
     */
    public static List<String> getActiveBonusDescriptions(Player player) {
        List<String> descriptions = new ArrayList<>();
        Map<WitcherSchool, Integer> active = getActiveSetBonuses(player);
        
        for (Map.Entry<WitcherSchool, Integer> entry : active.entrySet()) {
            WitcherSchool school = entry.getKey();
            int pieces = entry.getValue();
            SetBonus bonus = SET_BONUSES.get(school);
            
            if (bonus != null) {
                descriptions.add("§6" + school.displayName + " (" + pieces + "/6):");
                
                if (pieces >= 2) {
                    descriptions.add("  §a(2) " + bonus.twoBonus());
                }
                if (pieces >= 4) {
                    descriptions.add("  §a(4) " + bonus.fourBonus());
                }
                if (pieces >= 6) {
                    descriptions.add("  §a(6) " + bonus.sixBonus());
                }
            }
        }
        
        return descriptions;
    }
}
