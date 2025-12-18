package org.tgr.witchercraft.item.oil;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.tgr.witchercraft.entity.monster.AbstractWitcherMonster;

import java.util.function.Consumer;

/**
 * Blade oil item that can be applied to weapons.
 * Each oil type is effective against specific monster categories.
 */
public class BladeOilItem extends Item {
    
    private final AbstractWitcherMonster.MonsterCategory effectiveAgainst;
    private final float damageMultiplier;
    private final int charges; // Number of hits before oil wears off
    
    public BladeOilItem(Properties properties, AbstractWitcherMonster.MonsterCategory category, 
                        float damageMultiplier, int charges) {
        super(properties);
        this.effectiveAgainst = category;
        this.damageMultiplier = damageMultiplier;
        this.charges = charges;
    }
    
    public AbstractWitcherMonster.MonsterCategory getEffectiveCategory() {
        return effectiveAgainst;
    }
    
    public float getDamageMultiplier() {
        return damageMultiplier;
    }
    
    public int getCharges() {
        return charges;
    }
    
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack oilStack = player.getItemInHand(hand);
        
        // Get the weapon in the other hand
        InteractionHand otherHand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack weaponStack = player.getItemInHand(otherHand);
        
        // If other hand is empty, try main hand
        if (weaponStack.isEmpty() && hand == InteractionHand.OFF_HAND) {
            weaponStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        }
        
        // Check if it's a sword
        if (isSword(weaponStack)) {
            if (!level.isClientSide()) {
                // Apply oil to sword via NBT/components
                applyOilToWeapon(weaponStack, oilStack.getItem());
                
                // Consume oil
                if (!player.getAbilities().instabuild) {
                    oilStack.shrink(1);
                }
                
                player.displayClientMessage(Component.literal("Applied " + this.getDescriptionId() + " to weapon")
                    .withStyle(ChatFormatting.GREEN), true);
            }
            return InteractionResult.SUCCESS;
        }
        
        return InteractionResult.PASS;
    }
    
    private boolean isSword(ItemStack stack) {
        if (stack.isEmpty()) return false;
        String id = stack.getItem().getDescriptionId().toLowerCase();
        return id.contains("sword") || id.contains("blade");
    }
    
    private void applyOilToWeapon(ItemStack weapon, Item oil) {
        // Store oil type and charges in weapon's tag
        // Using simple component storage for 1.21
        // This is a simplified version - full implementation would use custom components
        weapon.set(net.minecraft.core.component.DataComponents.CUSTOM_NAME,
            Component.literal(weapon.getHoverName().getString() + " (Oiled)")
                .withStyle(ChatFormatting.GOLD));
    }
    
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, display, tooltip, tooltipFlag);
        
        tooltip.accept(Component.literal("Effective against: " + effectiveAgainst.name())
            .withStyle(ChatFormatting.YELLOW));
        tooltip.accept(Component.literal("Damage bonus: +" + ((int)((damageMultiplier - 1) * 100)) + "%")
            .withStyle(ChatFormatting.GREEN));
        tooltip.accept(Component.literal("Charges: " + charges + " hits")
            .withStyle(ChatFormatting.GRAY));
        tooltip.accept(Component.literal("Right-click with sword in other hand to apply")
            .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
    }
    
    /**
     * Create standard blade oil properties.
     */
    public static Properties createOilProperties(Rarity rarity) {
        return new Properties()
            .stacksTo(16)
            .rarity(rarity);
    }
}
