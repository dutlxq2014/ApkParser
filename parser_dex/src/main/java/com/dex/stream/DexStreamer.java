package com.dex.stream;

import com.common.stream.RandomAccessStreamer;


/**
 *
 * Created by xueqiulxq on 07/08/2017.
 */

public abstract class DexStreamer extends RandomAccessStreamer {

    public abstract int readU1();

    public abstract int readU2();

    public abstract long readU4();

    public abstract long parseUleb4(byte[] bytes);

    public abstract String readString(int len);
}
