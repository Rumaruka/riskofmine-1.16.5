package com.rumaruka.riskofmine.utils;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.common.entity.bullets.EntityGoldenIngotBullets;
import com.rumaruka.riskofmine.common.items.equipment.EquipmenShootItemBase;
import com.rumaruka.riskofmine.common.tiles.CommonChestTE;
import com.rumaruka.riskofmine.init.ROMBlocks;
import com.rumaruka.riskofmine.init.ROMItems;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.MixinEnvironment;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

import static net.minecraft.world.gen.feature.structure.StructurePiece.reorient;


public class ROMUtils {
    public static Minecraft minecraft = Minecraft.getInstance();

    private final List<CategoryEnum> categoryEnum = Lists.newArrayList();

    public static int durOld;

    public static ItemStack whereMyBestFriend(PlayerEntity player) {

        for (int i = 0; i < player.inventory.getContainerSize(); i++) {
            ItemStack itemStack = player.inventory.getItem(i);
            if (itemStack.getItem() == ROMItems.DIO_BEST_FRIEND) {
                return itemStack;
            }
        }

        return new ItemStack(ROMItems.DIO_BEST_FRIEND);
    }

    public static ItemStack whereMyBestFriendInCurio(PlayerEntity player) {


        if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.DIO_BEST_FRIEND, player).isPresent()) {
            ItemStack curiosStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.DIO_BEST_FRIEND, player).get().right;
            if (curiosStack.getItem() == ROMItems.DIO_BEST_FRIEND) {
                return curiosStack;
            }
        }

        return new ItemStack(ROMItems.DIO_BEST_FRIEND);
    }

    public static ItemStack getStack(Item item) {
        return new ItemStack(item);
    }

    public boolean hasCategory(CategoryEnum categoryEnum) {
        return this.categoryEnum.contains(categoryEnum);
    }


    public static int getDurOld() {
        return durOld;
    }

    /**
     * set the movespeed used for the new AI system
     */
    public static int setDurOld(int durNew) {
        return durOld = durNew;
    }


    public static void sendMessage(String msg) {
        PlayerEntity player = minecraft.player;

        if (player != null) {
            player.sendMessage(new TranslationTextComponent(msg), UUID.randomUUID());

        }

    }

    public boolean checkItemInInventory(ServerPlayerEntity player, Item item) {
        for (int i = 0; i < player.inventory.getContainerSize(); i++) {
            ItemStack itemStack = player.inventory.getItem(0);
            if (itemStack.getItem() == item) {
                return true;
            }
        }

        return false;
    }

    public boolean checkItemInCurios(ServerPlayerEntity player, Item item) {
        if (CuriosApi.getCuriosHelper().findEquippedCurio(item, player).isPresent()) {
            ItemStack curiosStack = CuriosApi.getCuriosHelper().findEquippedCurio(item, player).get().right;
            return curiosStack.getItem() == item;
        }
        return false;
    }

    public static ItemStack getBullets(PlayerEntity player, ItemStack stack) {
        if (!(stack.getItem() instanceof EquipmenShootItemBase)) {
            return ItemStack.EMPTY;
        } else {
            Predicate<ItemStack> predicate = ((EquipmenShootItemBase)stack.getItem()).getSupportedHeldProjectiles();
            ItemStack itemstack = EquipmenShootItemBase.getHeldProjectile(player, predicate);
            if (!itemstack.isEmpty()) {
                return itemstack;
            } else {
                predicate = ((EquipmenShootItemBase)stack.getItem()).getAllSupportedProjectiles();

                for(int i = 0; i < player.inventory.getContainerSize(); ++i) {
                    ItemStack itemstack1 = player.inventory.getItem(i);
                    if (predicate.test(itemstack1)) {
                        return itemstack1;
                    }
                }

                return player.abilities.instabuild ? new ItemStack(Items.GOLD_INGOT) : ItemStack.EMPTY;
            }
        }
    }
    public static EntityGoldenIngotBullets createBullets(World level, ItemStack stack, LivingEntity livingEntity) {
        EntityGoldenIngotBullets goldenIngotBullets = new EntityGoldenIngotBullets(level,livingEntity);
        goldenIngotBullets.setItem(stack);
        return goldenIngotBullets;
    }
}
