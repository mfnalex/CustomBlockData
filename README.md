# CustomBlockData

CustomBlockData is a library for the Bukkit API that allows you to store ANY information inside blocks.
It does so by providing the `CustomBlockData`class which implements the `PersistentDataHolder` interface.

It does not need any files or databases by saving the information inside the chunk's PersistentDataContainer, and the information is persistent even after server restarts.

CustomBlockData is compatible with all Bukkit versions from 1.14.1 onwards, including all forks.

## Advantages

- It does not need any files or databases
- When the chunk where the block is inside gets deleted, there will be no leftover information
- You can store anything that can be stored inside a normal `PersistantDataContainer` (which means, basically, **anything**)

## Maven

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

## Usage

To get a block's PersistentDataContainer, all you have to do is create an instance of `CustomBlockData` providing the block and
the instance of your main class:

```java
PersistentDataContainer customBlockData = new CustomBlockData(block, plugin);
```

## Example plugin

Click [here](https://github.com/JEFF-Media-GbR/CustomBlockData-Example) for an example plugin.
It lets you left-click on a block to store your currently held ItemStack inside. Once the block is broken,
it will drop the stored item.

## Discord

If you need, feel free to join my Discord server and head to #programming-help:

<a href="https://discord.jeff-media.de"><img src="https://api.jeff-media.de/img/discord1.png"></a>

## Donate

If you are using this project in your paid plugins, if you just want to buy me coffee, I would be happy over a small donation :)

<form action="https://www.paypal.com/donate" method="post" target="_top">
<input type="hidden" name="hosted_button_id" value="FFHTWPC6Z648S" />
<input type="image" src="https://www.paypalobjects.com/en_US/DK/i/btn/btn_donateCC_LG.gif" border="0" name="submit" title="PayPal - The safer, easier way to pay online!" alt="Donate with PayPal button" />
<img alt="" border="0" src="https://www.paypal.com/en_DE/i/scr/pixel.gif" width="1" height="1" />
</form>
