package com.elfso.stream;


/**
 *
 * Created by xueqiulxq on 07/07/2017.
 */

public abstract class SectionStreamer {

    private byte[] mData = new byte[8];
    private int cursor = 0;

    public SectionStreamer() {
    }

    public void use(byte[] data) {
        cursor = 0;
        mData = data;
    }

    public byte[] read(int len) {
        if (len + cursor > mData.length) {
            throw new RuntimeException("Section runs out of bound!");
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
