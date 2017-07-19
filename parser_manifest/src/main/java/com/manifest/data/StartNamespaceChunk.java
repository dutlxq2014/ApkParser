package com.manifest.data;

import com.common.PrintUtil;
import com.manifest.stream.MfStreamer;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by xueqiulxq on 16/07/2017.
 */

public class StartNamespaceChunk {

    public long chunkType;
    public long chunkSize;
    public long lineNumber;
    public long unknown;
    public long prefixIdx;
    public long uriIdx;

    public String prefixStr;
    public String uriStr;
    public Map<String, String> uri2prefixMap;
    public Map<String, String> prefix2UriMap;

    public static StartNamespaceChunk parseFrom(MfStreamer s, StringChunk stringChunk) {
        StartNamespaceChunk chunk = new StartNamespaceChunk();
        // Meta data
        chunk.chunkType = s.readUInt();
        chunk.chunkSize = s.readUInt();
        chunk.lineNumber = s.readUInt();
        chunk.unknown = s.readUInt();
        chunk.prefixIdx = s.readUInt();
        chunk.uriIdx = s.readUInt();
        // Fill data
        chunk.prefixStr = stringChunk.getString((int) chunk.prefixIdx);
        chunk.uriStr = stringChunk.getString((int) chunk.uriIdx);
        chunk.uri2prefixMap = new HashMap<String, String>();
        chunk.prefix2UriMap = new HashMap<String, String>();
        chunk.uri2prefixMap.put(chunk.uriStr, chunk.prefixStr);
        chunk.prefix2UriMap.put(chunk.prefixStr, chunk.uriStr);
        return chunk;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(256);
        String formH = "%-16s %s\n";

        builder.append("-- StartNamespace Chunk --").append('\n');
        builder.append(String.format(formH, "chunkType", PrintUtil.hex4(chunkType)));
        builder.append(String.format(formH, "chunkSize", PrintUtil.hex4(chunkSize)));
        builder.append(String.format(formH, "lineNumber", PrintUtil.hex4(lineNumber)));
        builder.append(String.format(formH, "unknown", PrintUtil.hex4(unknown)));
        builder.append(String.format(formH, "prefixIdx", PrintUtil.hex4(prefixIdx)));
        builder.append(String.format(formH, "uriIdx", PrintUtil.hex4(uriIdx)));
        builder.append(String.format(formH, "prefixStr", prefixStr));
        builder.append(String.format(formH, "uriStr", uriStr));
        builder.append("--------------------------\n");
        for (Map.Entry<String, String> entry : prefix2UriMap.entrySet()) {
            builder.append("xmlns:").append(entry.getKey()).append("=").append(entry.getValue()).append('\n');
        }
        return builder.toString();
    }
}
