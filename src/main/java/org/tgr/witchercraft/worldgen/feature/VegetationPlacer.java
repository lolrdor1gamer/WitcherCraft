package org.tgr.witchercraft.worldgen.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import org.tgr.witchercraft.registry.ModBlocks;
import org.tgr.witchercraft.worldgen.biome.WitcherBiomes;
import org.tgr.witchercraft.worldgen.region.RegionProvider;
import org.tgr.witchercraft.worldgen.region.ContinentRegion;

/**
 * Handles vegetation placement for The Continent biomes.
 * Places trees, grass, flowers, and custom Witcher herbs.
 */
public class VegetationPlacer {
    
    // Vanilla blocks
    private static final BlockState OAK_LOG = Blocks.OAK_LOG.defaultBlockState();
    private static final BlockState OAK_LEAVES = Blocks.OAK_LEAVES.defaultBlockState();
    private static final BlockState SPRUCE_LOG = Blocks.SPRUCE_LOG.defaultBlockState();
    private static final BlockState SPRUCE_LEAVES = Blocks.SPRUCE_LEAVES.defaultBlockState();
    private static final BlockState BIRCH_LOG = Blocks.BIRCH_LOG.defaultBlockState();
    private static final BlockState BIRCH_LEAVES = Blocks.BIRCH_LEAVES.defaultBlockState();
    private static final BlockState DARK_OAK_LOG = Blocks.DARK_OAK_LOG.defaultBlockState();
    private static final BlockState DARK_OAK_LEAVES = Blocks.DARK_OAK_LEAVES.defaultBlockState();
    private static final BlockState GRASS = Blocks.SHORT_GRASS.defaultBlockState();
    private static final BlockState TALL_GRASS = Blocks.TALL_GRASS.defaultBlockState();
    private static final BlockState FERN = Blocks.FERN.defaultBlockState();
    private static final BlockState LARGE_FERN = Blocks.LARGE_FERN.defaultBlockState();
    private static final BlockState DEAD_BUSH = Blocks.DEAD_BUSH.defaultBlockState();
    private static final BlockState DANDELION = Blocks.DANDELION.defaultBlockState();
    private static final BlockState POPPY = Blocks.POPPY.defaultBlockState();
    private static final BlockState BLUE_ORCHID = Blocks.BLUE_ORCHID.defaultBlockState();
    private static final BlockState ALLIUM = Blocks.ALLIUM.defaultBlockState();
    private static final BlockState AZURE_BLUET = Blocks.AZURE_BLUET.defaultBlockState();
    private static final BlockState OXEYE_DAISY = Blocks.OXEYE_DAISY.defaultBlockState();
    private static final BlockState CORNFLOWER = Blocks.CORNFLOWER.defaultBlockState();
    private static final BlockState LILY_OF_THE_VALLEY = Blocks.LILY_OF_THE_VALLEY.defaultBlockState();
    private static final BlockState LILAC = Blocks.LILAC.defaultBlockState();
    private static final BlockState ROSE_BUSH = Blocks.ROSE_BUSH.defaultBlockState();
    private static final BlockState PEONY = Blocks.PEONY.defaultBlockState();
    
    // Custom Witcher herbs
    private static final BlockState CROWS_EYE = ModBlocks.CROWS_EYE_BLOCK.defaultBlockState();
    private static final BlockState BLOWBALL = ModBlocks.BLOWBALL_BLOCK.defaultBlockState();
    private static final BlockState WHITE_MYRTLE = ModBlocks.WHITE_MYRTLE_BLOCK.defaultBlockState();
    private static final BlockState WOLFSBANE = ModBlocks.WOLFSBANE_BLOCK.defaultBlockState();
    private static final BlockState MANDRAKE_ROOT = ModBlocks.MANDRAKE_ROOT_BLOCK.defaultBlockState();
    private static final BlockState SEWANT_MUSHROOM = ModBlocks.SEWANT_MUSHROOM_BLOCK.defaultBlockState();
    private static final BlockState VERBENA = ModBlocks.VERBENA_BLOCK.defaultBlockState();
    private static final BlockState FOOLS_PARSLEY = ModBlocks.FOOLS_PARSLEY_BLOCK.defaultBlockState();
    private static final BlockState BERBERCANE_FRUIT = ModBlocks.BERBERCANE_FRUIT_BLOCK.defaultBlockState();
    private static final BlockState WORMWOOD = ModBlocks.WORMWOOD_BLOCK.defaultBlockState();
    private static final BlockState HAN = ModBlocks.HAN_BLOCK.defaultBlockState();
    
    private final RegionProvider regionProvider;
    
    public VegetationPlacer(long seed) {
        this.regionProvider = new RegionProvider(seed);
    }
    
    /**
     * Place vegetation in a chunk based on biome.
     */
    public void placeVegetation(WorldGenLevel level, ChunkAccess chunk, 
                                      RandomSource random, Biome biome) {
        BlockPos chunkPos = chunk.getPos().getWorldPosition();
        int centerX = chunkPos.getX() + 8;
        int centerZ = chunkPos.getZ() + 8;
        
        // Get region for region-specific herbs
        ContinentRegion region = regionProvider.getRegion(centerX, centerZ);
        
        float temperature = biome.getBaseTemperature();
        float downfall = 0.5f; // Use a default since downfall isn't directly accessible
        
        // Determine vegetation density based on biome
        int attempts = getVegetationAttempts(temperature, downfall);
        
        // Determine biome type and place appropriate vegetation
        for (int attempt = 0; attempt < attempts; attempt++) {
            int x = chunkPos.getX() + random.nextInt(16);
            int z = chunkPos.getZ() + random.nextInt(16);
            int y = chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x & 15, z & 15);
            
            BlockPos pos = new BlockPos(x, y, z);
            BlockState ground = chunk.getBlockState(pos.below());
            
            // Only place on grass blocks
            if (!ground.is(Blocks.GRASS_BLOCK)) {
                continue;
            }
            
            // Place Witcher herbs (5% chance per vegetation attempt)
            if (random.nextFloat() < 0.05f) {
                placeHerb(level, pos, random, region, temperature, downfall);
                continue;
            }
            
            // Place trees with biome-dependent probability
            float treeChance = getTreeChance(temperature, downfall);
            if (random.nextFloat() < treeChance) {
                placeTree(level, pos, random, biome);
            }
            // Place grass/flowers
            else if (random.nextFloat() < 0.4f) {
                placeGrass(level, pos, random, biome);
            }
        }
    }
    
    /**
     * Place a Witcher alchemy herb based on region and biome characteristics.
     */
    private void placeHerb(WorldGenLevel level, BlockPos pos, RandomSource random,
                          ContinentRegion region, float temperature, float downfall) {
        BlockState herb = selectHerb(random, region, temperature, downfall);
        if (herb != null && level.isEmptyBlock(pos)) {
            level.setBlock(pos, herb, 3);
        }
    }
    
    /**
     * Select an appropriate herb based on region and climate.
     * Each herb has specific biome/region requirements per WORLDGEN_SYSTEM.md.
     */
    private BlockState selectHerb(RandomSource random, ContinentRegion region, 
                                  float temperature, float downfall) {
        // Battlefield biomes (detected via temperature check - low temp + low rain)
        // Wormwood and Han grow on battlefields
        if (temperature < 0.4f && downfall < 0.4f) {
            return random.nextBoolean() ? WORMWOOD : HAN;
        }
        
        // Swamps (high humidity, moderate temp)
        // Crow's Eye, Blowball, White Myrtle
        if (downfall > 0.7f) {
            return switch (random.nextInt(3)) {
                case 0 -> CROWS_EYE;
                case 1 -> BLOWBALL;
                default -> WHITE_MYRTLE;
            };
        }
        
        // Mountains (cold, any humidity) - Sewant Mushroom, Verbena
        if (temperature < 0.3f) {
            return random.nextBoolean() ? SEWANT_MUSHROOM : VERBENA;
        }
        
        // Forests (moderate temp, moderate humidity) - Wolfsbane, Mandrake Root
        if (downfall > 0.5f && temperature > 0.4f && temperature < 0.7f) {
            return random.nextBoolean() ? WOLFSBANE : MANDRAKE_ROOT;
        }
        
        // Plains (warm, moderate humidity) - Berbercane Fruit
        if (temperature > 0.6f && downfall > 0.3f && downfall < 0.6f) {
            return BERBERCANE_FRUIT;
        }
        
        // Coastal areas and general - Fool's Parsley (poisonous, everywhere)
        if (random.nextFloat() < 0.1f) { // Rare everywhere
            return FOOLS_PARSLEY;
        }
        
        // Region-specific overrides
        switch (region) {
            case SKELLIGE:
                // Harsh climate - Sewant Mushroom, Verbena in caves
                return random.nextBoolean() ? SEWANT_MUSHROOM : VERBENA;
                
            case KAEDWEN:
                // Mountain region - Sewant Mushroom, Verbena, some Wolfsbane
                return switch (random.nextInt(3)) {
                    case 0 -> SEWANT_MUSHROOM;
                    case 1 -> VERBENA;
                    default -> WOLFSBANE;
                };
                
            case NILFGAARD:
                // Warm southern empire - Berbercane, White Myrtle
                return random.nextBoolean() ? BERBERCANE_FRUIT : WHITE_MYRTLE;
                
            case TEMERIA:
                // Farmlands - diverse herbs
                return switch (random.nextInt(4)) {
                    case 0 -> WOLFSBANE;
                    case 1 -> MANDRAKE_ROOT;
                    case 2 -> BERBERCANE_FRUIT;
                    default -> VERBENA;
                };
                
            case NO_MANS_LAND:
                // War-torn, swampy - Crow's Eye, Blowball, Wormwood
                return switch (random.nextInt(3)) {
                    case 0 -> CROWS_EYE;
                    case 1 -> BLOWBALL;
                    default -> WORMWOOD;
                };
                
            case REDANIA:
                // Northern kingdom - Wolfsbane, Verbena
                return random.nextBoolean() ? WOLFSBANE : VERBENA;
                
            case AEDIRN:
                // Eastern lands - Mandrake Root, Berbercane
                return random.nextBoolean() ? MANDRAKE_ROOT : BERBERCANE_FRUIT;
                
            default:
                // Default: random common herb
                return switch (random.nextInt(5)) {
                    case 0 -> CROWS_EYE;
                    case 1 -> BLOWBALL;
                    case 2 -> WOLFSBANE;
                    case 3 -> VERBENA;
                    default -> BERBERCANE_FRUIT;
                };
        }
    }
    
    /**
     * Determine vegetation attempt count based on climate.
     */
    private static int getVegetationAttempts(float temperature, float downfall) {
        // More humid = more vegetation
        // Temperate regions have most vegetation
        if (downfall > 0.7 && temperature > 0.5) {
            return 24; // Dense forests, marshes
        } else if (downfall > 0.5 || temperature > 0.6) {
            return 18; // Normal vegetation
        } else {
            return 12; // Sparse vegetation
        }
    }
    
    /**
     * Determine tree spawn chance based on climate.
     */
    private static float getTreeChance(float temperature, float downfall) {
        // Forests have more trees, scrublands have few
        if (downfall > 0.7) {
            return 0.12f; // Dense forests
        } else if (downfall > 0.5) {
            return 0.06f; // Normal forests
        } else {
            return 0.02f; // Sparse trees
        }
    }
    
    private static void placeTree(WorldGenLevel level, BlockPos pos, 
                                 RandomSource random, Biome biome) {
        float temperature = biome.getBaseTemperature();
        float downfall = 0.5f; // Default value
        
        // Simple tree (4-7 blocks tall)
        int height = 4 + random.nextInt(4);
        
        BlockState logType;
        BlockState leafType;
        
        // Select tree type based on climate
        if (temperature < 0.3) {
            // Cold biomes - spruce
            logType = SPRUCE_LOG;
            leafType = SPRUCE_LEAVES;
        } else if (downfall > 0.75 && temperature > 0.6) {
            // Very humid warm areas - dark oak (dense forests)
            logType = DARK_OAK_LOG;
            leafType = DARK_OAK_LEAVES;
            height += 1; // Taller
        } else if (temperature > 0.6 && downfall < 0.6) {
            // Warm dry areas - birch
            logType = BIRCH_LOG;
            leafType = BIRCH_LEAVES;
        } else {
            // Default - oak
            logType = OAK_LOG;
            leafType = OAK_LEAVES;
        }
        
        // Place trunk
        for (int y = 0; y < height; y++) {
            level.setBlock(pos.above(y), logType, 3);
        }
        
        // Place leaves (simple sphere)
        BlockPos leafPos = pos.above(height - 1);
        int leafRadius = 2;
        if (logType == DARK_OAK_LOG) {
            leafRadius = 3; // Bigger canopy for dark oak
        }
        
        for (int x = -leafRadius; x <= leafRadius; x++) {
            for (int y = -leafRadius; y <= leafRadius; y++) {
                for (int z = -leafRadius; z <= leafRadius; z++) {
                    if (Math.abs(x) + Math.abs(y) + Math.abs(z) <= leafRadius + 1) {
                        BlockPos p = leafPos.offset(x, y, z);
                        if (level.isEmptyBlock(p)) {
                            level.setBlock(p, leafType, 3);
                        }
                    }
                }
            }
        }
    }
    
    private static void placeGrass(WorldGenLevel level, BlockPos pos, 
                                  RandomSource random, Biome biome) {
        float temperature = biome.getBaseTemperature();
        float downfall = 0.5f; // Default value
        
        BlockState plant;
        
        if (isDryBiome(biome)) {
            // Dry biomes - dead bushes
            plant = DEAD_BUSH;
        } else if (random.nextFloat() < 0.15f) {
            // 15% chance for flowers - variety based on climate
            plant = selectFlower(random, temperature, downfall);
        } else if (downfall > 0.7 && random.nextFloat() < 0.4f) {
            // Humid biomes - more ferns
            plant = random.nextBoolean() ? FERN : LARGE_FERN;
        } else {
            // Standard grass
            plant = random.nextBoolean() ? GRASS : TALL_GRASS;
        }
        
        if (level.isEmptyBlock(pos)) {
            level.setBlock(pos, plant, 3);
        }
    }
    
    /**
     * Select a flower based on climate.
     */
    private static BlockState selectFlower(RandomSource random, float temperature, float downfall) {
        // Warm humid areas - more variety
        if (temperature > 0.7 && downfall > 0.6) {
            return switch (random.nextInt(7)) {
                case 0 -> BLUE_ORCHID;
                case 1 -> ALLIUM;
                case 2 -> LILAC;
                case 3 -> ROSE_BUSH;
                case 4 -> PEONY;
                case 5 -> LILY_OF_THE_VALLEY;
                default -> DANDELION;
            };
        }
        // Temperate areas - common flowers
        else if (temperature > 0.4) {
            return switch (random.nextInt(6)) {
                case 0 -> DANDELION;
                case 1 -> POPPY;
                case 2 -> AZURE_BLUET;
                case 3 -> OXEYE_DAISY;
                case 4 -> CORNFLOWER;
                default -> DANDELION;
            };
        }
        // Cold areas - simple flowers
        else {
            return random.nextBoolean() ? DANDELION : POPPY;
        }
    }
    
    private static boolean isColdBiome(Biome biome) {
        return biome.getBaseTemperature() < 0.3f;
    }
    
    private static boolean isDryBiome(Biome biome) {
        // Dry biome detection based on temperature (can't easily access downfall)
        return biome.getBaseTemperature() > 0.9f;
    }
}

