package com.stunned.economy;

import com.mojang.logging.LogUtils;
import com.stunned.economy.registry.ModCreativeTabs;
import com.stunned.economy.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import com.stunned.economy.bank.BankCommands;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.bus.api.SubscribeEvent;
import com.stunned.economy.registry.ModBlocks;
import com.stunned.economy.registry.ModMenuTypes;

@Mod(StunnedEconomy.MODID)
public class StunnedEconomy {
    public static final String MODID = "stunned_economy";
    public static final Logger LOGGER = LogUtils.getLogger();

    public StunnedEconomy(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModCreativeTabs.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        }
    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        BankCommands.register(event.getDispatcher());
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Stunned Economy loaded.");
    }
}