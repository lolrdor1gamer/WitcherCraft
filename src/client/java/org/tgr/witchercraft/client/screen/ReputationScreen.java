package org.tgr.witchercraft.client.reputation;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.faction.Faction;
import org.tgr.witchercraft.faction.FactionReputation;
import org.tgr.witchercraft.faction.ReputationLevel;

import java.util.ArrayList;
import java.util.List;

/**
 * Reputation screen with alchemy table aesthetic.
 */
public class ReputationScreen extends Screen {
    
    private static final ResourceLocation TEXTURE =
        ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "textures/gui/reputation.png");
    
    private static final int PANEL_WIDTH = 176;
    private static final int PANEL_HEIGHT = 210;
    private static final int FACTION_ENTRY_HEIGHT = 24;
    private static final int FACTION_SPACING = 2;
    private static final int FACTION_START_Y = 30;
    
    private int panelX;
    private int panelY;
    private final List<FactionEntry> factionEntries = new ArrayList<>();

    public ReputationScreen() {
        super(Component.literal("Faction Reputation"));
    }

    @Override
    protected void init() {
        super.init();
        
        this.panelX = (this.width - PANEL_WIDTH) / 2;
        this.panelY = (this.height - PANEL_HEIGHT) / 2;
        
        factionEntries.clear();
        if (minecraft != null && minecraft.player != null) {
            for (Faction faction : Faction.values()) {
                int score = FactionReputation.getReputation(minecraft.player, faction);
                ReputationLevel level = ReputationLevel.fromScore(score);
                factionEntries.add(new FactionEntry(faction, score, level));
            }
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        // Render background
        graphics.fill(0, 0, this.width, this.height, 0xC0101010);
        
        // Render the GUI texture
        graphics.blit(
            RenderPipelines.GUI_TEXTURED,
            TEXTURE,
            panelX, panelY,
            0, 0,
            PANEL_WIDTH, PANEL_HEIGHT,
            256, 256
        );
        
        // Title
        Component title = Component.literal("Faction Reputation").withColor(0xE5D2AD);
        int titleWidth = this.font.width(title);
        graphics.drawString(this.font, title, panelX + (PANEL_WIDTH - titleWidth) / 2, panelY + 8, 0xE5D2AD, false);
        
        // Render faction entries
        for (int i = 0; i < factionEntries.size(); i++) {
            FactionEntry entry = factionEntries.get(i);
            int entryY = panelY + FACTION_START_Y + i * (FACTION_ENTRY_HEIGHT + FACTION_SPACING);
            renderFactionEntry(graphics, entry, panelX, entryY, mouseX, mouseY);
        }
        
        // Global reputation
        if (minecraft != null && minecraft.player != null) {
            int globalScore = FactionReputation.getGlobalReputation(minecraft.player);
            ReputationLevel globalLevel = FactionReputation.getGlobalLevel(minecraft.player);
            
            Component globalText = Component.literal("Global: ")
                .withColor(0xC9B080)
                .append(Component.literal(globalLevel.getDisplayName()).withColor(globalLevel.getColor()))
                .append(Component.literal(" (" + globalScore + ")").withColor(0xAAAAAA));
            
            int globalY = panelY + PANEL_HEIGHT - 22;
            graphics.drawString(this.font, globalText, panelX + 10, globalY, 0xC9B080, false);
        }
        
        // Instructions
        Component instructions = Component.literal("Press ESC or O to close").withColor(0x888888);
        int instrWidth = this.font.width(instructions);
        graphics.drawString(this.font, instructions, panelX + (PANEL_WIDTH - instrWidth) / 2, 
                          panelY + PANEL_HEIGHT - 10, 0x888888, false);
        
        super.render(graphics, mouseX, mouseY, delta);
    }

    private void renderFactionEntry(GuiGraphics graphics, FactionEntry entry, int x, int y, int mouseX, int mouseY) {
        int entryX = x + 12;
        int entryWidth = PANEL_WIDTH - 24;
        
        Component name = Component.literal(entry.faction.getDisplayName()).withColor(0xFFFFFF);
        graphics.drawString(this.font, name, entryX + 10, y + 3, 0xFFFFFF, false);
        
        Component levelText = Component.literal(entry.level.getDisplayName()).withColor(entry.level.getColor());
        graphics.drawString(this.font, levelText, entryX + 10, y + 12, entry.level.getColor(), false);
        
        Component scoreText = Component.literal(String.valueOf(entry.score)).withColor(0xCCCCCC);
        int scoreWidth = this.font.width(scoreText);
        graphics.drawString(this.font, scoreText, entryX + entryWidth - scoreWidth - 5, y + 3, 0xCCCCCC, false);
        
        ReputationLevel nextLevel = entry.level.next();
        if (nextLevel != null) {
            int barX = entryX + 8;
            int barY = y + FACTION_ENTRY_HEIGHT - 10;
            int barWidth = entryWidth - 16;
            int barHeight = 6;
            
            int progress = entry.level.progressToNext(entry.score);
            int fillWidth = (barWidth * progress) / 100;
            
            graphics.fill(barX, barY, barX + fillWidth, barY + barHeight, 0xFF000000 | entry.faction.getColor());
            
            if (progress > 0) {
                Component progressText = Component.literal(progress + "%").withColor(0xFFFFFF);
                graphics.drawString(this.font, progressText, barX + 2, barY - 1, 0xFFFFFF, false);
            }
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private record FactionEntry(Faction faction, int score, ReputationLevel level) {
    }
}