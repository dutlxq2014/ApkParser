package com.arsc.data;

import com.arsc.stream.ArscStreamer;
import com.common.PrintUtil;

/**
 *
 * Created by xueqiulxq on 26/07/2017.
 */

public class ResFileHeaderChunk {

    public static final int LENGTH = 12;

    public ChunkHeader header;
    public long packageCount;

    public static ResFileHeaderChunk parseFrom(ArscStreamer s) {
        ResFileHeaderChunk chunk = new ResFileHeaderChunk();
        chunk.header = ChunkHeader.parseFrom(s);
        chunk.packageCount = s.readUInt();
        return chunk;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(64);
        String form = "%-16s %s\n";

        builder.append("-- ResTable Chunk --").append('\n');
        builder.append(header);
        builder.append(String.format(form, "packageCount", PrintUtil.hex4(packageCount)));
        return builder.toString();
    }
}
