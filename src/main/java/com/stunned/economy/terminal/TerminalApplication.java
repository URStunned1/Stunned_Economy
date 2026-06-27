package com.stunned.economy.terminal;

import com.stunned.economy.client.gui.IndustrialScreen;
import net.minecraft.client.gui.GuiGraphics;

public interface TerminalApplication {

    String getAppName();

    void init(IndustrialScreen<?> screen);

    void render(
            GuiGraphics graphics,
            int left,
            int top,
            int mouseX,
            int mouseY,
            float partialTick
    );

    default void on