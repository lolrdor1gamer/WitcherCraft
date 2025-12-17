package org.tgr.witchercraft.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import org.tgr.witchercraft.worldgen.biome.WitcherBiomes;
import org.tgr.witchercraft.worldgen.dimension.WitcherDimensions;

/**
 * Main data generation entry point for WitcherCraft mod
 */
public class WitchercraftDataGenerator implements DataGeneratorEntrypoint {
    
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        
        // Register world generation provider
        pack.addProvider(WitcherWorldGenProvider::new);
    }
    
    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        // Register biomes for data generation
        registryBuilder.add(Registries.BIOME, WitcherBiomes::bootstrap);
        
        // Register dimension types for data generation
        registryBuilder.add(Registries.DIMENSION_TYPE, WitcherDimensions::bootstrap);
        
        // Register level stems (dimension configurations)
        registryBuilder.add(Registries.LEVEL_STEM, WitcherDimensions::bootstrapStem);
    }
}
