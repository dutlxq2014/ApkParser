package com.dex.data;

import com.common.PrintUtil;
import com.dex.stream.DexStreamer;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Created by xueqiulxq on 17/09/2017.
 */

public class StringDataItem {

    public long size;
    public String data;
    // Assistant
    public int ulebLen;

    public static StringDataItem parseFrom(RandomAccessFile racFile, DexStreamer s, long offset) throws IOException {
        StringDataItem data = new StringDataItem();
        // Test the length of uleb
        racFile.seek(offset);
        byte[] leb128 = new byte[5];
        racFile.read(leb128, 0, leb128.length);
        s.use(leb128);
        leb128 = s.readUleb128Bytes();

        // Parse data item
        data.size = s.parseUleb4(leb128);
        racFile.seek(offset + leb128.length);   // Move cursor to the start position of string
        byte[] strBytes = new byte[(int) data.size];
        racFile.read(strBytes, 0, strBytes.length);
        data.data = new String(strBytes);
        return data;
    }

    @Override
    public String toString() {
        return String.format("size=%s\t \"%s\"", PrintUtil.hex4(size), data);
    }
}
