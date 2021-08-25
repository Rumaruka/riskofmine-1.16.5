package com.rumaruka.riskofmine.common.items.equipment;

import com.rumaruka.riskofmine.api.CategoryEnum;

import com.rumaruka.riskofmine.utils.ROMUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class TheCrowdFunderItem extends EquipmenShootItemBase {
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
            PlayerEntity player = (PlayerEntity) livingEntity;
            ItemStack itemStack = ROMUtils.getBullets(player, stack);
            int dur = getUseDuration(stack) - time;
            dur = ForgeEventFactory.onArrowLoose(stack, level, player, time, !itemStack.isEmpty());
            if (dur < 0) return;
            if (!itemStack.isEmpty()) {
                itemStack = new ItemStack(Items.GOLD_INGOT);
            }
            float f = getPowerForCharge(dur);
            if (!((double) f < 0.1D)) {
                boolean flag1 = player.abilities.instabuild || (itemStack.getItem() instanceof ArrowItem);
                if (!level.isClientSide) {
                    itemStack.getItem();
                    Item golditem = (Item) itemStack.getItem();
//                    EntityGoldenIngotBullets goldenIngotBullets = ROMUtils.createBullets(level, itemStack, player);
//                    goldenIngotBullets = bullets(goldenIngotBullets);
//                    goldenIngotBullets.shootFromRotation(player, player.xRot, player.yRot, 0.0F, f * 3.0F, 1.0F);
//                    if (f == 1.0F) {
//                        goldenIngotBullets.setCritArrow(true);
//                    }
//
//
//                    goldenIngotBullets.setBaseDamage(goldenIngotBullets.getBaseDamage() + 1);
//
//
//                    stack.hurtAndBreak(1, player, (p_220009_1_) -> {
//                        p_220009_1_.broadcastBreakEvent(player.getUsedItemHand());
//                    });
//                    if (flag1 || player.abilities.instabuild && (itemStack.getItem() == Items.SPECTRAL_ARROW || itemStack.getItem() == Items.TIPPED_ARROW)) {
//                        goldenIngotBullets.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
//                    }
//
//                    level.addFreshEntity(goldenIngotBullets);
                }

                level.playSound((PlayerEntity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                if (!flag1 && !player.abilities.instabuild) {
                    itemStack.shrink(1);
                    if (itemStack.isEmpty()) {
                        player.inventory.removeItem(itemStack);
                    }
                }

                player.awardStat(Stats.ITEM_USED.get(this));
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
        ItemStack stack = player.getItemInHand(hand);
        boolean hasBullets = ROMUtils.getBullets(player, stack).isEmpty();
        ActionResult<ItemStack> result = ForgeEventFactory.onArrowNock(stack, level, player, hand, hasBullets);
        if (result != null) return result;
        if (player.abilities.instabuild && !hasBullets) {
            return ActionResult.fail(stack);
        } else {
            player.startUsingItem(hand);

            return ActionResult.consume(stack);
        }
    }


//    public EntityGoldenIngotBullets bullets(EntityGoldenIngotBullets bullets) {
//        return bullets;
//    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return GOLDEN_INGOT;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 20;
    }
}
