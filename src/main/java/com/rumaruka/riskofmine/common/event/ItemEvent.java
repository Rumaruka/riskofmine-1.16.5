package com.rumaruka.riskofmine.common.event;

import com.rumaruka.riskofmine.common.config.ModConfig;
import com.rumaruka.riskofmine.common.entity.HealthOrbEntity;
import com.rumaruka.riskofmine.init.*;
import com.rumaruka.riskofmine.utils.ROMUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.UUID;

@Mod.EventBusSubscriber
public class ItemEvent {
    private static final UUID SPEED_MODIFIER_SPRINTING_UUID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");

    /**
     * onEntityDeath worked code !!
     */
    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        /**
         Player kill Entity
         */
        if (event.getSource().getEntity() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getSource().getEntity();
            LivingEntity livingEntity = event.getEntityLiving();
            World world = livingEntity.level;

            if (!world.isClientSide) {

                if (player != null) {

                    if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.MONSTER_TOOTH, player).isPresent()) {
                        ItemStack curiosStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.MONSTER_TOOTH, player).get().right;
                        world.addFreshEntity(new HealthOrbEntity(world, livingEntity.getX() + 0.5d, livingEntity.getY() + 0.5d, livingEntity.getZ() + 0.5d, curiosStack.getCount() / 2));

                    }
                    for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                        ItemStack itemStack = player.inventory.getItem(i);
                        if (itemStack.getItem() == ROMItems.MONSTER_TOOTH) {
                            world.addFreshEntity(new HealthOrbEntity(world, livingEntity.getX() + 0.5d, livingEntity.getY() + 0.5d, livingEntity.getZ() + 0.5d, itemStack.getCount() / 2));
                        }
                    }
                }

            }
        }
        /**
         Entity kill Player
         */
        if (event.getSource().getEntity() instanceof CreatureEntity && event.getEntityLiving() instanceof ServerPlayerEntity) {
            CreatureEntity livingEntity = (CreatureEntity) event.getSource().getEntity();
            ServerPlayerEntity player = (ServerPlayerEntity) event.getEntityLiving();
            World world = player.level;
            if (!world.isClientSide) {
                if (event.getSource().isBypassInvul()) {
                    return;
                } else {
                    if (livingEntity != null) {
                        for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                            ItemStack itemStack = player.inventory.getItem(i);
                            if (itemStack.getItem() == ROMItems.DIO_BEST_FRIEND) {
                                if (player.isDeadOrDying() || player.getHealth() < 2.5f) {
                                    Minecraft.getInstance().gameRenderer.displayItemActivation(ROMUtils.whereMyBestFriend(player));
                                    player.setHealth(player.getMaxHealth());
                                    player.removeAllEffects();
                                    player.addEffect(new EffectInstance(Effects.REGENERATION, 1800, 2));
                                    player.addEffect(new EffectInstance(Effects.ABSORPTION, 200, 2));
                                    player.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 1600, 1));
                                    player.addEffect(new EffectInstance(Effects.JUMP, 600, 1));
                                    player.level.broadcastEntityEvent(player, (byte) 35);


                                    itemStack.shrink(1);


                                }
                            }


                        }
                        if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.DIO_BEST_FRIEND, player).isPresent()) {
                            ItemStack curiosStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.DIO_BEST_FRIEND, player).get().right;
                            if (curiosStack.getItem() == ROMItems.DIO_BEST_FRIEND) {
                                if (player.isDeadOrDying() || player.getHealth() < 2.5f) {

                                    player.setHealth(player.getMaxHealth());
                                    player.removeAllEffects();
                                    player.addEffect(new EffectInstance(Effects.REGENERATION, 1800, 2));
                                    player.addEffect(new EffectInstance(Effects.ABSORPTION, 200, 2));
                                    player.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 1600, 1));
                                    player.addEffect(new EffectInstance(Effects.JUMP, 600, 1));
                                    player.level.broadcastEntityEvent(player, (byte) 35);

                                    Minecraft.getInstance().gameRenderer.displayItemActivation(ROMUtils.whereMyBestFriendInCurio(player));
                                    curiosStack.shrink(1);
                                }

                            }
                        }


                    }
                }


            }
        }
    }

    /**
     * onEntityHurt worked code !!
     * priority = EventPriority.LOW
     * If item use hurt and damage add only this method
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onEntityHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getSource().getEntity();
            LivingEntity livingEntity = event.getEntityLiving();
            World world = livingEntity.level;
            if (!world.isClientSide) {
                if (player != null) {
                    for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                        ItemStack itemStack = player.inventory.getItem(i);
                        if (itemStack.getItem() == ROMItems.GASOLINE && event.getEntity() instanceof CreatureEntity) {
                            event.getEntity().setRemainingFireTicks(itemStack.getCount() * 20);
                        }
                    }


                    if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.GASOLINE, player).isPresent()) {
                        ItemStack curioStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.GASOLINE, player).get().right;
                        if (event.getEntity() instanceof CreatureEntity) {
                            event.getEntity().setRemainingFireTicks(curioStack.getCount() * 20);
                        }
                    }
                }
                if (player != null) {
                    for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                        ItemStack itemStack = player.inventory.getItem(i);
                        if (itemStack.getItem() == ROMItems.CROWBAR && event.getEntity() instanceof CreatureEntity) {
                            if (((CreatureEntity) event.getEntity()).getHealth() > (((CreatureEntity) event.getEntity()).getMaxHealth() * 90 / 100)) {

                                event.getEntity().hurt(DamageSource.ANVIL, (float) (itemStack.getCount() * 1.00115d));
                            }
                        }
                    }
                    if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.CROWBAR, player).isPresent()) {
                        ItemStack curioStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.CROWBAR, player).get().right;
                        if (curioStack.getItem() == ROMItems.CROWBAR && event.getEntity() instanceof CreatureEntity) {
                            if (((CreatureEntity) event.getEntity()).getHealth() > (((CreatureEntity) event.getEntity()).getMaxHealth() * 90 / 100)) {

                                event.getEntity().hurt(DamageSource.ANVIL, (float) (curioStack.getCount() * 1.00115d));
                            }
                        }
                    }
                }
                if (player != null) {
                    for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                        ItemStack itemStack = player.inventory.getItem(i);
                        if (itemStack.getItem() == ROMItems.ARMOR_PIERCING_ROUNDS && (event.getEntity() instanceof WitherEntity || event.getEntity() instanceof EnderDragonEntity || !event.getEntity().canChangeDimensions())) {
                            event.getEntity().hurt(DamageSource.ANVIL, itemStack.getCount() * 2 - 1);
                        }
                    }
                    if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.ARMOR_PIERCING_ROUNDS, player).isPresent()) {
                        ItemStack curioStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.ARMOR_PIERCING_ROUNDS, player).get().right;
                        if (curioStack.getItem() == ROMItems.ARMOR_PIERCING_ROUNDS && event.getEntity() instanceof CreatureEntity) {
                            if (curioStack.getItem() == ROMItems.ARMOR_PIERCING_ROUNDS && (event.getEntity() instanceof WitherEntity || event.getEntity() instanceof EnderDragonEntity || !event.getEntity().canChangeDimensions())) {
                                event.getEntity().hurt(DamageSource.ANVIL, curioStack.getCount() * 2 - 1);
                            }
                        }
                    }
                }
                if (player != null) {
                    for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                        ItemStack itemStack = player.inventory.getItem(i);
                        if (itemStack.getItem() == ROMItems.CHRONOBAUBLE && event.getEntity() instanceof CreatureEntity) {
                            ((CreatureEntity) event.getEntity()).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, itemStack.getCount() * 2, 4));
                        }
                    }
                    if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.CHRONOBAUBLE, player).isPresent()) {
                        ItemStack curioStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.CHRONOBAUBLE, player).get().right;
                        if (curioStack.getItem() == ROMItems.CHRONOBAUBLE && event.getEntity() instanceof CreatureEntity) {
                            if (curioStack.getItem() == ROMItems.CHRONOBAUBLE && event.getEntity() instanceof CreatureEntity) {
                                ((CreatureEntity) event.getEntity()).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, curioStack.getCount() * 2, 4));
                            }
                        }
                    }
                }
                if (player != null) {
                    for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                        ItemStack itemStack = player.inventory.getItem(i);
                        if (itemStack.getItem() == ROMItems.TRI_TIP_DAGGER && event.getEntity() instanceof CreatureEntity) {
                            ((CreatureEntity) event.getEntity()).addEffect(new EffectInstance(ROMEffects.BLEED.get(), ModConfig.durBleedConfig.get() * itemStack.getCount(), 2, true, false));
                        }
                    }
                    if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.TRI_TIP_DAGGER, player).isPresent()) {
                        ItemStack curioStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.TRI_TIP_DAGGER, player).get().right;
                        if (curioStack.getItem() == ROMItems.TRI_TIP_DAGGER && event.getEntity() instanceof CreatureEntity) {
                            if (curioStack.getItem() == ROMItems.TRI_TIP_DAGGER && event.getEntity() instanceof CreatureEntity) {
                                ((CreatureEntity) event.getEntity()).addEffect(new EffectInstance(ROMEffects.BLEED.get(), ModConfig.durBleedConfig.get() * curioStack.getCount(), 2, true, false));
                            }
                        }
                    }
                    if (event.getEntity() instanceof CreatureEntity) {
                        if (((CreatureEntity) event.getEntity()).hasEffect(ROMEffects.BLEED.get())) {
                            event.getEntity().hurt(DamageSource.ANVIL, 4.0f);
                        }
                    }
                }
                if (player != null) {
                    for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                        ItemStack itemStack = player.inventory.getItem(i);
                        if (itemStack.getItem() == ROMItems.STUN_GRENADE && event.getEntity() instanceof CreatureEntity) {
                            ((CreatureEntity) event.getEntity()).addEffect(new EffectInstance(ROMEffects.STUN.get(), ROMUtils.setDurOld(ModConfig.durStunConfig.get() * itemStack.getCount()), 2, true, false));
                        }
                    }
                    if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.STUN_GRENADE, player).isPresent()) {
                        ItemStack curioStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.STUN_GRENADE, player).get().right;
                        if (curioStack.getItem() == ROMItems.STUN_GRENADE && event.getEntity() instanceof CreatureEntity) {
                            if (curioStack.getItem() == ROMItems.STUN_GRENADE && event.getEntity() instanceof CreatureEntity) {
                                ((CreatureEntity) event.getEntity()).addEffect(new EffectInstance(ROMEffects.STUN.get(), ROMUtils.setDurOld(ModConfig.durStunConfig.get() * curioStack.getCount()), 2, true, false));
                            }
                        }
                    }

                }
                if (player != null) {
                    for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                        ItemStack itemStack = player.inventory.getItem(i);
                        if (itemStack.getItem() == ROMItems.STICKY_BOMB && event.getEntity() instanceof CreatureEntity) {
                            event.getEntity().level.explode(event.getEntity(), event.getEntity().getX(), event.getEntity().getY(0.0625D), event.getEntity().getZ(), 4.0F, Explosion.Mode.BREAK);
                        }
                    }
                    if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.STICKY_BOMB, player).isPresent()) {
                        ItemStack curioStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.STUN_GRENADE, player).get().right;
                        if (curioStack.getItem() == ROMItems.STICKY_BOMB && event.getEntity() instanceof CreatureEntity) {
                            if (curioStack.getItem() == ROMItems.STICKY_BOMB && event.getEntity() instanceof CreatureEntity) {
                                event.getEntity().level.explode(event.getEntity(), event.getEntity().getX(), event.getEntity().getY(0.0625D), event.getEntity().getZ(), 4.0F, Explosion.Mode.BREAK);

                            }
                        }
                    }

                }
            }
        }

    }

    /**
     * onEntityUpdate worked code !!
     * If item use updating entity
     */
    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        PlayerEntity player = Minecraft.getInstance().player;
        if (event.getEntityLiving() instanceof CreatureEntity) {
            LivingEntity livingEntity = event.getEntityLiving();
            World world = livingEntity.level;
            if (!world.isClientSide) {
                if (player != null) {
                    for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                        ItemStack itemStack = player.inventory.getItem(i);
                        if (itemStack.getItem() == ROMItems.FOCUS_CRYSTAL) {
                            float distance = player.distanceTo(livingEntity);
                            if (distance <= 3.5) {
                                livingEntity.hurt(DamageSource.ANVIL, itemStack.getCount());
                                Minecraft.getInstance().particleEngine.createTrackingEmitter(livingEntity, ROMParticles.FOCUS_CRYSTAL.get());
                            }
                        }
                    }

                    if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.FOCUS_CRYSTAL, player).isPresent()) {
                        ItemStack curioStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.FOCUS_CRYSTAL, player).get().right;
                        if (curioStack.getItem() == ROMItems.FOCUS_CRYSTAL) {

                            float distance = player.distanceTo(livingEntity);
                            if (distance <= 3.5) {
                                livingEntity.hurt(DamageSource.ANVIL, curioStack.getCount());
                                Minecraft.getInstance().particleEngine.createTrackingEmitter(livingEntity, ROMParticles.FOCUS_CRYSTAL.get());
                            }
                        }
                    }


                }
                if (player != null) {
                    if (event.getEntityLiving().hasEffect(ROMEffects.STUN.get()) || player.hasEffect(ROMEffects.STUN.get())) {
                        Minecraft.getInstance().particleEngine.createTrackingEmitter(livingEntity, ROMParticles.STUN_PARTICLES.get(), 5);

                    }
                }

            }
        }
    }

    /**
     * onPlayerHurt  - for hurt player event
     *
     * @param event
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onPlayerHurt(LivingHurtEvent event) {

        if (event.getSource().getEntity() instanceof CreatureEntity && event.getEntityLiving() instanceof ServerPlayerEntity) {

            ServerPlayerEntity player = (ServerPlayerEntity) event.getEntityLiving();
            World world = player.level;
            if (!world.isClientSide) {

                for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                    ItemStack itemStack = player.inventory.getItem(i);
                    if (itemStack.getItem() == ROMItems.OLD_WAR_STEALTHKIT) {
                        if (player.getHealth() >= 2.5f) {
                            player.addEffect(new EffectInstance(Effects.INVISIBILITY, 1000, 1, false, false));
                            player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 1000, 1, false, false));
                        }

                    }

                }
                if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.OLD_WAR_STEALTHKIT, player).isPresent()) {
                    ItemStack curioStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.OLD_WAR_STEALTHKIT, player).get().right;
                    if (curioStack.getItem() == ROMItems.OLD_WAR_STEALTHKIT) {
                        if (curioStack.getItem() == ROMItems.OLD_WAR_STEALTHKIT) {
                            if (player.getHealth() >= 2.5f) {
                                player.addEffect(new EffectInstance(Effects.INVISIBILITY, 1000, 1, false, false));
                                player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 1000, 1, false, false));
                            }
                        }
                    }
                }

            }


        }
    }

/*
    @SubscribeEvent
    public void onXpLevelUp(PlayerXpEvent.LevelChange event) {

        PlayerEntity player = event.getPlayer();
        int levels = event.getLevels();
        player.experienceLevel += levels;
        World level = player.level;
        BlockPos pos = player.blockPosition();
        if (levels > 0 && player.experienceLevel % 2 == 0) {


            player.level.playSound(null, player.getX(), player.getY(), player.getZ(), ROMSounds.ROM_PLAYER_LEVEL_UP.get(), SoundCategory.MASTER, 1F, 1.0F);
            for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                ItemStack itemStack = player.inventory.getItem(i);
                if (itemStack.getItem() == ROMItems.WARBANNER) {
                    BlockState state = level.getBlockState(pos);
                    Block block = state.getBlock();
                    if (block == ROMBlocks.WAR_BANNER_BLOCK) {
                        level.setBlock(pos, state, 4);
                    }
                }
            }
        }

    }
*/
//TODO: EntityMissiles and Fireworks
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void attacksDronesFireworksMissiles(LivingAttackEvent event) {

    }


}








