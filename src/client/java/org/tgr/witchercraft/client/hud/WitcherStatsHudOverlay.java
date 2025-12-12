package org.tgr.witchercraft.client.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.tgr.witchercraft.client.player.ClientWitcherStats;

/**
 * Simple HUD overlay that renders stamina/mana bars and toxicity readouts.
 */
public final class WitcherStatsHudOverlay {

    private static final int BAR_WIDTH = 80;
    private static final int BAR_HEIGHT = 6;
    private static final int PADDING = 6;

    private WitcherStatsHudOverlay() {
    }

    @SuppressWarnings("deprecation")
    public static void register() {
        HudRenderCallback.EVENT.register((graphics, delta) -> render(graphics));
    }

    private static void render(GuiGraphics graphics) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || client.options.hideGui) {
            return;
        }

        ClientWitcherStats stats = ClientWitcherStats.get();
        int x = PADDING;
        int y = client.getWindow().getGuiScaledHeight() - (BAR_HEIGHT + PADDING) * 3;

        drawBar(graphics, x, y, BAR_WIDTH, BAR_HEIGHT, stats.getStaminaRatio(), 0xFF3FA34D, Component.translatable("hud.witchercraft.stamina"), stats.getStamina());
        drawBar(graphics, x, y + BAR_HEIGHT + PADDING, BAR_WIDTH, BAR_HEIGHT, stats.getManaRatio(), 0xFF4570DB, Component.translatable("hud.witchercraft.mana"), stats.getMana());
        drawBar(graphics, x, y + (BAR_HEIGHT + PADDING) * 2, BAR_WIDTH, BAR_HEIGHT, stats.getToxicityRatio(), 0xFFAA2C2C, Component.translatable("hud.witchercraft.toxicity"), stats.getToxicity());

        drawCooldownList(graphics, x + BAR_WIDTH + PADDING, y, stats);
    }

    private static void drawBar(GuiGraphics graphics, int x, int y, int width, int height, float ratio, int color, Component label, int value) {
        ratio = Mth.clamp(ratio, 0.0F, 1.0F);
        graphics.fill(x, y, x + width, y + height, 0x55000000);
        graphics.fill(x + 1, y + 1, x + 1 + Math.round((width - 2) * ratio), y + height - 1, color);
        graphics.drawString(Minecraft.getInstance().font, label.copy().append(": " + value), x + 2, y - 9, 0xFFFFFF, false);
    }

    private static void drawCooldownList(GuiGraphics graphics, int startX, int startY, ClientWitcherStats stats) {
        int line = 0;
        for (var entry : stats.cooldowns().object2IntEntrySet()) {
            int ticks = entry.getIntValue();
            if (ticks <= 0) {
                continue;
            }
            float seconds = ticks / 20.0F;
            Component text = Component.literal(entry.getKey() + ": " + String.format("%.1fs", seconds));
            graphics.drawString(Minecraft.getInstance().font, text, startX, startY + line * 10, 0xFFEEAA55, false);
            line++;
            if (line >= 5) {
                break;
            }
        }
    }
}
