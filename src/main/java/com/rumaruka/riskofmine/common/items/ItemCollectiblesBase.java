package com.rumaruka.riskofmine.common.items;

import com.rumaruka.riskofmine.ModSetup;
import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.api.EnumType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class ItemCollectiblesBase extends Item implements ICurioItem {
    private final EnumType type;
    private final CategoryEnum categoryEnum;

    private final int sizeStack;
    public int cooldownMinus;

    public ItemCollectiblesBase(EnumType type, CategoryEnum category,int size) {
        super(new Properties().tab(ModSetup.ITEM_GROUP).stacksTo(size));
        this.type = type;
        this.categoryEnum = category;

        this.sizeStack = size;
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return sizeStack;
    }
    
    public int getSizeStack() {
        return sizeStack;
    }

    public EnumType getType() {
        return type;
    }

    public String getTypeName() {
        return type.getName();
    }

    public TextFormatting getColor() {
        return type.getChatColor();
    }

    public TextFormatting getColors() {
        return categoryEnum.getChatColor();
    }

    public String getCategoryName() {
        return categoryEnum.getName();
    }


}
