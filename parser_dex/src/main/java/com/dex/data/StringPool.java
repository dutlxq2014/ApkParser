package com.dex.data;

import com.common.PrintUtil;
import com.dex.stream.DexStreamer;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Created by xueqiulxq on 17/09/2017.
 */

public class StringPool {

    public long[] stringDataOffsets;
    public StringDataItem[] stringDataItems;

    public static StringPool parseFrom(RandomAccessFile racFile, DexStreamer s, DexHeader dexHeader) throws IOException {

        byte[] bodyBytes = new byte[(int)dexHeader.stringIdsSize * 4];
        racFile.seek(dexHeader.stringIdsOff);
        racFile.read(bodyBytes, 0, bodyBytes.length);
        s.use(bodyBytes);

        StringPool pool = new StringPool();
        long[] offsets = pool.stringDataOffsets = new long[(int) dexHeader.stringIdsSize];
        for (int i=0; i<dexHeader.stringIdsSize; ++i) {
            offsets[i] = s.readU4();
        }

        StringDataItem[] items = pool.stringDataItems = new StringDataItem[offsets.length];
        for (int i=0; i<offsets.length; ++i) {
            items[i] = StringDataItem.parseFrom(racFile, s, offsets[i]);
        }
        return pool;
    }

    public String getString(int idx) {
        return idx < stringDataItems.length ? stringDataItems[idx].data : null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("-- String pool --\n");
        for (int i=0; i<stringDataOffsets.length; ++i) {
            builder.append(String.format("offsets=%s\t %s\n", PrintUtil.hex4(stringDataOffsets[i]), stringDataItems[i].toString()));
        }
        return builder.toString();
    }
}
