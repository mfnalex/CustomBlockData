package com.jeff_media.customblockdata.events;

import com.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public abstract class CustomBlockDataEvent extends Event implements Cancellable {

    final Plugin plugin;
    final Block block;
    final CustomBlockData cbd;
    final Event bukkitEvent;
    boolean isCancelled = false;

    protected CustomBlockDataEvent(Plugin plugin, Block block, Event bukkitEvent) {
        this.plugin = plugin;
        this.block = block;
        this.bukkitEvent = bukkitEvent;
        this.cbd = new CustomBlockData(block, plugin);
    }

    public Block getBlock() {
        return block;
    }

    public Event getBukkitEvent() {
        return bukkitEvent;
    }

    public CustomBlockData getCustomBlockData() {
        return cbd;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    public enum Reason {
        BLOCK_BREAK(BlockBreakEvent.class),
        BLOCK_PLACE(BlockPlaceEvent.class),
        EXPLOSION(EntityExplodeEvent.class, BlockExplodeEvent.class),
        PISTON(BlockPistonExtendEvent.class, BlockPistonRetractEvent.class),
        BURN(BlockBurnEvent.class),
        UNKNOWN((Class<? extends Event>) null);

        private final List<Class<? extends Event>> eventClasses;

        Reason(Class<? extends Event>... eventClasses) {
            this.eventClasses = Arrays.asList(eventClasses);
        }

        public List<Class<? extends Event>> getApplicableEvents() {
            return this.eventClasses;
        }
    }

    public Reason getReason() {
        if(bukkitEvent == null) return null;
        for(Reason reason : Reason.values()) {
            if(reason.eventClasses.stream().anyMatch(clazz -> clazz.equals(bukkitEvent.getClass()))) return reason;
        }
        return Reason.UNKNOWN;
    }
}
