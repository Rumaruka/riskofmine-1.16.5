package com.rumaruka.riskofmine.common.items.equipment;

import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.api.EnumType;
import com.rumaruka.riskofmine.common.items.ItemCollectiblesBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

import java.util.function.Predicate;

public abstract class EquipmentShootItemBase extends ItemCollectiblesBase {
    private final CategoryEnum categoryEnum;
    public static final Predicate<ItemStack> GOLDEN_INGOT = (predicate)->{
        return predicate.getItem()== Items.GOLD_INGOT;
    };
    public EquipmentShootItemBase(CategoryEnum categoryEnum) {
        super(EnumType.EQUIPMENT, categoryEnum, 1);
        this.categoryEnum = categoryEnum;

    }

    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return this.getAllSupportedProjectiles();
    }

    public abstract Predicate<ItemStack> getAllSupportedProjectiles();

    public static ItemStack getHeldProjectile(LivingEntity livingEntity, Predicate<ItemStack> predicate) {
        if (predicate.test(livingEntity.getItemInHand(Hand.OFF_HAND))) {
            return livingEntity.getItemInHand(Hand.OFF_HAND);
        } else {
            return predicate.test(livingEntity.getItemInHand(Hand.MAIN_HAND)) ? livingEntity.getItemInHand(Hand.MAIN_HAND) : ItemStack.EMPTY;
        }
    }

    public int getEnchantmentValue() {
        return 1;
    }

    public abstract int getDefaultProjectileRange();

}
