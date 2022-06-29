package com.rumaruka.riskofmine.client.screen.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.common.cap.barrier.ROMBarrier;
import com.rumaruka.riskofmine.common.cap.barrier.data.Barrier;
import com.rumaruka.riskofmine.common.cap.lunar.ROMLunar;
import com.rumaruka.riskofmine.common.cap.lunar.data.Lunar;
import com.rumaruka.riskofmine.common.cap.money.ROMMoney;
import com.rumaruka.riskofmine.common.cap.money.data.Money;
import com.rumaruka.riskofmine.common.cap.shields.ROMShields;
import com.rumaruka.riskofmine.common.cap.shields.data.Shields;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;
import ru.timeconqueror.timecore.api.util.client.DrawHelper;

import java.awt.*;

@Mod.EventBusSubscriber(modid = RiskOfMine.MODID, value = Dist.CLIENT)
public class ROMOverlayRender {
    public static KeyBinding keyShowOverlay;
    private static final Minecraft mc = Minecraft.getInstance();
    @SubscribeEvent
    public static void renderOverlays(RenderGameOverlayEvent.Post event) {


        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {

            if(keyShowOverlay.isDown()){
                renderNearbyMoneyDisplay(event.getMatrixStack());
                renderNearbyLunarDisplay(event.getMatrixStack());


            }else {
                renderNearbyShieldsDisplay(event.getMatrixStack());
                renderNearbyBarrierDisplay(event.getMatrixStack());
            }
        }
    }
    public static void keyPressed(FMLClientSetupEvent event){
        keyShowOverlay=new KeyBinding("Show Overlay", GLFW.GLFW_KEY_M,"Risk of Mine");
        ClientRegistry.registerKeyBinding(keyShowOverlay);

    }



    private static void renderNearbyMoneyDisplay(MatrixStack stack) {
        stack.pushPose();
        PlayerEntity player = Minecraft.getInstance().player;
        FontRenderer fontRenderer = Minecraft.getInstance().font;
        if (!player.isDeadOrDying()) {
            ROMMoney romMoney = ROMMoney.from(player);
            Money money = romMoney.money;
            String toDisplay = getMoneyDisplay(money);
            Color color = Color.magenta;
//            mc.textureManager.bind();

            DrawHelper.drawString(stack, fontRenderer, toDisplay, 27.5f, 20, color.getRGB());


        }
        stack.popPose();
    }
    private static void renderNearbyLunarDisplay(MatrixStack stack) {
        stack.pushPose();
        PlayerEntity player = Minecraft.getInstance().player;
        FontRenderer fontRenderer = Minecraft.getInstance().font;
        if (!player.isDeadOrDying()) {
            ROMLunar romLunar = ROMLunar.from(player);
            Lunar lunar = romLunar.lunar;
            String toDisplay = getLunarDisplay(lunar);
            Color color = Color.magenta;
//            mc.textureManager.bind();
//            AbstractGui.blit(stack,1,1,27.5f,32,1,1,1,1);
            DrawHelper.drawString(stack, fontRenderer, toDisplay, 27.5f, 30, color.getRGB());


        }
        stack.popPose();
    }
    private static void renderNearbyShieldsDisplay(MatrixStack stack) {
        stack.pushPose();
        PlayerEntity player = Minecraft.getInstance().player;
        FontRenderer fontRenderer = Minecraft.getInstance().font;
        if (!player.isDeadOrDying()) {
            ROMShields romShields = ROMShields.from(player);
            Shields shields = romShields.shields;
            String toDisplay = getShieldsDisplay(shields);
            Color color = Color.RED;
//            mc.textureManager.bind();

            DrawHelper.drawString(stack, fontRenderer, toDisplay, 27.5f, 20, color.getRGB());


        }
        stack.popPose();
    }

    private static void renderNearbyBarrierDisplay(MatrixStack stack) {
        stack.pushPose();
        PlayerEntity player = Minecraft.getInstance().player;
        FontRenderer fontRenderer = Minecraft.getInstance().font;
        if (!player.isDeadOrDying()) {
            ROMBarrier romBarrier = ROMBarrier.from(player);
            Barrier barrier = romBarrier.barrier;
            String toDisplay = getBarrierDisplay(barrier);
            Color color = Color.BLUE;
//            mc.textureManager.bind();

            DrawHelper.drawString(stack, fontRenderer, toDisplay, 27.5f, 30, color.getRGB());


        }
        stack.popPose();
    }

    private static String getShieldsDisplay(Shields shields) {
        float currentShields = shields.getCurrentShields();
        return I18n.get("riskofmine.currentshields.name") + currentShields;
    }
    private static String getBarrierDisplay(Barrier barrier) {
        float currentBarrier = barrier.getCurrentBarrier();
        return I18n.get("riskofmine.currentbarrier.name") + currentBarrier;
    }

    private static String getLunarDisplay(Lunar lunar) {
        float currentLunar = lunar.getCurrentLunar();
        return I18n.get("riskofmine.currentlunar.name") + currentLunar;

    }

    private static String getMoneyDisplay(Money money) {
        float currentMoney = money.getCurrentMoney();
        return I18n.get("riskofmine.currentmoney.name") + currentMoney;

    }
}
