package org.tgr.witchercraft.bestiary;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.entity.monster.AbstractWitcherMonster;
import org.tgr.witchercraft.registry.ModEntities;

import java.util.*;

/**
 * Static bestiary entry definitions with lore, weaknesses, and tips.
 */
public class BestiaryEntries {
    
    private static final Map<ResourceLocation, BestiaryEntry> ENTRIES = new HashMap<>();
    
    public record BestiaryEntry(
        ResourceLocation entityId,
        String name,
        AbstractWitcherMonster.MonsterCategory category,
        String shortDescription,
        String loreText,
        List<String> weaknesses,
        List<String> strengths,
        List<String> tactics,
        String habitat,
        String dangerLevel
    ) {
        public static Builder builder(ResourceLocation entityId, String name) {
            return new Builder(entityId, name);
        }
        
        public static class Builder {
            private final ResourceLocation entityId;
            private final String name;
            private AbstractWitcherMonster.MonsterCategory category = AbstractWitcherMonster.MonsterCategory.BEAST;
            private String shortDescription = "";
            private String loreText = "";
            private final List<String> weaknesses = new ArrayList<>();
            private final List<String> strengths = new ArrayList<>();
            private final List<String> tactics = new ArrayList<>();
            private String habitat = "Unknown";
            private String dangerLevel = "Low";
            
            private Builder(ResourceLocation entityId, String name) {
                this.entityId = entityId;
                this.name = name;
            }
            
            public Builder category(AbstractWitcherMonster.MonsterCategory cat) {
                this.category = cat;
                return this;
            }
            
            public Builder description(String desc) {
                this.shortDescription = desc;
                return this;
            }
            
            public Builder lore(String lore) {
                this.loreText = lore;
                return this;
            }
            
            public Builder weakness(String... w) {
                weaknesses.addAll(Arrays.asList(w));
                return this;
            }
            
            public Builder strength(String... s) {
                strengths.addAll(Arrays.asList(s));
                return this;
            }
            
            public Builder tactic(String... t) {
                tactics.addAll(Arrays.asList(t));
                return this;
            }
            
            public Builder habitat(String h) {
                this.habitat = h;
                return this;
            }
            
            public Builder danger(String d) {
                this.dangerLevel = d;
                return this;
            }
            
            public BestiaryEntry build() {
                return new BestiaryEntry(entityId, name, category, shortDescription, loreText,
                    Collections.unmodifiableList(weaknesses),
                    Collections.unmodifiableList(strengths),
                    Collections.unmodifiableList(tactics),
                    habitat, dangerLevel);
            }
        }
    }
    
    public static void initialize() {
        // Drowner / Drowned Corpse
        register(BestiaryEntry.builder(
                ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "drowned_corpse"), "Drowner")
            .category(AbstractWitcherMonster.MonsterCategory.NECROPHAGE)
            .description("Aquatic corpse animated by dark magic")
            .lore("Drowners are the remains of those who died by drowning. They lurk in shallow waters, " +
                  "waiting to drag unsuspecting victims beneath the surface. Though individually weak, " +
                  "they often attack in groups and can be deadly near water.")
            .weakness("Necrophage Oil", "Igni Sign", "Fire damage")
            .strength("Water regeneration", "Surprise attacks", "Pack tactics")
            .tactic("Fight on dry land when possible",
                    "Use Igni to burn away their regeneration",
                    "Apply Necrophage Oil before engaging")
            .habitat("Swamps, rivers, coastlines")
            .danger("Low")
            .build());
        
        // Nekker
        register(BestiaryEntry.builder(
                ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "nekker"), "Nekker")
            .category(AbstractWitcherMonster.MonsterCategory.OGROID)
            .description("Small but vicious underground dwellers")
            .lore("Nekkers are small, primitive creatures that live in underground tunnels. They are " +
                  "cowardly when alone but ferocious in packs. They will swarm any prey that ventures " +
                  "too close to their nests, overwhelming victims with numbers.")
            .weakness("Ogroid Oil", "Aard Sign", "Area attacks")
            .strength("Pack tactics", "Quick attacks", "Tunneling")
            .tactic("Use Aard to knock them back and create space",
                    "Target them one at a time with quick attacks",
                    "Destroy their nests to prevent respawning")
            .habitat("Caves, forests, underground")
            .danger("Low-Medium")
            .build());
        
        // Ghoul
        register(BestiaryEntry.builder(
                ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "ghoul"), "Ghoul")
            .category(AbstractWitcherMonster.MonsterCategory.NECROPHAGE)
            .description("Corpse-eating scavenger of battlefields")
            .lore("Ghouls are drawn to places of death - battlefields, cemeteries, and execution sites. " +
                  "They feast on corpses and are particularly aggressive when their meal is interrupted. " +
                  "Their claws carry disease and their bite can paralyze prey.")
            .weakness("Necrophage Oil", "Fire", "Silver weapons")
            .strength("Regeneration while feeding", "Disease-carrying attacks", "Enhanced in darkness")
            .tactic("Keep them away from corpses to prevent regeneration",
                    "Use fire to cauterize wounds and prevent healing",
                    "Fight during daytime when they are weaker")
            .habitat("Battlefields, graveyards, ruins")
            .danger("Medium")
            .build());
        
        // Alghoul
        register(BestiaryEntry.builder(
                ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "alghoul"), "Alghoul")
            .category(AbstractWitcherMonster.MonsterCategory.NECROPHAGE)
            .description("Evolved ghoul with spinal ridges and greater ferocity")
            .lore("Alghouls are ghouls that have survived and fed long enough to grow stronger. " +
                  "Their distinctive spinal ridges harden when threatened, making them resistant to damage. " +
                  "They are far more dangerous than common ghouls and should not be underestimated.")
            .weakness("Necrophage Oil", "Axii Sign", "Bombs")
            .strength("Damage resistance when enraged", "Powerful regeneration", "Pack leadership")
            .tactic("Use Axii to calm them and lower their defenses",
                    "Attack before they can raise their spines",
                    "Samum bombs can stun them out of rage")
            .habitat("Deep graveyards, ancient battlefields")
            .danger("Medium-High")
            .build());
        
        // Wraith
        register(BestiaryEntry.builder(
                ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "wraith"), "Wraith")
            .category(AbstractWitcherMonster.MonsterCategory.SPECTER)
            .description("Vengeful spirit bound to the mortal realm")
            .lore("Wraiths are the spirits of those who died with powerful emotions - rage, grief, or " +
                  "betrayal. They phase between the material and ethereal worlds, becoming immune to " +
                  "physical attacks while phased. Moon Dust can force them to remain corporeal.")
            .weakness("Specter Oil", "Yrden Sign", "Moon Dust bomb", "Silver weapons")
            .strength("Phasing immunity", "Draining attacks", "Night empowerment")
            .tactic("Use Yrden trap to force them into the material world",
                    "Moon Dust bomb prevents phasing for a time",
                    "Silver sword is essential - steel has no effect")
            .habitat("Ruins, battlefields, sites of tragedy")
            .danger("High")
            .build());
        
        // Werewolf
        register(BestiaryEntry.builder(
                ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "werewolf"), "Werewolf")
            .category(AbstractWitcherMonster.MonsterCategory.CURSED)
            .description("Cursed human that transforms under moonlight")
            .lore("Werewolves are humans afflicted by a lycanthropy curse. They transform during full " +
                  "moons and lose control of themselves, becoming savage beasts. Some retain fragments " +
                  "of humanity and can partially control the transformation. The curse can be lifted, " +
                  "but killing them is often the only practical solution.")
            .weakness("Cursed Oil", "Igni Sign", "Silver weapons", "Devil's Puffball")
            .strength("Rapid regeneration", "Enhanced senses", "Savage strength", "Moon empowerment")
            .tactic("Use Cursed Oil - essential for any chance of victory",
                    "Igni can interrupt their regeneration",
                    "Moon Dust can temporarily suppress the curse",
                    "Devils Puffball poison bypasses regeneration")
            .habitat("Forests, caves, abandoned structures")
            .danger("Very High")
            .build());
    }
    
    private static void register(BestiaryEntry entry) {
        ENTRIES.put(entry.entityId(), entry);
    }
    
    public static Optional<BestiaryEntry> getEntry(ResourceLocation entityId) {
        return Optional.ofNullable(ENTRIES.get(entityId));
    }
    
    public static Optional<BestiaryEntry> getEntry(EntityType<?> entityType) {
        return getEntry(EntityType.getKey(entityType));
    }
    
    public static Collection<BestiaryEntry> getAllEntries() {
        return Collections.unmodifiableCollection(ENTRIES.values());
    }
}
