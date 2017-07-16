package com.manifest.data;

import com.manifest.stream.LittleEndianStreamer;
import com.manifest.stream.MfStreamer;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Created by xueqiulxq on 16/07/2017.
 */

public class MfFile {

    private MfStreamer mStreamer;
    public MfHeader header;
    public StringChunk stringChunk;
    public ResourceIdChunk resourceIdChunk;
    public StartNamespaceChunk startNamespaceChunk;

    public void parse(RandomAccessFile racFile) throws IOException {
        racFile.seek(0);
        mStreamer = new LittleEndianStreamer();

        header = parseHeader(racFile);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(4096);
        builder.append(header).append('\n');

        return builder.toString();
    }

    public MfHeader parseHeader(RandomAccessFile racFile) throws IOException {
        long old = racFile.getFilePointer();
        racFile.seek(0);
        byte[] bytes = new byte[MfHeader.LENGTH];
        racFile.read(bytes, 0, bytes.length);
        mStreamer.use(bytes);
        MfHeader mfHeader = MfHeader.parseFrom(mStreamer);
        racFile.seek(old);
        return mfHeader;
    }


}
