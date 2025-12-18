package org.tgr.witchercraft.client.senses;

import com.mojang.blaze3d.vertex.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;


/**
 * Renders the Witcher Senses visual effects
 * - Entity outlines/glows
 * - Block highlights
 * - Connection lines to points of interest
 */
@Environment(EnvType.CLIENT)
public class WitcherSensesRenderer {

    public static void render(PoseStack poseStack, float partialTicks, Camera camera) {
        if (!WitcherSensesHandler.isActive()) {
            return;
        }

        float alpha = WitcherSensesHandler.getActivationProgress();
        Vec3 cameraPos = camera.getPosition();

        // Render entity highlights
        for (WitcherSensesHandler.HighlightedEntity highlighted : WitcherSensesHandler.getHighlightedEntities()) {
            renderEntityHighlight(poseStack, highlighted, cameraPos, alpha, partialTicks);
        }

        // Render block highlights
        for (WitcherSensesHandler.HighlightedBlock highlighted : WitcherSensesHandler.getHighlightedBlocks()) {
            renderBlockHighlight(poseStack, highlighted, cameraPos, alpha);
        }
    }

    private static void renderEntityHighlight(PoseStack poseStack,
                                              WitcherSensesHandler.HighlightedEntity highlighted, Vec3 cameraPos, float alpha, float partialTicks) {

        Entity entity = highlighted.entity();
        if (entity == null || !entity.isAlive()) return;

        WitcherSensesHandler.HighlightType type = highlighted.type();

        // Get interpolated entity position
        double x = entity.xOld + (entity.getX() - entity.xOld) * partialTicks - cameraPos.x;
        double y = entity.yOld + (entity.getY() - entity.yOld) * partialTicks - cameraPos.y;
        double z = entity.zOld + (entity.getZ() - entity.zOld) * partialTicks - cameraPos.z;

        // Create bounding box around entity
        AABB box = entity.getBoundingBox().move(-entity.getX(), -entity.getY(), -entity.getZ());

        poseStack.pushPose();
        poseStack.translate(x, y, z);

        Matrix4f matrix = poseStack.last().pose();

        int r = (int)(type.r * 255);
        int g = (int)(type.g * 255);
        int b = (int)(type.b * 255);
        int a = (int)(alpha * 127);
        int color = (a << 24) | (r << 16) | (g << 8) | b;

        // For now, we just note the entity is highlighted
        // Full wireframe rendering requires the new 1.21.1 rendering pipeline
        // which we'll implement when custom rendering is set up

        poseStack.popPose();
    }

    private static void renderBlockHighlight(PoseStack poseStack,
                                             WitcherSensesHandler.HighlightedBlock highlighted, Vec3 cameraPos, float alpha) {

        BlockPos pos = highlighted.pos();
        WitcherSensesHandler.HighlightType type = highlighted.type();

        double x = pos.getX() - cameraPos.x;
        double y = pos.getY() - cameraPos.y;
        double z = pos.getZ() - cameraPos.z;

        poseStack.pushPose();
        poseStack.translate(x, y, z);

        Matrix4f matrix = poseStack.last().pose();

        int r = (int)(type.r * 255);
        int g = (int)(type.g * 255);
        int b = (int)(type.b * 255);

        // Slightly pulsing effect
        float pulse = (float)(Math.sin(System.currentTimeMillis() * 0.005) * 0.1 + 0.9);
        int a = (int)(alpha * 102 * pulse);
        int color = (a << 24) | (r << 16) | (g << 8) | b;

        // Block highlight - simplified for 1.21.1 compatibility
        // Full implementation will use custom render types

        poseStack.popPose();
    }

    /**
     * Renders the HUD overlay when Witcher Senses is active
     */
    public static void renderHudOverlay(net.minecraft.client.gui.GuiGraphics guiGraphics, float partialTicks) {
        if (!WitcherSensesHandler.isActive()) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        float alpha = WitcherSensesHandler.getActivationProgress();

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        // Draw vignette effect with orange tint (Witcher vision)
        int vignetteColor = ((int)(alpha * 30) << 24) | 0xFF6600; // Orange with low alpha

        // Draw corners darker
        int cornerAlpha = (int)(alpha * 60);
        int cornerSize = 100;

        // Top-left corner gradient
        guiGraphics.fill(0, 0, cornerSize, cornerSize, (cornerAlpha << 24) | 0x000000);

        // Top-right corner gradient
        guiGraphics.fill(screenWidth - cornerSize, 0, screenWidth, cornerSize, (cornerAlpha << 24) | 0x000000);

        // Bottom-left corner gradient
        guiGraphics.fill(0, screenHeight - cornerSize, cornerSize, screenHeight, (cornerAlpha << 24) | 0x000000);

        // Bottom-right corner gradient
        guiGraphics.fill(screenWidth - cornerSize, screenHeight - cornerSize, screenWidth, screenHeight, (cornerAlpha << 24) | 0x000000);

        // Draw "WITCHER SENSES ACTIVE" text at top
        if (alpha > 0.5f) {
            String text = "WITCHER SENSES";
            int textWidth = mc.font.width(text);
            int textX = (screenWidth - textWidth) / 2;
            int textY = 10;
            int textColor = 0xFF6600 | ((int)(alpha * 200) << 24);
            guiGraphics.drawString(mc.font, text, textX, textY, textColor, false);
        }
    }
}