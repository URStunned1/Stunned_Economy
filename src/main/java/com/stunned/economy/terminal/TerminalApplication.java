package com.stunned.economy.terminal;

import net.minecraft.client.gui.GuiGraphics;

public interface TerminalApplication {

    String getAppName();

    void init();

    void render(
            GuiGraphics guiGraphics,
            int left,
            int top,
            int mouseX,
            int mouseY,
            float partialTick
    );

    default void onClose() {
    }
}