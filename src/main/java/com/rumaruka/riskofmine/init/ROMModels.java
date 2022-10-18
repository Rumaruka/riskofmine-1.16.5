package com.rumaruka.riskofmine.init;


import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.client.render.StickyBombRenderer;
import com.rumaruka.riskofmine.common.entity.weapon.EntityStickyBomb;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import ru.timeconqueror.timecore.api.registry.TimeModelRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;
import ru.timeconqueror.timecore.client.render.model.TimeEntityModel;
import ru.timeconqueror.timecore.client.render.model.TimeModel;
import ru.timeconqueror.timecore.client.render.model.TimeModelLocation;

import static com.rumaruka.riskofmine.RiskOfMine.MODID;
import static com.rumaruka.riskofmine.RiskOfMine.rl;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ROMModels {

    @AutoRegistrable
    private static final TimeModelRegister REGISTER = new TimeModelRegister(MODID);
    public static TimeModelLocation SMALL_CHEST = REGISTER.register("models/tile/small_chest.json");
    public static TimeModelLocation STICKY_BOMB = REGISTER.register("models/entity/sticky_bomb.json");
   // public static TimeModelLocation GOLDEN_BULLETS = REGISTER.register("models/entity/.json");
    public static TimeModelLocation MULTI_SHOP_OPEN = REGISTER.register("models/tile/multi_shop_open.json");

}
