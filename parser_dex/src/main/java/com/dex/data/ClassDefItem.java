package com.dex.data;

import com.common.PrintUtil;
import com.dex.stream.DexStreamer;

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

    public ClassDataItem dataItem;

    public static ClassDefItem parseFrom(DexStreamer s, StringPool stringPool, TypePool typePool) {
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



        return item;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        String form2 = "%-16s%s\n";
        String form3 = "%-16s%-16s%s\n";
        builder.append(String.format(form3, PrintUtil.hex4(classIdx), "classIdx", classStr));
        builder.append(String.format(form3, PrintUtil.hex4(accessFlags), "accessFlags", AccessFlags.accClassStr(accessFlags)));
        builder.append(String.format(form3, PrintUtil.hex4(superClassIdx), "superClassIdx", superClassStr));
        builder.append(String.format(form2, PrintUtil.hex4(interfacesOff), "interfacesOff"));
        builder.append(String.format(form3, PrintUtil.hex4(sourceFileIdx), "sourceFileIdx", sourceFileStr));
        builder.append(String.format(form2, PrintUtil.hex4(annotationsOff), "annotationsOff"));
        builder.append(String.format(form2, PrintUtil.hex4(classDataOff), "classDataOff"));
        builder.append(String.format(form2, PrintUtil.hex4(staticValueOff), "staticValueOff"));

        return builder.toString();
    }
}
