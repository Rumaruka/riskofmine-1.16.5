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

    public static final RegistryObject<Attribute> MAX_SHIELD =
            REGISTER.register("max_shield", () -> new RangedAttribute("riskofmine.max_shield", 0.0F, 0.0F, 1000000.0F).setSyncable(true));


}

