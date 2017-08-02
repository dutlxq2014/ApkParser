package com.common;

/**
 *
 * Created by xueqiulxq on 07/07/2017.
 */

public class LogUtil {


    public static void e(String tag, String... detail) {
        System.err.println(tag + ": " + format(detail));
    }

    public static void i(String tag, String... detail) {
        System.out.println(tag + ": " + format(detail));
    }

    private static String format(String... detail) {
        if (detail == null || detail.length == 0) {
            return "";
        } else if (detail.length == 1) {
            return detail[0];
        }
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<detail.length; ++i) {
            builder.append(detail[i]);
            if (i < detail.length - 1) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }
}
