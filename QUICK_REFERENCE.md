# WitcherCraft Quick Reference

## 🚀 Current Status: ✅ MOD LOADS - Ready for feature development!

---

## 📋 Files Overview

### Core Files
- **`Witchercraft.java`** - Main mod class, initializes everything
- **`WitchercraftClient.java`** - Client-side setup
- **`WitcherConfig.java`** - Configuration (YACL integration)

### Sign System ✨
- **`signs/WitcherSign.java`** - Base class for all signs
- **`signs/IgniSign.java`** - Fire blast sign (IMPLEMENTED)
- **`sign/SignKeyBindings.java`** - Key binding framework
- **`sign/SignCastingHandler.java`** - Detects key presses
- **`network/CastSignPacket.java`** - Client↔Server communication

### Item & Block System
- **`registry/ModItems.java`** - Item placeholder registry
- **`registry/ModBlocks.java`** - Block placeholder registry

### Configuration
- **`build.gradle`** - Gradle build configuration
- **`gradle.properties`** - Version properties
- **`fabric.mod.json`** - Mod metadata

---

## 🎯 Quick Tasks

### To Fix Item Registration (PRIORITY)
1. Create `src/main/java/org/tgr/witchercraft/registry/ItemRegistry.java`
2. Add proper item registration using Registry.register()
3. Create item textures in `src/main/resources/assets/witchercraft/textures/item/`
4. Create item models in `src/main/resources/assets/witchercraft/models/item/`
5. Add language strings in `src/main/resources/assets/witchercraft/lang/en_us.json`

### To Implement More Signs
1. Create new sign class (e.g., `AardSign.java`)
2. Extend `WitcherSign` base class
3. Implement `cast()` method with effect logic
4. Add to `SignCastingHandler.java` detection
5. Add to `CastSignPacket.java` server handler

### To Add Stamina System
1. Create `src/main/java/org/tgr/witchercraft/util/PlayerStaminaAttachment.java`
2. Register attachment in `Witchercraft.java`
3. Create HUD renderer for stamina bar
4. Add stamina cost checks to sign casting
5. Implement stamina regeneration

---

## 🔑 Key Bindings (Planned)

| Key | Sign | Effect |
|-----|------|--------|
| Q | Igni | Fire blast |
| E | Aard | Knockback |
| R | Quen | Shield |
| F | Yrden | Slowing trap |
| G | Axii | Confusion |

---

## 📦 Dependencies

```
Fabric API v0.138.3
├── YACL v3.8.1 (Configuration)
├── Malilib v0.26.6 (UI/Utils)
├── MagicLib v0.8.784 (Magic System)
├── TCDCommons v4.0.1 (Common Utilities)
└── Architectury v18.0.8 (Multi-platform)
```

All dependencies are already configured in `build.gradle`.

---

## 🛠️ Build & Run Commands

```bash
# Build the mod
./gradlew build

# Run the game with mod
./gradlew runClient

# Clean build
./gradlew clean build

# Generate Gradle wrapper
./gradlew wrapper
```

---

## 📁 File Structure Reference

```
src/main/
├── java/org/tgr/witchercraft/
│   ├── Witchercraft.java .................. Main entry point
│   ├── WitcherConfig.java ................. Configuration
│   ├── registry/
│   │   ├── ModItems.java .................. Item registry
│   │   └── ModBlocks.java ................. Block registry
│   ├── signs/
│   │   ├── WitcherSign.java ............... Base sign class
│   │   └── IgniSign.java .................. Fire sign
│   ├── sign/
│   │   ├── SignKeyBindings.java ........... Keybinding framework
│   │   └── SignCastingHandler.java ........ Input detection
│   ├── network/
│   │   └── CastSignPacket.java ............ Network comm
│   └── util/ .............................. Helper classes
├── resources/
│   ├── fabric.mod.json .................... Mod metadata
│   ├── witchercraft.mixins.json ........... Mixin config
│   └── assets/witchercraft/
│       ├── textures/ ...................... PNG images
│       ├── models/ ........................ JSON models
│       ├── lang/ .......................... Translations
│       └── sounds/ ........................ Audio files
└── generated/ ............................ Generated files

src/client/
├── java/org/tgr/witchercraft/
│   ├── client/
│   │   └── WitchercraftClient.java ........ Client init
│   └── sign/
│       ├── SignKeyBindings.java .......... Client keybindings
│       └── SignCastingHandler.java ....... Client handler
└── resources/
    ├── witchercraft.client.mixins.json ... Client mixins
    └── assets/witchercraft/ .............. Client assets
```

---

## 🎨 Asset Paths

### Textures
```
assets/witchercraft/textures/item/
├── silver_sword.png
├── steel_sword.png
├── swallow_potion.png
├── cat_potion.png
├── celandine.png
└── drowner_brain.png

assets/witchercraft/textures/block/
├── alchemy_table.png
└── grindstone_witcher.png
```

### Models
```
assets/witchercraft/models/item/
├── silver_sword.json
├── steel_sword.json
└── ...

assets/witchercraft/models/block/
├── alchemy_table.json
└── grindstone_witcher.json
```

### Translations
```
assets/witchercraft/lang/
├── en_us.json
└── other_languages.json
```

---

## 🔍 Debugging Tips

### Check Logs
```bash
# Game logs are in
run/logs/latest.log
```

### Common Errors
| Error | Cause | Fix |
|-------|-------|-----|
| Item id not set | Registration timing | Use initialize() method |
| NullPointerException | Missing dependency | Check build.gradle |
| KeyMapping not found | API mismatch | Check Fabric docs |
| Mixin failed | Mixin config error | Check mixins JSON |

### Debug Print
```java
Witchercraft.LOGGER.info("Debug message here");
```

---

## 💾 Important Files to NOT Modify

- `build.gradle` - Only update versions/dependencies carefully
- `settings.gradle` - Fabric Loom config
- `gradle/wrapper/` - Gradle wrapper files
- `fabric.mod.json` - Only change mod metadata

---

## ✅ Checklist for New Features

- [ ] Create new Java file
- [ ] Add proper package name
- [ ] Add class documentation
- [ ] Initialize/register in main classes
- [ ] Test for compilation errors
- [ ] Add to appropriate registry
- [ ] Create resources (textures/models)
- [ ] Add translation strings
- [ ] Test in-game
- [ ] Log success message

---

## 🎓 Learning Resources

- **Fabric Wiki:** https://fabricmc.net/wiki/
- **Minecraft Wiki:** https://minecraft.wiki/
- **Java Docs:** https://docs.oracle.com/
- **Gradle Docs:** https://docs.gradle.org/

---

## 📞 Quick Support

**Q: Mod won't load?**  
A: Check `run/logs/latest.log` for the error

**Q: Items don't appear?**  
A: Need to create ItemRegistry.java with proper registration

**Q: Signs don't work?**  
A: Need to fix KeyBinding registration in SignKeyBindings.java

**Q: Textures missing?**  
A: Create PNG files in `assets/witchercraft/textures/`

---

**Last Updated:** December 10, 2025  
**Status:** ✅ Ready for Development
