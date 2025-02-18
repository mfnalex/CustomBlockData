# CustomBlockData
<!--- Buttons start -->
<!--suppress HtmlDeprecatedAttribute -->
<p align="center">
  <a href="https://www.spigotmc.org/threads/custom-block-data-persistentdatacontainer-for-blocks.512422/">
    <img src="https://static.jeff-media.com/img/button_spigotmc_thread.png?3" alt="SpigotMC Thread">
  </a>
  <a href="https://hub.jeff-media.com/javadocs/com/jeff-media/custom-block-data/2.2.2/">
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
<p align="center">
<a href="https://maven-badges.herokuapp.com/maven-central/com.jeff-media/custom-block-data">
  <img src="https://maven-badges.herokuapp.com/maven-central/com.jeff-media/custom-block-data/badge.png" alt="Maven" /></a>
<img src="https://img.shields.io/github/last-commit/jeff-media-gbr/customblockdata" />
</p>


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

## Maven Dependency
The dependency is available on Maven Central:
```xml
<dependency>
    <groupId>com.jeff-media</groupId>
    <artifactId>custom-block-data</artifactId>
    <version>2.2.4</version>
    <scope>compile</scope>
</dependency>
```

**Shading and relocating**

You must shade (and you should relocate) the `customblockdata` package.

```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-shade-plugin</artifactId>
      <version>3.5.0</version>
      <configuration>
        <relocations>
          <relocation>
            <pattern>com.jeff_media.customblockdata</pattern>
            <shadedPattern>YOUR.PACKAGE.NAME.customblockdata</shadedPattern>
          </relocation>
        </relocations>
      </configuration>
      <executions>
        <execution>
          <phase>package</phase>
          <goals>
            <goal>shade</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

## Gradle

**Repository**

```groovy
repositories {
    mavenCentral()
}
```

**Dependencies**
```groovy
dependencies {
    implementation 'com.jeff-media:custom-block-data:2.2.4'
}
```

**Shading & Relocating**

You must shade (and you should relocate) the `customblockdata` package. You will need the Shadow plugin found [here](https://plugins.gradle.org/plugin/com.gradleup.shadow). Add the following to your shadowJar section!

```groovy
shadowJar {
    relocate 'com.jeff_media.customblockdata', 'your.package.customblockdata'
}
```

Optionally, make the build task depend on shadowJar:

```groovy
build.dependsOn += shadowJar
```

## Usage

To get a block's PersistentDataContainer, all you have to do is create an instance of `CustomBlockData` providing the block and
the instance of your main class:

```java
PersistentDataContainer customBlockData = new CustomBlockData(block, plugin);
```

If you want CustomBlockData to autimatically handle moving/removing block data for changed blocks (e.g. move data when a block gets moved with a piston, or to remove data when a player breaks a block, etc) you must call CustomBlockData.registerListener(Plugin) once in your onEnable().

For more information about how to use it, just look at the [API docs for the PersistentDataContainer](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/persistence/PersistentDataContainer.html) or look at [this tutorial](https://www.spigotmc.org/threads/a-guide-to-1-14-persistentdataholder-api.371200/).

## Javadocs

Javadocs can be found here: https://hub.jeff-media.com/javadocs/com/jeff-media/custom-block-data/2.2.2/

[//]: # (## Example plugin)

[//]: # ()
[//]: # (Click [here]&#40;https://github.com/JEFF-Media-GbR/CustomBlockData-Example&#41; for an example plugin.)

[//]: # (It lets you left-click on a block to store your currently held ItemStack inside. Once the block is broken,)

[//]: # (it will drop the stored item.)

## Discord

If you need help, feel free to join my Discord server and head to #programming-help:

<a href="https://discord.jeff-media.de"><img src="https://api.jeff-media.de/img/discord1.png"></a>

## Donate

If you are using this project in your paid plugins, or if you just want to buy me a coffee, I would be happy over a small donation :)

<a href="https://paypal.me/mfnalex"><img src="https://www.paypalobjects.com/en_US/DK/i/btn/btn_donateCC_LG.gif" border="0" name="submit" title="PayPal - The safer, easier way to pay online!" alt="Donate with PayPal" /></a>

