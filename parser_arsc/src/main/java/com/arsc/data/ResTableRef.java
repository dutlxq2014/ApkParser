package com.arsc.data;

import com.arsc.stream.ArscStreamer;
import com.common.PrintUtil;

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

    @Override
    public String toString() {
        return String.format("%-10s 0x%s\n", "ident", PrintUtil.hex4(ident));
    }
}
