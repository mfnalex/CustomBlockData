package com.jeff_media.customblockdata;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class MockPlugin extends JavaPlugin {

    public MockPlugin() {
        super();
    }

    public MockPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File datafolder, File file) {
        super(loader,description,datafolder,file);
    }

}
