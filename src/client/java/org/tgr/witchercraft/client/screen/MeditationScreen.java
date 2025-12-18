package org.tgr.witchercraft.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.tgr.witchercraft.client.player.ClientPlayerData;
import org.tgr.witchercraft.network.ClientNetworking;
import org.tgr.witchercraft.player.PlayerLevel;

/**
 * Meditation screen for time skip, healing, toxicity decay, and skill allocation.
 */
public class MeditationScreen extends Screen {
    
    private static final int PANEL_WIDTH = 300;
    private static final int PANEL_HEIGHT = 220;
    
    private int panelX;
    private int panelY;
    
    private int hoursToSkip = 1;
    private boolean isMeditating = false;
    private int meditationTicks = 0;
    
    public MeditationScreen() {
        super(Component.literal("Meditation"));
    }
    
    @Override
    protected void init() {
        super.init();
        
        panelX = (this.width - PANEL_WIDTH) / 2;
        panelY = (this.height - PANEL_HEIGHT) / 2;
        
        int buttonY = panelY + 60;
        int centerX = panelX + PANEL_WIDTH / 2;
        
        // Time skip controls
        addRenderableWidget(Button.builder(Component.literal("-"), button -> decreaseTime())
            .bounds(centerX - 70, buttonY, 30, 20)
            .build());
        
        addRenderableWidget(Button.builder(Component.literal("+"), button -> increaseTime())
            .bounds(centerX + 40, buttonY, 30, 20)
            .build());
        
        // Meditate button
        addRenderableWidget(Button.builder(Component.literal("Meditate"), button -> startMeditation())
            .bounds(centerX - 50, buttonY + 35, 100, 20)
            .build());
        
        // Quick options
        addRenderableWidget(Button.builder(Component.literal("Until Dawn"), button -> meditateUntilDawn())
            .bounds(panelX + 20, buttonY + 70, 80, 20)
            .build());
        
        addRenderableWidget(Button.builder(Component.literal("Until Dusk"), button -> meditateUntilDusk())
            .bounds(panelX + PANEL_WIDTH - 100, buttonY + 70, 80, 20)
            .build());
        
        // Close button
        addRenderableWidget(Button.builder(Component.literal("Close"), button -> this.onClose())
            .bounds(centerX - 40, panelY + PANEL_HEIGHT - 35, 80, 20)
            .build());
    }
    
    private void decreaseTime() {
        if (hoursToSkip > 1) {
            hoursToSkip--;
        }
    }
    
    private void increaseTime() {
        if (hoursToSkip < 12) {
            hoursToSkip++;
        }
    }
    
    private void startMeditation() {
        if (!isMeditating && minecraft != null && minecraft.player != null) {
            isMeditating = true;
            meditationTicks = 0;
            
            // Send meditation packet to server
            ClientNetworking.sendMeditationRequest(hoursToSkip);
        }
    }
    
    private void meditateUntilDawn() {
        if (minecraft != null && minecraft.level != null) {
            long dayTime = minecraft.level.getDayTime() % 24000;
            int hoursUntilDawn;
            
            // Dawn is around 0-1000 ticks (roughly 0-6000 = night, 6000-18000 = day, 18000-24000 = night)
            if (dayTime >= 0 && dayTime < 1000) {
                hoursUntilDawn = 0; // Already dawn
            } else if (dayTime < 13000) {
                // Daytime, wait until next dawn
                hoursUntilDawn = (int) ((24000 - dayTime) / 1000);
            } else {
                // Night time
                hoursUntilDawn = (int) ((24000 - dayTime) / 1000);
            }
            
            hoursToSkip = Math.max(1, Math.min(12, hoursUntilDawn));
            startMeditation();
        }
    }
    
    private void meditateUntilDusk() {
        if (minecraft != null && minecraft.level != null) {
            long dayTime = minecraft.level.getDayTime() % 24000;
            int hoursUntilDusk;
            
            // Dusk is around 12000-13000 ticks
            if (dayTime < 12000) {
                hoursUntilDusk = (int) ((12000 - dayTime) / 1000);
            } else {
                hoursUntilDusk = (int) ((36000 - dayTime) / 1000);
            }
            
            hoursToSkip = Math.max(1, Math.min(12, hoursUntilDusk));
            startMeditation();
        }
    }
    
    @Override
    public void tick() {
        super.tick();
        
        if (isMeditating) {
            meditationTicks++;
            
            // Meditation animation/effects
            if (meditationTicks >= 60) { // 3 seconds
                isMeditating = false;
                this.onClose();
            }
        }
    }
    
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        // Draw background panel
        graphics.fill(panelX, panelY, panelX + PANEL_WIDTH, panelY + PANEL_HEIGHT, 0xDD000000);
        graphics.fill(panelX + 1, panelY + 1, panelX + PANEL_WIDTH - 1, panelY + PANEL_HEIGHT - 1, 0xDD1A1A1A);
        
        // Draw border
        graphics.fill(panelX, panelY, panelX + PANEL_WIDTH, panelY + 2, 0xFF4A4A4A);
        graphics.fill(panelX, panelY + PANEL_HEIGHT - 2, panelX + PANEL_WIDTH, panelY + PANEL_HEIGHT, 0xFF4A4A4A);
        graphics.fill(panelX, panelY, panelX + 2, panelY + PANEL_HEIGHT, 0xFF4A4A4A);
        graphics.fill(panelX + PANEL_WIDTH - 2, panelY, panelX + PANEL_WIDTH, panelY + PANEL_HEIGHT, 0xFF4A4A4A);
        
        // Title
        graphics.drawCenteredString(this.font, "§e§lMeditation", panelX + PANEL_WIDTH / 2, panelY + 10, 0xFFFFFF);
        
        // Current time display
        if (minecraft != null && minecraft.level != null) {
            long dayTime = minecraft.level.getDayTime() % 24000;
            int hour = (int) ((dayTime / 1000 + 6) % 24); // MC time starts at 6am
            String amPm = hour >= 12 ? "PM" : "AM";
            int displayHour = hour % 12;
            if (displayHour == 0) displayHour = 12;
            
            graphics.drawCenteredString(this.font, "§7Current Time: §f" + displayHour + ":00 " + amPm, 
                panelX + PANEL_WIDTH / 2, panelY + 30, 0xFFFFFF);
        }
        
        // Hours to skip display
        graphics.drawCenteredString(this.font, "§7Skip §f" + hoursToSkip + "§7 hour" + (hoursToSkip > 1 ? "s" : ""), 
            panelX + PANEL_WIDTH / 2, panelY + 65, 0xFFFFFF);
        
        // Player stats that will be affected
        PlayerLevel level = ClientPlayerData.getLevel();
        int skillPoints = level.getSkillPoints();
        int attrPoints = level.getAttributePoints();
        
        int infoY = panelY + 140;
        graphics.drawString(this.font, "§aMeditation Effects:", panelX + 20, infoY, 0xFFFFFF);
        graphics.drawString(this.font, "§7• Restore health to full", panelX + 25, infoY + 12, 0xFFFFFF);
        graphics.drawString(this.font, "§7• Clear all toxicity", panelX + 25, infoY + 22, 0xFFFFFF);
        graphics.drawString(this.font, "§7• Replenish potions (if ingredients available)", panelX + 25, infoY + 32, 0xFFFFFF);
        
        if (skillPoints > 0 || attrPoints > 0) {
            graphics.drawString(this.font, "§a• " + (skillPoints + attrPoints) + " points to allocate!", panelX + 25, infoY + 44, 0xFFFFFF);
        }
        
        // Meditation animation
        if (isMeditating) {
            int alpha = (int) (200 + 55 * Math.sin(meditationTicks * 0.2));
            graphics.fill(0, 0, this.width, this.height, (alpha << 24));
            graphics.drawCenteredString(this.font, "§e§lMeditating...", this.width / 2, this.height / 2, 0xFFFFFF);
        }
        
        super.render(graphics, mouseX, mouseY, partialTick);
    }
    
    @Override
    public boolean isPauseScreen() {
        return true; // Meditation pauses the game in singleplayer
    }
}
