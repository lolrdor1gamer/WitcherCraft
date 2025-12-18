package org.tgr.witchercraft.item.bomb;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

public enum BombType {
    SAMUM("samum", 6.0f, 0, null, true),
    GRAPESHOT("grapeshot", 10.0f, 0, null, false),
    DANCING_STAR("dancing_star", 4.0f, 200, null, false),
    DEVILS_PUFFBALL("devils_puffball", 2.0f, 100, "minecraft:poison", false),
    DIMERITIUM("dimeritium", 0.0f, 200, null, false),
    DRAGONS_DREAM("dragons_dream", 0.0f, 0, null, false),
    MOON_DUST("moon_dust", 2.0f, 100, null, false),
    NORTHERN_WIND("northern_wind", 0.0f, 100, null, false);

    private final String name;
    private final float damage;
    private final int effectDuration;
    private final String effectId;
    private final boolean blinds;

    BombType(String name, float damage, int effectDuration, String effectId, boolean blinds) {
        this.name = name;
        this.damage = damage;
        this.effectDuration = effectDuration;
        this.effectId = effectId;
        this.blinds = blinds;
    }

    public String getName() { return name; }
    public float getDamage() { return damage; }
    public int getEffectDuration() { return effectDuration; }
    public MobEffect getEffect() {
        if (effectId == null) return null;
        return BuiltInRegistries.MOB_EFFECT.getValue(ResourceLocation.parse(effectId));
    }
    public boolean causesBlinds() { return blinds; }
}