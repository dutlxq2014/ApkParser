package com.manifest.data;

import com.common.PrintUtil;
import com.manifest.stream.MfStreamer;


/**
 *
 * Created by xueqiulxq on 16/07/2017.
 */

public class MfHeader {

    public static final int LENGTH = 8;
    public long magicNumber;
    public long fileLength;

    public static MfHeader parseFrom(MfStreamer s) {
        MfHeader header = new MfHeader();
        header.magicNumber = s.readUInt();
        header.fileLength = s.readUInt();
        return header;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Magic Number: ").append(PrintUtil.hex4(magicNumber)).append('\n');
        builder.append("File Length: ").append(PrintUtil.hex4(fileLength)).append('\n');
        return builder.toString();
    }
}
