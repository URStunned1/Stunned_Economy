package com.stunned.economy.menu;

import com.stunned.economy.bank.BankAccountManager;
import com.stunned.economy.bank.BankCommands;
import com.stunned.economy.registry.ModItems;
import com.stunned.economy.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;

public class AtmMenu extends AbstractContainerMenu {

    public static final int BUTTON_DEPOSIT = 0;
    public static final int BUTTON_WITHDRAW_100 = 1;
    public static final int BUTTON_BALANCE = 2;

    private int balance = 0;
    private int cashOnHand = 0;

    public AtmMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory);
    }

    public AtmMenu(int containerId, Inventory inventory) {
        super(ModMenuTypes.ATM_MENU.get(), containerId);

        refreshValues(inventory.player);

        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return balance;
            }

            @Override
            public void set(int value) {
                balance = value;
            }
        });

        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return cashOnHand;
            }

            @Override
            public void set(int value) {
                cashOnHand = value;
            }
        });
    }

    public int getBalance() {
        return balance;
    }

    public int getCashOnHand() {
        return cashOnHand;
    }

    private void refreshValues(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            balance = Math.toIntExact(BankAccountManager.getBalance(serverPlayer));
        }

        cashOnHand = countCash(player.getInventory());
    }

    private int countCash(Inventory inventory) {
        int total = 0;

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);

            if (stack.is(ModItems.CREDIT_1.get())) total += stack.getCount();
            else if (stack.is(ModItems.CREDIT_5.get())) total += stack.getCount() * 5;
            else if (stack.is(ModItems.CREDIT_10.get())) total += stack.getCount() * 10;
            else if (stack.is(ModItems.CREDIT_50.get())) total += stack.getCount() * 50;
            else if (stack.is(ModItems.CREDIT_100.get())) total += stack.getCount() * 100;
        }

        return total;
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
            case BUTTON_DEPOSIT -> BankCommands.deposit(serverPlayer);
            case BUTTON_WITHDRAW_100 -> BankCommands.withdraw(serverPlayer, 100);
            case BUTTON_BALANCE -> BankCommands.balance(serverPlayer);
            default -> {
                return false;
            }
        }

        refreshValues(serverPlayer);

        serverPlayer.getInventory().setChanged();
        serverPlayer.containerMenu.broadcastChanges();
        this.broadcastChanges();

        return true;
    }
}