package com.stunned.economy.registry;

import com.stunned.economy.StunnedEconomy;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.item.BlockItem;
import com.stunned.economy.registry.ModBlocks;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(StunnedEconomy.MODID);

    public static final DeferredItem<Item> CREDIT_1 = ITEMS.registerSimpleItem(
            "credit_1",
            new Item.Properties()
    );

    public static final DeferredItem<Item> CREDIT_5 = ITEMS.registerSimpleItem(
            "credit_5",
            new Item.Properties()
    );

    public static final DeferredItem<Item> CREDIT_10 = ITEMS.registerSimpleItem(
            "credit_10",
            new Item.Properties()
    );

    public static final DeferredItem<Item> CREDIT_50 = ITEMS.registerSimpleItem(
            "credit_50",
            new Item.Properties()
    );

    public static final DeferredItem<Item> CREDIT_100 = ITEMS.registerSimpleItem(
            "credit_100",
            new Item.Properties()
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    };
    public static final DeferredItem<BlockItem> ATM_TERMINAL = ITEMS.registerSimpleBlockItem(
            "atm_terminal",
            ModBlocks.ATM_TERMINAL
    );
}
