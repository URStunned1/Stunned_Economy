package com.stunned.economy.client.gui;

public class TerminalLayout {

    public final int left;
    public final int top;

    // Layout constants
    public static final int PADDING = 20;
    public static final int CONTROL_WIDTH = 110;
    public static final int CONTROL_HEIGHT = 20;
    public static final int COLUMN_GAP = 12;
    public static final int ROW_SPACING = 30;

    // Convenience fields
    public final int controlWidth = CONTROL_WIDTH;
    public final int controlHeight = CONTROL_HEIGHT;

    // Text positions
    public final int labelX;
    public final int valueX;

    // Control positions
    public final int leftControlX;
    public final int rightControlX;

    // Vertical positions
    public final int titleY;
    public final int dividerY;
    public final int balanceY;
    public final int cashY;
    public final int amountY;
    public final int actionY;

    public TerminalLayout(int left, int top, int width) {
        this.left = left;
        this.top = top;

        // ===== Text =====
        this.labelX = left + PADDING;
        this.valueX = left + 140;

        // ===== Controls =====
        int controlsTotalWidth = (CONTROL_WIDTH * 2) + COLUMN_GAP;

// Center the two-column control group inside the terminal
        int controlsLeft = left + (width - controlsTotalWidth) / 2;

        this.leftControlX = controlsLeft;
        this.rightControlX = controlsLeft + CONTROL_WIDTH + COLUMN_GAP;

        // ===== Vertical Layout =====
        this.titleY = top + 35;
        this.dividerY = titleY + 14;

        this.balanceY = top + 70;
        this.cashY = top + 90;

        this.amountY = top + 105;
        this.actionY = amountY + ROW_SPACING;
    }
}