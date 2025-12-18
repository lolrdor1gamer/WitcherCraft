package org.tgr.witchercraft.block.herb;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.tgr.witchercraft.registry.ModItems;

import java.util.function.Supplier;

/**
 * Wormwood herb - grows in battlefields and cursed lands
 * Used for spirit and wraith-repelling potions
 */
public class WormwoodBlock extends HerbBlock {
    public WormwoodBlock(BlockBehaviour.Properties properties) {
        super(properties, () -> ModItems.WORMWOOD);
    }
}
