/*
 * Copyright (c) 2023 Alexander Majka (mfnalex) / JEFF Media GbR
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License.
 *
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * If you need help or have any suggestions, feel free to join my Discord and head to #programming-help:
 *
 * Discord: https://discord.jeff-media.com/
 *
 * If you find this library helpful or if you're using it one of your paid plugins, please consider leaving a donation
 * to support the further development of this project :)
 *
 * Donations: https://paypal.me/mfnalex
 */

package com.jeff_media.customblockdata;import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CustomBlockDataTest {

    private Plugin plugin;
    private ServerMock server;
    private World world;
    private NamespacedKey key;

    @Before
    public void setup() {
        MockBukkit.mock();
        plugin = MockBukkit.load(TestPlugin.class);
        server = MockBukkit.getMock();
        world = server.addSimpleWorld("world");
        key = new NamespacedKey(plugin, "test");
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void test() {
        String string = "hello";
        Block block = world.getBlockAt(0,0,0);
        CustomBlockData cbd = new CustomBlockData(block, plugin);
        cbd.set(key, PersistentDataType.STRING, string);
        Assert.assertEquals(string, cbd.get(key, PersistentDataType.STRING));
    }
}
