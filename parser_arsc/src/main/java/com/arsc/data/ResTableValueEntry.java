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
    public String buildEntry2String(int packageId, int typeId, String typeStr, ResStringPoolChunk keyStringPool) {
        StringBuilder builder = new StringBuilder();
        long resId = (packageId << 24) | (typeId << 16) | (entryId & 0xffff);
        String form = "%s=\"%s\" ";
        builder.append("<public ");
        builder.append(String.format(form, "type", typeStr));
        builder.append(String.format(form, "name", keyStringPool.getString((int) key.index)));
        builder.append(String.format(form, "id", "0x" + PrintUtil.hex4(resId)));
        builder.append("/>\n");
        return builder.toString();
    }

    @Override
    public void translateValues(ResStringPoolChunk globalStringPool,
                                ResStringPoolChunk typeStringPool,
                                ResStringPoolChunk keyStringPool) {
        super.translateValues(globalStringPool, typeStringPool, keyStringPool);
        resValue.translateValues(globalStringPool, typeStringPool, keyStringPool);
    }
}
