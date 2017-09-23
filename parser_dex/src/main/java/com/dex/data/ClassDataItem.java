package com.dex.data;

/**
 *
 * Created by xueqiulxq on 19/09/2017.
 */

public class ClassDataItem {

    public int staticFieldsSize;    // uleb128
    public int instanceFieldsSize;  // uleb128
    public int directMethodsSize;   // uleb128
    public int virtualMethodSize;   // uleb128

    public EncodedField[] staticFields;
    public EncodedField[] instanceFields;
    public EncodedMethod[] directMethods;
    public EncodedMethod[] virtualMethods;

    public static ClassDataItem parseFrom() {
        ClassDataItem item = new ClassDataItem();

        return item;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }
}
