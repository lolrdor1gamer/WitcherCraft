package org.tgr.witchercraft.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.tgr.witchercraft.item.component.ItemCategory;
import org.tgr.witchercraft.item.component.ItemLore;
import org.tgr.witchercraft.item.component.LoreItem;

import java.util.function.Consumer;

/**
 * Witcher armor piece with set bonuses and durability
 * Simplified to work with existing LoreItem system
 */
public class WitcherArmorItem extends LoreItem {
    
    private final ArmorSet set;
    private final EquipmentSlot slot;
    private final int defense;
    
    public WitcherArmorItem(Properties properties, ItemLore lore, EquipmentSlot slot,
                            int defense, int durability, ArmorSet set) {
        super(applyArmorComponents(properties, slot, defense, durability), ItemCategory.ARMOR, lore);
        this.set = set;
        this.slot = slot;
        this.defense = defense;
    }
    
    private static Properties applyArmorComponents(Properties properties, EquipmentSlot slot, int defense, int durability) {
        // Convert to EquipmentSlotGroup
        EquipmentSlotGroup slotGroup = EquipmentSlotGroup.bySlot(slot);
        
        // Build attribute modifiers for armor
        ItemAttributeModifiers.Builder modifiers = ItemAttributeModifiers.builder();
        
        // Add armor defense
        if (defense > 0) {
            modifiers.add(
                Attributes.ARMOR,
                new AttributeModifier(
                    Item.BASE_ATTACK_DAMAGE_ID,
                    defense,
                    AttributeModifier.Operation.ADD_VALUE
                ),
                slotGroup
            );
        }
        
        return properties
            .stacksTo(1)
            .component(DataComponents.MAX_DAMAGE, durability)
            .component(DataComponents.EQUIPPABLE, Equippable.builder(slot).build())
            .attributes(modifiers.build());
    }
    
    public ArmorSet getSet() {
        return set;
    }
    
    public EquipmentSlot getSlot() {
        return slot;
    }
    
    public int getDefense() {
        return defense;
    }
    
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);
        
        if (set != null) {
            tooltip.accept(Component.literal(""));
            tooltip.accept(Component.literal(set.getName() + " Gear")
                .withStyle(ChatFormatting.GOLD));
            tooltip.accept(Component.literal(set.getWeight().getDisplayName() + " Armor")
                .withStyle(ChatFormatting.GRAY));
            
            // Show set bonuses
            tooltip.accept(Component.literal(""));
            tooltip.accept(Component.literal("Set Bonuses:").withStyle(ChatFormatting.AQUA));
            
            if (set == ArmorSet.CAT_SCHOOL) {
                addSetBonusTooltip(tooltip, 2, "+10% Attack Speed");
                addSetBonusTooltip(tooltip, 4, "+15% Critical Hit Damage");
                addSetBonusTooltip(tooltip, 6, "+25% Critical Hit Chance");
            } else if (set == ArmorSet.BEAR_SCHOOL) {
                addSetBonusTooltip(tooltip, 2, "+20% Maximum Health");
                addSetBonusTooltip(tooltip, 4, "+25% Damage Resistance");
                addSetBonusTooltip(tooltip, 6, "20% Chance to Stun Attackers");
            } else if (set == ArmorSet.GRIFFIN_SCHOOL) {
                addSetBonusTooltip(tooltip, 2, "+25% Sign Intensity");
                addSetBonusTooltip(tooltip, 4, "+50% Mana Regeneration");
                addSetBonusTooltip(tooltip, 6, "Signs cost 25% less Stamina");
            } else if (set == ArmorSet.WOLF_SCHOOL) {
                addSetBonusTooltip(tooltip, 2, "+10% Attack Damage");
                addSetBonusTooltip(tooltip, 4, "+10% Sign Intensity");
                addSetBonusTooltip(tooltip, 6, "Balanced Fighter: All stats +5%");
            } else if (set == ArmorSet.VIPER_SCHOOL) {
                addSetBonusTooltip(tooltip, 2, "Attacks have 15% chance to poison");
                addSetBonusTooltip(tooltip, 4, "+50% Poison Duration");
                addSetBonusTooltip(tooltip, 6, "Immune to Poison");
            } else if (set == ArmorSet.MANTICORE_SCHOOL) {
                addSetBonusTooltip(tooltip, 2, "+25% Alchemy Duration");
                addSetBonusTooltip(tooltip, 4, "+40% Toxicity Tolerance");
                addSetBonusTooltip(tooltip, 6, "Critical hits restore 5% toxicity as health");
            }
        }
    }
    
    private void addSetBonusTooltip(Consumer<Component> tooltip, int pieces, String description) {
        tooltip.accept(Component.literal("  (" + pieces + ") " + description)
            .withStyle(ChatFormatting.GRAY));
    }
}
