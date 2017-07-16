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
        return super.readUnsignedInt(Endian.Big);
    }

    @Override
    public int readElf32Half() {
        return super.readUnsignedShort(Endian.Big);
    }

    @Override
    public long readElf32Off() {
        return super.readUnsignedInt(Endian.Big);
    }

    @Override
    public int readElf32Sword() {
        return super.readSignedInt(Endian.Big);
    }

    @Override
    public long readElf32Word() {
        return super.readUnsignedInt(Endian.Big);
    }

    @Override
    public char readUChar() {
        return super.readChar8(Endian.Big);
    }
}
