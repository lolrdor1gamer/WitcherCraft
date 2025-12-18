package org.tgr.witchercraft.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.presets.WorldPreset;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.worldgen.dimension.WitcherDimensions;

import java.util.HashMap;
import java.util.Map;

/**
 * World presets for WitcherCraft - defines the default world type
 */
public class WitcherWorldPresets {
    
    // World preset key for The Continent
    public static final ResourceKey<WorldPreset> CONTINENT_PRESET = ResourceKey.create(
        Registries.WORLD_PRESET,
        ResourceLocation.fromNamespaceAndPath(Witchercraft.MOD_ID, "the_continent")
    );
    
    /**
     * Bootstrap method to register world presets
     */
    public static void bootstrap(BootstrapContext<WorldPreset> context) {
        HolderGetter<LevelStem> levelStems = context.lookup(Registries.LEVEL_STEM);
        HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        
        // Get the continent level stem and dimension type
        Holder<LevelStem> continentStem = levelStems.getOrThrow(WitcherDimensions.CONTINENT_STEM);
        Holder<DimensionType> continentType = dimensionTypes.getOrThrow(WitcherDimensions.CONTINENT_TYPE);
        
        // Create dimensions map with The Continent as the overworld
        Map<ResourceKey<LevelStem>, LevelStem> dimensions = new HashMap<>();
        dimensions.put(
            ResourceKey.create(Registries.LEVEL_STEM, 
                ResourceLocation.withDefaultNamespace("overworld")),
            continentStem.value()
        );
        
        // Create the world preset - using the dimensions map directly
        // In 1.21.1, we just pass the map; the primary dimension is determined by the map contents
        WorldPreset continentPreset = new WorldPreset(dimensions);
        context.register(CONTINENT_PRESET, continentPreset);
    }
}
