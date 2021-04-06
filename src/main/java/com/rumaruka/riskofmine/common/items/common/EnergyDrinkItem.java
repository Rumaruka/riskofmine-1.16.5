package com.rumaruka.riskofmine.common.items.common;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.api.EnumType;
import com.rumaruka.riskofmine.common.items.ItemCollectiblesBase;
import com.rumaruka.riskofmine.utils.ROMMathUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class EnergyDrinkItem extends ItemCollectiblesBase implements ICurioItem {
    private static final UUID SPEED_MODIFIER_SPRINTING_UUID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");

    public EnergyDrinkItem() {
        super(EnumType.COMMON, CategoryEnum.UTILITY, 64);
    }

    @Override
    public void inventoryTick(ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {


        if (!p_77663_2_.isClientSide()) {
            PlayerEntity playerEntity = (PlayerEntity) p_77663_3_;
            if (playerEntity.getOffhandItem().getItem() == this) {
                playerEntity.getAttributes().addTransientAttributeModifiers(this.getAttributeModifiers(EquipmentSlotType.OFFHAND, p_77663_1_));

            }

        }


        super.inventoryTick(p_77663_1_, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
    }


    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        final Multimap<Attribute, AttributeModifier> defaultModifiers;

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        if (slot == EquipmentSlotType.OFFHAND) {
            builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_MODIFIER_SPRINTING_UUID, "Speed",Math.abs(Math.tan(Math.PI*stack.getCount()/ 180)) , AttributeModifier.Operation.ADDITION));
        }

        defaultModifiers = builder.build();
        return defaultModifiers;
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (!livingEntity.level.isClientSide()) {
            PlayerEntity playerEntity = (PlayerEntity) livingEntity;
            playerEntity.getAttributes().addTransientAttributeModifiers(this.getAttributeModifiers(identifier, stack));
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack) {
        final Multimap<Attribute, AttributeModifier> defaultModifiers;

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

        builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_MODIFIER_SPRINTING_UUID, "Speed", Math.abs(Math.tan(Math.PI*stack.getCount()/ 180)), AttributeModifier.Operation.ADDITION));
        defaultModifiers = builder.build();
        return defaultModifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent((getColor() + getTypeName())));
        tooltip.add(new TranslationTextComponent("ror.shiftpress.info"));
        if (Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("ror.energydrink.info"));
            tooltip.add(new TranslationTextComponent("[Stacks:" + stack.getCount() + "]"));
        }
    }

}
