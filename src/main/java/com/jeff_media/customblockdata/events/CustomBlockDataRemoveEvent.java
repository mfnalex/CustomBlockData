package com.jeff_media.customblockdata.events;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomBlockDataRemoveEvent extends CustomBlockDataEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    public CustomBlockDataRemoveEvent(@NotNull Plugin plugin, @NotNull Block block, @Nullable Event bukkitEvent) {
        super(plugin, block, bukkitEvent);
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

}
