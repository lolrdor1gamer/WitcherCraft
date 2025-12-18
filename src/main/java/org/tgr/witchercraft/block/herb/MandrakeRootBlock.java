package org.tgr.witchercraft.block.herb;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.tgr.witchercraft.registry.ModItems;

import java.util.function.Supplier;

/**
 * Mandrake Root herb - rare, grows in ancient forests
 * Used for powerful alchemical concoctions and toxicity management
 */
public class MandrakeRootBlock extends HerbBlock {
    public MandrakeRootBlock(BlockBehaviour.Properties properties) {
        super(properties, () -> ModItems.MANDRAKE_ROOT);
    }
}
