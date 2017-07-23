package com.manifest.data;


/**
 *
 * Created by xueqiulxq on 20/07/2017.
 */

public class AttributeType {

    // Attribute Types: framework/base/include/androidfw/ResourceTypes.h
    public static final int TYPE_NULL = 0x00;
    public static final int TYPE_REFERENCE = 0x01;
    public static final int TYPE_ATTRIBUTE = 0x02;
    public static final int TYPE_STRING = 0x03;
    public static final int TYPE_FLOAT = 0x04;
    public static final int TYPE_DIMENSION = 0x05;
    public static final int TYPE_FRACTION = 0x06;
    public static final int TYPE_DYNAMIC_REFERENCE = 0x07;

    public static final int TYPE_FIRSTINT = 0x10;          // Beginning of integer flavors...

    public static final int TYPE_INT_DEC = 0x10;           // n..n.
    public static final int TYPE_INT_HEX = 0x11;           // 0xn..n.
    public static final int TYPE_INT_BOOLEAN = 0x12;       // 0 or 1, "false" or "true"

    public static final int TYPE_FIRST_COLOR_INT = 0x1c;   // Beginning of color integer flavors...
    public static final int TYPE_INT_COLOR_ARGB8 = 0x1c;   // #aarrggbb.
    public static final int TYPE_INT_COLOR_RGB8 = 0x1d;    // #rrggbb.
    public static final int TYPE_INT_COLOR_ARGB4 = 0x1e;   // #argb.
    public static final int TYPE_INT_COLOR_RGB4 = 0x1f;    // ##rgb.
    public static final int TYPE_LAST_COLOR_INT = 0x1f;    // ..end of integer flavors.

    public static final int TYPE_LAST_INT = 0x1f;          // ...end of integer flavors.
    public static final int TYPE = 12;

    public static String getAttributeType(AttributeEntry entry) {
        String typeStr;
        if (entry.type == TYPE_REFERENCE) {
            typeStr = "TYPE_REFERENCE";
        } else if (entry.type == TYPE_ATTRIBUTE) {
            typeStr = "TYPE_ATTRIBUTE";
        } else if (entry.type == TYPE_STRING) {
            typeStr = "TYPE_STRING";
        } else if (entry.type == TYPE_FLOAT) {
            typeStr = "TYPE_FLOAT";
        } else if (entry.type == TYPE_DIMENSION) {
            typeStr = "TYPE_DIMENSION";
        } else if (entry.type == TYPE_FRACTION) {
            typeStr = "TYPE_FRACTION";
        } else if (entry.type == TYPE_DYNAMIC_REFERENCE) {
            typeStr = "TYPE_DYNAMIC_REFERENCE";
        } else if (entry.type == TYPE_INT_DEC) {
            typeStr = "TYPE_INT_DEC";
        } else if (entry.type == TYPE_INT_HEX) {
            typeStr = "TYPE_INT_HEX";
        } else if (entry.type == TYPE_INT_BOOLEAN) {
            typeStr = "TYPE_INT_BOOLEAN";
        } else if (entry.type == TYPE_INT_COLOR_ARGB8) {
            typeStr = "TYPE_INT_COLOR_ARGB8";
        } else if (entry.type == TYPE_INT_COLOR_RGB8) {
            typeStr = "TYPE_INT_COLOR_RGB8";
        } else if (entry.type == TYPE_INT_COLOR_ARGB4) {
            typeStr = "TYPE_INT_COLOR_ARGB4";
        } else if (entry.type == TYPE_INT_COLOR_RGB4) {
            typeStr = "TYPE_INT_COLOR_RGB4";
        } else {
            typeStr = "TYPE_UNKNOWN";
        }
        return typeStr;
    }


    public static String getAttributeData(AttributeEntry entry, StringChunk stringChunk) {
        String attrData;
        if (entry.type == TYPE_REFERENCE) {
            attrData = String.format("@%s%08x", getPackage(entry.data), entry.data);
        } else if (entry.type == TYPE_ATTRIBUTE) {
            attrData = String.format("?%s%08x", getPackage(entry.data), entry.data);
        } else if (entry.type == TYPE_STRING) {
            attrData = stringChunk.getString(entry.data);
        } else if (entry.type == TYPE_FLOAT) {
            attrData = String.valueOf(Float.intBitsToFloat((int)entry.data));
        } else if (entry.type == TYPE_DIMENSION) {
            attrData = Float.toString(Float.intBitsToFloat((int) entry.data)) + getDimenUnit(entry.data);
        } else if (entry.type == TYPE_FRACTION) {
            attrData = Float.toString(Float.intBitsToFloat((int) entry.data)) + getFractionUnit(entry.data);
        } else if (entry.type == TYPE_DYNAMIC_REFERENCE) {
            attrData = "TYPE_DYNAMIC_REFERENCE";
        } else if (entry.type == TYPE_INT_DEC) {
            attrData = String.format("%d", entry.data);
        } else if (entry.type == TYPE_INT_HEX) {
            attrData = String.format("0x%08x", entry.data);
        } else if (entry.type == TYPE_INT_BOOLEAN) {
            attrData = entry.data == 0 ? "false" : "true";
        } else if (entry.type == TYPE_INT_COLOR_ARGB8) {
            attrData = String.format("#%08x", entry.data);
        } else if (entry.type == TYPE_INT_COLOR_RGB8) {
            attrData = String.format("#ff%06x", 0xffffff & entry.data);
        } else if (entry.type == TYPE_INT_COLOR_ARGB4) {
            attrData = String.format("#%04x", 0xffff & entry.data);
        } else if (entry.type == TYPE_INT_COLOR_RGB4) {
            attrData = String.format("#f%03x", 0x0fff & entry.data);
        } else {
            attrData = String.format("<0x%08x, type 0x%08x>", entry.data, entry.type);
        }

        return attrData;
    }

    public static final int COMPLEX_UNIT_SHIFT = 0;
    public static final int COMPLEX_UNIT_MASK = 0xf;
    public static final int COMPLEX_UNIT_PX = 0;
    public static final int COMPLEX_UNIT_DIP = 1;
    public static final int COMPLEX_UNIT_SP = 2;
    public static final int COMPLEX_UNIT_PT = 3;
    public static final int COMPLEX_UNIT_IN = 4;
    public static final int COMPLEX_UNIT_MM = 5;
    public static final int COMPLEX_UNIT_FRACTION = 6;
    public static final int COMPLEX_UNIT_FRACTION_PARENT = 7;

    private static String getDimenUnit(long data) {
        //noinspection PointlessBitwiseExpression
        switch ((int) (data >> COMPLEX_UNIT_SHIFT & COMPLEX_UNIT_MASK)) {
            case COMPLEX_UNIT_PX: return "px";
            case COMPLEX_UNIT_DIP: return "dp";
            case COMPLEX_UNIT_SP: return "sp";
            case COMPLEX_UNIT_PT: return "pt";
            case COMPLEX_UNIT_IN: return "in";
            case COMPLEX_UNIT_MM: return "mm";
            default: return " (unknown unit)";
        }
    }

    private static String getFractionUnit(long data) {
        //noinspection PointlessBitwiseExpression
        switch ((int) (data >> COMPLEX_UNIT_SHIFT & COMPLEX_UNIT_MASK)) {
            case COMPLEX_UNIT_FRACTION: return "%%";
            case COMPLEX_UNIT_FRACTION_PARENT: return "%%p";
            default: return " (unknown unit)";
        }
    }

    private static String getPackage(long data) {
        return "package";
    }
}
