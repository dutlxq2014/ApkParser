package com.arsc.data;

import com.arsc.stream.ArscStreamer;
import com.common.LogUtil;
import com.common.PrintUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by xueqiulxq on 26/07/2017.
 */

public class ResTablePackageChunk {

    public static final String TAG = ResTablePackageChunk.class.getSimpleName();

    public static final int RES_TABLE_TYPE_SPEC_TYPE = 0x0202;
    public static final int RES_TABLE_TYPE_TYPE = 0x0201;

    // Header Block 0x0120
    public ChunkHeader header;
    public long pkgId;                 // 0x0000007f->UserResources  0x00000001->SystemResources
    public String packageName;
    public long typeStringOffset;   // Offset in this chunk
    public long lastPublicType;     // Num of type string
    public long keyStringOffset;    // Offset in chunk
    public long lastPublicKey;      // Num of key string

    // DataBlock
    public ResStringPoolChunk typeStringPool;
    public ResStringPoolChunk keyStringPool;
    public List<BaseTypeChunk> typeChunks;

    // Create Index
    public Map<Integer, List<BaseTypeChunk>> typeInfoIndexer;

    public static ResTablePackageChunk parseFrom(ArscStreamer s, ResStringPoolChunk stringChunk) {
        ResTablePackageChunk chunk = new ResTablePackageChunk();
        chunk.header = ChunkHeader.parseFrom(s);
        chunk.pkgId = s.readUInt();
        chunk.packageName = s.readNullEndString16(128 * 2);
        chunk.typeStringOffset = s.readUInt();
        chunk.lastPublicType = s.readUInt();
        chunk.keyStringOffset = s.readUInt();
        chunk.lastPublicKey = s.readUInt();

        // Data Block
        s.seek(chunk.typeStringOffset);
        chunk.typeStringPool = ResStringPoolChunk.parseFrom(s);
        s.seek(chunk.keyStringOffset);
        chunk.keyStringPool = ResStringPoolChunk.parseFrom(s);

        // TableTypeSpecType   TableTypeType
        s.seek(chunk.keyStringOffset + chunk.keyStringPool.header.chunkSize);
        chunk.typeChunks = new ArrayList<BaseTypeChunk>();
        int resCount = 0;
        StringBuilder logInfo = new StringBuilder();
        while (s.getCursor() < s.length()) {

            logInfo.setLength(0);
            resCount++;
            ChunkHeader header = ChunkHeader.parseFrom(s);
            s.seek(s.getCursor() - ChunkHeader.LENGTH);

            BaseTypeChunk typeChunk = null;
            if (header.type == RES_TABLE_TYPE_SPEC_TYPE) {
                typeChunk = ResTableTypeSpecChunk.parseFrom(s, stringChunk);
            } else if (header.type == RES_TABLE_TYPE_TYPE){
                typeChunk = ResTableTypeInfoChunk.parseFrom(s, stringChunk);
            }
            if (typeChunk != null) {
                logInfo.append(typeChunk.getChunkName()).append(" ")
                        .append(String.format("type=%s ", typeChunk.getType()))
                        .append(String.format("count=%s ", typeChunk.getEntryCount()));
            } else {
                logInfo.append("None TableTypeSpecType or TableTypeType!!");
            }
            LogUtil.i(TAG, logInfo.toString());
            chunk.typeChunks.add(typeChunk);
        }

        chunk.createResourceIndex();
        for (int i=0; i<chunk.typeChunks.size(); ++i) {
            chunk.typeChunks.get(i).translateValues(stringChunk, chunk.typeStringPool, chunk.keyStringPool);
        }

        return chunk;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(64);
        String form = "%-16s %s\n";

        builder.append("-- ResTablePackage Chunk --").append('\n');
        builder.append(header);
        builder.append(String.format(form, "pkgId", PrintUtil.hex4(pkgId)));
        builder.append(String.format(form, "packageName", packageName));
        builder.append(String.format(form, "typeStringOffset", PrintUtil.hex4(typeStringOffset)));
        builder.append(String.format(form, "lastPublicType", PrintUtil.hex4(lastPublicType)));
        builder.append(String.format(form, "keyStringOffset", PrintUtil.hex4(keyStringOffset)));
        builder.append(String.format(form, "lastPublicKey", PrintUtil.hex4(lastPublicKey)));

        // Data block
        builder.append("\n-- TypeStringPool in ResTablePackage Chunk --\n");
        builder.append(typeStringPool);
        builder.append("\n-- KeyStringPool in ResTablePackage Chunk --\n");
        builder.append(keyStringPool);

        // TypeSpec and TypeInfo chunks.
        builder.append("\n-- TypeSpec and TypeInfo chunks --\n");
        for (int i=0; i<typeChunks.size(); ++i) {
            BaseTypeChunk chunk = typeChunks.get(i);
            builder.append("Sequence ID = ").append(i).append(" : ").append(chunk.getChunkName()).append("\n");
            builder.append(chunk);
            builder.append('\n');
        }

        return builder.toString();
    }

    public String buildEntry2String() {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        builder.append("<resources>\n\t");

        for (int i = 0; i<typeChunks.size(); ++i) {
            // All entries exist in ResTableTypeInfoChunk
            if (typeChunks.get(i) instanceof ResTableTypeSpecChunk) {
                // Extract following ResTableTypeInfoChunks
                List<ResTableTypeInfoChunk> typeInfos = new ArrayList<ResTableTypeInfoChunk>();
                for (int j = i + 1; j<typeChunks.size(); ++j) {
                    if (typeChunks.get(j) instanceof ResTableTypeInfoChunk) {
                        typeInfos.add((ResTableTypeInfoChunk) typeChunks.get(j));
                    } else {
                        break;
                    }
                }
                i += typeInfos.size();
                // Unique ResTableTypeInfoChunks
                String entry = ResTableTypeInfoChunk.uniqueEntries2String((int) pkgId & 0xff, typeStringPool, keyStringPool, typeInfos);
                builder.append(entry.replace("\n", "\n\t"));
            }
        }

        builder.setLength(builder.length() - 1);
        builder.append("</resources>");
        return builder.toString();
    }

    private void createResourceIndex() {
        typeInfoIndexer = new HashMap<Integer, List<BaseTypeChunk>>();
        for (BaseTypeChunk typeChunk : typeChunks) {
            // The first chunk in typeList should be ResTableTypeSpecChunk
            List<BaseTypeChunk> typeList = typeInfoIndexer.get(typeChunk.getTypeId());
            if (typeList == null) {
                typeList = new ArrayList<BaseTypeChunk>();
                typeInfoIndexer.put(typeChunk.getTypeId(), typeList);
            }
            typeList.add(typeChunk);
        }
    }

    public ResTableEntry getResource(int resId) {
        int typeId = (resId & 0x00ff0000) >> 16;
        List<BaseTypeChunk> typeList = typeInfoIndexer.get(typeId); // The first chunk in typeList should be ResTableTypeSpecChunk
        for (int i=1; i<typeList.size(); ++i) {
            if (typeList.get(i) instanceof ResTableTypeInfoChunk) {
                ResTableEntry entry = ((ResTableTypeInfoChunk) typeList.get(i)).getResource(resId);
                if (entry != null) {
                    return entry;
                }
            }
        }
        return null;
    }
}
