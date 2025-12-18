package org.tgr.witchercraft.block.herb;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.tgr.witchercraft.registry.ModItems;

import java.util.function.Supplier;

/**
 * Fools Parsley herb - grows in coastal areas
 * Used for toxin-based potions and bombs
 */
public class FoolsParsleyBlock extends HerbBlock {
    public FoolsParsleyBlock(BlockBehaviour.Properties properties) {
        super(properties, () -> ModItems.FOOLS_PARSLEY);
    }
}
