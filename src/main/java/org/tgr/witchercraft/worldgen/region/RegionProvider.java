package org.tgr.witchercraft.worldgen.region;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.LegacyRandomSource;

/**
 * Determines which region of The Continent a given coordinate belongs to.
 * Uses simple noise-like calculations for natural-looking region boundaries.
 */
public class RegionProvider {
    private static final double REGION_SCALE = 8000.0; // How spread out regions are
    private static final double TEMP_SCALE = 2000.0;   // Temperature variation scale
    private static final double COAST_SCALE = 4000.0;  // Coastal detection scale
    
    private final long seed;
    
    public RegionProvider(long worldSeed) {
        this.seed = worldSeed;
    }
    
    /**
     * Gets the region for a given world coordinate.
     */
    public ContinentRegion getRegion(int x, int z) {
        double continental = sampleNoise(x, z, REGION_SCALE, 12345L);
        double temperature = sampleNoise(x, z, TEMP_SCALE, 67890L);
        double humidity = sampleNoise(x, z, TEMP_SCALE, 11111L);
        double coastal = sampleNoise(x, z, COAST_SCALE, 22222L);
        
        // Normalize noise values to [0, 1]
        continental = (continental + 1.0) / 2.0;
        temperature = (temperature + 1.0) / 2.0;
        humidity = (humidity + 1.0) / 2.0;
        coastal = (coastal + 1.0) / 2.0;
        
        // Skellige: Far west and coastal
        if (x < -2000 && coastal > 0.6) {
            return ContinentRegion.SKELLIGE;
        }
        
        // Kaedwen: Far north and cold
        if (z < -2000 && temperature < 0.3) {
            return ContinentRegion.KAEDWEN;
        }
        
        // Redania: North-central
        if (z < -500 && temperature < 0.5 && continental > 0.3) {
            return ContinentRegion.REDANIA;
        }
        
        // Nilfgaard: Far south and warm
        if (z > 1500 && temperature > 0.65) {
            return ContinentRegion.NILFGAARD;
        }
        
        // Aedirn: Eastern regions
        if (x > 1500 && Math.abs(z) < 1000) {
            return ContinentRegion.AEDIRN;
        }
        
        // No Man's Land: Transitional border zones
        if (isBorderZone(continental, temperature)) {
            return ContinentRegion.NO_MANS_LAND;
        }
        
        // Default: Temeria (central region)
        return ContinentRegion.TEMERIA;
    }
    
    /**
     * Simple noise sampling using sine-based approximation with proper smoothing.
     */
    private double sampleNoise(int x, int z, double scale, long noiseSeed) {
        double nx = x / scale;
        double nz = z / scale;
        long combinedSeed = seed ^ noiseSeed;
        
        // Simple multi-octave noise approximation
        double value = 0.0;
        double amplitude = 1.0;
        double frequency = 1.0;
        
        for (int octave = 0; octave < 4; octave++) {
            double octaveSeed = combinedSeed + octave * 1000;
            value += amplitude * interpolatedNoise(nx * frequency, nz * frequency, octaveSeed);
            amplitude *= 0.5;
            frequency *= 2.0;
        }
        
        return Mth.clamp(value, -1.0, 1.0);
    }
    
    /**
     * Interpolated noise for smooth transitions between blocks.
     */
    private double interpolatedNoise(double x, double z, double seed) {
        // Get integer coordinates
        int x0 = Mth.floor(x);
        int z0 = Mth.floor(z);
        int x1 = x0 + 1;
        int z1 = z0 + 1;
        
        // Get fractional part
        double fx = x - x0;
        double fz = z - z0;
        
        // Smooth interpolation (smoothstep)
        fx = fx * fx * (3.0 - 2.0 * fx);
        fz = fz * fz * (3.0 - 2.0 * fz);
        
        // Get corner values
        double v00 = gridNoise(x0, z0, seed);
        double v10 = gridNoise(x1, z0, seed);
        double v01 = gridNoise(x0, z1, seed);
        double v11 = gridNoise(x1, z1, seed);
        
        // Bilinear interpolation
        double v0 = Mth.lerp(fx, v00, v10);
        double v1 = Mth.lerp(fx, v01, v11);
        return Mth.lerp(fz, v0, v1);
    }
    
    /**
     * Grid-based noise value that's consistent for integer coordinates.
     */
    private double gridNoise(int x, int z, double seed) {
        // Hash the coordinates with the seed
        long n = x + z * 57L + (long)seed * 131L;
        n = (n << 13) ^ n;
        n = (n * (n * n * 15731L + 789221L) + 1376312589L) & 0x7fffffffL;
        return 1.0 - ((double)n / 1073741824.0);
    }
    
    /**
     * Checks if a location is in a border/transition zone.
     */
    private boolean isBorderZone(double continental, double temperature) {
        // Borders occur where continental and temperature values are near transition points
        boolean continentalTransition = continental > 0.45 && continental < 0.55;
        boolean temperatureTransition = temperature > 0.4 && temperature < 0.6;
        return continentalTransition && temperatureTransition;
    }
    
    /**
     * Gets temperature at a coordinate (0.0 = cold, 1.0 = hot).
     */
    public double getTemperature(int x, int z) {
        double temp = sampleNoise(x, z, TEMP_SCALE, 67890L);
        return (temp + 1.0) / 2.0;
    }
    
    /**
     * Gets humidity at a coordinate (0.0 = dry, 1.0 = wet).
     */
    public double getHumidity(int x, int z) {
        double humid = sampleNoise(x, z, TEMP_SCALE, 11111L);
        return (humid + 1.0) / 2.0;
    }
    
    /**
     * Checks if a coordinate is coastal.
     */
    public boolean isCoastal(int x, int z) {
        double coastal = sampleNoise(x, z, COAST_SCALE, 22222L);
        coastal = (coastal + 1.0) / 2.0;
        return coastal > 0.6;
    }
    
    /**
     * Gets a noise value for special biome placement (0.0 to 1.0).
     * Used for rare biomes like Battlefields, Ancient Ruins, Cursed Swamps.
     */
    public double getSpecialBiomeNoise(int x, int z) {
        double special = sampleNoise(x, z, REGION_SCALE * 0.5, 55555L);
        return (special + 1.0) / 2.0;
    }
}
