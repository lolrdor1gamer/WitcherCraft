package org.tgr.witchercraft.block.herb;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.tgr.witchercraft.registry.ModItems;

import java.util.function.Supplier;

/**
 * Sewant Mushroom - grows in mountain caves and dark areas
 * Used for stamina and endurance potions
 */
public class SewantMushroomBlock extends HerbBlock {
    public SewantMushroomBlock(BlockBehaviour.Properties properties) {
        super(properties, () -> ModItems.SEWANT_MUSHROOM);
    }
}
