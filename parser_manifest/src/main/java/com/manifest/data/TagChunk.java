package com.manifest.data;

/**
 *
 * Created by xueqiulxq on 23/07/2017.
 */

public class TagChunk {

    public long chunkType;
    public long chunkSize;
    public long lineNumber;
    public long unknown;
    public long nameSpaceUri;
    public long name;           // Tag name index

    // Assistant
    public String nameSpaceUriStr;
    public String nameStr;
    public int chunkIdx;        // Which index does this chunk appeared in the source file.
}
