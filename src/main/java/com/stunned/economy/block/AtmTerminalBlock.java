package com.stunned.economy.block;

import com.stunned.economy.menu.AtmMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class AtmTerminalBlock extends Block {

    public AtmTerminalBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hitResult
    ) {

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {

            MenuProvider provider = new SimpleMenuProvider(
                    (containerId, inventory, p) ->
                            new AtmMenu(containerId, inventory),
                    Component.literal("Stunned National Bank")
            );

            serverPlayer.openMenu(provider);
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}