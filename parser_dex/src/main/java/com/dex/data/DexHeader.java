package com.dex.data;

import com.common.PrintUtil;
import com.dex.stream.DexStreamer;

/**
 *
 * Created by xueqiulxq on 10/08/2017.
 */

public class DexHeader {

    public static final int LENGTH = 0x70;

    public byte[] magic;        // 8B
    public long checksum;       // 4B
    public byte[] signature;    // 20B
    public long fileSize;       // 4B
    public long headerSize;     // 4B
    public long endianTag;      // 4B
    public long linkSize;       // 4B
    public long linkOff;        // 4B
    public long mapOff;         // 4B

    public long stringIdsSize;  // 4B
    public long stringIdsOff;   // 4B
    public long typeIdsSize;    // 4B
    public long typeIdsOff;     // 4B
    public long protoIdsSize;   // 4B
    public long protoIdsOff;    // 4B
    public long filedIdsSize;   // 4B
    public long filedIdsOff;    // 4B
    public long methodIdsSize;  // 4B
    public long methodIdsOff;   // 4B
    public long classDefsSize;  // 4B
    public long classDefsOff;   // 4B
    public long dataSize;       // 4B
    public long dataOff;        // 4B

    public static DexHeader parseFrom(DexStreamer s) {
        DexHeader dh = new DexHeader();
        dh.magic = s.read(8);
        dh.checksum = s.readU4();
        dh.signature = s.read(20);
        dh.fileSize = s.readU4();
        dh.headerSize = s.readU4();
        dh.endianTag = s.readU4();
        dh.linkSize = s.readU4();
        dh.linkOff = s.readU4();
        dh.mapOff = s.readU4();

        dh.stringIdsSize = s.readU4();
        dh.stringIdsOff = s.readU4();
        dh.typeIdsSize = s.readU4();
        dh.typeIdsOff = s.readU4();
        dh.protoIdsSize = s.readU4();
        dh.protoIdsOff = s.readU4();
        dh.filedIdsSize = s.readU4();
        dh.filedIdsOff = s.readU4();
        dh.methodIdsSize = s.readU4();
        dh.methodIdsOff = s.readU4();
        dh.classDefsSize = s.readU4();
        dh.classDefsOff = s.readU4();
        dh.dataSize = s.readU4();
        dh.dataOff = s.readU4();
        return dh;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String form = "|%-14s |0x%s\n";
        String form3 = "|%-14s |0x%s     |%d\n";

        builder.append("-- Dex File Header --\n");
        builder.append("|    Fields     |     Hex       |   Decimal\n");
        builder.append(String.format(form, "magic", PrintUtil.hex(magic)));
        builder.append(String.format(form, "checksum", PrintUtil.hex4(checksum)));
        builder.append(String.format(form, "signature", PrintUtil.hex(signature)));
        builder.append(String.format(form3, "fileSize", PrintUtil.hex4(fileSize), fileSize));
        builder.append(String.format(form3, "headerSize", PrintUtil.hex4(headerSize), headerSize));
        builder.append(String.format(form3, "endianTag", PrintUtil.hex4(endianTag), endianTag));
        builder.append(String.format(form3, "linkSize", PrintUtil.hex4(linkSize), linkSize));
        builder.append(String.format(form3, "linkOff", PrintUtil.hex4(linkOff), linkOff));
        builder.append(String.format(form3, "mapOff", PrintUtil.hex4(mapOff), mapOff));

        builder.append(String.format(form3, "stringIdsSize", PrintUtil.hex4(stringIdsSize), stringIdsSize));
        builder.append(String.format(form3, "stringIdsOff", PrintUtil.hex4(stringIdsOff), stringIdsOff));
        builder.append(String.format(form3, "typeIdsSize", PrintUtil.hex4(typeIdsSize), typeIdsSize));
        builder.append(String.format(form3, "typeIdsOff", PrintUtil.hex4(typeIdsOff), typeIdsOff));
        builder.append(String.format(form3, "protoIdsSize", PrintUtil.hex4(protoIdsSize), protoIdsSize));
        builder.append(String.format(form3, "protoIdsOff", PrintUtil.hex4(protoIdsOff), protoIdsOff));
        builder.append(String.format(form3, "filedIdsSize", PrintUtil.hex4(filedIdsSize), filedIdsSize));
        builder.append(String.format(form3, "filedIdsOff", PrintUtil.hex4(filedIdsOff), filedIdsOff));
        builder.append(String.format(form3, "methodIdsSize", PrintUtil.hex4(methodIdsSize), methodIdsSize));
        builder.append(String.format(form3, "methodIdsOff", PrintUtil.hex4(methodIdsOff), methodIdsOff));
        builder.append(String.format(form3, "classDefsSize", PrintUtil.hex4(classDefsSize), classDefsSize));
        builder.append(String.format(form3, "classDefsOff", PrintUtil.hex4(classDefsOff), classDefsOff));
        builder.append(String.format(form3, "dataSize", PrintUtil.hex4(dataSize), dataSize));
        builder.append(String.format(form3, "dataOff", PrintUtil.hex4(dataOff), dataOff));

        return builder.toString();
    }
}
