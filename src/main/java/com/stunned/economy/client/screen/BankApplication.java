package com.stunned.economy.client.screen;

import com.stunned.economy.client.gui.IndustrialScreen;
import com.stunned.economy.menu.AtmMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.client.gui.components.Button;

public class BankApplication extends IndustrialScreen<AtmMenu> {

    public BankApplication(AtmMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTick, mouseX, mouseY);

        int x = leftPos + 12;
        int y = topPos + 35;

        guiGraphics.drawString(font, "STUNNED NATIONAL BANK", x, y, 0x00FFFF, false);

        y += 20;
        guiGraphics.drawString(font, "Balance:", x, y, 0xFFFFFF, false);
        guiGraphics.drawString(font, "0 Credits", x + 110, y, 0x55FF55, false);

        y += 18;
        guiGraphics.drawString(font, "Cash On Hand:", x, y, 0xFFFFFF, false);
        guiGraphics.drawString(font, "0 Credits", x + 110, y, 0xFFFF55, false);
    }
    @Override
    protected void init() {
        super.init();

        int x = leftPos + 12;
        int y = topPos + 95;

        addRenderableWidget(Button.builder(
                Component.literal("Deposit All"),
                button -> minecraft.player.connection.sendCommand("bank deposit")
        ).bounds(x, y, 90, 20).build());

        addRenderableWidget(Button.builder(
                Component.literal("Withdraw 100"),
                button -> minecraft.player.connection.sendCommand("bank withdraw 100")
        ).bounds(x + 100, y, 100, 20).build());

        addRenderableWidget(Button.builder(
                Component.literal("Balance"),
                button -> minecraft.player.connection.sendCommand("bank balance")
        ).bounds(x, y + 25, 90, 20).build());
    }
}