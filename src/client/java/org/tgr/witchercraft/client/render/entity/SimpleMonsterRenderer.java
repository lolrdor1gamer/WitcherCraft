package org.tgr.witchercraft.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import org.tgr.witchercraft.Witchercraft;

/**
 * Simple monster renderer using zombie model as base.
 * This is a temporary solution until custom models are created.
 */
public class SimpleMonsterRenderer<T extends Mob> 
    extends MobRenderer<T, ZombieRenderState, ZombieModel<ZombieRenderState>> {

    private final ResourceLocation texture;
    private final float scale;

    public SimpleMonsterRenderer(EntityRendererProvider.Context context, ResourceLocation texture) {
        this(context, texture, 1.0f);
    }

    public SimpleMonsterRenderer(EntityRendererProvider.Context context, ResourceLocation texture, float scale) {
        super(context, new ZombieModel<>(context.bakeLayer(ModelLayers.ZOMBIE)), 0.5F);
        this.texture = texture;
        this.scale = scale;
    }

    @Override
    public ResourceLocation getTextureLocation(ZombieRenderState state) {
        return this.texture;
    }

    @Override
    public ZombieRenderState createRenderState() {
        return new ZombieRenderState();
    }

    @Override
    protected void scale(ZombieRenderState state, PoseStack poseStack) {
        poseStack.scale(scale, scale, scale);
    }
}
