package com.dex.data;

import com.dex.stream.DexStreamer;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Created by xueqiulxq on 17/09/2017.
 */

public class FieldPool {

    public FieldDataItem[] fields;

    public static FieldPool parseFrom(RandomAccessFile racFile, DexStreamer s, DexHeader dexHeader,
                                      StringPool stringPool, TypePool typePool) throws IOException {
        FieldPool pool = new FieldPool();
        FieldDataItem[] fields = pool.fields = new FieldDataItem[(int) dexHeader.fieldIdsSize];
        byte[] itemByte = new byte[FieldDataItem.LENGTH];
        for (int i=0; i<dexHeader.fieldIdsSize; ++i) {
            racFile.seek(dexHeader.fieldIdsOff + i * FieldDataItem.LENGTH);
            racFile.read(itemByte, 0, itemByte.length);
            s.use(itemByte);
            fields[i] = FieldDataItem.parseFrom(s, stringPool, typePool);
        }

        racFile.seek(dexHeader.fieldIdsOff);


        return pool;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("-- Field pool --\n");
        for (int i=0; i<fields.length; ++i) {
            builder.append('f').append(i).append(". ").append(fields[i]).append('\n');
        }
        return builder.toString();
    }
}
