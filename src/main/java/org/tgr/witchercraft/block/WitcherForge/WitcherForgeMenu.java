 package org.tgr.witchercraft.block.WitcherForge;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import java.util.Objects;

import org.tgr.witchercraft.registry.ModBlocks;
import org.tgr.witchercraft.registry.ModMenus;

public class WitcherForgeMenu extends AbstractContainerMenu {

    private static final int SLOT_COUNT = WitcherForgeEntity.SLOT_COUNT;
    private static final int PLAYER_INVENTORY_SLOTS = 27;
    private static final int HOTBAR_SLOTS = 9;

    private final Container container;
    private final ContainerLevelAccess access;

    public WitcherForgeMenu(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(SLOT_COUNT), ContainerLevelAccess.NULL);
    }

    public WitcherForgeMenu(int syncId, Inventory playerInventory, WitcherForgeEntity entity) {
        this(syncId, playerInventory, entity.getInventory(), ContainerLevelAccess.create(Objects.requireNonNull(entity.getLevel()), entity.getBlockPos()));
    }

    private WitcherForgeMenu(int syncId, Inventory playerInventory, Container container, ContainerLevelAccess access) {
        super(ModMenus.WITCHER_FORGE_MENU, syncId);
        this.container = container;
        this.access = access;

        checkContainerSize(container, SLOT_COUNT);
        container.startOpen(playerInventory.player);

        addForgeSlots();
        addPlayerInventory(playerInventory);
    }

    private void addForgeSlots() {
        // Pommel slot
        this.addSlot(new Slot(container, 0, WitcherForgeLayout.FORGE_SLOT_POINTS[0].x(), WitcherForgeLayout.FORGE_SLOT_POINTS[0].y()));

        // Grip slot
        this.addSlot(new Slot(container, 1, WitcherForgeLayout.FORGE_SLOT_POINTS[1].x(), WitcherForgeLayout.FORGE_SLOT_POINTS[1].y()));

        // Guard slot
        this.addSlot(new Slot(container, 2, WitcherForgeLayout.FORGE_SLOT_POINTS[2].x(), WitcherForgeLayout.FORGE_SLOT_POINTS[2].y()));

        // Blade material slot
        this.addSlot(new Slot(container, 3, WitcherForgeLayout.FORGE_SLOT_POINTS[3].x(), WitcherForgeLayout.FORGE_SLOT_POINTS[3].y()));

        // Blade core slot
        this.addSlot(new Slot(container, 4, WitcherForgeLayout.FORGE_SLOT_POINTS[4].x(), WitcherForgeLayout.FORGE_SLOT_POINTS[4].y()));

        // Payment slot
        this.addSlot(new Slot(container, 5, WitcherForgeLayout.FORGE_SLOT_POINTS[5].x(), WitcherForgeLayout.FORGE_SLOT_POINTS[5].y()));

        // Output slot
        this.addSlot(new Slot(container, 6, WitcherForgeLayout.FORGE_SLOT_POINTS[6].x(), WitcherForgeLayout.FORGE_SLOT_POINTS[6].y()) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });
    }

    private void addPlayerInventory(Inventory playerInventory) {
        int xStart = WitcherForgeLayout.PLAYER_INV_X;
        int yStart = WitcherForgeLayout.PLAYER_INV_Y;

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(playerInventory, column + row * 9 + 9, xStart + column * 18, yStart + row * 18));
            }
        }

        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInventory, column, xStart + column * 18, yStart + WitcherForgeLayout.HOTBAR_OFFSET_Y));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack original = slot.getItem();
            newStack = original.copy();

            if (index < SLOT_COUNT) {
                if (!moveItemStackTo(original, SLOT_COUNT, SLOT_COUNT + PLAYER_INVENTORY_SLOTS + HOTBAR_SLOTS, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(original, 0, SLOT_COUNT, false)) {
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
        return stillValid(access, player, ModBlocks.WITCHER_FORGE);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        container.stopOpen(player);
    }
}