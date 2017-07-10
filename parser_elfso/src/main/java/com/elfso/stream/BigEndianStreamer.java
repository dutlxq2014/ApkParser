package com.elfso.stream;


/**
 *
 * Created by xueqiulxq on 07/07/2017.
 */

public class BigEndianStreamer extends ElfStreamer {

    public BigEndianStreamer() {
        super();
    }

    @Override
    public long readElf32Addr() {
        byte[] buf = read(4);
        return buf[0] << 24 | buf[1] << 16 | buf[2] << 8 | buf[3];
    }

    @Override
    public int readElf32Half() {
        byte[] buf = read(2);
        return buf[0] << 8 | buf[1];
    }

    @Override
    public long readElf32Off() {
        byte[] buf = read(4);
        return buf[0] << 24 | buf[1] << 16 | buf[2] << 8 | buf[3];
    }

    @Override
    public int readElf32Sword() {
        byte[] buf = read(4);
        return buf[0] << 24 | buf[1] << 16 | buf[2] << 8 | buf[3];
    }

    @Override
    public long readElf32Word() {
        byte[] buf = read(4);
        return buf[0] << 24 | buf[1] << 16 | buf[2] << 8 | buf[3];
    }

    @Override
    public byte readUChar() {
        return read(1)[0];
    }

}
