package org.tgr.witchercraft.block.herb;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.tgr.witchercraft.registry.ModItems;

import java.util.function.Supplier;

/**
 * Wolfsbane herb - grows in dark forests
 * Used for beast oil and lycanthropy resistance potions
 */
public class WolfsbaneBlock extends HerbBlock {
    public WolfsbaneBlock(BlockBehaviour.Properties properties) {
        super(properties, () -> ModItems.WOLFSBANE);
    }
}
