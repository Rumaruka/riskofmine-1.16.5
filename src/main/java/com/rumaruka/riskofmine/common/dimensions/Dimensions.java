package com.rumaruka.riskofmine.common.dimensions;

import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.init.ROMBlocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Dimensions {

    public static final DeferredRegister<PointOfInterestType> POI = DeferredRegister.create(ForgeRegistries.POI_TYPES, RiskOfMine.MODID);

    public static final RegistryKey<World> DISTANT_ROOST = RegistryKey.create(Registry.DIMENSION_REGISTRY, RiskOfMine.rl("distant_roost"));


    public static RegistryObject<PointOfInterestType> DISTANT_ROOST_PORTAL = POI.register("distant_roost_portal", () -> new PointOfInterestType("distant_roost_portal", PointOfInterestType.getBlockStates(ROMBlocks.DISTANT_ROOST_GRASS), 0, 1));

    public static void register(IEventBus bus) {
        POI.register(bus);
    }
}
