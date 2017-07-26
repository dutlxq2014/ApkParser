package com.arsc.data;

import com.arsc.stream.ArscStreamer;
import com.common.PrintUtil;

/**
 *
 * Created by xueqiulxq on 25/07/2017.
 */

public class ChunkHeader {

    public static final int LENGTH = 2 + 2 + 4;
    public int type;
    public int headerSize;
    public long chunkSize;

    public static ChunkHeader parseFrom(ArscStreamer s) {
        ChunkHeader chunk = new ChunkHeader();
        chunk.type = s.readUShort();
        chunk.headerSize = s.readUShort();
        chunk.chunkSize = s.readUInt();
        return chunk;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String form = "%-16s %s\n";

        builder.append(String.format(form, "type", PrintUtil.hex2(type)));
        builder.append(String.format(form, "headerSize", PrintUtil.hex2(headerSize)));
        builder.append(String.format(form, "chunkSize", PrintUtil.hex4(chunkSize)));
        return builder.toString();
    }
}
