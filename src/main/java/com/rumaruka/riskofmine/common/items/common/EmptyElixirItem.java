package com.rumaruka.riskofmine.common.items.common;

import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.api.EnumType;
import com.rumaruka.riskofmine.common.items.ItemCollectiblesBase;

public class EmptyElixirItem extends ItemCollectiblesBase {
    public EmptyElixirItem() {
        super(EnumType.COMMON, CategoryEnum.SCRAP, 64);
    }
}
