package com.rumaruka.riskofmine.common.items.equipment;

import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.common.config.ROMConfig;
import com.rumaruka.riskofmine.init.ROMItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BlastShowerItem extends EquipmentItemBase {
    private EffectType category;

    public BlastShowerItem() {
        super(                CategoryEnum.UTILITY  );
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
            tooltip.add(new TranslationTextComponent("ror.blast_shower.info"));
        }
    }


    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {

        ItemStack stack = playerIn.getItemInHand(handIn);

        if (!worldIn.isClientSide) {
            playerIn.curePotionEffects(stack);

            if (!playerIn.abilities.instabuild) {

                for (int i = 0; i < playerIn.inventory.getContainerSize(); i++) {
                    ItemStack itemStack = playerIn.inventory.getItem(i);
                    if (itemStack.getItem() == ROMItems.ALIEN_HEAD) {
                        playerIn.removeAllEffects();
                        removeNegativeEffect(playerIn);
                        playerIn.getCooldowns().addCooldown(this, ROMConfig.General.cooldownEq.get() - ROMItems.ALIEN_HEAD.cooldownMinus);
                        MinecraftForge.EVENT_BUS.register(new ProjectileRemoveEvent());
                    }

                }
                playerIn.removeAllEffects();
                removeNegativeEffect(playerIn);
                MinecraftForge.EVENT_BUS.register(new ProjectileRemoveEvent());
                playerIn.getCooldowns().addCooldown(this, ROMConfig.General.cooldownEq.get());
            }
        }

        MinecraftForge.EVENT_BUS.unregister(new ProjectileRemoveEvent());
        return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getItemInHand(handIn));
    }


    private void removeNegativeEffect(LivingEntity entity) {
        List<Effect> potions = new ArrayList<>();
        potions.addAll(entity.getActiveEffectsMap().keySet());
        potions.stream().filter(potion -> isBadEffect()).forEach(entity::removeEffect);
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @javax.annotation.Nullable net.minecraft.nbt.CompoundNBT nbt) {
        return new net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper(stack);
    }

    public boolean isBadEffect() {
        return this.category == EffectType.HARMFUL;
    }

    @Mod.EventBusSubscriber(modid = RiskOfMine.MODID)
    public static class ProjectileRemoveEvent {
        @SubscribeEvent
        public static void onManipulationProjectiles(ProjectileImpactEvent event) {
            PlayerEntity playerEntity = Minecraft.getInstance().player;
            ProjectileEntity projectileEntity = (ProjectileEntity) event.getEntity();
            assert playerEntity != null;
            if (playerEntity.level.isClientSide) {
                projectileEntity.remove();
            }
        }
    }

}
