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

public class LunarCoinItem extends GamePlayItem {
    public LunarCoinItem( ) {
        super();

    }

    @Override
    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        ROMLunar romLunar = ROMLunar.from(player);
        Lunar lunar = romLunar.lunar;
        lunar.addLunar(player,1);
        romLunar.detectAndSendChanges();
        itemStack.shrink(1);
        return ActionResult.success(itemStack);
    }
}
