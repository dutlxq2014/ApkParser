package com.arsc.data;

import com.arsc.stream.ArscStreamer;
import com.common.PrintUtil;

/**
 *
 * Created by xueqiulxq on 29/07/2017.
 */

public class ResTableEntry {

    // If set, this is a complex entry, holding a set of name/value. It is followed by an array of ResTableMap structures.
    public static final int FLAG_COMPLEX = 0x0001;
    // If set, this resource has been declared public, so libraries are allowed to reference it.
    public static final int FLAG_PUBLIC = 0x0002;

    public int size;    // short
    public int flags;   // short
    public ResStringPoolRef key;    // Reference into ResTablePackage::keyStrings identifying this entry.

    public int entryId; // 16bit  0x7f01nnnn

    public static ResTableEntry parseFrom(ArscStreamer s) {
        ResTableEntry entry = new ResTableEntry();
        parseFrom(s, entry);
        return entry;
    }

    public static void parseFrom(ArscStreamer s, ResTableEntry entry) {
        entry.size = s.readUShort();
        entry.flags = s.readUShort();
        entry.key = ResStringPoolRef.parseFrom(s);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String form = "%-10s 0x%s\n";
        builder.append(String.format(form, "size", PrintUtil.hex2(size)));
        builder.append(String.format(form, "flags", PrintUtil.hex2(flags)));
        builder.append(String.format("%-10s {%s} \t%s\n", "key", key, "/* Reference into ResTablePackage::keyStrings */"));
        return builder.toString();
    }

    public String buildEntry2String(int packageId, int typeId, String typeStr, ResStringPoolChunk keyStringPool) {
        return "Not implemented.";
    }
}
