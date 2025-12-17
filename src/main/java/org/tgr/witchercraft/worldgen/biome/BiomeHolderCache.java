package org.tgr.witchercraft.worldgen.biome;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.Biome;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class to access biome holders at runtime.
 * Biomes must be accessed through holders after registry is built.
 */
public class BiomeHolderCache {
    
    private static final Map<ResourceKey<Biome>, Holder<Biome>> CACHE = new HashMap<>();
    private static boolean initialized = false;
    
    /**
     * Initialize the cache with biome holders from the server's registry.
     * Call this during server start.
     */
    public static void initialize(MinecraftServer server) {
        if (initialized) return;
        
        HolderGetter<Biome> biomeGetter = server.registryAccess().lookupOrThrow(Registries.BIOME);
        
        // Cache all Witcher biomes
        cacheHolder(biomeGetter, WitcherBiomes.TEMERIAN_FARMLANDS);
        cacheHolder(biomeGetter, WitcherBiomes.MAHAKAMAN_FOOTHILLS);
        cacheHolder(biomeGetter, WitcherBiomes.PONTAR_RIVERLANDS);
        cacheHolder(biomeGetter, WitcherBiomes.MOUNTAIN_PEAKS);
        cacheHolder(biomeGetter, WitcherBiomes.FROZEN_TAIGA);
        cacheHolder(biomeGetter, WitcherBiomes.NO_MANS_LAND);
        cacheHolder(biomeGetter, WitcherBiomes.REDANIAN_PLAINS);
        cacheHolder(biomeGetter, WitcherBiomes.NILFGAARDIAN_SCRUBLAND);
        cacheHolder(biomeGetter, WitcherBiomes.SKELLIGE_COAST);
        
        initialized = true;
    }
    
    private static void cacheHolder(HolderGetter<Biome> getter, ResourceKey<Biome> key) {
        getter.get(key).ifPresent(holder -> CACHE.put(key, holder));
    }
    
    /**
     * Get a cached biome holder.
     * Returns null if not initialized or biome not found.
     */
    public static Holder<Biome> get(ResourceKey<Biome> key) {
        return CACHE.get(key);
    }
    
    /**
     * Clear the cache (for dimension reload)
     */
    public static void clear() {
        CACHE.clear();
        initialized = false;
    }
    
    public static boolean isInitialized() {
        return initialized;
    }
}
