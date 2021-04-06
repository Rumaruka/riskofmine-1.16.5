package com.rumaruka.riskofmine.ntw;

import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.text.ITextComponent;

public class ROMNetHandler implements INetHandler {
    @Override
    public void onDisconnect(ITextComponent reason) {

    }

    @Override
    public NetworkManager getConnection() {
        return null;
    }
}
