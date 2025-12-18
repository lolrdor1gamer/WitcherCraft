package org.tgr.witchercraft.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.tgr.witchercraft.bestiary.BestiaryData;
import org.tgr.witchercraft.bestiary.BestiaryEntries;
import org.tgr.witchercraft.client.player.ClientPlayerData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Bestiary screen showing discovered monsters, their weaknesses, and lore.
 */
public class BestiaryScreen extends Screen {
    
    private static final int PANEL_WIDTH = 340;
    private static final int PANEL_HEIGHT = 260;
    private static final int LIST_WIDTH = 100;
    
    private int panelX;
    private int panelY;
    
    private List<ResourceLocation> discoveredList = new ArrayList<>();
    private int selectedIndex = 0;
    private int scrollOffset = 0;
    private static final int VISIBLE_ENTRIES = 8;
    
    private BestiaryData bestiaryData;
    
    public BestiaryScreen() {
        super(Component.literal("Bestiary"));
    }
    
    @Override
    protected void init() {
        super.init();
        
        // Get bestiary data from client cache
        this.bestiaryData = ClientPlayerData.getBestiaryData();
        
        // Build list of discovered monsters
        discoveredList.clear();
        Set<ResourceLocation> discovered = bestiaryData.getDiscoveredMonsters();
        
        // Add all registered bestiary entries (show undiscovered as "???")
        for (BestiaryEntries.BestiaryEntry entry : BestiaryEntries.getAllEntries()) {
            discoveredList.add(entry.entityId());
        }
        
        panelX = (this.width - PANEL_WIDTH) / 2;
        panelY = (this.height - PANEL_HEIGHT) / 2;
        
        // Create navigation buttons
        int buttonY = panelY + PANEL_HEIGHT - 30;
        
        addRenderableWidget(Button.builder(Component.literal("▲"), button -> scrollUp())
            .bounds(panelX + 5, panelY + 25, 20, 20)
            .build());
        
        addRenderableWidget(Button.builder(Component.literal("▼"), button -> scrollDown())
            .bounds(panelX + 5, panelY + PANEL_HEIGHT - 50, 20, 20)
            .build());
        
        addRenderableWidget(Button.builder(Component.literal("Close"), button -> this.onClose())
            .bounds(panelX + PANEL_WIDTH - 70, buttonY, 60, 20)
            .build());
        
        // Create monster selection buttons
        createMonsterButtons();
    }
    
    private void createMonsterButtons() {
        // Remove old monster buttons (keep navigation)
        this.clearWidgets();
        
        // Re-add navigation
        addRenderableWidget(Button.builder(Component.literal("▲"), button -> scrollUp())
            .bounds(panelX + 5, panelY + 25, 20, 20)
            .build());
        
        addRenderableWidget(Button.builder(Component.literal("▼"), button -> scrollDown())
            .bounds(panelX + 5, panelY + PANEL_HEIGHT - 50, 20, 20)
            .build());
        
        addRenderableWidget(Button.builder(Component.literal("Close"), button -> this.onClose())
            .bounds(panelX + PANEL_WIDTH - 70, panelY + PANEL_HEIGHT - 30, 60, 20)
            .build());
        
        // Add monster selection buttons
        int buttonStartY = panelY + 50;
        for (int i = 0; i < VISIBLE_ENTRIES && (i + scrollOffset) < discoveredList.size(); i++) {
            int index = i + scrollOffset;
            ResourceLocation monsterId = discoveredList.get(index);
            
            boolean isDiscovered = bestiaryData.getDiscoveredMonsters().contains(monsterId);
            String displayName = isDiscovered ? getMonsterName(monsterId) : "???";
            
            int finalIndex = index;
            Button btn = Button.builder(Component.literal(displayName), button -> selectMonster(finalIndex))
                .bounds(panelX + 5, buttonStartY + (i * 22), LIST_WIDTH - 10, 20)
                .build();
            addRenderableWidget(btn);
        }
    }
    
    private String getMonsterName(ResourceLocation id) {
        return BestiaryEntries.getEntry(id)
            .map(BestiaryEntries.BestiaryEntry::name)
            .orElse(id.getPath());
    }
    
    private void selectMonster(int index) {
        this.selectedIndex = index;
    }
    
    private void scrollUp() {
        if (scrollOffset > 0) {
            scrollOffset--;
            createMonsterButtons();
        }
    }
    
    private void scrollDown() {
        if (scrollOffset + VISIBLE_ENTRIES < discoveredList.size()) {
            scrollOffset++;
            createMonsterButtons();
        }
    }
    
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        // Draw background panel
        graphics.fill(panelX, panelY, panelX + PANEL_WIDTH, panelY + PANEL_HEIGHT, 0xDD000000);
        graphics.fill(panelX + 1, panelY + 1, panelX + PANEL_WIDTH - 1, panelY + PANEL_HEIGHT - 1, 0xDD222222);
        
        // Draw border
        graphics.fill(panelX, panelY, panelX + PANEL_WIDTH, panelY + 2, 0xFF666666);
        graphics.fill(panelX, panelY + PANEL_HEIGHT - 2, panelX + PANEL_WIDTH, panelY + PANEL_HEIGHT, 0xFF666666);
        graphics.fill(panelX, panelY, panelX + 2, panelY + PANEL_HEIGHT, 0xFF666666);
        graphics.fill(panelX + PANEL_WIDTH - 2, panelY, panelX + PANEL_WIDTH, panelY + PANEL_HEIGHT, 0xFF666666);
        
        // Draw list separator
        graphics.fill(panelX + LIST_WIDTH, panelY + 20, panelX + LIST_WIDTH + 2, panelY + PANEL_HEIGHT - 35, 0xFF444444);
        
        // Draw title
        graphics.drawCenteredString(this.font, "§e§lBestiary", panelX + PANEL_WIDTH / 2, panelY + 6, 0xFFFFFF);
        
        // Draw selected monster details
        if (selectedIndex >= 0 && selectedIndex < discoveredList.size()) {
            ResourceLocation monsterId = discoveredList.get(selectedIndex);
            boolean isDiscovered = bestiaryData.getDiscoveredMonsters().contains(monsterId);
            
            int detailX = panelX + LIST_WIDTH + 10;
            int detailY = panelY + 25;
            int detailWidth = PANEL_WIDTH - LIST_WIDTH - 20;
            
            if (isDiscovered) {
                BestiaryEntries.getEntry(monsterId).ifPresent(entry -> {
                    // Monster name
                    graphics.drawString(this.font, "§e§l" + entry.name(), detailX, detailY, 0xFFFFFF);
                    
                    // Category and danger level
                    graphics.drawString(this.font, "§7Category: §f" + entry.category().name(), 
                        detailX, detailY + 12, 0xFFFFFF);
                    graphics.drawString(this.font, "§7Danger: §c" + entry.dangerLevel(), 
                        detailX, detailY + 22, 0xFFFFFF);
                    
                    // Knowledge level and kills
                    BestiaryData.KnowledgeLevel knowledge = bestiaryData.getKnowledgeLevel(null); // TODO: get actual entity type
                    int kills = 0; // bestiaryData.getKillCount(entityType);
                    graphics.drawString(this.font, "§7Knowledge: §a" + knowledge.getDisplayName(), 
                        detailX, detailY + 34, 0xFFFFFF);
                    
                    // Description
                    graphics.drawString(this.font, "§7" + entry.shortDescription(), 
                        detailX, detailY + 50, 0xFFFFFF);
                    
                    // Show weaknesses based on knowledge level
                    if (knowledge.getLevel() >= BestiaryData.KnowledgeLevel.FAMILIAR.getLevel()) {
                        graphics.drawString(this.font, "§cWeaknesses:", detailX, detailY + 70, 0xFFFFFF);
                        int weakY = detailY + 82;
                        for (String weakness : entry.weaknesses()) {
                            graphics.drawString(this.font, "§f• " + weakness, detailX + 5, weakY, 0xFFFFFF);
                            weakY += 10;
                            if (weakY > panelY + PANEL_HEIGHT - 50) break;
                        }
                    } else {
                        graphics.drawString(this.font, "§8Kill more to learn weaknesses...", 
                            detailX, detailY + 70, 0xFFFFFF);
                    }
                    
                    // Show tactics at higher knowledge
                    if (knowledge.getLevel() >= BestiaryData.KnowledgeLevel.STUDIED.getLevel()) {
                        int tactY = detailY + 130;
                        graphics.drawString(this.font, "§aTactics:", detailX, tactY, 0xFFFFFF);
                        tactY += 12;
                        for (String tactic : entry.tactics()) {
                            if (tactY > panelY + PANEL_HEIGHT - 40) break;
                            // Word wrap would be nice here, but keeping it simple
                            String truncated = tactic.length() > 35 ? tactic.substring(0, 32) + "..." : tactic;
                            graphics.drawString(this.font, "§f• " + truncated, detailX + 5, tactY, 0xFFFFFF);
                            tactY += 10;
                        }
                    }
                });
            } else {
                graphics.drawString(this.font, "§7???", detailX, detailY, 0xFFFFFF);
                graphics.drawString(this.font, "§8Encounter this creature to learn about it.", 
                    detailX, detailY + 15, 0xFFFFFF);
            }
        }
        
        super.render(graphics, mouseX, mouseY, partialTick);
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
