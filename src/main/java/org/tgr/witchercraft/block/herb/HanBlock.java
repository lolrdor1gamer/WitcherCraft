package org.tgr.witchercraft.block.herb;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.tgr.witchercraft.registry.ModItems;

import java.util.function.Supplier;

/**
 * Han herb - rare, grows in battlefields with necrophage presence
 * Used for advanced alchemical formulas and mutagenic extracts
 */
public class HanBlock extends HerbBlock {
    public HanBlock(BlockBehaviour.Properties properties) {
        super(properties, () -> ModItems.HAN);
    }
}
