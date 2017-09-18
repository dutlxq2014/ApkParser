package com.dex.data;

import com.dex.stream.DexStreamer;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Created by xueqiulxq on 17/09/2017.
 */

public class MethodPool {

    public MethodDataItem[] methods;

    public static MethodPool parseFrom(RandomAccessFile racFile, DexStreamer s, DexHeader dexHeader,
                                       StringPool stringPool, TypePool typePool, ProtoPool protoPool) throws IOException {
        MethodPool pool = new MethodPool();
        MethodDataItem[] methods = pool.methods = new MethodDataItem[(int) dexHeader.methodIdsSize];
        byte[] itemBytes = new byte[MethodDataItem.LENGTH];
        for (int i=0; i<methods.length; ++i) {
            racFile.seek(dexHeader.methodIdsOff + MethodDataItem.LENGTH * i);
            racFile.read(itemBytes, 0, itemBytes.length);
            s.use(itemBytes);
            methods[i] = MethodDataItem.parseFrom(s, stringPool, typePool, protoPool);
        }
        return pool;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("-- Method pool --\n");
        for (int i=0; i<methods.length; ++i) {
            builder.append('m').append(i).append(". ").append(methods[i]).append('\n');
        }
        return builder.toString();
    }
}
