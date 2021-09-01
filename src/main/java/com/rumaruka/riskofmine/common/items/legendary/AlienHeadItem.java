package com.rumaruka.riskofmine.common.items.legendary;

import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.api.EnumType;
import com.rumaruka.riskofmine.common.items.ItemCollectiblesBase;
import com.rumaruka.riskofmine.utils.ROMUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class AlienHeadItem extends ItemCollectiblesBase {

    public AlienHeadItem() {
        super(EnumType.LEGENDARY, CategoryEnum.UTILITY, 64);
//        this.cooldownMinus =1800;
        this.cooldownMinus = ROMUtils.getStack(this).getCount() * 100;

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
            tooltip.add(new TranslationTextComponent("ror.alien_head.info"));
            tooltip.add(new TranslationTextComponent("[Stacks:" + stack.getCount() + "]"));
        }
    }


}
