package org.tgr.witchercraft.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.TooltipDisplay;
import org.tgr.witchercraft.registry.tag.WitcherEntityTypeTags;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Base class for Witcher swords. Provides custom attribute tuning and conditional bonus damage.
 */
public class WitcherSwordItem extends WitcherItem {

    private final SwordType swordType;
    private final float bonusDamage;
    private final int ineffectiveDurabilityCost;

    public WitcherSwordItem(Properties properties, SwordType swordType, float attackDamage, float attackSpeed, int durability) {
        super(applyAttributes(properties, attackDamage, attackSpeed, durability));
        this.swordType = swordType;
        this.bonusDamage = 2.0F;
        this.ineffectiveDurabilityCost = 2;
    }

    public SwordType swordType() {
        return swordType;
    }

    public boolean isEffectiveTarget(LivingEntity entity) {
        return swordType.isEffective(entity);
    }

    public float bonusDamage() {
        return bonusDamage;
    }

    public int ineffectiveDurabilityCost() {
        return ineffectiveDurabilityCost;
    }

    private static Properties applyAttributes(Properties properties, float attackDamage, float attackSpeed, int durability) {
        ItemAttributeModifiers modifiers = ItemAttributeModifiers.EMPTY
            .withModifierAdded(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, attackDamage - 1.0F, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .withModifierAdded(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);

        return properties
            .stacksTo(1)
            .component(DataComponents.MAX_DAMAGE, durability)
            .component(DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.translatable("tooltip.witchercraft.sword.type", Component.translatable(swordType.tooltipKey()))
            .withStyle(ChatFormatting.GOLD));
        tooltip.accept(Component.translatable(swordType.tooltipKey()).withStyle(ChatFormatting.GRAY));
    }

    public enum SwordType {
        STEEL(entity -> entity.getType().is(WitcherEntityTypeTags.HUMANOIDS),
            "message.witchercraft.sword.steel_ineffective",
            "tooltip.witchercraft.sword.steel"),
        SILVER(entity -> entity.getType().is(WitcherEntityTypeTags.MONSTERS),
            "message.witchercraft.sword.silver_ineffective",
            "tooltip.witchercraft.sword.silver");

        private final Predicate<LivingEntity> predicate;
        private final String feedbackKey;
        private final String tooltipKey;

        SwordType(Predicate<LivingEntity> predicate, String feedbackKey, String tooltipKey) {
            this.predicate = predicate;
            this.feedbackKey = feedbackKey;
            this.tooltipKey = tooltipKey;
        }

        public boolean isEffective(LivingEntity entity) {
            return predicate.test(entity);
        }

        public net.minecraft.network.chat.Component feedbackComponent() {
            return net.minecraft.network.chat.Component.translatable(feedbackKey);
        }

        public String feedbackKey() {
            return feedbackKey;
        }

        public String tooltipKey() {
            return tooltipKey;
        }
    }
}
