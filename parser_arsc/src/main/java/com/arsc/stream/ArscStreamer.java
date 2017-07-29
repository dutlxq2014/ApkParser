package com.arsc.stream;

import com.common.stream.RandomAccessStreamer;

/**
 *
 * Created by xueqiulxq on 25/07/2017.
 */

public abstract class ArscStreamer extends RandomAccessStreamer {

    public abstract long readUInt();

    public abstract int readUShort();

    public abstract char readChar16();

    public abstract char readChar8();

    public abstract String readNullEndString(int lenInclEnd);

    public abstract String readNullEndString16(int lenInclEnd);
}
