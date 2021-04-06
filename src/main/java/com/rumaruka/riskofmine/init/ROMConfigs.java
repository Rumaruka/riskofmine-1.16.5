package com.rumaruka.riskofmine.init;


import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.common.config.ModConfig;
import net.minecraftforge.registries.ObjectHolder;
import ru.timeconqueror.timecore.api.registry.ConfigRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

import static com.rumaruka.riskofmine.RiskOfMine.MODID;

@ObjectHolder(MODID)
public class ROMConfigs {


    private static class Setup {
        @AutoRegistrable
        private static final ConfigRegister REGISTER = new ConfigRegister(MODID);

        @AutoRegistrable.InitMethod
        private static void register() {
            REGISTER.register(ModConfig.MAIN);
        }
    }

}
