package com.jeff_media.customblockdata.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import com.jeff_media.customblockdata.CustomBlockData;

/**
 * Called when a CustomBlockData is saved into the chunk's
 * {@link PersistentDataContainer} See {@link CustomBlockData#save()}.
 * 
 * @param customBlockData the {@link CustomBlockData} that was saved
 */
public class CustomBlockDataSavedEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private final CustomBlockData customBlockData;

    public CustomBlockDataSavedEvent(@NotNull CustomBlockData customBlockData) {
        this.customBlockData = customBlockData;
    }

    public @NotNull CustomBlockData getCustomBlockData() {
        return customBlockData;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

}