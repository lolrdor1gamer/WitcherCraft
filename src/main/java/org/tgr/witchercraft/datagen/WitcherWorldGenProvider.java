package org.tgr.witchercraft.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.worldgen.biome.WitcherBiomes;

import java.util.concurrent.CompletableFuture;

/**
 * Data generator for world generation elements (biomes, dimensions, etc.)
 */
public class WitcherWorldGenProvider extends FabricDynamicRegistryProvider {
    
    public WitcherWorldGenProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }
    
    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        // Register biomes
        entries.addAll(registries.lookupOrThrow(Registries.BIOME));
    }
    
    @Override
    public String getName() {
        return Witchercraft.MOD_ID + " World Generation";
    }
}
