package com.stunned.economy.terminal;

import com.stunned.economy.terminal.apps.BankApplication;

public class TerminalManager {

    private TerminalApplication activeApplication = new BankApplication();

    public TerminalApplication getActiveApplication() {
        return activeApplication;
    }

    public void setActiveApplication(TerminalApplication application) {
        if (this.activeApplication != null) {
            this.activeApplication.onClose();
        }

        this.activeApplication = application;
    }
}