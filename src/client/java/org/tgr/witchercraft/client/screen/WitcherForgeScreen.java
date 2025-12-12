package org.tgr.witchercraft.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.tgr.witchercraft.block.WitcherForge.WitcherForgeLayout;
import org.tgr.witchercraft.block.WitcherForge.WitcherForgeLayout.SlotPoint;
import org.tgr.witchercraft.block.WitcherForge.WitcherForgeMenu;

public class WitcherForgeScreen extends AbstractContainerScreen<WitcherForgeMenu> {

    private static final ResourceLocation BASE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/container/anvil.png");

    private static final Component INSTRUCTION_TEXT =
            Component.translatable("gui.witchercraft.witcher_forge.instructions");
    private static final Component BUTTON_TEXT =
            Component.translatable("gui.witchercraft.witcher_forge.button");
    private static final Component SLOT_POMMEL_TEXT =
        Component.translatable("gui.witchercraft.witcher_forge.slot.pommel");
    private static final Component SLOT_GRIP_TEXT =
        Component.translatable("gui.witchercraft.witcher_forge.slot.grip");
    private static final Component SLOT_GUARD_TEXT =
        Component.translatable("gui.witchercraft.witcher_forge.slot.guard");
    private static final Component SLOT_BLADE_TEXT =
        Component.translatable("gui.witchercraft.witcher_forge.slot.blade");
    private static final Component SLOT_CORE_TEXT =
        Component.translatable("gui.witchercraft.witcher_forge.slot.core");
    private static final Component SLOT_TRIBUTE_TEXT =
        Component.translatable("gui.witchercraft.witcher_forge.slot.tribute");
    private static final Component SLOT_OUTPUT_TEXT =
        Component.translatable("gui.witchercraft.witcher_forge.slot.output");

    private static final int FORGE_AREA_TINT = 0x66301C13;
    private static final int SWORD_TINT = 0x332C2C34;
    private static final int SWORD_EDGE = 0x55BEC2D1;
    private static final int HANDLE_TINT = 0x884C3623;
    private static final int LINK_COLOR = 0xFF8C6C42;
    private static final int SLOT_OUTLINE = 0xFF3A3A45;
    private static final int SLOT_FILL = 0xFF1F1F26;
    private static final int SLOT_ACCENT = 0xFF9C723F;
    private static final int BUTTON_BORDER = 0xFF5C3F20;
    private static final int BUTTON_FACE = 0xFFE6C07B;
    private static final int BUTTON_SHADOW = 0xFF9A6723;
    private static final int TEXT_PRIMARY = 0xFFE9E2D5;
    private static final int TEXT_SECONDARY = 0xFFCBBEAA;

    private static final SlotLabel[] SLOT_LABELS = new SlotLabel[] {
            new SlotLabel(WitcherForgeLayout.SLOT_POMMEL, SLOT_POMMEL_TEXT, -2, -10),
            new SlotLabel(WitcherForgeLayout.SLOT_GRIP, SLOT_GRIP_TEXT, -4, -10),
            new SlotLabel(WitcherForgeLayout.SLOT_GUARD, SLOT_GUARD_TEXT, -6, -10),
            new SlotLabel(WitcherForgeLayout.SLOT_BLADE, SLOT_BLADE_TEXT, -2, -10),
            new SlotLabel(WitcherForgeLayout.SLOT_CORE, SLOT_CORE_TEXT, -2, -10),
            new SlotLabel(WitcherForgeLayout.SLOT_TRIBUTE, SLOT_TRIBUTE_TEXT, -6, -10),
            new SlotLabel(WitcherForgeLayout.SLOT_OUTPUT, SLOT_OUTPUT_TEXT, -4, -10)
    };

    public WitcherForgeScreen(WitcherForgeMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = WitcherForgeLayout.IMAGE_WIDTH;
        this.imageHeight = WitcherForgeLayout.IMAGE_HEIGHT;
        this.titleLabelX = 8;
        this.titleLabelY = 6;
        this.inventoryLabelX = WitcherForgeLayout.PLAYER_INV_X;
        this.inventoryLabelY = WitcherForgeLayout.PLAYER_INV_Y - 10;
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
        graphics.drawString(this.font, INSTRUCTION_TEXT, 8, 18, TEXT_PRIMARY, false);
        drawSlotLabels(graphics);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float delta, int mouseX, int mouseY) {
        int x = this.leftPos;
        int y = this.topPos;

        graphics.blit(BASE_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
        drawForgeBackdrop(graphics, x, y);
        drawSwordSilhouette(graphics, x, y);
        drawForgeConnections(graphics, x, y);
        drawSlots(graphics, x, y);
        drawForgeButton(graphics, x, y);
    }

    private void drawForgeBackdrop(GuiGraphics graphics, int offsetX, int offsetY) {
        graphics.fill(offsetX + WitcherForgeLayout.FORGE_PANEL_LEFT,
                offsetY + WitcherForgeLayout.FORGE_PANEL_TOP,
                offsetX + WitcherForgeLayout.FORGE_PANEL_RIGHT,
                offsetY + WitcherForgeLayout.FORGE_PANEL_BOTTOM,
                FORGE_AREA_TINT);
    }

    private void drawSwordSilhouette(GuiGraphics graphics, int offsetX, int offsetY) {
        SlotPoint pommel = WitcherForgeLayout.FORGE_SLOT_POINTS[WitcherForgeLayout.SLOT_POMMEL];
        SlotPoint core = WitcherForgeLayout.FORGE_SLOT_POINTS[WitcherForgeLayout.SLOT_CORE];

        float steps = 6f;
        for (int i = 0; i <= steps; i++) {
            float progress = i / steps;
            int centerX = offsetX + Math.round(Mth.lerp(progress, pommel.x() + 8, core.x() + 8));
            int centerY = offsetY + Math.round(Mth.lerp(progress, pommel.y() + 8, core.y() + 8));
            int color = i >= steps - 1 ? SWORD_EDGE : SWORD_TINT;
            graphics.fill(centerX - 3, centerY - 3, centerX + 3, centerY + 3, color);
        }

        SlotPoint guard = WitcherForgeLayout.FORGE_SLOT_POINTS[WitcherForgeLayout.SLOT_GUARD];
        int guardCenterX = offsetX + guard.x() + 8;
        int guardCenterY = offsetY + guard.y() + 8;
        graphics.fill(guardCenterX - 10, guardCenterY - 2, guardCenterX + 10, guardCenterY + 2, HANDLE_TINT);

        SlotPoint grip = WitcherForgeLayout.FORGE_SLOT_POINTS[WitcherForgeLayout.SLOT_GRIP];
        int gripCenterX = offsetX + grip.x() + 8;
        int gripCenterY = offsetY + grip.y() + 8;
        graphics.fill(gripCenterX - 3, gripCenterY - 6, gripCenterX + 3, gripCenterY + 6, HANDLE_TINT);
    }

    private void drawForgeConnections(GuiGraphics graphics, int offsetX, int offsetY) {
        SlotPoint tribute = WitcherForgeLayout.FORGE_SLOT_POINTS[WitcherForgeLayout.SLOT_TRIBUTE];
        SlotPoint output = WitcherForgeLayout.FORGE_SLOT_POINTS[WitcherForgeLayout.SLOT_OUTPUT];

        int swordCenterX = offsetX + WitcherForgeLayout.SWORD_LINK_X;
        int swordCenterY = offsetY + WitcherForgeLayout.SWORD_LINK_Y;
        int tributeCenterX = offsetX + tribute.x() + 8;
        int tributeCenterY = offsetY + tribute.y() + 8;
        int buttonCenterX = offsetX + WitcherForgeLayout.BUTTON_CENTER_X;
        int buttonCenterY = offsetY + WitcherForgeLayout.BUTTON_CENTER_Y;
        int outputCenterX = offsetX + output.x() + 8;
        int outputCenterY = offsetY + output.y() + 8;

        drawConnector(graphics, swordCenterX, swordCenterY, tributeCenterX, tributeCenterY);
        drawConnector(graphics, tributeCenterX, tributeCenterY, buttonCenterX, buttonCenterY);
        drawConnector(graphics, buttonCenterX, buttonCenterY, outputCenterX, outputCenterY);
    }

    private void drawConnector(GuiGraphics graphics, int startX, int startY, int endX, int endY) {
        if (startX != endX) {
            int left = Math.min(startX, endX);
            int right = Math.max(startX, endX);
            graphics.fill(left, startY, right, startY + 2, LINK_COLOR);
        }
        if (startY != endY) {
            int top = Math.min(startY, endY);
            int bottom = Math.max(startY, endY);
            graphics.fill(endX, top, endX + 2, bottom, LINK_COLOR);
        }
    }

    private void drawSlots(GuiGraphics graphics, int offsetX, int offsetY) {
        SlotPoint[] points = WitcherForgeLayout.FORGE_SLOT_POINTS;

        for (int i = 0; i < points.length; i++) {
            SlotPoint point = points[i];
            boolean isOutput = i == 6;
            drawSlot(graphics, offsetX + point.x(), offsetY + point.y(), isOutput);
        }
    }

    private void drawSlot(GuiGraphics graphics, int x, int y, boolean accent) {
        graphics.fill(x - 2, y - 2, x + 18, y + 18, 0x33000000);
        graphics.fill(x - 1, y - 1, x + 17, y + 17, accent ? SLOT_ACCENT : SLOT_OUTLINE);
        graphics.fill(x, y, x + 16, y + 16, accent ? 0xFF2E2014 : SLOT_FILL);
    }

    private void drawForgeButton(GuiGraphics graphics, int offsetX, int offsetY) {
        int x = offsetX + WitcherForgeLayout.BUTTON_X;
        int y = offsetY + WitcherForgeLayout.BUTTON_Y;
        int width = WitcherForgeLayout.BUTTON_WIDTH;
        int height = WitcherForgeLayout.BUTTON_HEIGHT;

        graphics.fill(x, y, x + width, y + height, BUTTON_BORDER);
        graphics.fill(x + 1, y + 1, x + width - 1, y + height - 1, BUTTON_FACE);
        graphics.fill(x + 1, y + height - 3, x + width - 1, y + height - 1, BUTTON_SHADOW);

        int textWidth = this.font.width(BUTTON_TEXT);
        int textX = x + (width - textWidth) / 2;
        int textY = y + 4;
        graphics.drawString(this.font, BUTTON_TEXT, textX, textY, 0xFF3B2913, false);
    }

    private void drawSlotLabels(GuiGraphics graphics) {
        for (SlotLabel label : SLOT_LABELS) {
            SlotPoint slotPoint = WitcherForgeLayout.FORGE_SLOT_POINTS[label.slotIndex()];
            int labelX = slotPoint.x() + label.offsetX();
            int labelY = slotPoint.y() + label.offsetY();
            graphics.drawString(this.font, label.text(), labelX, labelY, TEXT_SECONDARY, false);
        }
    }

    private record SlotLabel(int slotIndex, Component text, int offsetX, int offsetY) {
    }
}
