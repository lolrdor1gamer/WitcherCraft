package org.tgr.witchercraft.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.block.alchemy.AlchemyTableBlockEntity;
import org.tgr.witchercraft.block.alchemy.AlchemyTableLayout;
import org.tgr.witchercraft.block.alchemy.AlchemyTableMenu;

/**
 * Client-side screen for the Witcher alchemy table.
 */
public class AlchemyTableScreen extends AbstractContainerScreen<AlchemyTableMenu> {

    private static final ResourceLocation TEXTURE =
        ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "textures/gui/alchemy_table.png");

    private static final Component TITLE = Component.translatable("container.witchercraft.alchemy_table");
    private static final Component INSTRUCTION = Component.translatable("gui.witchercraft.alchemy_table.instructions");
    private static final Component SLOT_BASE = Component.translatable("gui.witchercraft.alchemy_table.slot.base");
    private static final Component SLOT_REAGENT = Component.translatable("gui.witchercraft.alchemy_table.slot.reagent");
    private static final Component SLOT_CATALYST = Component.translatable("gui.witchercraft.alchemy_table.slot.catalyst");
    private static final Component SLOT_OUTPUT = Component.translatable("gui.witchercraft.alchemy_table.slot.output");
    private static final Component STATUS_READY = Component.translatable("gui.witchercraft.alchemy_table.status.ready");
    private static final Component STATUS_WAITING = Component.translatable("gui.witchercraft.alchemy_table.status.waiting");
    private static final Component STATUS_INVALID = Component.translatable("gui.witchercraft.alchemy_table.status.invalid");

    private static final int STATUS_COLOR = 0xFFE5D2AD;
    private static final int SLOT_LABEL_COLOR = 0xFFC9B080;
    private static final int STATUS_Y = 52;
    private static final int PROGRESS_X = 112;
    private static final int PROGRESS_Y = 22;
    private static final int PROGRESS_WIDTH = 30;
    private static final int PROGRESS_HEIGHT = 22;

    public AlchemyTableScreen(AlchemyTableMenu menu, Inventory inventory, Component ignoredTitle) {
        super(menu, inventory, TITLE);
        this.imageWidth = AlchemyTableLayout.IMAGE_WIDTH;
        this.imageHeight = AlchemyTableLayout.IMAGE_HEIGHT;

        this.inventoryLabelX = -10000;
        this.titleLabelX = -10000;
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
        graphics.drawString(this.font, INSTRUCTION, 18, 12, STATUS_COLOR, false);
        drawSlotLabel(graphics, SLOT_BASE, AlchemyTableLayout.BASE_SLOT.x() - 4, AlchemyTableLayout.BASE_SLOT.y() + 26);
        drawSlotLabel(graphics, SLOT_REAGENT, AlchemyTableLayout.REAGENT_SLOT.x() - 8, AlchemyTableLayout.REAGENT_SLOT.y() + 24);
        drawSlotLabel(graphics, SLOT_CATALYST, AlchemyTableLayout.CATALYST_SLOT.x() - 10, AlchemyTableLayout.CATALYST_SLOT.y() + 26);
        drawSlotLabel(graphics, SLOT_OUTPUT, AlchemyTableLayout.OUTPUT_SLOT.x() - 2, AlchemyTableLayout.OUTPUT_SLOT.y() + 26);

        Component status = resolveStatus();
    graphics.drawString(this.font, status, 18, 62, STATUS_COLOR, false);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float delta, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;

    graphics.blit(
        RenderPipelines.GUI_TEXTURED,
        TEXTURE,
        x, y,
        0, 0,
        imageWidth, imageHeight,
            imageWidth,imageHeight
    );

        boolean ready = menu.getSlot(AlchemyTableBlockEntity.SLOT_OUTPUT).hasItem();
        renderProgressIndicator(graphics, x, y, ready);

        if (ready) {
            renderOutputGlow(graphics, x, y);
        }

    }

    private void renderProgressIndicator(GuiGraphics graphics, int left, int top, boolean ready) {
        int px = left + PROGRESS_X;
        int py = top + PROGRESS_Y;
        int innerLeft = px + 2;
        int innerRight = px + PROGRESS_WIDTH - 2;
        int innerTop = py + 2;
        int innerBottom = py + PROGRESS_HEIGHT - 2;

        graphics.fill(px, py, px + PROGRESS_WIDTH, py + PROGRESS_HEIGHT, 0xAA1E130C);
        graphics.fill(innerLeft, innerTop, innerRight, innerBottom, 0xFF2A1B11);

        int barColor = ready ? 0xFFEFD79F : 0xFF8F6E4C;
        int accentColor = ready ? 0xFFFFF1CA : 0xFFB48A61;
        graphics.fill(innerLeft + 2, innerTop + 4, innerRight - 6, innerBottom - 4, barColor);
        graphics.fill(innerRight - 6, innerTop + 2, innerRight - 2, innerBottom - 2, accentColor);
    }

    private void drawSlotLabel(GuiGraphics graphics, Component label, int x, int y) {
        graphics.drawString(this.font, label, x, y, SLOT_LABEL_COLOR, false);
    }

    private Component resolveStatus() {
        boolean hasBase = menu.getSlot(AlchemyTableBlockEntity.SLOT_BASE).hasItem();
        boolean hasReagent = menu.getSlot(AlchemyTableBlockEntity.SLOT_REAGENT).hasItem();
        boolean hasCatalyst = menu.getSlot(AlchemyTableBlockEntity.SLOT_CATALYST).hasItem();
        boolean ready = menu.getSlot(AlchemyTableBlockEntity.SLOT_OUTPUT).hasItem();

        if (ready) {
            return STATUS_READY;
        }
        if (hasBase && hasReagent && hasCatalyst) {
            return STATUS_INVALID;
        }
        return STATUS_WAITING;
    }

    private void renderOutputGlow(GuiGraphics graphics, int left, int top) {
        int slotX = left + AlchemyTableLayout.OUTPUT_SLOT.x();
        int slotY = top + AlchemyTableLayout.OUTPUT_SLOT.y();
        graphics.fillGradient(slotX - 2, slotY - 2, slotX + 18, slotY + 18, 0x40FFF3C4, 0x10FFF3C4);
    }
}
