package com.arsc.data;

import com.arsc.stream.ArscStreamer;

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
}
