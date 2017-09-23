package com.dex.data;

import com.dex.stream.DexStreamer;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Created by xueqiulxq on 18/09/2017.
 */

public class ClassPool {

    public ClassDefItem[] classItems;

    public static ClassPool parseFrom(RandomAccessFile racFile, DexStreamer s, DexHeader dexHeader,
                                      StringPool stringPool, TypePool typePool, ProtoPool protoPool) throws IOException {
        ClassPool pool = new ClassPool();
        ClassDefItem[] items = pool.classItems = new ClassDefItem[(int) dexHeader.classDefsSize];
        byte[] itemBytes = new byte[ClassDefItem.LENGTH];
        for (int i=0; i<items.length; ++i) {
            racFile.seek(dexHeader.classDefsOff + ClassDefItem.LENGTH * i);
            racFile.read(itemBytes, 0, itemBytes.length);
            s.use(itemBytes);
            items[i] = ClassDefItem.parseFrom(s, stringPool, typePool);
        }
        return pool;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("-- Class pool --\n");
        for (int i=0; i<classItems.length; ++i) {
            builder.append("id=").append(i).append('\n');
            builder.append(classItems[i]);
        }
        return builder.toString();
    }
}
