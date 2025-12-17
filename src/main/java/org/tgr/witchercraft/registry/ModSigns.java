package org.tgr.witchercraft.registry;

import org.tgr.witchercraft.signs.AardSign;
import org.tgr.witchercraft.signs.AxiiSign;
import org.tgr.witchercraft.signs.IgniSign;
import org.tgr.witchercraft.signs.QuenSign;
import org.tgr.witchercraft.signs.WitcherSign;
import org.tgr.witchercraft.signs.YrdenSign;

import java.util.Locale;
import java.util.Map;

/**
 * Central registry for Witcher signs available to both client and server.
 */
public final class ModSigns {

    private static final Map<String, WitcherSign> SIGNS = Map.of(
        "igni", new IgniSign(),
        "aard", new AardSign(),
        "quen", new QuenSign(),
        "yrden", new YrdenSign(),
        "axii", new AxiiSign()
    );

    private ModSigns() {
    }

    public static WitcherSign get(String name) {
        if (name == null) {
            return null;
        }
        return SIGNS.get(name.toLowerCase(Locale.ROOT));
    }

    public static Map<String, WitcherSign> all() {
        return SIGNS;
    }
}
