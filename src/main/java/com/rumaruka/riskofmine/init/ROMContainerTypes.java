package com.rumaruka.riskofmine.init;

import com.rumaruka.riskofmine.common.inventory.ChestInventory;
import com.rumaruka.riskofmine.common.inventory.ChestShopInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import ru.timeconqueror.timecore.api.registry.SimpleForgeRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

import static com.rumaruka.riskofmine.RiskOfMine.MODID;
import static ru.timeconqueror.timecore.api.util.Hacks.promise;

@ObjectHolder(MODID)
public class ROMContainerTypes {
    @AutoRegistrable
    private static final SimpleForgeRegister<ContainerType<?>> REGISTER = new SimpleForgeRegister<>(ForgeRegistries.CONTAINERS, MODID);

    public static final ContainerType<ChestInventory> SMALL_CHEST = promise();
    public static final ContainerType<ChestInventory> LARGE_CHEST = promise();
    public static final ContainerType<ChestInventory> LEGENDARY_CHEST = promise();
    public static final ContainerType<ChestInventory>LUNAR_CHEST = promise();
    public static final ContainerType<ChestShopInventory>MULTI_SHOP = promise();

    @AutoRegistrable.InitMethod
    private static void register() {
        REGISTER.register("small_chest", () -> new ContainerType<>(ChestInventory::createCommonContainer));
        REGISTER.register("large_chest", () -> new ContainerType<>(ChestInventory::createLargeContainer));
        REGISTER.register("legendary_chest", () -> new ContainerType<>(ChestInventory::createLegendaryContainer));
        REGISTER.register("lunar_chest",()->new ContainerType<>(ChestInventory::createLunarContainer));
        REGISTER.register("multi_shop",()->new ContainerType<>(ChestShopInventory::createMultiShopContainer));
    }
}
