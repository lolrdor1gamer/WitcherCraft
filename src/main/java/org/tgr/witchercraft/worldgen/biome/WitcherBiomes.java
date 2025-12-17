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

    // Redania Biomes
    public static final ResourceKey<Biome> REDANIAN_PLAINS = register("redanian_plains");

    // Nilfgaard Biomes
    public static final ResourceKey<Biome> NILFGAARDIAN_SCRUBLAND = register("nilfgaardian_scrubland");

    // Skellige Biomes
    public static final ResourceKey<Biome> SKELLIGE_COAST = register("skellige_coast");

    // No Man's Land
    public static final ResourceKey<Biome> NO_MANS_LAND = register("no_mans_land");

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
    
    // ========== OTHER REGIONS (Simplified for now) ==========
    
    private static Biome createRedanianPlains(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        // TODO: Customize - similar to plains but colder
        return createTemerianFarmlands(placedFeatures, carvers);
    }
    
    private static Biome createNilfgaardianScrubland(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        // TODO: Customize - hot, dry, sparse vegetation
        return createTemerianFarmlands(placedFeatures, carvers);
    }
    
    private static Biome createSkelligeCoast(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        // TODO: Customize - rocky coasts, cold
        return createFrozenTaiga(placedFeatures, carvers);
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
}
