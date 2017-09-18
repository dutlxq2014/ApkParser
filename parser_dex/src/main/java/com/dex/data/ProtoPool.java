package com.dex.data;

import com.dex.stream.DexStreamer;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Created by xueqiulxq on 17/09/2017.
 */

public class ProtoPool {

    public ProtoDataItem[] protoItems;

    public static ProtoPool parseFrom(RandomAccessFile racFile, DexStreamer s, DexHeader dexHeader, StringPool stringPool, TypePool typePool) throws IOException {
        ProtoPool pool = new ProtoPool();

        ProtoDataItem[] items = pool.protoItems = new ProtoDataItem[(int) dexHeader.protoIdsSize];
        byte[] itemBytes = new byte[ProtoDataItem.LENGTH];
        for (int i=0; i<dexHeader.protoIdsSize; ++i) {
            long offset = dexHeader.protoIdsOff + ProtoDataItem.LENGTH * i;
            racFile.seek(offset);
            racFile.read(itemBytes, 0, itemBytes.length);
            s.use(itemBytes);
            items[i] = ProtoDataItem.parseFrom(racFile, s, stringPool, typePool);
        }

        return pool;
    }

    public ProtoDataItem getProto(long idx) {
        return idx < protoItems.length ? protoItems[(int) idx] : null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("-- Proto pool --\n");
        for (int i=0; i<protoItems.length; ++i) {
            builder.append('p').append(i).append(". ").append(protoItems[i]).append('\n');
        }
        return builder.toString();
    }
}
