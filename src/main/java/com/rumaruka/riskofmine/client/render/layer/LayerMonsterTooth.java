package com.rumaruka.riskofmine.client.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.rumaruka.riskofmine.init.ROMItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerMonsterTooth extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {


    public  LayerMonsterTooth(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> p_i50926_1_) {
        super(p_i50926_1_);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int p_225628_3_, AbstractClientPlayerEntity playerEntity, float swing, float swingAmount, float pTicks, float aTicks, float yaw, float scale) {
        for (int i = 0; i < playerEntity.inventory.getContainerSize(); i++) {
            ItemStack itemStack = playerEntity.inventory.getItem(i);
            if(itemStack.getItem()== ROMItems.MONSTER_TOOTH){
                matrixStack.pushPose();
                matrixStack.translate(0.0F, 0.0F, 0.125F);
                double d0 = MathHelper.lerp((double)pTicks, playerEntity.xCloakO, playerEntity.xCloak) - MathHelper.lerp((double)pTicks, playerEntity.xo, playerEntity.getX());
                double d1 = MathHelper.lerp((double)pTicks, playerEntity.yCloakO, playerEntity.yCloak) - MathHelper.lerp((double)pTicks, playerEntity.yo, playerEntity.getY());
                double d2 = MathHelper.lerp((double)pTicks, playerEntity.zCloakO, playerEntity.zCloak) - MathHelper.lerp((double)pTicks, playerEntity.zo, playerEntity.getZ());
                float f = playerEntity.yBodyRotO + (playerEntity.yBodyRot - playerEntity.yBodyRotO);
                double d3 = (double)MathHelper.sin(f * ((float)Math.PI / 180F));
                double d4 = (double)(-MathHelper.cos(f * ((float)Math.PI / 180F)));
                float f1 = (float)d1 * 10.0F;
                f1 = MathHelper.clamp(f1, -.0F, 32.0F);
                float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
                f2 = MathHelper.clamp(f2, 0.0F, 150.0F);
                float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
                f3 = MathHelper.clamp(f3, 20.0F, 20.0F);
                if (f2 < 0.0F) {
                    f2 = 0.0F;
                }

                float f4 = MathHelper.lerp(pTicks, playerEntity.oBob, playerEntity.bob);
                f1 = f1 + MathHelper.sin(MathHelper.lerp(pTicks, playerEntity.walkDistO, playerEntity.walkDist) * 6.0F) * 32.0F * f4;
                if (playerEntity.isCrouching()) {
                    f1 += 25.0F;
                }

                matrixStack.mulPose(Vector3f.XP.rotationDegrees(6.0F + f2 / 2.0F + f1));
                matrixStack.mulPose(Vector3f.ZP.rotationDegrees(f3 / 2.0F));
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - f3 / 2.0F));
                Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemCameraTransforms.TransformType.FIXED, p_225628_3_, OverlayTexture.NO_OVERLAY, matrixStack, buffer);
                matrixStack.popPose();

            }
        }
    }
}
