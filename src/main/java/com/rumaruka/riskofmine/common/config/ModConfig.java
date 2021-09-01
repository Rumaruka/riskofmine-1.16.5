package com.rumaruka.riskofmine.common.config;

import com.rumaruka.riskofmine.RiskOfMine;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import ru.timeconqueror.timecore.api.common.config.Config;
import ru.timeconqueror.timecore.api.common.config.IQuickConfigValue;
import ru.timeconqueror.timecore.api.common.config.ImprovedConfigBuilder;

public class ModConfig extends Config {

    public static final ModConfig MAIN = new ModConfig();

    //Add parameters
    public static IQuickConfigValue<Integer> sizeCurio;
    public static IQuickConfigValue<Integer> cooldownEq;
    public static IQuickConfigValue<Integer> durStunConfig;
    public static IQuickConfigValue<Integer> durBleedConfig;
    public static IQuickConfigValue<Float> priceSmallChest;
    public static IQuickConfigValue<Float> priceLargeChest;
    public static IQuickConfigValue<Float> priceLegendaryChest;


    public ModConfig() {
        super(net.minecraftforge.fml.config.ModConfig.Type.COMMON, RiskOfMine.MODID, "Risk of Mine Configs");
    }

    @Override
    public void setup(ImprovedConfigBuilder builder) {
        sizeCurio = builder
                .optimized(builder
                        .comment("How much Curio slots.")
                        .define("Curios Size", 2));
        cooldownEq = builder
                .optimized(builder
                        .comment("Cooldowns Equipment Items.")
                        .define("Cooldowns", 5000));
        durStunConfig = builder
                .optimized(builder
                        .comment("Duration Stun")
                        .define("Duration Stun", 6000));
        durBleedConfig = builder
                .optimized(builder
                        .comment("Duration Bleed")
                        .define("Duration Bleed", 100));
        priceSmallChest = builder
                .optimized(builder
                        .comment("Payment for Open Small Chest")
                        .define("Payment Small Chest", 10.0f));
        priceLargeChest = builder
                .optimized(builder
                        .comment("Payment for Open Large Chest")
                        .define("Payment Large Chest", 100.0f));
        priceLegendaryChest = builder
                .optimized(builder
                        .comment("Payment for Open Legendary Chest")
                        .define("Payment Legendary Chest", 1000.0f));

    }
}
