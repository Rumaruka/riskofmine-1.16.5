package com.rumaruka.riskofmine.init;

import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.gen.commonchestgen.CommonChestFeature;
import com.rumaruka.riskofmine.gen.commonchestgen.CommonChestFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class ROMFeatures {
    private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, RiskOfMine.MODID);

    public static final RegistryObject<CommonChestFeature<CommonChestFeatureConfig>> SMALL_CHEST = FEATURES.register("small_chest", () -> new CommonChestFeature<>(CommonChestFeatureConfig.CODEC));

    public static void registerFeatures() {
        FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
