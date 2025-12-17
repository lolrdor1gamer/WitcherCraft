package org.tgr.witchercraft.worldgen.region;

/**
 * Represents the major political/geographical regions of The Continent.
 * Each region has unique climate, terrain, and cultural characteristics.
 */
public enum ContinentRegion {
    /**
     * Central hub - temperate plains, forests, rivers
     * Climate: Moderate
     * Culture: Trade-focused, diverse
     */
    TEMERIA("Temeria", 0.6f, 0.5f),
    
    /**
     * Northern kingdom - cold plains, lakes, religious architecture
     * Climate: Cold
     * Culture: Religious, witch hunters
     */
    REDANIA("Redania", 0.3f, 0.4f),
    
    /**
     * Far north - mountains, taiga, mining settlements
     * Climate: Arctic
     * Culture: Mining, hardy folk
     */
    KAEDWEN("Kaedwen", 0.1f, 0.3f),
    
    /**
     * Eastern kingdom - plains, hills, agricultural
     * Climate: Temperate
     * Culture: Agricultural, dragon lore
     */
    AEDIRN("Aedirn", 0.6f, 0.5f),
    
    /**
     * Southern empire - warm plains, scrubland
     * Climate: Warm/Hot
     * Culture: Imperial, military-focused
     */
    NILFGAARD("Nilfgaard", 0.8f, 0.3f),
    
    /**
     * Northwestern islands - coasts, tundra, Norse-inspired
     * Climate: Subarctic
     * Culture: Naval, clan-based
     */
    SKELLIGE("Skellige", 0.2f, 0.6f),
    
    /**
     * War-torn borderlands - scorched, ruined, dangerous
     * Climate: Varies
     * Culture: Lawless, monster-infested
     */
    NO_MANS_LAND("No Man's Land", 0.5f, 0.3f);
    
    private final String displayName;
    private final float temperature; // 0.0 = freezing, 1.0 = hot
    private final float humidity;    // 0.0 = arid, 1.0 = humid
    
    ContinentRegion(String displayName, float temperature, float humidity) {
        this.displayName = displayName;
        this.temperature = temperature;
        this.humidity = humidity;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public float getTemperature() {
        return temperature;
    }
    
    public float getHumidity() {
        return humidity;
    }
    
    /**
     * Returns whether this region is considered "safe" (patrolled, civilized)
     */
    public boolean isSafe() {
        return this != NO_MANS_LAND;
    }
    
    /**
     * Returns whether this region has coastline/ocean access
     */
    public boolean isCoastal() {
        return this == SKELLIGE || this == NILFGAARD;
    }
    
    /**
     * Returns whether this region is mountainous
     */
    public boolean isMountainous() {
        return this == KAEDWEN || this == SKELLIGE;
    }
}
