package org.tgr.witchercraft.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import org.tgr.witchercraft.client.hud.WitcherStatsHudOverlay;
import org.tgr.witchercraft.client.screen.AlchemyTableScreen;
import org.tgr.witchercraft.client.screen.WitcherForgeScreen;
import org.tgr.witchercraft.network.ClientNetworking;
import org.tgr.witchercraft.registry.ModMenus;
import org.tgr.witchercraft.sign.SignCastingHandler;
import org.tgr.witchercraft.sign.SignKeyBindings;

public class WitchercraftClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        // Register sign casting handler (packet registration happens in main mod class)
        SignKeyBindings.register();
        SignCastingHandler.register();
        ClientNetworking.initialize();
        WitcherStatsHudOverlay.register();

        MenuScreens.register(ModMenus.WITCHER_FORGE_MENU, WitcherForgeScreen::new);
        MenuScreens.register(ModMenus.ALCHEMY_TABLE_MENU, AlchemyTableScreen::new);
    }
}
