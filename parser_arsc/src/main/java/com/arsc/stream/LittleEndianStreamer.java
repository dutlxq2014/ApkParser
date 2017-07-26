package com.arsc.stream;

/**
 *
 * Created by xueqiulxq on 25/07/2017.
 */

public class LittleEndianStreamer extends ArscStreamer {

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
    public char readChar16() {
        return readChar16(Endian.Little);
    }

    @Override
    public char readChar8() {
        return readChar8(Endian.Little);
    }
}
