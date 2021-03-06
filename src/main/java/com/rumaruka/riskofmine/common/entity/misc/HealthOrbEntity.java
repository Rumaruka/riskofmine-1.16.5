package com.rumaruka.riskofmine.common.entity.misc;

import com.rumaruka.riskofmine.events.PlayerHealthEvent;
import com.rumaruka.riskofmine.init.ROMEntitys;
import com.rumaruka.riskofmine.init.ROMSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class HealthOrbEntity extends Entity {
    public int tickCount;
    public int age;
    public int throwTime;
    private int health = 5;
    public int value;
    private PlayerEntity followingPlayer;
    private int followingTime;

    public HealthOrbEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public HealthOrbEntity(World level, double x, double y, double z, int health) {
        super(ROMEntitys.HEALTH_ORB, level);
        this.setPos(x, y, z);
        this.yRot = (float) (this.random.nextDouble() * 360.0D);
        this.setDeltaMovement((this.random.nextDouble() * (double) 0.2F - (double) 0.1F) * 2.0D, this.random.nextDouble() * 0.2D * 2.0D, (this.random.nextDouble() * (double) 0.2F - (double) 0.1F) * 2.0D);
        this.value = health;
    }

    protected boolean isMovementNoisy() {
        return false;
    }

    protected void defineSynchedData() {
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
        if (this.throwTime > 0) {
            --this.throwTime;
        }

        this.xo = this.getX();
        this.yo = this.getY();
        this.zo = this.getZ();
        if (this.isEyeInFluid(FluidTags.WATER)) {
            this.setUnderwaterMovement();
        } else if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.03D, 0.0D));
        }

        if (this.level.getFluidState(this.blockPosition()).is(FluidTags.LAVA)) {
            this.setDeltaMovement((this.random.nextFloat() - this.random.nextFloat()) * 0.2F, 0.2F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            this.playSound(SoundEvents.GENERIC_BURN, 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
        }

        if (!this.level.noCollision(this.getBoundingBox())) {
            this.moveTowardsClosestSpace(this.getX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0D, this.getZ());
        }

        double d0 = 8.0D;
        if (this.followingTime < this.tickCount - 20 + this.getId() % 100) {
            if (this.followingPlayer == null || this.followingPlayer.distanceToSqr(this) > 64.0D) {
                this.followingPlayer = this.level.getNearestPlayer(this, 8.0D);
            }

            this.followingTime = this.tickCount;
        }

        if (this.followingPlayer != null && this.followingPlayer.isSpectator()) {
            this.followingPlayer = null;
        }

        if (this.followingPlayer != null) {
            Vector3d vector3d = new Vector3d(this.followingPlayer.getX() - this.getX(), this.followingPlayer.getY() + (double) this.followingPlayer.getEyeHeight() / 2.0D - this.getY(), this.followingPlayer.getZ() - this.getZ());
            double d1 = vector3d.lengthSqr();
            if (d1 < 64.0D) {
                double d2 = 1.0D - Math.sqrt(d1) / 8.0D;
                this.setDeltaMovement(this.getDeltaMovement().add(vector3d.normalize().scale(d2 * d2 * 0.1D)));
            }
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        float f = 0.98F;
        if (this.onGround) {
            BlockPos pos = new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
            f = this.level.getBlockState(pos).getSlipperiness(this.level, pos, this) * 0.98F;
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(f, 0.98D, f));
        if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, -0.9D, 1.0D));
        }

        ++this.tickCount;
        ++this.age;
        if (this.age >= 6000) {
            this.remove();
        }

    }

    private void setUnderwaterMovement() {
        Vector3d vector3d = this.getDeltaMovement();
        this.setDeltaMovement(vector3d.x * (double) 0.99F, Math.min(vector3d.y + (double) 5.0E-4F, 0.06F), vector3d.z * (double) 0.99F);
    }


    protected void doWaterSplashEffect() {
    }


    public boolean hurt(DamageSource source, float amount) {
        if (this.level.isClientSide || this.removed) return false;
        if (!this.isInvulnerableTo(source)) {
            this.markHurt();
            this.health = (int) ((float) this.health - amount);
            if (this.health <= 0) {
                this.remove();
            }

        }
        return false;
    }

    public void addAdditionalSaveData(CompoundNBT compound) {
        compound.putShort("Health", (short) this.health);
        compound.putShort("Age", (short) this.age);
        compound.putShort("Value", (short) this.value);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundNBT compound) {
        this.health = compound.getShort("Health");
        this.age = compound.getShort("Age");
        this.value = compound.getShort("Value");
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void playerTouch(@NotNull PlayerEntity player) {
        if (!this.level.isClientSide) {
            if (MinecraftForge.EVENT_BUS.post(new PlayerHealthEvent.PickupHealth(player, this))) return;
            player.level.playSound(null, new BlockPos(player.getX(),player.getY(),player.getZ()), ROMSounds.PROC_MT_IMPACT.get(), SoundCategory.MASTER,2,2);

            player.take(this, 1);
            player.heal(this.value);

            this.remove();
        }
    }

    private int durabilityToXp(int durability) {
        return durability / 2;
    }

    private int xpToDurability(int xp) {
        return xp * 2;
    }

    /**
     * Returns the XP value of this XP orb.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Returns a number from 1 to 10 based on how much XP this orb is worth. This is used by RenderXPOrb to determine
     * what texture to use.
     */
    @OnlyIn(Dist.CLIENT)
    public int getIcon() {
        if (this.value >= 2477) {
            return 10;
        } else if (this.value >= 1237) {
            return 9;
        } else if (this.value >= 617) {
            return 8;
        } else if (this.value >= 307) {
            return 7;
        } else if (this.value >= 149) {
            return 6;
        } else if (this.value >= 73) {
            return 5;
        } else if (this.value >= 37) {
            return 4;
        } else if (this.value >= 17) {
            return 3;
        } else if (this.value >= 7) {
            return 2;
        } else {
            return this.value >= 3 ? 1 : 0;
        }
    }

    /**
     * Get a fragment of the maximum experience points value for the supplied value of experience points value.
     */
    public static int getExperienceValue(int expValue) {
        if (expValue >= 2477) {
            return 2477;
        } else if (expValue >= 1237) {
            return 1237;
        } else if (expValue >= 617) {
            return 617;
        } else if (expValue >= 307) {
            return 307;
        } else if (expValue >= 149) {
            return 149;
        } else if (expValue >= 73) {
            return 73;
        } else if (expValue >= 37) {
            return 37;
        } else if (expValue >= 17) {
            return 17;
        } else if (expValue >= 7) {
            return 7;
        } else {
            return expValue >= 3 ? 3 : 1;
        }
    }

    /**
     * Returns true if it's possible to attack this entity with an item.
     */
    public boolean isAttackable() {
        return false;
    }

    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
