package org.tgr.witchercraft.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Weapon runes provide permanent enchantments to weapons.
 * Up to 3 runes can be socketed per weapon.
 */
public class WeaponRune {
    
    public static final String NBT_KEY = "WeaponRunes";
    public static final int MAX_RUNE_SLOTS = 3;
    
    private final RuneType type;
    private final int level; // 1-3
    
    public WeaponRune(RuneType type, int level) {
        this.type = type;
        this.level = Math.max(1, Math.min(3, level));
    }
    
    public RuneType getType() {
        return type;
    }
    
    public int getLevel() {
        return level;
    }
    
    /**
     * Apply rune effects on hit
     */
    public void onHit(LivingEntity attacker, LivingEntity target, Level level) {
        type.onHit(attacker, target, this.level, level);
    }
    
    /**
     * Get rune's stat bonuses
     */
    public float getDamageBonus() {
        return type.getDamageBonus(level);
    }
    
    public float getCritChanceBonus() {
        return type.getCritChanceBonus(level);
    }
    
    public float getCritDamageBonus() {
        return type.getCritDamageBonus(level);
    }
    
    /**
     * Add rune to weapon
     */
    public static boolean addRune(ItemStack weapon, RuneType type, int level) {
        List<WeaponRune> runes = getRunes(weapon);
        
        if (runes.size() >= MAX_RUNE_SLOTS) {
            return false; // No more slots
        }
        
        runes.add(new WeaponRune(type, level));
        saveRunes(weapon, runes);
        return true;
    }
    
    /**
     * Get all runes from weapon
     * TODO: Migrate to Data Components API in MC 1.21.1
     */
    public static List<WeaponRune> getRunes(ItemStack weapon) {
        List<WeaponRune> runes = new ArrayList<>();
        // TODO: Use Data Components instead of NBT
        // List<RuneData> runeData = weapon.get(ModDataComponents.WEAPON_RUNES);
        // if (runeData != null) {
        //     for (RuneData data : runeData) {
        //         runes.add(new WeaponRune(data.type(), data.level()));
        //     }
        // }
        return runes;
    }
    
    /**
     * Save runes to weapon
     * TODO: Migrate to Data Components API in MC 1.21.1
     */
    private static void saveRunes(ItemStack weapon, List<WeaponRune> runes) {
        // TODO: Use Data Components instead of NBT
        // List<RuneData> runeData = runes.stream()
        //     .map(r -> new RuneData(r.getType(), r.getLevel()))
        //     .toList();
        // weapon.set(ModDataComponents.WEAPON_RUNES, runeData);
    }
    
    /**
     * Add rune tooltips to weapon
     */
    public static void addTooltips(ItemStack weapon, java.util.function.Consumer<Component> tooltip) {
        List<WeaponRune> runes = getRunes(weapon);
        
        if (!runes.isEmpty()) {
            tooltip.accept(Component.literal("Runes:").withStyle(ChatFormatting.LIGHT_PURPLE));
            for (WeaponRune rune : runes) {
                tooltip.accept(Component.literal("  " + rune.getType().displayName + " " + 
                    "★".repeat(rune.getLevel())).withStyle(ChatFormatting.AQUA));
                tooltip.accept(Component.literal("    " + rune.getType().getDescription(rune.getLevel()))
                    .withStyle(ChatFormatting.GRAY));
            }
        }
    }
    
    public enum RuneType {
        CHERNOBOG("Chernobog's") {
            @Override
            public float getDamageBonus(int level) { return level * 0.05f; }
            @Override
            public String getDescription(int level) { 
                return "+" + (int)(level * 5) + "% damage"; 
            }
        },
        DAZHBOG("Dazhbog's") {
            @Override
            public float getCritChanceBonus(int level) { return level * 0.03f; }
            @Override
            public String getDescription(int level) { 
                return "+" + (int)(level * 3) + "% critical chance"; 
            }
        },
        DEVANA("Devana's") {
            @Override
            public float getCritDamageBonus(int level) { return level * 0.15f; }
            @Override
            public String getDescription(int level) { 
                return "+" + (int)(level * 15) + "% critical damage"; 
            }
        },
        MORANA("Morana's") {
            @Override
            public void onHit(LivingEntity attacker, LivingEntity target, int level, Level world) {
                // Chance to poison
                if (world.random.nextFloat() < level * 0.1f) {
                    target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                        net.minecraft.world.effect.MobEffects.POISON, 60, level - 1));
                }
            }
            @Override
            public String getDescription(int level) { 
                return (int)(level * 10) + "% chance to poison"; 
            }
        },
        PERUN("Perun's") {
            @Override
            public void onHit(LivingEntity attacker, LivingEntity target, int level, Level world) {
                // Chance to deal lightning damage
                if (world.random.nextFloat() < level * 0.15f) {
                    // TODO: Fix lightning spawn in MC 1.21.1
                    // Lightning bolt entity = new LightningBolt(EntityType.LIGHTNING_BOLT, world);
                    // entity.moveTo(target.blockPosition(), 0, 0);
                    // world.addFreshEntity(entity);
                }
            }
            @Override
            public String getDescription(int level) { 
                return (int)(level * 15) + "% chance to strike with lightning"; 
            }
        },
        SVAROG("Svarog's") {
            @Override
            public void onHit(LivingEntity attacker, LivingEntity target, int level, Level world) {
                // Chance to set on fire
                if (world.random.nextFloat() < level * 0.2f) {
                    target.setRemainingFireTicks(level * 40);
                }
            }
            @Override
            public String getDescription(int level) { 
                return (int)(level * 20) + "% chance to ignite"; 
            }
        },
        VELES("Veles's") {
            @Override
            public void onHit(LivingEntity attacker, LivingEntity target, int level, Level world) {
                // Lifesteal
                float healing = target.getMaxHealth() * level * 0.02f;
                attacker.heal(Math.min(healing, 4.0f));
            }
            @Override
            public String getDescription(int level) { 
                return (int)(level * 2) + "% lifesteal"; 
            }
        },
        ZORIA("Zoria's") {
            @Override
            public float getDamageBonus(int level) { return level * 0.03f; }
            @Override
            public void onHit(LivingEntity attacker, LivingEntity target, int level, Level world) {
                // Bonus damage at night (dayTime > 13000 is night)
                if (world.getDayTime() % 24000 > 13000) {
                    target.hurt(world.damageSources().magic(), level * 2.0f);
                }
            }
            @Override
            public String getDescription(int level) { 
                return "+" + (int)(level * 3) + "% damage, +" + level * 2 + " damage at night"; 
            }
        };
        
        protected final String displayName;
        
        RuneType(String displayName) {
            this.displayName = displayName;
        }
        
        public void onHit(LivingEntity attacker, LivingEntity target, int level, Level world) {
            // Default: no on-hit effect
        }
        
        public float getDamageBonus(int level) {
            return 0.0f;
        }
        
        public float getCritChanceBonus(int level) {
            return 0.0f;
        }
        
        public float getCritDamageBonus(int level) {
            return 0.0f;
        }
        
        public abstract String getDescription(int level);
    }
}
