package org.tgr.witchercraft.item.component;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class ForgedWeaponItem extends LoreItem {

    public ForgedWeaponItem(Properties properties, ItemLore lore, float attackDamage, float attackSpeed, int durability) {
        super(applyWeaponComponents(properties, attackDamage, attackSpeed, durability), ItemCategory.WEAPON, lore);
    }

    private static Properties applyWeaponComponents(Properties properties, float attackDamage, float attackSpeed, int durability) {
        ItemAttributeModifiers modifiers = ItemAttributeModifiers.EMPTY
            .withModifierAdded(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, attackDamage - 1.0F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .withModifierAdded(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);

        return properties
            .stacksTo(1)
            .component(DataComponents.MAX_DAMAGE, durability)
            .component(DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
    }
}
