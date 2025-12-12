package org.tgr.witchercraft.block.alchemy;

/**
 * Layout constants shared between the alchemy table menu and screen.
 */
public final class AlchemyTableLayout {

    public static final int IMAGE_WIDTH = 176;
    public static final int IMAGE_HEIGHT = 166;

    public static final SlotPoint BASE_SLOT = new SlotPoint(30, 34);
    public static final SlotPoint REAGENT_SLOT = new SlotPoint(60, 20);
    public static final SlotPoint CATALYST_SLOT = new SlotPoint(90, 34);
    public static final SlotPoint OUTPUT_SLOT = new SlotPoint(140, 28);

    public static final int PLAYER_INV_X = 8;
    public static final int PLAYER_INV_Y = 84;
    public static final int HOTBAR_OFFSET_Y = 58;

    private AlchemyTableLayout() {
    }

    public record SlotPoint(int x, int y) {
    }
}
