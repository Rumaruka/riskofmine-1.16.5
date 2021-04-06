package com.rumaruka.riskofmine.client.fx;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class FocusCrystalFX extends SpriteTexturedParticle {


    protected FocusCrystalFX(ClientWorld worldIn, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(worldIn, x, y, z, motionX, motionY, motionZ);
        this.xd *= 0.1F;
        this.yd *= 0.1F;
        this.zd *= 0.1F;
        this.xd += motionX * 0.4D;
        this.yd += motionY * 0.4D;
        this.zd += motionZ * 0.4D;
        float f = (float) (Math.random() * (double) 0.3F + (double) 0.6F);
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;
        this.quadSize *= 0.75F;
        this.lifetime = Math.max((int) (6.0D / (Math.random() * 0.8D + 0.6D)), 1);
        this.hasPhysics = false;
        this.tick();

    }

    public float getQuadSize(float scaleFactor) {
        return this.quadSize * MathHelper.clamp(((float) this.age + scaleFactor) / (float) this.lifetime * 32.0F, 0.0F, 1.0F);
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.move(this.xd, this.yd, this.zd);
            this.gCol = (float) ((double) this.gCol * 0.96D);
            this.bCol = (float) ((double) this.bCol * 0.9D);
            this.xd *= 0.7F;
            this.yd *= 0.7F;
            this.zd *= 0.7F;
            this.yd -= 0.02F;
            if (this.onGround) {
                this.xd *= 0.7F;
                this.zd *= 0.7F;
            }

        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {

        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            FocusCrystalFX focusCrystalFX = new FocusCrystalFX(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            focusCrystalFX.setColor(1.0f, 1.0f, 1.0f);
            focusCrystalFX.setSpriteFromAge(this.spriteSet);
            return focusCrystalFX;
        }
    }
}

