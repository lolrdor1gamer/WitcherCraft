package org.tgr.witchercraft.block.kaedwen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Slightly mystical ice block used in the Kaedweni frozen well centerpiece. It applies a brief
 * slowness effect when entities stand on it to reinforce the "frozen" fantasy. Later we can extend
 * this with particles or ambient sounds.
 */
public class FrozenWellCoreBlock extends Block {

    public FrozenWellCoreBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);
        if (!level.isClientSide() && entity.isAlive() && entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 40, 0, true, true));
        }
    }
}