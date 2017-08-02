package com.arsc.data;

import com.arsc.stream.ArscStreamer;
import com.common.LogUtil;
import com.common.PrintUtil;

import java.util.ArrayList;
import java.util.List;

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
    public long id;
    public String packageName;
    public long typeStringOffest;   // Offset in this chunk
    public long lastPublicType;     // Num of type string
    public long keyStringOffset;    // Offset in chunk
    public long lastPublicKey;      // Num of key string

    // DataBlock
    public ResStringPoolChunk typeStringPool;
    public ResStringPoolChunk keyStringPool;
    public List<BaseTypeChunk> typeChunks;

    public static ResTablePackageChunk parseFrom(ArscStreamer s, ResStringPoolChunk stringChunk) {
        ResTablePackageChunk chunk = new ResTablePackageChunk();
        chunk.header = ChunkHeader.parseFrom(s);
        chunk.id = s.readUInt();
        chunk.packageName = s.readNullEndString16(128 * 2);
        chunk.typeStringOffest = s.readUInt();
        chunk.lastPublicType = s.readUInt();
        chunk.keyStringOffset = s.readUInt();
        chunk.lastPublicKey = s.readUInt();
        s.read(4);  // Skip

        // Data Block
        s.seek(chunk.typeStringOffest);
        chunk.typeStringPool = ResStringPoolChunk.parseFrom(s);
        s.seek(chunk.keyStringOffset);
        chunk.keyStringPool = ResStringPoolChunk.parseFrom(s);

        // TableTypeSpecType   TableTypeType
        s.seek(chunk.keyStringOffset + chunk.keyStringPool.header.chunkSize);
        chunk.typeChunks = new ArrayList<BaseTypeChunk>();
        int resCount = 0;
        while (s.getCursor() < s.length()) {

            resCount++;
            ChunkHeader header = ChunkHeader.parseFrom(s);
            s.seek(s.getCursor() - ChunkHeader.LENGTH);

            BaseTypeChunk typeChunk = null;
            if (header.type == RES_TABLE_TYPE_SPEC_TYPE) {
                LogUtil.i(TAG, "RES_TABLE_TYPE_SPEC_TYPE");
                typeChunk = ResTableTypeSpecChunk.parseFrom(s, stringChunk);
            } else if (header.type == RES_TABLE_TYPE_TYPE){
                LogUtil.i(TAG, "RES_TABLE_TYPE_TYPE");
                typeChunk = ResTableTypeInfoChunk.parseFrom(s, stringChunk);
            } else {
                LogUtil.e(TAG, "None TableTypeSpecType or TableTypeType!!");
            }
            chunk.typeChunks.add(typeChunk);
        }

        return chunk;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(64);
        String form = "%-16s %s\n";

        builder.append("-- ResTablePackage Chunk --").append('\n');
        builder.append(header);
        builder.append(String.format(form, "id", PrintUtil.hex4(id)));
        builder.append(String.format(form, "packageName", packageName));
        builder.append(String.format(form, "typeStringOffest", PrintUtil.hex4(typeStringOffest)));
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
        for (int i=0; i<typeChunks.size(); ++i) {
            String entry = typeChunks.get(i).buildEntry2String(typeStringPool, keyStringPool);
            entry = entry.replace("\n", "\n\t");
            builder.append(entry);
        }
        builder.setLength(builder.length() - 1);
        builder.append("</resources>");
        return builder.toString();
    }
}
