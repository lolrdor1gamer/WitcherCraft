package org.tgr.witchercraft.block.herb;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.tgr.witchercraft.registry.ModItems;

import java.util.function.Supplier;

/**
 * Crows Eye herb - grows in swamp biomes
 * Used for potions of sight and detection
 */
public class CrowsEyeBlock extends HerbBlock {
    public CrowsEyeBlock(BlockBehaviour.Properties properties) {
        super(properties, () -> ModItems.CROWS_EYE);
    }
}
