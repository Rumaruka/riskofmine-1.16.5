package com.rumaruka.riskofmine.common.items.equipment;

import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.api.EnumType;
import com.rumaruka.riskofmine.common.items.ItemCollectiblesBase;
import com.rumaruka.riskofmine.init.ROMItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.function.Predicate;

public  class EquipmentItemBase extends ItemCollectiblesBase {
    private final CategoryEnum categoryEnum;

    public int cooldownMinus;

    public EquipmentItemBase(CategoryEnum categoryEnum) {
        super(EnumType.EQUIPMENT, categoryEnum, 1);
        this.categoryEnum = categoryEnum;

    }


}
