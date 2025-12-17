package org.tgr.witchercraft.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Witcher armor sets with set bonuses
 */
public class ArmorSet {
    
    private final String id;
    private final String name;
    private final ArmorWeight weight;
    private final List<SetBonusEntry> bonuses;
    
    public ArmorSet(String id, String name, ArmorWeight weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.bonuses = new ArrayList<>();
    }
    
    public ArmorSet addBonus(int piecesRequired, SetBonus bonus) {
        bonuses.add(new SetBonusEntry(piecesRequired, bonus));
        return this;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public ArmorWeight getWeight() {
        return weight;
    }
    
    /**
     * Get active bonuses based on equipped pieces
     */
    public List<SetBonus> getActiveBonuses(Player player) {
        int equippedPieces = countEquippedPieces(player);
        List<SetBonus> active = new ArrayList<>();
        
        for (SetBonusEntry entry : bonuses) {
            if (equippedPieces >= entry.piecesRequired) {
                active.add(entry.bonus);
            }
        }
        
        return active;
    }
    
    /**
     * Count how many pieces of this set the player is wearing
     */
    public int countEquippedPieces(Player player) {
        int count = 0;
        
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
                ItemStack stack = player.getItemBySlot(slot);
                if (stack.getItem() instanceof WitcherArmorItem armor) {
                    if (armor.getSet() != null && armor.getSet().getId().equals(this.id)) {
                        count++;
                    }
                }
            }
        }
        
        return count;
    }
    
    private record SetBonusEntry(int piecesRequired, SetBonus bonus) {}
    
    public enum ArmorWeight {
        LIGHT("Light", 0.0f, 0.15f, 0.10f),
        MEDIUM("Medium", 0.05f, 0.10f, 0.05f),
        HEAVY("Heavy", 0.10f, 0.0f, -0.05f);
        
        private final String displayName;
        private final float damageReduction;
        private final float staminaBonus;
        private final float speedModifier;
        
        ArmorWeight(String displayName, float damageReduction, float staminaBonus, float speedModifier) {
            this.displayName = displayName;
            this.damageReduction = damageReduction;
            this.staminaBonus = staminaBonus;
            this.speedModifier = speedModifier;
        }
        
        public String getDisplayName() { return displayName; }
        public float getDamageReduction() { return damageReduction; }
        public float getStaminaBonus() { return staminaBonus; }
        public float getSpeedModifier() { return speedModifier; }
    }
    
    /**
     * Set bonus effects
     */
    public interface SetBonus {
        String getDescription();
        void apply(Player player);
        void remove(Player player);
    }
    
    // Predefined armor sets
    public static final ArmorSet CAT_SCHOOL = new ArmorSet("cat", "Cat School", ArmorWeight.LIGHT)
        .addBonus(2, new SetBonus() {
            @Override
            public String getDescription() { return "+10% Attack Speed"; }
            @Override
            public void apply(Player player) {
                // Applied through attribute calculation
            }
            @Override
            public void remove(Player player) {}
        })
        .addBonus(4, new SetBonus() {
            @Override
            public String getDescription() { return "+15% Critical Hit Damage"; }
            @Override
            public void apply(Player player) {}
            @Override
            public void remove(Player player) {}
        })
        .addBonus(6, new SetBonus() {
            @Override
            public String getDescription() { return "+25% Critical Hit Chance"; }
            @Override
            public void apply(Player player) {}
            @Override
            public void remove(Player player) {}
        });
    
    public static final ArmorSet BEAR_SCHOOL = new ArmorSet("bear", "Bear School", ArmorWeight.HEAVY)
        .addBonus(2, new SetBonus() {
            @Override
            public String getDescription() { return "+20% Maximum Health"; }
            @Override
            public void apply(Player player) {
                // Applied through attribute calculation
            }
            @Override
            public void remove(Player player) {}
        })
        .addBonus(4, new SetBonus() {
            @Override
            public String getDescription() { return "+25% Damage Resistance"; }
            @Override
            public void apply(Player player) {}
            @Override
            public void remove(Player player) {}
        })
        .addBonus(6, new SetBonus() {
            @Override
            public String getDescription() { return "20% Chance to Stun Attackers"; }
            @Override
            public void apply(Player player) {}
            @Override
            public void remove(Player player) {}
        });
    
    public static final ArmorSet GRIFFIN_SCHOOL = new ArmorSet("griffin", "Griffin School", ArmorWeight.MEDIUM)
        .addBonus(2, new SetBonus() {
            @Override
            public String getDescription() { return "+25% Sign Intensity"; }
            @Override
            public void apply(Player player) {}
            @Override
            public void remove(Player player) {}
        })
        .addBonus(4, new SetBonus() {
            @Override
            public String getDescription() { return "+50% Mana Regeneration"; }
            @Override
            public void apply(Player player) {}
            @Override
            public void remove(Player player) {}
        })
        .addBonus(6, new SetBonus() {
            @Override
            public String getDescription() { return "Signs cost 25% less Stamina"; }
            @Override
            public void apply(Player player) {}
            @Override
            public void remove(Player player) {}
        });
    
    public static final ArmorSet WOLF_SCHOOL = new ArmorSet("wolf", "Wolf School", ArmorWeight.MEDIUM)
        .addBonus(2, new SetBonus() {
            @Override
            public String getDescription() { return "+10% Attack Damage"; }
            @Override
            public void apply(Player player) {}
            @Override
            public void remove(Player player) {}
        })
        .addBonus(4, new SetBonus() {
            @Override
            public String getDescription() { return "+10% Sign Intensity"; }
            @Override
            public void apply(Player player) {}
            @Override
            public void remove(Player player) {}
        })
        .addBonus(6, new SetBonus() {
            @Override
            public String getDescription() { return "Balanced Fighter: All stats +5%"; }
            @Override
            public void apply(Player player) {}
            @Override
            public void remove(Player player) {}
        });
    
    public static final ArmorSet VIPER_SCHOOL = new ArmorSet("viper", "Viper School", ArmorWeight.LIGHT)
        .addBonus(2, new SetBonus() {
            @Override
            public String getDescription() { return "Attacks have 15% chance to poison"; }
            @Override
            public void apply(Player player) {}
            @Override
            public void remove(Player player) {}
        })
        .addBonus(4, new SetBonus() {
            @Override
            public String getDescription() { return "+50% Poison Duration"; }
            @Override
            public void apply(Player player) {}
            @Override
            public void remove(Player player) {}
        })
        .addBonus(6, new SetBonus() {
            @Override
            public String getDescription() { return "Immune to Poison"; }
            @Override
            public void apply(Player player) {}
            @Override
            public void remove(Player player) {}
        });
    
    public static final ArmorSet MANTICORE_SCHOOL = new ArmorSet("manticore", "Manticore School", ArmorWeight.LIGHT)
        .addBonus(2, new SetBonus() {
            @Override
            public String getDescription() { return "+25% Alchemy Duration"; }
            @Override
            public void apply(Player player) {}
            @Override
            public void remove(Player player) {}
        })
        .addBonus(4, new SetBonus() {
            @Override
            public String getDescription() { return "+40% Toxicity Tolerance"; }
            @Override
            public void apply(Player player) {}
            @Override
            public void remove(Player player) {}
        })
        .addBonus(6, new SetBonus() {
            @Override
            public String getDescription() { return "Critical hits restore 5% toxicity as health"; }
            @Override
            public void apply(Player player) {}
            @Override
            public void remove(Player player) {}
        });
}
