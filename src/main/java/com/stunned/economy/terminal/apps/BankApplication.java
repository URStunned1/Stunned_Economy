package com.stunned.economy.terminal.apps;

import com.stunned.economy.client.gui.IndustrialScreen;
import com.stunned.economy.terminal.TerminalApplication;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import com.stunned.economy.client.gui.IndustrialScreen;

public class BankApplication implements TerminalApplication {

    @Override
    public String getAppName() {
        return "Stunned National Bank";
    }

    @Override
    public void init(IndustrialScreen<?> screen) {
    }

    @Override
    public void render(GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTick) {
        Minecraft mc = Minecraft.getInstance();

        int x = left + 20;
        int y = top + 35;

        guiGraphics.drawString(mc.font, "STUNNED NATIONAL BANK", x, y, 0x00FFFF, false);

        y += 30;
        guiGraphics.drawString(mc.font, "Balance:", x, y, 0xFFFFFF, false);
        guiGraphics.drawString(mc.font, "0 Credits", x + 120, y, 0x55FF55, false);

        y += 20;
        guiGraphics.drawString(mc.font, "Cash On Hand:", x, y, 0xFFFFFF, false);
        guiGraphics.drawString(mc.font, "0 Credits", x + 120, y, 0xFFFF55, false);
    }

    @Override
    public void onClose() {

    }
}