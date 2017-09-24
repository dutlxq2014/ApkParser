package com.dex.data;

import com.common.PrintUtil;
import com.dex.stream.DexStreamer;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Created by xueqiulxq on 19/09/2017.
 */

public class ClassDataItem {

    public long staticFieldsSize;    // uleb128
    public long instanceFieldsSize;  // uleb128
    public long directMethodsSize;   // uleb128
    public long virtualMethodSize;   // uleb128

    public EncodedField[] staticFields;
    public EncodedField[] instanceFields;
    public EncodedMethod[] directMethods;
    public EncodedMethod[] virtualMethods;

    public static ClassDataItem parseFrom(RandomAccessFile racFile, DexStreamer s,
                                          StringPool stringPool, TypePool typePool, ProtoPool protoPool) throws IOException {
        long start = racFile.getFilePointer();

        // Read uleb128 based data
        long len = 0;
        long[] values = new long[4];
        for (int i=0; i<values.length; ++i) {
            byte[] uleb = s.readUleb128BytesFrom(racFile);
            len += uleb.length;
            values[i] = s.parseUleb4(uleb);
        }
        ClassDataItem item = new ClassDataItem();
        item.staticFieldsSize = values[0];
        item.instanceFieldsSize = values[1];
        item.directMethodsSize = values[2];
        item.virtualMethodSize = values[3];

        // Read field and methods
        EncodedField[] staticField = item.staticFields = new EncodedField[(int) item.staticFieldsSize];
        for (int i=0; i<item.staticFieldsSize; ++i) {
            staticField[i] = EncodedField.parseFrom(racFile, s, stringPool, typePool, protoPool);
        }
        EncodedField[] instanceFields = item.instanceFields = new EncodedField[(int) item.instanceFieldsSize];
        for (int i=0; i<item.instanceFieldsSize; ++i) {
            instanceFields[i] = EncodedField.parseFrom(racFile, s, stringPool, typePool, protoPool);
        }
        EncodedMethod[] directMethods = item.directMethods = new EncodedMethod[(int) item.directMethodsSize];
        for (int i=0; i<item.directMethodsSize; ++i) {
            directMethods[i] = EncodedMethod.parseFrom(racFile, s, stringPool, typePool, protoPool);
        }
        EncodedMethod[] virtualMethods = item.virtualMethods = new EncodedMethod[(int) item.virtualMethodSize];
        for (int i=0; i<item.virtualMethodSize; ++i) {
            virtualMethods[i] = EncodedMethod.parseFrom(racFile, s, stringPool, typePool, protoPool);
        }

        return item;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("-- ClassDataItem --\n");
        String form2 = "%-20s0x%s\n";
        builder.append(String.format(form2, "staticFieldsSize", PrintUtil.hex4(staticFieldsSize)));
        builder.append(String.format(form2, "instanceFieldsSize", PrintUtil.hex4(instanceFieldsSize)));
        builder.append(String.format(form2, "directMethodsSize", PrintUtil.hex4(directMethodsSize)));
        builder.append(String.format(form2, "virtualMethodSize", PrintUtil.hex4(virtualMethodSize)));

        String form = "// %s: size=%d\n";
        builder.append(String.format(form, "static fields", staticFieldsSize));
        for (int i=0; i<staticFields.length; ++i) {
            builder.append(staticFields[i]);
        }
        builder.append(String.format(form, "instance fields", instanceFieldsSize));
        for (int i=0; i<instanceFields.length; ++i) {
            builder.append(instanceFields[i]);
        }
        builder.append(String.format(form, "direct methods", directMethodsSize));
        for (int i=0; i<directMethods.length; ++i) {
            builder.append("DMethod #").append(i).append('\n');
            builder.append(directMethods[i]);
        }
        builder.append(String.format(form, "virtual methods", virtualMethodSize));
        for (int i=0; i<virtualMethods.length; ++i) {
            builder.append("VMethod #").append(i).append('\n');
            builder.append(virtualMethods[i]);
        }
        return builder.toString();
    }
}
