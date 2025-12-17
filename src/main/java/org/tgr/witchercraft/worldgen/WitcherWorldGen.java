package org.tgr.witchercraft.worldgen;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.worldgen.biome.ContinentBiomeSource;
import org.tgr.witchercraft.worldgen.biome.WitcherBiomes;
import org.tgr.witchercraft.worldgen.dimension.ContinentChunkGenerator;
import org.tgr.witchercraft.worldgen.dimension.WitcherDimensions;

/**
 * Handles registration of all world generation elements.
 */
public class WitcherWorldGen {
    
    /**
     * Called during data generation to register biomes
     */
    public static void bootstrap(net.minecraft.data.worldgen.BootstrapContext<?> context) {
        if (context.lookup(Registries.BIOME) != null) {
            // Bootstrap is handled by WitcherBiomes directly during datagen
        }
    }
    
    /**
     * Called during mod initialization
     */
    public static void initialize() {
        Witchercraft.LOGGER.info("Initializing WitcherCraft world generation...");
        
        // Register BiomeSource codec
        Registry.register(
            BuiltInRegistries.BIOME_SOURCE,
            ResourceLocation.fromNamespaceAndPath(Witchercraft.MOD_ID, "continent_biome_source"),
            ContinentBiomeSource.CODEC
        );
        Witchercraft.LOGGER.info("Registered ContinentBiomeSource codec");
        
        // Register ChunkGenerator codec
        Registry.register(
            BuiltInRegistries.CHUNK_GENERATOR,
            ResourceLocation.fromNamespaceAndPath(Witchercraft.MOD_ID, "continent_chunk_generator"),
            ContinentChunkGenerator.CODEC
        );
        Witchercraft.LOGGER.info("Registered ContinentChunkGenerator codec");
        
        Witchercraft.LOGGER.info("World generation initialization complete!");
    }
}
