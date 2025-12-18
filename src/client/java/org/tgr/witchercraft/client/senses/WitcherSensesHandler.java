package org.tgr.witchercraft.client.senses;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.tgr.witchercraft.registry.ModBlocks;
import org.tgr.witchercraft.client.ui.UIKeyBindings;
import org.tgr.witchercraft.entity.monster.AbstractWitcherMonster;

import java.util.ArrayList;
import java.util.List;

/**
 * Witcher Senses - A special vision mode that highlights important objects
 * Hold V (default) to activate
 * 
 * Features:
 * - Highlights monsters in red
 * - Highlights herbs in green
 * - Highlights items in yellow
 * - Highlights interactable blocks in blue
 * - Draws trails to nearby points of interest
 */
@Environment(EnvType.CLIENT)
public class WitcherSensesHandler {
    
    private static boolean active = false;
    private static float activationProgress = 0.0f;
    private static final float ACTIVATION_SPEED = 0.1f;
    private static final int SENSE_RANGE = 32;
    
    // Cached highlights for rendering
    private static List<HighlightedEntity> highlightedEntities = new ArrayList<>();
    private static List<HighlightedBlock> highlightedBlocks = new ArrayList<>();
    private static long lastScanTime = 0;
    private static final long SCAN_INTERVAL = 200; // ms between scans
    
    public static void tick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) {
            return;
        }
        
        // Check if key is held (not just pressed)
        boolean keyHeld = UIKeyBindings.WitcherSensesKey.isDown();
        
        // Update activation state
        if (keyHeld && !active) {
            active = true;
        } else if (!keyHeld && active) {
            active = false;
        }
        
        // Smooth activation/deactivation
        if (active) {
            activationProgress = Math.min(1.0f, activationProgress + ACTIVATION_SPEED);
        } else {
            activationProgress = Math.max(0.0f, activationProgress - ACTIVATION_SPEED);
        }
        
        // Scan for highlightable objects periodically
        if (active && System.currentTimeMillis() - lastScanTime > SCAN_INTERVAL) {
            scanForHighlights(mc);
            lastScanTime = System.currentTimeMillis();
        }
        
        // Clear highlights when deactivated
        if (!active && activationProgress <= 0) {
            highlightedEntities.clear();
            highlightedBlocks.clear();
        }
    }
    
    private static void scanForHighlights(Minecraft mc) {
        LocalPlayer player = mc.player;
        if (player == null || mc.level == null) return;
        
        highlightedEntities.clear();
        highlightedBlocks.clear();
        
        Vec3 playerPos = player.position();
        
        // Scan for entities
        AABB searchBox = new AABB(player.blockPosition()).inflate(SENSE_RANGE);
        for (Entity entity : mc.level.getEntities(player, searchBox)) {
            if (entity instanceof AbstractWitcherMonster) {
                // Witcher monsters - red highlight
                highlightedEntities.add(new HighlightedEntity(entity, HighlightType.MONSTER));
            } else if (entity instanceof Monster) {
                // Regular monsters - orange highlight
                highlightedEntities.add(new HighlightedEntity(entity, HighlightType.HOSTILE));
            } else if (entity instanceof ItemEntity) {
                // Items - yellow highlight
                highlightedEntities.add(new HighlightedEntity(entity, HighlightType.ITEM));
            } else if (entity instanceof LivingEntity && !(entity.equals(player))) {
                // Other living entities - white highlight
                highlightedEntities.add(new HighlightedEntity(entity, HighlightType.NEUTRAL));
            }
        }
        
        // Scan for interesting blocks
        BlockPos playerBlockPos = player.blockPosition();
        for (int x = -SENSE_RANGE; x <= SENSE_RANGE; x++) {
            for (int y = -SENSE_RANGE / 2; y <= SENSE_RANGE / 2; y++) {
                for (int z = -SENSE_RANGE; z <= SENSE_RANGE; z++) {
                    BlockPos checkPos = playerBlockPos.offset(x, y, z);
                    BlockState state = mc.level.getBlockState(checkPos);
                    Block block = state.getBlock();
                    
                    // Check for herb blocks
                    if (isHerbBlock(block)) {
                        highlightedBlocks.add(new HighlightedBlock(checkPos, HighlightType.HERB));
                    }
                    // Check for alchemy/crafting blocks
                    else if (isAlchemyBlock(block)) {
                        highlightedBlocks.add(new HighlightedBlock(checkPos, HighlightType.INTERACTABLE));
                    }
                    // Check for monster nests
                    else if (isMonsterNest(block)) {
                        highlightedBlocks.add(new HighlightedBlock(checkPos, HighlightType.MONSTER_NEST));
                    }
                }
            }
        }
    }
    
    private static boolean isHerbBlock(Block block) {
        // Check if it's one of our herb blocks
        return block == ModBlocks.CROWS_EYE_BLOCK ||
               block == ModBlocks.BLOWBALL_BLOCK ||
               block == ModBlocks.WHITE_MYRTLE_BLOCK ||
               block == ModBlocks.WOLFSBANE_BLOCK ||
               block == ModBlocks.MANDRAKE_ROOT_BLOCK ||
               block == ModBlocks.SEWANT_MUSHROOM_BLOCK ||
               block == ModBlocks.VERBENA_BLOCK ||
               block == ModBlocks.FOOLS_PARSLEY_BLOCK ||
               block == ModBlocks.BERBERCANE_FRUIT_BLOCK ||
               block == ModBlocks.WORMWOOD_BLOCK ||
               block == ModBlocks.HAN_BLOCK;
    }
    
    private static boolean isAlchemyBlock(Block block) {
        return block == ModBlocks.ALCHEMY_TABLE ||
               block == ModBlocks.GRINDSTONE_WITCHER ||
               block == ModBlocks.WITCHER_FORGE;
    }
    
    private static boolean isMonsterNest(Block block) {
        return block == ModBlocks.NEKKER_NEST ||
               block == ModBlocks.DROWNED_NEST ||
               block == ModBlocks.GHOUL_NEST ||
               block == ModBlocks.WRAITH_CURSED_STONE;
    }
    
    public static boolean isActive() {
        return active || activationProgress > 0;
    }
    
    public static float getActivationProgress() {
        return activationProgress;
    }
    
    public static List<HighlightedEntity> getHighlightedEntities() {
        return highlightedEntities;
    }
    
    public static List<HighlightedBlock> getHighlightedBlocks() {
        return highlightedBlocks;
    }
    
    // Highlight types with associated colors
    public enum HighlightType {
        MONSTER(1.0f, 0.2f, 0.2f),      // Red - Witcher monsters
        HOSTILE(1.0f, 0.5f, 0.2f),       // Orange - Other hostile
        ITEM(1.0f, 1.0f, 0.3f),          // Yellow - Items
        NEUTRAL(0.8f, 0.8f, 0.8f),       // White - Neutral mobs
        HERB(0.2f, 1.0f, 0.3f),          // Green - Herbs
        INTERACTABLE(0.3f, 0.5f, 1.0f),  // Blue - Interactable blocks
        MONSTER_NEST(0.8f, 0.2f, 0.8f);  // Purple - Monster nests
        
        public final float r, g, b;
        
        HighlightType(float r, float g, float b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }
    }
    
    public record HighlightedEntity(Entity entity, HighlightType type) {}
    public record HighlightedBlock(BlockPos pos, HighlightType type) {}
}
