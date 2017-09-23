package com.dex.data;

import com.common.PrintUtil;
import com.dex.stream.DexStreamer;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Created by xueqiulxq on 19/09/2017.
 */

public class EncodedField {

    public byte[] fieldIdxDiff;     // uleb128: index into field_ids for ID of this filed
    public byte[] accessFlags;      // uleb128: access flags like public, static etc.
    // Assistant
    public long fieldIdxDiffInt;
    public long accessFlagsInt;

    public static EncodedField parseFrom(RandomAccessFile racFile, DexStreamer s,
                                         StringPool stringPool, TypePool typePool, ProtoPool protoPool) throws IOException {
        EncodedField field = new EncodedField();
        field.fieldIdxDiff = s.readUleb128BytesFrom(racFile);
        field.accessFlags = s.readUleb128BytesFrom(racFile);

        field.fieldIdxDiffInt = s.parseUleb4(field.fieldIdxDiff);
        field.accessFlagsInt = s.parseUleb4(field.accessFlags);
        return field;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String form3 = "%-20s0x%s \t%s\n";
        builder.append(String.format(form3, "fieldIdxDiff", PrintUtil.hex(fieldIdxDiff), ""));
        builder.append(String.format(form3, "accessFlags", PrintUtil.hex(accessFlags), AccessFlags.accFieldStr(accessFlagsInt)));
        return builder.toString();
    }
}
