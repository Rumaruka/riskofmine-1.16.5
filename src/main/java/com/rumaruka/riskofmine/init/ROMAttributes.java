package com.rumaruka.riskofmine.init;
import com.rumaruka.riskofmine.RiskOfMine;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import ru.timeconqueror.timecore.api.registry.SimpleForgeRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

public class ROMAttributes {
    @AutoRegistrable
    public static SimpleForgeRegister<Attribute> REGISTER = new SimpleForgeRegister<>(ForgeRegistries.ATTRIBUTES, RiskOfMine.MODID);

    public static final RegistryObject<Attribute> MAX_MONEY =
            REGISTER.register("max_money", () -> new RangedAttribute("riskofmine.max_money", 0.0F, 0.0F, 1000000.0F).setSyncable(true));

    public static final RegistryObject<Attribute> MONEY_REGEN_SPEED =
            REGISTER.register("essence_money_speed", () -> new RangedAttribute("riskofmine.money_regen_speed", 0.012F, 0.0F, 0.065F).setSyncable(true));

    public static final RegistryObject<Attribute> MONEY_BURNOUT =
            REGISTER.register("money_burnout_time", () -> new RangedAttribute("riskofmine.money_burnout_time", 5.0F, 0.0F, 50.0F).setSyncable(false));
}

