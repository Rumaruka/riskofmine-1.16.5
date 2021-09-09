package com.rumaruka.riskofmine.init;


import com.rumaruka.riskofmine.ModSetup;
import com.rumaruka.riskofmine.common.blocks.*;
import com.rumaruka.riskofmine.common.blocks.chests.*;
import net.minecraftforge.registries.ObjectHolder;
import ru.timeconqueror.timecore.api.registry.BlockRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

import static com.rumaruka.riskofmine.RiskOfMine.MODID;
import static ru.timeconqueror.timecore.api.util.Hacks.promise;

@ObjectHolder(MODID)
public class ROMBlocks {
    public static final SmallChestBlock SMALL_CHEST = promise();
    public static final LargeChestBlock LARGE_CHEST = promise();
    public static final LegendaryChestBlock LEGENDARY_CHEST = promise();
  public static final LunarChestBlock LUNAR_CHEST = promise();
//  public static final SmallChestBlock EQUIPMENT_BARREL = promise();

//  public static final SmallChestBlock DAMAGE_CHEST = promise();
//  public static final SmallChestBlock HEALING_CHEST = promise();
//  public static final SmallChestBlock UTILITY_CHEST = promise();

  public static final MultiShopBlock MULTI_SHOP = promise();
  public static final EquipmentTripleBarrelBlock EQUIPMENT_TRIPLE_BARREL = promise();
//  public static final SmallChestBlock EQUIPMENT_TRIPLE_BARREL = promise();
//  public static final SmallChestBlock RUSTY_CHEST = promise();

    public static final WarBannerBlock WAR_BANNER_BLOCK = promise();


    private static class Setup {

        @AutoRegistrable
        private static final BlockRegister REGISTER = new BlockRegister(MODID);

        @AutoRegistrable.InitMethod
        private static void register() {
            REGISTER.register("small_chest", SmallChestBlock::new).oneVarStateAndCubeAllModel().defaultBlockItem(ModSetup.ITEM_GROUP);
            REGISTER.register("large_chest", LargeChestBlock::new).oneVarStateAndCubeAllModel().defaultBlockItem(ModSetup.ITEM_GROUP);
            REGISTER.register("legendary_chest", LegendaryChestBlock::new).oneVarStateAndCubeAllModel().defaultBlockItem(ModSetup.ITEM_GROUP);
            REGISTER.register("lunar_chest",LunarChestBlock::new).oneVarStateAndCubeAllModel().defaultBlockItem(ModSetup.ITEM_GROUP);
            REGISTER.register("multi_shop",MultiShopBlock::new).oneVarStateAndCubeAllModel().defaultBlockItem(ModSetup.ITEM_GROUP);
            REGISTER.register("equipment_triple_barrel",EquipmentTripleBarrelBlock::new).oneVarStateAndCubeAllModel().defaultBlockItem(ModSetup.ITEM_GROUP);

            REGISTER.register("war_banner", WarBannerBlock::new).oneVarStateAndCubeAllModel().defaultBlockItem(ModSetup.ITEM_GROUP);


        }
    }


}