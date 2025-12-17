package org.tgr.witchercraft.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.tgr.witchercraft.client.player.ClientPlayerData;
import org.tgr.witchercraft.network.ClientNetworking;
import org.tgr.witchercraft.player.PlayerAttributes;
import org.tgr.witchercraft.player.PlayerLevel;

/**
 * Character stats screen showing level, XP, attributes, and allowing point spending
 */
public class CharacterStatsScreen extends Screen {
    
    private static final int PANEL_WIDTH = 280;
    private static final int PANEL_HEIGHT = 220;
    
    private PlayerLevel playerLevel;
    private PlayerAttributes attributes;
    
    private int panelX;
    private int panelY;
    
    // Text widgets
    private StringWidget levelWidget;
    private StringWidget xpWidget;
    private StringWidget attributePointsWidget;
    private StringWidget skillPointsWidget;
    private StringWidget strengthWidget;
    private StringWidget vitalityWidget;
    private StringWidget combatWidget;
    private StringWidget signsWidget;
    private StringWidget alchemyWidget;
    
    public CharacterStatsScreen() {
        super(Component.literal("Character"));
    }
    
    @Override
    protected void init() {
        super.init();
        
        // Request data from server
        ClientNetworking.requestPlayerData();
        
        // Use client-side synced data
        this.playerLevel = ClientPlayerData.getLevel();
        this.attributes = ClientPlayerData.getAttributes();
        
        panelX = (this.width - PANEL_WIDTH) / 2;
        panelY = (this.height - PANEL_HEIGHT) / 2;
        
        // Create text widgets
        int textX = panelX + 20;
        int textY = panelY + 30;
        int textWidth = PANEL_WIDTH - 40;
        
        // Level
        levelWidget = new StringWidget(textX, textY, textWidth, 10, 
            Component.literal("§eLevel: §f" + playerLevel.getLevel()), this.font);
        addRenderableWidget(levelWidget);
        
        // XP (with progress bar info)
        xpWidget = new StringWidget(textX, textY + 20, textWidth, 10,
            Component.literal("§eXP: §f" + playerLevel.getExperience() + " / " + playerLevel.getXpForNextLevel()), this.font);
        addRenderableWidget(xpWidget);
        
        // Attribute Points
        attributePointsWidget = new StringWidget(textX, textY + 40, textWidth, 10,
            Component.literal("§aAttribute Points: §f" + playerLevel.getAttributePoints()), this.font);
        addRenderableWidget(attributePointsWidget);
        
        // Skill Points
        skillPointsWidget = new StringWidget(textX, textY + 50, textWidth, 10,
            Component.literal("§aSkill Points: §f" + playerLevel.getSkillPoints()), this.font);
        addRenderableWidget(skillPointsWidget);
        
        // Attributes section
        int attrY = textY + 70;
        int attrSpacing = 25;
        
        strengthWidget = new StringWidget(textX, attrY, textWidth - 30, 10,
            Component.literal("§cStrength: §f" + attributes.getStrength()), this.font);
        addRenderableWidget(strengthWidget);
        
        vitalityWidget = new StringWidget(textX, attrY + attrSpacing, textWidth - 30, 10,
            Component.literal("§4Vitality: §f" + attributes.getVitality()), this.font);
        addRenderableWidget(vitalityWidget);
        
        combatWidget = new StringWidget(textX, attrY + attrSpacing * 2, textWidth - 30, 10,
            Component.literal("§6Combat: §f" + attributes.getCombat()), this.font);
        addRenderableWidget(combatWidget);
        
        signsWidget = new StringWidget(textX, attrY + attrSpacing * 3, textWidth - 30, 10,
            Component.literal("§9Signs: §f" + attributes.getSigns()), this.font);
        addRenderableWidget(signsWidget);
        
        alchemyWidget = new StringWidget(textX, attrY + attrSpacing * 4, textWidth - 30, 10,
            Component.literal("§2Alchemy: §f" + attributes.getAlchemy()), this.font);
        addRenderableWidget(alchemyWidget);
        
        int buttonY = panelY + 90;
        int buttonSpacing = 25;
        
        // Attribute increase buttons
        this.addRenderableWidget(Button.builder(
            Component.literal("+"),
            btn -> increaseAttribute("strength"))
            .bounds(panelX + 240, buttonY, 20, 20)
            .build());
        
        this.addRenderableWidget(Button.builder(
            Component.literal("+"),
            btn -> increaseAttribute("vitality"))
            .bounds(panelX + 240, buttonY + buttonSpacing, 20, 20)
            .build());
        
        this.addRenderableWidget(Button.builder(
            Component.literal("+"),
            btn -> increaseAttribute("combat"))
            .bounds(panelX + 240, buttonY + buttonSpacing * 2, 20, 20)
            .build());
        
        this.addRenderableWidget(Button.builder(
            Component.literal("+"),
            btn -> increaseAttribute("signs"))
            .bounds(panelX + 240, buttonY + buttonSpacing * 3, 20, 20)
            .build());
        
        this.addRenderableWidget(Button.builder(
            Component.literal("+"),
            btn -> increaseAttribute("alchemy"))
            .bounds(panelX + 240, buttonY + buttonSpacing * 4, 20, 20)
            .build());
        
        // Close button
        this.addRenderableWidget(Button.builder(
            Component.literal("Close"),
            btn -> this.onClose())
            .bounds(panelX + PANEL_WIDTH / 2 - 40, panelY + PANEL_HEIGHT - 30, 80, 20)
            .build());
    }
    
    private void increaseAttribute(String attr) {
        if (playerLevel.getAttributePoints() <= 0) {
            return;
        }
        
        switch (attr) {
            case "strength" -> attributes.increaseStrength();
            case "vitality" -> attributes.increaseVitality();
            case "combat" -> attributes.increaseCombat();
            case "signs" -> attributes.increaseSigns();
            case "alchemy" -> attributes.increaseAlchemy();
        }
        
        playerLevel.spendAttributePoint();
        
        // Update widgets
        updateWidgets();
    }
    
    private void updateWidgets() {
        levelWidget.setMessage(Component.literal("§eLevel: §f" + playerLevel.getLevel()));
        xpWidget.setMessage(Component.literal("§eXP: §f" + playerLevel.getExperience() + " / " + playerLevel.getXpForNextLevel()));
        attributePointsWidget.setMessage(Component.literal("§aAttribute Points: §f" + playerLevel.getAttributePoints()));
        skillPointsWidget.setMessage(Component.literal("§aSkill Points: §f" + playerLevel.getSkillPoints()));
        strengthWidget.setMessage(Component.literal("§cStrength: §f" + attributes.getStrength()));
        vitalityWidget.setMessage(Component.literal("§4Vitality: §f" + attributes.getVitality()));
        combatWidget.setMessage(Component.literal("§6Combat: §f" + attributes.getCombat()));
        signsWidget.setMessage(Component.literal("§9Signs: §f" + attributes.getSigns()));
        alchemyWidget.setMessage(Component.literal("§2Alchemy: §f" + attributes.getAlchemy()));
    }
    
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        // Dark background
        graphics.fill(0, 0, this.width, this.height, 0xAA000000);
        
        // Main panel background
        graphics.fill(panelX, panelY, panelX + PANEL_WIDTH, panelY + PANEL_HEIGHT, 0xFF2C2C2C);
        
        // Title
        graphics.drawCenteredString(this.font, "§6§lCHARACTER", 
            this.width / 2, panelY + 10, 0xFFFFFF);
        
        // XP progress bar
        int barWidth = 240;
        int barHeight = 10;
        int barX = panelX + 20;
        int barY = panelY + 50;
        float progress = playerLevel.getXpProgress();
        
        // XP bar background
        graphics.fill(barX, barY, barX + barWidth, barY + barHeight, 0xFF555555);
        // XP bar fill
        graphics.fill(barX, barY, barX + (int)(barWidth * progress), barY + barHeight, 0xFF00AA00);
        
        // Render widgets and buttons
        super.render(graphics, mouseX, mouseY, partialTick);
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
