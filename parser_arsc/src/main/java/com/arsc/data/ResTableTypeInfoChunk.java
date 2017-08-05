package com.arsc.data;

import com.arsc.stream.ArscStreamer;
import com.common.PrintUtil;

import java.util.List;

/**
 *
 * Created by xueqiulxq on 26/07/2017.
 */

public class ResTableTypeInfoChunk extends BaseTypeChunk {

    public static final long NO_ENTRY = 0xffffffffL;

    public ChunkHeader header;
    public int typeId;      // 1byte    resource type 0x00ff0000
    public int res0;    // 1byte
    public int res1;    // 2byte
    public long entryCount;
    public long entriesStart;       // start of table entries.
    public ResTableConfig resConfig;

    // Data Block
    public long[] entryOffsets;     // offset of table entries.
    public ResTableEntry[] tableEntries;

    public static ResTableTypeInfoChunk parseFrom(ArscStreamer s, ResStringPoolChunk stringChunk) {
        ResTableTypeInfoChunk chunk = new ResTableTypeInfoChunk();
        int start = s.getCursor();
        chunk.header = ChunkHeader.parseFrom(s);
        chunk.typeId = s.readUInt8();
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
        s.seek(start + chunk.entriesStart); // Locate entry start point.
        for (int i=0; i<chunk.entryCount; ++i) {
            // This is important!
            if (chunk.entryOffsets[i] == NO_ENTRY) {
                continue;
            }

            int cursor = s.getCursor();     // Remember the start cursor
            ResTableEntry entry = ResTableEntry.parseFrom(s);

            s.seek(cursor);                 // Rest cursor
            // We need to parse entry into ResTableMapEntry instead of ResTableMapEntry
            if (entry.flags == ResTableEntry.FLAG_COMPLEX) {
                entry = ResTableMapEntry.parseFrom(s);      // Complex ResTableMapEntry
            } else {
                entry = ResTableValueEntry.parseFrom(s);    // ResTableEntry follows a ResValue
            }
            entry.entryId = i;                 // Remember entry index in tableEntries to recover ids in public.xml
            chunk.tableEntries[i] = entry;
        }

        return chunk;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(64);
        String form = "%-16s %s\n";
        String form3 = "%-16s %s \t%s\n";

        builder.append("-- ResTableTypeInfoChunk --\n");
        builder.append(header);
        builder.append(String.format(form3, "typeId", PrintUtil.hex1(typeId), "/* Reference into ResTablePackage::typeStringPool */"));
        builder.append(String.format(form, "res0", PrintUtil.hex1(res0)));
        builder.append(String.format(form, "res1", PrintUtil.hex1(res1)));
        builder.append(String.format(form, "entryCount", PrintUtil.hex4(entryCount)));
        builder.append(String.format(form, "entriesStart", PrintUtil.hex4(entriesStart)));
        builder.append(resConfig);

        builder.append(String.format("EntryOffset array: length=%d\n", entryOffsets.length));
        for (int i=0; i<entryOffsets.length; ++i) {
            builder.append(PrintUtil.hex4(entryOffsets[i])).append(' ');
            if ((i + 1) % 16 == 0) {
                builder.append('\n');
            }
        }
        if (entryOffsets.length % 16 != 0) {
            builder.append('\n');
        }
        builder.append('\n');

        builder.append(String.format("ResTableEntry array: length=%d\n", tableEntries.length));
        for (int i=0; i<tableEntries.length; ++i) {
            ResTableEntry entry = tableEntries[i];
            if (entry instanceof ResTableMapEntry) {
                builder.append(String.format("ResTableMapEntry ID = %d\n", i));
            } else {
                builder.append(String.format("ResTableValueEntry ID = %d\n", i));
            }
            if (entry == null) {
                builder.append("null: no_entry\n");
            } else {
                builder.append(entry);
            }
            builder.append('\n');
        }

        return builder.toString();
    }

    public String buildEntry2String(int entryId, int packageId, ResStringPoolChunk typeStringPool, ResStringPoolChunk keyStringPool) {
        if (entryId < tableEntries.length && tableEntries[entryId] != null) {
            String typeStr = typeStringPool.getString(typeId - 1);  // from 1
            return tableEntries[entryId].buildEntry2String(packageId, typeId, typeStr, keyStringPool);
        } else {
            //System.out.println("NO_ENTRY for " + typeId + " " + entryId);
            return null;
        }
    }

    public static String uniqueEntries2String(int packageId,
                                              ResStringPoolChunk typeStringPool,
                                              ResStringPoolChunk keyStringPool,
                                              List<ResTableTypeInfoChunk> typeInfos) {
        StringBuilder builder = new StringBuilder();

        int configCount = typeInfos.size();
        int entryCount = (int) typeInfos.get(0).entryCount;

        for (int i=0; i<entryCount; ++i) {
            for (int j=0; j<configCount; ++j) {
                String entryStr = typeInfos.get(j).buildEntry2String(i, packageId, typeStringPool, keyStringPool);
                if (entryStr != null && entryStr.length() > 0) {
                    builder.append(entryStr);
                    break;  // This entryId has done.
                }
            }
        }
        return builder.toString();
    }

    @Override
    public String getChunkName() {
        return "ResTableTypeInfoChunk";
    }

    @Override
    public long getEntryCount() {
        return entryCount;
    }

    @Override
    public String getType() {
        return String.format("0x%s", PrintUtil.hex1(typeId));
    }

    public int getTypeId() {
        return typeId;
    }

    @Override
    public void translateValues(ResStringPoolChunk globalStringPool, ResStringPoolChunk typeStringPool, ResStringPoolChunk keyStringPool) {
        for (ResTableEntry entry : tableEntries) {
            if (entry != null) {
                entry.translateValues(globalStringPool, typeStringPool, keyStringPool);
            }
        }
    }

    public ResTableEntry getResource(int resId) {
        int entryId = resId & 0x0000ffff;
        return entryId < tableEntries.length ? tableEntries[entryId] : null;
    }
}
