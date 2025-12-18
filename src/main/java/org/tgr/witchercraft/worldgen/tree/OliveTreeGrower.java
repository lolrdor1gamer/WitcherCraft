package org.tgr.witchercraft.worldgen.tree;

import net.minecraft.world.level.block.grower.TreeGrower;
import org.tgr.witchercraft.Witchercraft;

import java.util.Optional;

/**
 * Tree grower for Olive trees in Toussaint region.
 * Uses a static TreeGrower instance since TreeGrower is final in 1.21.
 * TODO: Create configured feature for olive tree generation
 */
public final class OliveTreeGrower {
    
    public static final TreeGrower OLIVE = new TreeGrower(
        Witchercraft.MODID + "_olive",
        Optional.empty(),  // Mega tree
        Optional.empty(),  // Primary tree (TODO: implement)
        Optional.empty()   // Flowers
    );
    
    private OliveTreeGrower() {
        // Utility class
    }
}
