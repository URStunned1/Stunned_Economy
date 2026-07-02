package com.stunned.economy.terminal.apps;

import com.stunned.economy.client.gui.IndustrialScreen;
import com.stunned.economy.terminal.TerminalApplication;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import com.stunned.economy.menu.AtmMenu;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class BankApplication implements TerminalApplication {

    @Override
    public String getAppName() {
        return "Stunned National Bank";
    }

    @Override
    public void init(IndustrialScreen<?> screen) {
        int left = screen.getGuiLeft();
        int top = screen.getGuiTop();

        screen.addTerminalWidget(Button.builder(
                Component.literal("Deposit All"),
                button -> {
                    Minecraft mc = Minecraft.getInstance();

                    if (mc.player != null && mc.gameMode != null) {
                        mc.gameMode.handleInventoryButtonClick(
                                mc.player.containerMenu.containerId,
                                AtmMenu.BUTTON_DEPOSIT
                        );
                    }
                }

        ).bounds(left + 20, top + 105, 90, 20).build());

        screen.addTerminalWidget(Button.builder(
                Component.literal("Withdraw 100"),
                button -> {
                    Minecraft mc = Minecraft.getInstance();

                    if (mc.player != null && mc.gameMode != null) {
                        mc.gameMode.handleInventoryButtonClick(
                                mc.player.containerMenu.containerId,
                                AtmMenu.BUTTON_WITHDRAW_100
                        );
                    }
                }
        ).bounds(left + 120, top + 105, 100, 20).build());

        screen.addTerminalWidget(Button.builder(
                Component.literal("Balance"),
                button -> {
                    Minecraft mc = Minecraft.getInstance();

                    if (mc.player != null && mc.gameMode != null) {
                        mc.gameMode.handleInventoryButtonClick(
                                mc.player.containerMenu.containerId,
                                AtmMenu.BUTTON_BALANCE
                        );
                    }
                }

        ).bounds(left + 20, top + 130, 90, 20).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTick) {
        Minecraft mc = Minecraft.getInstance();

        int x = left + 20;
        int y = top + 35;

        guiGraphics.drawString(mc.font, "STUNNED NATIONAL BANK", x, y, 0x00FFFF, false);

        y += 30;
        guiGraphics.drawString(mc.font, "Balance:", x, y, 0xFFFFFF, false);
        long balance = 0;
        int cash = 0;

        if (Minecraft.getInstance().player != null &&
                Minecraft.getInstance().player.containerMenu instanceof AtmMenu atmMenu) {

            balance = atmMenu.getBalance();
            cash = atmMenu.getCashOnHand();
        }

        guiGraphics.drawString(
                mc.font,
                balance + " Credits",
                x + 120,
                y,
                0x55FF55,
                false
        );

        y += 20;

        guiGraphics.drawString(
                mc.font,
                "Cash On Hand:",
                x,
                y,
                0xFFFFFF,
                false
        );

        guiGraphics.drawString(
                mc.font,
                cash + " Credits",
                x + 120,
                y,
                0xFFFF55,
                false
        );
    }

    @Override
    public void onClose() {

    }
}