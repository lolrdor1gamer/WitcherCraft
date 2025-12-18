package org.tgr.witchercraft.worldgen.dimension;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import org.tgr.witchercraft.worldgen.biome.ContinentBiomeSource;
import org.tgr.witchercraft.worldgen.feature.LandmarkPlacer;
import org.tgr.witchercraft.worldgen.feature.OrePlacer;
import org.tgr.witchercraft.worldgen.feature.VegetationPlacer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Custom chunk generator for The Continent dimension.
 * Generates terrain using noise-based height maps with biome-specific modifications.
 */
public class ContinentChunkGenerator extends ChunkGenerator {
    
    public static final MapCodec<ContinentChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(instance ->
        instance.group(
            BiomeSource.CODEC.fieldOf("biome_source").forGetter(generator -> generator.biomeSource),
            NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(generator -> generator.settings)
        ).apply(instance, ContinentChunkGenerator::new)
    );
    
    private final Holder<NoiseGeneratorSettings> settings;
    private OrePlacer orePlacer;
    private VegetationPlacer vegetationPlacer;
    private LandmarkPlacer landmarkPlacer;
    
    public ContinentChunkGenerator(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> settings) {
        super(biomeSource);
        this.settings = settings;
    }
    
    /**
     * Initialize the feature placers with the world seed.
     * Must be called before terrain generation.
     */
    private void ensurePlacersInitialized(long seed) {
        if (this.orePlacer == null) {
            this.orePlacer = new OrePlacer(seed);
        }
        if (this.vegetationPlacer == null) {
            this.vegetationPlacer = new VegetationPlacer(seed);
        }
        if (this.landmarkPlacer == null) {
            this.landmarkPlacer = new LandmarkPlacer(seed);
        }
    }
    
    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }
    
    @Override
    public void applyCarvers(WorldGenRegion region, long seed, RandomState randomState,
                            BiomeManager biomeManager, StructureManager structureManager,
                            ChunkAccess chunk) {
        // TODO: Add cave carving later
        // BiomeManager biomeManager2 = biomeManager.withDifferentSource(this.biomeSource);
        // WorldgenRandom worldgenRandom = new WorldgenRandom(new LegacyRandomSource(RandomSource.create().nextLong()));
        
        // ChunkPos chunkPos = chunk.getPos();
        
        // Apply air carvers (caves)
        // this.applyCarvingStep(GenerationStep.Carving.AIR, region, seed, randomState, 
        //                      biomeManager2, structureManager, chunk, worldgenRandom, chunkPos);
    }

    
    @Override
    public void buildSurface(WorldGenRegion region, StructureManager structureManager, 
                            RandomState randomState, ChunkAccess chunk) {
        // Ensure placers are initialized with world seed
        ensurePlacersInitialized(region.getSeed());
        
        // Generate surface blocks based on biome
        // Place grass/dirt on top of stone, sand on beaches, etc.
        int minY = this.getMinY();
        int seaLevel = this.getSeaLevel();
        
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = chunk.getPos().getMinBlockX() + x;
                int worldZ = chunk.getPos().getMinBlockZ() + z;
                
                // Find the top solid block
                for (int y = this.getGenDepth() + minY - 1; y >= minY; y--) {
                    var pos = chunk.getPos().getBlockAt(x, y, z);
                    BlockState state = chunk.getBlockState(pos);
                    
                    if (state.is(Blocks.STONE)) {
                        // Determine surface block based on height and biome
                        BlockState surfaceBlock;
                        BlockState underBlock = Blocks.DIRT.defaultBlockState();
                        
                        if (y < seaLevel - 5) {
                            // Deep underwater - gravel
                            surfaceBlock = Blocks.GRAVEL.defaultBlockState();
                            underBlock = Blocks.GRAVEL.defaultBlockState();
                        } else if (y < seaLevel + 3) {
                            // Beach/shallow water - sand
                            surfaceBlock = Blocks.SAND.defaultBlockState();
                            underBlock = Blocks.SANDSTONE.defaultBlockState();
                        } else if (y > 90) {
                            // High mountains - stone
                            surfaceBlock = Blocks.STONE.defaultBlockState();
                            underBlock = Blocks.STONE.defaultBlockState();
                        } else {
                            // Normal terrain - grass
                            surfaceBlock = Blocks.GRASS_BLOCK.defaultBlockState();
                        }
                        
                        // Place surface and subsurface layers
                        chunk.setBlockState(pos, surfaceBlock, 3);
                        if (y > minY) {
                            chunk.setBlockState(chunk.getPos().getBlockAt(x, y - 1, z), underBlock, 3);
                            chunk.setBlockState(chunk.getPos().getBlockAt(x, y - 2, z), underBlock, 3);
                        }
                        break;
                    }
                }
            }
        }
        
        // Place ores in stone (using instance with region-based distribution)
        orePlacer.placeOres(region, chunk, region.getRandom());
        
        // Place vegetation on grass blocks (using instance with region-based herbs)
        var biome = region.getBiome(chunk.getPos().getWorldPosition());
        vegetationPlacer.placeVegetation(region, chunk, region.getRandom(), biome.value());
        
        // Place landmarks (Places of Power, Ancient Trees, Monster Nests)
        landmarkPlacer.placeLandmarks(region, chunk, region.getRandom());
        
        // Place natural terrain features (boulders, logs, mushrooms)
        org.tgr.witchercraft.worldgen.feature.TerrainFeatures.placeFeatures(
            chunk, region.getRandom(), biome.value()
        );
    }
    
    @Override
    public void spawnOriginalMobs(WorldGenRegion region) {
        // Spawn mobs according to biome settings
        // Handled by vanilla mob spawning system
    }
    
    @Override
    public int getGenDepth() {
        return settings.value().noiseSettings().height();
    }
    
    @Override
    public int getMinY() {
        return settings.value().noiseSettings().minY();
    }
    
    @Override
    public int getSeaLevel() {
        return 63;
    }
    
    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState randomState,
                                                        StructureManager structureManager, ChunkAccess chunk) {
        return CompletableFuture.supplyAsync(() -> {
            // Fill chunk with blocks based on noise
            this.doFill(randomState, chunk);
            return chunk;
        });
    }
    
    private void doFill(RandomState randomState, ChunkAccess chunk) {
        NoiseSettings noiseSettings = this.settings.value().noiseSettings();
        int minY = noiseSettings.minY();
        int height = noiseSettings.height();
        
        // Generate terrain using improved noise
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = chunk.getPos().getMinBlockX() + x;
                int worldZ = chunk.getPos().getMinBlockZ() + z;
                
                // Get height from improved noise function
                int terrainHeight = getBaseHeight(worldX, worldZ, 
                                                 Heightmap.Types.WORLD_SURFACE_WG, 
                                                 chunk, randomState);
                
                for (int y = minY; y < minY + height; y++) {
                    BlockState state;
                    if (y <= terrainHeight) {
                        state = Blocks.STONE.defaultBlockState();
                    } else if (y <= 63) {
                        state = Blocks.WATER.defaultBlockState();
                    } else {
                        state = Blocks.AIR.defaultBlockState();
                    }
                    
                    chunk.setBlockState(chunk.getPos().getBlockAt(x, y, z), state, 3);
                }
            }
        }
    }
    
    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types heightmapType, 
                            LevelHeightAccessor level, RandomState randomState) {
        // Calculate terrain height using improved noise
        double baseHeight = 64.0; // Sea level
        
        // Continental scale - large rolling hills
        double continentalNoise = sampleNoise(x, z, 800.0) * 30.0;
        
        // Regional scale - medium features
        double regionalNoise = sampleNoise(x, z, 200.0) * 15.0;
        
        // Local scale - small details
        double localNoise = sampleNoise(x, z, 50.0) * 5.0;
        
        // Combine all scales
        double height = baseHeight + continentalNoise + regionalNoise + localNoise;
        
        return (int) Math.max(this.getMinY(), Math.min(this.getGenDepth() + this.getMinY() - 1, height));
    }
    
    /**
     * Sample noise at different scales for natural terrain.
     */
    private double sampleNoise(int x, int z, double scale) {
        double nx = x / scale;
        double nz = z / scale;
        
        // Multi-octave noise
        double value = 0.0;
        double amplitude = 1.0;
        double frequency = 1.0;
        
        for (int octave = 0; octave < 3; octave++) {
            value += amplitude * interpolatedNoise(nx * frequency, nz * frequency, octave * 1000L);
            amplitude *= 0.5;
            frequency *= 2.0;
        }
        
        return value;
    }
    
    /**
     * Interpolated noise for smooth terrain.
     */
    private double interpolatedNoise(double x, double z, long seed) {
        int x0 = (int) Math.floor(x);
        int z0 = (int) Math.floor(z);
        int x1 = x0 + 1;
        int z1 = z0 + 1;
        
        double fx = x - x0;
        double fz = z - z0;
        
        // Smoothstep interpolation
        fx = fx * fx * (3.0 - 2.0 * fx);
        fz = fz * fz * (3.0 - 2.0 * fz);
        
        // Get corner values
        double v00 = gridNoise(x0, z0, seed);
        double v10 = gridNoise(x1, z0, seed);
        double v01 = gridNoise(x0, z1, seed);
        double v11 = gridNoise(x1, z1, seed);
        
        // Bilinear interpolation
        double v0 = v00 * (1 - fx) + v10 * fx;
        double v1 = v01 * (1 - fx) + v11 * fx;
        return v0 * (1 - fz) + v1 * fz;
    }
    
    /**
     * Grid noise value for consistent terrain.
     */
    private double gridNoise(int x, int z, long seed) {
        long n = x + z * 57L + seed * 131L;
        n = (n << 13) ^ n;
        n = (n * (n * n * 15731L + 789221L) + 1376312589L) & 0x7fffffffL;
        return 1.0 - ((double) n / 1073741824.0);
    }
    
    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor level, RandomState randomState) {
        int minY = this.getMinY();
        int height = this.getGenDepth();
        BlockState[] states = new BlockState[height];
        
        // Fill with air by default
        for (int i = 0; i < height; i++) {
            states[i] = Blocks.AIR.defaultBlockState();
        }
        
        return new NoiseColumn(minY, states);
    }
    
    @Override
    public void addDebugScreenInfo(List<String> info, RandomState randomState, net.minecraft.core.BlockPos pos) {
        info.add("Continent Chunk Generator");
        info.add("Biome Source: " + (this.biomeSource instanceof ContinentBiomeSource ? "Continent" : "Other"));
    }
}
