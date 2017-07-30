package com.arsc.data;

import com.arsc.stream.ArscStreamer;

/**
 *
 * Created by xueqiulxq on 26/07/2017.
 */

public class ResTableTypeInfoChunk extends BaseTypeChunk {

    public static final long NO_ENTRY = 0xffffffffL;

    public ChunkHeader header;
    public int id;      // 1byte
    public int res0;    // 1byte
    public int res1;    // 2byte
    public long entryCount;
    public long entriesStart;       // start of table entries.
    public ResTableConfig resConfig;
    // DataBlock
    public long[] entryOffsets;     // offset of table entries.
    public ResTableEntry[] tableEntries;
    public ResValue[] resValues;    // direct value;

    public static ResTableTypeInfoChunk parseFrom(ArscStreamer s, ResStringPoolChunk stringChunk) {
        ResTableTypeInfoChunk chunk = new ResTableTypeInfoChunk();
        int start = s.getCursor();
        chunk.header = ChunkHeader.parseFrom(s);
        chunk.id = s.readUInt8();
        chunk.res0 = s.readUInt8();
        chunk.res1 = s.readUShort();
        chunk.entryCount = s.readUInt();
        chunk.entriesStart = s.readUInt();
        chunk.resConfig = ResTableConfig.parseFrom(s);

        chunk.entryOffsets = new long[(int) chunk.entryCount];
        for (int i=0; i<chunk.entryCount; ++i) {
            chunk.entryOffsets[i] = s.readUInt();
        }

        chunk.tableEntries = new ResTableEntry[(int) chunk.entryCount];
        chunk.resValues = new ResValue[(int) chunk.entryCount];
        s.seek(start + chunk.entriesStart); // Locate entry start point.
        for (int i=0; i<chunk.entryCount; ++i) {
            if (chunk.entryOffsets[i] == NO_ENTRY) {
                continue;
            }

            int cursor = s.getCursor();     // Remember the start cursor
            ResTableEntry entry = ResTableEntry.parseFrom(s);

            // We need to parse entry into ResTableMapEntry instead of ResTableEntry
            if (entry.flags == 0x01) {
                s.seek(cursor);             // Rest cursor
                entry = ResTableMapEntry.parseFrom(s);      // Complex ResTableMapEntry
            } else {
                chunk.resValues[i] = ResValue.parseFrom(s); // ResTableEntry follows a ResValue
            }
            chunk.tableEntries[i] = entry;
        }

        return chunk;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(64);
        String form = "%-16s %s\n";

        builder.append(header);
        return builder.toString();
    }
}
