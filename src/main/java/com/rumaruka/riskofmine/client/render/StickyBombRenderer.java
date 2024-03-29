package com.rumaruka.riskofmine.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.common.entity.weapon.EntityStickyBomb;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import ru.timeconqueror.timecore.api.TimeMod;
import ru.timeconqueror.timecore.client.render.model.ModelConfiguration;
import ru.timeconqueror.timecore.client.render.model.TimeEntityModel;
import ru.timeconqueror.timecore.client.render.model.TimeModel;

import static com.rumaruka.riskofmine.RiskOfMine.rl;
import static com.rumaruka.riskofmine.init.ROMModels.STICKY_BOMB;

public class StickyBombRenderer extends EntityRenderer<EntityStickyBomb> {

    private final TimeModel stickyBombModel;

    public StickyBombRenderer(EntityRendererManager rendererManager) {
        super(rendererManager);
        stickyBombModel = new TimeModel(ModelConfiguration.builder(STICKY_BOMB).build());
    }

    @Override
    public void render(EntityStickyBomb stickyBomb, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        ResourceLocation texture = getTextureLocation(stickyBomb);
        this.entityRenderDispatcher.textureManager.bind(texture);
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.5F, 0.01f, 0.5F);
        matrixStackIn.scale(1.15f, 1f, 1.25f);
        stickyBombModel.renderToBuffer(matrixStackIn, bufferIn.getBuffer(stickyBombModel.renderType(texture)), packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

        matrixStackIn.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EntityStickyBomb p_110775_1_) {
        return rl("textures/items/sticky_bomb_model.png");
    }
}
