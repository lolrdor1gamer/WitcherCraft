package org.tgr.witchercraft.worldgen.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.util.RandomSource;

/**
 * Generates advanced terrain features like boulders, fallen logs, and natural decorations.
 * Adds visual variety to the landscape beyond basic vegetation.
 */
public class TerrainFeatures {
    
    private static final BlockState STONE = Blocks.STONE.defaultBlockState();
    private static final BlockState COBBLESTONE = Blocks.COBBLESTONE.defaultBlockState();
    private static final BlockState MOSSY_COBBLESTONE = Blocks.MOSSY_COBBLESTONE.defaultBlockState();
    private static final BlockState OAK_LOG = Blocks.OAK_LOG.defaultBlockState();
    private static final BlockState SPRUCE_LOG = Blocks.SPRUCE_LOG.defaultBlockState();
    private static final BlockState PUMPKIN = Blocks.PUMPKIN.defaultBlockState();
    private static final BlockState MUSHROOM_BROWN = Blocks.BROWN_MUSHROOM.defaultBlockState();
    private static final BlockState MUSHROOM_RED = Blocks.RED_MUSHROOM.defaultBlockState();
    
    /**
     * Places terrain features in a chunk based on the biome.
     */
    public static void placeFeatures(ChunkAccess chunk, RandomSource random, Biome biome) {
        ChunkPos chunkPos = chunk.getPos();
        
        // Get biome properties
        float temperature = biome.getBaseTemperature();
        // Use temperature as a proxy for downfall since the API changed in 1.21
        float downfall = temperature > 0.8f ? 0.3f : (temperature < 0.2f ? 0.2f : 0.6f);
        
        // Attempt to place features 3-5 times per chunk
        int attempts = 3 + random.nextInt(3);
        
        for (int i = 0; i < attempts; i++) {
            int x = random.nextInt(16);
            int z = random.nextInt(16);
            int y = chunk.getHeight(net.minecraft.world.level.levelgen.Heightmap.Types.WORLD_SURFACE_WG, x, z);
            
            BlockPos surfacePos = new BlockPos(x, y, z);
            BlockState surfaceBlock = chunk.getBlockState(surfacePos);
            
            // Only place on solid blocks
            if (!surfaceBlock.isSolid()) {
                continue;
            }
            
            // Different features for different biomes
            float featureRoll = random.nextFloat();
            
            // Boulders in mountains and hills (15% chance)
            if (y > 75 && featureRoll < 0.15) {
                placeBoulder(chunk, x, y + 1, z, random);
            }
            // Fallen logs in forests (10% chance in humid areas)
            else if (downfall > 0.6 && temperature > 0.3 && featureRoll < 0.10) {
                placeFallenLog(chunk, x, y + 1, z, random, temperature < 0.5);
            }
            // Mushroom clusters in forests (8% chance)
            else if (downfall > 0.7 && temperature > 0.4 && featureRoll < 0.08) {
                placeMushroomCluster(chunk, x, y + 1, z, random);
            }
            // Pumpkin patches in farmlands (5% chance in temperate areas)
            else if (temperature > 0.5 && temperature < 0.8 && downfall > 0.4 && featureRoll < 0.05) {
                placePumpkinPatch(chunk, x, y + 1, z, random);
            }
        }
    }
    
    /**
     * Places a natural boulder (1-3 blocks).
     */
    private static void placeBoulder(ChunkAccess chunk, int x, int y, int z, RandomSource random) {
        BlockPos basePos = new BlockPos(x, y, z);
        
        // Choose boulder type
        BlockState boulderBlock = random.nextFloat() < 0.3 ? MOSSY_COBBLESTONE : 
                                   (random.nextFloat() < 0.5 ? COBBLESTONE : STONE);
        
        // Size: 1-3 blocks
        int size = 1 + random.nextInt(3);
        
        // Place center block
        chunk.setBlockState(basePos, boulderBlock, 3);
        
        if (size > 1) {
            // Add a block on top
            chunk.setBlockState(basePos.above(), boulderBlock, 3);
        }
        
        if (size > 2) {
            // Add one more block to the side
            int direction = random.nextInt(4);
            BlockPos sidePos = switch (direction) {
                case 0 -> basePos.north();
                case 1 -> basePos.south();
                case 2 -> basePos.east();
                default -> basePos.west();
            };
            
            if (sidePos.getX() >= 0 && sidePos.getX() < 16 && sidePos.getZ() >= 0 && sidePos.getZ() < 16) {
                chunk.setBlockState(sidePos, boulderBlock, 3);
            }
        }
    }
    
    /**
     * Places a fallen log (3-6 blocks long).
     */
    private static void placeFallenLog(ChunkAccess chunk, int x, int y, int z, RandomSource random, boolean isCold) {
        BlockState logBlock = isCold ? SPRUCE_LOG : OAK_LOG;
        
        // Log length: 3-6 blocks
        int length = 3 + random.nextInt(4);
        
        // Direction: north-south or east-west
        boolean isNorthSouth = random.nextBoolean();
        
        for (int i = 0; i < length; i++) {
            int logX = isNorthSouth ? x : x + i;
            int logZ = isNorthSouth ? z + i : z;
            
            // Check bounds
            if (logX < 0 || logX >= 16 || logZ < 0 || logZ >= 16) {
                break;
            }
            
            // Adjust Y to follow terrain
            int logY = chunk.getHeight(net.minecraft.world.level.levelgen.Heightmap.Types.WORLD_SURFACE_WG, logX, logZ) + 1;
            
            BlockPos logPos = new BlockPos(logX, logY, logZ);
            BlockState below = chunk.getBlockState(logPos.below());
            
            // Only place on solid ground
            if (below.isSolid()) {
                chunk.setBlockState(logPos, logBlock, 3);
                
                // Small chance for mushrooms to grow on log
                if (random.nextFloat() < 0.2 && i > 0) {
                    chunk.setBlockState(logPos.above(), random.nextBoolean() ? MUSHROOM_BROWN : MUSHROOM_RED, 3);
                }
            }
        }
    }
    
    /**
     * Places a cluster of mushrooms (2-4 mushrooms).
     */
    private static void placeMushroomCluster(ChunkAccess chunk, int x, int y, int z, RandomSource random) {
        int count = 2 + random.nextInt(3);
        
        for (int i = 0; i < count; i++) {
            int offsetX = x + random.nextInt(3) - 1;
            int offsetZ = z + random.nextInt(3) - 1;
            
            // Check bounds
            if (offsetX < 0 || offsetX >= 16 || offsetZ < 0 || offsetZ >= 16) {
                continue;
            }
            
            int mushY = chunk.getHeight(net.minecraft.world.level.levelgen.Heightmap.Types.WORLD_SURFACE_WG, offsetX, offsetZ) + 1;
            BlockPos mushroomPos = new BlockPos(offsetX, mushY, offsetZ);
            BlockState below = chunk.getBlockState(mushroomPos.below());
            
            // Place on grass or dirt
            if (below.is(Blocks.GRASS_BLOCK) || below.is(Blocks.DIRT)) {
                BlockState mushroom = random.nextBoolean() ? MUSHROOM_BROWN : MUSHROOM_RED;
                chunk.setBlockState(mushroomPos, mushroom, 3);
            }
        }
    }
    
    /**
     * Places a small pumpkin patch (1-3 pumpkins).
     */
    private static void placePumpkinPatch(ChunkAccess chunk, int x, int y, int z, RandomSource random) {
        int count = 1 + random.nextInt(3);
        
        for (int i = 0; i < count; i++) {
            int offsetX = x + random.nextInt(4) - 2;
            int offsetZ = z + random.nextInt(4) - 2;
            
            // Check bounds
            if (offsetX < 0 || offsetX >= 16 || offsetZ < 0 || offsetZ >= 16) {
                continue;
            }
            
            int pumpY = chunk.getHeight(net.minecraft.world.level.levelgen.Heightmap.Types.WORLD_SURFACE_WG, offsetX, offsetZ) + 1;
            BlockPos pumpkinPos = new BlockPos(offsetX, pumpY, offsetZ);
            BlockState below = chunk.getBlockState(pumpkinPos.below());
            
            // Place on grass
            if (below.is(Blocks.GRASS_BLOCK)) {
                chunk.setBlockState(pumpkinPos, PUMPKIN, 3);
            }
        }
    }
}
