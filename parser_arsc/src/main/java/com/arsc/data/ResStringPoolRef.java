package com.arsc.data;

import com.arsc.stream.ArscStreamer;
import com.common.PrintUtil;

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

    @Override
    public String toString() {
        String form = "%-10s 0x%s\n";
        return String.format(form, "index", PrintUtil.hex4(index));
    }
}
