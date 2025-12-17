package org.tgr.witchercraft.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.tgr.witchercraft.player.*;

/**
 * Commands for testing and managing Witcher systems
 */
public class WitcherCommands {
    
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            registerLevelCommands(dispatcher);
            registerAttributeCommands(dispatcher);
            registerSkillCommands(dispatcher);
        });
    }
    
    private static void registerLevelCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("witcher")
            .then(Commands.literal("level")
                .then(Commands.literal("check")
                    .executes(WitcherCommands::checkLevel))
                .then(Commands.literal("addxp")
                    .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                        .executes(WitcherCommands::addXP)))
                .then(Commands.literal("set")
                    .requires(source -> source.hasPermission(2))
                    .then(Commands.argument("level", IntegerArgumentType.integer(1, 70))
                        .executes(WitcherCommands::setLevel)))));
    }
    
    private static void registerAttributeCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("witcher")
            .then(Commands.literal("attributes")
                .then(Commands.literal("check")
                    .executes(WitcherCommands::checkAttributes))
                .then(Commands.literal("add")
                    .then(Commands.argument("attribute", StringArgumentType.word())
                        .suggests((context, builder) -> {
                            builder.suggest("strength");
                            builder.suggest("vitality");
                            builder.suggest("combat");
                            builder.suggest("signs");
                            builder.suggest("alchemy");
                            return builder.buildFuture();
                        })
                        .then(Commands.argument("points", IntegerArgumentType.integer(1))
                            .executes(WitcherCommands::addAttribute))))));
    }
    
    private static void registerSkillCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("witcher")
            .then(Commands.literal("skills")
                .then(Commands.literal("check")
                    .executes(WitcherCommands::checkSkills))
                .then(Commands.literal("learn")
                    .then(Commands.argument("skill", StringArgumentType.string())
                        .executes(WitcherCommands::learnSkill)))
                .then(Commands.literal("list")
                    .executes(WitcherCommands::listSkills))));
    }
    
    // Level commands
    private static int checkLevel(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return 0;
        
        ServerLevel level = (ServerLevel) player.level();
        WitcherPlayerProgress progress = WitcherPlayerProgress.get(level);
        PlayerLevel playerLevel = progress.getLevel(player);
        
        player.sendSystemMessage(Component.literal("§6=== Witcher Level ==="));
        player.sendSystemMessage(Component.literal("§eLevel: §f" + playerLevel.getLevel()));
        player.sendSystemMessage(Component.literal("§eXP: §f" + playerLevel.getExperience() + " / " + playerLevel.getXpForNextLevel()));
        player.sendSystemMessage(Component.literal("§eAttribute Points: §a" + playerLevel.getAttributePoints()));
        player.sendSystemMessage(Component.literal("§eSkill Points: §a" + playerLevel.getSkillPoints()));
        
        return 1;
    }
    
    private static int addXP(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return 0;
        
        int amount = IntegerArgumentType.getInteger(context, "amount");
        ServerLevel level = (ServerLevel) player.level();
        WitcherPlayerProgress progress = WitcherPlayerProgress.get(level);
        PlayerLevel playerLevel = progress.getLevel(player);
        
        int oldLevel = playerLevel.getLevel();
        playerLevel.addExperience(player, amount);
        int newLevel = playerLevel.getLevel();
        
        player.sendSystemMessage(Component.literal("§a+§e" + amount + " §aXP"));
        
        if (newLevel > oldLevel) {
            player.sendSystemMessage(Component.literal("§6§l⚔ LEVEL UP! §r§6Level " + newLevel));
            player.sendSystemMessage(Component.literal("§a+5 Attribute Points"));
            player.sendSystemMessage(Component.literal("§a+1 Skill Point"));
        }
        
        return 1;
    }
    
    private static int setLevel(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return 0;
        
        int targetLevel = IntegerArgumentType.getInteger(context, "level");
        ServerLevel level = (ServerLevel) player.level();
        WitcherPlayerProgress progress = WitcherPlayerProgress.get(level);
        PlayerLevel playerLevel = progress.getLevel(player);
        
        // Just set XP to reach the target level (simplified)
        int totalXP = 0;
        for (int i = 1; i < targetLevel; i++) {
            playerLevel.addExperience(player, playerLevel.getXpForNextLevel());
        }
        
        player.sendSystemMessage(Component.literal("§6Level set to " + targetLevel));
        
        return 1;
    }
    
    // Attribute commands
    private static int checkAttributes(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return 0;
        
        ServerLevel level = (ServerLevel) player.level();
        WitcherPlayerProgress progress = WitcherPlayerProgress.get(level);
        PlayerAttributes attrs = progress.getAttributes(player);
        
        player.sendSystemMessage(Component.literal("§6=== Attributes ==="));
        player.sendSystemMessage(Component.literal("§cStrength: §f" + attrs.getStrength() + " §7(+" + String.format("%.0f", (attrs.getDamageBonus() - 1) * 100) + "% damage)"));
        player.sendSystemMessage(Component.literal("§cVitality: §f" + attrs.getVitality() + " §7(+" + attrs.getHealthBonus() + " HP)"));
        player.sendSystemMessage(Component.literal("§cCombat: §f" + attrs.getCombat() + " §7(" + String.format("%.1f", attrs.getCriticalChance() * 100) + "% crit)"));
        player.sendSystemMessage(Component.literal("§9Signs: §f" + attrs.getSigns() + " §7(+" + String.format("%.0f", (attrs.getSignIntensity() - 1) * 100) + "% power)"));
        player.sendSystemMessage(Component.literal("§2Alchemy: §f" + attrs.getAlchemy() + " §7(+" + String.format("%.0f", (attrs.getPotionDurationBonus() - 1) * 100) + "% duration)"));
        
        return 1;
    }
    
    private static int addAttribute(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return 0;
        
        String attribute = StringArgumentType.getString(context, "attribute").toLowerCase();
        int points = IntegerArgumentType.getInteger(context, "points");
        
        ServerLevel level = (ServerLevel) player.level();
        WitcherPlayerProgress progress = WitcherPlayerProgress.get(level);
        PlayerLevel playerLevel = progress.getLevel(player);
        PlayerAttributes attrs = progress.getAttributes(player);
        
        if (playerLevel.getAttributePoints() < points) {
            player.sendSystemMessage(Component.literal("§cNot enough attribute points! (Have: " + playerLevel.getAttributePoints() + ")"));
            return 0;
        }
        
        // Apply attribute points
        for (int i = 0; i < points; i++) {
            switch (attribute) {
                case "strength" -> attrs.increaseStrength();
                case "vitality" -> attrs.increaseVitality();
                case "combat" -> attrs.increaseCombat();
                case "signs" -> attrs.increaseSigns();
                case "alchemy" -> attrs.increaseAlchemy();
                default -> {
                    player.sendSystemMessage(Component.literal("§cInvalid attribute! Use: strength, vitality, combat, signs, or alchemy"));
                    return 0;
                }
            }
            playerLevel.spendAttributePoint();
        }
        
        player.sendSystemMessage(Component.literal("§a+§e" + points + " §a" + attribute.toUpperCase()));
        return 1;
    }
    
    // Skill commands
    private static int checkSkills(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return 0;
        
        ServerLevel level = (ServerLevel) player.level();
        WitcherPlayerProgress progress = WitcherPlayerProgress.get(level);
        PlayerSkills skills = progress.getSkills(player);
        
        player.sendSystemMessage(Component.literal("§6=== Learned Skills ==="));
        
        for (String skillId : skills.getLearnedSkills().keySet()) {
            int rank = skills.getSkillRank(skillId);
            String active = skills.isSkillActive(skillId) ? " §a[ACTIVE]" : "";
            player.sendSystemMessage(Component.literal("§e" + skillId + " §7(Rank " + rank + ")" + active));
        }
        
        return 1;
    }
    
    private static int learnSkill(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return 0;
        
        String skillId = StringArgumentType.getString(context, "skill");
        
        ServerLevel level = (ServerLevel) player.level();
        WitcherPlayerProgress progress = WitcherPlayerProgress.get(level);
        PlayerLevel playerLevel = progress.getLevel(player);
        PlayerSkills skills = progress.getSkills(player);
        
        if (playerLevel.getSkillPoints() < 1) {
            player.sendSystemMessage(Component.literal("§cNot enough skill points!"));
            return 0;
        }
        
        if (skills.canLearnSkill(skillId, playerLevel.getLevel())) {
            skills.learnSkill(skillId);
            playerLevel.spendSkillPoint();
            player.sendSystemMessage(Component.literal("§aLearned skill: §e" + skillId));
            return 1;
        } else {
            player.sendSystemMessage(Component.literal("§cCannot learn this skill! (Missing prerequisites, max rank, or level too low)"));
            return 0;
        }
    }
    
    private static int listSkills(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return 0;
        
        player.sendSystemMessage(Component.literal("§6=== Available Skills ==="));
        player.sendSystemMessage(Component.literal("§cCombat: §ffast_attack, strong_attack, whirl, fleet_footed"));
        player.sendSystemMessage(Component.literal("§9Signs: §faard_sweep, igni_intensity, quen_shield, yrden_range, axii_duration"));
        player.sendSystemMessage(Component.literal("§2Alchemy: §fpoisoned_blades, synergy, toxicity_tolerance"));
        
        return 1;
    }
}
