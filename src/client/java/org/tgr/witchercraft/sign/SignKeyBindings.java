package org.tgr.witchercraft.sign;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public final class SignKeyBindings
{
    public static KeyMapping IgniKey;
    public static KeyMapping AardKey;
    public static KeyMapping QuenKey;
    public static KeyMapping AxiiKey;
    public static KeyMapping YrdenKey;
    public static KeyMapping.Category WitcherCraftSigns;

    public static void register() {
        WitcherCraftSigns = KeyMapping.Category.register(ResourceLocation.bySeparator("witchercraft.witchercraft_signs", '.'));
        IgniKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.witchercraft.igni",                         // translation key
                        InputConstants.Type.KEYSYM,                 // key type
                        GLFW.GLFW_KEY_R,                            // default key
                        WitcherCraftSigns                          // category
                )
        );
        AardKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.witchercraft.aard",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_T,
                        WitcherCraftSigns
                )
        );
        QuenKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.witchercraft.quen",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_Y,
                        WitcherCraftSigns
                )
        );
        AxiiKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.witchercraft.axii",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_U,
                        WitcherCraftSigns
                )
        );
        YrdenKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.witchercraft.yrden",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_I,
                        WitcherCraftSigns
                )
        );
        
    }
}
