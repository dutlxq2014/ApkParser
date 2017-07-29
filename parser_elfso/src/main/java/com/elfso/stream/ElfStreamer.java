package com.elfso.stream;


import com.common.stream.RandomAccessStreamer;

/**
 *
 * Created by xueqiulxq on 07/07/2017.
 */

public abstract class ElfStreamer extends RandomAccessStreamer {

    public ElfStreamer() {
        super();
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
    public abstract char readUChar();
}
