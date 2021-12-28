package com.rumaruka.riskofmine;


import com.rumaruka.riskofmine.api.mobs.EliteMobModifier;
import com.rumaruka.riskofmine.client.ROMEntityRegister;
import com.rumaruka.riskofmine.client.render.layer.LayerMonsterTooth;
import com.rumaruka.riskofmine.client.screen.BaseScreen;
import com.rumaruka.riskofmine.client.screen.BaseShopScreen;
import com.rumaruka.riskofmine.client.screen.overlay.ROMOverlayRender;
import com.rumaruka.riskofmine.common.config.ROMConfig;
import com.rumaruka.riskofmine.common.entity.elites.*;
import com.rumaruka.riskofmine.common.event.ItemEvent;
import com.rumaruka.riskofmine.common.event.LunarEvent;
import com.rumaruka.riskofmine.common.event.MoneyEvent;
import com.rumaruka.riskofmine.compat.jer.ROMJerPlugin;
import com.rumaruka.riskofmine.events.GenerationEventHandler;
import com.rumaruka.riskofmine.init.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.timeconqueror.timecore.api.TimeMod;
import ru.timeconqueror.timecore.api.client.resource.location.TextureLocation;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rumaruka.riskofmine.RiskOfMine.MODID;

@Mod(MODID)
public class RiskOfMine implements TimeMod {

    public static final String MODID = "riskofmine";
    public static final Logger logger = LogManager.getLogger(MODID);
    private static final ModList MOD_LIST = ModList.get();
    protected File configFile;
    private ArrayList<Class<? extends EliteMobModifier>> mobMods = null;
    public RiskOfMine() {
        logger.info("Risk Of Mine add in modpack");
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
    }

    @SubscribeEvent
    public void commonSetup(FMLServerStartedEvent evt) {
        initIfNeeded(evt.getServer().getAllLevels().iterator().next());

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


    }
    private void enqueueIMC(InterModEnqueueEvent event) {
        for (SlotTypePreset preset : SlotTypePreset.values()) {
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> preset.getMessageBuilder().size(ROMConfig.General.sizeCurio.get()).build());
        }
        for (PlayerRenderer render : Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().values()) {
            render.addLayer(new LayerMonsterTooth(render));
        }





    }

    public void initIfNeeded(World world) {
        if (mobMods == null) {
            prepareModList();

            File mcFolder;
            if (world.isClientSide()) {
                RiskOfMineClient.load();
                mcFolder = RiskOfMineClient.getMcFolder();
            } else {
                MinecraftServer server = LogicalSidedProvider.INSTANCE.get(LogicalSide.SERVER);
                mcFolder = server.getFile("");
            }

            configFile = new File(mcFolder, File.separatorChar + "config" + File.separatorChar + "riskofmine-elite-mob.cfg");
//            loadConfig();

            logger.info("RISK OF MINE setup completed! Modifiers ready: " + mobMods.size());
            logger.info("RISK OF MINE setup completed! config file at: " + configFile.getAbsolutePath());
        }
    }
    private void prepareModList() {
        mobMods = new ArrayList<>();

        mobMods.add(BlazingElite.class);
        mobMods.add(OverloadingElite.class);
        mobMods.add(GlacialElite.class);
        mobMods.add(MalachiteElite.class);
        mobMods.add(CelestineElite.class);
        mobMods.add(PerfectedElite.class);

    }

    //    private void loadConfig() {
//        ROMConfig defaultConfig = new ROMConfig();
//        defaultConfig.setEliteRarity(15);
//        defaultConfig.setUltraRarity(7);
//        defaultConfig.setInfernoRarity(7);
//        defaultConfig.setUseSimpleEntityClassNames(true);
//        defaultConfig.setDisableHealthBar(false);
//        defaultConfig.setModHealthFactor(1.0D);
//
//        List<String> dropsElite = new ArrayList<>();
//        dropsElite.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.IRON_SHOVEL)));
//        dropsElite.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.IRON_PICKAXE)));
//        dropsElite.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.IRON_AXE)));
//        dropsElite.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.IRON_SWORD)));
//        dropsElite.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.IRON_HOE)));
//        dropsElite.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.CHAINMAIL_HELMET)));
//        dropsElite.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.CHAINMAIL_BOOTS)));
//        dropsElite.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.CHAINMAIL_CHESTPLATE)));
//        dropsElite.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.CHAINMAIL_LEGGINGS)));
//        dropsElite.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.IRON_HELMET)));
//        dropsElite.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.IRON_BOOTS)));
//        dropsElite.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.IRON_CHESTPLATE)));
//        dropsElite.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.IRON_LEGGINGS)));
//        dropsElite.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.COOKIE, 5)));
//        defaultConfig.setDroppedItemIDsElite(dropsElite);
//
//        List<String> dropsUltra = new ArrayList<>();
//        dropsUltra.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.IRON_HOE)));
//        dropsUltra.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.BOW)));
//        dropsUltra.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.CHAINMAIL_HELMET)));
//        dropsUltra.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.CHAINMAIL_BOOTS)));
//        dropsUltra.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.CHAINMAIL_CHESTPLATE)));
//        dropsUltra.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.CHAINMAIL_LEGGINGS)));
//        dropsUltra.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.IRON_HELMET)));
//        dropsUltra.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.IRON_BOOTS)));
//        dropsUltra.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.IRON_CHESTPLATE)));
//        dropsUltra.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.IRON_LEGGINGS)));
//        dropsUltra.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.GOLDEN_HELMET)));
//        dropsUltra.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.GOLDEN_BOOTS)));
//        dropsUltra.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.GOLDEN_CHESTPLATE)));
//        dropsUltra.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.GOLDEN_LEGGINGS)));
//        dropsUltra.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.GOLDEN_APPLE, 3)));
//        dropsUltra.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.BLAZE_POWDER, 5)));
//        dropsUltra.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.ENCHANTED_BOOK)));
//        defaultConfig.setDroppedItemIDsUltra(dropsUltra);
//
//        List<String> dropsInfernal = new ArrayList<>();
//        dropsInfernal.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.ENCHANTED_BOOK)));
//        dropsInfernal.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.DIAMOND, 3)));
//        dropsInfernal.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.DIAMOND_SWORD)));
//        dropsInfernal.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.DIAMOND_AXE)));
//        dropsInfernal.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.DIAMOND_HOE)));
//        dropsInfernal.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.DIAMOND_PICKAXE)));
//        dropsInfernal.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.DIAMOND_SHOVEL)));
//        dropsInfernal.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.CHAINMAIL_HELMET)));
//        dropsInfernal.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.CHAINMAIL_BOOTS)));
//        dropsInfernal.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.CHAINMAIL_CHESTPLATE)));
//        dropsInfernal.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.CHAINMAIL_LEGGINGS)));
//        dropsInfernal.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.DIAMOND_HELMET)));
//        dropsInfernal.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.DIAMOND_BOOTS)));
//        dropsInfernal.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.DIAMOND_CHESTPLATE)));
//        dropsInfernal.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.DIAMOND_LEGGINGS)));
//        dropsInfernal.add(ItemConfigHelper.fromItemStack(new ItemStack(Items.ENDER_PEARL, 3)));
//        defaultConfig.setDroppedItemIDsInfernal(dropsInfernal);
//
//        defaultConfig.setMaxDamage(10D);
//        defaultConfig.setDimensionIDBlackList(new ArrayList<>());
//
//        Map<String, Boolean> modsEnabledMap = new HashMap<>();
//        for (Class<?> c : mobMods) {
//            modsEnabledMap.put(c.getSimpleName(), true);
//        }
//        defaultConfig.setModsEnabled(modsEnabledMap);
//
//        config = GsonConfig.loadConfigWithDefault(InfernalMobsConfig.class, configFile, defaultConfig);
//
//        lootItemDropsElite = new ItemConfigHelper(config.getDroppedItemIDsElite(), logger);
//        lootItemDropsUltra = new ItemConfigHelper(config.getDroppedItemIDsUltra(), logger);
//        lootItemDropsInfernal = new ItemConfigHelper(config.getDroppedItemIDsInfernal(), logger);
//
//        mobMods.removeIf(c -> !config.getModsEnabled().containsKey(c.getSimpleName()) || !config.getModsEnabled().get(c.getSimpleName()));
//    }
    public static ResourceLocation rl(String path) {
        return new ResourceLocation(RiskOfMine.MODID, path);
    }
    public static TextureLocation tl(String path) {
        return new TextureLocation(RiskOfMine.MODID, path);
    }

}