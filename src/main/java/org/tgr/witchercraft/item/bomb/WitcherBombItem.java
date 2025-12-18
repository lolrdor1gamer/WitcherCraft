package org.tgr.witchercraft.item.bomb;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.tgr.witchercraft.registry.ModEntities;

import java.util.function.Consumer;

public class WitcherBombItem extends Item {
    private final BombType bombType;

    public WitcherBombItem(Properties properties, BombType bombType) {
        super(properties.stacksTo(16));
        this.bombType = bombType;
    }

    public BombType getBombType() {
        return bombType;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5f, 0.4f);

        if (!level.isClientSide()) {
            WitcherBombProjectile projectile = new WitcherBombProjectile(
                    ModEntities.WITCHER_BOMB_PROJECTILE, player, level, bombType);
            projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 1.5f, 1.0f);
            level.addFreshEntity(projectile);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        stack.consume(1, player);
        player.getCooldowns().addCooldown(stack, 20);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display,
                                Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.translatable("item.witchercraft." + bombType.getName() + "_bomb.tooltip")
                .withStyle(ChatFormatting.GRAY));
        if (bombType.getDamage() > 0) {
            tooltip.accept(Component.literal("Damage: " + bombType.getDamage())
                    .withStyle(ChatFormatting.RED));
        }
    }
}