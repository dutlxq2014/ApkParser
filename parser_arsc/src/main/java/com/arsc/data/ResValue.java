package com.arsc.data;

import com.arsc.stream.ArscStreamer;
import com.common.PrintUtil;

/**
 *
 * Created by xueqiulxq on 29/07/2017.
 */

public class ResValue {

    public final static int TYPE_NULL = 0x00;
    public final static int TYPE_REFERENCE = 0x01;
    public final static int TYPE_ATTRIBUTE = 0x02;
    public final static int TYPE_STRING = 0x03;
    public final static int TYPE_FLOAT = 0x04;
    public final static int TYPE_DIMENSION = 0x05;
    public final static int TYPE_FRACTION = 0x06;
    public final static int TYPE_FIRST_INT = 0x10;
    public final static int TYPE_INT_DEC = 0x10;
    public final static int TYPE_INT_HEX = 0x11;
    public final static int TYPE_INT_BOOLEAN = 0x12;
    public final static int TYPE_FIRST_COLOR_INT = 0x1c;
    public final static int TYPE_INT_COLOR_ARGB8 = 0x1c;
    public final static int TYPE_INT_COLOR_RGB8 = 0x1d;
    public final static int TYPE_INT_COLOR_ARGB4 = 0x1e;
    public final static int TYPE_INT_COLOR_RGB4 = 0x1f;
    public final static int TYPE_LAST_COLOR_INT = 0x1f;
    public final static int TYPE_LAST_INT = 0x1f;

    public static final int COMPLEX_UNIT_PX			=0;
    public static final int COMPLEX_UNIT_DIP		=1;
    public static final int COMPLEX_UNIT_SP			=2;
    public static final int COMPLEX_UNIT_PT			=3;
    public static final int COMPLEX_UNIT_IN			=4;
    public static final int COMPLEX_UNIT_MM			=5;
    public static final int COMPLEX_UNIT_SHIFT		=0;
    public static final int COMPLEX_UNIT_MASK		=15;
    public static final int COMPLEX_UNIT_FRACTION	=0;
    public static final int COMPLEX_UNIT_FRACTION_PARENT=1;
    public static final int COMPLEX_RADIX_23p0		=0;
    public static final int COMPLEX_RADIX_16p7		=1;
    public static final int COMPLEX_RADIX_8p15		=2;
    public static final int COMPLEX_RADIX_0p23		=3;
    public static final int COMPLEX_RADIX_SHIFT		=4;
    public static final int COMPLEX_RADIX_MASK		=3;
    public static final int COMPLEX_MANTISSA_SHIFT	=8;
    public static final int COMPLEX_MANTISSA_MASK	=0xFFFFFF;

    public int size;        // short
    public int res0;        // byte
    public int dataType;    // byte;
    public long data;       // int index to data

    public static ResValue parseFrom(ArscStreamer s) {
        ResValue value = new ResValue();
        value.size = s.readUShort();
        value.res0 = s.readUInt8();
        value.dataType = s.readUInt8();
        value.data = s.readUInt();
        return value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String form = "%-10s %s\n";
        builder.append("<ResValue>\n");
        builder.append(String.format(form, "size", PrintUtil.hex2(size)));
        builder.append(String.format(form, "res0", PrintUtil.hex1(res0)));
        builder.append(String.format(form, "dataType", PrintUtil.hex1(dataType)));
        builder.append(String.format(form, "data", PrintUtil.hex4(data)));
        builder.append("</ResValue>\n");
        return builder.toString();
    }

    public String getTypeStr(){
        switch(dataType){
            case TYPE_NULL:
                return "TYPE_NULL";
            case TYPE_REFERENCE:
                return "TYPE_REFERENCE";
            case TYPE_ATTRIBUTE:
                return "TYPE_ATTRIBUTE";
            case TYPE_STRING:
                return "TYPE_STRING";
            case TYPE_FLOAT:
                return "TYPE_FLOAT";
            case TYPE_DIMENSION:
                return "TYPE_DIMENSION";
            case TYPE_FRACTION:
                return "TYPE_FRACTION";
            case TYPE_FIRST_INT:
                return "TYPE_FIRST_INT";
            case TYPE_INT_HEX:
                return "TYPE_INT_HEX";
            case TYPE_INT_BOOLEAN:
                return "TYPE_INT_BOOLEAN";
            case TYPE_FIRST_COLOR_INT:
                return "TYPE_FIRST_COLOR_INT";
            case TYPE_INT_COLOR_RGB8:
                return "TYPE_INT_COLOR_RGB8";
            case TYPE_INT_COLOR_ARGB4:
                return "TYPE_INT_COLOR_ARGB4";
            case TYPE_INT_COLOR_RGB4:
                return "TYPE_INT_COLOR_RGB4";
        }
        return "Unknown";
    }

    public String getDataStr() {
        return "Unknown";
    }

    private static String getPackage(int id) {
        if (id >>> 24 == 1) {
            return "android:";
        }
        return "";
    }

    public static float complexToFloat(int complex) {
        return (float) (complex & 0xFFFFFF00) * RADIX_MULTS[(complex>>4) & 3];
    }

    private static final float RADIX_MULTS[]={
            0.00390625F,3.051758E-005F,1.192093E-007F,4.656613E-010F
    };

    private static final String DIMENSION_UNITS[]={
            "px","dip","sp","pt","in","mm","",""
    };

    private static final String FRACTION_UNITS[]={
            "%","%p","","","","","",""
    };


}
