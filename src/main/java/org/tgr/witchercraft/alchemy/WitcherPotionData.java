package org.tgr.witchercraft.alchemy;

import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.player.WitcherPlayerData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Loads Witcher potion properties from {@code data/<namespace>/witcher_potions/*.json} files.
 */
public final class WitcherPotionData implements SimpleSynchronousResourceReloadListener {

    private static final WitcherPotionData INSTANCE = new WitcherPotionData();
    private static final ResourceLocation FABRIC_ID = ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "witcher_potion_data");
    private static final String DATA_FOLDER = "witcher_potions";

    private Map<Item, PotionData> potionData = Map.of();

    private WitcherPotionData() {
    }

    public static void register() {
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(INSTANCE);
    }

    public static void apply(ItemStack stack, Level level, Player player) {
        if (!(player instanceof net.minecraft.server.level.ServerPlayer serverPlayer)) {
            return;
        }
        PotionData data = INSTANCE.potionData.get(stack.getItem());
        if (data == null) {
            return;
        }

        if (data.toxicity() > 0) {
            WitcherPlayerData.addToxicity(serverPlayer, data.toxicity());
        }

        if (data.staminaRegen() != null) {
            WitcherPlayerData.applyStaminaRegenBoost(serverPlayer, data.staminaRegen().duration(), data.staminaRegen().amount());
        }
        if (data.manaRegen() != null) {
            WitcherPlayerData.applyManaRegenBoost(serverPlayer, data.manaRegen().duration(), data.manaRegen().amount());
        }

        Healing healing = data.healing();
        if (healing.fullHeal()) {
            serverPlayer.heal(serverPlayer.getMaxHealth());
        }
        if (healing.healAmount() > 0) {
            serverPlayer.heal(healing.healAmount());
        }
        if (healing.healPercent() > 0) {
            serverPlayer.heal(serverPlayer.getMaxHealth() * healing.healPercent());
        }

        for (ConfiguredEffect configuredEffect : data.effects()) {
            MobEffectInstance instance = new MobEffectInstance(
                configuredEffect.effect(),
                configuredEffect.duration(),
                configuredEffect.amplifier(),
                configuredEffect.ambient(),
                configuredEffect.visible()
            );
            serverPlayer.addEffect(instance);
        }
    }

    @Nullable
    public static PotionData get(Item item) {
        return INSTANCE.potionData.get(item);
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        Witchercraft.LOGGER.info("[WitcherPotionData] Reloading potion definitions");
        Object2ObjectOpenHashMap<Item, PotionData> loaded = new Object2ObjectOpenHashMap<>();
        Predicate<ResourceLocation> filter = id -> id.getPath().endsWith(".json");

        for (Map.Entry<ResourceLocation, Resource> entry : resourceManager.listResources(DATA_FOLDER, filter).entrySet()) {
            ResourceLocation id = entry.getKey();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(entry.getValue().open(), StandardCharsets.UTF_8))) {
                JsonObject json = GsonHelper.parse(reader);
                PotionData data = parse(json);
                loaded.put(data.item(), data);
            } catch (IOException | IllegalStateException e) {
                Witchercraft.LOGGER.error("Failed to read potion data {}", id, e);
            }
        }

        this.potionData = loaded;
        Witchercraft.LOGGER.info("[WitcherPotionData] Loaded {} potion definitions", this.potionData.size());
    }

    private PotionData parse(JsonObject root) {
        Item item = resolveItem(root, "item");
        int toxicity = GsonHelper.getAsInt(root, "toxicity", 0);
        List<ConfiguredEffect> effects = parseEffects(root);
        RegenBoost stamina = root.has("stamina_regen_bonus") ? parseRegen(GsonHelper.getAsJsonObject(root, "stamina_regen_bonus")) : null;
        RegenBoost mana = root.has("mana_regen_bonus") ? parseRegen(GsonHelper.getAsJsonObject(root, "mana_regen_bonus")) : null;
        Healing healing = root.has("healing") ? parseHealing(GsonHelper.getAsJsonObject(root, "healing")) : Healing.NONE;
        return new PotionData(item, toxicity, effects, stamina, mana, healing);
    }

    private List<ConfiguredEffect> parseEffects(JsonObject root) {
        if (!root.has("effects")) {
            return List.of();
        }
        var array = GsonHelper.getAsJsonArray(root, "effects");
        List<ConfiguredEffect> effects = new ArrayList<>(array.size());
        for (var element : array) {
            JsonObject obj = element.getAsJsonObject();
            Holder<MobEffect> effect = resolveEffect(obj, "id");
            int duration = GsonHelper.getAsInt(obj, "duration");
            int amplifier = GsonHelper.getAsInt(obj, "amplifier", 0);
            boolean ambient = GsonHelper.getAsBoolean(obj, "ambient", false);
            boolean visible = GsonHelper.getAsBoolean(obj, "visible", true);
            effects.add(new ConfiguredEffect(effect, duration, amplifier, ambient, visible));
        }
        return effects;
    }

    private RegenBoost parseRegen(JsonObject json) {
        int amount = GsonHelper.getAsInt(json, "amount");
        int duration = GsonHelper.getAsInt(json, "duration");
        return new RegenBoost(amount, duration);
    }

    private Healing parseHealing(JsonObject json) {
        boolean full = GsonHelper.getAsBoolean(json, "full", false);
        float percent = GsonHelper.getAsFloat(json, "heal_percent", 0.0F);
        float amount = GsonHelper.getAsFloat(json, "heal_amount", 0.0F);
        return new Healing(full, percent, amount);
    }

    private static Item resolveItem(JsonObject json, String member) {
        String id = GsonHelper.getAsString(json, member);
        ResourceLocation identifier = ResourceLocation.parse(id);
        return BuiltInRegistries.ITEM.getOptional(identifier)
            .orElseThrow(() -> new IllegalStateException("Unknown item id: " + identifier));
    }

    private static Holder<MobEffect> resolveEffect(JsonObject json, String member) {
        String id = GsonHelper.getAsString(json, member);
        ResourceLocation identifier = ResourceLocation.parse(id);
        MobEffect effect = BuiltInRegistries.MOB_EFFECT.getOptional(identifier)
            .orElseThrow(() -> new IllegalStateException("Unknown effect id: " + identifier));
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect);
    }

    public record PotionData(Item item, int toxicity, List<ConfiguredEffect> effects, @Nullable RegenBoost staminaRegen,
                              @Nullable RegenBoost manaRegen, Healing healing) {
    }

    public record ConfiguredEffect(Holder<MobEffect> effect, int duration, int amplifier, boolean ambient,
                                   boolean visible) {
    }

    public record RegenBoost(int amount, int duration) {
    }

    public record Healing(boolean fullHeal, float healPercent, float healAmount) {
        private static final Healing NONE = new Healing(false, 0.0F, 0.0F);
    }

    @Override
    public ResourceLocation getFabricId() {
        return FABRIC_ID;
    }
}
