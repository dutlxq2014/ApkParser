package com.arsc.data;

import com.arsc.stream.ArscStreamer;

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

    public static ResTableEntry parseFrom(ArscStreamer s) {
        ResTableEntry entry = new ResTableEntry();
        entry.size = s.readUShort();
        entry.flags = s.readUShort();
        entry.key = ResStringPoolRef.parseFrom(s);

        return entry;
    }
}
