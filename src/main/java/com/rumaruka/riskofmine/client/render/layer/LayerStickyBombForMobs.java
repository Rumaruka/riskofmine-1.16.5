package com.rumaruka.riskofmine.client.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;

public class LayerStickyBombForMobs<T extends LivingEntity,M extends EntityModel<T>>extends LayerRenderer<T,M> {
    public LayerStickyBombForMobs() {
        super((IEntityRenderer<T, M>) Minecraft.getInstance().getEntityRenderDispatcher().renderers); // nonnull, we'll just pass the player renderer

    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {


    }
}

