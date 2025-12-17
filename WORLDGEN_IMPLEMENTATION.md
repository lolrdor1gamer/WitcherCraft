# World Generation System

## Overview
The WitcherCraft mod implements a custom world generation system that recreates The Continent from The Witcher universe. The system uses a multi-layered approach to generate distinct regions with appropriate biomes.

**Current Status**: Phase 2 - Custom dimension with improved terrain generation implemented. Structures planned for Phase 3.

## Architecture

### Layer 1: Region System
- **RegionProvider**: Uses sine-wave noise to divide the world into 7 continental regions
  - Temeria (temperate farmlands)
  - Kaedwen (mountainous north)
  - Redania (rich plains)
  - Nilfgaard (southern scrubland)
  - Skellige (coastal islands)
  - Aedirn (eastern kingdom)
  - No Man's Land (war-torn wasteland)

### Layer 2: Biome Selection
- **ContinentBiomeSource**: Custom BiomeSource that selects biomes based on:
  - Region (which part of The Continent)
  - Temperature (from noise)
  - Humidity (from noise)
  - Coastal proximity
  - Elevation (Y-level)

### Layer 3: Custom Biomes
Nine distinct biomes representing different areas of The Continent:

1. **Temerian Farmlands** - Lush green farmland with peaceful animals
2. **Mahakaman Foothills** - Forested hills with wolves
3. **Pontar Riverlands** - River biome with fish and drowned
4. **Mountain Peaks** - Cold mountain tops with goats and strays
5. **Frozen Taiga** - Snowy northern forests
6. **No Man's Land** - Dead wasteland with high monster spawn rates
7. **Redanian Plains** - Temperate grasslands
8. **Nilfgaardian Scrubland** - Arid southern lands
9. **Skellige Coast** - Cold coastal waters

### Layer 4: Chunk Generation
- **ContinentChunkGenerator**: Extends ChunkGenerator to create terrain
- Multi-scale noise (continental, regional, local)
- Biome-specific surface rules (grass, sand, gravel, stone)

### Layer 5: Dimension
- **The Continent**: Custom dimension accessible via `/continent` command
- Overworld-like properties (day/night cycle, weather, etc.)
- Y-range: -64 to 384 (same as vanilla overworld)

## File Structure

```
worldgen/
├── WitcherWorldGen.java          # Central registration
├── region/
│   ├── ContinentRegion.java      # Region enum
│   └── RegionProvider.java       # Region detection logic
├── biome/
│   ├── WitcherBiomes.java        # Biome definitions
│   ├── ContinentBiomeSource.java # Biome selection logic
│   └── BiomeHolderCache.java     # Runtime biome holder access
└── dimension/
    ├── WitcherDimensions.java    # Dimension type
    └── ContinentChunkGenerator.java # Terrain generation

command/
└── ContinentCommand.java         # Teleport command

datagen/
├── WitchercraftDataGenerator.java    # Main datagen
└── WitcherWorldGenProvider.java      # Worldgen datagen

resources/data/witchercraft/
└── dimension/
    └── the_continent.json        # Dimension configuration
```

## Usage

### For Players
- Use `/continent` command (requires OP level 2) to teleport to The Continent
- Explore different regions with distinct biomes
- Each region has unique mobs, vegetation, and atmosphere

### For Developers
1. **Adding New Biomes**:
   - Add ResourceKey to `WitcherBiomes.java`
   - Create biome builder method
   - Register in `bootstrap()` method
   - Add to `selectBiomeForRegion()` in `ContinentBiomeSource`
   - Add to `BiomeHolderCache.initialize()`

2. **Modifying Regions**:
   - Edit `ContinentRegion` enum for new regions
   - Update `RegionProvider.getRegion()` logic
   - Add cases in `ContinentBiomeSource.selectBiomeForRegion()`

3. **Customizing Terrain**:
   - Modify `ContinentChunkGenerator.doFill()` for custom terrain shapes
   - Adjust noise parameters in `getBaseHeight()`
   - Add custom surface rules in `buildSurface()`

## Technical Details

### Biome Selection Logic

```java
TEMERIA:
  humidity > 0.7  → Pontar Riverlands
  humidity > 0.4  → Temerian Farmlands
  else            → Mahakaman Foothills

KAEDWEN:
  y > 80 OR temp < 0.3  → Mountain Peaks
  temp < 0.5            → Frozen Taiga
  else                  → Mahakaman Foothills

SKELLIGE:
  coastal  → Skellige Coast
  y > 70   → Mountain Peaks
  else     → Frozen Taiga

NO_MANS_LAND:
  always   → No Man's Land (wasteland)
```

### Noise Functions
- Uses sine-wave approximation for simplicity (avoiding complex PerlinNoise API)
- Multiple octaves for natural variation
- Seed-based for consistent world generation

### Data Generation
Run `./gradlew runDatagen` to generate:
- Biome JSON files
- Dimension type JSON files
- Feature placement data

## Completed Features

1. ✅ **Multi-scale Terrain Generation**:
   - Continental-scale features (large hills/valleys)
   - Regional-scale variations (medium terrain)
   - Local-scale details (small bumps)
   - Smooth interpolated noise for natural terrain

2. ✅ **Biome-specific Surface Blocks**:
   - Grass blocks on normal terrain
   - Sand on beaches (near sea level)
   - Gravel in deep water
   - Stone on mountains (high elevation)
   - Proper dirt subsurface layers

3. ✅ **Region System**:
   - Smooth interpolated noise for large biome regions
   - 7 continental regions with proper boundaries
   - Environmental factors (temperature, humidity, coastal)

## Future Enhancements

1. **Structures** (Requires Minecraft 1.21.1 structure API research):
   - Village structures for all regions
   - Witcher keeps with training grounds
   - Monster nests with spawners
   - Ancient ruins with loot
   - Structure placement logic (biome-specific)

2. **Advanced Terrain**:
   - Rivers following noise patterns
   - Mountain ranges with proper peaks/valleys
   - Coastline generation for Skellige
   - Swamps in certain regions

3. **More Biomes**:
   - Brokilon Forest (dryad territory)
   - Vizima Swamps
   - Velen Marshes
   - Toussaint Vineyards

4. **Dynamic Features**:
   - War-torn areas with destroyed structures in No Man's Land
   - Trade routes between regions
   - Regional weather patterns

## API Compatibility

Built for **Minecraft 1.21.1** with **Fabric Loader 0.18.2**

Key API differences from older versions:
- `BiomeSource` uses `MapCodec` instead of `Codec`
- `MobSpawnSettings` requires separate weight parameter
- `BiomeGenerationSettings.Builder` requires `HolderGetter` parameters
- Bootstrap uses `BootstrapContext` instead of `Registerable`

## Credits

World generation system designed and implemented for The Witcher-themed Minecraft mod.
Based on The Witcher universe by Andrzej Sapkowski.
