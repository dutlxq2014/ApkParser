package com.arsc.stream;

import java.io.UnsupportedEncodingException;

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

    @Override
    public String readNullEndString(int lenInclEnd) {
        byte[] bytes = super.read(lenInclEnd);
        try {
            return new String(bytes, 0, lenInclEnd-1, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new String(bytes, 0, lenInclEnd-1);
        }
    }

    @Override
    public String readNullEndString16(int lenInclEnd) {
        StringBuilder builder = new StringBuilder(lenInclEnd / 2);
        char c;
        while ((lenInclEnd -= 2) >= 0 && (c = readChar16()) != 0) {
            builder.append(c);
        }
        if (lenInclEnd > 0) {
            read(lenInclEnd);   // Skip remained nulls.
        }
        return builder.toString();
    }
}
