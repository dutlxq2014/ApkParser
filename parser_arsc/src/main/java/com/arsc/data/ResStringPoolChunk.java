package com.arsc.data;

import com.arsc.stream.ArscStreamer;
import com.common.PrintUtil;

import java.util.ArrayList;
import java.util.List;

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
    public long stringsStart;           // Offset from this chunk starting.
    public long stylesStart;
    public long stringOffsetArray[];    // Offset from string pool. The first one is 0x00000000
    public long styleOffsetArray[];
    public List<String> strings;
    public List<String> styles;

    public static ResStringPoolChunk parseFrom(ArscStreamer s) {

        ResStringPoolChunk chunk = new ResStringPoolChunk();
        chunk.header = ChunkHeader.parseFrom(s);
        chunk.stringCount = s.readUInt();
        chunk.styleCount = s.readUInt();
        chunk.flags = s.readUInt();
        chunk.stringsStart = s.readUInt();
        chunk.stylesStart = s.readUInt();
        long[] strOffsets = chunk.stringOffsetArray = new long[(int) chunk.stringCount];
        long[] styleOffsets = chunk.styleOffsetArray = new long[(int) chunk.styleCount];
        List<String> strings = chunk.strings = new ArrayList<String>((int) chunk.stringCount);
        List<String> styles = chunk.styles = new ArrayList<String>((int) chunk.styleCount);

        for (int i=0; i<chunk.stringCount; ++i) {
            strOffsets[i] = s.readUInt();
        }
        for (int i=0; i<chunk.styleCount; ++i) {
            styleOffsets[i] = s.readUInt();
        }
        for (int i=0; i<chunk.stringCount; ++i) {
            int len = (s.readUShort() & 0x7f00) >> 8;
            String str = s.readNullEndString(len + 1); // The last byte is 0x00
            strings.add(str);
        }
        for (int i=0; i<chunk.styleCount; ++i) {

        }

        return chunk;
    }

    public String getString(int idx) {
        return strings != null && idx < strings.size() ? strings.get(idx) : null;
    }

    public String getStyle(int idx) {
        return styles != null && idx < styles.size() ? styles.get(idx) : null;
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

        builder.append(String.format("\nString offset array: %d\n", stringOffsetArray.length));
        for (int i=0; i<stringOffsetArray.length; ++i) {
            builder.append(PrintUtil.hex4(stringOffsetArray[i])).append(" ");
            if ((i+1) % 16 == 0) {
                builder.append('\n');
            }
        }

        builder.append(String.format("\nStyle offset array: %d\n", styleOffsetArray.length));
        for (int i=0; i<styleOffsetArray.length; ++i) {
            builder.append(PrintUtil.hex4(styleOffsetArray[i])).append(" ");
        }

        builder.append(String.format("\nStringPool Content: %d\n", stringCount));
        for (int i=0; i<stringCount; ++i) {
            builder.append(String.format("0x%x: ", i+1)).append(strings.get(i)).append('\n');
        }
        builder.append(String.format("\nStylePools Content: %d\n", styleCount));
        for (int i=0; i<styleCount; ++i) {
            builder.append(styles.get(i)).append("\n");
        }

        return builder.toString();
    }
}
