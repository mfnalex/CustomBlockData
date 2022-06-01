package com.jeff_media.customblockdata.internal;

import com.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import java.util.function.Predicate;

public class CustomBlockDataPredicate implements Predicate<Block> {

    private final Plugin plugin;

    public CustomBlockDataPredicate(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean test(Block block) {
        CustomBlockData cbd = new CustomBlockData(block, plugin);
        return !cbd.isEmpty();
    }
}
