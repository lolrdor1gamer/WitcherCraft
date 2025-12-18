package org.tgr.witchercraft.client.ui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public final class UIKeyBindings {
    
    public static KeyMapping CharacterScreenKey;
    public static KeyMapping SkillTreeKey;
    public static KeyMapping QuestJournalKey;
    public static KeyMapping BestiaryKey;
    public static KeyMapping MeditationKey;
    public static KeyMapping WitcherSensesKey;
    public static KeyMapping DodgeKey;
    public static KeyMapping.Category WitcherCraftUI;

    public static void register() {
        WitcherCraftUI = KeyMapping.Category.register(ResourceLocation.bySeparator("witchercraft.witchercraft_ui", '.'));
        
        CharacterScreenKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.witchercraft.character",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_C,
                        WitcherCraftUI
                )
        );
        
        SkillTreeKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.witchercraft.skills",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_K,
                        WitcherCraftUI
                )
        );
        
        QuestJournalKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.witchercraft.quest_journal",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_J,
                        WitcherCraftUI
                )
        );
        
        BestiaryKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.witchercraft.bestiary",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_B,
                        WitcherCraftUI
                )
        );
        
        MeditationKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.witchercraft.meditation",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_M,
                        WitcherCraftUI
                )
        );
        
        WitcherSensesKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.witchercraft.witcher_senses",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_V,
                        WitcherCraftUI
                )
        );
        
        DodgeKey = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        "key.witchercraft.dodge",
                        InputConstants.Type.KEYSYM,
                        GLFW.GLFW_KEY_LEFT_ALT,
                        WitcherCraftUI
                )
        );
    }
}
