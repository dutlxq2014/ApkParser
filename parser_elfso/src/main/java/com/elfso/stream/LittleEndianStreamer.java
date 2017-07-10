package com.elfso.stream;


/**
 *
 * Created by xueqiulxq on 07/07/2017.
 */

public class LittleEndianStreamer extends ElfStreamer {

    public LittleEndianStreamer() {
        super();
    }

    @Override
    public long readElf32Addr() {
        byte[] buf = read(4);
        long ret = 0;
        for (int i=3; i>=0; --i) {
            ret <<= 8;
            ret |= (buf[i] & 0xff);
        }
        return ret;
    }

    @Override
    public int readElf32Half() {
        byte[] buf = read(2);
        return (buf[1] << 8 | buf[0]) & 0x0000ffff;
    }

    @Override
    public long readElf32Off() {
        byte[] buf = read(4);
        long ret = 0;
        for (int i=3; i>=0; --i) {
            ret <<= 8;
            ret |= (buf[i] & 0xff);
        }
        return ret;
    }

    @Override
    public int readElf32Sword() {
        byte[] buf = read(4);
        return buf[3] << 24 | buf[2] << 16 | buf[1] << 8 | buf[0];
    }

    @Override
    public long readElf32Word() {
        byte[] buf = read(4);
        long ret = 0;
        for (int i=3; i>=0; --i) {
            ret <<= 8;
            ret |= (buf[i] & 0xff);
        }
        return ret;
    }

    @Override
    public byte readUChar() {
        return read(1)[0];
    }

}
