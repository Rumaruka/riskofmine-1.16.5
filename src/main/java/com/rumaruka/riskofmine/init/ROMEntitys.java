package com.rumaruka.riskofmine.init;

import com.rumaruka.riskofmine.common.entity.misc.HealthOrbEntity;

import com.rumaruka.riskofmine.common.entity.bullets.EntityGoldenIngotBullets;
import com.rumaruka.riskofmine.common.entity.weapon.EntityStickyBomb;
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

    public static final EntityType<EntityGoldenIngotBullets> GOLD_BULLETS = REGISTER.register("gold_bullets",
                    Builder.<EntityGoldenIngotBullets>of(EntityGoldenIngotBullets::new, EntityClassification.MISC)
                            .setTrackingRange(80)
                            .setShouldReceiveVelocityUpdates(true)
                            .sized(3.5F, 3.5F))
            .retrieve();

    public static final EntityType<EntityStickyBomb> STICKY_BOMB = REGISTER.register("sticky_bomb",
                    Builder.<EntityStickyBomb>of(EntityStickyBomb::new, EntityClassification.MISC)
                            .setTrackingRange(80)
                            .setShouldReceiveVelocityUpdates(true)
                            .sized(3.5F, 3.5F))
            .retrieve();

}
