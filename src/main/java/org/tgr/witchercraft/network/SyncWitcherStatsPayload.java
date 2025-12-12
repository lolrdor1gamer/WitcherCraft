package org.tgr.witchercraft.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.player.WitcherPlayerData;

import java.util.HashMap;
import java.util.Map;

/**
 * Server-to-client payload that keeps Witcher stamina/mana/cooldown values in sync for HUD use.
 */
public final class SyncWitcherStatsPayload implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "sync_witcher_stats");
    public static final Type<SyncWitcherStatsPayload> TYPE = new Type<>(ID);
    public static final StreamCodec<FriendlyByteBuf, SyncWitcherStatsPayload> CODEC =
            CustomPacketPayload.codec(SyncWitcherStatsPayload::write, SyncWitcherStatsPayload::new);

    private final int stamina;
    private final int mana;
    private final int toxicity;
    private final Map<String, Integer> cooldowns;

    public SyncWitcherStatsPayload(WitcherPlayerData.StatsSnapshot snapshot) {
        this(snapshot.stamina(), snapshot.mana(), snapshot.toxicity(), snapshot.cooldowns());
    }

    public SyncWitcherStatsPayload(int stamina, int mana, int toxicity, Map<String, Integer> cooldowns) {
        this.stamina = stamina;
        this.mana = mana;
        this.toxicity = toxicity;
        this.cooldowns = cooldowns;
    }

    private SyncWitcherStatsPayload(FriendlyByteBuf buf) {
        this.stamina = buf.readVarInt();
        this.mana = buf.readVarInt();
        this.toxicity = buf.readVarInt();
        int size = buf.readVarInt();
        this.cooldowns = new HashMap<>();
        for (int i = 0; i < size; i++) {
            String key = buf.readUtf();
            int value = buf.readVarInt();
            this.cooldowns.put(key, value);
        }
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeVarInt(stamina);
        buf.writeVarInt(mana);
        buf.writeVarInt(toxicity);
        buf.writeVarInt(cooldowns.size());
        cooldowns.forEach((sign, ticks) -> {
            buf.writeUtf(sign);
            buf.writeVarInt(ticks);
        });
    }

    @Override
    public Type<SyncWitcherStatsPayload> type() {
        return TYPE;
    }

    public int stamina() {
        return stamina;
    }

    public int mana() {
        return mana;
    }

    public int toxicity() {
        return toxicity;
    }

    public Map<String, Integer> cooldowns() {
        return cooldowns;
    }

    public static void register() {
        PayloadTypeRegistry.playS2C().register(TYPE, CODEC);
    }

    public static void send(ServerPlayer player, WitcherPlayerData.StatsSnapshot snapshot) {
        ServerPlayNetworking.send(player, new SyncWitcherStatsPayload(snapshot));
    }
}
