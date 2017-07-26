package com.arsc.data;

import com.arsc.stream.ArscStreamer;

/**
 *
 * Created by xueqiulxq on 26/07/2017.
 */

public class ResTablePackageChunk {

    public static ResTablePackageChunk parseFrom(ArscStreamer s) {
        ResTablePackageChunk chunk = new ResTablePackageChunk();
        return chunk;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(64);
        String form = "%-16s %s\n";

        builder.append("-- ResTablePackage Chunk --").append('\n');
        return builder.toString();
    }
}
