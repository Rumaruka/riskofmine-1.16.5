package com.rumaruka.riskofmine.init;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

import javax.annotation.Nullable;

public class ROMDamageSource {

    public static final DamageSource BLEED = (new DamageSource("bleed"));


    public static DamageSource bleeded(FireworkRocketEntity fireworkRocketEntity_, @Nullable Entity entity_) {
        return (new IndirectEntityDamageSource("bleeded", fireworkRocketEntity_, entity_)).setScalesWithDifficulty();
    }

}
