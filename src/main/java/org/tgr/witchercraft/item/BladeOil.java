package org.tgr.witchercraft.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * Represents a blade oil applied to a weapon.
 * Provides bonus damage against specific enemy types.
 */
public class BladeOil {
    
    public static final String NBT_KEY = "BladeOil";
    public static final String OIL_TYPE_KEY = "OilType";
    public static final String CHARGES_KEY = "Charges";
    
    private final OilType type;
    private int charges;
    
    public BladeOil(OilType type, int charges) {
        this.type = type;
        this.charges = charges;
    }
    
    public OilType getType() {
        return type;
    }
    
    public int getCharges() {
        return charges;
    }
    
    public void consumeCharge() {
        if (charges > 0) {
            charges--;
        }
    }
    
    public boolean isActive() {
        return charges > 0;
    }
    
    /**
     * Check if this oil is effective against an entity
     */
    public boolean isEffectiveAgainst(LivingEntity entity) {
        return type.predicate.test(entity);
    }
    
    /**
     * Get bonus damage multiplier
     */
    public float getDamageBonus() {
        return isActive() ? type.damageBonus : 0.0f;
    }
    
    /**
     * Apply oil to a weapon
     * TODO: Migrate to Data Components API in MC 1.21.1
     */
    public static void applyOil(ItemStack weapon, OilType type) {
        // TODO: Use Data Components instead of NBT
        // weapon.set(ModDataComponents.OIL_TYPE, type);
        // weapon.set(ModDataComponents.OIL_CHARGES, 20);
    }
    
    /**
     * Get oil from weapon
     * TODO: Migrate to Data Components API in MC 1.21.1
     */
    public static BladeOil getOil(ItemStack weapon) {
        // TODO: Use Data Components instead of NBT
        // OilType type = weapon.get(ModDataComponents.OIL_TYPE);
        // Integer charges = weapon.get(ModDataComponents.OIL_CHARGES);
        // if (type != null && charges != null) {
        //     return new BladeOil(type, charges);
        // }
        return null;
    }
    
    /**
     * Update oil charges on weapon
     * TODO: Migrate to Data Components API in MC 1.21.1
     */
    public static void updateCharges(ItemStack weapon, int newCharges) {
        // TODO: Use Data Components instead of NBT
        // if (weapon.has(ModDataComponents.OIL_CHARGES)) {
        //     weapon.set(ModDataComponents.OIL_CHARGES, Math.max(0, newCharges));
        //     if (newCharges <= 0) {
        //         weapon.remove(ModDataComponents.OIL_TYPE);
        //         weapon.remove(ModDataComponents.OIL_CHARGES);
        //     }
        // }
    }
    
    /**
     * Add oil tooltip to weapon
     */
    public static void addTooltip(ItemStack weapon, java.util.function.Consumer<Component> tooltip) {
        BladeOil oil = getOil(weapon);
        if (oil != null && oil.isActive()) {
            tooltip.accept(Component.literal("Oil: " + oil.getType().displayName)
                .withStyle(ChatFormatting.AQUA));
            tooltip.accept(Component.literal("Charges: " + oil.getCharges() + "/20")
                .withStyle(ChatFormatting.GRAY));
            tooltip.accept(Component.literal("+" + (int)(oil.getDamageBonus() * 100) + "% damage vs " + oil.getType().targetType)
                .withStyle(ChatFormatting.GREEN));
        }
    }
    
    public enum OilType {
        BEAST("Beast Oil", "Beasts", 0.25f, 
            entity -> entity.getType().is(net.minecraft.tags.EntityTypeTags.SENSITIVE_TO_SMITE)),
        SPECTER("Specter Oil", "Specters", 0.30f,
            entity -> entity.getType().is(net.minecraft.tags.EntityTypeTags.UNDEAD)),
        CURSED("Cursed Oil", "Cursed Ones", 0.30f,
            entity -> entity.getType().is(org.tgr.witchercraft.registry.tag.WitcherEntityTypeTags.MONSTERS)),
        DRACONID("Draconid Oil", "Draconids", 0.35f,
            entity -> entity.getType() == net.minecraft.world.entity.EntityType.ENDER_DRAGON),
        INSECTOID("Insectoid Oil", "Insectoids", 0.25f,
            entity -> entity.getType() == net.minecraft.world.entity.EntityType.SPIDER || 
                     entity.getType() == net.minecraft.world.entity.EntityType.CAVE_SPIDER ||
                     entity.getType() == net.minecraft.world.entity.EntityType.SILVERFISH),
        NECROPHAGE("Necrophage Oil", "Necrophages", 0.25f,
            entity -> entity.getType().is(net.minecraft.tags.EntityTypeTags.UNDEAD)),
        HYBRID("Hybrid Oil", "Hybrids", 0.30f,
            entity -> entity.getType().is(org.tgr.witchercraft.registry.tag.WitcherEntityTypeTags.MONSTERS)),
        ELEMENTA("Elementa Oil", "Elementals", 0.25f,
            entity -> entity.getType() == net.minecraft.world.entity.EntityType.BLAZE ||
                     entity.getType() == net.minecraft.world.entity.EntityType.MAGMA_CUBE),
        RELICT("Relict Oil", "Relicts", 0.35f,
            entity -> entity.getType().is(org.tgr.witchercraft.registry.tag.WitcherEntityTypeTags.MONSTERS)),
        OGROID("Ogroid Oil", "Ogroids", 0.25f,
            entity -> entity.getType() == net.minecraft.world.entity.EntityType.HOGLIN ||
                     entity.getType() == net.minecraft.world.entity.EntityType.RAVAGER);
        
        private final String displayName;
        private final String targetType;
        private final float damageBonus;
        private final java.util.function.Predicate<LivingEntity> predicate;
        
        OilType(String displayName, String targetType, float damageBonus, 
                java.util.function.Predicate<LivingEntity> predicate) {
            this.displayName = displayName;
            this.targetType = targetType;
            this.damageBonus = damageBonus;
            this.predicate = predicate;
        }
    }
}
