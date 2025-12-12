package org.tgr.witchercraft.block.alchemy;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Clearable;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.tgr.witchercraft.alchemy.WitcherPotionRecipes;
import org.tgr.witchercraft.registry.ModBlockEntities;

/**
 * Block entity that stores input slots and handles potion recipe resolution.
 */
public class AlchemyTableBlockEntity extends BlockEntity implements MenuProvider, Clearable {

    public static final int SLOT_BASE = 0;
    public static final int SLOT_REAGENT = 1;
    public static final int SLOT_CATALYST = 2;
    public static final int SLOT_OUTPUT = 3;
    public static final int SLOT_COUNT = 4;

    private final SimpleContainer inventory = new SimpleContainer(SLOT_COUNT) {
        @Override
        public void setChanged() {
            super.setChanged();
            if (!updatingResult) {
                AlchemyTableBlockEntity.this.onInventoryChanged();
            }
        }
    };

    private boolean updatingResult;
    @Nullable
    private WitcherPotionRecipes.PotionRecipe activeRecipe;

    public AlchemyTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ALCHEMY_TABLE_ENTITY, pos, state);
    }

    public SimpleContainer getInventory() {
        return inventory;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.witchercraft.alchemy_table");
    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new AlchemyTableMenu(syncId, playerInventory, this);
    }

    @Override
    public void clearContent() {
        inventory.clearContent();
    }

    public void onResultTaken(Player player) {
        if (level == null || level.isClientSide()) {
            return;
        }
        if (activeRecipe == null) {
            return;
        }
        consumeIngredient(SLOT_BASE);
        consumeIngredient(SLOT_REAGENT);
        consumeIngredient(SLOT_CATALYST);
        setChanged();
        updateResult(level);
    }

    private void onInventoryChanged() {
        if (level == null || level.isClientSide()) {
            return;
        }
        updateResult(level);
    }

    private void updateResult(Level level) {
        WitcherPotionRecipes.PotionRecipe match = WitcherPotionRecipes.match(
            inventory.getItem(SLOT_BASE),
            inventory.getItem(SLOT_REAGENT),
            inventory.getItem(SLOT_CATALYST)
        );
        this.activeRecipe = match;
        ItemStack stack = match == null ? ItemStack.EMPTY : match.result().copy();
        updatingResult = true;
        inventory.setItem(SLOT_OUTPUT, stack);
        updatingResult = false;
    }

    private void consumeIngredient(int slot) {
        ItemStack stack = inventory.getItem(slot);
        if (!stack.isEmpty()) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                inventory.setItem(slot, ItemStack.EMPTY);
            }
        }
    }
}
