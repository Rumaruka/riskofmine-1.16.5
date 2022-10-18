package com.rumaruka.riskofmine.common.items.voiditems;

import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.common.items.common.BustlingFungusItem;
import com.rumaruka.riskofmine.common.items.uncommon.ChronobaubleItem;
import com.rumaruka.riskofmine.events.MovingHandler;
import com.rumaruka.riskofmine.init.ROMItems;
import com.rumaruka.riskofmine.init.ROMSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;



public class WeepingFungusItem extends ItemVoidBase{
    public WeepingFungusItem() {
        super(CategoryEnum.HEALING, 15);
    }


    @Override
    public void inventoryTick(ItemStack p_77663_1_, World level, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        if(p_77663_3_ instanceof PlayerEntity ){

            for (int i = 0; i < ((PlayerEntity) p_77663_3_).inventory.getContainerSize(); i++) {
                ItemStack itemStack = ((PlayerEntity) p_77663_3_).inventory.getItem(i);
                if (itemStack.getItem() instanceof BustlingFungusItem) {
                    level.playSound(null,new BlockPos(p_77663_3_.getX(),p_77663_3_.getY(),p_77663_3_.getZ()), ROMSounds.UI_VOID_REPLACE_ITEM.get(), SoundCategory.MASTER, 2.0F, 1.0F);
                    replaceItem(itemStack, p_77663_1_);
                }
            }
            if(!level.isClientSide()){
                if (MovingHandler.isMoving((ServerPlayerEntity) p_77663_3_)) {

                    ((PlayerEntity) p_77663_3_).heal((p_77663_1_.getCount() + 0.045f) / 20f);

                }
            }
        }

    }

}
