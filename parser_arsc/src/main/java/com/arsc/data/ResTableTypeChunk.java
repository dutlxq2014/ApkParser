package com.arsc.data;

import com.arsc.stream.ArscStreamer;

/**
 *
 * Created by xueqiulxq on 26/07/2017.
 */

public class ResTableTypeChunk {


    public static ResTableTypeChunk parseFrom(ArscStreamer s) {
        ResTableTypeChunk chunk = new ResTableTypeChunk();
        return chunk;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(64);
        String form = "%-16s %s\n";

        builder.append("-- ResTableType Chunk --").append('\n');
        return builder.toString();
    }
}
