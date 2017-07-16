package com.manifest.stream;

/**
 *
 * Created by xueqiulxq on 16/07/2017.
 */

public class LittleEndianStreamer extends MfStreamer {

    public LittleEndianStreamer() {
        super();
    }

    @Override
    public long readUInt() {
        return super.readUnsignedInt(Endian.Little);
    }

    @Override
    public int readUShort() {
        return super.readUnsignedShort(Endian.Little);
    }

    @Override
    public char readChar8() {
        return super.readChar8(Endian.Little);
    }

    @Override
    public char readChar16() {
        return super.readChar16(Endian.Little);
    }
}
