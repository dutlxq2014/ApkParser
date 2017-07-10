package com.elfso.stream;


import com.common.LogUtil;

/**
 *
 * Created by xueqiulxq on 07/07/2017.
 */

public abstract class ElfStreamer {

    private byte[] mData = new byte[8];
    private int cursor = 0;

    public ElfStreamer() {
    }

    public void use(byte[] data) {
        cursor = 0;
        mData = data;
    }

    public int length() {
        return mData != null ? mData.length : 0;
    }

    public byte[] read(int len) {
        if (cursor >= mData.length) {
            LogUtil.e("Stream has end!!");
            return null;
        }
        if (cursor + len > mData.length) {
            LogUtil.e(String.format("Cannot read %d bytes with only %d remains. Return %bytes!",
                    len, mData.length - cursor, mData.length - cursor));
            len = mData.length - cursor;
        }
        byte[] ret = new byte[len];
        System.arraycopy(mData, cursor, ret, 0, len);
        cursor += len;
        return ret;
    }

    // unsigned int addr 4byte
    public abstract long readElf32Addr();

    // unsigned short half 2byte
    public abstract int readElf32Half();

    // unsinged int off 4byte
    public abstract long readElf32Off();

    // signed int 4byte
    public abstract int readElf32Sword();

    // unsigned int 4byte
    public abstract long readElf32Word();

    // unsinged char 1byte
    public abstract byte readUChar();
}
