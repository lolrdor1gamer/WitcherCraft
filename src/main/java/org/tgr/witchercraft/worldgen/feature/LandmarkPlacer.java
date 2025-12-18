package org.tgr.witchercraft.worldgen.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import org.tgr.witchercraft.registry.ModBlocks;
import org.tgr.witchercraft.worldgen.region.RegionProvider;
import org.tgr.witchercraft.worldgen.region.ContinentRegion;

/**
 * Handles landmark placement for The Continent.
 * Places Places of Power, Ancient Trees, and Monster Nests.
 * 
 * Per WORLDGEN_SYSTEM.md:
 * - Places of Power: 1 per ~2000 blocks (rare)
 * - Ancient Trees: 1 per ~1500 blocks (uncommon)
 * - Monster Nests: Variable based on region danger level
 */
public class LandmarkPlacer {
    
    // Place of Power blocks
    private static final BlockState PLACE_OF_POWER_STONE = ModBlocks.PLACE_OF_POWER_STONE.defaultBlockState();
    private static final BlockState PLACE_OF_POWER_RUNE_STONE = ModBlocks.PLACE_OF_POWER_RUNE_STONE.defaultBlockState();
    private static final BlockState PLACE_OF_POWER_PEDESTAL = ModBlocks.PLACE_OF_POWER_PEDESTAL.defaultBlockState();
    
    // Monster nest blocks
    private static final BlockState NEKKER_NEST = ModBlocks.NEKKER_NEST.defaultBlockState();
    private static final BlockState GHOUL_NEST = ModBlocks.GHOUL_NEST.defaultBlockState();
    private static final BlockState WRAITH_CURSED_STONE = ModBlocks.WRAITH_CURSED_STONE.defaultBlockState();
    // TODO: Add DROWNER_NEST when block is created - using NEKKER_NEST as placeholder for now
    
    private final RegionProvider regionProvider;
    private final long seed;
    
    public LandmarkPlacer(long seed) {
        this.seed = seed;
        this.regionProvider = new RegionProvider(seed);
    }
    
    /**
     * Place landmarks in a chunk.
     */
    public void placeLandmarks(WorldGenLevel level, ChunkAccess chunk, RandomSource random) {
        BlockPos chunkPos = chunk.getPos().getWorldPosition();
        int centerX = chunkPos.getX() + 8;
        int centerZ = chunkPos.getZ() + 8;
        
        // Get region for region-specific landmarks
        ContinentRegion region = regionProvider.getRegion(centerX, centerZ);
        
        // Check for Place of Power (very rare, ~1 per 125 chunks = 2000 blocks)
        if (shouldPlaceLandmark(centerX, centerZ, 125)) {
            placePlaceOfPower(level, chunk, random, region);
        }
        
        // Check for Ancient Tree (uncommon, ~1 per 90 chunks = 1440 blocks)
        // Only in forested regions
        if (isForestRegion(region) && shouldPlaceLandmark(centerX + 1000, centerZ + 1000, 90)) {
            placeAncientTree(level, chunk, random);
        }
        
        // Check for Monster Nests (variable based on region danger)
        float nestChance = getMonsterNestChance(region);
        if (random.nextFloat() < nestChance) {
            placeMonsterNest(level, chunk, random, region);
        }
    }
    
    /**
     * Deterministic check if a landmark should be placed at chunk.
     * Uses consistent hashing so landmarks always generate in the same chunks.
     */
    private boolean shouldPlaceLandmark(int x, int z, int rarity) {
        // Hash the position for deterministic placement
        long hash = x * 341873128712L + z * 132897987541L + seed * 984756312L;
        hash = hash ^ (hash >> 17);
        hash = hash * 0x94d049bb133111ebL;
        
        int mod = (int) (Math.abs(hash) % rarity);
        return mod == 0;
    }
    
    /**
     * Place a Place of Power structure.
     * Creates a small stone circle with rune stones and a central pedestal.
     */
    private void placePlaceOfPower(WorldGenLevel level, ChunkAccess chunk, 
                                   RandomSource random, ContinentRegion region) {
        // Find a suitable spot
        int x = chunk.getPos().getMinBlockX() + 4 + random.nextInt(8);
        int z = chunk.getPos().getMinBlockZ() + 4 + random.nextInt(8);
        int y = chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x & 15, z & 15);
        
        BlockPos center = new BlockPos(x, y, z);
        
        // Check if ground is suitable
        if (!level.getBlockState(center.below()).is(Blocks.GRASS_BLOCK) &&
            !level.getBlockState(center.below()).is(Blocks.STONE)) {
            return;
        }
        
        // Clear the area
        for (int dx = -3; dx <= 3; dx++) {
            for (int dz = -3; dz <= 3; dz++) {
                for (int dy = 0; dy < 4; dy++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    if (!level.getBlockState(pos).is(Blocks.STONE) && 
                        !level.getBlockState(pos).is(Blocks.DIRT)) {
                        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    }
                }
            }
        }
        
        // Place floor stones
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                if (Math.abs(dx) + Math.abs(dz) <= 3) {
                    level.setBlock(center.offset(dx, -1, dz), PLACE_OF_POWER_STONE, 3);
                }
            }
        }
        
        // Place rune stones in a circle (at the 4 cardinal directions and corners)
        level.setBlock(center.offset(2, 0, 0), PLACE_OF_POWER_RUNE_STONE, 3);
        level.setBlock(center.offset(-2, 0, 0), PLACE_OF_POWER_RUNE_STONE, 3);
        level.setBlock(center.offset(0, 0, 2), PLACE_OF_POWER_RUNE_STONE, 3);
        level.setBlock(center.offset(0, 0, -2), PLACE_OF_POWER_RUNE_STONE, 3);
        
        // Place central pedestal
        level.setBlock(center, PLACE_OF_POWER_PEDESTAL, 3);
    }
    
    /**
     * Place an Ancient Tree (large, magical tree).
     */
    private void placeAncientTree(WorldGenLevel level, ChunkAccess chunk, RandomSource random) {
        int x = chunk.getPos().getMinBlockX() + 4 + random.nextInt(8);
        int z = chunk.getPos().getMinBlockZ() + 4 + random.nextInt(8);
        int y = chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x & 15, z & 15);
        
        BlockPos base = new BlockPos(x, y, z);
        
        // Check if ground is suitable
        if (!level.getBlockState(base.below()).is(Blocks.GRASS_BLOCK)) {
            return;
        }
        
        // Create a massive dark oak tree
        BlockState log = Blocks.DARK_OAK_LOG.defaultBlockState();
        BlockState leaves = Blocks.DARK_OAK_LEAVES.defaultBlockState();
        
        // Trunk - thick at base, thinner at top
        int height = 12 + random.nextInt(6);
        
        // Base (2x2 trunk)
        for (int dy = 0; dy < height / 2; dy++) {
            level.setBlock(base.offset(0, dy, 0), log, 3);
            level.setBlock(base.offset(1, dy, 0), log, 3);
            level.setBlock(base.offset(0, dy, 1), log, 3);
            level.setBlock(base.offset(1, dy, 1), log, 3);
        }
        
        // Upper trunk (1 block)
        for (int dy = height / 2; dy < height; dy++) {
            level.setBlock(base.offset(0, dy, 0), log, 3);
        }
        
        // Massive leaf canopy
        int leafRadius = 5 + random.nextInt(2);
        BlockPos leafCenter = base.above(height - 2);
        
        for (int dx = -leafRadius; dx <= leafRadius; dx++) {
            for (int dy = -leafRadius / 2; dy <= leafRadius / 2; dy++) {
                for (int dz = -leafRadius; dz <= leafRadius; dz++) {
                    double dist = Math.sqrt(dx * dx + dy * dy * 4 + dz * dz);
                    if (dist <= leafRadius && random.nextFloat() < 0.8f) {
                        BlockPos leafPos = leafCenter.offset(dx, dy, dz);
                        if (level.isEmptyBlock(leafPos)) {
                            level.setBlock(leafPos, leaves, 3);
                        }
                    }
                }
            }
        }
        
        // Add roots at base
        for (int i = 0; i < 4; i++) {
            int rx = random.nextInt(3) - 1;
            int rz = random.nextInt(3) - 1;
            if (rx != 0 || rz != 0) {
                level.setBlock(base.offset(rx * 2, 0, rz * 2), log, 3);
                if (random.nextBoolean()) {
                    level.setBlock(base.offset(rx * 2, 1, rz * 2), log, 3);
                }
            }
        }
    }
    
    /**
     * Place a Monster Nest based on region.
     */
    private void placeMonsterNest(WorldGenLevel level, ChunkAccess chunk, 
                                  RandomSource random, ContinentRegion region) {
        int x = chunk.getPos().getMinBlockX() + random.nextInt(16);
        int z = chunk.getPos().getMinBlockZ() + random.nextInt(16);
        int y = chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x & 15, z & 15);
        
        BlockPos pos = new BlockPos(x, y, z);
        
        // Select nest type based on region
        BlockState nest = selectNestType(region, random);
        
        // Check if ground is suitable
        BlockState ground = level.getBlockState(pos.below());
        if (!ground.is(Blocks.GRASS_BLOCK) && !ground.is(Blocks.DIRT) && 
            !ground.is(Blocks.MUD) && !ground.is(Blocks.SAND)) {
            return;
        }
        
        // Place the nest
        level.setBlock(pos, nest, 3);
        
        // Some nests have additional blocks around them
        if (nest == NEKKER_NEST) {
            // Nekker nests have dirt mounds
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (random.nextFloat() < 0.5f && (dx != 0 || dz != 0)) {
                        BlockPos moundPos = pos.offset(dx, 0, dz);
                        if (level.isEmptyBlock(moundPos)) {
                            level.setBlock(moundPos, Blocks.COARSE_DIRT.defaultBlockState(), 3);
                        }
                    }
                }
            }
        } else if (nest == GHOUL_NEST) {
            // Ghoul nests have bone blocks around them
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (random.nextFloat() < 0.3f && (dx != 0 || dz != 0)) {
                        BlockPos bonePos = pos.offset(dx, 0, dz);
                        if (level.isEmptyBlock(bonePos)) {
                            level.setBlock(bonePos, Blocks.BONE_BLOCK.defaultBlockState(), 3);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Select monster nest type based on region.
     */
    private BlockState selectNestType(ContinentRegion region, RandomSource random) {
        return switch (region) {
            case TEMERIA, REDANIA, AEDIRN -> {
                // Farmlands - Nekkers in forests, some ghouls
                yield random.nextFloat() < 0.7f ? NEKKER_NEST : GHOUL_NEST;
            }
            case KAEDWEN, SKELLIGE -> {
                // Cold regions - more wraiths and ghouls
                yield random.nextFloat() < 0.5f ? WRAITH_CURSED_STONE : GHOUL_NEST;
            }
            case NO_MANS_LAND -> {
                // War-torn swamps - nekkers (placeholder for drowners) and ghouls
                yield random.nextFloat() < 0.5f ? NEKKER_NEST : // TODO: Change to DROWNER_NEST
                      (random.nextFloat() < 0.5f ? GHOUL_NEST : NEKKER_NEST);
            }
            case NILFGAARD -> {
                // Nilfgaard - fewer nests, mostly controlled
                yield random.nextFloat() < 0.5f ? NEKKER_NEST : GHOUL_NEST;
            }
            default -> NEKKER_NEST;
        };
    }
    
    /**
     * Get monster nest spawn chance based on region danger level.
     */
    private float getMonsterNestChance(ContinentRegion region) {
        return switch (region) {
            case NO_MANS_LAND -> 0.08f;  // 8% - very dangerous
            case SKELLIGE -> 0.05f;       // 5% - harsh wilderness
            case KAEDWEN -> 0.04f;        // 4% - mountain wilds
            case TEMERIA, REDANIA -> 0.02f; // 2% - settled lands
            case AEDIRN -> 0.02f;         // 2% - settled but frontier
            case NILFGAARD -> 0.01f;      // 1% - well-patrolled empire
            default -> 0.02f;
        };
    }
    
    /**
     * Check if region is forested (for Ancient Tree placement).
     */
    private boolean isForestRegion(ContinentRegion region) {
        return switch (region) {
            case TEMERIA, REDANIA, AEDIRN, NO_MANS_LAND -> true;
            default -> false;
        };
    }
}
