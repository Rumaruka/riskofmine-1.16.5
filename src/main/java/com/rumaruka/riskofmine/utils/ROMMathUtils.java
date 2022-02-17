package com.rumaruka.riskofmine.utils;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class ROMMathUtils {


    public static double summ(double x, double y) {
        return x + y;

    }

    public static double multiply(double x, double y) {
        return x * y;
    }

    public static float divide(float x, float y) {
        if (y == 0) {
            throw new ArithmeticException("Divide Null");
        }
        return x / y;
    }

   public static float percent(float x){
        return x / 100;
   }




}
