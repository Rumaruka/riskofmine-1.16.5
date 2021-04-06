package com.rumaruka.riskofmine.init;


import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.rumaruka.riskofmine.RiskOfMine.MODID;

public class ROMParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID);
    public static final RegistryObject<BasicParticleType> FOCUS_CRYSTAL = PARTICLES.register("focus_crystal", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> STUN_PARTICLES = PARTICLES.register("stun", () -> new BasicParticleType(true));
}
