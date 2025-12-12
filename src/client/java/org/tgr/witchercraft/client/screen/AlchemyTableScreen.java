package org.tgr.witchercraft.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.tgr.witchercraft.block.alchemy.AlchemyTableLayout;
import org.tgr.witchercraft.block.alchemy.AlchemyTableMenu;

/**
 * Client-side screen for the Witcher alchemy table.
 */
public class AlchemyTableScreen extends AbstractContainerScreen<AlchemyTableMenu> {

    private static final ResourceLocation BASE_TEXTURE =
        ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/container/grindstone.png");

    private static final Component TITLE = Component.translatable("container.witchercraft.alchemy_table");
    private static final Component INSTRUCTION = Component.translatable("gui.witchercraft.alchemy_table.instructions");

    public AlchemyTableScreen(AlchemyTableMenu menu, Inventory inventory, Component ignoredTitle) {
        super(menu, inventory, TITLE);
        this.imageWidth = AlchemyTableLayout.IMAGE_WIDTH;
        this.imageHeight = AlchemyTableLayout.IMAGE_HEIGHT;
        this.titleLabelX = 8;
        this.titleLabelY = 6;
        this.inventoryLabelX = AlchemyTableLayout.PLAYER_INV_X;
        this.inventoryLabelY = AlchemyTableLayout.PLAYER_INV_Y - 10;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(graphics, mouseX, mouseY, delta);
        super.render(graphics, mouseX, mouseY, delta);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderLabels(graphics, mouseX, mouseY);
        graphics.drawString(this.font, INSTRUCTION, 8, 20, 0xFFE9E2D5, false);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float delta, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;
        graphics.blit(BASE_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
    }
}
