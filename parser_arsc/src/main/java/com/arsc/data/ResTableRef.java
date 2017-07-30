package com.arsc.data;

import com.arsc.stream.ArscStreamer;

/**
 *
 * Created by xueqiulxq on 29/07/2017.
 */

public class ResTableRef {

    public long ident;

    public static ResTableRef parseFrom(ArscStreamer s) {
        ResTableRef ref = new ResTableRef();
        ref.ident = s.readUInt();
        return ref;
    }
}
