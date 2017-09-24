package com.dex.data;

import com.common.PrintUtil;
import com.dex.stream.DexStreamer;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Created by xueqiulxq on 18/09/2017.
 */

public class ClassDefItem {

    public static final int LENGTH = 4 * 8;

    public long classIdx;
    public long accessFlags;
    public long superClassIdx;
    public long interfacesOff;      // -> type_list
    public long sourceFileIdx;
    public long annotationsOff;     // -> annotations_directory_item
    public long classDataOff;       // -> class_data_item  {@link ClassDataItem}
    public long staticValueOff;     // -> encoded_array_item
    // Assistant
    public String classStr;
    public String superClassStr;
    public String sourceFileStr;

    public ClassInterfaceItem interfaceItem;
    public ClassDataItem dataItem;

    public static ClassDefItem parseFrom(RandomAccessFile racFile, DexStreamer s,
                                         StringPool stringPool, TypePool typePool, ProtoPool protoPool) throws IOException {
        ClassDefItem item = new ClassDefItem();

        item.classIdx = s.readU4();
        item.accessFlags = s.readU4();
        item.superClassIdx = s.readU4();
        item.interfacesOff = s.readU4();
        item.sourceFileIdx = s.readU4();
        item.annotationsOff = s.readU4();
        item.classDataOff = s.readU4();
        item.staticValueOff = s.readU4();

        item.classStr = typePool.getType(item.classIdx);
        item.superClassStr = typePool.getType(item.superClassIdx);
        item.sourceFileStr = stringPool.getString(item.sourceFileIdx);

        racFile.seek(item.classDataOff);
        item.dataItem = ClassDataItem.parseFrom(racFile, s, stringPool, typePool, protoPool);

        if (item.interfacesOff != 0) {
            racFile.seek(item.interfacesOff);
            item.interfaceItem = ClassInterfaceItem.parseFrom(racFile, s, stringPool, typePool, protoPool);
        } else {
            item.interfaceItem = new ClassInterfaceItem();
        }

        return item;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        String form3 = "%-16s%-16s%s\n";
        builder.append(String.format(form3, "classIdx", PrintUtil.hex4(classIdx), classStr));
        builder.append(String.format(form3, "accessFlags", PrintUtil.hex4(accessFlags), AccessFlags.accClassStr(accessFlags)));
        builder.append(String.format(form3, "superClassIdx", PrintUtil.hex4(superClassIdx), superClassStr));
        builder.append(String.format(form3, "interfacesOff", PrintUtil.hex4(interfacesOff), "-> ClassInterfaceItem"));
        builder.append(String.format(form3, "sourceFileIdx", PrintUtil.hex4(sourceFileIdx), sourceFileStr));
        builder.append(String.format(form3, "annotationsOff", PrintUtil.hex4(annotationsOff), "->"));
        builder.append(String.format(form3, "classDataOff", PrintUtil.hex4(classDataOff), "-> ClassDataItem"));
        builder.append(String.format(form3, "staticValueOff", PrintUtil.hex4(staticValueOff), "->"));

        String itfStr = interfaceItem.toString();
        builder.append(PrintUtil.indent(itfStr));
        String dataItemStr = dataItem.toString();
        builder.append(PrintUtil.indent(dataItemStr));

        return builder.toString();
    }
}
