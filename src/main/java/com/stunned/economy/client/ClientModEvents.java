package com.stunned.economy.client;

import com.stunned.economy.StunnedEconomy;
import com.stunned.economy.client.screen.AtmScreen;
import com.stunned.economy.registry.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(
        modid = StunnedEconomy.MODID,
        bus = EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.ATM_MENU.get(), AtmScreen::new);
    }
}
