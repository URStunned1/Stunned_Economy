package com.stunned.economy.registry;

import com.stunned.economy.StunnedEconomy;
import com.stunned.economy.menu.AtmMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, StunnedEconomy.MODID);

    public static final Supplier<MenuType<AtmMenu>> ATM_MENU =
            MENUS.register("atm_menu", () ->
                    IMenuTypeExtension.create(AtmMenu::new)
            );

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}