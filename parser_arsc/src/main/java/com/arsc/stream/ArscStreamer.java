package com.arsc.stream;

import com.common.stream.BaseStreamer;

import java.io.UnsupportedEncodingException;

/**
 *
 * Created by xueqiulxq on 25/07/2017.
 */

public abstract class ArscStreamer extends BaseStreamer {

    public abstract long readUInt();

    public abstract int readUShort();

    public abstract char readChar16();

    public abstract char readChar8();

    public String readNullEndString(int lenInclEnd) {
        byte[] bytes = super.read(lenInclEnd);
        try {
            return new String(bytes, 0, lenInclEnd-1, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new String(bytes, 0, lenInclEnd-1);
        }
    }
}
