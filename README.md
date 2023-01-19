# CustomBlockData
<!--- Buttons start -->
<!--suppress HtmlDeprecatedAttribute -->
<p align="center">
  <a href="https://www.spigotmc.org/threads/custom-block-data-persistentdatacontainer-for-blocks.512422/">
    <img src="https://static.jeff-media.com/img/button_spigotmc_thread.png?3" alt="SpigotMC Thread">
  </a>
  <a href="https://hub.jeff-media.com/javadocs/customblockdata">
    <img src="https://static.jeff-media.com/img/button_javadocs.png?3" alt="Javadocs">
  </a>
  <a href="https://discord.jeff-media.com/">
    <img src="https://static.jeff-media.com/img/button_discord.png?3" alt="Discord">
  </a>
  <a href="https://paypal.me/mfnalex">
    <img src="https://static.jeff-media.com/img/button_donate.png?3" alt="Donate">
  </a>
</p>
<!--- Buttons end -->

CustomBlockData is a library for the Bukkit API that allows you to store ANY information inside blocks.
It does so by providing the `CustomBlockData` class which implements the `PersistentDataContainer` interface.

It does not need any files or databases by saving the information inside the chunk's PersistentDataContainer, and the information is persistent even after server restarts.

CustomBlockData is compatible with all Bukkit versions from 1.16.3 onwards, including all forks. Older versions are not supported because *Chunk* only implements *PersistentDataHolder* since 1.16.3.

## Advantages

- It does not need any files or databases
- When the chunk where the block is inside gets deleted, there will be no leftover information
- You can store anything that can be stored inside a normal `PersistantDataContainer` (which means, basically, **anything**)
- It can automatically keep track of block changes and automatically delete block data when a block gets broken, move data when a block gets moved, etc
  - You can make specific blocks protected from this, or listen to the cancellable `CustomBlockDataEvent`s 
  - (This is disabled by default for backwards compatibility - just call `CustomBlockData#registerListener(Plugin)` to enable it) 

## Maven

**Repository**

```xml
<repository>
    <id>jeff-media-gbr</id>
    <url>https://hub.jeff-media.com/nexus/repository/jeff-media-public/</url>
</repository>
```

**Dependency**
```xml
<dependency>
    <groupId>com.jeff_media</groupId>
    <artifactId>CustomBlockData</artifactId>
    <version>2.2.0</version>
    <scope>compile</scope>
</dependency>
```

**Shading and relocating**

You must shade (and you should relocate) the `CustomBlockData` class. Just add this to
the `<configuration>` section of your maven-shade-plugin declaration:

```xml
<relocations>
    <relocation>
        <pattern>com.jeff_media.customblockdata</pattern>
        <shadedPattern>YOUR.PACKAGE.customblockdata</shadedPattern>
    </relocation>
</relocations>
```

## Gradle

**Repository**

```groovy
repositories {
    maven {
      url = 'https://hub.jeff-media.com/nexus/repository/jeff-media-public/'
    }
}
```

**Dependencies**
```groovy
dependencies {
    implementation 'com.jeff_media:CustomBlockData:2.2.0'
}
```

**Shading & Relocating**

You must shade (and you should relocate) the `CustomBlockData` class. You will need Shadow found on [here](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow). Add the following to your shadowJar section!

```groovy
shadowJar {
    relocate 'com.jeff_media.customblockdata', 'your.package.customblockdata'
}
```


## Usage

To get a block's PersistentDataContainer, all you have to do is create an instance of `CustomBlockData` providing the block and
the instance of your main class:

```java
PersistentDataContainer customBlockData = new CustomBlockData(block, plugin);
```

For more information about how to use it, just look at the [API docs for the PersistentDataContainer](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/persistence/PersistentDataContainer.html) or look at [this tutorial](https://www.spigotmc.org/threads/a-guide-to-1-14-persistentdataholder-api.371200/).

## Javadocs

Javadocs can be found here: https://hub.jeff-media.com/javadocs/customblockdata/

## Example plugin

Click [here](https://github.com/JEFF-Media-GbR/CustomBlockData-Example) for an example plugin.
It lets you left-click on a block to store your currently held ItemStack inside. Once the block is broken,
it will drop the stored item.

## Discord

If you need help, feel free to join my Discord server and head to #programming-help:

<a href="https://discord.jeff-media.de"><img src="https://api.jeff-media.de/img/discord1.png"></a>

## Donate

If you are using this project in your paid plugins, or if you just want to buy me a coffee, I would be happy over a small donation :)

<a href="https://paypal.me/mfnalex"><img src="https://www.paypalobjects.com/en_US/DK/i/btn/btn_donateCC_LG.gif" border="0" name="submit" title="PayPal - The safer, easier way to pay online!" alt="Donate with PayPal" /></a>

