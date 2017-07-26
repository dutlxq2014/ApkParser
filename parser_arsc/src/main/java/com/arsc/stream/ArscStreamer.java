package com.arsc.stream;

import com.common.stream.BaseStreamer;

/**
 *
 * Created by xueqiulxq on 25/07/2017.
 */

public abstract class ArscStreamer extends BaseStreamer {

    public abstract long readUInt();

    public abstract int readUShort();

    public abstract char readChar16();

    public abstract char readChar8();
}
