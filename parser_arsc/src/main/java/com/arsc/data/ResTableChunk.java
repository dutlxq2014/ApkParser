package com.arsc.data;

import com.arsc.stream.ArscStreamer;

/**
 *
 * Created by xueqiulxq on 26/07/2017.
 */

public class ResTableChunk {

    public ChunkHeader header;
    public long packageCount;

    public static ResTableChunk parseFrom(ArscStreamer s) {
        ResTableChunk chunk = new ResTableChunk();
        chunk.header = ChunkHeader.parseFrom(s);
        chunk.packageCount = s.readUInt();
        return chunk;
    }
}
