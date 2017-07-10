package com.elfso.data;

import com.elfso.stream.SectionStreamer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by xueqiulxq on 07/07/2017.
 */

public class StringTable {

    public String[] strs;
    public byte[] strBytes;

    public static StringTable parseFrom(SectionStreamer s) {
        int len = s.length();
        StringTable strTab = new StringTable();
        strTab.strBytes = new byte[len];
        List<String> tmp = new ArrayList<String>();
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<len; ++i) {
            byte[] b = s.read(1);
            strTab.strBytes[i] = b[0];
            if (b[0] == 0) {
                tmp.add(builder.toString());
                builder.setLength(0);
            } else {
                builder.append((char) b[0]);
            }
        }
        strTab.strs = new String[tmp.size()];
        tmp.toArray(strTab.strs);
        return strTab;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<strs.length; ++i) {
            builder.append(i).append(":").append(strs[i]).append("  ");
        }
        return builder.toString();
    }

    public String get(int idx) {
        StringBuilder builder = new StringBuilder();
        if (strBytes != null && idx < strBytes.length) {
            for (int i=idx; i<strBytes.length; ++i) {
                if (strBytes[i] != 0) {
                    builder.append((char)strBytes[i]);
                } else {
                    break;
                }
            }
        }
        return builder.toString();
    }
}
