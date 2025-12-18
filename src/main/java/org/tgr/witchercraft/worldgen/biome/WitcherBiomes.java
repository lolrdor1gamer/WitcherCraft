package org.tgr.witchercraft.worldgen.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.tgr.witchercraft.Witchercraft;

/**
 * Defines all custom biomes for The Continent.
 * Note: Biome creation in 1.21.1 requires HolderGetter access during bootstrap.
 */
public class WitcherBiomes {
    
    // Temeria Biomes
    public static final ResourceKey<Biome> TEMERIAN_FARMLANDS = register("temerian_farmlands");
    public static final ResourceKey<Biome> MAHAKAMAN_FOOTHILLS = register("mahakaman_foothills");
    public static final ResourceKey<Biome> PONTAR_RIVERLANDS = register("pontar_riverlands");

    // Kaedwen Biomes
    public static final ResourceKey<Biome> MOUNTAIN_PEAKS = register("mountain_peaks");
    public static final ResourceKey<Biome> FROZEN_TAIGA = register("frozen_taiga");
    public static final ResourceKey<Biome> MINE_VALLEYS = register("mine_valleys");
    public static final ResourceKey<Biome> GLACIAL_LAKES = register("glacial_lakes");

    // Redania Biomes
    public static final ResourceKey<Biome> REDANIAN_PLAINS = register("redanian_plains");
    public static final ResourceKey<Biome> OXENFURT_LAKES = register("oxenfurt_lakes");

    // Nilfgaard Biomes
    public static final ResourceKey<Biome> NILFGAARDIAN_SCRUBLAND = register("nilfgaardian_scrubland");
    public static final ResourceKey<Biome> SOUTHERN_PLAINS = register("southern_plains");
    public static final ResourceKey<Biome> OLIVE_GROVES = register("olive_groves");
    public static final ResourceKey<Biome> IMPERIAL_GARDENS = register("imperial_gardens");
    public static final ResourceKey<Biome> ARID_SCRUBLAND = register("arid_scrubland");

    // Skellige Biomes
    public static final ResourceKey<Biome> SKELLIGE_COAST = register("skellige_coast");
    public static final ResourceKey<Biome> ROCKY_COASTLINES = register("rocky_coastlines");
    public static final ResourceKey<Biome> TUNDRA_PLAINS = register("tundra_plains");
    public static final ResourceKey<Biome> GLACIER_FIELDS = register("glacier_fields");
    public static final ResourceKey<Biome> STORM_SEAS = register("storm_seas");

    // No Man's Land
    public static final ResourceKey<Biome> NO_MANS_LAND = register("no_mans_land");
    
    // Special Biomes (Cross-Regional)
    public static final ResourceKey<Biome> BATTLEFIELD = register("battlefield");
    public static final ResourceKey<Biome> CURSED_SWAMP = register("cursed_swamp");
    public static final ResourceKey<Biome> ANCIENT_RUINS = register("ancient_ruins");
    
    // Existing Phase 3 Biomes
    public static final ResourceKey<Biome> BROKILON_FOREST = register("brokilon_forest");
    public static final ResourceKey<Biome> VELEN_MARSHES = register("velen_marshes");
    public static final ResourceKey<Biome> TOUSSAINT_VINEYARDS = register("toussaint_vineyards");
    public static final ResourceKey<Biome> MAHAKAMAN_MOUNTAINS = register("mahakaman_mountains");

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(Witchercraft.MOD_ID, name));
    }

    /**
     * Bootstrap method called during data generation.
     */
    public static void bootstrap(net.minecraft.data.worldgen.BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);
        
        context.register(TEMERIAN_FARMLANDS, createTemerianFarmlands(placedFeatures, carvers));
        context.register(MAHAKAMAN_FOOTHILLS, createMahakamanFoothills(placedFeatures, carvers));
        context.register(PONTAR_RIVERLANDS, createPontarRiverlands(placedFeatures, carvers));
        context.register(MOUNTAIN_PEAKS, createMountainPeaks(placedFeatures, carvers));
        context.register(FROZEN_TAIGA, createFrozenTaiga(placedFeatures, carvers));
        context.register(REDANIAN_PLAINS, createRedanianPlains(placedFeatures, carvers));
        context.register(NILFGAARDIAN_SCRUBLAND, createNilfgaardianScrubland(placedFeatures, carvers));
        context.register(SKELLIGE_COAST, createSkelligeCoast(placedFeatures, carvers));
        context.register(NO_MANS_LAND, createNoMansLand(placedFeatures, carvers));
        
        // Existing Phase 3 biomes
        context.register(BROKILON_FOREST, brokilonForest(placedFeatures, carvers));
        context.register(VELEN_MARSHES, velenMarshes(placedFeatures, carvers));
        context.register(TOUSSAINT_VINEYARDS, toussaintVineyards(placedFeatures, carvers));
        context.register(MAHAKAMAN_MOUNTAINS, mahakamanMountains(placedFeatures, carvers));
        
        // NEW BIOMES - Per WORLDGEN_SYSTEM.md design
        // Kaedwen
        context.register(MINE_VALLEYS, createMineValleys(placedFeatures, carvers));
        context.register(GLACIAL_LAKES, createGlacialLakes(placedFeatures, carvers));
        
        // Redania
        context.register(OXENFURT_LAKES, createOxenfurtLakes(placedFeatures, carvers));
        
        // Nilfgaard
        context.register(SOUTHERN_PLAINS, createSouthernPlains(placedFeatures, carvers));
        context.register(OLIVE_GROVES, createOliveGroves(placedFeatures, carvers));
        context.register(IMPERIAL_GARDENS, createImperialGardens(placedFeatures, carvers));
        context.register(ARID_SCRUBLAND, createAridScrubland(placedFeatures, carvers));
        
        // Skellige
        context.register(ROCKY_COASTLINES, createRockyCoastlines(placedFeatures, carvers));
        context.register(TUNDRA_PLAINS, createTundraPlains(placedFeatures, carvers));
        context.register(GLACIER_FIELDS, createGlacierFields(placedFeatures, carvers));
        context.register(STORM_SEAS, createStormSeas(placedFeatures, carvers));
        
        // Special Cross-Regional Biomes
        context.register(BATTLEFIELD, createBattlefield(placedFeatures, carvers));
        context.register(CURSED_SWAMP, createCursedSwamp(placedFeatures, carvers));
        context.register(ANCIENT_RUINS, createAncientRuins(placedFeatures, carvers));
    }
    
    // ========== TEMERIA BIOMES ==========
    
    private static Biome createTemerianFarmlands(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        
        // Passive mobs (farm animals) - addSpawn(category, weight, spawnerData)
        spawnSettings.addSpawn(MobCategory.CREATURE, 12, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 4, 4));
        spawnSettings.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.PIG, 4, 4));
        spawnSettings.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.COW, 4, 4));
        spawnSettings.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 4, 4));
        
        // Hostile mobs (low spawn rate - civilized area)
        spawnSettings.addSpawn(MobCategory.MONSTER, 2, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 1, 2));
        spawnSettings.addSpawn(MobCategory.MONSTER, 2, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 1, 2));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        
        // Add default features
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addDefaultSoftDisks(generationSettings);
        BiomeDefaultFeatures.addPlainGrass(generationSettings);
        BiomeDefaultFeatures.addDefaultFlowers(generationSettings);
        BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
        
        // Farmland-specific: more grass, flowers
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_PLAINS);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.6f)
            .downfall(0.5f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x3F76E4)
                .waterFogColor(0x050533)
                .fogColor(0xC0D8FF)
                .skyColor(0x78A7FF)
                .grassColorOverride(0x79C05A)
                .foliageColorOverride(0x59AE30)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    private static Biome createMahakamanFoothills(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        
        // Forest creatures
        spawnSettings.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 1, 3));
        spawnSettings.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 3));
        
        // Monsters (moderate)
        spawnSettings.addSpawn(MobCategory.MONSTER, 5, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 1, 3));
        spawnSettings.addSpawn(MobCategory.MONSTER, 5, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 1, 3));
        spawnSettings.addSpawn(MobCategory.MONSTER, 4, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 1, 2));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addDefaultSoftDisks(generationSettings);
        BiomeDefaultFeatures.addForestFlowers(generationSettings);
        BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
        
        // Hills: More trees (birch and oak)
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_BIRCH);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.6f)
            .downfall(0.6f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x3F76E4)
                .waterFogColor(0x050533)
                .fogColor(0xC0D8FF)
                .skyColor(0x78A7FF)
                .grassColorOverride(0x6BA043)
                .foliageColorOverride(0x4C9F2F)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    private static Biome createPontarRiverlands(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        
        // Water creatures
        spawnSettings.addSpawn(MobCategory.WATER_CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 3, 5));
        spawnSettings.addSpawn(MobCategory.WATER_AMBIENT, 15, new MobSpawnSettings.SpawnerData(EntityType.COD, 3, 6));
        spawnSettings.addSpawn(MobCategory.MONSTER, 8, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 1, 3));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addSwampClayDisk(generationSettings);
        BiomeDefaultFeatures.addSwampVegetation(generationSettings);
        BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.6f)
            .downfall(0.8f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x4C6559)
                .waterFogColor(0x0C1F16)
                .fogColor(0xC0D8FF)
                .skyColor(0x78A7FF)
                .grassColorOverride(0x6A7039)
                .foliageColorOverride(0x6A7039)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    // ========== KAEDWEN BIOMES ==========
    
    private static Biome createMountainPeaks(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        
        spawnSettings.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityType.GOAT, 1, 3));
        spawnSettings.addSpawn(MobCategory.MONSTER, 3, new MobSpawnSettings.SpawnerData(EntityType.STRAY, 1, 2));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addExtraGold(generationSettings);
        BiomeDefaultFeatures.addExtraEmeralds(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.0f)
            .downfall(0.5f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x3938C9)
                .waterFogColor(0x050533)
                .fogColor(0xC0D8FF)
                .skyColor(0x8AB8FF)
                .grassColorOverride(0x80B497)
                .foliageColorOverride(0x60A17B)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    private static Biome createFrozenTaiga(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        
        spawnSettings.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 2, 4));
        spawnSettings.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 3));
        spawnSettings.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.FOX, 1, 2));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addTaigaTrees(generationSettings);
        BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.2f)
            .downfall(0.4f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x3938C9)
                .waterFogColor(0x050533)
                .fogColor(0xC0D8FF)
                .skyColor(0x8AB8FF)
                .grassColorOverride(0x80B497)
                .foliageColorOverride(0x60A17B)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    // ========== OTHER REGIONS ==========
    
    private static Biome createRedanianPlains(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        
        // Rich agricultural lands - many farm animals
        spawnSettings.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 3, 5));
        spawnSettings.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.COW, 2, 4));
        spawnSettings.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 2, 4));
        spawnSettings.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(EntityType.PIG, 2, 3));
        // Moderate monster spawns - patrolled area
        spawnSettings.addSpawn(MobCategory.MONSTER, 4, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 1, 2));
        spawnSettings.addSpawn(MobCategory.MONSTER, 4, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 1, 2));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addPlainGrass(generationSettings);
        BiomeDefaultFeatures.addDefaultFlowers(generationSettings);
        BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.4f)  // Colder than Temeria
            .downfall(0.5f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x3F76E4)
                .waterFogColor(0x050533)
                .fogColor(0xC8D8E8)
                .skyColor(0x7BA4CC)
                .grassColorOverride(0x79B05A)  // Slightly different green
                .foliageColorOverride(0x59A030)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    private static Biome createNilfgaardianScrubland(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        
        // Hot dry climate - different creature mix
        spawnSettings.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 4));
        spawnSettings.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.ARMADILLO, 1, 2));
        spawnSettings.addSpawn(MobCategory.MONSTER, 6, new MobSpawnSettings.SpawnerData(EntityType.HUSK, 1, 3));  // Desert zombies
        spawnSettings.addSpawn(MobCategory.MONSTER, 4, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 1, 2));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addDesertVegetation(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(false)  // Dry climate
            .temperature(0.9f)  // Hot
            .downfall(0.2f)  // Arid
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x3F76E4)
                .waterFogColor(0x050533)
                .fogColor(0xD8D0C0)  // Dusty
                .skyColor(0x7EC0D0)
                .grassColorOverride(0xB0A060)  // Yellow-brown grass
                .foliageColorOverride(0x9A8A40)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    private static Biome createSkelligeCoast(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        
        // Norse-inspired coastal area
        spawnSettings.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(EntityType.POLAR_BEAR, 1, 2));
        spawnSettings.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 2, 4));
        spawnSettings.addSpawn(MobCategory.WATER_CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.COD, 3, 6));
        spawnSettings.addSpawn(MobCategory.MONSTER, 6, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 2, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, 4, new MobSpawnSettings.SpawnerData(EntityType.STRAY, 1, 2));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addSnowyTrees(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.1f)  // Cold
            .downfall(0.6f)  // Wet from ocean
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x2C4A6A)  // Cold ocean
                .waterFogColor(0x050520)
                .fogColor(0xA8B8C8)
                .skyColor(0x7A98B8)
                .grassColorOverride(0x70907A)
                .foliageColorOverride(0x508060)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    private static Biome createNoMansLand(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        
        // HIGH monster spawn rate
        spawnSettings.addSpawn(MobCategory.MONSTER, 15, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 2, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, 15, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 2, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, 10, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 1, 3));
        spawnSettings.addSpawn(MobCategory.MONSTER, 8, new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 1, 2));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        // Sparse vegetation
        BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.5f)
            .downfall(0.3f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x4C6559)
                .waterFogColor(0x0C1F16)
                .fogColor(0x999999)
                .skyColor(0x7A7A7A)
                .grassColorOverride(0x4C4C26)
                .foliageColorOverride(0x3A3A1C)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    // NEW BIOMES - Phase 3
    
    private static Biome brokilonForest(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        
        // Peaceful forest - minimal hostile spawns
        spawnSettings.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 2, 4));
        spawnSettings.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 3));
        spawnSettings.addSpawn(MobCategory.MONSTER, 2, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 1, 2));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addForestFlowers(generationSettings);
        BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.7f)
            .downfall(0.8f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x3F76E4)
                .waterFogColor(0x050533)
                .fogColor(0xC0D8FF)
                .skyColor(0x77ADFF)
                .grassColorOverride(0x79C05A)
                .foliageColorOverride(0x59AE30)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    private static Biome velenMarshes(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        
        // Dangerous swamp
        spawnSettings.addSpawn(MobCategory.MONSTER, 12, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 2, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, 10, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 2, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, 8, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 1, 3));
        spawnSettings.addSpawn(MobCategory.MONSTER, 5, new MobSpawnSettings.SpawnerData(EntityType.WITCH, 1, 1));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addSwampVegetation(generationSettings);
        BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.6f)
            .downfall(0.9f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x617B64)
                .waterFogColor(0x232317)
                .fogColor(0x7E9977)
                .skyColor(0x6B8E82)
                .grassColorOverride(0x6A7039)
                .foliageColorOverride(0x4C763C)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    private static Biome toussaintVineyards(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        
        // Peaceful, safe region
        spawnSettings.addSpawn(MobCategory.CREATURE, 12, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 3, 5));
        spawnSettings.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.COW, 2, 4));
        spawnSettings.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 3, 6));
        spawnSettings.addSpawn(MobCategory.MONSTER, 1, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 1, 1));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addPlainGrass(generationSettings);
        BiomeDefaultFeatures.addDefaultFlowers(generationSettings);
        BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.8f)
            .downfall(0.6f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x3F76E4)
                .waterFogColor(0x050533)
                .fogColor(0xC0D8FF)
                .skyColor(0x7EC0FF)
                .grassColorOverride(0x7FDB4C)
                .foliageColorOverride(0x77AB2F)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    private static Biome mahakamanMountains(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        
        // Mountain creatures
        spawnSettings.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.GOAT, 2, 4));
        spawnSettings.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 2, 3));
        spawnSettings.addSpawn(MobCategory.MONSTER, 5, new MobSpawnSettings.SpawnerData(EntityType.STRAY, 1, 2));
        spawnSettings.addSpawn(MobCategory.MONSTER, 3, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 1, 2));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addDefaultSoftDisks(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.2f)
            .downfall(0.5f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x3F76E4)
                .waterFogColor(0x050533)
                .fogColor(0xC0D8FF)
                .skyColor(0x8CB8FF)
                .grassColorOverride(0x80B497)
                .foliageColorOverride(0x60A17B)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    // ========== NEW KAEDWEN BIOMES ==========
    
    private static Biome createMineValleys(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        spawnSettings.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityType.BAT, 2, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, 8, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 1, 3));
        spawnSettings.addSpawn(MobCategory.MONSTER, 6, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 1, 2));
        spawnSettings.addSpawn(MobCategory.MONSTER, 5, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 1, 2));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addExtraGold(generationSettings);
        BiomeDefaultFeatures.addExtraEmeralds(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.15f)
            .downfall(0.4f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x3F76E4)
                .waterFogColor(0x050533)
                .fogColor(0xC0D8FF)
                .skyColor(0x7BA4FF)
                .grassColorOverride(0x6B8E4C)
                .foliageColorOverride(0x5A7D3F)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    private static Biome createGlacialLakes(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        spawnSettings.addSpawn(MobCategory.WATER_CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 2, 5));
        spawnSettings.addSpawn(MobCategory.MONSTER, 4, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 1, 2));
        spawnSettings.addSpawn(MobCategory.MONSTER, 3, new MobSpawnSettings.SpawnerData(EntityType.STRAY, 1, 2));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addIcebergs(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(-0.1f)
            .downfall(0.6f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x3D57D6)
                .waterFogColor(0x050533)
                .fogColor(0xC0D8FF)
                .skyColor(0x8CBAFF)
                .grassColorOverride(0x80B497)
                .foliageColorOverride(0x60A17B)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    // ========== NEW REDANIA BIOMES ==========
    
    private static Biome createOxenfurtLakes(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        spawnSettings.addSpawn(MobCategory.WATER_CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.COD, 3, 6));
        spawnSettings.addSpawn(MobCategory.WATER_AMBIENT, 8, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 2, 4));
        spawnSettings.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(EntityType.FROG, 2, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, 3, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 1, 2));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.4f)
            .downfall(0.75f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x3F76E4)
                .waterFogColor(0x050533)
                .fogColor(0xC0D8FF)
                .skyColor(0x78A7FF)
                .grassColorOverride(0x79C05A)
                .foliageColorOverride(0x59AE30)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    // ========== NEW NILFGAARD BIOMES ==========
    
    private static Biome createSouthernPlains(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        spawnSettings.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 2, 6));
        spawnSettings.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 3, 5));
        spawnSettings.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(EntityType.COW, 2, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, 2, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 1, 2));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addPlainGrass(generationSettings);
        BiomeDefaultFeatures.addDefaultFlowers(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.85f)
            .downfall(0.4f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x3F76E4)
                .waterFogColor(0x050533)
                .fogColor(0xDDE4CC)
                .skyColor(0x7EC8E3)
                .grassColorOverride(0xBDB25F)
                .foliageColorOverride(0xAEA42A)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    private static Biome createOliveGroves(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        spawnSettings.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 2, 4));
        spawnSettings.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 3, 5));
        spawnSettings.addSpawn(MobCategory.MONSTER, 1, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 1, 1));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addDefaultFlowers(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.9f)
            .downfall(0.35f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x3F76E4)
                .waterFogColor(0x050533)
                .fogColor(0xE8E4D4)
                .skyColor(0x7EC8E3)
                .grassColorOverride(0xA9BA4F)
                .foliageColorOverride(0x8EA52D)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    private static Biome createImperialGardens(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        spawnSettings.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 4));
        spawnSettings.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 2, 3));
        // Very safe area - minimal monsters
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addDefaultFlowers(generationSettings);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_FOREST_FLOWERS);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.8f)
            .downfall(0.5f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x4E94FF)
                .waterFogColor(0x050533)
                .fogColor(0xC8E4CC)
                .skyColor(0x7EC8E3)
                .grassColorOverride(0x7FDB4C)
                .foliageColorOverride(0x59C93C)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    private static Biome createAridScrubland(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        spawnSettings.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 3));
        spawnSettings.addSpawn(MobCategory.MONSTER, 5, new MobSpawnSettings.SpawnerData(EntityType.HUSK, 1, 2));
        spawnSettings.addSpawn(MobCategory.MONSTER, 4, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 1, 2));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addDesertVegetation(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(false)
            .temperature(1.0f)
            .downfall(0.1f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x3F76E4)
                .waterFogColor(0x050533)
                .fogColor(0xE8DCC8)
                .skyColor(0x7EC8E3)
                .grassColorOverride(0xBFB755)
                .foliageColorOverride(0xAEA42A)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    // ========== NEW SKELLIGE BIOMES ==========
    
    private static Biome createRockyCoastlines(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        spawnSettings.addSpawn(MobCategory.WATER_CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.COD, 3, 6));
        spawnSettings.addSpawn(MobCategory.WATER_CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 1, 2));
        spawnSettings.addSpawn(MobCategory.MONSTER, 6, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 2, 4));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.25f)
            .downfall(0.7f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x3D57D6)
                .waterFogColor(0x050533)
                .fogColor(0xB8C8D8)
                .skyColor(0x8AB8FF)
                .grassColorOverride(0x6B8E4C)
                .foliageColorOverride(0x5A7D3F)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    private static Biome createTundraPlains(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        spawnSettings.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityType.POLAR_BEAR, 1, 2));
        spawnSettings.addSpawn(MobCategory.CREATURE, 6, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 2, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, 5, new MobSpawnSettings.SpawnerData(EntityType.STRAY, 1, 2));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addSnowyTrees(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.0f)
            .downfall(0.4f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x3D57D6)
                .waterFogColor(0x050533)
                .fogColor(0xC0D8FF)
                .skyColor(0x8CB8FF)
                .grassColorOverride(0x80B497)
                .foliageColorOverride(0x60A17B)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    private static Biome createGlacierFields(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        spawnSettings.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityType.POLAR_BEAR, 1, 2));
        spawnSettings.addSpawn(MobCategory.MONSTER, 6, new MobSpawnSettings.SpawnerData(EntityType.STRAY, 1, 3));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addIcebergs(generationSettings);
        BiomeDefaultFeatures.addBlueIce(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(-0.3f)
            .downfall(0.5f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x3D57D6)
                .waterFogColor(0x050533)
                .fogColor(0xD8E8FF)
                .skyColor(0x8CB8FF)
                .grassColorOverride(0x80B497)
                .foliageColorOverride(0x60A17B)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    private static Biome createStormSeas(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        spawnSettings.addSpawn(MobCategory.WATER_CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityType.COD, 4, 8));
        spawnSettings.addSpawn(MobCategory.WATER_CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 2, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, 8, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 2, 4));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.3f)
            .downfall(0.9f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x2C3E50)
                .waterFogColor(0x050533)
                .fogColor(0x6A7A8A)
                .skyColor(0x546E7A)
                .grassColorOverride(0x80B497)
                .foliageColorOverride(0x60A17B)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    // ========== SPECIAL CROSS-REGIONAL BIOMES ==========
    
    private static Biome createBattlefield(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        // Very high hostile spawn rates
        spawnSettings.addSpawn(MobCategory.MONSTER, 20, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 3, 6));
        spawnSettings.addSpawn(MobCategory.MONSTER, 15, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 2, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, 10, new MobSpawnSettings.SpawnerData(EntityType.PHANTOM, 1, 3));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        // Sparse vegetation
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.5f)
            .downfall(0.3f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x4C3A2A)
                .waterFogColor(0x1A1410)
                .fogColor(0x8A8070)
                .skyColor(0x6A6A6A)
                .grassColorOverride(0x4A4A26)
                .foliageColorOverride(0x3A3A1C)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    private static Biome createCursedSwamp(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        spawnSettings.addSpawn(MobCategory.MONSTER, 15, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 2, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, 12, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 2, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, 10, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 1, 3));
        spawnSettings.addSpawn(MobCategory.MONSTER, 8, new MobSpawnSettings.SpawnerData(EntityType.WITCH, 1, 2));
        spawnSettings.addSpawn(MobCategory.MONSTER, 5, new MobSpawnSettings.SpawnerData(EntityType.PHANTOM, 1, 2));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addSwampVegetation(generationSettings);
        BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.6f)
            .downfall(0.95f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x3A5F3A)
                .waterFogColor(0x0A1A0A)
                .fogColor(0x4A5A4A)
                .skyColor(0x4A5A5A)
                .grassColorOverride(0x4C6039)
                .foliageColorOverride(0x3A4C2D)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
    
    private static Biome createAncientRuins(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        spawnSettings.addSpawn(MobCategory.MONSTER, 10, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 2, 4));
        spawnSettings.addSpawn(MobCategory.MONSTER, 8, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 1, 3));
        spawnSettings.addSpawn(MobCategory.MONSTER, 5, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 1, 2));
        
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultOres(generationSettings);
        BiomeDefaultFeatures.addForestFlowers(generationSettings);
        BiomeDefaultFeatures.addDefaultMushrooms(generationSettings);
        
        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.6f)
            .downfall(0.7f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x3F76E4)
                .waterFogColor(0x050533)
                .fogColor(0xB8C8B8)
                .skyColor(0x78A7FF)
                .grassColorOverride(0x6A9A4A)
                .foliageColorOverride(0x5A8A3A)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .build())
            .mobSpawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build();
    }
}
