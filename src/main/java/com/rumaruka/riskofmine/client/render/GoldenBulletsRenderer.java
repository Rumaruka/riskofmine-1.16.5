package com.rumaruka.riskofmine.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rumaruka.riskofmine.common.entity.weapon.EntityStickyBomb;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

import static com.rumaruka.riskofmine.RiskOfMine.rl;

public class GoldenBulletsRenderer extends EntityRenderer<EntityStickyBomb> {

    // private final TimeModel goldenBulletsModel;

    public GoldenBulletsRenderer(EntityRendererManager rendererManager) {
        super(rendererManager);
        // goldenBulletsModel = new TimeModel(ModelConfiguration.builder(GOLDEN_BULLETS).build());
    }

    @Override
    public void render(EntityStickyBomb stickyBomb, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        ResourceLocation texture = getTextureLocation(stickyBomb);
        this.entityRenderDispatcher.textureManager.bind(texture);
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.5F, 0.01f, 0.5F);
        matrixStackIn.scale(1.15f, 1f, 1.25f);
        //  goldenBulletsModel.renderToBuffer(matrixStackIn, bufferIn.getBuffer(goldenBulletsModel.renderType(texture)), packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

        matrixStackIn.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EntityStickyBomb p_110775_1_) {
        return rl("textures/items/.png");
    }
}
