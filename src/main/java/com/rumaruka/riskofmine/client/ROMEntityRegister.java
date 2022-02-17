package com.rumaruka.riskofmine.client;

import com.rumaruka.riskofmine.client.render.HealthOrbRenderer;
import com.rumaruka.riskofmine.client.render.StickyBombRenderer;
import com.rumaruka.riskofmine.init.ROMEntitys;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ROMEntityRegister {


    public static void renderEntity() {
        RenderingRegistry.registerEntityRenderingHandler(ROMEntitys.HEALTH_ORB, HealthOrbRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ROMEntitys.STICKY_BOMB, StickyBombRenderer::new);

    }


}
