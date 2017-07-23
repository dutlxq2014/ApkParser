package com.manifest.data;

import com.common.PrintUtil;
import com.manifest.stream.MfStreamer;

/**
 *
 * Created by xueqiulxq on 24/07/2017.
 */

public class EndNamespaceChunk {

    public long chunkType;
    public long chunkSize;
    public long lineNumber;
    public long unknown;
    public long prefixIdx;
    public long uriIdx;

    // Assistant
    public String prefixStr;
    public String uriStr;

    public static EndNamespaceChunk parseFrom(MfStreamer s, StringChunk stringChunk) {
        EndNamespaceChunk chunk = new EndNamespaceChunk();
        chunk.chunkType = s.readUInt();
        chunk.chunkSize = s.readUInt();
        chunk.lineNumber = s.readUInt();
        chunk.unknown = s.readUInt();
        chunk.prefixIdx = s.readUInt();
        chunk.uriIdx = s.readUInt();

        chunk.prefixStr = stringChunk.getString((int) chunk.prefixIdx);
        chunk.uriStr = stringChunk.getString((int) chunk.uriIdx);
        return chunk;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        String form2 = "%-16s %s\n";
        String form3 = "%-16s %s   %s\n";

        builder.append("-- EndNamespace Chunk --").append('\n');
        builder.append(String.format(form2, "chunkType", PrintUtil.hex4(chunkType)));
        builder.append(String.format(form2, "chunkSize", PrintUtil.hex4(chunkSize)));
        builder.append(String.format(form2, "lineNumber", PrintUtil.hex4(lineNumber)));
        builder.append(String.format(form2, "unknown", PrintUtil.hex4(unknown)));
        builder.append(String.format(form3, "prefixIdx", PrintUtil.hex4(prefixIdx), prefixStr));
        builder.append(String.format(form3, "uriIdx", PrintUtil.hex4(uriIdx), uriStr));
        return builder.toString();
    }
}
