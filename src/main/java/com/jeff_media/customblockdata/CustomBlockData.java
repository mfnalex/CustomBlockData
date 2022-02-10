/*
 * Made by mfnalex / JEFF Media GbR
 *
 * If you find this helpful or if you're using this project inside your paid plugins,
 * consider leaving a donation :)
 *
 * https://paypal.me/mfnalex
 *
 * If you need help or have any suggestions, just create an issue or join my discord
 * and head to the channel #programming-help
 *
 * https://discord.jeff-media.de
 */

package com.jeff_media.customblockdata;

import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * PersistentDataContainer for blocks!
 * <p>
 * Blocks do not implement PersistentDataHolder, so you cannot store custom data inside them.
 * We can however just use the chunk instead. Every Block gets its own PersistentDataContainer,
 * created and stored only when needed.
 * <p>
 * Once you clear the custom data of a block, the PersistentDataContainer also gets removed
 * from the chunk's PersistentDataContainer.
 * <p>
 * The basic idea is extremely simple - every block's PersistentDataContainer is stored as
 * {@link org.bukkit.persistence.PersistentDataType#TAG_CONTAINER} inside the chunk.
 * The {@link org.bukkit.NamespacedKey} used inside the chunk's container is linked to the block's
 * relative coordinates inside the chunk. That's basically it^^
 */
public class CustomBlockData implements PersistentDataContainer {

    private static final Pattern KEY_REGEX = Pattern.compile("^x(\\d+)y(-?\\d+)z(\\d+)$");
    private static final int CHUNK_MIN_XZ = 0;
    private static final int CHUNK_MAX_XZ = 15;
    private static boolean hasWorldInfoGetMinHeightMethod;
    private final PersistentDataContainer pdc;
    private final Chunk chunk;
    private final NamespacedKey key;

    static {
        try {
            Class.forName("org.bukkit.generator.WorldInfo");
            hasWorldInfoGetMinHeightMethod = true;
        } catch (final ClassNotFoundException exception) {
            hasWorldInfoGetMinHeightMethod = false;
        }
    }

    /**
     * Gets the PersistentDataContainer associated with the given block and plugin
     *
     * @param block  Block
     * @param plugin Plugin
     */
    public CustomBlockData(final @NotNull Block block, final @NotNull Plugin plugin) {
        this.chunk = block.getChunk();
        this.key = new NamespacedKey(plugin, getOldKey(block));
        this.pdc = getPersistentDataContainer();
    }

    /**
     * Gets the PersistentDataContainer associated with the given block and plugin
     *
     * @param block     Block
     * @param namespace Namespace
     *
     * @deprecated Use {@link #CustomBlockData(Block, Plugin)} instead.
     */
    @Deprecated
    public CustomBlockData(final @NotNull Block block, final @NotNull String namespace) {
        this.chunk = block.getChunk();
        this.key = new NamespacedKey(namespace, getOldKey(block));
        this.pdc = getPersistentDataContainer();
    }

    /**
     * Returns a Set&lt;Block&gt; of all blocks in this chunk containing Custom Block Data created by the given plugin
     *
     * @param plugin Plugin
     * @param chunk Chunk
     *
     * @return A Set containing all blocks in this chunk containing Custom Block Data created by the given plugin
     */
    @NotNull
    public static Set<Block> getBlocksWithCustomData(final Plugin plugin, final Chunk chunk) {
        final NamespacedKey dummy = new NamespacedKey(plugin, "dummy");
       return getBlocksWithCustomData(chunk, dummy);
    }

    /**
     * Returns a Set&lt;Block&gt; of all blocks in this chunk containing Custom Block Data created by the given plugin
     *
     * @param namespace Namespace
     * @param chunk Chunk
     *
     * @return A Set containing all blocks in this chunk containing Custom Block Data created by the given plugin
     *
     * @deprecated Use {@link #getBlocksWithCustomData(Plugin, Chunk)} instead
     */
    @NotNull
    @Deprecated
    public static Set<Block> getBlocksWithCustomData(final String namespace, final Chunk chunk) {
        final NamespacedKey dummy = new NamespacedKey(namespace, "dummy");
        return getBlocksWithCustomData(chunk, dummy);
    }

    @NotNull
    private static Set<Block> getBlocksWithCustomData(final @NotNull Chunk chunk, final @NotNull NamespacedKey dummy) {
        final PersistentDataContainer chunkPDC = chunk.getPersistentDataContainer();
        return chunkPDC.getKeys().stream()
                .filter(key -> key.getNamespace().equals(dummy.getNamespace()))
                .map(key -> getBlockFromKey(key, chunk))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Nullable
    private static Block getBlockFromKey(final NamespacedKey key, final Chunk chunk) {
        final Matcher matcher = KEY_REGEX.matcher(key.getKey());
        if(!matcher.matches()) return null;
        final int x = Integer.parseInt(matcher.group(1));
        final int y = Integer.parseInt(matcher.group(2));
        final int z = Integer.parseInt(matcher.group(3));
        if((x < CHUNK_MIN_XZ || x > CHUNK_MAX_XZ)
            || (z < CHUNK_MIN_XZ || z > CHUNK_MAX_XZ)
            || (y < getWorldMinHeight(chunk.getWorld()) || y > chunk.getWorld().getMaxHeight() - 1)) return null;
        return chunk.getBlock(x,y,z);
    }

    private static int getWorldMinHeight(final World world) {
        if(hasWorldInfoGetMinHeightMethod) {
            return world.getMinHeight();
        } else {
            return 0;
        }
    }

    /**
     * Gets a String-based NamespacedKey that consists of the block's relative coordinates within its chunk
     *
     * @param block block
     * @return NamespacedKey consisting of the block's relative coordinates within its chunk
     */
    @NotNull
    private static String getOldKey(@NotNull final Block block) {
        final int x = block.getX() & 0x000F;
        final int y = block.getY();
        final int z = block.getZ() & 0x000F;
        return String.format("x%dy%dz%d", x, y, z);
    }

    /**
     * Removes all custom block data
     */
    public void clear() {
        pdc.getKeys().forEach(pdc::remove);
        save();
    }

    /**
     * Gets the PersistentDataContainer associated with this block.
     *
     * @return PersistentDataContainer of this block
     */
    @NotNull
    private PersistentDataContainer getPersistentDataContainer() {
        final PersistentDataContainer chunkPDC = chunk.getPersistentDataContainer();
        final PersistentDataContainer blockPDC;
        if (chunkPDC.has(key, PersistentDataType.TAG_CONTAINER)) {
            blockPDC = chunkPDC.get(key, PersistentDataType.TAG_CONTAINER);
            assert blockPDC != null;
            return blockPDC;
        }
        blockPDC = chunkPDC.getAdapterContext().newPersistentDataContainer();
        //chunkPDC.set(key, PersistentDataType.TAG_CONTAINER, blockPDC);
        return blockPDC;
    }

    /**
     * Saves the block's PersistentDataContainer inside the chunk's PersistentDataContainer
     */
    private void save() {
        if (pdc.isEmpty()) {
            chunk.getPersistentDataContainer().remove(key);
        } else {
            chunk.getPersistentDataContainer().set(key, PersistentDataType.TAG_CONTAINER, pdc);
        }
    }

    @Override
    public <T, Z> void set(final @NotNull NamespacedKey namespacedKey, final @NotNull PersistentDataType<T, Z> persistentDataType, final @NotNull Z z) {
        pdc.set(namespacedKey, persistentDataType, z);
        save();
    }

    @Override
    public <T, Z> boolean has(final @NotNull NamespacedKey namespacedKey, final @NotNull PersistentDataType<T, Z> persistentDataType) {
        return pdc.has(namespacedKey, persistentDataType);
    }

    @Nullable
    @Override
    public <T, Z> Z get(final @NotNull NamespacedKey namespacedKey, final @NotNull PersistentDataType<T, Z> persistentDataType) {
        return pdc.get(namespacedKey, persistentDataType);
    }

    @NotNull
    @Override
    public <T, Z> Z getOrDefault(final @NotNull NamespacedKey namespacedKey, final @NotNull PersistentDataType<T, Z> persistentDataType, final @NotNull Z z) {
        return pdc.getOrDefault(namespacedKey, persistentDataType, z);
    }

    @NotNull
    @Override
    public Set<NamespacedKey> getKeys() {
        return pdc.getKeys();
    }

    @Override
    public void remove(final @NotNull NamespacedKey namespacedKey) {
        pdc.remove(namespacedKey);
        save();
    }

    @Override
    public boolean isEmpty() {
        return pdc.isEmpty();
    }

    @NotNull
    @Override
    public PersistentDataAdapterContext getAdapterContext() {
        return pdc.getAdapterContext();
    }
}

