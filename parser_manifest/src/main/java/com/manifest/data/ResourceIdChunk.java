package com.manifest.data;

import com.common.PrintUtil;
import com.manifest.stream.MfStreamer;

/**
 *
 * Created by xueqiulxq on 16/07/2017.
 */

public class ResourceIdChunk {

    public long chunkType;
    public long chunkSize;
    public long[] resourceIds;
    // Assistant
    public int numIds = 0;

    public static ResourceIdChunk parseFrom(MfStreamer s) {
        ResourceIdChunk chunk = new ResourceIdChunk();
        chunk.chunkType = s.readUInt();
        chunk.chunkSize = s.readUInt();
        chunk.numIds = (int)((chunk.chunkSize - 8) / 4);
        chunk.resourceIds = new long[chunk.numIds];
        for (int i=0; i<chunk.numIds; ++i) {
            chunk.resourceIds[i] = s.readUInt();
        }
        return chunk;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(512);
        String formH = "%-16s %s\n";

        builder.append("-- ResourceId Chunk --").append('\n');
        builder.append(String.format(formH, "chunkType", PrintUtil.hex4(chunkType)));
        builder.append(String.format(formH, "chunkSize", PrintUtil.hex4(chunkSize)));

        builder.append("|----|------------|----------").append('\n');
        builder.append("| Id |     hex    |    dec   ").append('\n');
        builder.append("|----|------------|----------").append('\n');
        String formC = "|%-4d| 0x%-8s | %8d\n";
        for (int i=0; i<numIds; ++i) {
            builder.append(String.format(formC, i, PrintUtil.hex4(resourceIds[i]), resourceIds[i]));
        }

        return builder.toString();
    }
}
