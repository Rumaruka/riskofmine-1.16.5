package com.rumaruka.riskofmine.common.event;

import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.common.cap.shields.ROMShields;
import com.rumaruka.riskofmine.common.cap.shields.data.Shields;
import com.rumaruka.riskofmine.init.ROMItems;
import com.rumaruka.riskofmine.utils.ROMUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(modid = RiskOfMine.MODID)
public class ShieldsEvent {


    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {

        if (event.getSource().getEntity() instanceof MobEntity && event.getEntityLiving() instanceof ServerPlayerEntity) {

            ServerPlayerEntity player = (ServerPlayerEntity) event.getEntityLiving();
            MobEntity mob = (MobEntity) event.getSource().getEntity();
            World world = player.level;
            if (!world.isClientSide) {

                for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                    ItemStack itemStack = player.inventory.getItem(i);
                    if (itemStack.getItem() == ROMItems.TOPAZ_BROOCH) {
                        ROMShields romShields = ROMShields.from(player);
                        Shields shields = romShields.shields;


                        shields.addShields(player, -1);
                        romShields.detectAndSendChanges();
                        event.setCanceled(true);


                    }

                }
                if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.OLD_WAR_STEALTHKIT, player).isPresent()) {
                    ItemStack curioStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.OLD_WAR_STEALTHKIT, player).get().right;
                    if (curioStack.getItem() == ROMItems.OLD_WAR_STEALTHKIT) {
                        if (curioStack.getItem() == ROMItems.TOPAZ_BROOCH) {
                            ROMUtils.maybeDisableShield(player, mob.getMainHandItem(), curioStack);
                            player.getCooldowns().addCooldown(ROMItems.TOPAZ_BROOCH, 10);
                            event.setCanceled(true);
                        }
                    }
                }

            }


        }
    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        Entity entity = event.getSource().getEntity();
        if (entity instanceof ServerPlayerEntity) {
            ROMShields romShields = ROMShields.from((PlayerEntity) entity);
            Shields shields = romShields.shields;
            shields.addShields((PlayerEntity) entity, 10.0f);

            romShields.detectAndSendChanges();
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

    }
}
