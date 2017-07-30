package com.common;

/**
 *
 * Created by xueqiulxq on 07/07/2017.
 */

public class LogUtil {


    public static void e(String... detail) {
        System.err.println("E: " + format(detail));
    }


    public static void i(String... detail) {
        System.out.println("I: " + format(detail));
    }

    private static String format(String... detail) {
        if (detail == null) {
            return "";
        }
        String ret = "";
        for (int i=0; i<detail.length; ++i) {
            ret += detail[i];
            if (i < detail.length - 1) {
                ret += " ; ";
            }
        }
        return ret;
    }
}
