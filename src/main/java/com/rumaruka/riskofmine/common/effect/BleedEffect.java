package com.rumaruka.riskofmine.common.effect;

import com.google.common.collect.Maps;
import com.rumaruka.riskofmine.init.ROMDamageSource;
import com.rumaruka.riskofmine.init.ROMEffects;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public class BleedEffect extends Effect {
    private final Map<Attribute, AttributeModifier> attributeModifiers = Maps.newHashMap();

    public BleedEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {

        if (this == ROMEffects.BLEED.get()) {
            if (entityLivingBaseIn instanceof CreatureEntity || entityLivingBaseIn instanceof ServerPlayerEntity) {

                if (entityLivingBaseIn.getHealth() > 2.5F) {
                    int i = (int) ((double) (2 << amplifier) + 1.005D);

                    entityLivingBaseIn.hurt(ROMDamageSource.BLEED, i);
                }

            }


        }


        super.applyEffectTick(entityLivingBaseIn, amplifier);
    }

    @Override
    public String getDescriptionId() {
        return super.getDescriptionId();
    }

    @Override
    public Effect addAttributeModifier(Attribute attributeIn, String uuid, double amount, AttributeModifier.Operation operation) {
        AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(uuid), this::getDescriptionId, amount, operation);
        this.attributeModifiers.put(attributeIn, attributemodifier);
        return this;
    }

    public Map<Attribute, AttributeModifier> getAttributeModifiers() {
        return this.attributeModifiers;
    }

    public void removeAttributeModifiers(LivingEntity entityLivingBaseIn, AttributeModifierManager attributeMapIn, int amplifier) {
        for (Map.Entry<Attribute, AttributeModifier> entry : this.attributeModifiers.entrySet()) {
            ModifiableAttributeInstance modifiableattributeinstance = attributeMapIn.getInstance(entry.getKey());
            if (modifiableattributeinstance != null) {
                modifiableattributeinstance.removeModifier(entry.getValue());
            }
        }

    }

    public void addAttributeModifiers(LivingEntity entityLivingBaseIn, AttributeModifierManager attributeMapIn, int amplifier) {
        for (Map.Entry<Attribute, AttributeModifier> entry : this.attributeModifiers.entrySet()) {
            ModifiableAttributeInstance modifiableattributeinstance = attributeMapIn.getInstance(entry.getKey());
            if (modifiableattributeinstance != null) {
                AttributeModifier attributemodifier = entry.getValue();
                modifiableattributeinstance.removeModifier(attributemodifier);
                modifiableattributeinstance.addPermanentModifier(new AttributeModifier(attributemodifier.getId(), this.getDescriptionId() + " " + amplifier, this.getAttributeModifierValue(amplifier, attributemodifier), attributemodifier.getOperation()));
            }
        }

    }

    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        return modifier.getAmount() * (double) (amplifier + 1);
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health) {

        if (this == ROMEffects.BLEED.get()) {
            if (entityLivingBaseIn instanceof CreatureEntity || entityLivingBaseIn instanceof ServerPlayerEntity) {


                if (entityLivingBaseIn.getHealth() > 2.5F) {
                    int i = (int) (health * (double) (2 << amplifier) + 1.005D);

                    entityLivingBaseIn.hurt(ROMDamageSource.BLEED, i);
                } else this.applyEffectTick(entityLivingBaseIn, amplifier);

            }


        }

        super.applyInstantenousEffect(source, indirectSource, entityLivingBaseIn, amplifier, health);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        if (this == ROMEffects.BLEED.get()) {
            int k = 50 >> amplifier;
            if (k > 0) {
                return duration % k == 0;
            } else {
                return true;
            }
        }
        return super.isDurationEffectTick(duration, amplifier);
    }

    @Override
    public boolean isInstantenous() {
        return false;
    }

    @Override
    public boolean isBeneficial() {
        return true;
    }

    @Override
    public Effect getEffect() {
        return ROMEffects.BLEED.get();
    }
}
