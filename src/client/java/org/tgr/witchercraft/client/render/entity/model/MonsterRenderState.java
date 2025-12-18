package org.tgr.witchercraft.client.render.entity.model;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

/**
 * Render state for Witcher monsters.
 */
public class MonsterRenderState extends LivingEntityRenderState {
    
    // Can add additional render state fields here
    public boolean isRaging = false;
    public float phaseAlpha = 1.0f;
    
}
