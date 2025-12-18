package org.tgr.witchercraft.gear;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Glyph types that can be socketed into Witcher armor.
 * Each glyph provides a specific defensive or utility bonus.
 */
public enum Glyph {
    // Defensive glyphs
    QUEN_GLYPH("Quen", "Increases Quen shield strength by 20%", ChatFormatting.YELLOW, 
        GlyphType.QUEN_STRENGTH, 0.20f),
    AARD_GLYPH("Aard", "Increases Aard knockback by 15%", ChatFormatting.WHITE, 
        GlyphType.AARD_POWER, 0.15f),
    IGNI_GLYPH("Igni", "Increases Igni damage by 20%", ChatFormatting.RED, 
        GlyphType.IGNI_DAMAGE, 0.20f),
    YRDEN_GLYPH("Yrden", "Increases Yrden duration by 25%", ChatFormatting.DARK_PURPLE, 
        GlyphType.YRDEN_DURATION, 0.25f),
    AXII_GLYPH("Axii", "Increases Axii effectiveness by 15%", ChatFormatting.LIGHT_PURPLE, 
        GlyphType.AXII_POWER, 0.15f),
    
    // Protection glyphs
    BINDING("Binding", "Reduces incoming damage by 3%", ChatFormatting.GRAY, 
        GlyphType.DAMAGE_REDUCTION, 0.03f),
    REINFORCEMENT("Reinforcement", "Increases armor by 2", ChatFormatting.GRAY, 
        GlyphType.ARMOR, 2.0f),
    WARDING("Warding", "Increases magic resistance by 10%", ChatFormatting.AQUA, 
        GlyphType.MAGIC_RESIST, 0.10f),
    
    // Utility glyphs
    VITALITY("Vitality", "Increases health by 10%", ChatFormatting.GREEN, 
        GlyphType.HEALTH, 0.10f),
    REGENERATION("Regeneration", "Increases health regeneration", ChatFormatting.GREEN, 
        GlyphType.HEALTH_REGEN, 0.5f),
    MENDING("Mending", "Reduces toxicity by 5%", ChatFormatting.DARK_GREEN, 
        GlyphType.TOXICITY_REDUCTION, 0.05f);
    
    public enum GlyphType {
        QUEN_STRENGTH,
        AARD_POWER,
        IGNI_DAMAGE,
        YRDEN_DURATION,
        AXII_POWER,
        DAMAGE_REDUCTION,
        ARMOR,
        MAGIC_RESIST,
        HEALTH,
        HEALTH_REGEN,
        TOXICITY_REDUCTION
    }
    
    private final String displayName;
    private final String description;
    private final ChatFormatting color;
    private final GlyphType type;
    private final float value;
    
    Glyph(String name, String desc, ChatFormatting color, GlyphType type, float value) {
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
    
    public GlyphType getType() {
        return type;
    }
    
    public float getValue() {
        return value;
    }
    
    public Component getFormattedName() {
        return Component.literal(displayName + " Glyph").withStyle(color);
    }
    
    /**
     * Get all glyphs socketed in an item
     */
    public static List<Glyph> getSocketedGlyphs(ItemStack stack) {
        List<Glyph> glyphs = new ArrayList<>();
        
        if (stack.isEmpty()) return glyphs;
        
        // Check NBT for socketed glyphs
        if (stack.has(net.minecraft.core.component.DataComponents.CUSTOM_DATA)) {
            var customData = stack.get(net.minecraft.core.component.DataComponents.CUSTOM_DATA);
            if (customData != null) {
                var tag = customData.copyTag();
                if (tag.contains("SocketedGlyphs")) {
                    tag.getList("SocketedGlyphs").ifPresent(glyphList -> {
                        for (int i = 0; i < glyphList.size(); i++) {
                            if (glyphList.get(i) instanceof net.minecraft.nbt.StringTag stringTag) {
                                try {
                                    Glyph glyph = Glyph.valueOf(stringTag.value().toUpperCase());
                                    glyphs.add(glyph);
                                } catch (IllegalArgumentException ignored) {}
                            }
                        }
                    });
                }
            }
        }
        
        return glyphs;
    }
    
    /**
     * Calculate total sign bonus from all equipped armor glyphs
     */
    public static float getSignBonus(net.minecraft.world.entity.player.Player player, GlyphType signType) {
        float bonus = 0;
        
        for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack armor = player.getItemBySlot(slot);
            for (Glyph glyph : getSocketedGlyphs(armor)) {
                if (glyph.type == signType) {
                    bonus += glyph.value;
                }
            }
        }
        
        return bonus;
    }
    
    /**
     * Calculate total damage reduction from all equipped armor glyphs
     */
    public static float getDamageReduction(net.minecraft.world.entity.player.Player player) {
        float reduction = 0;
        
        for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack armor = player.getItemBySlot(slot);
            for (Glyph glyph : getSocketedGlyphs(armor)) {
                if (glyph.type == GlyphType.DAMAGE_REDUCTION) {
                    reduction += glyph.value;
                }
            }
        }
        
        return reduction;
    }
}
