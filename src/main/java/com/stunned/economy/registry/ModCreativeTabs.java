package com.stunned.economy.registry;

import com.stunned.economy.StunnedEconomy;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, StunnedEconomy.MODID);

    public static final Supplier<CreativeModeTab> STUNNED_ECONOMY_TAB =
            CREATIVE_MODE_TABS.register("stunned_economy_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.stunned_economy"))
                    .icon(() -> ModItems.CREDIT_100.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.CREDIT_1.get());
                        output.accept(ModItems.CREDIT_5.get());
                        output.accept(ModItems.CREDIT_10.get());
                        output.accept(ModItems.CREDIT_50.get());
                        output.accept(ModItems.CREDIT_100.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}