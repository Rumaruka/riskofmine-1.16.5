package com.rumaruka.riskofmine.client.tesr;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rumaruka.riskofmine.common.blocks.SmallChestBlock;
import com.rumaruka.riskofmine.common.tiles.CommonChestTE;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import ru.timeconqueror.timecore.api.client.render.model.TimeModelLoader;
import ru.timeconqueror.timecore.api.util.client.DrawHelper;
import ru.timeconqueror.timecore.client.render.model.TimeModel;

import java.awt.*;

import static com.rumaruka.riskofmine.RiskOfMine.rl;

public class CommonChestTESR extends TileEntityRenderer<CommonChestTE> {
    public TimeModel model = TimeModelLoader.loadJsonModel(rl("models/tile/small_chest.json"), RenderType::entityCutout);

    public CommonChestTESR(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(CommonChestTE tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ResourceLocation texture = getTexture(tileEntityIn);
        this.renderer.textureManager.bind(texture);
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.5F, 0.01f, 0.5F);
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(180));
        matrixStackIn.scale(1.15f, 1f, 1.25f);
        model.renderToBuffer(matrixStackIn, bufferIn.getBuffer(model.renderType(texture)), combinedLightIn, combinedOverlayIn, 1, 1, 1, 1);
        matrixStackIn.popPose();
    }



    private ResourceLocation getTexture(CommonChestTE tileEntityIn) {
        return rl("textures/tile/small_chest.png");
    }

}
