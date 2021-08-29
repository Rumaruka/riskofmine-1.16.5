package com.rumaruka.riskofmine.common.items.gameplay;

import com.rumaruka.riskofmine.ModSetup;
import com.rumaruka.riskofmine.common.cap.lunar.ROMLunar;
import com.rumaruka.riskofmine.common.cap.lunar.data.Lunar;
import com.rumaruka.riskofmine.common.items.ItemCollectiblesBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class LunarCoinItem extends Item {
    public LunarCoinItem( ) {
        super(new Properties().tab(ModSetup.ITEM_GROUP));
    }

    @Override
    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        ItemStack itemStack = p_77659_2_.getItemInHand(p_77659_3_);
        ROMLunar romLunar = ROMLunar.from(p_77659_2_);
        Lunar lunar = romLunar.lunar;
        lunar.addLunar(p_77659_2_,1);
        romLunar.detectAndSendChanges();
        itemStack.shrink(1);
        return ActionResult.success(itemStack);
    }
}
