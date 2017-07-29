package com.arsc.data;

import com.arsc.stream.ArscStreamer;

/**
 *
 * Created by xueqiulxq on 26/07/2017.
 */

public class ResTableTypeTypeChunk {

    public ChunkHeader header;

    public static ResTableTypeTypeChunk parseFrom(ArscStreamer s, ResStringPoolChunk stringChunk) {
        ResTableTypeTypeChunk chunk = new ResTableTypeTypeChunk();
        chunk.header = ChunkHeader.parseFrom(s);
        return chunk;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(64);
        String form = "%-16s %s\n";

        builder.append(header);
        return builder.toString();
    }
}
