package com.manifest.stream;

import com.common.stream.RandomAccessStreamer;

/**
 *
 * Created by xueqiulxq on 16/07/2017.
 */

public abstract class MfStreamer extends RandomAccessStreamer {

    public MfStreamer() {
        super();
    }

    public abstract long readUInt();

    public abstract int readUShort();

    // utf-8 char
    public abstract char readChar8();

    // utf-16 char
    public abstract char readChar16();
}
