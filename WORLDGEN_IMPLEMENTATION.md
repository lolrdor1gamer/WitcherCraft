# World Generation System

## Overview
The WitcherCraft mod implements a custom world generation system that recreates The Continent from The Witcher universe. The system uses a multi-layered approach to generate distinct regions with appropriate biomes, natural features, and rivers.

**Current Status**: Phase 3 Complete - Full terrain generation with rivers, enhanced biomes, terrain features, and diverse vegetation.

## Architecture

### Layer 1: Region System
- **RegionProvider**: Uses interpolated noise to divide the world into 7 continental regions
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
Thirteen distinct biomes representing different areas of The Continent:

**Original Biomes:**
1. **Temerian Farmlands** - Lush green farmland with peaceful animals
2. **Mahakaman Foothills** - Forested hills with wolves
3. **Pontar Riverlands** - River biome with fish and drowned
4. **Mountain Peaks** - Cold mountain tops with goats and strays
5. **Frozen Taiga** - Snowy northern forests
6. **No Man's Land** - Dead wasteland with high monster spawn rates
7. **Redanian Plains** - Temperate grasslands
8. **Nilfgaardian Scrubland** - Arid southern lands
9. **Skellige Coast** - Cold coastal waters

**New Biomes (Phase 3):**
10. **Brokilon Forest** - Dense magical forest with dryads (Temeria borders)
11. **Velen Marshes** - Dangerous swamps with high monster spawns (No Man's Land)
12. **Toussaint Vineyards** - Mediterranean paradise with peaceful wildlife (Nilfgaard)
13. **Mahakaman Mountains** - High altitude peaks with mountain creatures (Kaedwen/Skellige)

### Layer 4: Chunk Generation
- **ContinentChunkGenerator**: Extends ChunkGenerator to create terrain
- Multi-scale noise (continental, regional, local)
- Biome-specific surface rules (grass, sand, gravel, stone)
- Integrated cave carving system
- River generation with natural carving
- Enhanced vegetation with climate-based variety
- Natural terrain features (boulders, fallen logs, mushrooms)

### Layer 5: Feature Generation
- **VegetationPlacer**: Climate-based vegetation
  - Tree types: Oak, Spruce, Birch, Dark Oak
  - 16+ flower varieties
  - Grass, ferns, and tall plants
  - Density based on humidity and temperature
  
- **OrePlacer**: All vanilla ores with proper Y-ranges
  - Coal, Iron, Gold, Diamond, Redstone, Lapis
  - Deepslate variants below Y=0
  
- **RiverGenerator**: Natural river systems
  - 2D noise-based river paths
  - Rivers 4-12 blocks wide
  - Natural riverbanks with gravel/sand beds
  - Connects water features across the continent
  
- **TerrainFeatures**: Natural decorations
  - Stone boulders (1-3 blocks) in mountains
  - Fallen logs (3-6 blocks) in forests
  - Mushroom clusters in humid areas
  - Pumpkin patches in farmlands

### Layer 6: Dimension
- **The Continent**: Custom dimension accessible via `/continent` command
- Overworld-like properties (day/night cycle, weather, etc.)
- Y-range: -64 to 384 (same as vanilla overworld)

## Completed Features

✅ **Phase 1: Basic Structure**
- Custom dimension with proper JSON format
- Region-based biome selection
- 9 initial biomes with unique characteristics

✅ **Phase 2: Terrain Generation**
- Interpolated noise for smooth biome regions
- Multi-scale terrain (continental, regional, local)
- Height-based surface blocks (grass, sand, gravel, stone)
- Vanilla cave system integration
- Basic vegetation (oak/spruce trees, grass, flowers)
- Complete ore generation

✅ **Phase 3: Rivers, Biomes, and Features** (Current)
- 4 new thematic biomes (Brokilon, Velen, Toussaint, Mahakaman)
- River generation system with natural paths
- Enhanced vegetation (4 tree types, 16+ flowers, climate-based density)
- Terrain features (boulders, logs, mushrooms, pumpkins)
- Biome-specific mob spawns
- Climate-aware feature placement

## File Structure

```
worldgen/
├── WitcherWorldGen.java          # Central registration
├── region/
│   ├── ContinentRegion.java      # Region enum
│   └── RegionProvider.java       # Region detection logic
├── biome/
│   ├── WitcherBiomes.java        # Biome definitions (13 biomes)
│   ├── ContinentBiomeSource.java # Biome selection logic
│   └── BiomeHolderCache.java     # Runtime biome holder access
├── feature/
│   ├── VegetationPlacer.java     # Enhanced vegetation (trees, flowers, grass)
│   ├── OrePlacer.java            # Ore generation
│   ├── RiverGenerator.java       # River system (NEW)
│   └── TerrainFeatures.java      # Natural decorations (NEW)
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
- Rivers connect regions and provide natural water sources
- Look for special features like Brokilon Forest or Toussaint Vineyards

### For Developers
1. **Adding New Biomes**:
   - Add ResourceKey to `WitcherBiomes.java`
   - Create biome builder method
   - Register in `bootstrap()` method
   - Add to `selectBiomeForRegion()` in `ContinentBiomeSource`
   - Add to `BiomeHolderCache.initialize()`
   - Add to `collectPossibleBiomes()` stream

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
  y > 85                → Mahakaman Mountains (NEW)
  y > 80 OR temp < 0.3  → Mountain Peaks
  temp < 0.5            → Frozen Taiga
  else                  → Mahakaman Foothills

NILFGAARD:
  temp > 0.75 AND humidity 0.5-0.7  → Toussaint Vineyards (NEW)
  else                              → Nilfgaardian Scrubland

SKELLIGE:
  coastal  → Skellige Coast
  y > 75   → Mahakaman Mountains (NEW)
  y > 70   → Mountain Peaks
  else     → Frozen Taiga

NO_MANS_LAND:
  humidity > 0.8 AND y < 68  → Velen Marshes (NEW)
  else                       → No Man's Land (wasteland)

TEMERIA:
  humidity > 0.75 AND y < 70  → Brokilon Forest (NEW)
  humidity > 0.7              → Pontar Riverlands
  humidity > 0.4              → Temerian Farmlands
  else                        → Mahakaman Foothills
```

### Noise Functions
- Uses interpolated noise with grid-based hashing
- Multiple octaves for natural variation
- Seed-based for consistent world generation
- Smoothstep interpolation for smooth transitions

### Data Generation
Run `./gradlew runDatagen` to generate:
- Biome JSON files (13 biomes)
- Dimension type JSON files
- Feature placement data

## Technical Details

### River Generation
- **Algorithm**: 2D noise-based river paths
- **Coverage**: ~10% of terrain (threshold: |noise| < 0.05)
- **Width**: 4-12 blocks (wider near centerline)
- **Depth**: 3-5 blocks below surface
- **Riverbed**: 70% gravel, 30% sand
- **Features**: Natural winding paths, smooth banks

### Enhanced Vegetation
- **Density**: 12-24 attempts per chunk (climate-based)
- **Tree Types**:
  - Spruce: Cold biomes (temp < 0.3)
  - Dark Oak: Very humid + warm (downfall > 0.75, temp > 0.6)
  - Birch: Warm + dry (temp > 0.6, downfall < 0.6)
  - Oak: Default temperate
- **Tree Chance**: 2-12% based on humidity
- **Flower Variety**: 16 types (climate-dependent selection)
- **Plant Types**: Grass, tall grass, ferns, large ferns, flowers, dead bushes

### Terrain Features
- **Boulders**: 1-3 block formations in mountains (15% chance)
- **Fallen Logs**: 3-6 block logs in forests (10% chance)
- **Mushroom Clusters**: 2-4 mushrooms in humid areas (8% chance)
- **Pumpkin Patches**: 1-3 pumpkins in farmlands (5% chance)
- **Placement**: 3-5 attempts per chunk

## Previous Implementation Details

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

4. ✅ **Cave Generation**:
   - Vanilla cave carving system integrated
   - Natural cave networks throughout the world
   - Proper cave generation at all Y levels

5. ✅ **Basic Vegetation System**:
   - Biome-specific trees (oak in temperate, spruce in cold)
   - Grass, flowers, and ferns
   - Dead bushes in dry biomes
   - Proper density and distribution

6. ✅ **Ore Distribution**:
   - All vanilla ores (coal, iron, gold, diamond, redstone, lapis)
   - Proper Y-level ranges
   - Deepslate variants below Y=0
   - Realistic vein sizes and frequencies

7. ✅ **River System** (Phase 3):
   - Natural winding rivers across the continent
   - Rivers 4-12 blocks wide with variable depth
   - Gravel/sand riverbeds
   - Smooth natural riverbanks

8. ✅ **Enhanced Vegetation** (Phase 3):
   - 4 tree types: Oak, Spruce, Birch, Dark Oak
   - 16+ flower varieties (climate-based)
   - Large ferns, tall grass, and other plants
   - Climate-aware density (12-24 attempts per chunk)

9. ✅ **Terrain Features** (Phase 3):
   - Stone boulders in mountains
   - Fallen logs in forests with mushrooms
   - Mushroom clusters in humid areas
   - Pumpkin patches in farmlands

10. ✅ **New Biomes** (Phase 3):
    - Brokilon Forest (dense magical forest)
    - Velen Marshes (dangerous swampland)
    - Toussaint Vineyards (Mediterranean paradise)
    - Mahakaman Mountains (high altitude peaks)

## Future Enhancements

1. **Structures** (Requires Minecraft 1.21.1 structure API research):
   - Village structures for all regions
   - Witcher keeps with training grounds
   - Monster nests with spawners
   - Ancient ruins with loot
   - Structure placement logic (biome-specific)

3. **Advanced Terrain**:
   - Better mountain ranges with peaks/valleys
   - Coastal cliffs and overhangs
   - Natural arches and pillars
   - Regional terrain variations

4. **More Biomes**:
   - Brokilon Forest (dryad territory)
   - Vizima Swamps
   - Velen Marshes
   - Toussaint Vineyards

5. **Custom Ores and Resources**:
   - Meteorite ore (for Witcher swords)
   - Region-specific ores
   - Herb nodes (alchemy ingredients)
   - Monster nests as terrain features

6. **Dynamic Features**:
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
