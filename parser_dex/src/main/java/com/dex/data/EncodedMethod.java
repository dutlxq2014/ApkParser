package com.dex.data;

/**
 *
 * Created by xueqiulxq on 19/09/2017.
 */

public class EncodedMethod {

    public byte[] methodIdxDiff;    // uleb128
    public byte[] accessFlags;      // uleb128
    public byte[] codeOff;          // uleb128 -> code_item {@link CodeItem}

    public CodeItem codeItem;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }
}
