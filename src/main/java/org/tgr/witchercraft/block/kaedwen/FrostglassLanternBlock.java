package org.tgr.witchercraft.block.kaedwen;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Lantern variant that emits a cold cyan light plus drifting snow particles for Kaedweni streets.
 */
public class FrostglassLanternBlock extends LanternBlock {

    private static final MapCodec<FrostglassLanternBlock> CODEC = simpleCodec(FrostglassLanternBlock::new);

    public FrostglassLanternBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<? extends LanternBlock> codec() {
        return CODEC;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        if (random.nextFloat() < 0.35F) {
            double x = pos.getX() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
            double y = pos.getY() + 0.9D + random.nextDouble() * 0.2D;
            double z = pos.getZ() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
            level.addParticle(ParticleTypes.SNOWFLAKE, x, y, z, 0.0D, -0.02D, 0.0D);
        }
    }
}