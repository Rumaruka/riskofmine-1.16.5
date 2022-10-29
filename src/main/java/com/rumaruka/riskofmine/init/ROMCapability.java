package com.rumaruka.riskofmine.init;

import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.common.cap.barrier.IBarrier;
import com.rumaruka.riskofmine.common.cap.barrier.ROMBarrier;
import com.rumaruka.riskofmine.common.cap.barrier.ROMBarrierStorage;
import com.rumaruka.riskofmine.common.cap.lunar.ILunar;
import com.rumaruka.riskofmine.common.cap.lunar.ROMLunar;
import com.rumaruka.riskofmine.common.cap.lunar.ROMLunarStorage;
import com.rumaruka.riskofmine.common.cap.money.IMoney;
import com.rumaruka.riskofmine.common.cap.money.ROMMoney;
import com.rumaruka.riskofmine.common.cap.money.ROMMoneyStorage;
import com.rumaruka.riskofmine.common.cap.shields.IShields;
import com.rumaruka.riskofmine.common.cap.shields.ROMShields;
import com.rumaruka.riskofmine.common.cap.shields.ROMShieldsStorage;
import com.rumaruka.riskofmine.common.cap.timer.ITimer;
import com.rumaruka.riskofmine.common.cap.timer.ROMTimer;
import com.rumaruka.riskofmine.common.cap.timer.ROMTimerStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import ru.timeconqueror.timecore.TimeCore;
import ru.timeconqueror.timecore.api.registry.CapabilityRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;
import ru.timeconqueror.timecore.api.util.Hacks;
import ru.timeconqueror.timecore.common.capability.owner.CapabilityOwner;

import static ru.timeconqueror.timecore.api.util.Hacks.promise;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ROMCapability {

    @AutoRegistrable
    private static final CapabilityRegister REGISTER = new CapabilityRegister(RiskOfMine.MODID);

    @CapabilityInject(IMoney.class)
    public static final Capability<ROMMoney> MONEY = promise();
    @CapabilityInject(ILunar.class)
    public static final Capability<ROMLunar> LUNAR = promise();
    @CapabilityInject(IShields.class)
    public static final Capability<ROMShields> SHIELDS = promise();
    @CapabilityInject(IBarrier.class)
    public static final Capability<ROMBarrier> BARRIER = promise();

    @CapabilityInject(ITimer.class)
    public static final Capability<ROMTimer>TIMER = promise();
    @AutoRegistrable.InitMethod
    private static void register() {

        REGISTER.regCapability(IMoney.class, new ROMMoneyStorage());
        REGISTER.regCapability(ILunar.class,new ROMLunarStorage());
        REGISTER.regCapability(IShields.class,new ROMShieldsStorage());
        REGISTER.regCapability(IBarrier.class,new ROMBarrierStorage());
        REGISTER.regCapability(ITimer.class,new ROMTimerStorage());
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            TimeCore.INSTANCE.getCapabilityManager().attachStaticCoffeeCapability(CapabilityOwner.ENTITY, MONEY, entity -> entity instanceof PlayerEntity, entity -> new ROMMoney((PlayerEntity) entity));
            TimeCore.INSTANCE.getCapabilityManager().enableSyncingPlayerCapabilityOnJoin(entity -> {
                ROMMoney cap = ROMMoney.from(entity);
                if (cap != null) cap.sendAllData();
            });
        });

        event.enqueueWork(()->{
           TimeCore.INSTANCE.getCapabilityManager().attachStaticCoffeeCapability(CapabilityOwner.ENTITY,LUNAR,entity -> entity instanceof PlayerEntity, entity -> new ROMLunar((PlayerEntity) entity));
            TimeCore.INSTANCE.getCapabilityManager().enableSyncingPlayerCapabilityOnJoin(entity -> {
                ROMLunar cap = ROMLunar.from(entity);
                if (cap != null) cap.sendAllData();
            });
        });


        event.enqueueWork(()->{
            TimeCore.INSTANCE.getCapabilityManager().attachStaticCoffeeCapability(CapabilityOwner.ENTITY,SHIELDS,entity -> entity instanceof PlayerEntity, entity -> new ROMShields((PlayerEntity) entity));
            TimeCore.INSTANCE.getCapabilityManager().enableSyncingPlayerCapabilityOnJoin(entity -> {
                ROMShields cap = ROMShields.from(entity);
                if (cap != null) cap.sendAllData();
            });
        });

        event.enqueueWork(()->{
            TimeCore.INSTANCE.getCapabilityManager().attachStaticCoffeeCapability(CapabilityOwner.ENTITY,BARRIER,entity -> entity instanceof PlayerEntity, entity -> new ROMBarrier((PlayerEntity) entity));
            TimeCore.INSTANCE.getCapabilityManager().enableSyncingPlayerCapabilityOnJoin(entity -> {
                ROMBarrier cap = ROMBarrier.from(entity);
                if (cap != null) cap.sendAllData();
            });
        });


        event.enqueueWork(()->{
            TimeCore.INSTANCE.getCapabilityManager().attachStaticCoffeeCapability(CapabilityOwner.ENTITY,TIMER,entity -> entity instanceof PlayerEntity, entity -> new ROMTimer((PlayerEntity) entity));
            TimeCore.INSTANCE.getCapabilityManager().enableSyncingPlayerCapabilityOnJoin(entity -> {
                ROMTimer cap = ROMTimer.from(entity);
                if (cap != null) cap.sendAllData();
            });
        });
    }
}
