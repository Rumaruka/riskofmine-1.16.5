package com.rumaruka.riskofmine.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.minecart.MinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class ROMMathFormula {

    private final PlayerEntity player = Minecraft.getInstance().player;
    public static float powerIncreasing(float x,float y) {

        return MathHelper.abs((float) ROMMathUtils.multiply(x,y))/30 - (ROMMathUtils.percent(15) * ROMMathUtils.percent(x*y));


    }

    public static double speedIncreasing(float x) {

        return  Math.abs(Math.tan(Math.PI* x / 180));


    }


}
