package org.tgr.witchercraft.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.tgr.witchercraft.faction.Faction;
import org.tgr.witchercraft.faction.FactionReputation;
import org.tgr.witchercraft.faction.ReputationLevel;

import java.util.Arrays;

/**
 * Debug command to modify faction reputation for testing.
 * Usage: /reputation <faction> <amount> [player]
 */
public class ReputationCommand {

    private static final DynamicCommandExceptionType INVALID_FACTION = new DynamicCommandExceptionType(
        name -> Component.literal("Unknown faction: " + name)
    );

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("reputation")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("get")
                    .then(Commands.argument("faction", StringArgumentType.word())
                        .suggests((ctx, builder) -> SharedSuggestionProvider.suggest(
                            Arrays.stream(Faction.values()).map(f -> f.name().toLowerCase()), builder))
                        .executes(ctx -> getReputation(ctx, ctx.getSource().getPlayerOrException()))
                        .then(Commands.argument("player", EntityArgument.player())
                            .executes(ctx -> getReputation(ctx, EntityArgument.getPlayer(ctx, "player")))
                        )
                    )
                )
                .then(Commands.literal("set")
                    .then(Commands.argument("faction", StringArgumentType.word())
                        .suggests((ctx, builder) -> SharedSuggestionProvider.suggest(
                            Arrays.stream(Faction.values()).map(f -> f.name().toLowerCase()), builder))
                        .then(Commands.argument("amount", IntegerArgumentType.integer(-2000, 5000))
                            .executes(ctx -> setReputation(ctx, ctx.getSource().getPlayerOrException()))
                            .then(Commands.argument("player", EntityArgument.player())
                                .executes(ctx -> setReputation(ctx, EntityArgument.getPlayer(ctx, "player")))
                            )
                        )
                    )
                )
                .then(Commands.literal("add")
                    .then(Commands.argument("faction", StringArgumentType.word())
                        .suggests((ctx, builder) -> SharedSuggestionProvider.suggest(
                            Arrays.stream(Faction.values()).map(f -> f.name().toLowerCase()), builder))
                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                            .executes(ctx -> addReputation(ctx, ctx.getSource().getPlayerOrException()))
                            .then(Commands.argument("player", EntityArgument.player())
                                .executes(ctx -> addReputation(ctx, EntityArgument.getPlayer(ctx, "player")))
                            )
                        )
                    )
                )
        );
    }

    private static Faction getFaction(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        String name = StringArgumentType.getString(ctx, "faction").toUpperCase();
        try {
            return Faction.valueOf(name);
        } catch (IllegalArgumentException e) {
            throw INVALID_FACTION.create(name);
        }
    }

    private static int getReputation(CommandContext<CommandSourceStack> ctx, ServerPlayer player) throws CommandSyntaxException {
        Faction faction = getFaction(ctx);
        int score = FactionReputation.getReputation(player, faction);
        ReputationLevel level = ReputationLevel.fromScore(score);
        
        ctx.getSource().sendSuccess(
            () -> Component.literal(player.getName().getString() + "'s reputation with " 
                + faction.getDisplayName() + ": " + score + " (" + level.getDisplayName() + ")"),
            false
        );
        return score;
    }

    private static int setReputation(CommandContext<CommandSourceStack> ctx, ServerPlayer player) throws CommandSyntaxException {
        Faction faction = getFaction(ctx);
        int amount = IntegerArgumentType.getInteger(ctx, "amount");
        
        FactionReputation.setReputation(player, faction, amount);
        ReputationLevel level = ReputationLevel.fromScore(amount);
        
        ctx.getSource().sendSuccess(
            () -> Component.literal("Set " + player.getName().getString() + "'s reputation with " 
                + faction.getDisplayName() + " to " + amount + " (" + level.getDisplayName() + ")"),
            true
        );
        return amount;
    }

    private static int addReputation(CommandContext<CommandSourceStack> ctx, ServerPlayer player) throws CommandSyntaxException {
        Faction faction = getFaction(ctx);
        int amount = IntegerArgumentType.getInteger(ctx, "amount");
        
        int oldScore = FactionReputation.getReputation(player, faction);
        FactionReputation.modifyReputation(player, faction, amount);
        int newScore = FactionReputation.getReputation(player, faction);
        ReputationLevel level = ReputationLevel.fromScore(newScore);
        
        ctx.getSource().sendSuccess(
            () -> Component.literal("Modified " + player.getName().getString() + "'s reputation with " 
                + faction.getDisplayName() + " by " + amount + " (" + oldScore + " → " + newScore + ", " + level.getDisplayName() + ")"),
            true
        );
        return newScore;
    }
}
