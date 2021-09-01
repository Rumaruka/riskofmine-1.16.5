package com.rumaruka.riskofmine.common.items.common;

import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.api.EnumType;
import com.rumaruka.riskofmine.common.items.ItemCollectiblesBase;
import com.rumaruka.riskofmine.events.MovingHandler;
import com.rumaruka.riskofmine.init.ROMItems;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class BustlingFungusItem extends ItemCollectiblesBase {
    public BustlingFungusItem() {
        super(EnumType.COMMON, CategoryEnum.HEALING, 64);
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
            tooltip.add(new TranslationTextComponent("ror.bf.info"));
            tooltip.add(new TranslationTextComponent("[Stacks:" + stack.getCount() + "]"));
        }
    }
    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!worldIn.isClientSide()) {
            if (!MovingHandler.isMoving((ServerPlayerEntity) entityIn)) {

                ((ServerPlayerEntity) entityIn).heal((stack.getCount() + 0.045f) / 20f);

            }

        }
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean canRightClickEquip(ItemStack stack) {
        return true;
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof ServerPlayerEntity) {
            if (!livingEntity.level.isClientSide()) {
                if (!MovingHandler.isMoving((ServerPlayerEntity) livingEntity)) {

                    livingEntity.heal((stack.getCount() + 0.045f) / 20f);

                }
            }
        }
    }

    @Override
    public boolean canEquip(String identifier, LivingEntity livingEntity, ItemStack stack) {
        return !CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.BUSTLING_FUNGUS, livingEntity)
                .isPresent();
    }


    @Override
    public boolean canSync(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        return true;
    }


}
