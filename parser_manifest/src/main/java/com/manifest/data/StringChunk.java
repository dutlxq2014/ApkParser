package com.manifest.data;

import com.common.PrintUtil;
import com.manifest.stream.MfStreamer;

/**
 *
 * Created by xueqiulxq on 16/07/2017.
 */

public class StringChunk {

    public long chunkType;
    public long chunkSize;
    public long stringCount;
    public long styleCount;
    public long unknown;
    public long stringPoolOffset;
    public long stylePoolOffset;
    public long[] stringOffset; // Offset of each string
    public long[] styleOffset;  // Offset of each string

    public int[] stringLens;    // Length of each string
    public int[] styleLens;     // Length of each style
    public String[] strings;    // Content of each string
    public String[] styles;     // Content of each style

    public static StringChunk parseFrom(MfStreamer s) {
        StringChunk chunk = new StringChunk();
        // Chunk header
        chunk.chunkType = s.readUInt();
        chunk.chunkSize = s.readUInt();
        chunk.stringCount = s.readUInt();
        chunk.styleCount = s.readUInt();
        chunk.unknown = s.readUInt();
        chunk.stringPoolOffset = s.readUInt();
        chunk.stylePoolOffset = s.readUInt();
        chunk.stringOffset = new long[(int) chunk.stringCount];
        chunk.styleOffset = new long[(int) chunk.styleCount];
        for (int i=0; i<chunk.stringOffset.length; ++i) {
            chunk.stringOffset[i] = s.readUInt();
        }
        for (int i=0; i<chunk.styleOffset.length; ++i) {
            chunk.styleOffset[i] = s.readUInt();
        }
        // String Content
        chunk.strings = new String[(int) chunk.stringCount];
        chunk.stringLens = new int[(int) chunk.stringCount];
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<chunk.stringCount; ++i) {
            builder.setLength(0);
            int len = chunk.stringLens[i] = s.readUShort();   // The leading two bytes are length of string
            for (int j=0; j<len; ++j) {
                builder.append(s.readChar16());
            }
            char end0x0000 = s.readChar16();
            chunk.strings[i] = builder.toString();
        }
        // Style content
        chunk.styles = new String[(int) chunk.styleCount];
        chunk.styleLens = new int[(int) chunk.styleCount];
        for (int i=0; i<chunk.styleCount; ++i) {
            builder.setLength(0);
            int len = chunk.styleLens[i] = s.readUShort();   // The leading two bytes are length of string
            for (int j=0; j<len; ++j) {
                builder.append(s.readChar16());
            }
            char end0x00 = s.readChar16();
            chunk.styles[i] = builder.toString();
        }
        return chunk;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(1024);
        String formH = "%-16s %s\n";

        builder.append("-- String Chunk --").append('\n');
        builder.append(String.format(formH, "chunkType", PrintUtil.hex4(chunkType)));
        builder.append(String.format(formH, "chunkSize", PrintUtil.hex4(chunkSize)));
        builder.append(String.format(formH, "stringCount", PrintUtil.hex4(stringCount)));
        builder.append(String.format(formH, "styleCount", PrintUtil.hex4(styleCount)));
        builder.append(String.format(formH, "unknown", PrintUtil.hex4(unknown)));
        builder.append(String.format(formH, "stringPoolOffset", PrintUtil.hex4(stringPoolOffset)));
        builder.append(String.format(formH, "stylePoolOffset", PrintUtil.hex4(stylePoolOffset)));

        builder.append("|----|----------|-----|---------").append('\n');
        builder.append("| Id |  Offset  | Len | Content").append('\n');
        builder.append("|----|----------|-----|---------").append('\n');
        String formC = "|%-4d| %-8s | %-3d | %s\n";
        for (int i=0; i<stringCount; ++i) {
            builder.append(String.format(formC, i, PrintUtil.hex4(stringOffset[i]), stringLens[i], strings[i]));
        }
        for (int i=0; i<styleCount; ++i) {
            builder.append(String.format(formC, i, PrintUtil.hex4(styleOffset[i]), styleLens[i], styles[i]));
        }

        return builder.toString();
    }
}
