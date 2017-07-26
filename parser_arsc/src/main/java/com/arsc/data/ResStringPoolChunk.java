package com.arsc.data;

import com.arsc.stream.ArscStreamer;
import com.common.PrintUtil;

/**
 *
 * Created by xueqiulxq on 26/07/2017.
 */

public class ResStringPoolChunk {

    private static final int SORTED_FLAG = 1 << 0;
    private static final int UTF8_FLAG = 1 << 8;

    public ChunkHeader header;
    public long stringCount;
    public long styleCount;
    public long flags;
    public long stringsStart;
    public long stylesStart;


    public static ResStringPoolChunk parseFrom(ArscStreamer s) {

        ResStringPoolChunk chunk = new ResStringPoolChunk();
        chunk.header = ChunkHeader.parseFrom(s);
        chunk.stringCount = s.readUInt();
        chunk.styleCount = s.readUInt();
        chunk.flags = s.readUInt();
        chunk.stringsStart = s.readUInt();
        chunk.stylesStart = s.readUInt();

        return chunk;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(64);
        String form = "%-16s %s\n";

        builder.append("-- ResStringPool Chunk --").append('\n');
        builder.append(header);
        builder.append(String.format(form, "stringCount", PrintUtil.hex4(stringCount)));
        builder.append(String.format(form, "styleCount", PrintUtil.hex4(styleCount)));
        builder.append(String.format("%-16s %s", "flags", PrintUtil.hex4(flags)));
        builder.append("\tutf8=").append((flags & UTF8_FLAG) != 0 ? "Y" : "N")
                .append("  ").append("sorted=").append((flags & SORTED_FLAG) != 0 ? "Y" : "N").append('\n');
        builder.append(String.format(form, "stringsStart", PrintUtil.hex4(stringsStart)));
        builder.append(String.format(form, "stylesStart", PrintUtil.hex4(stylesStart)));

        return builder.toString();
    }
}
