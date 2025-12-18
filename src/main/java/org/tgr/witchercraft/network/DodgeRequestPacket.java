package org.tgr.witchercraft.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.combat.DodgeData;

/**
 * Client requests a dodge/roll - server handles movement and invincibility frames
 */
public class DodgeRequestPacket implements CustomPacketPayload {
    
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "dodge_request");
    
    public static final StreamCodec<FriendlyByteBuf, DodgeRequestPacket> CODEC = 
        CustomPacketPayload.codec(DodgeRequestPacket::write, DodgeRequestPacket::new);
    
    public static final CustomPacketPayload.Type<DodgeRequestPacket> TYPE = 
        new CustomPacketPayload.Type<>(ID);
    
    private final double forward;
    private final double strafe;
    
    public DodgeRequestPacket(double forward, double strafe) {
        this.forward = forward;
        this.strafe = strafe;
    }
    
    public DodgeRequestPacket(FriendlyByteBuf buf) {
        this.forward = buf.readDouble();
        this.strafe = buf.readDouble();
    }
    
    private void write(FriendlyByteBuf buf) {
        buf.writeDouble(forward);
        buf.writeDouble(strafe);
    }
    
    @Override
    public CustomPacketPayload.Type<DodgeRequestPacket> type() {
        return TYPE;
    }
    
    public static void register() {
        PayloadTypeRegistry.playC2S().register(TYPE, CODEC);
        
        ServerPlayNetworking.registerGlobalReceiver(TYPE, (payload, context) -> {
            context.server().submit(() -> {
                ServerPlayer player = context.player();
                
                // Check if player can dodge
                if (!canDodge(player)) {
                    return;
                }
                
                // Perform dodge
                performDodge(player, payload.forward, payload.strafe);
            });
        });
    }
    
    private static boolean canDodge(ServerPlayer player) {
        // Can't dodge in water, on fire, or while flying
        if (player.isInWater() || player.isOnFire() || player.getAbilities().flying) {
            return false;
        }
        
        // Check cooldown via attachment data
        DodgeData data = DodgeData.get(player);
        if (data.isOnCooldown()) {
            return false;
        }
        
        return true;
    }
    
    private static void performDodge(ServerPlayer player, double forward, double strafe) {
        // Calculate dodge direction based on player's look direction
        float yaw = player.getYRot() * ((float) Math.PI / 180F);
        
        // Forward vector
        double forwardX = -Math.sin(yaw);
        double forwardZ = Math.cos(yaw);
        
        // Right vector (perpendicular to forward)
        double rightX = forwardZ;
        double rightZ = -forwardX;
        
        // Combine directions
        double dirX = forwardX * forward + rightX * strafe;
        double dirZ = forwardZ * forward + rightZ * strafe;
        
        // Normalize
        double length = Math.sqrt(dirX * dirX + dirZ * dirZ);
        if (length > 0) {
            dirX /= length;
            dirZ /= length;
        }
        
        // Apply dodge velocity
        double dodgeSpeed = 1.2;
        double dodgeUpward = 0.3;
        
        Vec3 currentVel = player.getDeltaMovement();
        player.setDeltaMovement(
            dirX * dodgeSpeed,
            currentVel.y + dodgeUpward,
            dirZ * dodgeSpeed
        );
        player.hurtMarked = true; // Force velocity update to client
        
        // Apply invincibility frames
        DodgeData data = DodgeData.get(player);
        data.startDodge();
        player.setInvulnerable(false); // Use our own iframe system
        
        // Play dodge sound
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
            SoundEvents.WOOL_FALL, SoundSource.PLAYERS, 0.5f, 1.2f);
        
        Witchercraft.LOGGER.debug("Player {} performed dodge", player.getName().getString());
    }
}
