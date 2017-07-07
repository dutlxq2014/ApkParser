package com.common;

/**
 *
 * Created by xueqiulxq on 07/07/2017.
 */

public class PrintUtil {

    public static char bChar(byte num) {
        return (char) num;
    }

    public static String hex1(int num) {
        return String.format("%02x", num);
    }

    public static String hex2(int num) {
        return String.format("%04x", num);
    }

    public static String hex4(int num) {
        return String.format("%04x", num);
    }

    public static String hex4(long num) {
        return String.format("%08x", num);
    }
}
