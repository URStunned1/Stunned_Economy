package com.stunned.economy.menu;

import com.stunned.economy.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import com.stunned.economy.bank.BankCommands;
import net.minecraft.server.level.ServerPlayer;

public class AtmMenu extends AbstractContainerMenu {

    public AtmMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory);
    }
    public static final int BUTTON_DEPOSIT = 0;
    public static final int BUTTON_WITHDRAW_100 = 1;
    public static final int BUTTON_BALANCE = 2;

    public AtmMenu(int containerId, Inventory inventory) {
        super(ModMenuTypes.ATM_MENU.get(), containerId);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return false;
        }

        switch (id) {
            case BUTTON_DEPOSIT -> {
                BankCommands.deposit(serverPlayer);
                return true;
            }

            case BUTTON_WITHDRAW_100 -> {
                BankCommands.withdraw(serverPlayer, 100);
                return true;
            }

            case BUTTON_BALANCE -> {
                BankCommands.balance(serverPlayer);
                return true;
            }
        }

        return false;
    }
}