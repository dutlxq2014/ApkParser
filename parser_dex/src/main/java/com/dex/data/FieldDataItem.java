package com.dex.data;

import com.dex.stream.DexStreamer;

/**
 *
 * Created by xueqiulxq on 17/09/2017.
 */

public class FieldDataItem {

    public static final int LENGTH = 8;

    public int classIdx;    // 2B The class index this field belongs to in type_ids
    public int typeIdx;     // 2B Field type index in type_ids
    public long nameIdx;    // 4B The name index in string_ids

    // Assistant
    public String classStr;
    public String typeStr;
    public String nameStr;

    public static FieldDataItem parseFrom(DexStreamer s, StringPool stringPool, TypePool typePool) {
        FieldDataItem item = new FieldDataItem();
        item.classIdx = s.readU2();
        item.typeIdx = s.readU2();
        item.nameIdx = s.readU4();

        item.classStr = typePool.getType(item.classIdx);
        item.typeStr = typePool.getType(item.typeIdx);
        item.nameStr = stringPool.getString(item.nameIdx);
        return item;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(classStr).append(" -> ").append(typeStr).append(" ").append(nameStr);
        return builder.toString();
    }
}
