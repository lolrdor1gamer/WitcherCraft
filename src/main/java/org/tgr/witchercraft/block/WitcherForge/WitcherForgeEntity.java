package org.tgr.witchercraft.block.WitcherForge;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Clearable;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.tgr.witchercraft.registry.ModBlockEntities;

public class WitcherForgeEntity extends BlockEntity implements MenuProvider, Clearable {

    public static final int SLOT_COUNT = 7;

    private final SimpleContainer inventory = new SimpleContainer(SLOT_COUNT) {
        @Override
        public void setChanged() {
            super.setChanged();
            WitcherForgeEntity.this.setChanged();
        }
    };

    public WitcherForgeEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WITCHER_FORGE_ENTITY, pos, state);
    }

    public SimpleContainer getInventory() {
        return inventory;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.witchercraft.witcher_forge");
    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new WitcherForgeMenu(syncId, playerInventory, this);
    }

    @Override
    public void clearContent() {
        inventory.clearContent();
    }
}
