package org.tgr.witchercraft.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.tgr.witchercraft.worldgen.dimension.WitcherDimensions;

/**
 * Command to teleport players to The Continent dimension
 */
public class ContinentCommand {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("continent")
                .requires(source -> source.hasPermission(2))
                .executes(ContinentCommand::teleportToContinent)
        );
    }
    
    private static int teleportToContinent(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            source.sendFailure(Component.literal("Only players can use this command"));
            return 0;
        }
        
        ServerLevel continentLevel = source.getServer().getLevel(WitcherDimensions.CONTINENT_LEVEL);
        
        if (continentLevel == null) {
            source.sendFailure(Component.literal("§cThe Continent dimension is not loaded!"));
            source.sendFailure(Component.literal("§eMake sure you:"));
            source.sendFailure(Component.literal("§e1. Ran datagen (gradlew runDatagen)"));
            source.sendFailure(Component.literal("§e2. Created a NEW world after datagen"));
            source.sendFailure(Component.literal("§e3. The dimension files exist in generated/data/"));
            return 0;
        }
        
        // Find a safe spawn location
        BlockPos spawnPos = new BlockPos(0, 100, 0);
        Vec3 destination = new Vec3(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5);
        
        // Teleport the player using correct 1.21.1 API
        player.teleportTo(continentLevel, destination.x, destination.y, destination.z, 
                         java.util.Set.of(), player.getYRot(), player.getXRot(), true);
        
        source.sendSuccess(() -> Component.literal("Welcome to The Continent!"), true);
        return 1;
    }
}
