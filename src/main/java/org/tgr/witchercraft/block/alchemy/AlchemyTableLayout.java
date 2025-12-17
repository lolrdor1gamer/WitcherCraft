package org.tgr.witchercraft.block.alchemy;

/**
 * Layout constants shared between the alchemy table menu and screen.
 */
public final class AlchemyTableLayout {

    public static final int IMAGE_WIDTH = 176;
    public static final int IMAGE_HEIGHT = 166;

    public static final int BLOCK_AREA_HEIGHT = 72;

    public static final SlotPoint BASE_SLOT = new SlotPoint(30, 28);
    public static final SlotPoint REAGENT_SLOT = new SlotPoint(62, 14);
    public static final SlotPoint CATALYST_SLOT = new SlotPoint(94, 28);
    public static final SlotPoint OUTPUT_SLOT = new SlotPoint(140, 22);

    public static final int PLAYER_INV_X = 8;
    public static final int PLAYER_INV_Y = BLOCK_AREA_HEIGHT + 12;
    public static final int HOTBAR_OFFSET_Y = 58;

    private AlchemyTableLayout() {
    }

    public record SlotPoint(int x, int y) {
    }
}
