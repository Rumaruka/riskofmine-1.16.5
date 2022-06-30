package com.rumaruka.riskofmine.common.items.voiditems;

import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.api.EnumType;
import com.rumaruka.riskofmine.api.IVoidItem;
import com.rumaruka.riskofmine.common.items.ItemCollectiblesBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemVoidBase extends ItemCollectiblesBase implements IVoidItem {


    public ItemVoidBase(CategoryEnum category, int size) {
        super(EnumType.VOID, category, size);

    }
    @Override
    public void replaceItem(ItemStack uncorruted, ItemStack corruted) {
        uncorruted.shrink(1);
        corruted.shrink(-1);

    }

}


