package org.tgr.witchercraft.worldgen.biome;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import org.tgr.witchercraft.worldgen.region.ContinentRegion;
import org.tgr.witchercraft.worldgen.region.RegionProvider;

import java.util.stream.Stream;

/**
 * Custom BiomeSource that generates biomes based on The Continent's regions.
 * Uses RegionProvider for natural region boundaries and climate-based biome selection.
 */
public class ContinentBiomeSource extends BiomeSource {
    
    public static final MapCodec<ContinentBiomeSource> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            Biome.CODEC.fieldOf("default_biome").forGetter(source -> source.defaultBiome)
        ).apply(instance, ContinentBiomeSource::new)
    );
    
    private final Holder<Biome> defaultBiome;
    private final RegionProvider regionProvider;
    
    public ContinentBiomeSource(Holder<Biome> defaultBiome) {
        this.defaultBiome = defaultBiome;
        this.regionProvider = new RegionProvider(0L); // Will be replaced with world seed
    }
    
    public ContinentBiomeSource(Holder<Biome> defaultBiome, long seed) {
        this.defaultBiome = defaultBiome;
        this.regionProvider = new RegionProvider(seed);
    }
    
    @Override
    protected MapCodec<? extends BiomeSource> codec() {
        return CODEC;
    }
    
    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        // Return all biomes that can be selected by this biome source
        if (!BiomeHolderCache.isInitialized()) {
            return Stream.of(defaultBiome);
        }
        
        return Stream.of(
            BiomeHolderCache.get(WitcherBiomes.TEMERIAN_FARMLANDS),
            BiomeHolderCache.get(WitcherBiomes.MAHAKAMAN_FOOTHILLS),
            BiomeHolderCache.get(WitcherBiomes.PONTAR_RIVERLANDS),
            BiomeHolderCache.get(WitcherBiomes.MOUNTAIN_PEAKS),
            BiomeHolderCache.get(WitcherBiomes.FROZEN_TAIGA),
            BiomeHolderCache.get(WitcherBiomes.NO_MANS_LAND),
            BiomeHolderCache.get(WitcherBiomes.REDANIAN_PLAINS),
            BiomeHolderCache.get(WitcherBiomes.NILFGAARDIAN_SCRUBLAND),
            BiomeHolderCache.get(WitcherBiomes.SKELLIGE_COAST)
        ).filter(holder -> holder != null);
    }
    
    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        // Convert biome coordinates to world coordinates
        // Biome coordinates are at 1/4 scale (multiply by 4)
        int worldX = x * 4;
        int worldZ = z * 4;
        
        // Get the region for this location
        ContinentRegion region = regionProvider.getRegion(worldX, worldZ);
        
        // Get environmental data for fine-grained biome selection
        double temperature = regionProvider.getTemperature(worldX, worldZ);
        double humidity = regionProvider.getHumidity(worldX, worldZ);
        boolean isCoastal = regionProvider.isCoastal(worldX, worldZ);
        
        // Select biome based on region and environmental factors
        return selectBiomeForRegion(region, temperature, humidity, isCoastal, y);
    }
    
    /**
     * Select the appropriate biome for a region based on environmental factors.
     * Uses BiomeHolderCache to get actual biome holders at runtime.
     */
    private Holder<Biome> selectBiomeForRegion(ContinentRegion region, double temperature, 
                                                double humidity, boolean isCoastal, int y) {
        // If cache not initialized yet, return default
        if (!BiomeHolderCache.isInitialized()) {
            return defaultBiome;
        }
        
        Holder<Biome> selectedBiome = null;
        
        switch (region) {
            case TEMERIA:
                // Temperate farmland region with rivers
                if (humidity > 0.7) {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.PONTAR_RIVERLANDS);
                } else if (humidity > 0.4) {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.TEMERIAN_FARMLANDS);
                } else {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.MAHAKAMAN_FOOTHILLS);
                }
                break;
                
            case KAEDWEN:
                // Mountainous northern kingdom
                if (y > 80 || temperature < 0.3) {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.MOUNTAIN_PEAKS);
                } else if (temperature < 0.5) {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.FROZEN_TAIGA);
                } else {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.MAHAKAMAN_FOOTHILLS);
                }
                break;
                
            case REDANIA:
                // Rich kingdom with varied terrain
                if (isCoastal) {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.PONTAR_RIVERLANDS);
                } else {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.REDANIAN_PLAINS);
                }
                break;
                
            case NILFGAARD:
                // Southern empire - arid/scrubland
                selectedBiome = BiomeHolderCache.get(WitcherBiomes.NILFGAARDIAN_SCRUBLAND);
                break;
                
            case SKELLIGE:
                // Island archipelago - coastal/mountainous
                if (isCoastal) {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.SKELLIGE_COAST);
                } else if (y > 70) {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.MOUNTAIN_PEAKS);
                } else {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.FROZEN_TAIGA);
                }
                break;
                
            case NO_MANS_LAND:
                // War-torn wasteland
                selectedBiome = BiomeHolderCache.get(WitcherBiomes.NO_MANS_LAND);
                break;
                
            case AEDIRN:
                // Eastern kingdom - similar to Temeria
                if (humidity > 0.6) {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.PONTAR_RIVERLANDS);
                } else {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.TEMERIAN_FARMLANDS);
                }
                break;
                
            default:
                selectedBiome = defaultBiome;
        }
        
        // Return selected biome or default if not found
        return selectedBiome != null ? selectedBiome : defaultBiome;
    }
}
