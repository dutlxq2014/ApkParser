package com.dex.data;

/**
 *
 * Created by xueqiulxq on 19/09/2017.
 */

public class EncodedField {

    public byte[] fieldIdxDiff;     // uleb128: index into field_ids for ID of this filed
    public byte[] accessFlags;      // uleb128: access flags like public, static etc.



    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }
}
