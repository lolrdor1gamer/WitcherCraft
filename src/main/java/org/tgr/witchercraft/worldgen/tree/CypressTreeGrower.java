package org.tgr.witchercraft.worldgen.tree;

import net.minecraft.world.level.block.grower.TreeGrower;
import org.tgr.witchercraft.Witchercraft;

import java.util.Optional;

/**
 * Tree grower for Cypress trees in Toussaint region.
 * Uses a static TreeGrower instance since TreeGrower is final in 1.21.
 * TODO: Create configured feature for cypress tree generation
 */
public final class CypressTreeGrower {
    
    public static final TreeGrower CYPRESS = new TreeGrower(
        Witchercraft.MODID + "_cypress",
        Optional.empty(),  // Mega tree
        Optional.empty(),  // Primary tree (TODO: implement)
        Optional.empty()   // Flowers
    );
    
    private CypressTreeGrower() {
        // Utility class
    }
}
