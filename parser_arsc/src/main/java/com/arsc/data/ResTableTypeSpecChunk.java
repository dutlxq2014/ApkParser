package com.arsc.data;

import com.arsc.stream.ArscStreamer;
import com.common.PrintUtil;

/**
 *
 * Created by xueqiulxq on 26/07/2017.
 */

public class ResTableTypeSpecChunk extends BaseTypeChunk {

    public ChunkHeader header;
    public int id;      // 1byte
    public int res0;    // 1byte
    public int res1;    // 2byte
    public long entryCount;
    public long[] entryConfig;

    public static ResTableTypeSpecChunk parseFrom(ArscStreamer s, ResStringPoolChunk stringChunk) {
        ResTableTypeSpecChunk chunk = new ResTableTypeSpecChunk();
        chunk.header = ChunkHeader.parseFrom(s);
        chunk.id = s.readUInt8();
        chunk.res0 = s.readUInt8();
        chunk.res1 = s.readUShort();
        chunk.entryCount = s.readUInt();
        chunk.entryConfig = new long[(int) chunk.entryCount];

        for (int i=0; i<chunk.entryCount; ++i) {
            chunk.entryConfig[i] = s.readUInt();
        }
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

        builder.append(String.format("entryConfig array: length=%d\n", entryCount));
        for (int i=0; i<entryCount; ++i) {
            builder.append(PrintUtil.hex4(entryConfig[i])).append(" ");
            if ((i + 1) % 16 == 0) {
                builder.append('\n');
            }
        }
        if (entryCount % 16 != 0) {
            builder.append('\n');
        }

        return builder.toString();
    }

    @Override
    public String buildEntry2String(ResStringPoolChunk typeStringPool, ResStringPoolChunk keyStringPool) {
        return "";
    }

    @Override
    public String getChunkName() {
        return "ResTableTypeSpecChunk";
    }

    @Override
    public long getEntryCount() {
        return entryCount;
    }

    @Override
    public String getType() {
        return String.format("0x%s", PrintUtil.hex1(id));
    }
}
