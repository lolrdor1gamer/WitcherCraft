package org.tgr.witchercraft.block.herb;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.tgr.witchercraft.registry.ModItems;

import java.util.function.Supplier;

/**
 * Blowball herb - grows in swamp and wetland biomes
 * Used for healing and regeneration potions
 */
public class BlowballBlock extends HerbBlock {
    public BlowballBlock(BlockBehaviour.Properties properties) {
        super(properties, () -> ModItems.BLOWBALL);
    }
}
