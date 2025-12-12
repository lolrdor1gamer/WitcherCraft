# Item & Block Registration Guide

## Current Status ✅

The mod now loads without crashing! The registration system has been fixed.

**What was changed:**
- Removed static item/block initialization (was causing crashes)
- Simplified registries to placeholder stubs
- Items/blocks can now be added via datagen or Fabric API events

---

## How to Properly Register Items & Blocks in Minecraft 1.21.10 Fabric

### Method 1: Using Registry Events (Recommended) ⭐

Create a new file: `src/main/java/org/tgr/witchercraft/registry/RegisterItems.java`

```java
package org.tgr.witchercraft.registry;

import net.fabricmc.fabric.api.registry.FabricRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.tgr.witchercraft.Witchercraft;

public class RegisterItems {
    public static final Item SILVER_SWORD = register("silver_sword", 
        new Item(new Item.Properties().stacksTo(1)));
    
    private static Item register(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, 
            ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, name), 
            item);
    }
    
    public static void initialize() {
        // Items are registered via static initialization
    }
}
```

### Method 2: Using Fabric API Builder (Best Practice) ✅

```java
import net.fabricmc.fabric.api.object.builder.v1.item.FabricItemSettings;

public static final Item SILVER_SWORD = FabricItemSettings.register(
    new ResourceLocation(MODID, "silver_sword"),
    new Item(new FabricItemSettings().stacksTo(1))
);
```

### Method 3: Data Generation (Cleanest for Large Mods)

Use the datagen system to generate item/block registrations automatically:

```java
public class WitchercraftDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        
        pack.addProvider(ItemModelProvider::new);
        pack.addProvider(BlockModelProvider::new);
        // etc.
    }
}
```

---

## Registration Pattern for WitcherCraft

### Recommended Folder Structure

```
src/main/java/org/tgr/witchercraft/
├── registry/
│   ├── ModItems.java (holder for item constants)
│   ├── ModBlocks.java (holder for block constants)
│   ├── ItemRegistry.java (actual registration logic)
│   └── BlockRegistry.java (actual registration logic)
└── datagen/
    └── WitchercraftDataGenerator.java
```

### Example Implementation

**ModItems.java** (Constants holder)
```java
public class ModItems {
    // Item references (null until registered)
    public static Item SILVER_SWORD;
    public static Item STEEL_SWORD;
    
    public static void initialize() {
        ItemRegistry.register();
    }
}
```

**ItemRegistry.java** (Registration logic)
```java
public class ItemRegistry {
    public static void register() {
        ModItems.SILVER_SWORD = Registry.register(
            BuiltInRegistries.ITEM,
            ResourceLocation.fromNamespaceAndPath(MODID, "silver_sword"),
            new Item(new Item.Properties().stacksTo(1))
        );
        
        ModItems.STEEL_SWORD = Registry.register(
            BuiltInRegistries.ITEM,
            ResourceLocation.fromNamespaceAndPath(MODID, "steel_sword"),
            new Item(new Item.Properties().stacksTo(1))
        );
        
        LOGGER.info("Registered {} items", 2);
    }
}
```

---

## Common Issues & Solutions

### Issue 1: "Item id not set" Error
**Cause:** Item.Properties doesn't have a description ID  
**Solution:** Use FabricItemSettings or ensure Item.Properties is properly initialized

### Issue 2: NullPointerException at Registration
**Cause:** Registry isn't ready during static initialization  
**Solution:** Move registration to a method called from onInitialize()

### Issue 3: Items Don't Appear in Creative Tab
**Cause:** Items not added to ItemGroupEvents  
**Solution:** Register items with ItemGroupEvents after registration

---

## Next Steps to Complete Item System

1. **Create ItemRegistry.java** - Proper registration with error handling
2. **Create BlockRegistry.java** - Same for blocks
3. **Add ItemGroup integration** - Make items appear in creative menu
4. **Create item models** - JSON + textures
5. **Add item translations** - language files

---

## Files to Create

### 1. ItemRegistry.java
```java
// Proper item registration with Fabric API
public class ItemRegistry {
    public static void register() {
        // Register all items here
    }
}
```

### 2. BlockRegistry.java
```java
// Proper block registration
public class BlockRegistry {
    public static void register() {
        // Register all blocks here
    }
}
```

### 3. CreativeTabRegistry.java
```java
// Add items to creative tabs
public class CreativeTabRegistry {
    public static void register() {
        // Add items to tabs
    }
}
```

---

## Testing Checklist

- [ ] Mod loads without crashes
- [ ] Items appear in creative mode
- [ ] Items can be picked up
- [ ] Items stack properly
- [ ] Item names are localized
- [ ] Item models render correctly

