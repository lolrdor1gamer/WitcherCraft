package org.tgr.witchercraft;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tgr.witchercraft.alchemy.WitcherPotionRecipes;
import org.tgr.witchercraft.player.WitcherPlayerData;

public class Witchercraft implements ModInitializer {
    public static final String MODID = "witchercraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        LOGGER.info("WitcherCraft is initializing!");
        
        // Initialize item and block registries
        // Using fully qualified names to ensure proper class loading timing
        org.tgr.witchercraft.registry.ModItems.initialize();
        org.tgr.witchercraft.registry.ModBlocks.initialize();
        org.tgr.witchercraft.registry.ModBlockEntities.initialize();
        org.tgr.witchercraft.registry.ModMenus.initialize();
        org.tgr.witchercraft.item.WitcherSwordHandler.register();
        
        // Register network packets
        org.tgr.witchercraft.network.CastSignPacket.register();
        org.tgr.witchercraft.network.SyncWitcherStatsPayload.register();
        WitcherPotionRecipes.register();

        registerPlayerSystems();
        
        LOGGER.info("WitcherCraft initialized successfully!");
    }

    private void registerPlayerSystems() {
        ServerTickEvents.END_SERVER_TICK.register(server ->
            server.getPlayerList().getPlayers().forEach(WitcherPlayerData::tick)
        );

        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) ->
            WitcherPlayerData.copy(oldPlayer, newPlayer)
        );

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) ->
            WitcherPlayerData.remove(handler.player)
        );
    }
}
