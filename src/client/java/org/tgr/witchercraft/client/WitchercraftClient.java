package org.tgr.witchercraft.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.client.hud.WitcherStatsHudOverlay;
import org.tgr.witchercraft.client.quest.QuestBoardClientHandler;
import org.tgr.witchercraft.client.reputation.ReputationHUDBindings;
import org.tgr.witchercraft.client.reputation.ReputationopenHandler;
import org.tgr.witchercraft.client.screen.AlchemyTableScreen;
import org.tgr.witchercraft.client.screen.WitcherForgeScreen;
import org.tgr.witchercraft.client.render.entity.SimpleHumanoidRenderer;
import org.tgr.witchercraft.client.render.entity.SimpleMonsterRenderer;
import org.tgr.witchercraft.client.senses.WitcherSensesHandler;
import org.tgr.witchercraft.client.senses.WitcherSensesRenderer;
import org.tgr.witchercraft.network.ClientNetworking;
import org.tgr.witchercraft.registry.ModMenus;
import org.tgr.witchercraft.registry.ModEntities;
import org.tgr.witchercraft.sign.SignCastingHandler;
import org.tgr.witchercraft.sign.SignKeyBindings;
import org.tgr.witchercraft.client.ui.UIKeyBindings;
import org.tgr.witchercraft.client.ui.UIScreenHandler;
import org.tgr.witchercraft.client.combat.DodgeHandler;

public class WitchercraftClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        // Register sign casting handler (packet registration happens in main mod class)
        SignKeyBindings.register();
        SignCastingHandler.register();
        
        // Register UI keybindings and screen handler
        UIKeyBindings.register();
        UIScreenHandler.register();
        
        // Register dodge handler
        DodgeHandler.register();
        
        ReputationHUDBindings.register();
        ReputationopenHandler.register();
        ClientNetworking.initialize();
        WitcherStatsHudOverlay.register();
        
        // Initialize quest board client handler
        QuestBoardClientHandler.init();
        
        // Register Witcher Senses tick handler
        ClientTickEvents.END_CLIENT_TICK.register(client -> WitcherSensesHandler.tick());
        
        // Register Witcher Senses HUD overlay (shows info when senses active)
        HudRenderCallback.EVENT.register((guiGraphics, tickCounter) -> {
            WitcherSensesRenderer.renderHudOverlay(guiGraphics, tickCounter.getGameTimeDeltaPartialTick(true));
        });

        MenuScreens.register(ModMenus.WITCHER_FORGE_MENU, WitcherForgeScreen::new);
        MenuScreens.register(ModMenus.ALCHEMY_TABLE_MENU, AlchemyTableScreen::new);

        EntityRendererRegistry.register(ModEntities.KAEDWENI_SOLDIER,
            ctx -> new SimpleHumanoidRenderer<>(ctx, texture("humanoids/kaedweni_soldier")));
        EntityRendererRegistry.register(ModEntities.REDANIAN_GUARD,
            ctx -> new SimpleHumanoidRenderer<>(ctx, texture("humanoids/redanian_guard")));
        EntityRendererRegistry.register(ModEntities.NILFGAARDIAN_SCOUT,
            ctx -> new SimpleHumanoidRenderer<>(ctx, texture("humanoids/nilfgaardian_scout")));

        // Monster renderers
        EntityRendererRegistry.register(ModEntities.DROWNED_CORPSE,
            ctx -> new SimpleMonsterRenderer<>(ctx, texture("monsters/drowner"), 1.0f));
        EntityRendererRegistry.register(ModEntities.NEKKER,
            ctx -> new SimpleMonsterRenderer<>(ctx, texture("monsters/nekker"), 0.7f));
        EntityRendererRegistry.register(ModEntities.GHOUL,
            ctx -> new SimpleMonsterRenderer<>(ctx, texture("monsters/ghoul"), 1.0f));
        EntityRendererRegistry.register(ModEntities.ALGHOUL,
            ctx -> new SimpleMonsterRenderer<>(ctx, texture("monsters/alghoul"), 1.1f));
        EntityRendererRegistry.register(ModEntities.WRAITH,
            ctx -> new SimpleMonsterRenderer<>(ctx, texture("monsters/wraith"), 1.1f));
        EntityRendererRegistry.register(ModEntities.WEREWOLF,
            ctx -> new SimpleMonsterRenderer<>(ctx, texture("monsters/werewolf"), 1.2f));
        
        // Projectile renderers
        EntityRendererRegistry.register(ModEntities.WITCHER_BOMB_PROJECTILE, ThrownItemRenderer::new);
    }

    private static ResourceLocation texture(String path) {
        return ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "textures/entity/" + path + ".png");
    }
}
