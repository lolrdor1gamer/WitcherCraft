package org.tgr.witchercraft;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tgr.witchercraft.alchemy.WitcherPotionRecipes;
import org.tgr.witchercraft.bestiary.BestiaryEntries;
import org.tgr.witchercraft.command.BiomeInfoCommand;
import org.tgr.witchercraft.command.ContinentCommand;
import org.tgr.witchercraft.command.ReputationCommand;
import org.tgr.witchercraft.command.WitcherCommands;
import org.tgr.witchercraft.faction.FactionReputation;
import org.tgr.witchercraft.player.WitcherPlayerData;

public class Witchercraft implements ModInitializer {
    public static final String MODID = "witchercraft";
    public static final String MOD_ID = MODID; // Alias for consistency
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
    org.tgr.witchercraft.registry.ModEntities.initialize();
        org.tgr.witchercraft.item.WitcherSwordHandler.register();
        
        // Register network packets
        org.tgr.witchercraft.network.CastSignPacket.register();
        org.tgr.witchercraft.network.RequestPlayerDataPacket.register();
        org.tgr.witchercraft.network.SyncPlayerDataPacket.register();
        org.tgr.witchercraft.network.SyncWitcherStatsPayload.register();
        org.tgr.witchercraft.network.MeditationRequestPacket.register();
        org.tgr.witchercraft.network.DodgeRequestPacket.register();
        WitcherPotionRecipes.register();
        
        // Initialize combat system
        org.tgr.witchercraft.combat.CombatEventHandler.register();
        
        // Initialize gear set bonuses
        org.tgr.witchercraft.gear.SetBonusHandler.register();
        
        // Initialize bestiary entries
        BestiaryEntries.initialize();

        // Register commands
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            ReputationCommand.register(dispatcher);
            BiomeInfoCommand.register(dispatcher);
            ContinentCommand.register(dispatcher);
        });
        WitcherCommands.register();
        
        // Initialize world generation
        org.tgr.witchercraft.worldgen.WitcherWorldGen.initialize();
        
        // Initialize biome holder cache when server starts
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            org.tgr.witchercraft.worldgen.biome.BiomeHolderCache.initialize(server);
            LOGGER.info("Initialized biome holder cache");
        });

        registerPlayerSystems();
        
        LOGGER.info("WitcherCraft initialized successfully!");
    }

    private void registerPlayerSystems() {
        ServerTickEvents.END_SERVER_TICK.register(server ->
            server.getPlayerList().getPlayers().forEach(WitcherPlayerData::tick)
        );

        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            WitcherPlayerData.copy(oldPlayer, newPlayer);
            FactionReputation.copy(oldPlayer, newPlayer);
            
            // Update health based on Vitality attribute after respawn
            org.tgr.witchercraft.progression.AttributeHealthModifier.updatePlayerHealth(newPlayer);
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            WitcherPlayerData.remove(handler.player);
            FactionReputation.remove(handler.player);
        });
        
        // Update health when player first joins
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            org.tgr.witchercraft.progression.AttributeHealthModifier.updatePlayerHealth(handler.player);
        });
    }
}
