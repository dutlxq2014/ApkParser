package com.dex.data;

import com.dex.stream.DexStreamer;

/**
 *
 * Created by xueqiulxq on 10/08/2017.
 */

public class DexHeader {

    public byte[] magic;        // 8B
    public long checksum;       // 4B
    public byte[] signature;    // 20B
    public long fileSize;
    public long headerSize;
    public long endianTag;
    public long linkSize;
    public long linkOff;
    public long mapOff;

    public long stringIdsSize;
    public long stringIdsOff;
    public long typeIdsSize;
    public long typeIdsOff;
    public long protoIdsSize;
    public long protoIdsOff;
    public long methodIdsSize;
    public long methodIdsOff;
    public long classDefsSize;
    public long classDefsOff;
    public long dataSize;
    public long dataOff;

    public static DexHeader parseFrom(DexStreamer s) {
        DexHeader dh = new DexHeader();
        dh.magic = s.read(8);
        dh.checksum = s.readU4();
        dh.signature = s.read(20);
        dh.fileSize = s.readU4();
        dh.headerSize = s.readU4();
        return dh;
    }
}
