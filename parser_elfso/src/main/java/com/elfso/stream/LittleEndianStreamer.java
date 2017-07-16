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
        return super.readUnsignedInt(Endian.Little);
    }

    @Override
    public int readElf32Half() {
        return super.readUnsignedShort(Endian.Little);
    }

    @Override
    public long readElf32Off() {
        return super.readUnsignedInt(Endian.Little);
    }

    @Override
    public int readElf32Sword() {
        return super.readSignedInt(Endian.Little);
    }

    @Override
    public long readElf32Word() {
        return super.readUnsignedInt(Endian.Little);
    }

    @Override
    public char readUChar() {
        return super.readChar8(Endian.Little);
    }
}
