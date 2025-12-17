package org.tgr.witchercraft.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.tgr.witchercraft.client.player.ClientPlayerData;
import org.tgr.witchercraft.network.ClientNetworking;
import org.tgr.witchercraft.player.PlayerLevel;
import org.tgr.witchercraft.player.PlayerSkills;
import org.tgr.witchercraft.player.Skill;

import java.util.ArrayList;
import java.util.List;

/**
 * Skill tree screen showing Combat, Signs, and Alchemy trees
 */
public class SkillTreeScreen extends Screen {
    
    private static final int PANEL_WIDTH = 320;
    private static final int PANEL_HEIGHT = 240;
    
    private PlayerLevel playerLevel;
    private PlayerSkills skills;
    
    private int panelX;
    private int panelY;
    
    private SkillTree currentTree = SkillTree.COMBAT;
    
    // Text widgets
    private StringWidget skillPointsWidget;
    private final List<StringWidget> skillNameWidgets = new ArrayList<>();
    private final List<StringWidget> skillRankWidgets = new ArrayList<>();
    private final List<Button> learnButtons = new ArrayList<>();
    
    private enum SkillTree {
        COMBAT("§cCombat", new String[]{"fast_attack", "strong_attack", "whirl", "rend", "fleet_footed", "muscle_memory", "razor_focus"}),
        SIGNS("§9Signs", new String[]{"aard_sweep", "igni_intensity", "quen_shield", "yrden_range", "axii_duration", "sign_stamina", "elemental_fury"}),
        ALCHEMY("§2Alchemy", new String[]{"poisoned_blades", "synergy", "toxicity_tolerance", "delayed_recovery", "refreshment", "killing_spree"});
        
        final String name;
        final String[] skillIds;
        
        SkillTree(String name, String[] skillIds) {
            this.name = name;
            this.skillIds = skillIds;
        }
    }
    
    public SkillTreeScreen() {
        super(Component.literal("Skills"));
    }
    
    @Override
    protected void init() {
        super.init();
        
        // Request data from server
        ClientNetworking.requestPlayerData();
        
        // Use client-side synced data
        this.playerLevel = ClientPlayerData.getLevel();
        this.skills = ClientPlayerData.getSkills();
        
        panelX = (this.width - PANEL_WIDTH) / 2;
        panelY = (this.height - PANEL_HEIGHT) / 2;
        
        // Skill points widget
        skillPointsWidget = new StringWidget(panelX + 10, panelY + 30, PANEL_WIDTH - 20, 10,
            Component.literal("§aSkill Points: §f" + playerLevel.getSkillPoints()), this.font);
        addRenderableWidget(skillPointsWidget);
        
        // Create skill widgets (7 skills max per tree)
        int skillY = panelY + 55;
        for (int i = 0; i < 7; i++) {
            int y = skillY + (i * 25);
            
            // Skill name widget
            StringWidget nameWidget = new StringWidget(panelX + 10, y, 200, 10,
                Component.empty(), this.font);
            nameWidget.visible = false;
            skillNameWidgets.add(nameWidget);
            addRenderableWidget(nameWidget);
            
            // Skill rank widget
            StringWidget rankWidget = new StringWidget(panelX + 215, y, 50, 10,
                Component.empty(), this.font);
            rankWidget.visible = false;
            skillRankWidgets.add(rankWidget);
            addRenderableWidget(rankWidget);
            
            // Learn button
            int finalI = i;
            Button learnBtn = Button.builder(
                Component.literal("Learn"),
                btn -> learnSkill(finalI))
                .bounds(panelX + 270, y - 2, 40, 14)
                .build();
            learnBtn.visible = false;
            learnButtons.add(learnBtn);
            addRenderableWidget(learnBtn);
        }
        
        // Update skill display
        updateSkillDisplay();
        
        // Tree selection buttons
        int tabY = panelY - 25;
        this.addRenderableWidget(Button.builder(
            Component.literal("Combat"),
            btn -> { currentTree = SkillTree.COMBAT; updateSkillDisplay(); })
            .bounds(panelX, tabY, 80, 20)
            .build());
        
        this.addRenderableWidget(Button.builder(
            Component.literal("Signs"),
            btn -> { currentTree = SkillTree.SIGNS; updateSkillDisplay(); })
            .bounds(panelX + 85, tabY, 80, 20)
            .build());
        
        this.addRenderableWidget(Button.builder(
            Component.literal("Alchemy"),
            btn -> { currentTree = SkillTree.ALCHEMY; updateSkillDisplay(); })
            .bounds(panelX + 170, tabY, 80, 20)
            .build());
        
        // Close button
        this.addRenderableWidget(Button.builder(
            Component.literal("Close"),
            btn -> this.onClose())
            .bounds(panelX + PANEL_WIDTH - 80, panelY + PANEL_HEIGHT - 30, 75, 20)
            .build());
    }
    
    private void updateSkillDisplay() {
        // Update skill points
        skillPointsWidget.setMessage(Component.literal("§aSkill Points: §f" + playerLevel.getSkillPoints()));
        
        // Update skill widgets
        String[] skillIds = currentTree.skillIds;
        for (int i = 0; i < skillNameWidgets.size(); i++) {
            if (i < skillIds.length) {
                String skillId = skillIds[i];
                Skill skill = Skill.getSkill(skillId);
                
                if (skill != null) {
                    int rank = skills.getSkillRank(skillId);
                    int maxRank = skill.getMaxRank();
                    boolean canLearn = skills.canLearnSkill(skillId, playerLevel.getLevel());
                    boolean isActive = skills.isSkillActive(skillId);
                    
                    // Skill name with color
                    String color = rank > 0 ? (isActive ? "§a" : "§2") : (canLearn ? "§7" : "§8");
                    skillNameWidgets.get(i).setMessage(Component.literal(color + skill.getName()));
                    skillNameWidgets.get(i).visible = true;
                    
                    // Skill rank
                    skillRankWidgets.get(i).setMessage(Component.literal("§7[" + rank + "/" + maxRank + "]"));
                    skillRankWidgets.get(i).visible = true;
                    
                    // Learn button
                    boolean canUpgrade = canLearn && playerLevel.getSkillPoints() > 0 && rank < maxRank;
                    learnButtons.get(i).visible = canUpgrade;
                    learnButtons.get(i).active = canUpgrade;
                } else {
                    skillNameWidgets.get(i).visible = false;
                    skillRankWidgets.get(i).visible = false;
                    learnButtons.get(i).visible = false;
                }
            } else {
                skillNameWidgets.get(i).visible = false;
                skillRankWidgets.get(i).visible = false;
                learnButtons.get(i).visible = false;
            }
        }
    }
    
    private void learnSkill(int index) {
        if (index < 0 || index >= currentTree.skillIds.length) return;
        
        String skillId = currentTree.skillIds[index];
        if (playerLevel.getSkillPoints() > 0 && 
            skills.canLearnSkill(skillId, playerLevel.getLevel())) {
            skills.learnSkill(skillId);
            playerLevel.spendSkillPoint();
            updateSkillDisplay();
        }
    }
    
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        // Dark background
        graphics.fill(0, 0, this.width, this.height, 0xAA000000);
        
        // Main panel
        graphics.fill(panelX, panelY, panelX + PANEL_WIDTH, panelY + PANEL_HEIGHT, 0xFF2C2C2C);
        
        // Title
        graphics.drawCenteredString(this.font, "§6§lSKILL TREE - " + currentTree.name, 
            this.width / 2, panelY + 10, 0xFFFFFF);
        
        // Render widgets and buttons
        super.render(graphics, mouseX, mouseY, partialTick);
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
