package com.jeff_media.customblockdata.events;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

// TODO: Make one event for the whole List<Block>
public class CustomBlockDataMoveEvent extends CustomBlockDataEvent {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Block blockTo;

    public CustomBlockDataMoveEvent(Plugin plugin, Block blockFrom, Block blockTo, Event bukkitEvent) {
        super(plugin, blockFrom, bukkitEvent);
        this.blockTo = blockTo;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Block getBlockTo() {
        return blockTo;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

}
