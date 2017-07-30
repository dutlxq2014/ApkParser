package com.arsc.data;

import com.arsc.stream.ArscStreamer;
import com.common.PrintUtil;

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(parent);
        builder.append(String.format("%-10s 0x%s\n", "count", PrintUtil.hex4(count)));
        builder.append("ResTableMap array:\n");
        for (int i=0; i<resTableMaps.length; ++i) {
            builder.append("ResTableMap ID = ").append(i).append('\n');
            builder.append(resTableMaps[i]);
        }
        return builder.toString();
    }
}
