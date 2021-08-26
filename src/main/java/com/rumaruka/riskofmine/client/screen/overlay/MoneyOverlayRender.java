package com.rumaruka.riskofmine.client.screen.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.common.cap.money.ROMMoney;
import com.rumaruka.riskofmine.common.cap.money.data.Money;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.timeconqueror.timecore.api.util.client.DrawHelper;

import java.awt.*;


@Mod.EventBusSubscriber(modid = RiskOfMine.MODID, value = Dist.CLIENT)
public class MoneyOverlayRender {


    @SubscribeEvent
    public static void renderOverlays(RenderGameOverlayEvent.Post event) {

        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            renderNearbyDisplay(event.getMatrixStack());
        }
    }

    private static void renderNearbyDisplay(MatrixStack stack) {
        stack.pushPose();
        PlayerEntity player = Minecraft.getInstance().player;
        FontRenderer fontRenderer = Minecraft.getInstance().font;
        if (!player.isDeadOrDying()) {
            ROMMoney romMoney = ROMMoney.from(player);
            Money money = romMoney.money;
            //float maxRectWidth = 0;
            float startY = 20;
            float finalStartY = startY;
            String toDisplay = getMoneyDisplay(money);
            Color color = Color.magenta;
            //maxRectWidth = Math.max(maxRectWidth,fontRenderer.width(toDisplay)+ 5.5F * 2);
            //float finalMaxRectWidth = maxRectWidth;
            DrawHelper.drawString(stack, fontRenderer, toDisplay, 27.5f, finalStartY, color.getRGB());
            startY += 7 * 1.5F;

        }
        stack.popPose();
    }


    private static String getMoneyDisplay(Money money) {
        float currentMoney = money.getCurrentMoney();
        return I18n.get("riskofmine.currentmoney.name") + currentMoney;

    }
}