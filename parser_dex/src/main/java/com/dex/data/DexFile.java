package com.dex.data;

import com.dex.stream.DexStreamer;
import com.dex.stream.LittleEndianStreamer;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Created by xueqiulxq on 07/08/2017.
 */

public class DexFile {

    private DexStreamer mStreamer;
    public DexHeader dexHeader;

    public void parse(RandomAccessFile racFile) throws IOException {
        racFile.seek(0);
        mStreamer = new LittleEndianStreamer();

        byte[] headerBytes;
        byte[] bodyBytes;
        long cursor = 0;

        headerBytes = new byte[DexHeader.LENGTH];
        cursor += racFile.read(headerBytes, 0, headerBytes.length);
        mStreamer.use(headerBytes);
        dexHeader = DexHeader.parseFrom(mStreamer);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(dexHeader);

        return builder.toString();
    }
}
