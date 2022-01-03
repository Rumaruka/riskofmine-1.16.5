package com.rumaruka.riskofmine.init;

import net.minecraft.potion.Effect;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.rumaruka.riskofmine.RiskOfMine.MODID;
import static com.rumaruka.riskofmine.RiskOfMine.rl;

public class ROMDimensions {

   public static final RegistryKey<DimensionType>DISTANT_ROOST = RegistryKey.create(Registry.DIMENSION_TYPE_REGISTRY,rl("distant_roost"));
   public static final RegistryKey<World>DISTANT_ROOST_WORLD = RegistryKey.create(Registry.DIMENSION_REGISTRY,rl("distant_roost"));
//   public static final RegistryKey<DimensionType>TITANIC_PLAINS= RegistryKey.create(Registry.DIMENSION_TYPE_REGISTRY,rl("titanic_plains"));
//   public static final RegistryKey<World>TITANIC_PLAINS_WORLD= RegistryKey.create(Registry.DIMENSION_REGISTRY,rl("titanic_plains"));
//   public static final RegistryKey<DimensionType>ABANDONED_AQUEDUCT= RegistryKey.create(Registry.DIMENSION_TYPE_REGISTRY,rl("abandoned_aqueduct"));
//   public static final RegistryKey<DimensionType>WETLAND_ASPECT= RegistryKey.create(Registry.DIMENSION_TYPE_REGISTRY,rl("wetland_aspect"));
//   public static final RegistryKey<DimensionType>RALLYPOINT_DELTA= RegistryKey.create(Registry.DIMENSION_TYPE_REGISTRY,rl("rallypoint_delta"));
//   public static final RegistryKey<DimensionType>SCORCHED_ACRES= RegistryKey.create(Registry.DIMENSION_TYPE_REGISTRY,rl("scorched_acres"));
//   public static final RegistryKey<DimensionType>ABYSSAL_DEPTHS= RegistryKey.create(Registry.DIMENSION_TYPE_REGISTRY,rl("abyssal_depths"));
//   public static final RegistryKey<DimensionType>SIRENS_CALL= RegistryKey.create(Registry.DIMENSION_TYPE_REGISTRY,rl("sirens_call"));
//   public static final RegistryKey<DimensionType>SUNDERED_GROVE= RegistryKey.create(Registry.DIMENSION_TYPE_REGISTRY,rl("sundered_grove"));
//   public static final RegistryKey<DimensionType>SKY_MEADOW= RegistryKey.create(Registry.DIMENSION_TYPE_REGISTRY,rl("sky_meadow"));
//   public static final RegistryKey<DimensionType>COMMENCEMENT= RegistryKey.create(Registry.DIMENSION_TYPE_REGISTRY,rl("commencement"));

}
