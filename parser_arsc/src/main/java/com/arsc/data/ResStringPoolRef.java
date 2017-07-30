package com.arsc.data;

import com.arsc.stream.ArscStreamer;

/**
 *
 * Created by xueqiulxq on 29/07/2017.
 */

public class ResStringPoolRef {

    public long index;

    public static ResStringPoolRef parseFrom(ArscStreamer s) {
        ResStringPoolRef ref = new ResStringPoolRef();
        ref.index = s.readUInt();
        return ref;
    }
}
