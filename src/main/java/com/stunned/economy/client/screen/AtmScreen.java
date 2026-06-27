package com.stunned.economy.client.screen;

import com.stunned.economy.client.gui.IndustrialScreen;
import com.stunned.economy.menu.AtmMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class AtmScreen extends IndustrialScreen<AtmMenu> {

    public AtmScreen(AtmMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }
}