package org.tgr.witchercraft.gear;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Runestone types that can be socketed into Witcher weapons.
 * Each runestone provides a specific combat bonus.
 */
public enum Runestone {
    // Damage runestones
    CHERNOBOG("Chernobog", "Increases attack power by 5%", ChatFormatting.RED, 
        RunestoneType.DAMAGE, 0.05f),
    DEVANA("Devana", "Increases attack power by 10%", ChatFormatting.RED, 
        RunestoneType.DAMAGE, 0.10f),
    ZORIA("Zoria", "Increases attack power by 15%", ChatFormatting.RED, 
        RunestoneType.DAMAGE, 0.15f),
    
    // Critical runestones
    DAZHBOG("Dazhbog", "Increases critical hit chance by 5%", ChatFormatting.GOLD, 
        RunestoneType.CRITICAL, 0.05f),
    PERUN("Perun", "Increases critical hit chance by 10%", ChatFormatting.GOLD, 
        RunestoneType.CRITICAL, 0.10f),
    SVAROG("Svarog", "Increases critical hit damage by 15%", ChatFormatting.GOLD, 
        RunestoneType.CRITICAL_DAMAGE, 0.15f),
    
    // Elemental runestones
    IGNI_RUNE("Igni", "Adds fire damage to attacks", ChatFormatting.DARK_RED, 
        RunestoneType.FIRE, 3.0f),
    MORANA("Morana", "Adds frost damage and slows enemies", ChatFormatting.AQUA, 
        RunestoneType.FROST, 2.0f),
    STRIBOG("Stribog", "Adds shock damage with chance to stun", ChatFormatting.YELLOW, 
        RunestoneType.SHOCK, 2.5f),
    
    // Utility runestones
    VELES("Veles", "Chance to poison enemies", ChatFormatting.DARK_GREEN, 
        RunestoneType.POISON, 0.20f),
    TRIGLAV("Triglav", "Increases sign intensity by 10%", ChatFormatting.BLUE, 
        RunestoneType.SIGN_INTENSITY, 0.10f);
    
    public enum RunestoneType {
        DAMAGE,
        CRITICAL,
        CRITICAL_DAMAGE,
        FIRE,
        FROST,
        SHOCK,
        POISON,
        SIGN_INTENSITY
    }
    
    private final String displayName;
    private final String description;
    private final ChatFormatting color;
    private final RunestoneType type;
    private final float value;
    
    Runestone(String name, String desc, ChatFormatting color, RunestoneType type, float value) {
        this.displayName = name;
        this.description = desc;
        this.color = color;
        this.type = type;
        this.value = value;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public ChatFormatting getColor() {
        return color;
    }
    
    public RunestoneType getType() {
        return type;
    }
    
    public float getValue() {
        return value;
    }
    
    public Component getFormattedName() {
        return Component.literal(displayName + " Runestone").withStyle(color);
    }
    
    /**
     * Get all runestones socketed in an item
     */
    public static List<Runestone> getSocketedRunestones(ItemStack stack) {
        List<Runestone> runes = new ArrayList<>();
        
        if (stack.isEmpty()) return runes;
        
        // Check NBT for socketed runes
        if (stack.has(net.minecraft.core.component.DataComponents.CUSTOM_DATA)) {
            var customData = stack.get(net.minecraft.core.component.DataComponents.CUSTOM_DATA);
            if (customData != null) {
                var tag = customData.copyTag();
                if (tag.contains("SocketedRunes")) {
                    tag.getList("SocketedRunes").ifPresent(runeList -> {
                        for (int i = 0; i < runeList.size(); i++) {
                            if (runeList.get(i) instanceof net.minecraft.nbt.StringTag stringTag) {
                                try {
                                    Runestone rune = Runestone.valueOf(stringTag.value().toUpperCase());
                                    runes.add(rune);
                                } catch (IllegalArgumentException ignored) {}
                            }
                        }
                    });
                }
            }
        }
        
        return runes;
    }
    
    /**
     * Get total damage bonus from all socketed runestones
     */
    public static float getTotalDamageBonus(ItemStack stack) {
        float bonus = 0;
        for (Runestone rune : getSocketedRunestones(stack)) {
            if (rune.type == RunestoneType.DAMAGE) {
                bonus += rune.value;
            }
        }
        return bonus;
    }
    
    /**
     * Get total critical chance bonus from all socketed runestones
     */
    public static float getTotalCritBonus(ItemStack stack) {
        float bonus = 0;
        for (Runestone rune : getSocketedRunestones(stack)) {
            if (rune.type == RunestoneType.CRITICAL) {
                bonus += rune.value;
            }
        }
        return bonus;
    }
}
