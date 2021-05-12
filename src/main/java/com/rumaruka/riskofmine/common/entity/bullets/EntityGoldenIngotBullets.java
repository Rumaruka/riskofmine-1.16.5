package com.rumaruka.riskofmine.common.entity.bullets;

import com.rumaruka.riskofmine.init.ROMEntitys;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static net.minecraft.particles.ParticleTypes.ENTITY_EFFECT;

public class EntityGoldenIngotBullets extends AbstractArrowEntity {
    public EntityGoldenIngotBullets(EntityType<? extends AbstractArrowEntity> type, World level) {
        super(type, level);
    }

    public EntityGoldenIngotBullets( double posX, double posY, double posZ, World level) {
        super(ROMEntitys.GOLDEN_INGOT_BULLETS,posX,posY,posZ,level);

    }

    public EntityGoldenIngotBullets( World level, LivingEntity livingEntity) {
        super(ROMEntitys.GOLDEN_INGOT_BULLETS, livingEntity, level);
    }


    @Override
    public void tick() {
        super.tick();
        if(level.isClientSide){
            if (this.inGround) {
                if (this.inGroundTime % 5 == 0) {
                    this.setParticles(1);
                }
            } else {
                this.setParticles(2);
            }
        } else if (this.inGround && this.inGroundTime != 0 && this.inGroundTime >= 600) {
            this.level.broadcastEntityEvent(this, (byte)0);

        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Items.GOLD_INGOT);
    }

    private void setParticles(int particlesSize){
        for (int i = 0 ; i <particlesSize;++i){
            level.addParticle(ENTITY_EFFECT,getRandomX(1d),getRandomY(),getRandomZ(1d),1,1,1);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleEntityEvent(byte p_70103_1_) {
        super.handleEntityEvent(p_70103_1_);
    }

    public void setItem(ItemStack stack) {
        if(stack.getItem()==Items.GOLD_INGOT){
            return;
        }
    }
}
