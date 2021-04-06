package com.rumaruka.riskofmine.utils;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.common.tiles.CommonChestTE;
import com.rumaruka.riskofmine.init.ROMBlocks;
import com.rumaruka.riskofmine.init.ROMItems;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.MixinEnvironment;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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


}
