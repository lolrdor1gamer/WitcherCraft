package org.tgr.witchercraft.client.reputation;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public final class ReputationHUDBindings
{
    public static KeyMapping ReputationKey;
    public static KeyMapping QuestsKey;

    public static KeyMapping.Category WitcherCraftREP;

    public static void register() {
        WitcherCraftREP = KeyMapping.Category.register(ResourceLocation.bySeparator("witchercraft.witchercraft_hud", '.'));
        ReputationKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.witchercraft.reputation",                         // translation key
                        InputConstants.Type.KEYSYM,                 // key type
                        GLFW.GLFW_KEY_O,                            // default key
                        WitcherCraftREP                          // category
                )
        );
        QuestsKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.witchercraft.quests",                         // translation key
                        InputConstants.Type.KEYSYM,                 // key type
                        GLFW.GLFW_KEY_P,                            // default key
                        WitcherCraftREP                          // category
                )
        );
    }
}
