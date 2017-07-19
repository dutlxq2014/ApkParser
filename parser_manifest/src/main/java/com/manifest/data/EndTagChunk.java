package com.manifest.data;

import com.manifest.stream.MfStreamer;

/**
 *
 * Created by xueqiulxq on 19/07/2017.
 */

public class EndTagChunk {

    public static EndTagChunk parseFrom(MfStreamer s, StringChunk stringChunk) {
        EndTagChunk chunk = new EndTagChunk();
        return chunk;
    }
}
