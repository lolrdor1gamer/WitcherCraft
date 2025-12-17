package org.tgr.witchercraft.item.component;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ForgedArmorItem extends LoreItem {

    public ForgedArmorItem(Properties properties, ItemLore lore, int durability) {
        super(applyArmorComponents(properties, durability), ItemCategory.ARMOR, lore);
    }

    private static Properties applyArmorComponents(Properties properties, int durability) {
        // Determine armor slot and defense from durability
        // This is a simplification - ideally slot would be a parameter
        EquipmentSlot slot = EquipmentSlot.CHEST; // Default to chest
        int defense = Math.max(1, durability / 150); // Scale defense with durability
        
        EquipmentSlotGroup slotGroup = EquipmentSlotGroup.bySlot(slot);
        ItemAttributeModifiers.Builder modifiers = ItemAttributeModifiers.builder();
        
        // Add armor defense
        modifiers.add(
            Attributes.ARMOR,
            new AttributeModifier(
                Item.BASE_ATTACK_DAMAGE_ID,
                defense,
                AttributeModifier.Operation.ADD_VALUE
            ),
            slotGroup
        );
        
        return properties
            .stacksTo(1)
            .component(DataComponents.MAX_DAMAGE, durability)
            .component(DataComponents.EQUIPPABLE, Equippable.builder(slot).build())
            .attributes(modifiers.build());
    }
}
