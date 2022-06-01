package com.jeff_media.customblockdata.internal;

import com.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.block.Block;

import java.util.*;

public class CustomBlockDataUtils {
    public static <K,V> Map<K,V> reverse(Map<K,V> map) {
        LinkedHashMap<K,V> map2 = new LinkedHashMap<>();
        List<K> keys = new ArrayList<>(map.keySet());
        Collections.reverse(keys);
        keys.forEach((key) -> map2.put(key,map.get(key)));
        return map2;
    }
}
