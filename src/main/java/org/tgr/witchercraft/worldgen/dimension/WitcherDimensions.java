package org.tgr.witchercraft.worldgen.dimension;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.worldgen.biome.ContinentBiomeSource;
import org.tgr.witchercraft.worldgen.biome.WitcherBiomes;

import java.util.OptionalLong;

/**
 * Dimension type definitions for The Continent.
 */
public class WitcherDimensions {
    
    // Dimension type key
    public static final ResourceKey<DimensionType> CONTINENT_TYPE = ResourceKey.create(
        Registries.DIMENSION_TYPE,
        ResourceLocation.fromNamespaceAndPath(Witchercraft.MOD_ID, "the_continent")
    );
    
    // Dimension level key
    public static final ResourceKey<Level> CONTINENT_LEVEL = ResourceKey.create(
        Registries.DIMENSION,
        ResourceLocation.fromNamespaceAndPath(Witchercraft.MOD_ID, "the_continent")
    );
    
    // Level stem key
    public static final ResourceKey<LevelStem> CONTINENT_STEM = ResourceKey.create(
        Registries.LEVEL_STEM,
        ResourceLocation.fromNamespaceAndPath(Witchercraft.MOD_ID, "the_continent")
    );
    
    /**
     * Create the dimension type for The Continent.
     * This defines the physical properties of the dimension.
     */
    public static DimensionType createContinentType() {
        return new DimensionType(
            OptionalLong.empty(),           // Fixed time (empty = normal day/night cycle)
            true,                           // Has skylight
            false,                          // Has ceiling (like Nether)
            false,                          // Ultrawarm (like Nether)
            true,                           // Natural (mob spawning, etc.)
            1.0,                            // Coordinate scale
            false,                          // Bed works
            false,                          // Respawn anchor works
            -64,                            // Min Y
            384,                            // Height
            384,                            // Logical height
            BlockTags.INFINIBURN_OVERWORLD, // Infiniburn blocks
            BuiltinDimensionTypes.OVERWORLD_EFFECTS, // Effects (sky rendering, etc.)
            0.0f,                           // Ambient light
            java.util.Optional.empty(),     // Raids - portal setup
            new DimensionType.MonsterSettings(false, true, UniformInt.of(0, 7), 0)
        );
    }
    
    /**
     * Bootstrap method for data generation
     */
    public static void bootstrap(BootstrapContext<DimensionType> context) {
        context.register(CONTINENT_TYPE, createContinentType());
    }
    
    /**
     * Bootstrap method for level stems (dimension configuration)
     */
    public static void bootstrapStem(BootstrapContext<LevelStem> context) {
        HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);
        
        // Get the dimension type holder
        Holder<DimensionType> continentType = dimensionTypes.getOrThrow(CONTINENT_TYPE);
        
        // Get default biome for the biome source
        Holder<Biome> defaultBiome = biomes.getOrThrow(WitcherBiomes.TEMERIAN_FARMLANDS);
        
        // Create biome source
        ContinentBiomeSource biomeSource = new ContinentBiomeSource(defaultBiome, 0L);
        
        // Create chunk generator using overworld noise settings
        ResourceKey<NoiseGeneratorSettings> overworldKey = ResourceKey.create(
            Registries.NOISE_SETTINGS,
            ResourceLocation.withDefaultNamespace("overworld")
        );
        Holder<NoiseGeneratorSettings> overworldSettings = noiseSettings.getOrThrow(overworldKey);
        
        NoiseBasedChunkGenerator chunkGenerator = new NoiseBasedChunkGenerator(
            biomeSource,
            overworldSettings
        );
        
        // Register the level stem
        LevelStem stem = new LevelStem(continentType, chunkGenerator);
        context.register(CONTINENT_STEM, stem);
    }
}
