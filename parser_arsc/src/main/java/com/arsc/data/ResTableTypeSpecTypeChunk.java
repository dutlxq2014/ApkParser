package com.arsc.data;

import com.arsc.stream.ArscStreamer;
import com.common.PrintUtil;

/**
 *
 * Created by xueqiulxq on 26/07/2017.
 */

public class ResTableTypeSpecTypeChunk {

    public ChunkHeader header;
    public int id;      // 1byte
    public int res0;    // 1byte
    public int res1;    // 2byte
    public long entryCount;
    public long entriesStart;
    public ResTableConfig resConfig;

    public static ResTableTypeSpecTypeChunk parseFrom(ArscStreamer s, ResStringPoolChunk stringChunk) {
        ResTableTypeSpecTypeChunk chunk = new ResTableTypeSpecTypeChunk();
        chunk.header = ChunkHeader.parseFrom(s);
        chunk.id = s.readUint8();
        chunk.res0 = s.readUint8();
        chunk.res1 = s.readUShort();
        chunk.entryCount = s.readUInt();

        return chunk;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(64);
        String form = "%-16s %s\n";

        builder.append(header);
        builder.append(String.format(form, "id", PrintUtil.hex1(id)));
        builder.append(String.format(form, "res0", PrintUtil.hex1(res0)));
        builder.append(String.format(form, "res1", PrintUtil.hex1(res1)));
        builder.append(String.format(form, "entryCount", PrintUtil.hex4(entryCount)));


        return builder.toString();
    }
}
