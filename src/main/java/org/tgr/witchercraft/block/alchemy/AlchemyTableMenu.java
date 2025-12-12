package org.tgr.witchercraft.block.alchemy;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.tgr.witchercraft.registry.ModBlocks;
import org.tgr.witchercraft.registry.ModMenus;

import java.util.Objects;

/**
 * Menu for arranging potion ingredients and retrieving brewed outputs.
 */
public class AlchemyTableMenu extends AbstractContainerMenu {

    private static final int PLAYER_INVENTORY_SLOTS = 27;
    private static final int HOTBAR_SLOTS = 9;

    private final Container container;
    private final ContainerLevelAccess access;
    private final AlchemyTableBlockEntity blockEntity;

    public AlchemyTableMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(AlchemyTableBlockEntity.SLOT_COUNT), ContainerLevelAccess.NULL, null);
    }

    public AlchemyTableMenu(int syncId, Inventory playerInventory, AlchemyTableBlockEntity entity) {
        this(syncId, playerInventory, entity.getInventory(), ContainerLevelAccess.create(Objects.requireNonNull(entity.getLevel()), entity.getBlockPos()), entity);
    }

    private AlchemyTableMenu(int syncId, Inventory playerInventory, Container container, ContainerLevelAccess access, AlchemyTableBlockEntity entity) {
        super(ModMenus.ALCHEMY_TABLE_MENU, syncId);
        this.container = container;
        this.access = access;
        this.blockEntity = entity;

        checkContainerSize(container, AlchemyTableBlockEntity.SLOT_COUNT);
        container.startOpen(playerInventory.player);

        addIngredientSlots();
        addPlayerInventory(playerInventory);
    }

    private void addIngredientSlots() {
        this.addSlot(new Slot(container, AlchemyTableBlockEntity.SLOT_BASE, AlchemyTableLayout.BASE_SLOT.x(), AlchemyTableLayout.BASE_SLOT.y()));
        this.addSlot(new Slot(container, AlchemyTableBlockEntity.SLOT_REAGENT, AlchemyTableLayout.REAGENT_SLOT.x(), AlchemyTableLayout.REAGENT_SLOT.y()));
        this.addSlot(new Slot(container, AlchemyTableBlockEntity.SLOT_CATALYST, AlchemyTableLayout.CATALYST_SLOT.x(), AlchemyTableLayout.CATALYST_SLOT.y()));
        this.addSlot(new Slot(container, AlchemyTableBlockEntity.SLOT_OUTPUT, AlchemyTableLayout.OUTPUT_SLOT.x(), AlchemyTableLayout.OUTPUT_SLOT.y()) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                super.onTake(player, stack);
                if (blockEntity != null) {
                    blockEntity.onResultTaken(player);
                }
            }
        });
    }

    private void addPlayerInventory(Inventory playerInventory) {
        int xStart = AlchemyTableLayout.PLAYER_INV_X;
        int yStart = AlchemyTableLayout.PLAYER_INV_Y;

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(playerInventory, column + row * 9 + 9, xStart + column * 18, yStart + row * 18));
            }
        }

        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInventory, column, xStart + column * 18, yStart + AlchemyTableLayout.HOTBAR_OFFSET_Y));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack original = slot.getItem();
            newStack = original.copy();

            if (index < AlchemyTableBlockEntity.SLOT_COUNT) {
                if (!moveItemStackTo(original, AlchemyTableBlockEntity.SLOT_COUNT, AlchemyTableBlockEntity.SLOT_COUNT + PLAYER_INVENTORY_SLOTS + HOTBAR_SLOTS, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(original, 0, AlchemyTableBlockEntity.SLOT_COUNT - 1, false)) {
                return ItemStack.EMPTY;
            }

            if (original.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return newStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, ModBlocks.ALCHEMY_TABLE);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        container.stopOpen(player);
    }
}
