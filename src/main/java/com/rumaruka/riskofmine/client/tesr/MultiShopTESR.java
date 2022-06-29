
package com.rumaruka.riskofmine.client.tesr;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rumaruka.riskofmine.common.tiles.MultiShopTE;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fml.common.Mod;
import ru.timeconqueror.timecore.client.render.model.ModelConfiguration;
import ru.timeconqueror.timecore.client.render.model.TimeModel;

import static com.rumaruka.riskofmine.RiskOfMine.rl;
import static com.rumaruka.riskofmine.init.ROMModels.MULTI_SHOP_OPEN;

public class MultiShopTESR extends TileEntityRenderer<MultiShopTE> {
    public TimeModel modelOpen;
//    public TimeModel modelClose = TimeModelLoader.loadJsonModel(rl("models/tile/multi_shop_close.json"), RenderType::entityCutout);

    public MultiShopTESR(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
        modelOpen = new TimeModel(ModelConfiguration.builder(MULTI_SHOP_OPEN).build());
    }

    @Override
    public void render(MultiShopTE multiShopTE, float ticks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, int overlay) {
        ResourceLocation textureOpen = getMultiShopOpenTexture(multiShopTE);
//        ResourceLocation textureClose = getMultiShopCloseTexture(multiShopTE);
        this.renderer.textureManager.bind(textureOpen);
        matrixStack.pushPose();
        matrixStack.translate(0.5F, 0.01f, 0.5F);
        matrixStack.scale(1.25f, 2f, 1.25f);
        modelOpen.renderToBuffer(matrixStack, buffer.getBuffer(modelOpen.renderType(textureOpen)), light, overlay, 1, 1, 1, 1);
        matrixStack.popPose();


    }

    private ResourceLocation getMultiShopOpenTexture(MultiShopTE tileEntityIn) {
        return rl("textures/tile/multi_shop_open.png");
    }

//    private ResourceLocation getMultiShopCloseTexture(MultiShopTE tileEntityIn) {
//        return rl("textures/tile/multi_shop_close.png");
//    }

}
