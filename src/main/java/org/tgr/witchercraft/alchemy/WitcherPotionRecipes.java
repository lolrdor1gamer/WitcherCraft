package org.tgr.witchercraft.alchemy;

import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.tgr.witchercraft.Witchercraft;

import net.minecraft.core.registries.BuiltInRegistries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Data-driven Witcher potion recipes loaded from {@code data/<namespace>/witcher_alchemy_recipes/*.json}.
 */
public final class WitcherPotionRecipes implements SimpleSynchronousResourceReloadListener {

    private static final String DATA_FOLDER = "witcher_alchemy_recipes";
    private static final ResourceLocation FABRIC_ID = ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, DATA_FOLDER);
    private static final WitcherPotionRecipes INSTANCE = new WitcherPotionRecipes();

    private Map<IngredientKey, PotionRecipe> recipes = Map.of();

    private WitcherPotionRecipes() {
    }

    public static void register() {
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(INSTANCE);
    }

    @Nullable
    public static PotionRecipe match(ItemStack base, ItemStack reagent, ItemStack catalyst) {
        return INSTANCE.findMatch(base, reagent, catalyst);
    }

    @Nullable
    private PotionRecipe findMatch(ItemStack base, ItemStack reagent, ItemStack catalyst) {
        if (base.isEmpty() || reagent.isEmpty() || catalyst.isEmpty()) {
            return null;
        }
        return recipes.get(new IngredientKey(base.getItem(), reagent.getItem(), catalyst.getItem()));
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        Object2ObjectOpenHashMap<IngredientKey, PotionRecipe> loaded = new Object2ObjectOpenHashMap<>();
        Predicate<ResourceLocation> filter = id -> id.getPath().endsWith(".json");

        for (Map.Entry<ResourceLocation, Resource> entry : resourceManager.listResources(DATA_FOLDER, filter).entrySet()) {
            ResourceLocation id = entry.getKey();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(entry.getValue().open(), StandardCharsets.UTF_8))) {
                JsonObject root = GsonHelper.parse(reader);
                PotionRecipe recipe = parse(root);
                IngredientKey key = new IngredientKey(recipe.base(), recipe.reagent(), recipe.catalyst());
                loaded.put(key, recipe);
            } catch (IOException | IllegalStateException exception) {
                Witchercraft.LOGGER.error("Failed to load Witcher alchemy recipe {}", id, exception);
            }
        }

        this.recipes = Map.copyOf(loaded);
        Witchercraft.LOGGER.info("Loaded {} Witcher alchemy recipes", this.recipes.size());
    }

    private PotionRecipe parse(JsonObject root) {
        Item base = resolveItem(root, "base");
        Item reagent = resolveItem(root, "reagent");
        Item catalyst = resolveItem(root, "catalyst");

        JsonObject resultObject = GsonHelper.getAsJsonObject(root, "result");
        Item resultItem = resolveItem(resultObject, "item");
        int count = GsonHelper.getAsInt(resultObject, "count", 1);
        ItemStack resultStack = new ItemStack(resultItem, count);

        return new PotionRecipe(base, reagent, catalyst, resultStack);
    }

    private static Item resolveItem(JsonObject json, String member) {
        String id = GsonHelper.getAsString(json, member);
        ResourceLocation identifier = ResourceLocation.parse(id);
        return BuiltInRegistries.ITEM.getOptional(identifier)
            .orElseThrow(() -> new IllegalStateException("Unknown item id: " + identifier));
    }

    @Override
    public ResourceLocation getFabricId() {
        return FABRIC_ID;
    }

    private record IngredientKey(Item base, Item reagent, Item catalyst) {
        private IngredientKey {
            Objects.requireNonNull(base, "Base ingredient cannot be null");
            Objects.requireNonNull(reagent, "Reagent ingredient cannot be null");
            Objects.requireNonNull(catalyst, "Catalyst ingredient cannot be null");
        }
    }

    public record PotionRecipe(Item base, Item reagent, Item catalyst, ItemStack result) {
    }
}
