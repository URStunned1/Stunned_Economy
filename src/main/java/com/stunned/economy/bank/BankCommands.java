package com.stunned.economy.bank;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.stunned.economy.registry.ModItems;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class BankCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("bank")
                        .then(Commands.literal("balance")
                                .executes(ctx -> balance(ctx.getSource().getPlayerOrException())))

                        .then(Commands.literal("deposit")
                                .executes(ctx -> deposit(ctx.getSource().getPlayerOrException())))

                        .then(Commands.literal("withdraw")
                                .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                        .executes(ctx -> withdraw(
                                                ctx.getSource().getPlayerOrException(),
                                                IntegerArgumentType.getInteger(ctx, "amount")
                                        ))))

                        .then(Commands.literal("pay")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                                .executes(ctx -> pay(
                                                        ctx.getSource().getPlayerOrException(),
                                                        EntityArgument.getPlayer(ctx, "player"),
                                                        IntegerArgumentType.getInteger(ctx, "amount")
                                                )))))
        );
    }

    public static int balance(ServerPlayer player) {
        long balance = BankAccountManager.getBalance(player);
        player.sendSystemMessage(Component.literal("Bank Balance: " + balance + " Stunned Credits"));
        return 1;
    }

    public static int deposit(ServerPlayer player) {
        int total = 0;

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);

            if (stack.is(ModItems.CREDIT_1.get())) {
                total += stack.getCount();
                stack.setCount(0);
            } else if (stack.is(ModItems.CREDIT_5.get())) {
                total += stack.getCount() * 5;
                stack.setCount(0);
            } else if (stack.is(ModItems.CREDIT_10.get())) {
                total += stack.getCount() * 10;
                stack.setCount(0);
            } else if (stack.is(ModItems.CREDIT_50.get())) {
                total += stack.getCount() * 50;
                stack.setCount(0);
            } else if (stack.is(ModItems.CREDIT_100.get())) {
                total += stack.getCount() * 100;
                stack.setCount(0);
            }
        }

        if (total <= 0) {
            player.sendSystemMessage(Component.literal("You have no Stunned Credits to deposit."));
            return 0;
        }

        BankAccountManager.addBalance(player, total);
        player.sendSystemMessage(Component.literal("Deposited " + total + " Stunned Credits."));
        balance(player);
        return total;
    }

    public static int withdraw(ServerPlayer player, int amount) {
        long balance = BankAccountManager.getBalance(player);

        if (balance < amount) {
            player.sendSystemMessage(Component.literal("Not enough credits in your bank."));
            return 0;
        }

        if (!giveCredits(player, amount)) {
            player.sendSystemMessage(Component.literal("Inventory full. Withdrawal cancelled."));
            return 0;
        }

        BankAccountManager.removeBalance(player, amount);
        player.sendSystemMessage(Component.literal("Withdrew " + amount + " Stunned Credits."));
        balance(player);
        return amount;
    }

    private static boolean giveCredits(ServerPlayer player, int amount) {
        int remaining = amount;

        remaining = giveDenomination(player, remaining, 100);
        remaining = giveDenomination(player, remaining, 50);
        remaining = giveDenomination(player, remaining, 10);
        remaining = giveDenomination(player, remaining, 5);
        remaining = giveDenomination(player, remaining, 1);

        return remaining == 0;
    }

    private static int giveDenomination(ServerPlayer player, int remaining, int value) {
        while (remaining >= value) {
            ItemStack stack;

            if (value == 100) {
                stack = new ItemStack(ModItems.CREDIT_100.get());
            } else if (value == 50) {
                stack = new ItemStack(ModItems.CREDIT_50.get());
            } else if (value == 10) {
                stack = new ItemStack(ModItems.CREDIT_10.get());
            } else if (value == 5) {
                stack = new ItemStack(ModItems.CREDIT_5.get());
            } else {
                stack = new ItemStack(ModItems.CREDIT_1.get());
            }

            if (!player.getInventory().add(stack)) {
                return remaining;
            }

            remaining -= value;
        }

        return remaining;
    }

    private static int pay(ServerPlayer sender, ServerPlayer target, int amount) {
        if (sender.getUUID().equals(target.getUUID())) {
            sender.sendSystemMessage(Component.literal("You cannot pay yourself."));
            return 0;
        }

        if (BankAccountManager.getBalance(sender) < amount) {
            sender.sendSystemMessage(Component.literal("Not enough credits."));
            return 0;
        }

        BankAccountManager.removeBalance(sender, amount);
        BankAccountManager.addBalance(target, amount);

        sender.sendSystemMessage(Component.literal("Paid " + target.getName().getString() + " " + amount + " credits."));
        target.sendSystemMessage(Component.literal(sender.getName().getString() + " paid you " + amount + " credits."));
        return amount;
    }
}