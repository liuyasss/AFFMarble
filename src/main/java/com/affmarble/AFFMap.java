package com.affmarble;

import android.util.Pair;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class AFFMap {


    private AFFMap() {
        throw new UnsupportedOperationException(AFFConstant.UNSUPPORTED_OPERATION_EXCEPTION_TIP);
    }


    @SafeVarargs
    public static <K, V> HashMap<K, V> newHashMap(final Pair<K, V>... pairs) {
        HashMap<K, V> map = new HashMap<>();
        if (pairs == null || pairs.length == 0) {
            return map;
        }
        for (Pair<K, V> pair : pairs) {
            if (pair == null) continue;
            map.put(pair.first, pair.second);
        }
        return map;
    }

    @SafeVarargs
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(final Pair<K, V>... pairs) {
        LinkedHashMap<K, V> map = new LinkedHashMap<>();
        if (pairs == null || pairs.length == 0) {
            return map;
        }
        for (Pair<K, V> pair : pairs) {
            if (pair == null) continue;
            map.put(pair.first, pair.second);
        }
        return map;
    }

    public static boolean isNullOrEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Map map) {
        return map != null && !map.isEmpty();
    }

}
