package com.stunned.economy.bank;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.server.level.ServerPlayer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class BankAccountManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File BANK_FILE = new File("config/stunned_economy/bank_balances.json");

    private static final Map<UUID, BankAccount> BALANCES = new HashMap<>();
    private static boolean loaded = false;

    public static long getBalance(ServerPlayer player) {
        loadIfNeeded();
        return getOrCreate(player).balance;
    }

    public static void addBalance(ServerPlayer player, long amount) {
        addBalance(player, amount, "Deposit", "Deposited " + amount + " credits");
    }

    public static void addBalance(ServerPlayer player, long amount, String type, String note) {
        loadIfNeeded();
        BankAccount account = getOrCreate(player);
        account.balance += amount;
        account.name = player.getName().getString();
        addTransaction(account, type, amount, note);
        save();
    }

    public static boolean removeBalance(ServerPlayer player, long amount) {
        return removeBalance(player, amount, "Withdraw", "Withdrew " + amount + " credits");
    }

    public static boolean removeBalance(ServerPlayer player, long amount, String type, String note) {
        loadIfNeeded();
        BankAccount account = getOrCreate(player);

        if (account.balance < amount) {
            return false;
        }

        account.balance -= amount;
        account.name = player.getName().getString();
        addTransaction(account, type, -amount, note);
        save();
        return true;
    }

    public static void transfer(ServerPlayer from, ServerPlayer to, long amount) {
        removeBalance(from, amount, "Transfer Sent", "Sent " + amount + " credits to " + to.getName().getString());
        addBalance(to, amount, "Transfer Received", "Received " + amount + " credits from " + from.getName().getString());
    }

    public static boolean hasBalance(ServerPlayer player, long amount) {
        return getBalance(player) >= amount;
    }

    public static void setBalance(ServerPlayer player, long amount) {
        loadIfNeeded();
        BankAccount account = getOrCreate(player);
        account.balance = Math.max(0, amount);
        account.name = player.getName().getString();
        addTransaction(account, "Admin Set", amount, "Balance set to " + amount);
        save();
    }

    private static BankAccount getOrCreate(ServerPlayer player) {
        return BALANCES.computeIfAbsent(player.getUUID(), uuid ->
                new BankAccount(player.getName().getString(), 0L)
        );
    }

    private static void addTransaction(BankAccount account, String type, long amount, String note) {
        if (account.transactions == null) {
            account.transactions = new ArrayList<>();
        }

        account.transactions.add(new BankTransaction(type, amount, System.currentTimeMillis(), note));

        while (account.transactions.size() > 25) {
            account.transactions.remove(0);
        }
    }

    private static void loadIfNeeded() {
        if (!loaded) {
            load();
            loaded = true;
        }
    }

    public static void load() {
        try {
            if (!BANK_FILE.exists()) {
                BANK_FILE.getParentFile().mkdirs();
                save();
                return;
            }

            try (FileReader reader = new FileReader(BANK_FILE)) {
                BankSaveData data = GSON.fromJson(reader, BankSaveData.class);
                BALANCES.clear();

                if (data != null && data.accounts != null) {
                    for (Map.Entry<String, BankAccount> entry : data.accounts.entrySet()) {
                        BALANCES.put(UUID.fromString(entry.getKey()), entry.getValue());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("[Stunned Economy] Failed to load bank balances:");
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            BANK_FILE.getParentFile().mkdirs();

            BankSaveData data = new BankSaveData();

            for (Map.Entry<UUID, BankAccount> entry : BALANCES.entrySet()) {
                data.accounts.put(entry.getKey().toString(), entry.getValue());
            }

            try (FileWriter writer = new FileWriter(BANK_FILE)) {
                GSON.toJson(data, writer);
            }
        } catch (Exception e) {
            System.err.println("[Stunned Economy] Failed to save bank balances:");
            e.printStackTrace();
        }
    }

    private static class BankSaveData {
        Map<String, BankAccount> accounts = new HashMap<>();
    }

    private static class BankAccount {
        String name;
        long balance;
        List<BankTransaction> transactions = new ArrayList<>();

        BankAccount(String name, long balance) {
            this.name = name;
            this.balance = balance;
        }
    }

    private static class BankTransaction {
        String type;
        long amount;
        long time;
        String note;

        BankTransaction(String type, long amount, long time, String note) {
            this.type = type;
            this.amount = amount;
            this.time = time;
            this.note = note;
        }
    }
}