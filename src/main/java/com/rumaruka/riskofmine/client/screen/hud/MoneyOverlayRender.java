package com.rumaruka.riskofmine.client.screen.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.common.cap.ROMMoney;
import com.rumaruka.riskofmine.common.cap.data.Money;
import com.rumaruka.riskofmine.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import static com.rumaruka.riskofmine.RiskOfMine.tl;


@Mod.EventBusSubscriber(modid = RiskOfMine.MODID, value = Dist.CLIENT)
public class MoneyOverlayRender {
    private static float transparency;
    private static float burnoutTransparency;

    @SubscribeEvent
    public static void renderMoneyBar(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            Minecraft minecraft = Minecraft.getInstance();
            PlayerEntity player = minecraft.player;
            assert player != null;
            if (!player.isCreative()) {
                MatrixStack matrixStack = event.getMatrixStack();
                ROMMoney romMoney = ROMMoney.from(player);
                if (romMoney != null) {
                    float currentMoney = romMoney.money.getCurrentMoney();
                    float maxMoney = Money.getMaxMoney(player);
                    float cooldown = romMoney.money.getBurnout();

                    boolean isMoneyUsed = currentMoney < maxMoney;
                    if (isMoneyUsed && transparency <= 1.0) {
                        transparency += .02;
                    } else if (transparency > 0) {
                        transparency -= .02;
                    }

                    boolean cooldownActive = cooldown > 1.0F;

                    if (cooldownActive && burnoutTransparency < 1) {
                        burnoutTransparency += .02;
                    } else if (burnoutTransparency > 0) {
                        burnoutTransparency -= .02;
                    }

                    if (!minecraft.options.hideGui && transparency > 0) {
                        int l = event.getWindow().getGuiScaledHeight() - 32 + 3;
                        int w = event.getWindow().getGuiScaledWidth() / 2 - 91;

                        RenderSystem.color4f(1.0F, 1.0F, 1.0F, transparency);
                        minecraft.getTextureManager().bind(tl("gui/essence.png").fullLocation());
                        RenderUtils.blit(matrixStack, w, l, 0, 5, 81, 5, 81, 15);

                        if (cooldownActive) {
                            RenderSystem.color4f(1.0F, 1.0F, 1.0F, (float) -Math.sin((float) player.tickCount / 5) * burnoutTransparency / 2);
                            RenderUtils.blit(matrixStack, w, l, 0, 0, 81, 5, 81, 15);
                        } else {
                            int i = (int) ((currentMoney / maxMoney) * 81);
                            RenderUtils.blit(matrixStack, w, l, 0, 0, i, 5, 81, 15);
                        }


                        if (burnoutTransparency > 0) {
                            RenderSystem.color4f(1.0F, 1.0F, 1.0F, burnoutTransparency);
                            RenderUtils.blit(matrixStack, w, l, 0, 10, 81, 5, 81, 15);
                        }
                    }
                }
            }
        }

    }
}