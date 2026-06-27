package com.stunned.economy.registry;

import com.stunned.economy.StunnedEconomy;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.stunned.economy.block.AtmTerminalBlock;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(StunnedEconomy.MODID);

    public static final DeferredBlock<Block> ATM_TERMINAL = BLOCKS.register(
            "atm_terminal",
            () -> new AtmTerminalBlock(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.METAL)
                            .strength(3.0f, 6.0f)
                            .requiresCorrectToolForDrops()
            )
    );

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}