package com.rumaruka.riskofmine;


import com.mojang.brigadier.CommandDispatcher;
import com.rumaruka.riskofmine.client.ROMEntityRegister;
import com.rumaruka.riskofmine.client.render.layer.LayerMonsterTooth;
import com.rumaruka.riskofmine.client.screen.BaseScreen;
import com.rumaruka.riskofmine.client.screen.BaseShopScreen;
import com.rumaruka.riskofmine.client.screen.overlay.ROMOverlayRender;
import com.rumaruka.riskofmine.common.config.ROMConfig;
import com.rumaruka.riskofmine.common.event.ItemEvent;
import com.rumaruka.riskofmine.common.event.LunarEvent;
import com.rumaruka.riskofmine.common.event.MoneyEvent;
import com.rumaruka.riskofmine.common.event.ShieldsEvent;
import com.rumaruka.riskofmine.compat.jer.ROMJerPlugin;
import com.rumaruka.riskofmine.events.GenerationEventHandler;
import com.rumaruka.riskofmine.init.*;
import com.rumaruka.riskofmine.ntw.cmd.MoneyAddCommand;
import com.rumaruka.riskofmine.ntw.cmd.MoneySetCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.command.CommandSource;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.timeconqueror.timecore.api.TimeMod;
import ru.timeconqueror.timecore.api.client.resource.location.TextureLocation;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.util.*;

import static com.rumaruka.riskofmine.RiskOfMine.MODID;

@Mod(MODID)
@Mod.EventBusSubscriber
public class RiskOfMine implements TimeMod {
    private static RiskOfMine instance;
    public static final String MODID = "riskofmine";
    public static final Logger logger = LogManager.getLogger(MODID);
    private static final ModList MOD_LIST = ModList.get();
    private ArrayList<Enchantment> enchantmentList;

    public RiskOfMine() {
        logger.info("Risk Of Mine add in modpack");
        instance = this;
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, ROMConfig.commonConfig);
        eventBus.register(ROMConfig.class);
        eventBus.addListener(this::setup);
        eventBus.addListener(this::enqueueIMC);
        ROMSounds.REGISTER.register(eventBus);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            // Client setup
            eventBus.addListener(this::clientSetup);
            eventBus.addListener(ROMOverlayRender::keyPressed);
        });

        ROMParticles.PARTICLES.register(eventBus);
        ROMEffects.EFFECTS.register(eventBus);
        ROMEffects.POTIONS.register(eventBus);
        ROMFeatures.registerFeatures();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ItemEvent());
        MinecraftForge.EVENT_BUS.register(new MoneyEvent());
        MinecraftForge.EVENT_BUS.register(new LunarEvent());
        MinecraftForge.EVENT_BUS.register(new ShieldsEvent());
    }
    @SubscribeEvent
    public static void onRegisterCommandEvent(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> commandDispatcher = event.getDispatcher();
        MoneyAddCommand.register(commandDispatcher);
        MoneySetCommand.register(commandDispatcher);
    }

    private void setup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new GenerationEventHandler());
        if (MOD_LIST.isLoaded("jeresources")) {
            ROMJerPlugin.setup(event);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void clientSetup(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(ROMBlocks.SMALL_CHEST, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(ROMBlocks.LARGE_CHEST, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(ROMBlocks.LUNAR_CHEST, RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(ROMBlocks.LEGENDARY_CHEST, RenderType.cutoutMipped());

        ScreenManager.register(ROMContainerTypes.SMALL_CHEST, BaseScreen::new);
        ScreenManager.register(ROMContainerTypes.LARGE_CHEST, BaseScreen::new);
        ScreenManager.register(ROMContainerTypes.LEGENDARY_CHEST, BaseScreen::new);
        ScreenManager.register(ROMContainerTypes.LUNAR_CHEST, BaseScreen::new);


        ScreenManager.register(ROMContainerTypes.MULTI_SHOP, BaseShopScreen::new);
        ScreenManager.register(ROMContainerTypes.EQUIPMENT_TRIPLE_BARREL, BaseShopScreen::new);

        ROMEntityRegister.renderEntity();

        for (PlayerRenderer render : Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().values()) {
            render.addLayer(new LayerMonsterTooth(render));
        }
    }

    private void enqueueIMC(InterModEnqueueEvent event) {
        for (SlotTypePreset preset : SlotTypePreset.values()) {
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> preset.getMessageBuilder().size(ROMConfig.General.sizeCurio.get()).build());
        }

    }


    public static RiskOfMine instance() {
        return instance;
    }


    public static ResourceLocation rl(String path) {
        return new ResourceLocation(RiskOfMine.MODID, path);
    }

    public static TextureLocation tl(String path) {
        return new TextureLocation(RiskOfMine.MODID, path);
    }




    //Api


}