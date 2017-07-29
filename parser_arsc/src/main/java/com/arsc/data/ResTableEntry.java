package com.arsc.data;

/**
 *
 * Created by xueqiulxq on 29/07/2017.
 */

public class ResTableEntry {

    public static final int FLAG_COMPLEX = 0x0001;
    public static final int FLAG_PUBLIC = 0x0002;

    public int size;
    public int flags;
    public ResStringPoolRef key;
}
