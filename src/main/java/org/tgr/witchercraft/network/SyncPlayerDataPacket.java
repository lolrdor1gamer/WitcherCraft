package org.tgr.witchercraft.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.tgr.witchercraft.Witchercraft;

import java.util.HashMap;
import java.util.Map;

/**
 * Server sends player progression data to client
 * Client receives and updates UI screens
 */
public class SyncPlayerDataPacket implements CustomPacketPayload {
    
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "sync_player_data");
    
    public static final StreamCodec<FriendlyByteBuf, SyncPlayerDataPacket> CODEC = 
        CustomPacketPayload.codec(SyncPlayerDataPacket::write, SyncPlayerDataPacket::new);
    
    public static final CustomPacketPayload.Type<SyncPlayerDataPacket> TYPE = 
        new CustomPacketPayload.Type<>(ID);
    
    private final int level;
    private final int experience;
    private final int attributePoints;
    private final int skillPoints;
    private final int strength;
    private final int vitality;
    private final int combat;
    private final int signs;
    private final int alchemy;
    private final Map<String, Integer> skills;
    
    public SyncPlayerDataPacket(int level, int experience, int attributePoints, int skillPoints,
                                 int strength, int vitality, int combat, int signs, int alchemy,
                                 Map<String, Integer> skills) {
        this.level = level;
        this.experience = experience;
        this.attributePoints = attributePoints;
        this.skillPoints = skillPoints;
        this.strength = strength;
        this.vitality = vitality;
        this.combat = combat;
        this.signs = signs;
        this.alchemy = alchemy;
        this.skills = skills;
    }
    
    public SyncPlayerDataPacket(FriendlyByteBuf buf) {
        this.level = buf.readInt();
        this.experience = buf.readInt();
        this.attributePoints = buf.readInt();
        this.skillPoints = buf.readInt();
        this.strength = buf.readInt();
        this.vitality = buf.readInt();
        this.combat = buf.readInt();
        this.signs = buf.readInt();
        this.alchemy = buf.readInt();
        
        // Read skills map
        int skillCount = buf.readInt();
        this.skills = new HashMap<>();
        for (int i = 0; i < skillCount; i++) {
            String skillId = buf.readUtf();
            int rank = buf.readInt();
            this.skills.put(skillId, rank);
        }
    }
    
    private void write(FriendlyByteBuf buf) {
        buf.writeInt(level);
        buf.writeInt(experience);
        buf.writeInt(attributePoints);
        buf.writeInt(skillPoints);
        buf.writeInt(strength);
        buf.writeInt(vitality);
        buf.writeInt(combat);
        buf.writeInt(signs);
        buf.writeInt(alchemy);
        
        // Write skills map
        buf.writeInt(skills.size());
        for (Map.Entry<String, Integer> entry : skills.entrySet()) {
            buf.writeUtf(entry.getKey());
            buf.writeInt(entry.getValue());
        }
    }
    
    @Override
    public CustomPacketPayload.Type<SyncPlayerDataPacket> type() {
        return TYPE;
    }
    
    // Getters
    public int getLevel() { return level; }
    public int getExperience() { return experience; }
    public int getAttributePoints() { return attributePoints; }
    public int getSkillPoints() { return skillPoints; }
    public int getStrength() { return strength; }
    public int getVitality() { return vitality; }
    public int getCombat() { return combat; }
    public int getSigns() { return signs; }
    public int getAlchemy() { return alchemy; }
    public Map<String, Integer> getSkills() { return skills; }
    
    public static void register() {
        PayloadTypeRegistry.playS2C().register(TYPE, CODEC);
        // Client-side handling is in ClientNetworking
    }
}
