package org.tgr.witchercraft.client.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.tgr.witchercraft.bestiary.BestiaryData;
import org.tgr.witchercraft.network.SyncPlayerDataPacket;
import org.tgr.witchercraft.player.PlayerAttributes;
import org.tgr.witchercraft.player.PlayerLevel;
import org.tgr.witchercraft.player.PlayerSkills;

import java.util.Map;

/**
 * Client-side copy of player progression data
 * Updated when receiving SyncPlayerDataPacket from server
 */
@Environment(EnvType.CLIENT)
public class ClientPlayerData {
    
    private static final PlayerLevel level = new PlayerLevel();
    private static final PlayerAttributes attributes = new PlayerAttributes();
    private static final PlayerSkills skills = new PlayerSkills();
    private static BestiaryData bestiaryData = new BestiaryData();
    
    public static void update(SyncPlayerDataPacket packet) {
        // Update level data
        level.setLevel(packet.getLevel());
        level.setExperience(packet.getExperience());
        level.setAttributePoints(packet.getAttributePoints());
        level.setSkillPoints(packet.getSkillPoints());
        
        // Update attributes
        attributes.setStrength(packet.getStrength());
        attributes.setVitality(packet.getVitality());
        attributes.setCombat(packet.getCombat());
        attributes.setSigns(packet.getSigns());
        attributes.setAlchemy(packet.getAlchemy());
        
        // Update skills
        for (Map.Entry<String, Integer> entry : packet.getSkills().entrySet()) {
            skills.setSkillRank(entry.getKey(), entry.getValue());
        }
    }
    
    public static PlayerLevel getLevel() {
        return level;
    }
    
    public static PlayerAttributes getAttributes() {
        return attributes;
    }
    
    public static PlayerSkills getSkills() {
        return skills;
    }
    
    public static BestiaryData getBestiaryData() {
        return bestiaryData;
    }
    
    public static void updateBestiaryData(BestiaryData data) {
        bestiaryData = data;
    }
}
