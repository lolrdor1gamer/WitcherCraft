package org.tgr.witchercraft.client.render.entity;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.PathfinderMob;

public class SimpleHumanoidRenderer<T extends PathfinderMob>
    extends HumanoidMobRenderer<T, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {

    private final ResourceLocation texture;

    public SimpleHumanoidRenderer(EntityRendererProvider.Context context, ResourceLocation texture) {
        super(context, createModel(context), createModel(context), 0.5F);
        this.texture = texture;
        ArmorModelSet<HumanoidModel<HumanoidRenderState>> armorModels = ArmorModelSet.bake(
            ModelLayers.PLAYER_ARMOR,
            context.getModelSet(),
            modelPart -> new HumanoidModel<>(modelPart)
        );
        this.addLayer(new HumanoidArmorLayer<>(this, armorModels, context.getEquipmentRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(HumanoidRenderState state) {
        return this.texture;
    }

    @Override
    public HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }

    private static HumanoidModel<HumanoidRenderState> createModel(EntityRendererProvider.Context context) {
        return new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER));
    }
}
