package com.manifest.stream;

import com.common.stream.BaseStreamer;

/**
 *
 * Created by xueqiulxq on 16/07/2017.
 */

public abstract class MfStreamer extends BaseStreamer {

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
