package org.tgr.witchercraft.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.tgr.witchercraft.quest.Quest;
import org.tgr.witchercraft.quest.QuestManager;
import org.tgr.witchercraft.quest.QuestStatus;

import java.util.ArrayList;
import java.util.List;

public class QuestJournalScreen extends Screen {
    
    private final int panelWidth = 360;
    private final int panelHeight = 260;
    private int panelX;
    private int panelY;
    
    private List<Quest> activeQuests = new ArrayList<>();
    private List<Quest> completedQuests = new ArrayList<>();
    private Quest selectedQuest = null;
    private boolean showingActive = true;
    private int scrollOffset = 0;
    
    public QuestJournalScreen() {
        super(Component.literal("Quest Journal"));
    }
    
    @Override
    protected void init() {
        super.init();
        
        // Load quest data from server-side
        if (minecraft != null && minecraft.player != null) {
            this.activeQuests = QuestManager.getActiveQuests(minecraft.player);
            this.completedQuests = QuestManager.getCompletedQuests(minecraft.player);
        } else {
            // Fallback
            this.activeQuests = new ArrayList<>();
            this.completedQuests = new ArrayList<>();
        }
        
        if (!activeQuests.isEmpty()) {
            this.selectedQuest = activeQuests.get(0);
        }
        
        this.panelX = (this.width - panelWidth) / 2;
        this.panelY = (this.height - panelHeight) / 2;
        
        // Active/Completed tabs
        this.addRenderableWidget(Button.builder(
            Component.literal("Active"),
            button -> {
                showingActive = true;
                scrollOffset = 0;
                selectedQuest = activeQuests.isEmpty() ? null : activeQuests.get(0);
            })
            .bounds(panelX + 10, panelY + 25, 80, 20)
            .build());
            
        this.addRenderableWidget(Button.builder(
            Component.literal("Completed"),
            button -> {
                showingActive = false;
                scrollOffset = 0;
                selectedQuest = completedQuests.isEmpty() ? null : completedQuests.get(0);
            })
            .bounds(panelX + 95, panelY + 25, 80, 20)
            .build());
        
        // Close button
        this.addRenderableWidget(Button.builder(
            Component.literal("Close"),
            button -> this.onClose())
            .bounds(panelX + (panelWidth - 60) / 2, panelY + panelHeight - 25, 60, 20)
            .build());
    }
    
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        // Dark background overlay
        graphics.fill(0, 0, this.width, this.height, 0xAA000000);
        
        // Main panel background
        graphics.fill(panelX, panelY, panelX + panelWidth, panelY + panelHeight, 0xFF2C2C2C);
        
        // Title bar
        graphics.fill(panelX, panelY, panelX + panelWidth, panelY + 20, 0xFF1A1A1A);
        graphics.drawCenteredString(this.font, "§6§lQUEST JOURNAL", panelX + panelWidth / 2, panelY + 6, 0xFFFFFF);
        
        // Quest list panel (left side)
        int listX = panelX + 10;
        int listY = panelY + 50;
        int listWidth = 160;
        int listHeight = panelHeight - 85;
        
        graphics.fill(listX, listY, listX + listWidth, listY + listHeight, 0xFF1A1A1A);
        
        List<Quest> questsToShow = showingActive ? activeQuests : completedQuests;
        
        // Draw quest list
        int yOffset = 0;
        for (int i = scrollOffset; i < questsToShow.size(); i++) {
            Quest quest = questsToShow.get(i);
            int questY = listY + 5 + yOffset;
            
            if (questY + 20 > listY + listHeight) break;
            
            boolean isSelected = quest == selectedQuest;
            int bgColor = isSelected ? 0xFF444444 : 0xFF222222;
            
            graphics.fill(listX + 2, questY, listX + listWidth - 2, questY + 18, bgColor);
            
            // Quest name (truncate if too long)
            String questName = quest.getTitle();
            if (this.font.width(questName) > listWidth - 20) {
                while (this.font.width(questName + "...") > listWidth - 20 && questName.length() > 0) {
                    questName = questName.substring(0, questName.length() - 1);
                }
                questName += "...";
            }
            
            int nameColor = showingActive ? 0xFFFFAA00 : 0xFF00AA00;
            graphics.drawString(this.font, questName, listX + 5, questY + 5, nameColor);
            
            yOffset += 20;
        }
        
        // Quest details panel (right side)
        int detailsX = panelX + 180;
        int detailsY = panelY + 50;
        int detailsWidth = panelWidth - 190;
        int detailsHeight = panelHeight - 85;
        
        graphics.fill(detailsX, detailsY, detailsX + detailsWidth, detailsY + detailsHeight, 0xFF1A1A1A);
        
        if (selectedQuest != null) {
            int textY = detailsY + 5;
            
            // Quest name
            graphics.drawString(this.font, "§6§l" + selectedQuest.getTitle(), detailsX + 5, textY, 0xFFFFFF);
            textY += 15;
            
            // Quest type and level
            String typeAndLevel = "§e" + selectedQuest.getType().toString() + " §7(Level " + selectedQuest.getRecommendedLevel() + ")";
            graphics.drawString(this.font, typeAndLevel, detailsX + 5, textY, 0xFFFFFF);
            textY += 12;
            
            // Quest description (wrapped)
            List<String> wrappedDesc = wrapText(selectedQuest.getDescription(), detailsWidth - 10);
            for (String line : wrappedDesc) {
                if (textY + 10 > detailsY + detailsHeight - 5) break;
                graphics.drawString(this.font, "§7" + line, detailsX + 5, textY, 0xFFFFFF);
                textY += 10;
            }
            textY += 5;
            
            // Progress (for active quests)
            if (showingActive && selectedQuest.getMaxProgress() > 0) {
                graphics.drawString(this.font, "§eProgress:", detailsX + 5, textY, 0xFFFFFF);
                textY += 12;
                
                int progress = selectedQuest.getProgress();
                int maxProgress = selectedQuest.getMaxProgress();
                String progressText = "§7" + progress + "/" + maxProgress;
                graphics.drawString(this.font, progressText, detailsX + 8, textY, 0xFFFFFF);
                
                // Progress bar
                int barWidth = detailsWidth - 16;
                int barX = detailsX + 8;
                int barY = textY + 12;
                graphics.fill(barX, barY, barX + barWidth, barY + 6, 0xFF333333);
                int fillWidth = (int) ((float) progress / maxProgress * barWidth);
                graphics.fill(barX, barY, barX + fillWidth, barY + 6, 0xFF00AA00);
                
                textY += 20;
            }
            
            // Rewards
            if (selectedQuest.getRewardGold() > 0 || selectedQuest.getRewardReputation() > 0) {
                if (textY + 10 <= detailsY + detailsHeight - 5) {
                    graphics.drawString(this.font, "§6Rewards:", detailsX + 5, textY, 0xFFFFFF);
                    textY += 12;
                    
                    if (selectedQuest.getRewardGold() > 0) {
                        graphics.drawString(this.font, "§7• " + selectedQuest.getRewardGold() + " Gold", detailsX + 8, textY, 0xFFFFFF);
                        textY += 10;
                    }
                    
                    if (selectedQuest.getRewardReputation() > 0) {
                        String reputationReward = selectedQuest.getRewardReputation() + " Reputation";
                        if (selectedQuest.getRequiredFaction() != null) {
                            reputationReward += " (" + selectedQuest.getRequiredFaction().getDisplayName() + ")";
                        }
                        graphics.drawString(this.font, "§7• " + reputationReward, detailsX + 8, textY, 0xFFFFFF);
                        textY += 10;
                    }
                }
            }
        } else {
            String noQuestsMsg = showingActive ? "No active quests" : "No completed quests";
            graphics.drawCenteredString(this.font, "§7" + noQuestsMsg, detailsX + detailsWidth / 2, detailsY + detailsHeight / 2, 0xFFFFFF);
        }
        
        // Render buttons
        super.render(graphics, mouseX, mouseY, partialTick);
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        List<Quest> questsToShow = showingActive ? activeQuests : completedQuests;
        int maxScroll = Math.max(0, questsToShow.size() - 8);
        
        scrollOffset = (int) Math.max(0, Math.min(maxScroll, scrollOffset - scrollY));
        
        return true;
    }
    
    private List<String> wrapText(String text, int maxWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();
        
        for (String word : words) {
            String testLine = currentLine.length() == 0 ? word : currentLine + " " + word;
            if (this.font.width(testLine) <= maxWidth) {
                if (currentLine.length() > 0) currentLine.append(" ");
                currentLine.append(word);
            } else {
                if (currentLine.length() > 0) {
                    lines.add(currentLine.toString());
                    currentLine = new StringBuilder(word);
                } else {
                    lines.add(word);
                }
            }
        }
        
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }
        
        return lines;
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
