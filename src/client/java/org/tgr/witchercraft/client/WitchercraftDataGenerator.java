package org.tgr.witchercraft.client;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

/**
 * Data generator for WitcherCraft mod
 * Generates models, loot tables, recipes, etc.
 */
public class WitchercraftDataGenerator implements DataGeneratorEntrypoint
{
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        
        // Data providers will be added here as needed
        // For now, items are registered via ModItems.initialize()
    }
}
