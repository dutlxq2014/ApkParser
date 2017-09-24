package com.dex.data;

import com.common.PrintUtil;
import com.dex.stream.DexStreamer;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Created by xueqiulxq on 24/09/2017.
 */

public class ClassInterfaceItem {

    public long size;                // 4B
    public int[] typeIdx;           // 2B * size
    // Assistant
    public String[] interfaceStrs;

    public static ClassInterfaceItem parseFrom(RandomAccessFile racFile, DexStreamer s,
                                               StringPool stringPool, TypePool typePool, ProtoPool protoPool) throws IOException {
        ClassInterfaceItem item = new ClassInterfaceItem();

        byte[] sizeBytes = new byte[4];
        racFile.read(sizeBytes, 0, sizeBytes.length);
        s.use(sizeBytes);
        item.size = s.readU4();

        int[] typeIdxs = item.typeIdx = new int[(int) item.size];
        byte[] idxBytes = new byte[typeIdxs.length * 2];
        racFile.read(idxBytes, 0, idxBytes.length);
        s.use(idxBytes);
        for (int i=0; i<typeIdxs.length; ++i) {
            typeIdxs[i] = s.readU2();
        }

        String[] itfStrs = item.interfaceStrs = new String[typeIdxs.length];
        for (int i=0; i<itfStrs.length; ++i) {
            itfStrs[i] = typePool.getType(typeIdxs[i]);
        }

        return item;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("-- ClassInterfaceItem --\n");

        if (interfaceStrs != null) {
            for (int i=0; i<interfaceStrs.length; ++i) {
                builder.append(String.format("\t%d. idx=%s  %s\n", i, PrintUtil.hex4(typeIdx[i]), interfaceStrs[i]));
            }
        } else {
            builder.append("\tNo any interface.\n");
        }

        return builder.toString();
    }
}
