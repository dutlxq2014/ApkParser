package com.dex.stream;

/**
 *
 * Created by xueqiulxq on 07/08/2017.
 */

public class LittleEndianStreamer extends DexStreamer {

    public int readU1() {
        return readUInt8();
    }

    public int readU2() {
        return readUnsignedShort(Endian.Little);
    }

    public long readU4() {
        return readUnsignedInt(Endian.Little);
    }
}
