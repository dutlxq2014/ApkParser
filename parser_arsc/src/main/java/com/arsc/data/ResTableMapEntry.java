package com.arsc.data;

import com.arsc.stream.ArscStreamer;

/**
 *
 * Created by xueqiulxq on 29/07/2017.
 */

public class ResTableMapEntry extends ResTableEntry {

    public ResTableRef parent;  // Reference parent ResTableMapEntry id, if parent not exists the value should be zero.
    public long count;          // Num of ResTableMap following.
    public ResTableMap[] resTableMaps;

    public static ResTableMapEntry parseFrom(ArscStreamer s) {
        ResTableMapEntry entry = new ResTableMapEntry();
        entry.size = s.readUShort();
        entry.flags = s.readUShort();
        entry.key = ResStringPoolRef.parseFrom(s);

        entry.parent = ResTableRef.parseFrom(s);
        entry.count = s.readUInt();

        entry.resTableMaps = new ResTableMap[(int) entry.count];
        for (int i=0; i<entry.count; ++i) {
            entry.resTableMaps[i] = ResTableMap.parseFrom(s);
        }

        return entry;
    }
}
