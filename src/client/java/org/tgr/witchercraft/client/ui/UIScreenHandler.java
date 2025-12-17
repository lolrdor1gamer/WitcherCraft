package org.tgr.witchercraft.client.ui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import org.tgr.witchercraft.client.screen.CharacterStatsScreen;
import org.tgr.witchercraft.client.screen.SkillTreeScreen;
import org.tgr.witchercraft.client.screen.QuestJournalScreen;

/**
 * Handles opening UI screens when keys are pressed
 */
@Environment(EnvType.CLIENT)
public class UIScreenHandler {

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.screen != null) {
                return;
            }

            // Character Screen
            while (UIKeyBindings.CharacterScreenKey.consumeClick()) {
                openCharacterScreen(client);
            }
            
            // Skill Tree Screen
            while (UIKeyBindings.SkillTreeKey.consumeClick()) {
                openSkillTreeScreen(client);
            }
            
            // Quest Journal Screen
            while (UIKeyBindings.QuestJournalKey.consumeClick()) {
                openQuestJournalScreen(client);
            }
        });
    }

    private static void openCharacterScreen(Minecraft client) {
        if (client.player == null) return;
        client.setScreen(new CharacterStatsScreen());
    }

    private static void openSkillTreeScreen(Minecraft client) {
        if (client.player == null) return;
        client.setScreen(new SkillTreeScreen());
    }

    private static void openQuestJournalScreen(Minecraft client) {
        if (client.player == null) return;
        client.setScreen(new QuestJournalScreen());
    }
}
