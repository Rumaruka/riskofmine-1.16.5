package com.rumaruka.riskofmine.datagen.chests;

import com.rumaruka.riskofmine.datagen.loot.ROMLootTables;
import com.rumaruka.riskofmine.init.ROMItems;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.loot.*;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiConsumer;

public class ChestLootTableROM extends ChestLootTables {
    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(ROMLootTables.SMALL_CHEST, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(ROMItems.ARMOR_PIERCING_ROUNDS).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F))))
                        .add(ItemLootEntry.lootTableItem(ROMItems.CROWBAR).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F))))
                        .add(ItemLootEntry.lootTableItem(ROMItems.SOLDIER_SYRINGE).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F))))
                        .add(ItemLootEntry.lootTableItem(ROMItems.BUSTLING_FUNGUS).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F))))
                        .add(ItemLootEntry.lootTableItem(ROMItems.ENERGY_DRINK).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F))))
                        .add(ItemLootEntry.lootTableItem(ROMItems.FOCUS_CRYSTAL).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F))))
                        .add(ItemLootEntry.lootTableItem(ROMItems.GASOLINE).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F))))
                        .add(ItemLootEntry.lootTableItem(ROMItems.MONSTER_TOOTH).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F))))
                        .add(ItemLootEntry.lootTableItem(ROMItems.STUN_GRENADE).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F))))
                        .add(ItemLootEntry.lootTableItem(ROMItems.TRI_TIP_DAGGER).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1,1))))));

        consumer.accept(ROMLootTables.LARGE_CHEST, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(ROMItems.INFUSION).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1,1))))
                        .add(ItemLootEntry.lootTableItem(ROMItems.BUSTLING_FUNGUS).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1,1))))

                )
        );
    }

}
