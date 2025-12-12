package org.tgr.witchercraft.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import org.tgr.witchercraft.Witchercraft;
import org.tgr.witchercraft.block.WitcherForge.WitcherForgeMenu;
import org.tgr.witchercraft.block.alchemy.AlchemyTableMenu;

public final class ModMenus {

    public static final MenuType<WitcherForgeMenu> WITCHER_FORGE_MENU = Registry.register(
            BuiltInRegistries.MENU,
            ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "witcher_forge"),
        new MenuType<>(WitcherForgeMenu::new, FeatureFlags.VANILLA_SET)
    );

    public static final MenuType<AlchemyTableMenu> ALCHEMY_TABLE_MENU = Registry.register(
            BuiltInRegistries.MENU,
            ResourceLocation.fromNamespaceAndPath(Witchercraft.MODID, "alchemy_table"),
        new MenuType<>(AlchemyTableMenu::new, FeatureFlags.VANILLA_SET)
    );

    private ModMenus() {
    }

    public static void initialize() {
        Witchercraft.LOGGER.info("Registered menu types");
    }
}
