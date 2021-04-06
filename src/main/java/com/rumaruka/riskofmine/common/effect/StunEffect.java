package com.rumaruka.riskofmine.common.effect;

import com.google.common.collect.Maps;
import com.rumaruka.riskofmine.common.config.ModConfig;
import com.rumaruka.riskofmine.init.ROMEffects;
import com.rumaruka.riskofmine.utils.ROMUtils;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;


public class StunEffect extends Effect {
    private final Map<Attribute, AttributeModifier> attributeModifiers = Maps.newHashMap();

    public StunEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {

        if (this == ROMEffects.STUN.get()) {
            if (entityLivingBaseIn instanceof CreatureEntity || entityLivingBaseIn instanceof ServerPlayerEntity) {
                entityLivingBaseIn.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, ROMUtils.setDurOld(ModConfig.durStunConfig.get()), 9, true, false));
                entityLivingBaseIn.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, ROMUtils.setDurOld(ModConfig.durStunConfig.get()), 4, true, false));
                entityLivingBaseIn.addEffect(new EffectInstance(Effects.BLINDNESS, ROMUtils.setDurOld(ModConfig.durStunConfig.get()), 4, true, false));
                entityLivingBaseIn.addEffect(new EffectInstance(Effects.WEAKNESS, ROMUtils.setDurOld(ModConfig.durStunConfig.get()), 5, true, false));
                entityLivingBaseIn.addEffect(new EffectInstance(Effects.CONFUSION, ROMUtils.setDurOld(ModConfig.durStunConfig.get()), 5, true, false));
                entityLivingBaseIn.addEffect(new EffectInstance(Effects.GLOWING, ROMUtils.setDurOld(ModConfig.durStunConfig.get()), 5, true, false));

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

        if (this == ROMEffects.STUN.get()) {
            if (entityLivingBaseIn instanceof CreatureEntity || entityLivingBaseIn instanceof ServerPlayerEntity) {
                entityLivingBaseIn.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, ROMUtils.setDurOld(ModConfig.durStunConfig.get()), 9, true, false));
                entityLivingBaseIn.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, ROMUtils.setDurOld(ModConfig.durStunConfig.get()), 4, true, false));
                entityLivingBaseIn.addEffect(new EffectInstance(Effects.BLINDNESS, ROMUtils.setDurOld(ModConfig.durStunConfig.get()), 4, true, false));
                entityLivingBaseIn.addEffect(new EffectInstance(Effects.WEAKNESS, ROMUtils.setDurOld(ModConfig.durStunConfig.get()), 5, true, false));
                entityLivingBaseIn.addEffect(new EffectInstance(Effects.CONFUSION, ROMUtils.setDurOld(ModConfig.durStunConfig.get()), 5, true, false));
                entityLivingBaseIn.addEffect(new EffectInstance(Effects.GLOWING, ROMUtils.setDurOld(ModConfig.durStunConfig.get()), 5, true, false));


            }
        }

        super.applyInstantenousEffect(source, indirectSource, entityLivingBaseIn, amplifier, health);
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }

    @Override
    public boolean isBeneficial() {
        return true;
    }

    @Override
    public Effect getEffect() {
        return ROMEffects.STUN.get();
    }


}
