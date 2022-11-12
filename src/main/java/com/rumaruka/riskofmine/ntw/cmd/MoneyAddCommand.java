package com.rumaruka.riskofmine.ntw.cmd;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.rumaruka.riskofmine.common.cap.money.ROMMoney;
import com.rumaruka.riskofmine.common.cap.money.data.Money;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.impl.ExperienceCommand;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Collection;

public class MoneyAddCommand {

    public static void register(CommandDispatcher<CommandSource> dispatcher){
        LiteralCommandNode<CommandSource> literalCommandNode = dispatcher.register(Commands.literal("money").requires((p_198442_0_) -> {
            return p_198442_0_.hasPermission(2);
        }).then(Commands.literal("add").then(Commands.argument("targets", EntityArgument.players()).then(Commands.argument("amount", FloatArgumentType.floatArg()).executes((p_198445_0_) -> {
            return addMoney(p_198445_0_.getSource(), EntityArgument.getPlayers(p_198445_0_, "targets"), FloatArgumentType.getFloat(p_198445_0_, "amount"));
    })))));
        dispatcher.register(Commands.literal("rom_money").requires((p_198441_0_) -> {
            return p_198441_0_.hasPermission(2);
        }).redirect(literalCommandNode));
    }



    private static int addMoney (CommandSource source, Collection<? extends ServerPlayerEntity>playerEntities, float amount){
        for (ServerPlayerEntity player: playerEntities){
            ROMMoney romMoney = ROMMoney.from(player);
            if (romMoney!=null){
                Money money = romMoney.money;
                money.addMoney(player,amount);
                romMoney.detectAndSendChanges();
            }


        }
        if (playerEntities.size() == 1) {
            source.sendSuccess(new TranslationTextComponent("commands.add." + "money"+ ".success.single", amount, playerEntities.iterator().next().getDisplayName()), true);

        }
        return playerEntities.size();
    }

    private static int setMoney (CommandSource source, Collection<? extends ServerPlayerEntity>playerEntities, float amount){
        for (ServerPlayerEntity player: playerEntities){
            ROMMoney romMoney = ROMMoney.from(player);
            if (romMoney!=null){
                Money money = romMoney.money;
                money.setMoney(amount);
                romMoney.detectAndSendChanges();
            }


        }
        if (playerEntities.size() == 1) {
            source.sendSuccess(new TranslationTextComponent("commands.set." + "money"+ ".success.single", amount, playerEntities.iterator().next().getDisplayName()), true);

        }
        return playerEntities.size();
    }
}
