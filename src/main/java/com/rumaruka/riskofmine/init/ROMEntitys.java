package com.rumaruka.riskofmine.init;

import com.rumaruka.riskofmine.common.entity.HealthOrbEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.ObjectHolder;
import ru.timeconqueror.timecore.api.registry.EntityRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

import static com.rumaruka.riskofmine.RiskOfMine.MODID;
import static net.minecraft.entity.EntityType.Builder;


@ObjectHolder(MODID)
public class ROMEntitys {
    @AutoRegistrable
    private static final EntityRegister REGISTER = new EntityRegister(MODID);


    public static final EntityType<HealthOrbEntity> HEALTH_ORB = REGISTER.register("health_orb",
            Builder.<HealthOrbEntity>of(HealthOrbEntity::new, EntityClassification.MISC)
                    .setTrackingRange(80)
                    .setShouldReceiveVelocityUpdates(true)
                    .sized(3.5F, 3.5F))
            .retrieve();


}
