package org.tgr.witchercraft.client.reputation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.tgr.witchercraft.client.quest.QuestScreen;
import org.tgr.witchercraft.client.screen.ReputationScreen;

@Environment(EnvType.CLIENT)
public class ReputationopenHandler {

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.screen != null) {
                return;
            }

            // Open reputation screen when the reputation key is pressed
            while (ReputationHUDBindings.ReputationKey.consumeClick()) {
                client.setScreen(new ReputationScreen());
            }
            
            // Open quest screen when the quest key is pressed
            while (ReputationHUDBindings.QuestsKey.consumeClick()) {
                client.setScreen(new QuestScreen());
            }
        });
    }
}
