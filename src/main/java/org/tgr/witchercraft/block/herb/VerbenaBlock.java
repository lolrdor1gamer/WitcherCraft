package org.tgr.witchercraft.block.herb;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.tgr.witchercraft.registry.ModItems;

import java.util.function.Supplier;

/**
 * Verbena herb - grows in mountain meadows
 * Used for anti-vampire potions and spectral oils
 */
public class VerbenaBlock extends HerbBlock {
    public VerbenaBlock(BlockBehaviour.Properties properties) {
        super(properties, () -> ModItems.VERBENA);
    }
}
