package org.tgr.witchercraft.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.tgr.witchercraft.player.WitcherPlayerData;

/**
 * Place of Power block - grants a skill point when interacted with
 * Each Place of Power can only be used once per player
 * They are associated with a specific Sign and provide a bonus to that Sign for a period
 */
public class PlaceOfPowerBlock extends Block {
    
    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");
    
    private final SignType signType;
    
    public PlaceOfPowerBlock(BlockBehaviour.Properties properties, SignType signType) {
        super(properties.lightLevel(state -> state.getValue(ACTIVATED) ? 10 : 5));
        this.signType = signType;
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVATED, false));
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }
    
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        
        if (state.getValue(ACTIVATED)) {
            // Already activated - show message
            player.displayClientMessage(Component.literal("This Place of Power has already been drained."), true);
            return InteractionResult.CONSUME;
        }
        
        // Grant skill point using static method
        WitcherPlayerData.addSkillPoints(player, 1);
        
        // Activate the block
        level.setBlock(pos, state.setValue(ACTIVATED, true), 3);
        
        // Play effects
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.ENCHANT,
                    pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5,
                    50, 0.5, 1.0, 0.5, 0.1);
            
            serverLevel.sendParticles(signType.getParticle(),
                    pos.getX() + 0.5, pos.getY() + 2.0, pos.getZ() + 0.5,
                    30, 0.3, 0.5, 0.3, 0.05);
        }
        
        level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
        level.playSound(null, pos, SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0f, 1.5f);
        
        // Show message
        player.displayClientMessage(Component.literal("§6Place of Power - " + signType.getDisplayName()), false);
        player.displayClientMessage(Component.literal("§aYou gained 1 ability point!"), true);
        
        return InteractionResult.SUCCESS;
    }
    
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        // Emit particles based on state and sign type
        if (random.nextInt(3) == 0) {
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.5;
            double y = pos.getY() + 1.0 + random.nextDouble() * 0.5;
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.5;
            
            if (state.getValue(ACTIVATED)) {
                // Dim particles when drained
                level.addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0.02, 0);
            } else {
                // Active particles based on sign
                level.addParticle(signType.getParticle(), x, y, z, 0, 0.05, 0);
                
                // Occasional enchant particles rising up
                if (random.nextInt(5) == 0) {
                    level.addParticle(ParticleTypes.ENCHANT, x, y, z,
                            (random.nextDouble() - 0.5) * 0.2, 0.1, (random.nextDouble() - 0.5) * 0.2);
                }
            }
        }
        
        // Ambient magical sound
        if (random.nextInt(100) == 0 && !state.getValue(ACTIVATED)) {
            level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(),
                    SoundEvents.BEACON_AMBIENT, SoundSource.BLOCKS,
                    0.3f, random.nextFloat() * 0.4f + 0.8f, false);
        }
    }
    
    public SignType getSignType() {
        return signType;
    }
    
    /**
     * Enum representing the types of signs for Places of Power
     */
    public enum SignType {
        IGNI("Igni", ParticleTypes.FLAME),
        AARD("Aard", ParticleTypes.CLOUD),
        QUEN("Quen", ParticleTypes.END_ROD),
        AXII("Axii", ParticleTypes.ENCHANT),
        YRDEN("Yrden", ParticleTypes.PORTAL);
        
        private final String displayName;
        private final ParticleOptions particle;
        
        SignType(String displayName, ParticleOptions particle) {
            this.displayName = displayName;
            this.particle = particle;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public ParticleOptions getParticle() {
            return particle;
        }
    }
}
