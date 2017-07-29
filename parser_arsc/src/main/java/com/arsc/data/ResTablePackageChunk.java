package com.arsc.data;

import com.arsc.stream.ArscStreamer;
import com.common.PrintUtil;

/**
 *
 * Created by xueqiulxq on 26/07/2017.
 */

public class ResTablePackageChunk {

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

        // TableTypeSpecType TableTypeType


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

        return builder.toString();
    }
}
