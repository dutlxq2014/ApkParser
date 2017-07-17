package com.manifest.data;

import com.manifest.stream.MfStreamer;

/**
 * Created by xueqiulxq on 17/07/2017.
 */

public class ChunkInfo {

    public static final int LENGTH = 8;

    public long chunkType;
    public long chunkSize;

    public static ChunkInfo parseFrom(MfStreamer s) {
        ChunkInfo chunk = new ChunkInfo();
        chunk.chunkType = s.readUInt();
        chunk.chunkSize = s.readUInt();
        return chunk;
    }
}
