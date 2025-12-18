package org.tgr.witchercraft.block.herb;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.tgr.witchercraft.registry.ModItems;

import java.util.function.Supplier;

/**
 * Berbercane Fruit - grows in plains and grasslands
 * Used for energy and focus potions
 */
public class BerbercaneFruitBlock extends HerbBlock {
    public BerbercaneFruitBlock(BlockBehaviour.Properties properties) {
        super(properties, () -> ModItems.BERBERCANE_FRUIT);
    }
}
