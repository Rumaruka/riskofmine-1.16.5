package com.rumaruka.riskofmine.init;

import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.gen.commonchestgen.CommonChestFeature;
import com.rumaruka.riskofmine.gen.commonchestgen.CommonChestFeatureConfig;
import com.rumaruka.riskofmine.gen.largechestgen.LargeChestFeature;
import com.rumaruka.riskofmine.gen.largechestgen.LargeChestFeatureConfig;
import com.rumaruka.riskofmine.gen.lunarchestgen.LunarChestFeature;
import com.rumaruka.riskofmine.gen.lunarchestgen.LunarChestFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class ROMFeatures {
    private static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, RiskOfMine.MODID);
    private static final  DeferredRegister<Structure<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, RiskOfMine.MODID);
    public static final RegistryObject<CommonChestFeature<CommonChestFeatureConfig>> SMALL_CHEST = FEATURES.register("small_chest", () -> new CommonChestFeature<>(CommonChestFeatureConfig.CODEC));
    public static final RegistryObject<LargeChestFeature<LargeChestFeatureConfig>> LARGE_CHEST= FEATURES.register("large_chest",()->new LargeChestFeature<>(LargeChestFeatureConfig.CODEC));
    public static final RegistryObject<LunarChestFeature<LunarChestFeatureConfig>> LUNAR_CHEST= FEATURES.register("lunar_chest",()->new LunarChestFeature<>(LunarChestFeatureConfig.CODEC));
    public static void registerFeatures() {
        FEATURES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
