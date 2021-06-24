# CustomBlockData

CustomBlockData is a library for the Spigot API that allows you to store ANY information inside blocks.
It does so by providing the `CustomBlockData`class which implements the `PersistentDataHolder` interface.

It does not need any files or databases and the information is persistent even after server restarts

### Advantages

- It does not need any files or databases
- When the chunk where the block is inside gets deleted, there will be no leftover information
- You can store anything that can be stored inside a normal `PersistantDataContainer` (which means, basically, anything)

### Maven

**Repository**

```xml
<repository>
    <id>jeff-media-gbr</id>
    <url>https://repo.jeff-media.de/maven2</url>
</repository>
```

**Dependency**
```xml
<dependency>
    <groupId>de.jeff_media</groupId>
    <artifactId>CustomBlockData</artifactId>
    <version>1.0-SNAPSHOT</version>
    <scope>compile</scope>
</dependency>
```

**Shading and relocating**

You must shade (and you should relocate) the `CustomBlockData` class. Just add this to
the `<configuration>` section of your maven-shade-plugin declaration:

```xml
<relocations>
    <relocation>
        <pattern>de.jeff_media.customblockdata.CustomBlockData</pattern>
        <shadedPattern>your.package.name.CustomBlockData</shadedPattern>
    </relocation>
</relocations>
```

### Example plugin
Click [here](https://github.com/JEFF-Media-GbR/CustomBlockData-Example) for an example plugin.
It lets you left-click on a block to store your currently held ItemStack inside. Once the block is broken,
it will drop the stored item.