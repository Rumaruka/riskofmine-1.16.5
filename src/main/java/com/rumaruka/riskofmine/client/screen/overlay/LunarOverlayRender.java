package com.rumaruka.riskofmine.client.screen.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.common.cap.lunar.ROMLunar;
import com.rumaruka.riskofmine.common.cap.lunar.data.Lunar;
import com.rumaruka.riskofmine.common.cap.money.ROMMoney;
import com.rumaruka.riskofmine.common.cap.money.data.Money;
import net.minecraft.client.Minecraft;
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
public class LunarOverlayRender {
    public static KeyBinding keyLunar;
    private static final Minecraft mc = Minecraft.getInstance();
    @SubscribeEvent
    public static void renderOverlays(RenderGameOverlayEvent.Post event) {


            if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            if(keyLunar.isDown()){
                renderNearbyDisplay(event.getMatrixStack());

            }
        }
    }
    public static void keyPressed(FMLClientSetupEvent event){
        keyLunar=new KeyBinding("Open Lunar Overlay", GLFW.GLFW_KEY_L,"Risk of Mine");
        ClientRegistry.registerKeyBinding(keyLunar);

    }



    private static void renderNearbyDisplay(MatrixStack stack) {
        stack.pushPose();
        PlayerEntity player = Minecraft.getInstance().player;
        FontRenderer fontRenderer = Minecraft.getInstance().font;
        if (!player.isDeadOrDying()) {
            ROMLunar romLunar = ROMLunar.from(player);
            Lunar lunar = romLunar.lunar;
            //float maxRectWidth = 0;
            float startY = 20;
            float finalStartY = startY;
            String toDisplay = getLunarDisplay(lunar);
            Color color = Color.magenta;
//            mc.textureManager.bind();
            //maxRectWidth = Math.max(maxRectWidth,fontRenderer.width(toDisplay)+ 5.5F * 2);
            //float finalMaxRectWidth = maxRectWidth;
            DrawHelper.drawString(stack, fontRenderer, toDisplay, 27.5f, 30, color.getRGB());
            startY += 5 * 1.5F;

        }
        stack.popPose();
    }


    private static String getLunarDisplay(Lunar lunar) {
        float currentLunar = lunar.getCurrentLunar();
        return I18n.get("riskofmine.currentlunar.name") + currentLunar;

    }
}