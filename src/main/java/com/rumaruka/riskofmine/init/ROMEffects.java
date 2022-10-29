package com.rumaruka.riskofmine.init;

import com.rumaruka.riskofmine.common.effect.BleedEffect;
import com.rumaruka.riskofmine.common.effect.NullifiedEffect;
import com.rumaruka.riskofmine.common.effect.StunEffect;
import com.rumaruka.riskofmine.common.entity.elites.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.rumaruka.riskofmine.RiskOfMine.MODID;

public class ROMEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, MODID);
    /**
     * POTIONS FOR TESTING EFFECTS!
     */
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, MODID);


    public static final RegistryObject<Effect> STUN = EFFECTS.register("stun", () -> new StunEffect(EffectType.HARMFUL, 0x2A2D2E));
    public static final RegistryObject<Effect> BLEED = EFFECTS.register("bleed", () -> new BleedEffect(EffectType.BENEFICIAL, 5646433));
    public static final RegistryObject<Effect> NULLIFIED = EFFECTS.register("nullified", () -> new NullifiedEffect(EffectType.BENEFICIAL, 5646433));



    public static final RegistryObject<Effect> BLAZING = EFFECTS.register("blazing",()->new BlazingMobs(EffectType.BENEFICIAL,5646433));
    public static final RegistryObject<Effect> CELESTINE = EFFECTS.register("celestine",()->new CelestineMobs(EffectType.BENEFICIAL,5646433));
    public static final RegistryObject<Effect> GLACIAL = EFFECTS.register("glacial",()->new GlacialMobs(EffectType.BENEFICIAL,5646433));
    public static final RegistryObject<Effect> MALACHITE = EFFECTS.register("malachite",()->new MalachiteMobs(EffectType.BENEFICIAL,5646433));
    public static final RegistryObject<Effect> OVERLOADING = EFFECTS.register("overloading",()->new OverloadingMobs(EffectType.BENEFICIAL,5646433));
    public static final RegistryObject<Effect> PERFECTED = EFFECTS.register("perfected",()->new PerfectedMobs(EffectType.BENEFICIAL,5646433));
}
