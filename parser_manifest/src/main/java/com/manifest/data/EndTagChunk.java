package com.manifest.data;

import com.common.PrintUtil;
import com.manifest.stream.MfStreamer;

/**
 *
 * Created by xueqiulxq on 19/07/2017.
 */

public class EndTagChunk {

    public long chunkType;
    public long chunkSize;
    public long lineNumber;
    public long unknown;
    public long nameSpaceUri;
    public long name;           // Tag name index

    // Assistant
    public String nameSpaceUriStr;
    public String nameStr;

    public static EndTagChunk parseFrom(MfStreamer s, StringChunk stringChunk) {
        EndTagChunk chunk = new EndTagChunk();
        chunk.chunkType = s.readUInt();
        chunk.chunkSize = s.readUInt();
        chunk.lineNumber = s.readUInt();
        chunk.unknown = s.readUInt();
        chunk.nameSpaceUri = s.readUInt();
        chunk.name = s.readUInt();

        // Fill data
        chunk.nameSpaceUriStr = stringChunk.getString((int) chunk.nameSpaceUri);
        chunk.nameStr = stringChunk.getString((int) chunk.name);
        return chunk;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(512);
        String form2 = "%-16s %s\n";
        String form3 = "%-16s %-16s %s\n";

        builder.append(String.format(form2, "chunkType", PrintUtil.hex4(chunkType)));
        builder.append(String.format(form2, "chunkSize", PrintUtil.hex4(chunkSize)));
        builder.append(String.format(form2, "lineNumber", PrintUtil.hex4(lineNumber)));
        builder.append(String.format(form2, "unknown", PrintUtil.hex4(unknown)));
        builder.append(String.format(form3, "nameSpaceUri", PrintUtil.hex4(nameSpaceUri), nameSpaceUriStr));
        builder.append(String.format(form3, "name", PrintUtil.hex4(name), nameStr));
        return builder.toString();
    }
}
