package com.arsc.data;

import com.arsc.stream.ArscStreamer;

/**
 *
 * Created by xueqiulxq on 26/07/2017.
 */

public class ResTableTypeSpecChunk {

    public static ResTableTypeSpecChunk parseFrom(ArscStreamer s) {
        ResTableTypeSpecChunk chunk = new ResTableTypeSpecChunk();
        return chunk;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(64);
        String form = "%-16s %s\n";

        builder.append("-- ResTableTypeSpec Chunk --").append('\n');
        return builder.toString();
    }
}
