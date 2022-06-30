package com.rumaruka.riskofmine.api;

import com.rumaruka.riskofmine.common.items.ItemCollectiblesBase;
import net.minecraft.item.ItemStack;

public interface IVoidItem {


    void replaceItem(ItemStack uncorruted, ItemStack corruted);
}
