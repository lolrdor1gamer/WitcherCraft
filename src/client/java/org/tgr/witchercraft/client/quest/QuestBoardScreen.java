package org.tgr.witchercraft.client.quest;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.faction.Faction;
import org.tgr.witchercraft.quest.Quest;
import org.tgr.witchercraft.quest.QuestManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Quest board screen showing available quests for a specific faction.
 * Click a quest to view details, then click Accept to start it.
 */
public class QuestBoardScreen extends Screen {
    
    private static final ResourceLocation TEXTURE =
        ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "textures/gui/quest_board.png");
    
    private static final int PANEL_WIDTH = 256;
    private static final int PANEL_HEIGHT = 220;
    private static final int LIST_WIDTH = 120;
    private static final int DETAIL_WIDTH = 130;
    private static final int ENTRY_HEIGHT = 18;
    private static final int ENTRY_SPACING = 2;
    private static final int ENTRY_START_Y = 35;
    
    private final Faction faction;
    private int panelX;
    private int panelY;
    private final List<Quest> availableQuests = new ArrayList<>();
    private Quest selectedQuest = null;
    private Button acceptButton = null;
    
    // Quest detail widgets
    private StringWidget questTitleWidget;
    private StringWidget questTypeLevelWidget;
    private StringWidget descLabelWidget;
    private final List<StringWidget> descriptionWidgets = new ArrayList<>();
    private StringWidget goldRewardWidget;
    private StringWidget repRewardWidget;
    
    public QuestBoardScreen(Faction faction) {
        super(Component.literal(faction.getDisplayName() + " Quest Board"));
        this.faction = faction;
    }

    @Override
    protected void init() {
        super.init();
        
        this.panelX = (this.width - PANEL_WIDTH) / 2;
        this.panelY = (this.height - PANEL_HEIGHT) / 2;
        
        // Load available quests for this faction
        availableQuests.clear();
        if (minecraft != null && minecraft.player != null) {
            List<Quest> allAvailable = QuestManager.getAvailableQuests(minecraft.player);
            
            // Filter quests for this faction or neutral quests
            for (Quest quest : allAvailable) {
                if (quest.getRequiredFaction() == null || quest.getRequiredFaction() == faction) {
                    availableQuests.add(quest);
                }
            }
        }
        
        // Create quest selection buttons (left panel)
        for (int i = 0; i < Math.min(availableQuests.size(), 8); i++) {
            final Quest quest = availableQuests.get(i);
            int entryY = panelY + ENTRY_START_Y + i * (ENTRY_HEIGHT + ENTRY_SPACING);
            int entryX = panelX + 8;
            
            // Truncate quest name if too long
            String questName = quest.getTitle();
            if (this.font.width(questName) > LIST_WIDTH - 10) {
                questName = this.font.plainSubstrByWidth(questName, LIST_WIDTH - 15) + "...";
            }
            
            addRenderableWidget(Button.builder(
                Component.literal(questName),
                btn -> selectQuest(quest)
            ).bounds(entryX, entryY, LIST_WIDTH - 4, ENTRY_HEIGHT).build());
        }
        
        // Accept button (right panel, shown when quest is selected)
        int acceptX = panelX + LIST_WIDTH + 15;
        int acceptY = panelY + PANEL_HEIGHT - 35;
        acceptButton = Button.builder(
            Component.literal("Accept Quest").withColor(0x00FF00),
            btn -> acceptQuest()
        ).bounds(acceptX, acceptY, DETAIL_WIDTH - 20, 20).build();
        
        acceptButton.visible = false;
        addRenderableWidget(acceptButton);
        
        // Initialize quest detail widgets (right panel)
        int detailX = panelX + LIST_WIDTH + 10;
        int detailY = panelY + 40;
        int detailWidth = DETAIL_WIDTH - 15;
        
        // Quest title
        questTitleWidget = new StringWidget(detailX, detailY, detailWidth, 10, Component.empty(), this.font);
        questTitleWidget.visible = false;
        addRenderableWidget(questTitleWidget);
        
        // Type and level
        questTypeLevelWidget = new StringWidget(detailX, detailY + 12, detailWidth, 10, Component.empty(), this.font);
        questTypeLevelWidget.visible = false;
        addRenderableWidget(questTypeLevelWidget);
        
        // Description label
        descLabelWidget = new StringWidget(detailX, detailY + 26, detailWidth, 10, Component.empty(), this.font);
        descLabelWidget.visible = false;
        addRenderableWidget(descLabelWidget);
        
        // Description lines (up to 5 lines)
        for (int i = 0; i < 5; i++) {
            StringWidget descLine = new StringWidget(detailX, detailY + 36 + (i * 9), detailWidth, 9, Component.empty(), this.font);
            descLine.visible = false;
            descriptionWidgets.add(descLine);
            addRenderableWidget(descLine);
        }
        
        // Gold reward
        goldRewardWidget = new StringWidget(detailX, detailY + 86, detailWidth, 10, Component.empty(), this.font);
        goldRewardWidget.visible = false;
        addRenderableWidget(goldRewardWidget);
        
        // Reputation reward
        repRewardWidget = new StringWidget(detailX, detailY + 96, detailWidth, 10, Component.empty(), this.font);
        repRewardWidget.visible = false;
        addRenderableWidget(repRewardWidget);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        // Render the GUI texture background FIRST
        graphics.blit(
            RenderPipelines.GUI_TEXTURED,
            TEXTURE,
            panelX, panelY,
            0, 0,
            PANEL_WIDTH, PANEL_HEIGHT,
            256, 256
        );

        // Draw vertical divider line between list and details
        int dividerX = panelX + LIST_WIDTH + 4;
        graphics.fill(dividerX, panelY + 30, dividerX + 1, panelY + PANEL_HEIGHT - 10, 0xFF8B6914);

        // Call super.render to render widgets and background
        super.render(graphics, mouseX, mouseY, delta);

        // Title with faction color - rendered AFTER super.render
        Component title = Component.literal(faction.getDisplayName() + " Quest Board");
        int titleWidth = this.font.width(title);
        graphics.drawString(this.font, title, 
            panelX + (PANEL_WIDTH - titleWidth) / 2, 
            panelY + 10, faction.getColor(), true);
        
        // Bottom instructions  
        String instructions = "Click a quest to view details";
        int instrWidth = this.font.width(instructions);
        graphics.drawString(this.font, instructions, 
            panelX + (PANEL_WIDTH - instrWidth) / 2, 
            panelY + PANEL_HEIGHT - 10, 0x666666, true);
        
        // Show instruction text when no quest selected
        if (selectedQuest == null) {
            String instruction = "Select a quest";
            int instrWidth2 = this.font.width(instruction);
            graphics.drawString(this.font, instruction,
                panelX + LIST_WIDTH + 10 + (DETAIL_WIDTH - instrWidth2 - 15) / 2,
                panelY + 60, 0xAAAAAA, true);
        }
    }

    private void selectQuest(Quest quest) {
        selectedQuest = quest;
        
        // Show accept button
        if (acceptButton != null) {
            acceptButton.visible = true;
        }
        
        // Update quest detail widgets
        if (quest != null) {
            int detailWidth = DETAIL_WIDTH - 15;
            
            // Quest title
            questTitleWidget.setMessage(Component.literal(quest.getTitle()).withColor(0xFFD700));
            questTitleWidget.visible = true;
            
            // Type and level
            questTypeLevelWidget.setMessage(Component.literal(quest.getType().getDisplayName() + " - Lv " + quest.getRecommendedLevel()).withColor(0xAAAAAA));
            questTypeLevelWidget.visible = true;
            
            // Description label
            descLabelWidget.setMessage(Component.literal("Description:").withColor(0xE5D2AD));
            descLabelWidget.visible = true;
            
            // Description lines (wrapped)
            List<String> descLines = wrapText(quest.getDescription(), detailWidth);
            for (int i = 0; i < descriptionWidgets.size(); i++) {
                StringWidget widget = descriptionWidgets.get(i);
                if (i < descLines.size()) {
                    widget.setMessage(Component.literal(descLines.get(i)).withColor(0xCCCCCC));
                    widget.visible = true;
                } else {
                    widget.visible = false;
                }
            }
            
            // Gold reward
            if (quest.getRewardGold() > 0) {
                goldRewardWidget.setMessage(Component.literal("⛃ " + quest.getRewardGold() + " crowns").withColor(0xFFD700));
                goldRewardWidget.visible = true;
            } else {
                goldRewardWidget.visible = false;
            }
            
            // Reputation reward
            if (quest.getRewardReputation() > 0) {
                repRewardWidget.setMessage(Component.literal("★ +" + quest.getRewardReputation() + " reputation").withColor(0x00FF00));
                repRewardWidget.visible = true;
            } else {
                repRewardWidget.visible = false;
            }
        } else {
            // Hide all widgets
            hideQuestDetails();
        }
    }
    
    private void hideQuestDetails() {
        questTitleWidget.visible = false;
        questTypeLevelWidget.visible = false;
        descLabelWidget.visible = false;
        for (StringWidget widget : descriptionWidgets) {
            widget.visible = false;
        }
        goldRewardWidget.visible = false;
        repRewardWidget.visible = false;
    }

    private void acceptQuest() {
        if (minecraft != null && minecraft.player != null && selectedQuest != null) {
            // Start the quest
            QuestManager.startQuest(minecraft.player, selectedQuest.getId());
            
            // Show confirmation message
            minecraft.player.displayClientMessage(
                Component.literal("Quest accepted: ").withColor(0x00FF00)
                    .append(Component.literal(selectedQuest.getTitle()).withColor(0xFFD700)),
                false
            );
            
            // Close the screen
            this.onClose();
        }
    }

    private List<String> wrapText(String text, int maxWidth) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();
        
        for (String word : words) {
            String testLine = currentLine.isEmpty() ? word : currentLine + " " + word;
            if (this.font.width(testLine) > maxWidth) {
                if (!currentLine.isEmpty()) {
                    lines.add(currentLine.toString());
                    currentLine = new StringBuilder(word);
                } else {
                    lines.add(word);
                }
            } else {
                currentLine = new StringBuilder(testLine);
            }
        }
        
        if (!currentLine.isEmpty()) {
            lines.add(currentLine.toString());
        }
        
        return lines;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
