package com.arsc.data;

import com.arsc.stream.ArscStreamer;
import com.arsc.stream.LittleEndianStreamer;
import com.common.LogUtil;

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

    public ArscFile() {

    }

    public void parse(RandomAccessFile racFile) throws IOException {
        racFile.seek(0);
        mStreamer = new LittleEndianStreamer();
        long fileLen = racFile.getFilePointer();
        LogUtil.i("fileLen = " + fileLen);

        // Load header first


        long cursor = 0;
        do {
            byte[] headBytes = new byte[ChunkHeader.LENGTH];
            cursor += racFile.read(headBytes, 0, headBytes.length);
            mStreamer.use(headBytes);
            ChunkHeader header = ChunkHeader.parseFrom(mStreamer);

            // Chunk size = ChunkInfo + BodySize
            byte[] chunkBytes = new byte[(int) header.chunkSize];
            System.arraycopy(headBytes, 0, chunkBytes, 0, ChunkHeader.LENGTH);
            cursor += racFile.read(chunkBytes, ChunkHeader.LENGTH, (int)header.chunkSize - ChunkHeader.LENGTH);

            LogUtil.i("fileLen = " + header.headerSize + "  " + header.chunkSize + " " + cursor);

        } while (cursor < fileLen);
    }
}
