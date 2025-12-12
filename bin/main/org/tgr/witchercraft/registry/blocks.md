  Creating Your First Block | Fabric Documentation              (()=>{let e=localStorage.getItem(\`vitepress-theme-appearance\`)||\`auto\`,t=window.matchMedia(\`(prefers-color-scheme: dark)\`).matches;(!e||e===\`auto\`?t:e===\`dark\`)&&document.documentElement.classList.add(\`dark\`)})(); document.documentElement.classList.toggle(\`mac\`,/Mac|iPhone|iPod|iPad/i.test(navigator.platform));         

[Skip to content](#VPContent)

[![](/logo.png)Fabric Documentation](/)

Search

Main Navigation [Home](https://fabricmc.net/)[Contribute](/contributing)

Code

[Fabric API](https://github.com/FabricMC/fabric)

[Fabric Loader](https://github.com/FabricMC/fabric-loader)

[Fabric Loom](https://github.com/FabricMC/fabric-loom)

[Fabric Yarn](https://github.com/FabricMC/yarn)

1.21.10

[Minecraft 1.21.8](/1.21.8/develop/blocks/first-block)[Minecraft 1.21.4](/1.21.4/develop/blocks/first-block)[Minecraft 1.21.1](/1.21.1/develop/blocks/first-block)[Minecraft 1.20.4](/1.20.4/develop/blocks/first-block)

🇺🇸 English (US)

[🇨🇿 Čeština (Česko)](/cs_cz/develop/blocks/first-block)

[🇩🇪 Deutsch (Deutschland)](/de_de/develop/blocks/first-block)

[🇬🇷 Ελληνικά (Ελλάδα)](/el_gr/develop/blocks/first-block)

[🇪🇸 Español (España)](/es_es/develop/blocks/first-block)

[🇫🇷 Français (France)](/fr_fr/develop/blocks/first-block)

[🇮🇹 Italiano (Italia)](/it_it/develop/blocks/first-block)

[🇯🇵 日本語 (日本)](/ja_jp/develop/blocks/first-block)

[🇰🇷 한국어(대한민국)](/ko_kr/develop/blocks/first-block)

[🇵🇱 Polski (Polska)](/pl_pl/develop/blocks/first-block)

[🇧🇷 Português (Brasil)](/pt_br/develop/blocks/first-block)

[🇷🇺 Русский (Россия)](/ru_ru/develop/blocks/first-block)

[🇺🇦 Українська (Україна)](/uk_ua/develop/blocks/first-block)

[🇻🇳 Tiếng Việt (Việt Nam)](/vi_vn/develop/blocks/first-block)

[🇨🇳 中文（简体）](/zh_cn/develop/blocks/first-block)

[🇹🇼 中文（繁體）](/zh_tw/develop/blocks/first-block)

[](https://github.com/FabricMC/fabric-docs)[](https://discord.gg/v6v4pMv)[](https://crowdin.com/project/fabricmc/)

🇺🇸 English (US)

[🇨🇿 Čeština (Česko)](/cs_cz/develop/blocks/first-block)

[🇩🇪 Deutsch (Deutschland)](/de_de/develop/blocks/first-block)

[🇬🇷 Ελληνικά (Ελλάδα)](/el_gr/develop/blocks/first-block)

[🇪🇸 Español (España)](/es_es/develop/blocks/first-block)

[🇫🇷 Français (France)](/fr_fr/develop/blocks/first-block)

[🇮🇹 Italiano (Italia)](/it_it/develop/blocks/first-block)

[🇯🇵 日本語 (日本)](/ja_jp/develop/blocks/first-block)

[🇰🇷 한국어(대한민국)](/ko_kr/develop/blocks/first-block)

[🇵🇱 Polski (Polska)](/pl_pl/develop/blocks/first-block)

[🇧🇷 Português (Brasil)](/pt_br/develop/blocks/first-block)

[🇷🇺 Русский (Россия)](/ru_ru/develop/blocks/first-block)

[🇺🇦 Українська (Україна)](/uk_ua/develop/blocks/first-block)

[🇻🇳 Tiếng Việt (Việt Nam)](/vi_vn/develop/blocks/first-block)

[🇨🇳 中文（简体）](/zh_cn/develop/blocks/first-block)

[🇹🇼 中文（繁體）](/zh_tw/develop/blocks/first-block)

Appearance

[](https://github.com/FabricMC/fabric-docs)[](https://discord.gg/v6v4pMv)[](https://crowdin.com/project/fabricmc/)

Menu

Return to top

Sidebar Navigation

[

Developer Guides
----------------

](/develop/)

[

Creating a Project

](/develop/getting-started/creating-a-project)

[

Project Structure

](/develop/getting-started/project-structure)

[

Setting Up Your IDE

](/develop/getting-started/setting-up)

[

Opening a Project

](/develop/getting-started/opening-a-project)

[

Launching the Game

](/develop/getting-started/launching-the-game)

[

Generating Sources

](/develop/getting-started/generating-sources)

[

IDE Tips and Tricks

](/develop/getting-started/tips-and-tricks)

Items
-----

[

Creating Your First Item

](/develop/items/first-item)

[

Item Models

](/develop/items/item-models)

[

Food Items

](/develop/items/food)

[

Custom Tools

](/develop/items/custom-tools)

[

Custom Armor

](/develop/items/custom-armor)

[

Custom Creative Tabs

](/develop/items/custom-item-groups)

[

Custom Item Interactions

](/develop/items/custom-item-interactions)

[

Custom Enchantment Effects

](/develop/items/custom-enchantment-effects)

[

Custom Data Components

](/develop/items/custom-data-components)

[

Potions

](/develop/items/potions)

[

Item Appearance

](/develop/items/item-appearance)

Blocks
------

[

Creating Your First Block

](/develop/blocks/first-block)

[

Block Models

](/develop/blocks/block-models)

[

Block States

](/develop/blocks/blockstates)

[

### Block Entities

](/develop/blocks/block-entities)

[

Block Entity Renderers

](/develop/blocks/block-entity-renderer)

[

Transparency and Tinting

](/develop/blocks/transparency-and-tinting)

Entities
--------

[

Attributes

](/develop/entities/attributes)

[

Mob Effects

](/develop/entities/effects)

[

Damage Types

](/develop/entities/damage-types)

Sounds
------

[

Playing Sounds

](/develop/sounds/using-sounds)

[

Creating Custom Sounds

](/develop/sounds/custom)

[

Dynamic Sounds

](/develop/sounds/dynamic-sounds)

Commands
--------

[

Creating Commands

](/develop/commands/basics)

[

Command Arguments

](/develop/commands/arguments)

[

Command Suggestions

](/develop/commands/suggestions)

Rendering
---------

[

Basic Rendering Concepts

](/develop/rendering/basic-concepts)

[

Drawing to the GUI

](/develop/rendering/draw-context)

[

Rendering in the HUD

](/develop/rendering/hud)

[

Rendering in the World

](/develop/rendering/world)

### GUIs and Screens

[

Custom Screens

](/develop/rendering/gui/custom-screens)

[

Custom Widgets

](/develop/rendering/gui/custom-widgets)

### Particles

[

Creating Custom Particles

](/develop/rendering/particles/creating-particles)

Data Generation
---------------

[

Data Generation Setup

](/develop/data-generation/setup)

[

Tag Generation

](/develop/data-generation/tags)

[

Translation Generation

](/develop/data-generation/translations)

[

Advancement Generation

](/develop/data-generation/advancements)

[

Recipe Generation

](/develop/data-generation/recipes)

[

Loot Table Generation

](/develop/data-generation/loot-tables)

[

Block Model Generation

](/develop/data-generation/block-models)

Loom
----

[

Introduction

](/develop/loom)

[

Fabric API DSL

](/develop/loom/fabric-api)

[

Extension Options

](/develop/loom/options)

[

Production Run Tasks

](/develop/loom/production-run-tasks)

[

Classpath Groups

](/develop/loom/classpath-groups)

[

Other Tasks

](/develop/loom/tasks)

Miscellaneous Pages
-------------------

[

Codecs

](/develop/codecs)

[

Data Attachments

](/develop/data-attachments)

[

Events

](/develop/events)

[

Networking

](/develop/networking)

[

Saved Data

](/develop/saved-data)

[

Text and Translations

](/develop/text-and-translations)

[

### Migrating Mappings

](/develop/migrating-mappings)

[

Loom (Gradle)

](/develop/migrating-mappings/loom)

[

Ravel (IntelliJ IDEA)

](/develop/migrating-mappings/ravel)

[

Debugging Mods

](/develop/debugging)

[

Automated Testing

](/develop/automatic-testing)

On this page

This page is written for version **1.21.10**.

Page Authors
------------

[![Earthcomputer](https://wsrv.nl/?af=&maxage=7d&url=https%3A%2F%2Fgithub.com%2FEarthcomputer.png%3Fsize%3D32&default=https%3A%2F%2Fdocs.fabricmc.net%2Fassets%2Favatater.png "Earthcomputer")](https://github.com/Earthcomputer)[![IMB11](https://wsrv.nl/?af=&maxage=7d&url=https%3A%2F%2Fgithub.com%2FIMB11.png%3Fsize%3D32&default=https%3A%2F%2Fdocs.fabricmc.net%2Fassets%2Favatater.png "IMB11")](https://github.com/IMB11)[![its-miroma](https://wsrv.nl/?af=&maxage=7d&url=https%3A%2F%2Fgithub.com%2Fits-miroma.png%3Fsize%3D32&default=https%3A%2F%2Fdocs.fabricmc.net%2Fassets%2Favatater.png "its-miroma")](https://github.com/its-miroma)[![xEobardThawne](https://wsrv.nl/?af=&maxage=7d&url=https%3A%2F%2Fgithub.com%2FxEobardThawne.png%3Fsize%3D32&default=https%3A%2F%2Fdocs.fabricmc.net%2Fassets%2Favatater.png "xEobardThawne")](https://github.com/xEobardThawne)

Creating Your First Block
=========================

Page Authors
------------

[![Earthcomputer](https://wsrv.nl/?af=&maxage=7d&url=https%3A%2F%2Fgithub.com%2FEarthcomputer.png%3Fsize%3D32&default=https%3A%2F%2Fdocs.fabricmc.net%2Fassets%2Favatater.png "Earthcomputer")](https://github.com/Earthcomputer)[![IMB11](https://wsrv.nl/?af=&maxage=7d&url=https%3A%2F%2Fgithub.com%2FIMB11.png%3Fsize%3D32&default=https%3A%2F%2Fdocs.fabricmc.net%2Fassets%2Favatater.png "IMB11")](https://github.com/IMB11)[![its-miroma](https://wsrv.nl/?af=&maxage=7d&url=https%3A%2F%2Fgithub.com%2Fits-miroma.png%3Fsize%3D32&default=https%3A%2F%2Fdocs.fabricmc.net%2Fassets%2Favatater.png "its-miroma")](https://github.com/its-miroma)[![xEobardThawne](https://wsrv.nl/?af=&maxage=7d&url=https%3A%2F%2Fgithub.com%2FxEobardThawne.png%3Fsize%3D32&default=https%3A%2F%2Fdocs.fabricmc.net%2Fassets%2Favatater.png "xEobardThawne")](https://github.com/xEobardThawne)

This page is written for version **1.21.10**.

Blocks are the building blocks of Minecraft (no pun intended) - just like everything else in Minecraft, they're stored in registries.

Preparing Your Blocks Class [​](#preparing-your-blocks-class)
-------------------------------------------------------------

If you've completed the [Creating Your First Item](./../items/first-item) page, this process will feel extremely familiar - you will need to create a method that registers your block, and its block item.

You should put this method in a class called `ModBlocks` (or whatever you want to name it).

Mojang does something extremely similar like this with vanilla blocks; you can refer to the `Blocks` class to see how they do it.

java

    public class ModBlocks {
    	private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties settings, boolean shouldRegisterItem) {
    		// Create a registry key for the block
    		ResourceKey<Block> blockKey = keyOfBlock(name);
    		// Create the block instance
    		Block block = blockFactory.apply(settings.setId(blockKey));
    
    		// Sometimes, you may not want to register an item for the block.
    		// Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
    		if (shouldRegisterItem) {
    			// Items need to be registered with a different type of registry key, but the ID
    			// can be the same.
    			ResourceKey<Item> itemKey = keyOfItem(name);
    
    			BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
    			Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
    		}
    
    		return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    	}
    
    	private static ResourceKey<Block> keyOfBlock(String name) {
    		return ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, name));
    	}
    
    	private static ResourceKey<Item> keyOfItem(String name) {
    		return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, name));
    	}
    
    }

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  
20  
21  
22  
23  
24  
25  
26  
27  
28  
29  
30  

Just like with items, you need to ensure that the class is loaded so that all static fields containing your block instances are initialized.

You can do this by creating a dummy `initialize` method, which can be called in your [mod's initializer](./../getting-started/project-structure#entrypoints) to trigger the static initialization.

INFO

If you are unaware of what static initialization is, it is the process of initializing static fields in a class. This is done when the class is loaded by the JVM, and is done before any instances of the class are created.

java

    public class ModBlocks {
        // ...
    
        public static void initialize() {}
    }

1  
2  
3  
4  
5  

java

    public class ExampleModBlocks implements ModInitializer {
    	@Override
    	public void onInitialize() {
    		ModBlocks.initialize();
    	}
    }

1  
2  
3  
4  
5  
6  

Creating And Registering Your Block [​](#creating-and-registering-your-block)
-----------------------------------------------------------------------------

Similarly to items, blocks take a `BlockBehavior.Properties` class in their constructor, which specifies properties about the block, such as its sound effects and mining level.

We will not cover all the options here: you can view the class yourself to see the various options, which should be self-explanatory.

For example purposes, we will be creating a simple block that has the properties of dirt, but is a different material.

*   We create our block settings in a similar way to how we created item settings in the item tutorial.
*   We tell the `register` method to create a `Block` instance from the block settings by calling the `Block` constructor.

TIP

You can also use `BlockBehavior.Properties.ofFullCopy(BlockBehavior block)` to copy the settings of an existing block, in this case, we could have used `Blocks.DIRT` to copy the settings of dirt, but for example purposes we'll use the builder.

java

    public static final Block CONDENSED_DIRT = register(
    		"condensed_dirt",
    		Block::new,
    		BlockBehaviour.Properties.of().sound(SoundType.GRASS),
    		true
    );

1  
2  
3  
4  
5  
6  
7  

To automatically create the block item, we can pass `true` to the `shouldRegisterItem` parameter of the `register` method we created in the previous step.

### Adding Your Block's Item to an Item Group [​](#adding-your-block-s-item-to-an-item-group)

Since the `BlockItem` is automatically created and registered, to add it to an item group, you must use the `Block.asItem()` method to get the `BlockItem` instance.

For this example, we'll use a custom item group created in the [Custom Item Groups](./../items/custom-item-groups) page.

java

    ItemGroupEvents.modifyEntriesEvent(ModItems.CUSTOM_ITEM_GROUP_KEY).register((itemGroup) -> {
    	itemGroup.accept(ModBlocks.CONDENSED_DIRT.asItem());
    });

1  
2  
3  

You should place this within the `initialize()` function of your class.

You should now notice that your block is in the creative inventory, and can be placed in the world!

![Block in world without suitable model or texture](/assets/develop/blocks/first_block_0.png)

There are a few issues though - the block item is not named, and the block has no texture, block model or item model.

Adding Block Translations [​](#adding-block-translations)
---------------------------------------------------------

To add a translation, you must create a translation key in your translation file - `assets/example-mod/lang/en_us.json`.

Minecraft will use this translation in the creative inventory and other places where the block name is displayed, such as command feedback.

json

    {
      "block.example-mod.condensed_dirt": "Condensed Dirt"
    }

1  
2  
3  

You can either restart the game or build your mod and press F3+T to apply changes - and you should see that the block has a name in the creative inventory and other places such as the statistics screen.

Models and Textures [​](#models-and-textures)
---------------------------------------------

All block textures can be found in the `assets/example-mod/textures/block` folder - an example texture for the "Condensed Dirt" block is free to use.

![](/assets/develop/blocks/first_block_1.png)[Download Texture](/assets/develop/blocks/first_block_1_small.png)

To make the texture show up in-game, you must create a block model which can be found in the `assets/example-mod/models/block/condensed_dirt.json` file for the "Condensed Dirt" block. For this block, we're going to use the `block/cube_all` model type.

json

    {
      "parent": "minecraft:block/cube_all",
      "textures": {
        "all": "example-mod:block/condensed_dirt"
      }
    }

1  
2  
3  
4  
5  
6  

For the block to show in your inventory, you will need to create an [Client Item](./../items/first-item#creating-the-client-item) that points to your block model. For this example, the client item for the "Condensed Dirt" block can be found at `assets/example-mod/items/condensed_dirt.json`.

json

    {
      "model": {
        "type": "minecraft:model",
        "model": "example-mod:block/condensed_dirt"
      }
    }

1  
2  
3  
4  
5  
6  

TIP

You only need to create an client item if you've registered a `BlockItem` along with your block!

When you load into the game, you may notice that the texture is still missing. This is because you need to add a blockstate definition.

Creating the Block State Definition [​](#creating-the-block-state-definition)
-----------------------------------------------------------------------------

The blockstate definition is used to instruct the game on which model to render based on the current state of the block.

For the example block, which doesn't have a complex blockstate, only one entry is needed in the definition.

This file should be located in the `assets/example-mod/blockstates` folder, and its name should match the block ID used when registering your block in the `ModBlocks` class. For instance, if the block ID is `condensed_dirt`, the file should be named `condensed_dirt.json`.

json

    {
      "variants": {
        "": {
          "model": "example-mod:block/condensed_dirt"
        }
      }
    }

1  
2  
3  
4  
5  
6  
7  

TIP

Blockstates are incredibly complex, which is why they will be covered next in [their own separate page](./blockstates).

Restarting the game, or reloading via F3+T to apply changes - you should be able to see the block texture in the inventory and physically in the world:

![Block in world with suitable texture and model](/assets/develop/blocks/first_block_4.png)

Adding Block Drops [​](#adding-block-drops)
-------------------------------------------

When breaking the block in survival, you may see that the block does not drop - you might want this functionality, however to make your block drop as an item on break you must implement its loot table - the loot table file should be placed in the `data/example-mod/loot_table/blocks/` folder.

INFO

For a greater understanding of loot tables, you can refer to the [Minecraft Wiki - Loot Tables](https://minecraft.wiki/w/Loot_table) page.

json

    {
      "type": "minecraft:block",
      "pools": [
        {
          "rolls": 1,
          "entries": [
            {
              "type": "minecraft:item",
              "name": "example-mod:condensed_dirt"
            }
          ],
          "conditions": [
            {
              "condition": "minecraft:survives_explosion"
            }
          ]
        }
      ]
    }

1  
2  
3  
4  
5  
6  
7  
8  
9  
10  
11  
12  
13  
14  
15  
16  
17  
18  
19  

This loot table provides a single item drop of the block item when the block is broken, and when it is blown up by an explosion.

Recommending a Harvesting Tool [​](#recommending-a-harvesting-tool)
-------------------------------------------------------------------

You may also want your block to be harvestable only by a specific tool - for example, you may want to make your block faster to harvest with a shovel.

All the tool tags should be placed in the `data/minecraft/tags/block/mineable/` folder - where the name of the file depends on the type of tool used, one of the following:

*   `hoe.json`
*   `axe.json`
*   `pickaxe.json`
*   `shovel.json`

The contents of the file are quite simple - it is a list of items that should be added to the tag.

This example adds the "Condensed Dirt" block to the `shovel` tag.

json

    {
        "replace": false,
        "values": [
            "example-mod:condensed_dirt"
        ]
    }

1  
2  
3  
4  
5  
6  

If you wish for a tool to be required to mine the block, you'll want to append `.requiresTool()` to your block settings, as well as add the appropriate mining level tag.

Mining Levels [​](#mining-levels)
---------------------------------

Similarly, the mining level tag can be found in the `data/minecraft/tags/block/` folder, and respects the following format:

*   `needs_stone_tool.json` - A minimum level of stone tools
*   `needs_iron_tool.json` - A minimum level of iron tools
*   `needs_diamond_tool.json` - A minimum level of diamond tools.

The file has the same format as the harvesting tool file - a list of items to be added to the tag.

Extra Notes [​](#extra-notes)
-----------------------------

If you're adding multiple blocks to your mod, you may want to consider using [Data Generation](./../data-generation/setup) to automate the process of creating block and item models, blockstate definitions, and loot tables.

[Edit this page on GitHub](https://github.com/FabricMC/fabric-docs/edit/main/develop/blocks/first-block.md)

Last updated:

Pager

[Previous pageItem Appearance](/develop/items/item-appearance)

[Next pageBlock Models](/develop/blocks/block-models)

NOT AN OFFICIAL MINECRAFT WEBSITE. NOT APPROVED BY OR ASSOCIATED WITH MOJANG OR MICROSOFT.

© 2025 - The Fabric Documentation Team. Released under the [Creative Commons license](https://github.com/FabricMC/fabric-docs/blob/-/LICENSE).