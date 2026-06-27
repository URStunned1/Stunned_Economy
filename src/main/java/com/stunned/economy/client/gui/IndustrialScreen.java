package com.stunned.economy.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public abstract class IndustrialScreen<T extends AbstractContainerMenu>
        extends AbstractContainerScreen<T> {

    public IndustrialScreen(T menu, Inventory inventory, Component title) {
        super(menu, inventory, title);

        this.imageWidth = 240;
        this.imageHeight = 170;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {

        guiGraphics.fill(leftPos, topPos,
                leftPos + imageWidth,
                topPos + imageHeight,
                0xFF1A1A1A);

        guiGraphics.fill(leftPos + 4, topPos + 4,
                leftPos + imageWidth - 4,
                topPos + 22,
                0xFF2E3A46);

        guiGraphics.fill(leftPos + 4, topPos + 26,
                leftPos + imageWidth - 4,
                topPos + imageHeight - 20,
                0xFF101820);

        guiGraphics.fill(leftPos + 4,
                topPos + imageHeight - 16,
                leftPos + imageWidth - 4,
                topPos + imageHeight - 4,
                0xFF2A2A2A);

        guiGraphics.drawString(
                font,
                "STUNNED INDUSTRIES TERMINAL",
                leftPos + 10,
                topPos + 10,
                0xFFFFFF
        );

        guiGraphics.drawString(
                font,
                "ONLINE",
                leftPos + 10,
                topPos + imageHeight - 13,
                0x55FF55
        );
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}