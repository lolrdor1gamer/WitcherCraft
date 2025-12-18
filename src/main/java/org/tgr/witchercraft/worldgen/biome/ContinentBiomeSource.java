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
            // Temeria
            BiomeHolderCache.get(WitcherBiomes.TEMERIAN_FARMLANDS),
            BiomeHolderCache.get(WitcherBiomes.MAHAKAMAN_FOOTHILLS),
            BiomeHolderCache.get(WitcherBiomes.PONTAR_RIVERLANDS),
            BiomeHolderCache.get(WitcherBiomes.BROKILON_FOREST),
            
            // Kaedwen
            BiomeHolderCache.get(WitcherBiomes.MOUNTAIN_PEAKS),
            BiomeHolderCache.get(WitcherBiomes.FROZEN_TAIGA),
            BiomeHolderCache.get(WitcherBiomes.MINE_VALLEYS),
            BiomeHolderCache.get(WitcherBiomes.GLACIAL_LAKES),
            BiomeHolderCache.get(WitcherBiomes.MAHAKAMAN_MOUNTAINS),
            
            // Redania
            BiomeHolderCache.get(WitcherBiomes.REDANIAN_PLAINS),
            BiomeHolderCache.get(WitcherBiomes.OXENFURT_LAKES),
            
            // Nilfgaard
            BiomeHolderCache.get(WitcherBiomes.NILFGAARDIAN_SCRUBLAND),
            BiomeHolderCache.get(WitcherBiomes.SOUTHERN_PLAINS),
            BiomeHolderCache.get(WitcherBiomes.OLIVE_GROVES),
            BiomeHolderCache.get(WitcherBiomes.IMPERIAL_GARDENS),
            BiomeHolderCache.get(WitcherBiomes.ARID_SCRUBLAND),
            BiomeHolderCache.get(WitcherBiomes.TOUSSAINT_VINEYARDS),
            
            // Skellige
            BiomeHolderCache.get(WitcherBiomes.SKELLIGE_COAST),
            BiomeHolderCache.get(WitcherBiomes.ROCKY_COASTLINES),
            BiomeHolderCache.get(WitcherBiomes.TUNDRA_PLAINS),
            BiomeHolderCache.get(WitcherBiomes.GLACIER_FIELDS),
            BiomeHolderCache.get(WitcherBiomes.STORM_SEAS),
            
            // No Man's Land / War Zones
            BiomeHolderCache.get(WitcherBiomes.NO_MANS_LAND),
            BiomeHolderCache.get(WitcherBiomes.VELEN_MARSHES),
            
            // Special Cross-Regional
            BiomeHolderCache.get(WitcherBiomes.BATTLEFIELD),
            BiomeHolderCache.get(WitcherBiomes.CURSED_SWAMP),
            BiomeHolderCache.get(WitcherBiomes.ANCIENT_RUINS)
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
        
        // Check for special cross-regional biomes first (rare)
        double specialChance = regionProvider.getSpecialBiomeNoise((int)(temperature * 1000), (int)(humidity * 1000));
        if (specialChance > 0.95 && region == ContinentRegion.NO_MANS_LAND) {
            // 5% chance for battlefield in No Man's Land
            selectedBiome = BiomeHolderCache.get(WitcherBiomes.BATTLEFIELD);
            if (selectedBiome != null) return selectedBiome;
        }
        if (specialChance > 0.92 && humidity > 0.85) {
            // Cursed swamps in very humid areas
            selectedBiome = BiomeHolderCache.get(WitcherBiomes.CURSED_SWAMP);
            if (selectedBiome != null) return selectedBiome;
        }
        if (specialChance > 0.90 && y > 72 && humidity < 0.5) {
            // Ancient ruins on high dry ground
            selectedBiome = BiomeHolderCache.get(WitcherBiomes.ANCIENT_RUINS);
            if (selectedBiome != null) return selectedBiome;
        }
        
        switch (region) {
            case TEMERIA:
                // Temperate farmland region with rivers and forests
                if (humidity > 0.80 && y < 68) {
                    // Dense magical forest near borders (rare)
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.BROKILON_FOREST);
                } else if (humidity > 0.70) {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.PONTAR_RIVERLANDS);
                } else if (y > 75) {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.MAHAKAMAN_FOOTHILLS);
                } else {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.TEMERIAN_FARMLANDS);
                }
                break;
                
            case KAEDWEN:
                // Mountainous northern kingdom
                if (y > 90) {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.MAHAKAMAN_MOUNTAINS);
                } else if (y > 80) {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.MOUNTAIN_PEAKS);
                } else if (humidity > 0.7) {
                    // Glacial lakes in wet valleys
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.GLACIAL_LAKES);
                } else if (temperature < 0.35) {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.FROZEN_TAIGA);
                } else {
                    // Mining valleys (default)
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.MINE_VALLEYS);
                }
                break;
                
            case REDANIA:
                // Rich northern kingdom with religious architecture
                if (humidity > 0.75) {
                    // Oxenfurt lakes region
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.OXENFURT_LAKES);
                } else if (isCoastal) {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.PONTAR_RIVERLANDS);
                } else {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.REDANIAN_PLAINS);
                }
                break;
                
            case NILFGAARD:
                // Southern empire - varied terrain
                if (temperature > 0.85 && humidity > 0.5 && humidity < 0.65) {
                    // Toussaint wine country
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.TOUSSAINT_VINEYARDS);
                } else if (temperature > 0.8 && humidity > 0.55) {
                    // Imperial gardens near cities
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.IMPERIAL_GARDENS);
                } else if (temperature > 0.75 && humidity > 0.4) {
                    // Olive groves
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.OLIVE_GROVES);
                } else if (humidity < 0.25) {
                    // Arid scrubland
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.ARID_SCRUBLAND);
                } else if (humidity < 0.45) {
                    // Nilfgaardian scrubland
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.NILFGAARDIAN_SCRUBLAND);
                } else {
                    // Southern plains
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.SOUTHERN_PLAINS);
                }
                break;
                
            case SKELLIGE:
                // Island archipelago - Norse-inspired
                if (y < 58) {
                    // Deep ocean areas
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.STORM_SEAS);
                } else if (isCoastal) {
                    if (y > 68) {
                        selectedBiome = BiomeHolderCache.get(WitcherBiomes.ROCKY_COASTLINES);
                    } else {
                        selectedBiome = BiomeHolderCache.get(WitcherBiomes.SKELLIGE_COAST);
                    }
                } else if (y > 85) {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.GLACIER_FIELDS);
                } else if (y > 75) {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.MOUNTAIN_PEAKS);
                } else {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.TUNDRA_PLAINS);
                }
                break;
                
            case NO_MANS_LAND:
                // War-torn wasteland
                if (humidity > 0.80 && y < 66) {
                    // Velen swamps - dangerous marshlands
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.VELEN_MARSHES);
                } else if (humidity < 0.3 && temperature > 0.4) {
                    // Scorched battlefields
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.BATTLEFIELD);
                } else {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.NO_MANS_LAND);
                }
                break;
                
            case AEDIRN:
                // Eastern kingdom - agricultural with dragon lore
                if (humidity > 0.65) {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.PONTAR_RIVERLANDS);
                } else if (y > 75) {
                    selectedBiome = BiomeHolderCache.get(WitcherBiomes.MAHAKAMAN_FOOTHILLS);
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
