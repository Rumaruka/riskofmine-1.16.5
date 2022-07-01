package com.rumaruka.riskofmine.common.items.voiditems;

import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.common.items.uncommon.ChronobaubleItem;
import com.rumaruka.riskofmine.init.ROMItems;
import com.rumaruka.riskofmine.init.ROMSounds;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TentabaubleItem extends ItemVoidBase {
    public TentabaubleItem() {
        super(CategoryEnum.UTILITY, 10);
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
            tooltip.add(new TranslationTextComponent("ror.tenta.info"));
            tooltip.add(new TranslationTextComponent("[Stacks:" + stack.getCount() + "]"));
        }
    }

    @Override
    public void inventoryTick(ItemStack p_77663_1_, World level, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        if(p_77663_3_ instanceof PlayerEntity){

            for (int i = 0; i < ((PlayerEntity) p_77663_3_).inventory.getContainerSize(); i++) {
                ItemStack itemStack = ((PlayerEntity) p_77663_3_).inventory.getItem(i);
                if (itemStack.getItem() instanceof ChronobaubleItem) {
                    level.playSound(null,new BlockPos(p_77663_3_.getX(),p_77663_3_.getY(),p_77663_3_.getZ()), ROMSounds.UI_VOID_REPLACE_ITEM.get(), SoundCategory.MASTER, 2.0F, 1.0F);
                    replaceItem(itemStack, p_77663_1_);
                }
            }

        }

    }


}
