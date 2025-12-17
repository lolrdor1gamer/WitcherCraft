package org.tgr.witchercraft.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import org.tgr.witchercraft.worldgen.dimension.WitcherDimensions;

import java.util.concurrent.CompletableFuture;

/**
 * Data generator for dimension configuration
 */
public class WitcherDimensionProvider extends FabricDynamicRegistryProvider {
    
    public WitcherDimensionProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }
    
    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        // Add dimension type
        entries.addAll(registries.lookupOrThrow(Registries.DIMENSION_TYPE));
        
        // Add level stems (dimension configurations)
        entries.addAll(registries.lookupOrThrow(Registries.LEVEL_STEM));
    }
    
    @Override
    public String getName() {
        return "WitcherCraft Dimensions";
    }
}
