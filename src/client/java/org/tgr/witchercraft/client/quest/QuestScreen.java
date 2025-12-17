package org.tgr.witchercraft.client.quest;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.quest.Quest;
import org.tgr.witchercraft.quest.QuestManager;
import org.tgr.witchercraft.quest.QuestStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Quest screen with alchemy table aesthetic.
 * Shows active and completed quests only.
 */
public class QuestScreen extends Screen {
    
    private static final ResourceLocation TEXTURE =
        ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "textures/gui/quest.png");
    
    private static final int PANEL_WIDTH = 176;
    private static final int PANEL_HEIGHT = 200;
    private static final int ENTRY_HEIGHT = 20;
    private static final int ENTRY_SPACING = 3;
    private static final int ENTRY_START_Y = 35;
    
    private int panelX;
    private int panelY;
    private final List<Quest> activeQuests = new ArrayList<>();
    private final List<Quest> completedQuests = new ArrayList<>();
    
    private QuestTab currentTab = QuestTab.ACTIVE;
    
    public QuestScreen() {
        super(Component.literal("Quests"));
    }

    @Override
    protected void init() {
        super.init();
        
        // Center the panel
        this.panelX = (this.width - PANEL_WIDTH) / 2;
        this.panelY = (this.height - PANEL_HEIGHT) / 2;
        
        // Load quests
        if (minecraft != null && minecraft.player != null) {
            activeQuests.clear();
            activeQuests.addAll(QuestManager.getActiveQuests(minecraft.player));
            
            completedQuests.clear();
            completedQuests.addAll(QuestManager.getCompletedQuests(minecraft.player));
        }
        
        // Tab buttons
        int tabY = panelY + 8;
        addRenderableWidget(Button.builder(
            Component.literal("Active"),
            btn -> switchTab(QuestTab.ACTIVE)
        ).bounds(panelX + 10, tabY, 60, 18).build());
        
        addRenderableWidget(Button.builder(
            Component.literal("Completed"),
            btn -> switchTab(QuestTab.COMPLETED)
        ).bounds(panelX + 75, tabY, 60, 18).build());
        
        // Quest buttons - show quest names
        List<Quest> quests = getCurrentQuestList();
        for (int i = 0; i < Math.min(quests.size(), 8); i++) {
            final Quest quest = quests.get(i);
            int entryY = panelY + ENTRY_START_Y + i * (ENTRY_HEIGHT + ENTRY_SPACING);
            int entryX = panelX + 10;
            int entryWidth = PANEL_WIDTH - 20;
            
            // Color based on status
            int color = quest.getStatus() == QuestStatus.COMPLETED ? 0x00AA00 : 0xFFD700;
            
            addRenderableWidget(Button.builder(
                Component.literal(quest.getTitle()).withColor(color),
                btn -> {
                    // Quest interaction could go here (track, abandon, etc.)
                }
            ).bounds(entryX, entryY, entryWidth, ENTRY_HEIGHT).build());
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        // Render translucent background
        graphics.fill(0, 0, this.width, this.height, 0xC0101010);
        
        // Render the GUI texture background
        graphics.blit(
            RenderPipelines.GUI_TEXTURED,
            TEXTURE,
            panelX, panelY,
            0, 0,
            PANEL_WIDTH, PANEL_HEIGHT,
            256, 256
        );
        
        super.render(graphics, mouseX, mouseY, delta);
        
        // Instructions
        Component instructions = Component.literal("Press ESC or P to close").withColor(0x888888);
        int instrWidth = this.font.width(instructions);
        graphics.drawString(this.font, instructions, panelX + (PANEL_WIDTH - instrWidth) / 2, 
                          panelY + PANEL_HEIGHT - 12, 0x888888, false);
    }

    private void switchTab(QuestTab tab) {
        currentTab = tab;
        
        // Rebuild UI
        clearWidgets();
        init();
    }

    private List<Quest> getCurrentQuestList() {
        return currentTab == QuestTab.ACTIVE ? activeQuests : completedQuests;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private enum QuestTab {
        ACTIVE,
        COMPLETED
    }
}
