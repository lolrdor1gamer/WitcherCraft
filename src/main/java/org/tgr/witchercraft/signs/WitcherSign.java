package org.tgr.witchercraft.signs;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.tgr.witchercraft.player.WitcherPlayerData;

/**
 * Base class for all Witcher Signs
 */
public abstract class WitcherSign {
    
    protected final String name;
    protected final int cooldown;
    protected final int manaCost;
    
    public WitcherSign(String name, int cooldown, int manaCost) {
        this.name = name;
        this.cooldown = cooldown;
        this.manaCost = manaCost;
    }
    
    /**
     * Cast the sign
     * @param player The player casting the sign
     * @param level The world
     * @return true if the sign was successfully cast
     */
    public abstract boolean cast(Player player, Level level);
    
    /**
     * Check if the player can cast this sign
     */
    public boolean canCast(Player player) {
        return player != null && WitcherPlayerData.hasResourcesForSign(player, this);
    }
    
    public String getName() {
        return name;
    }
    
    public int getCooldown() {
        return cooldown;
    }
    
    public int getManaCost() {
        return manaCost;
    }
}
