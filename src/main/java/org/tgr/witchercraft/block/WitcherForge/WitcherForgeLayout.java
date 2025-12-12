package org.tgr.witchercraft.block.WitcherForge;

/**
 * Shared layout constants for the Witcher Forge menu and screen.
 */
public final class WitcherForgeLayout {

    public static final int IMAGE_WIDTH = 176;
    public static final int IMAGE_HEIGHT = 166;

    public static final int SLOT_POMMEL = 0;
    public static final int SLOT_GRIP = 1;
    public static final int SLOT_GUARD = 2;
    public static final int SLOT_BLADE = 3;
    public static final int SLOT_CORE = 4;
    public static final int SLOT_TRIBUTE = 5;
    public static final int SLOT_OUTPUT = 6;

    public static final SlotPoint[] FORGE_SLOT_POINTS = new SlotPoint[] {
        new SlotPoint(14, 60),   // Pommel
        new SlotPoint(26, 48),   // Grip
        new SlotPoint(38, 36),   // Guard
        new SlotPoint(50, 24),   // Blade material
        new SlotPoint(62, 12),   // Blade core
        new SlotPoint(80, 40),   // Tribute/payment
        new SlotPoint(138, 38)   // Output
    };

    public static final int PLAYER_INV_X = 8;
    public static final int PLAYER_INV_Y = 84;
    public static final int HOTBAR_OFFSET_Y = 58;

    public static final int FORGE_PANEL_LEFT = 8;
    public static final int FORGE_PANEL_TOP = 8;
    public static final int FORGE_PANEL_RIGHT = IMAGE_WIDTH - 8;
    public static final int FORGE_PANEL_BOTTOM = 82;

    public static final int BUTTON_WIDTH = 54;
    public static final int BUTTON_HEIGHT = 16;
    public static final int BUTTON_X = (IMAGE_WIDTH - BUTTON_WIDTH) / 2;
    public static final int BUTTON_Y = 60;

    public static final int SWORD_LINK_X = FORGE_SLOT_POINTS[SLOT_GUARD].x() + 8;
    public static final int SWORD_LINK_Y = FORGE_SLOT_POINTS[SLOT_GUARD].y() + 8;

    public static final int BUTTON_CENTER_X = BUTTON_X + BUTTON_WIDTH / 2;
    public static final int BUTTON_CENTER_Y = BUTTON_Y + BUTTON_HEIGHT / 2;

    private WitcherForgeLayout() {
    }

    public record SlotPoint(int x, int y) {
    }
}
