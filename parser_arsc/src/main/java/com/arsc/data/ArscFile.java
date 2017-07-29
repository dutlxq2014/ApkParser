package com.arsc.data;

import com.arsc.stream.ArscStreamer;
import com.arsc.stream.LittleEndianStreamer;
import com.common.LogUtil;
import com.common.PrintUtil;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Created by xueqiulxq on 26/07/2017.
 */

public class ArscFile {

    private static final int RES_TABLE_TYPE = 0x0002;
    private static final int RES_STRING_POOL_TYPE = 0x0001;
    private static final int RES_TABLE_PACKAGE_TYPE = 0x0200;
    private static final int RES_TABLE_TYPE_SPEC_TYPE = 0x0202;
    private static final int RES_TABLE_TYPE_TYPE = 0x0201;

    private ArscStreamer mStreamer;
    public ResTableChunk arscHeader;
    public ResStringPoolChunk resStringPoolChunk;
    public ResTablePackageChunk resTablePackageChunk;
    public ResTableTypeSpecChunk resTableTypeSpecChunk;
    public ResTableTypeChunk resTableTypeChunk;

    public ArscFile() {

    }

    public void parse(RandomAccessFile racFile) throws IOException {
        racFile.seek(0);
        mStreamer = new LittleEndianStreamer();
        long fileLen = racFile.length();
        LogUtil.i("fileLen = 0x" + PrintUtil.hex4(racFile.length()));

        byte[] headBytes;
        byte[] chunkBytes;
        long cursor = 0;
        // Load header first
        chunkBytes = new byte[ResTableChunk.LENGTH];
        cursor += racFile.read(chunkBytes, 0, chunkBytes.length);
        mStreamer.use(chunkBytes);
        arscHeader = ResTableChunk.parseFrom(mStreamer);

        do {
            headBytes = new byte[ChunkHeader.LENGTH];
            cursor += racFile.read(headBytes, 0, headBytes.length);
            mStreamer.use(headBytes);
            ChunkHeader header = ChunkHeader.parseFrom(mStreamer);

            // Chunk size = ChunkInfo + BodySize
            chunkBytes = new byte[(int) header.chunkSize];
            System.arraycopy(headBytes, 0, chunkBytes, 0, ChunkHeader.LENGTH);
            cursor += racFile.read(chunkBytes, ChunkHeader.LENGTH, (int)header.chunkSize - ChunkHeader.LENGTH);
            LogUtil.i(header.toRowString().replace("\n", ""), "cursor=0x" + PrintUtil.hex4(cursor));

            switch (header.type) {
                case RES_STRING_POOL_TYPE:
                    resStringPoolChunk = parseStringPoolChunk(chunkBytes);
                    break;
                case RES_TABLE_PACKAGE_TYPE:
                    resTablePackageChunk = parseTablePackageChunk(chunkBytes);
                    break;
                case RES_TABLE_TYPE_SPEC_TYPE:
                    resTableTypeSpecChunk = parseTableTypeSpecChunk(chunkBytes);
                    break;
                case RES_TABLE_TYPE_TYPE:
                    resTableTypeChunk = parseTableTypeChunk(chunkBytes);
                    break;
                default:
                    LogUtil.e("Unknown type: 0x" + PrintUtil.hex2(header.type));
            }

        } while (cursor < fileLen);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(4096);
        builder.append(arscHeader).append('\n');
        builder.append(resStringPoolChunk).append('\n');
        builder.append(resTablePackageChunk).append('\n');
        builder.append(resTableTypeSpecChunk).append('\n');
        builder.append(resTableTypeChunk).append('\n');
        return builder.toString();
    }

    private ResStringPoolChunk parseStringPoolChunk(byte[] chunkBytes) {
        mStreamer.use(chunkBytes);
        return ResStringPoolChunk.parseFrom(mStreamer);
    }

    private ResTablePackageChunk parseTablePackageChunk(byte[] chunkBytes) {
        mStreamer.use(chunkBytes);
        return ResTablePackageChunk.parseFrom(mStreamer);
    }

    private ResTableTypeSpecChunk parseTableTypeSpecChunk(byte[] chunkBytes) {
        mStreamer.use(chunkBytes);
        return ResTableTypeSpecChunk.parseFrom(mStreamer);
    }

    private ResTableTypeChunk parseTableTypeChunk(byte[] chunkBytes) {
        mStreamer.use(chunkBytes);
        return ResTableTypeChunk.parseFrom(mStreamer);
    }
}
