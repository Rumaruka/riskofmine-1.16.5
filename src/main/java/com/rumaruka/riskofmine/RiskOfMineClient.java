package com.rumaruka.riskofmine;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rumaruka.riskofmine.api.mobs.EliteMobModifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

import java.io.File;
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid =RiskOfMine.MODID)
public class RiskOfMineClient {
    private static final ResourceLocation GUI_BARS_TEXTURES = new ResourceLocation("textures/gui/bars.png");
    private static int airOverrideValue = -999;
    private static final double NAME_VISION_DISTANCE = 32D;
    private static long airDisplayTimeout;
    private static Minecraft mc;
    private static long nextPacketTime;

    private static long healthBarRetainTime;
    private static LivingEntity retainedTarget;

    @SubscribeEvent
    public static void playerLoginToServer(ClientPlayerNetworkEvent.LoggedInEvent evt) {
        // client starting point, also local servers
        mc = Minecraft.getInstance();
        if (evt.getPlayer() != null) {
//            InfernalMobsCore.instance().initIfNeeded(evt.getPlayer().level);
        }
    }

    public static void load() {
        nextPacketTime = 0;
        healthBarRetainTime = 0;
        retainedTarget = null;
    }

    @SubscribeEvent
    public static void onEntityJoinedWorld(EntityJoinWorldEvent event) {
        if (event.getWorld().isClientSide && mc.player != null && (event.getEntity() instanceof MobEntity || (event.getEntity() instanceof LivingEntity && event.getEntity() instanceof IMob))) {
//            InfernalMobsCore.instance().networkHelper.sendPacketToServer(new MobModsPacket(mc.player.getName().getContents(), event.getEntity().getId(), (byte) 0));
            RiskOfMine.logger.debug("onEntityJoinedWorld {}, ent-id {} querying modifiers from server", event.getEntity(), event.getEntity().getId());
        }
    }

    private static void askServerMods(Entity ent) {
        if (System.currentTimeMillis() > nextPacketTime && (ent instanceof MobEntity || (ent instanceof LivingEntity && ent instanceof IMob))) {
//            InfernalMobsCore.instance().networkHelper.sendPacketToServer(new MobModsPacket(mc.player.getName().getContents(), ent.getId(), (byte) 0));
            RiskOfMine.logger.debug("askServerMods {}, ent-id {} querying modifiers from server", ent, ent.getId());
            nextPacketTime = System.currentTimeMillis() + 100L;
        }
    }

    private static void askServerHealth(Entity ent) {
        if (System.currentTimeMillis() > nextPacketTime) {
//            InfernalMobsCore.instance().networkHelper.sendPacketToServer(new HealthPacket(mc.player.getName().getContents(), ent.getId(), 0f, 0f));
            nextPacketTime = System.currentTimeMillis() + 100L;
        }
    }

    @SubscribeEvent
    public static void playerLoggedOut(ClientPlayerNetworkEvent.LoggedOutEvent evt) {
        if (evt.getPlayer() != null && evt.getPlayer().level != null) {
//            SidedCache.getEliteMobs(evt.getPlayer().level).clear();
        }
    }

    @SubscribeEvent
    public static void onPreRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
        if (InfernalMobsCore.instance().getIsHealthBarDisabled() || event.getType() != RenderGameOverlayEvent.ElementType.BOSSHEALTH || mc.gui.getBossOverlay().shouldPlayMusic()) {
            return;
        }

        LivingEntity ent = getEntityCrosshairOver(event.getPartialTicks(), mc);
        boolean retained = false;

        if (ent == null && System.currentTimeMillis() < healthBarRetainTime) {
            ent = retainedTarget;
            retained = true;
        }

        if (ent != null) {
            EliteMobModifier mod = RiskOfMine.getMobModifiers(ent);
            if (mod != null) {
                askServerHealth(ent);

                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bind(GUI_BARS_TEXTURES);
                GL11.glDisable(GL11.GL_BLEND);

                String buffer = mod.getEntityDisplayName(ent);

                int screenwidth = mc.getWindow().getGuiScaledWidth();
                FontRenderer fontR = mc.font;

                IngameGui gui = mc.gui;
                short lifeBarLength = 182;
                int x = screenwidth / 2 - lifeBarLength / 2;

                int lifeBarLeft = (int) (mod.getActualHealth(ent) / mod.getActualMaxHealth(ent) * (float) (lifeBarLength + 1));
                byte y = 12;
                MatrixStack matrixStack = event.getMatrixStack();
                gui.blit(matrixStack, x, y, 0, 74, lifeBarLength, 5);
                gui.blit(matrixStack, x, y, 0, 74, lifeBarLength, 5);

                if (lifeBarLeft > 0) {
                    gui.blit(matrixStack, x, y, 0, 79, lifeBarLeft, 5);
                }

                int yCoord = 10;
                fontR.drawShadow(matrixStack, buffer, screenwidth / 2 - fontR.width(buffer) / 2, yCoord, 0x2F96EB);

                String[] display = mod.getDisplayNames();
                int i = 0;
                while (i < display.length && display[i] != null) {
                    yCoord += 10;
                    fontR.drawShadow(matrixStack, display[i], screenwidth / 2 - fontR.width(display[i]) / 2, yCoord, 0xffffff);
                    i++;
                }

                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bind(PlayerContainer.BLOCK_ATLAS);

                if (!retained) {
                    retainedTarget = ent;
                    healthBarRetainTime = System.currentTimeMillis() + 3000L;
                }

            } else {
                askServerMods(ent);
            }
        }
    }

    private static LivingEntity getEntityCrosshairOver(float partialTicks, Minecraft mc) {

        Entity entity = mc.getCameraEntity();
        if (entity != null && mc.level != null) {

            double distance = NAME_VISION_DISTANCE;
            RayTraceResult result = entity.pick(distance, partialTicks, false);
            Vector3d vec3d = entity.getEyePosition(partialTicks);

            double distanceToHit = result.getLocation().distanceToSqr(vec3d);

            Vector3d vec3d1 = entity.getEyePosition(1.0F);
            Vector3d vec3d2 = vec3d.add(vec3d1.x * distance, vec3d1.y * distance, vec3d1.z * distance);
            AxisAlignedBB axisalignedbb = entity.getBoundingBox().expandTowards(vec3d1.scale(distance)).inflate(1.0D, 1.0D, 1.0D);
            EntityRayTraceResult entityraytraceresult = ProjectileHelper.getEntityHitResult(entity, vec3d, vec3d2, axisalignedbb, (p_lambda$getMouseOver$0_0_) -> !p_lambda$getMouseOver$0_0_.isSpectator() && p_lambda$getMouseOver$0_0_.canBeCollidedWith(), distanceToHit);
            if (entityraytraceresult != null) {
                Entity entity1 = entityraytraceresult.getEntity();
                Vector3d vec3d3 = entityraytraceresult.getLocation();
                double d2 = vec3d.distanceToSqr(vec3d3);
                if (d2 < distanceToHit && entity1 instanceof LivingEntity) {
                    return (LivingEntity) entity1;
                }
            }
        }
        return null;
    }

    public static void onHealthPacketForClient(int entID, float health, float maxhealth) {
        Minecraft.getInstance().submitAsync(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> onHealthPacket(entID, health, maxhealth)));
    }

    private static void onHealthPacket(int entID, float health, float maxhealth) {
        Entity ent = Minecraft.getInstance().level.getEntity(entID);
        if (ent instanceof LivingEntity) {
            EliteMobModifier mod = InfernalMobsCore.getMobModifiers((LivingEntity) ent);
            if (mod != null) {
                mod.setActualHealth(health, maxhealth);
            }
        }
    }

    public static void onKnockBackPacket(float xv, float zv) {
//        mc.submitAsync(() -> MM_Gravity.knockBack(mc.player, xv, zv));
    }

    public static void onMobModsPacketToClient(String stringData, int entID) {
//        InfernalMobsCore.instance().addRemoteEntityModifiers(mc.level, entID, stringData);
    }

    public static void onVelocityPacket(float xv, float yv, float zv) {
        mc.submitAsync(() -> mc.player.push(xv, yv, zv));
    }

    public static void onAirPacket(int air) {
        airOverrideValue = air;
        airDisplayTimeout = System.currentTimeMillis() + 3000L;
    }

    public static File getMcFolder() {
        return Minecraft.getInstance().gameDirectory;
    }

    @SubscribeEvent
    public static void onTick(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.AIR) {
            if (System.currentTimeMillis() > airDisplayTimeout) {
                airOverrideValue = -999;
            }
            if (!mc.player.isEyeInFluid(FluidTags.WATER) && airOverrideValue != -999) {
                GL11.glEnable(GL11.GL_BLEND);

                int right_height = 39;

                final int left = mc.getWindow().getGuiScaledWidth() / 2 + 91;
                final int top = mc.getWindow().getGuiScaledWidth() - right_height;
                final int full = MathHelper.ceil((double) (airOverrideValue - 2) * 10.0D / 300.0D);
                final int partial = MathHelper.ceil((double) airOverrideValue * 10.0D / 300.0D) - full;

                for (int i = 0; i < full + partial; ++i) {
                    mc.gui.blit(event.getMatrixStack(), left - i * 8 - 9, top, (i < full ? 16 : 25), 18, 9, 9);
                }
                GL11.glDisable(GL11.GL_BLEND);
            }
        }
    }
}
