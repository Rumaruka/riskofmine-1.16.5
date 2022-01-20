package com.rumaruka.riskofmine.events;

import com.rumaruka.riskofmine.client.render.layer.LayerStickyBombForMobs;
import com.rumaruka.riskofmine.init.ROMItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextProperties;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber
public class CommonEvent {




    public void addLayer(){
        LayerStickyBombForMobs layerStickyBombForMobs = new LayerStickyBombForMobs();
        HashSet<LivingRenderer> addedRenderers = new HashSet<>();

        EntityRendererManager renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
        Map<String, PlayerRenderer> skinMap = renderManager.getSkinMap();
        for(Map.Entry<String, PlayerRenderer> e : skinMap.entrySet())
        {
            e.getValue().addLayer(layerStickyBombForMobs);
            addedRenderers.add(e.getValue());
        }
        renderManager.renderers.forEach((entityType, entityRenderer) -> {
            if(addedRenderers.contains(entityRenderer))
            {
                return;
            }

            if(entityRenderer instanceof LivingRenderer)
            {
                LivingRenderer renderer = (LivingRenderer)entityRenderer;
                renderer.addLayer(layerStickyBombForMobs);
            }

        });
    }


}
