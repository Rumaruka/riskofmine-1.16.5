package com.rumaruka.riskofmine.common.items.common;

import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.api.EnumType;
import com.rumaruka.riskofmine.common.items.ItemCollectiblesBase;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class FocusCrystalItem extends ItemCollectiblesBase {
    public FocusCrystalItem() {
        super(EnumType.COMMON, CategoryEnum.DAMAGE, 64);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("ror.alt.info"));
        if (Screen.hasAltDown()) {

            tooltip.add(new TranslationTextComponent("riskofmine.rarity" + ":"));
            tooltip.add(new TranslationTextComponent((getColor() + getTypeName())));
            tooltip.add(new TranslationTextComponent("riskofmine.category" + ":"));
            tooltip.add(new TranslationTextComponent((getColors() + getCategoryName())));
        }
        tooltip.add(new TranslationTextComponent("ror.shiftpress.info"));
        if (Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("ror.focus_crystal.info"));
            tooltip.add(new TranslationTextComponent("[Stacks:" + stack.getCount() + "]"));
        }
    }


}
