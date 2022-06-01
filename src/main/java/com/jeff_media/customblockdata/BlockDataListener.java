package com.jeff_media.customblockdata;

import com.jeff_media.customblockdata.events.CustomBlockDataMoveEvent;
import com.jeff_media.customblockdata.events.CustomBlockDataRemoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.function.Predicate;

final class BlockDataListener implements Listener {

    private final Plugin plugin;
    private final Predicate<Block> customDataPredicate;

    public BlockDataListener(Plugin plugin) {
        this.plugin = plugin;
        this.customDataPredicate = block -> CustomBlockData.hasCustomBlockData(block, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        callAndRemove(event);
    }

    private void callAndRemove(BlockEvent blockEvent) {
        if (callEvent(blockEvent)) {
            getCbd(blockEvent).clear();
        }
    }

    private boolean callEvent(BlockEvent blockEvent) {
        return callEvent(blockEvent.getBlock(), blockEvent);
    }

    private CustomBlockData getCbd(BlockEvent event) {
        return getCbd(event.getBlock());
    }

    private boolean callEvent(Block block, Event bukkitEvent) {
        CustomBlockData cbd = new CustomBlockData(block, plugin);
        if (cbd.isEmpty()) return false;

        CustomBlockDataRemoveEvent cbdEvent = new CustomBlockDataRemoveEvent(plugin, block, bukkitEvent);
        Bukkit.getPluginManager().callEvent(cbdEvent);

        return !cbdEvent.isCancelled();
    }

    private CustomBlockData getCbd(Block block) {
        return new CustomBlockData(block, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        callAndRemove(event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntity(EntityChangeBlockEvent event) {
        callAndRemove(event.getBlock(), event);
    }

    private void callAndRemove(Block block, Event bukkitEvent) {
        if (callEvent(block, bukkitEvent)) {
            getCbd(block).clear();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onExplode(BlockExplodeEvent event) {
        onExplode(event.blockList(), event);
    }

    private void onExplode(List<Block> blocks, Event bukkitEvent) {
        blocks.stream().filter(customDataPredicate).forEach(block -> callAndRemove(block, bukkitEvent));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onExplode(EntityExplodeEvent event) {
        onExplode(event.blockList(), event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBurn(BlockBurnEvent event) {
        callAndRemove(event);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPiston(BlockPistonExtendEvent event) {
        onPiston(event.getBlocks(), event);
    }

    private void onPiston(List<Block> blocks, BlockPistonEvent bukkitEvent) {
        Map<Block, CustomBlockData> map = new LinkedHashMap<>();
        BlockFace direction = bukkitEvent.getDirection();
        blocks.stream().filter(customDataPredicate).forEach(block -> {
            CustomBlockData cbd = new CustomBlockData(block, plugin);
            Block destinationBlock = block.getRelative(direction);
            CustomBlockDataMoveEvent moveEvent = new CustomBlockDataMoveEvent(plugin, block, destinationBlock, bukkitEvent);
            Bukkit.getPluginManager().callEvent(moveEvent);
            if (moveEvent.isCancelled()) return;
            map.put(destinationBlock, cbd);
        });
        reverse(map).forEach((block, cbd) -> {
            cbd.copyTo(block, plugin);
            cbd.clear();
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPiston(BlockPistonRetractEvent event) {
        onPiston(event.getBlocks(), event);
    }

    private static <K, V> Map<K, V> reverse(Map<K, V> map) {
        LinkedHashMap<K, V> reversed = new LinkedHashMap<>();
        List<K> keys = new ArrayList<>(map.keySet());
        Collections.reverse(keys);
        keys.forEach((key) -> reversed.put(key, map.get(key)));
        return reversed;
    }

}
