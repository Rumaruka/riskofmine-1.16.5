package com.rumaruka.riskofmine.client.fx;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StunFX extends SpriteTexturedParticle {
    private StunFX(ClientWorld worldIn, double x, double y, double z) {
        super(worldIn, x, y, z, 0.0D, 0.0D, 0.0D);
        this.xd *= 0.01F;
        this.yd *= 0.01F;
        this.zd *= 0.01F;
        this.yd += 0.1D;
        this.quadSize *= 0.5F;
        this.lifetime = 16;
        this.hasPhysics = false;
    }

    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public float getQuadSize(float scaleFactor) {
        return this.quadSize * MathHelper.clamp(((float) this.age + scaleFactor) / (float) this.lifetime * 10.0F, 0.0F, 1.5F);
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.move(this.xd, this.yd, this.zd);
            if (this.y == this.yo) {
                this.xd *= 1.1D;
                this.zd *= 1.1D;
            }

            this.xd *= 0.86F;
            this.yd *= 0.86F;
            this.zd *= 0.86F;
            if (this.onGround) {
                this.xd *= 0.7F;
                this.zd *= 0.7F;
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite sprite;

        public Factory(IAnimatedSprite spriteSet) {
            this.sprite = spriteSet;
        }

        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            StunFX stunFX = new StunFX(worldIn, x, y + 1.5D, z);
            stunFX.pickSprite(this.sprite);
            stunFX.setColor(1.0F, 1.0F, 1.0F);
            return stunFX;
        }
    }


}