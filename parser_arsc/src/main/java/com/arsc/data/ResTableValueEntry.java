package com.arsc.data;

import com.arsc.stream.ArscStreamer;
import com.common.PrintUtil;

/**
 *
 * Created by xueqiulxq on 02/08/2017.
 */

public class ResTableValueEntry extends ResTableEntry {

    public ResValue resValue;

    public static ResTableValueEntry parseFrom(ArscStreamer s) {
        ResTableValueEntry entry = new ResTableValueEntry();
        ResTableEntry.parseFrom(s, entry);
        entry.resValue = ResValue.parseFrom(s);
        return entry;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(resValue);
        return builder.toString();
    }

    @Override
    public String buildEntry2String(String type, ResStringPoolChunk keyStringPool) {
        StringBuilder builder = new StringBuilder();
        String form = "%s=\"%s\" ";
        builder.append("<public ");
        builder.append(String.format(form, "type", type));
        builder.append(String.format(form, "name", keyStringPool.getString((int) key.index)));
        builder.append(String.format(form, "id", "0x" + PrintUtil.hex4(0)));
        builder.append("/>\n");
        return builder.toString();
    }
}
