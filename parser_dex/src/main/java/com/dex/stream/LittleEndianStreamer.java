package com.dex.stream;

import java.io.UnsupportedEncodingException;

/**
 *
 * Created by xueqiulxq on 07/08/2017.
 */

public class LittleEndianStreamer extends DexStreamer {

    @Override
    public int readU1() {
        return readUInt8();
    }

    @Override
    public int readU2() {
        return readUnsignedShort(Endian.Little);
    }

    @Override
    public long readU4() {
        return readUnsignedInt(Endian.Little);
    }

    @Override
    public long parseUleb4(byte[] bytes) {
        return parseUleb128Int(bytes, Endian.Little);
    }

    @Override
    public String readString(int len) {
        byte[] bytes = read(len);
        try {
            return new String(bytes, 0, bytes.length, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new String(bytes, 0, bytes.length);
        }
    }
}
