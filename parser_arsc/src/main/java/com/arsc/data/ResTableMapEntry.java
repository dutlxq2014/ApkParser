package com.arsc.data;

import com.arsc.stream.ArscStreamer;
import com.common.PrintUtil;

/**
 *
 * Created by xueqiulxq on 29/07/2017.
 */

public class ResTableMapEntry extends ResTableEntry {

    public ResTableRef parent;  // Reference parent ResTableMapEntry pkgId, if parent not exists the value should be zero.
    public long count;          // Num of ResTableMap following.
    public ResTableMap[] resTableMaps;

    public static ResTableMapEntry parseFrom(ArscStreamer s) {
        ResTableMapEntry entry = new ResTableMapEntry();
        ResTableEntry.parseFrom(s, entry);

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
        builder.append(String.format("%-10s {%s}\n", "parent", parent));
        builder.append(String.format("%-10s 0x%s\n", "count", PrintUtil.hex4(count)));
        builder.append(String.format("ResTableMaps array: length=%d\n", resTableMaps.length));
        for (int i=0; i<resTableMaps.length; ++i) {
            builder.append("ResTableMap ID = ").append(i).append('\n');
            builder.append(resTableMaps[i]);
        }
        return builder.toString();
    }

    public String buildEntry2String(int packageId, int typeId, String typeStr, ResStringPoolChunk keyStringPool) {
        StringBuilder builder = new StringBuilder();
        long resId = (packageId << 24) | (typeId << 16) | (entryId & 0xffff);
        String form = "%s=\"%s\" ";
        builder.append("<public ");
        builder.append(String.format(form, "type", typeStr));
        builder.append(String.format(form, "name", keyStringPool.getString((int) key.index)));
        builder.append(String.format(form, "pkgId", "0x" + PrintUtil.hex4(resId)));
        builder.append("/>\n");
        return builder.toString();
    }
}
