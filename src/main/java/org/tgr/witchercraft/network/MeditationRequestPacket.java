package org.tgr.witchercraft.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import org.tgr.witchercraft.Witchercraft;

/**
 * Client requests meditation - server handles time skip and healing
 */
public class MeditationRequestPacket implements CustomPacketPayload {
    
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "meditation_request");
    
    public static final StreamCodec<FriendlyByteBuf, MeditationRequestPacket> CODEC = 
        CustomPacketPayload.codec(MeditationRequestPacket::write, MeditationRequestPacket::new);
    
    public static final CustomPacketPayload.Type<MeditationRequestPacket> TYPE = 
        new CustomPacketPayload.Type<>(ID);
    
    private final int hours;
    
    public MeditationRequestPacket(int hours) {
        this.hours = Math.max(1, Math.min(12, hours));
    }
    
    public MeditationRequestPacket(FriendlyByteBuf buf) {
        this.hours = buf.readInt();
    }
    
    private void write(FriendlyByteBuf buf) {
        buf.writeInt(hours);
    }
    
    @Override
    public CustomPacketPayload.Type<MeditationRequestPacket> type() {
        return TYPE;
    }
    
    public int getHours() {
        return hours;
    }
    
    public static void register() {
        PayloadTypeRegistry.playC2S().register(TYPE, CODEC);
        
        ServerPlayNetworking.registerGlobalReceiver(TYPE, (payload, context) -> {
            context.server().submit(() -> {
                ServerPlayer player = context.player();
                int hoursToSkip = payload.getHours();
                
                // Validate - player must be on ground, not in combat, etc.
                if (!canMeditate(player)) {
                    return;
                }
                
                // Perform meditation effects
                performMeditation(player, hoursToSkip);
            });
        });
    }
    
    private static boolean canMeditate(ServerPlayer player) {
        // Basic checks
        if (player.isInWater() || player.isOnFire() || player.isFreezing()) {
            return false;
        }
        
        // Check if player is on ground
        if (!player.onGround()) {
            return false;
        }
        
        // Could add more checks: nearby hostile mobs, etc.
        return true;
    }
    
    private static void performMeditation(ServerPlayer player, int hours) {
        // 1. Heal the player fully
        player.heal(player.getMaxHealth());
        
        // 2. Clear negative effects (simulating toxicity decay)
        player.removeEffect(MobEffects.POISON);
        player.removeEffect(MobEffects.WITHER);
        player.removeEffect(MobEffects.WEAKNESS);
        player.removeEffect(MobEffects.HUNGER);
        player.removeEffect(MobEffects.SLOWNESS);
        player.removeEffect(MobEffects.MINING_FATIGUE);
        
        // 3. Restore food level
        player.getFoodData().setFoodLevel(20);
        player.getFoodData().setSaturation(5.0f);
        
        // 4. Skip time (1000 ticks = 1 in-game hour)
        ServerLevel level = (ServerLevel) player.level();
        if (level != null) {
            long currentTime = level.getDayTime();
            long ticksToAdd = hours * 1000L;
            
            // Only change time in singleplayer or if player is the only one / has permissions
            if (level.getServer().isSingleplayer() || player.hasPermissions(2)) {
                level.setDayTime(currentTime + ticksToAdd);
            }
        }
        
        // 5. Send updated player data
        // Could also replenish potions here if we track ingredients
        
        Witchercraft.LOGGER.debug("Player {} meditated for {} hours", player.getName().getString(), hours);
    }
}
