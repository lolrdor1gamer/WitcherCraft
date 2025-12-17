package org.tgr.witchercraft.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.tgr.witchercraft.faction.Faction;

import java.util.function.Consumer;

/**
 * Quest board block that opens the quest board GUI for a specific faction.
 */
public class QuestBoardBlock extends Block {
    
    private final Faction faction;
    private static Consumer<Faction> clientOpener = null;
    
    public QuestBoardBlock(Properties properties, Faction faction) {
        super(properties);
        this.faction = faction;
    }
    
    public Faction getFaction() {
        return faction;
    }
    
    /**
     * Called from client initialization to set up the GUI opener.
     */
    public static void setClientOpener(Consumer<Faction> opener) {
        clientOpener = opener;
    }
    
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, 
                                              Player player, BlockHitResult hit) {
        if (world.isClientSide() && clientOpener != null) {
            // Client-side handler will open the GUI
            clientOpener.accept(faction);
        }
        return InteractionResult.SUCCESS;
    }
}
