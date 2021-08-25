package com.rumaruka.riskofmine.init;

import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.common.cap.IMoney;
import com.rumaruka.riskofmine.common.cap.ROMMoney;
import com.rumaruka.riskofmine.common.cap.ROMMoneyStorage;
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

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ROMCapability {

    @AutoRegistrable
    private static final CapabilityRegister REGISTER = new CapabilityRegister(RiskOfMine.MODID);

    @CapabilityInject(IMoney.class)
    public static final Capability<ROMMoney> MONEY = Hacks.promise();

    @AutoRegistrable.InitMethod
    private static void register() {
        REGISTER.regCapability(IMoney.class, new ROMMoneyStorage());
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            TimeCore.INSTANCE.getCapabilityManager().attachStaticCoffeeCapability(CapabilityOwner.ENTITY, MONEY, entity -> entity instanceof PlayerEntity, entity -> new ROMMoney((PlayerEntity) entity));
//            TimeCore.INSTANCE.getCapabilityManager().enableKeepingPlayerCapability(player -> {
//                JPlayer cap = JPlayer.from(player);
//                cap.serialize(coffeeProperty -> {coffeeProperty.})
//            });//TODO make better
            TimeCore.INSTANCE.getCapabilityManager().enableSyncingPlayerCapabilityOnJoin(entity -> {
                ROMMoney cap = ROMMoney.from(entity);
                if (cap != null) cap.sendAllData();
            });
        });
    }
}
