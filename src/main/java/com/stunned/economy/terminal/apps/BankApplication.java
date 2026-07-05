package com.stunned.economy.terminal.apps;

import com.stunned.economy.client.gui.IndustrialScreen;
import com.stunned.economy.menu.AtmMenu;
import com.stunned.economy.terminal.TerminalApplication;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import com.stunned.economy.client.gui.TerminalLayout;

import java.text.NumberFormat;

public class BankApplication implements TerminalApplication {
    private EditBox amountBox;

    @Override
    public String getAppName() {
        return "Stunned National Bank";
    }


    @Override
    public void init(IndustrialScreen<?> screen) {
        TerminalLayout layout = new TerminalLayout(
                screen.getGuiLeft(),
                screen.getGuiTop(),
                screen.getTerminalWidth()
        );

        amountBox = new EditBox(
                Minecraft.getInstance().font,
                layout.leftControlX,
                layout.amountY,
                layout.controlWidth,
                layout.controlHeight,
                Component.literal("Amount")
        );

        amountBox.setFilter(text -> text.matches("\\d*"));
        amountBox.setHint(Component.literal("Amount"));
        amountBox.setValue("0");

        screen.addTerminalWidget(amountBox);

        screen.addTerminalWidget(Button.builder(
                Component.literal("Deposit"),
                button -> runBankCommand("deposit")
        ).bounds(
                layout.rightControlX,
                layout.amountY,
                layout.controlWidth,
                layout.controlHeight
        ).build());

        screen.addTerminalWidget(Button.builder(
                Component.literal("Withdraw"),
                button -> runBankCommand("withdraw")
        ).bounds(
                layout.rightControlX,
                layout.actionY,
                layout.controlWidth,
                layout.controlHeight
        ).build());

        screen.addTerminalWidget(Button.builder(
                Component.literal("Update"),
                button -> {
                    Minecraft mc = Minecraft.getInstance();

                    if (mc.player != null && mc.gameMode != null) {
                        mc.gameMode.handleInventoryButtonClick(
                                mc.player.containerMenu.containerId,
                                AtmMenu.BUTTON_BALANCE
                        );
                    }
                }
        ).bounds(
                layout.leftControlX,
                layout.actionY,
                layout.controlWidth,
                layout.controlHeight
        ).build());
    }

    private void runBankCommand(String action) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null || amountBox == null) {
            return;
        }

        String text = amountBox.getValue();

        if (text.isEmpty()) {
            return;
        }

        int amount = Integer.parseInt(text);

        if (amount <= 0) {
            return;
        }

        mc.player.connection.sendCommand("bank " + action + " " + amount);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTick) {
        Minecraft mc = Minecraft.getInstance();

        TerminalLayout layout = new TerminalLayout(left, top, 240);

        long balance = 0;
        int cash = 0;

        if (mc.player != null && mc.player.containerMenu instanceof AtmMenu atmMenu) {
            balance = atmMenu.getBalance();
            cash = atmMenu.getCashOnHand();
        }

        guiGraphics.drawString(
                mc.font,
                "STUNNED NATIONAL BANK",
                layout.labelX,
                layout.titleY,
                0x00FFFF,
                false
        );

// Divider line
        guiGraphics.fill(
                layout.labelX,
                layout.dividerY,
                left + 220,
                layout.dividerY + 1,
                0xFF444444
        );

        guiGraphics.drawString(
                mc.font,
                "Balance:",
                layout.labelX,
                layout.balanceY,
                0xFFFFFF,
                false
        );

        guiGraphics.drawString(
                mc.font,
                NumberFormat.getIntegerInstance().format(balance) + " Credits",
                layout.valueX,
                layout.balanceY,
                0x55FF55,
                false
        );

        guiGraphics.drawString(
                mc.font,
                "Cash On Hand:",
                layout.labelX,
                layout.cashY,
                0xFFFFFF,
                false
        );

        guiGraphics.drawString(
                mc.font,
                NumberFormat.getIntegerInstance().format(cash) + " Credits",
                layout.valueX,
                layout.cashY,
                0xFFFF55,
                false
        );
    }

    @Override
    public void onClose() {
    }
}