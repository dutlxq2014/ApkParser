package com.dex.data;

import com.common.PrintUtil;
import com.dex.stream.DexStreamer;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Created by xueqiulxq on 19/09/2017.
 */

public class EncodedMethod {

    public byte[] methodIdxDiff;    // uleb128
    public byte[] accessFlags;      // uleb128
    public byte[] codeOff;          // uleb128 -> code_item {@link CodeItem}

    public CodeItem codeItem;
    // Assistant
    public long methodIdxDiffInt;
    public long accessFlagsInt;
    public long codeOffInt;

    public static EncodedMethod parseFrom(RandomAccessFile racFile, DexStreamer s,
                                          StringPool stringPool, TypePool typePool, ProtoPool protoPool) throws IOException {
        EncodedMethod method = new EncodedMethod();
        method.methodIdxDiff = s.readUleb128BytesFrom(racFile);
        method.accessFlags = s.readUleb128BytesFrom(racFile);
        method.codeOff = s.readUleb128BytesFrom(racFile);

        method.methodIdxDiffInt = s.parseUleb4(method.methodIdxDiff);
        method.accessFlagsInt = s.parseUleb4(method.accessFlags);
        method.codeOffInt = s.parseUleb4(method.codeOff);

        if (method.codeOffInt != 0) {
            long cursor = racFile.getFilePointer();
            racFile.seek(method.codeOffInt);
            method.codeItem = CodeItem.parseFrom(racFile, s, stringPool, typePool, protoPool);
            racFile.seek(cursor);
        }
        return method;
    }

    public int getLength() {
        return methodIdxDiff.length + accessFlags.length + codeOff.length;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String form2 = "%-20s0x%s\n";
        String form3 = "%-20s0x%s \t%s\n";
        builder.append(String.format(form2, "methodIdxDiff", PrintUtil.hex(methodIdxDiff)));
        builder.append(String.format(form3, "accessFlags", PrintUtil.hex(accessFlags), AccessFlags.accMethodStr(accessFlagsInt)));
        builder.append(String.format(form2, "codeOff", PrintUtil.hex(codeOff)));
        if (codeItem != null) {
            String codeItemStr = codeItem.toString();
            builder.append("\t").append(codeItemStr.trim().replace("\n", "\n\t")).append('\n');
        } else {
            builder.append("\tCodeItem: ").append("Not implemented.").append('\n');
        }
        return builder.toString();
    }
}
