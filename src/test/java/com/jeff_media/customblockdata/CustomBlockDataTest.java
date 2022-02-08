package com.jeff_media.customblockdata;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomBlockDataTest {

    ServerMock serverMock;
    MockPlugin plugin;
    World world;

    @BeforeEach
    void setup() {
        serverMock = MockBukkit.mock();
        plugin = MockBukkit.load(MockPlugin.class);
        world = new WorldMock(Material.STONE, 64);
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    void test_addBlockData() {
        int[][] coords = {{0,64,0},{1,64,0},{2,64,0},{100,-64,100}};
        NamespacedKey dummy = new NamespacedKey(plugin,"key");
        int currentValue = 0;
        for(int[] coord : coords) {
            Block block = world.getBlockAt(coord[0],coord[1],coord[2]);
            System.out.println(block);
            CustomBlockData cbd = new CustomBlockData(block,plugin);
            assert cbd.getKeys().isEmpty();

            cbd.set(dummy, PersistentDataType.INTEGER,currentValue);

            CustomBlockData cbd2 = new CustomBlockData(block, plugin);
            assert cbd2.get(dummy,PersistentDataType.INTEGER) == currentValue;

            currentValue++;
        }
    }

}
