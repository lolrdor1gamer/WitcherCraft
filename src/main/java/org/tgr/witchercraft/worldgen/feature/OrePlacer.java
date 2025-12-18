package org.tgr.witchercraft.worldgen.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.tgr.witchercraft.registry.ModBlocks;
import org.tgr.witchercraft.worldgen.region.RegionProvider;
import org.tgr.witchercraft.worldgen.region.ContinentRegion;

/**
 * Handles ore placement for The Continent.
 * Places vanilla ores and custom Witcher ores with region-based distribution.
 */
public class OrePlacer {
    
    // Vanilla ores
    private static final BlockState COAL_ORE = Blocks.COAL_ORE.defaultBlockState();
    private static final BlockState IRON_ORE = Blocks.IRON_ORE.defaultBlockState();
    private static final BlockState GOLD_ORE = Blocks.GOLD_ORE.defaultBlockState();
    private static final BlockState DIAMOND_ORE = Blocks.DIAMOND_ORE.defaultBlockState();
    private static final BlockState REDSTONE_ORE = Blocks.REDSTONE_ORE.defaultBlockState();
    private static final BlockState LAPIS_ORE = Blocks.LAPIS_ORE.defaultBlockState();
    private static final BlockState DEEPSLATE_COAL_ORE = Blocks.DEEPSLATE_COAL_ORE.defaultBlockState();
    private static final BlockState DEEPSLATE_IRON_ORE = Blocks.DEEPSLATE_IRON_ORE.defaultBlockState();
    private static final BlockState COPPER_ORE = Blocks.COPPER_ORE.defaultBlockState();
    private static final BlockState DEEPSLATE_COPPER_ORE = Blocks.DEEPSLATE_COPPER_ORE.defaultBlockState();
    private static final BlockState EMERALD_ORE = Blocks.EMERALD_ORE.defaultBlockState();
    private static final BlockState DEEPSLATE_EMERALD_ORE = Blocks.DEEPSLATE_EMERALD_ORE.defaultBlockState();
    
    // Custom Witcher ores
    private static final BlockState SILVER_ORE = ModBlocks.SILVER_ORE.defaultBlockState();
    private static final BlockState DEEPSLATE_SILVER_ORE = ModBlocks.DEEPSLATE_SILVER_ORE.defaultBlockState();
    private static final BlockState METEORITE_ORE = ModBlocks.METEORITE_ORE.defaultBlockState();
    private static final BlockState DARK_IRON_ORE = ModBlocks.DARK_IRON_ORE.defaultBlockState();
    private static final BlockState DEEPSLATE_DARK_IRON_ORE = ModBlocks.DEEPSLATE_DARK_IRON_ORE.defaultBlockState();
    private static final BlockState DIMERITIUM_ORE = ModBlocks.DIMERITIUM_ORE.defaultBlockState();
    private static final BlockState DEEPSLATE_DIMERITIUM_ORE = ModBlocks.DEEPSLATE_DIMERITIUM_ORE.defaultBlockState();
    
    private final RegionProvider regionProvider;
    
    public OrePlacer(long seed) {
        this.regionProvider = new RegionProvider(seed);
    }
    
    /**
     * Place ores in a chunk.
     */
    public void placeOres(WorldGenLevel level, ChunkAccess chunk, RandomSource random) {
        BlockPos chunkPos = chunk.getPos().getWorldPosition();
        int centerX = chunkPos.getX() + 8;
        int centerZ = chunkPos.getZ() + 8;
        
        // Get region for region-specific ores
        ContinentRegion region = regionProvider.getRegion(centerX, centerZ);
        
        // === VANILLA ORES (all regions) ===
        
        // Coal ore (common, Y: 0-128)
        placeOreVein(level, chunk, random, chunkPos, COAL_ORE, DEEPSLATE_COAL_ORE, 
                    20, 0, 128, 16);
        
        // Iron ore (common, Y: -64-72)
        placeOreVein(level, chunk, random, chunkPos, IRON_ORE, DEEPSLATE_IRON_ORE, 
                    20, -64, 72, 8);
        
        // Copper ore (common, Y: -16-112)
        placeOreVein(level, chunk, random, chunkPos, COPPER_ORE, DEEPSLATE_COPPER_ORE, 
                    16, -16, 112, 10);
        
        // Gold ore (uncommon, Y: -64-32)
        placeOreVein(level, chunk, random, chunkPos, GOLD_ORE, GOLD_ORE, 
                    4, -64, 32, 8);
        
        // Diamond ore (rare, Y: -64-16)
        placeOreVein(level, chunk, random, chunkPos, DIAMOND_ORE, DIAMOND_ORE, 
                    1, -64, 16, 4);
        
        // Redstone ore (common below Y: 16)
        placeOreVein(level, chunk, random, chunkPos, REDSTONE_ORE, REDSTONE_ORE, 
                    8, -64, 16, 8);
        
        // Lapis ore (uncommon, Y: -64-64)
        placeOreVein(level, chunk, random, chunkPos, LAPIS_ORE, LAPIS_ORE, 
                    2, -64, 64, 6);
        
        // === CUSTOM WITCHER ORES ===
        
        // Silver ore - ALL regions, more common in mountains (Y: 10-50)
        // Essential for Witcher silver weapons
        int silverVeins = 6;
        if (region == ContinentRegion.KAEDWEN || region == ContinentRegion.SKELLIGE) {
            silverVeins = 10; // More silver in mountainous regions
        }
        placeOreVein(level, chunk, random, chunkPos, SILVER_ORE, DEEPSLATE_SILVER_ORE, 
                    silverVeins, 10, 50, 6);
        
        // Meteorite ore - RARE, all regions (Y: 0-60) - meteorite impact sites
        // Used for meteorite silver swords
        if (random.nextFloat() < 0.02f) { // 2% chance per chunk
            placeOreVein(level, chunk, random, chunkPos, METEORITE_ORE, METEORITE_ORE, 
                        1, 0, 60, 3);
        }
        
        // Dark Iron ore - KAEDWEN only (Y: 5-30)
        // Found in the deep mines of Kaedwen
        if (region == ContinentRegion.KAEDWEN) {
            placeOreVein(level, chunk, random, chunkPos, DARK_IRON_ORE, DEEPSLATE_DARK_IRON_ORE, 
                        8, 5, 30, 5);
        }
        
        // Dimeritium ore - NILFGAARD only (Y: 20-40)
        // Anti-magic metal, strictly controlled by the Empire
        if (region == ContinentRegion.NILFGAARD) {
            placeOreVein(level, chunk, random, chunkPos, DIMERITIUM_ORE, DEEPSLATE_DIMERITIUM_ORE, 
                        4, 20, 40, 4);
        }
        
        // === REGION-SPECIFIC VANILLA ORE BONUSES ===
        
        // Emeralds more common in mountainous regions
        if (region == ContinentRegion.KAEDWEN || region == ContinentRegion.SKELLIGE) {
            placeOreVein(level, chunk, random, chunkPos, EMERALD_ORE, DEEPSLATE_EMERALD_ORE, 
                        3, 4, 32, 1);
        }
        
        // More gold in Nilfgaard (wealthy empire)
        if (region == ContinentRegion.NILFGAARD) {
            placeOreVein(level, chunk, random, chunkPos, GOLD_ORE, GOLD_ORE, 
                        4, -64, 32, 8);
        }
    }
    
    private void placeOreVein(WorldGenLevel level, ChunkAccess chunk, RandomSource random,
                                    BlockPos chunkPos, BlockState ore, BlockState deepslateOre,
                                    int veinsPerChunk, int minY, int maxY, int veinSize) {
        for (int vein = 0; vein < veinsPerChunk; vein++) {
            int x = chunkPos.getX() + random.nextInt(16);
            int y = minY + random.nextInt(maxY - minY + 1);
            int z = chunkPos.getZ() + random.nextInt(16);
            
            BlockPos center = new BlockPos(x, y, z);
            
            // Place vein
            for (int i = 0; i < veinSize; i++) {
                int dx = random.nextInt(3) - 1;
                int dy = random.nextInt(3) - 1;
                int dz = random.nextInt(3) - 1;
                
                BlockPos pos = center.offset(dx, dy, dz);
                BlockState existingState = level.getBlockState(pos);
                
                // Only replace stone or deepslate
                if (existingState.is(Blocks.STONE) || existingState.is(Blocks.DEEPSLATE)) {
                    BlockState oreToPlace = existingState.is(Blocks.DEEPSLATE) ? deepslateOre : ore;
                    level.setBlock(pos, oreToPlace, 3);
                }
            }
        }
    }
}
