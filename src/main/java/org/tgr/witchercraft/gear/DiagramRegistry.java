package org.tgr.witchercraft.gear;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.tgr.witchercraft.Witchercraft;

import java.util.*;

/**
 * Diagrams are crafting recipes for Witcher gear that must be found/unlocked.
 * Players can only craft gear once they have discovered the diagram.
 */
public class DiagramRegistry {
    
    private static final Map<ResourceLocation, Diagram> DIAGRAMS = new HashMap<>();
    
    public enum DiagramTier {
        BASIC("Basic", 0x808080, 1),
        ENHANCED("Enhanced", 0x00AA00, 2),
        SUPERIOR("Superior", 0x5555FF, 3),
        MASTERCRAFTED("Mastercrafted", 0xFFAA00, 4),
        GRANDMASTER("Grandmaster", 0xFF55FF, 5);
        
        public final String displayName;
        public final int color;
        public final int level;
        
        DiagramTier(String name, int color, int level) {
            this.displayName = name;
            this.color = color;
            this.level = level;
        }
    }
    
    public record Diagram(
        ResourceLocation id,
        String name,
        WitcherSetBonuses.WitcherSchool school,
        DiagramTier tier,
        String slotType, // "helmet", "chestplate", "leggings", "boots", "sword"
        List<DiagramIngredient> ingredients
    ) {
        public Component getDisplayName() {
            return Component.literal(tier.displayName + " " + name)
                .withStyle(style -> style.withColor(tier.color));
        }
    }
    
    public record DiagramIngredient(
        Item item,
        int count
    ) {}
    
    static {
        // Register Wolf School diagrams
        registerWolfDiagrams();
        
        // Register Cat School diagrams  
        registerCatDiagrams();
        
        // Register Bear School diagrams
        registerBearDiagrams();
        
        // Register Griffin School diagrams
        registerGriffinDiagrams();
        
        // Register Viper School diagrams
        registerViperDiagrams();
    }
    
    private static void registerWolfDiagrams() {
        // Basic Wolf gear
        register(new Diagram(
            loc("wolf_armor_basic"),
            "Wolf School Armor",
            WitcherSetBonuses.WitcherSchool.WOLF,
            DiagramTier.BASIC,
            "chestplate",
            List.of()
        ));
        
        register(new Diagram(
            loc("wolf_boots_basic"),
            "Wolf School Boots",
            WitcherSetBonuses.WitcherSchool.WOLF,
            DiagramTier.BASIC,
            "boots",
            List.of()
        ));
        
        register(new Diagram(
            loc("wolf_gauntlets_basic"),
            "Wolf School Gauntlets",
            WitcherSetBonuses.WitcherSchool.WOLF,
            DiagramTier.BASIC,
            "leggings",
            List.of()
        ));
        
        register(new Diagram(
            loc("wolf_steel_sword_basic"),
            "Wolf School Steel Sword",
            WitcherSetBonuses.WitcherSchool.WOLF,
            DiagramTier.BASIC,
            "sword",
            List.of()
        ));
        
        register(new Diagram(
            loc("wolf_silver_sword_basic"),
            "Wolf School Silver Sword",
            WitcherSetBonuses.WitcherSchool.WOLF,
            DiagramTier.BASIC,
            "sword",
            List.of()
        ));
    }
    
    private static void registerCatDiagrams() {
        register(new Diagram(
            loc("feline_armor_basic"),
            "Feline Armor",
            WitcherSetBonuses.WitcherSchool.CAT,
            DiagramTier.BASIC,
            "chestplate",
            List.of()
        ));
    }
    
    private static void registerBearDiagrams() {
        register(new Diagram(
            loc("ursine_armor_basic"),
            "Ursine Armor",
            WitcherSetBonuses.WitcherSchool.BEAR,
            DiagramTier.BASIC,
            "chestplate",
            List.of()
        ));
    }
    
    private static void registerGriffinDiagrams() {
        register(new Diagram(
            loc("griffin_armor_basic"),
            "Griffin School Armor",
            WitcherSetBonuses.WitcherSchool.GRIFFIN,
            DiagramTier.BASIC,
            "chestplate",
            List.of()
        ));
    }
    
    private static void registerViperDiagrams() {
        register(new Diagram(
            loc("viper_armor_basic"),
            "Viper School Armor",
            WitcherSetBonuses.WitcherSchool.VIPER,
            DiagramTier.BASIC,
            "chestplate",
            List.of()
        ));
    }
    
    private static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "diagram/" + path);
    }
    
    private static void register(Diagram diagram) {
        DIAGRAMS.put(diagram.id(), diagram);
    }
    
    public static Optional<Diagram> getDiagram(ResourceLocation id) {
        return Optional.ofNullable(DIAGRAMS.get(id));
    }
    
    public static Collection<Diagram> getAllDiagrams() {
        return DIAGRAMS.values();
    }
    
    public static List<Diagram> getDiagramsForSchool(WitcherSetBonuses.WitcherSchool school) {
        return DIAGRAMS.values().stream()
            .filter(d -> d.school() == school)
            .sorted(Comparator.comparingInt(d -> d.tier().level))
            .toList();
    }
    
    /**
     * Check if a player has unlocked a specific diagram
     */
    public static boolean hasUnlocked(Player player, ResourceLocation diagramId) {
        // For now, all diagrams are unlocked
        // TODO: Implement persistent diagram discovery
        return true;
    }
    
    /**
     * Unlock a diagram for a player
     */
    public static void unlock(Player player, ResourceLocation diagramId) {
        // TODO: Implement persistent diagram discovery
        getDiagram(diagramId).ifPresent(diagram -> {
            player.displayClientMessage(
                Component.literal("Discovered: ").append(diagram.getDisplayName()),
                false
            );
        });
    }
}
