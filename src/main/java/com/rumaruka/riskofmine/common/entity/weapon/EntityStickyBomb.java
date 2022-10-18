package com.rumaruka.riskofmine.common.entity.weapon;

import com.rumaruka.riskofmine.init.ROMEntitys;
import com.rumaruka.riskofmine.init.ROMItems;
import com.rumaruka.riskofmine.utils.ROMMathFormula;
import com.rumaruka.riskofmine.utils.ROMUtils;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class EntityStickyBomb extends Entity {

    private static final DataParameter<Integer> DATA_FUSE_ID;

    private LivingEntity owner;
    private int life;


    private LivingEntity target;

    public EntityStickyBomb(EntityType<? extends EntityStickyBomb> p_i50216_1_, World p_i50216_2_) {
        super(p_i50216_1_, p_i50216_2_);
        this.life = 30;
        this.blocksBuilding = true;
    }

    public EntityStickyBomb(World p_i1730_1_, double p_i1730_2_, double p_i1730_4_, double p_i1730_6_, LivingEntity p_i1730_8_, LivingEntity target) {
        this(ROMEntitys.STICKY_BOMB, p_i1730_1_);
        this.setPos(p_i1730_2_, p_i1730_4_, p_i1730_6_);
        double d0 = p_i1730_1_.random.nextDouble() * 6.2831854820251465D;
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, 0.20000000298023224D, -Math.cos(d0) * 0.02D);
        this.setFuse(80);
        this.xo = p_i1730_2_;
        this.yo = p_i1730_4_;
        this.zo = p_i1730_6_;
        this.owner = p_i1730_8_;
        this.target = target;
    }

    public void setTarget() {
        if (target != null) {
            this.moveTo(target.getX(), target.getY(), target.getZ());
        }


    }

    protected void defineSynchedData() {
        this.entityData.define(DATA_FUSE_ID, 80);
    }

    protected boolean isMovementNoisy() {
        return false;
    }

    public boolean isPickable() {
        return !this.removed;
    }

    public void tick() {

        --this.life;


        setTarget();
        if (this.life <= 0) {
            this.remove();
            if (!this.level.isClientSide) {
                this.explode();
//                this.explodeInc();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level.isClientSide) {
                this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    protected void explode() {
        float f = 2f;
        this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), f, Explosion.Mode.DESTROY);
    }




    protected void addAdditionalSaveData(CompoundNBT p_213281_1_) {
        p_213281_1_.putShort("Fuse", (short) this.getLife());
    }

    protected void readAdditionalSaveData(CompoundNBT p_70037_1_) {
        this.setFuse(p_70037_1_.getShort("Fuse"));
    }

    @Nullable
    public LivingEntity getOwner() {
        return this.owner;
    }

    protected float getEyeHeight(Pose p_213316_1_, EntitySize p_213316_2_) {
        return 0.15F;
    }

    public void setFuse(int p_184534_1_) {
        this.entityData.set(DATA_FUSE_ID, p_184534_1_);
        this.life = p_184534_1_;
    }

    public void onSyncedDataUpdated(DataParameter<?> p_184206_1_) {
        if (DATA_FUSE_ID.equals(p_184206_1_)) {
            this.life = this.getFuse();
        }

    }

    public int getFuse() {
        return (Integer) this.entityData.get(DATA_FUSE_ID);
    }

    public int getLife() {
        return this.life;
    }

    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    static {
        DATA_FUSE_ID = EntityDataManager.defineId(EntityStickyBomb.class, DataSerializers.INT);
    }
}
