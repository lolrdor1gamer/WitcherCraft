package org.tgr.witchercraft.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.Biome;
import org.tgr.witchercraft.worldgen.region.ContinentRegion;
import org.tgr.witchercraft.worldgen.region.RegionProvider;

/**
 * Command to check current biome and region information
 */
public class BiomeInfoCommand {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("biomeinfo")
                .executes(BiomeInfoCommand::showBiomeInfo)
        );
    }
    
    private static int showBiomeInfo(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            source.sendFailure(Component.literal("Only players can use this command"));
            return 0;
        }
        
        BlockPos pos = player.blockPosition();
        
        // Get biome at position
        Biome biome = player.level().getBiome(pos).value();
        String biomeName = player.level().registryAccess()
            .lookupOrThrow(net.minecraft.core.registries.Registries.BIOME)
            .getResourceKey(biome)
            .map(key -> key.location().toString())
            .orElse("unknown");
        
        // Get region information
        RegionProvider regionProvider = new RegionProvider(player.level().getSeed());
        ContinentRegion region = regionProvider.getRegion(pos.getX(), pos.getZ());
        double temperature = regionProvider.getTemperature(pos.getX(), pos.getZ());
        double humidity = regionProvider.getHumidity(pos.getX(), pos.getZ());
        boolean isCoastal = regionProvider.isCoastal(pos.getX(), pos.getZ());
        
        // Send info to player
        source.sendSuccess(() -> Component.literal("=== Biome Information ==="), false);
        source.sendSuccess(() -> Component.literal("Position: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ()), false);
        source.sendSuccess(() -> Component.literal("Biome: " + biomeName), false);
        source.sendSuccess(() -> Component.literal("Region: " + region.getDisplayName()), false);
        source.sendSuccess(() -> Component.literal("Temperature: " + String.format("%.2f", temperature)), false);
        source.sendSuccess(() -> Component.literal("Humidity: " + String.format("%.2f", humidity)), false);
        source.sendSuccess(() -> Component.literal("Coastal: " + (isCoastal ? "Yes" : "No")), false);
        
        return 1;
    }
}
