package com.jeff_media.customblockdata;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataType;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AllTests {

    ServerMock serverMock;
    MockPlugin plugin;
    World world;
    NamespacedKey dummy;
    int[][] coords;
    int[][] chunkCoords;

    @BeforeAll
    void setup() {
        System.out.println("SETUP");
        serverMock = MockBukkit.mock();
        plugin = MockBukkit.load(MockPlugin.class);
        world = new WorldMock(Material.STONE, 64);
        dummy = new NamespacedKey(plugin,"key");
        coords = new int[][]{
                {0, 0, 0},
                {1, 64, 0},
                {2, 127, 0},
                {100, 127, 100},
                {1234, 64, 987654}
        };
        chunkCoords = new int[][]{
                {0,0,3},
                {6,6,1},
                {77,61728,1}
        };
    }

    @AfterAll
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    void test_addBlockData() {
        int currentValue = 0;
        for(final int[] coord : coords) {
            final Block block = world.getBlockAt(coord[0],coord[1],coord[2]);
            final CustomBlockData cbd = new CustomBlockData(block,plugin);
            assert cbd.getKeys().isEmpty();

            cbd.set(dummy, PersistentDataType.INTEGER,currentValue);

            final CustomBlockData cbd2 = new CustomBlockData(block, plugin);
            assert cbd2.get(dummy,PersistentDataType.INTEGER) == currentValue;

            //System.out.println("Set PDC value in chunk " + block.getChunk().getX() + ", " + block.getChunk().getZ());

            currentValue++;
        }
    }

    @Test
    void test_getBlockData() {
        for(final int[] chunkCoord : chunkCoords) {
            //System.out.println("Checking PDC values in chunk " + chunkCoord[0] + ", " + chunkCoord[1] + " (expecting " + chunkCoord[2] + ")");
            final Chunk chunk = world.getChunkAt(chunkCoord[0],chunkCoord[1]);
            assert CustomBlockData.getBlocksWithCustomData(plugin, chunk).size() == chunkCoord[2];
        }
    }

}
