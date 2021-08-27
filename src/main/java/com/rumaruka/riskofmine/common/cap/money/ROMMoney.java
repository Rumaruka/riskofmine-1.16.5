package com.rumaruka.riskofmine.common.cap.money;


import com.rumaruka.riskofmine.common.cap.money.data.Money;
import com.rumaruka.riskofmine.init.ROMCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.jetbrains.annotations.NotNull;
import ru.timeconqueror.timecore.common.capability.CoffeeCapability;
import ru.timeconqueror.timecore.common.capability.owner.CapabilityOwner;
import ru.timeconqueror.timecore.common.capability.owner.serializer.CapabilityOwnerSerializer;

import javax.annotation.Nullable;

public class ROMMoney extends CoffeeCapability<Entity> implements IMoney {
    public final Money money = container("money", new Money());

    private final PlayerEntity player;

    public ROMMoney(PlayerEntity player) {
        this.player = player;
    }

    @NotNull
    @Override
    public Capability<? extends CoffeeCapability<Entity>> getCapability() {
        return ROMCapability.MONEY;
    }

    @NotNull
    @Override
    public CapabilityOwnerSerializer<Entity> getOwnerSerializer() {
        return CapabilityOwner.ENTITY.getSerializer();
    }

    @Override
    public void sendChangesToClients(@NotNull SimpleChannel channel, @NotNull Object data) {
        channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), data);
    }

    public void detectAndSendChanges() {
        detectAndSendChanges(player.level, player);
    }

    public void sendAllData() {
        sendAllData(player.level, player);
    }

    @Nullable
    public static ROMMoney from(@Nullable PlayerEntity player) {
        if (player != null) {
            LazyOptional<ROMMoney> cap = player.getCapability(ROMCapability.MONEY);

            if (cap.isPresent()) {
                return cap.orElseThrow(IllegalStateException::new);
            }
        }

        return null;
    }
}