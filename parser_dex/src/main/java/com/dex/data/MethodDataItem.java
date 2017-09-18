package com.dex.data;

import com.dex.stream.DexStreamer;

/**
 *
 * Created by xueqiulxq on 17/09/2017.
 */

public class MethodDataItem {

    public static final int LENGTH = 8;

    public int classIdx;    // 2B
    public int protoIdx;    // 2B
    public long nameIdx;     // 4B

    // Assistant
    public String classStr;
    public ProtoDataItem protoItem;
    public String nameStr;

    public static MethodDataItem parseFrom(DexStreamer s, StringPool stringPool, TypePool typePool, ProtoPool protoPool) {
        MethodDataItem item = new MethodDataItem();
        item.classIdx = s.readU2();
        item.protoIdx = s.readU2();
        item.nameIdx = s.readU4();

        item.classStr = typePool.getType(item.classIdx);
        item.nameStr = stringPool.getString(item.nameIdx);
        item.protoItem = protoPool.getProto(item.protoIdx);

        return item;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(classStr).append(" -> ").append(nameStr).append(protoItem);
        return builder.toString();
    }
}
