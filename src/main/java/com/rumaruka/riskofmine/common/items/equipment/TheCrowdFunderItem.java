package com.rumaruka.riskofmine.common.items.equipment;

import com.rumaruka.riskofmine.api.CategoryEnum;

import com.rumaruka.riskofmine.common.cap.money.ROMMoney;
import com.rumaruka.riskofmine.common.cap.money.data.Money;
import com.rumaruka.riskofmine.common.entity.bullets.EntityGoldenIngotBullets;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class TheCrowdFunderItem extends EquipmentItemBase {
    public TheCrowdFunderItem() {
        super(CategoryEnum.DAMAGE);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World level, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new TranslationTextComponent("ror.alt.info"));
        if (Screen.hasAltDown()) {

            tooltip.add(new TranslationTextComponent("riskofmine.rarity" + ":"));
            tooltip.add(new TranslationTextComponent((getColor() + getTypeName())));
            tooltip.add(new TranslationTextComponent("riskofmine.category" + ":"));
            tooltip.add(new TranslationTextComponent((getColors() + getCategoryName())));
        }
        tooltip.add(new TranslationTextComponent("ror.shiftpress.info"));
        if (Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("ror.the_crowdfunder.info"));
        }
    }


    @Override
    public void releaseUsing(ItemStack stack, World level, LivingEntity livingEntity, int time) {
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity) livingEntity;
            int i = this.getUseDuration(stack) - time;

            float f = getPowerForCharge(i);
            ROMMoney romMoney = ROMMoney.from(playerentity);
            Money money = romMoney.money;
            if (!level.isClientSide) {

                if(!playerentity.abilities.instabuild&&money.getCurrentMoney()>0){
                    money.consumeMoney(playerentity,0.5f);
                }
            }

        }
    }
    

    public static float getPowerForCharge(int charge) {
        float f = (float) charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

            return ActionResult.consume(itemstack);

    }

    @Override
    public UseAction getUseAnimation( ItemStack p_77661_1_) {
        return UseAction.BOW;
    }

    public EntityGoldenIngotBullets bullets(EntityGoldenIngotBullets bullets) {
        return bullets;
    }


}
