package com.arsc.data;

import com.arsc.stream.ArscStreamer;

/**
 *
 * Created by xueqiulxq on 29/07/2017.
 */

public class ResTableMap {

    // Bag resource ID
    public ResTableRef name;

    // Bag resource item value
    public ResValue value;

    public static ResTableMap parseFrom(ArscStreamer s) {
        ResTableMap tableMap = new ResTableMap();
        tableMap.name = ResTableRef.parseFrom(s);
        tableMap.value = ResValue.parseFrom(s);
        return tableMap;
    }
}
