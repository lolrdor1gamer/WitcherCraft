package org.tgr.witchercraft.client.quest;

import net.minecraft.client.Minecraft;
import org.tgr.witchercraft.block.QuestBoardBlock;
import org.tgr.witchercraft.faction.Faction;

/**
 * Client-side handler for opening quest board screens.
 * This class is only loaded on the client side.
 */
public class QuestBoardClientHandler {
    
    public static void init() {
        // Register the client-side opener with the block
        QuestBoardBlock.setClientOpener(QuestBoardClientHandler::openQuestBoard);
    }
    
    private static void openQuestBoard(Faction faction) {
        Minecraft.getInstance().setScreen(new QuestBoardScreen(faction));
    }
}
