package org.tgr.witchercraft.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.client.hud.WitcherStatsHudOverlay;
import org.tgr.witchercraft.client.quest.QuestBoardClientHandler;
import org.tgr.witchercraft.client.reputation.ReputationHUDBindings;
import org.tgr.witchercraft.client.reputation.ReputationopenHandler;
import org.tgr.witchercraft.client.screen.AlchemyTableScreen;
import org.tgr.witchercraft.client.screen.WitcherForgeScreen;
import org.tgr.witchercraft.client.render.entity.SimpleHumanoidRenderer;
import org.tgr.witchercraft.network.ClientNetworking;
import org.tgr.witchercraft.registry.ModMenus;
import org.tgr.witchercraft.registry.ModEntities;
import org.tgr.witchercraft.sign.SignCastingHandler;
import org.tgr.witchercraft.sign.SignKeyBindings;
import org.tgr.witchercraft.client.ui.UIKeyBindings;
import org.tgr.witchercraft.client.ui.UIScreenHandler;

public class WitchercraftClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        // Register sign casting handler (packet registration happens in main mod class)
        SignKeyBindings.register();
        SignCastingHandler.register();
        
        // Register UI keybindings and screen handler
        UIKeyBindings.register();
        UIScreenHandler.register();
        
        ReputationHUDBindings.register();
        ReputationopenHandler.register();
        ClientNetworking.initialize();
        WitcherStatsHudOverlay.register();
        
        // Initialize quest board client handler
        QuestBoardClientHandler.init();

        MenuScreens.register(ModMenus.WITCHER_FORGE_MENU, WitcherForgeScreen::new);
        MenuScreens.register(ModMenus.ALCHEMY_TABLE_MENU, AlchemyTableScreen::new);

        EntityRendererRegistry.register(ModEntities.KAEDWENI_SOLDIER,
            ctx -> new SimpleHumanoidRenderer<>(ctx, texture("humanoids/kaedweni_soldier")));
        EntityRendererRegistry.register(ModEntities.REDANIAN_GUARD,
            ctx -> new SimpleHumanoidRenderer<>(ctx, texture("humanoids/redanian_guard")));
        EntityRendererRegistry.register(ModEntities.NILFGAARDIAN_SCOUT,
            ctx -> new SimpleHumanoidRenderer<>(ctx, texture("humanoids/nilfgaardian_scout")));
    }

    private static ResourceLocation texture(String path) {
        return ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "textures/entity/" + path + ".png");
    }
}
