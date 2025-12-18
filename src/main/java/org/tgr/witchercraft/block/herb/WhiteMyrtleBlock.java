package org.tgr.witchercraft.block.herb;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.tgr.witchercraft.registry.ModItems;

import java.util.function.Supplier;

/**
 * White Myrtle herb - rare, grows in deep swamps
 * Used for advanced Witcher potions and oils
 */
public class WhiteMyrtleBlock extends HerbBlock {
    public WhiteMyrtleBlock(BlockBehaviour.Properties properties) {
        super(properties, () -> ModItems.WHITE_MYRTLE);
    }
}
