package com.dex.data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * Created by xueqiulxq on 23/09/2017.
 */

public class AccessFlags {

    public static final int
            ACC_PUBLIC = 0x00000001,        // class, field, method, ic
            ACC_PRIVATE = 0x00000002,       // field, method, ic
            ACC_PROTECTED = 0x00000004,     // field, method, ic
            ACC_STATIC = 0x00000008,        // field, method, ic
            ACC_FINAL = 0x00000010,         // class, field, method, ic
            ACC_SYNCHRONIZED = 0x00000020,  // method (only allowed on natives)
            ACC_SUPER = 0x00000020,         // class (not used in Dalvik)
            ACC_VOLATILE = 0x00000040,      // field
            ACC_BRIDGE = 0x00000040,        // method (1.5)
            ACC_TRANSIENT = 0x00000080,     // field
            ACC_VARARGS = 0x00000080,       // method (1.5)
            ACC_NATIVE = 0x00000100,        // method
            ACC_INTERFACE = 0x00000200,     // class, ic
            ACC_ABSTRACT = 0x00000400,      // class, method, ic
            ACC_STRICT = 0x00000800,        // method
            ACC_SYNTHETIC = 0x00001000,     // field, method, ic
            ACC_ANNOTATION = 0x00002000,    // class, ic (1.5)
            ACC_ENUM = 0x00004000,          // class, field, ic (1.5)
            ACC_CONSTRUCTOR = 0x00010000,   // method (Dalvik only)
            ACC_DECLARED_SYNCHRONIZED = 0x00020000  // method (Dalvik only)
            ;
    public static final Map<Integer, String> classStrs = new LinkedHashMap<Integer, String>() {
        {
            put(ACC_PUBLIC, "ACC_PUBLIC");
            put(ACC_FINAL, "ACC_FINAL");
            put(ACC_INTERFACE, "ACC_INTERFACE");
            put(ACC_ABSTRACT, "ACC_ABSTRACT");
            put(ACC_SYNTHETIC, "ACC_SYNTHETIC");
            put(ACC_ANNOTATION, "ACC_ANNOTATION");
            put(ACC_ENUM, "ACC_ENUM");
            // Inner class
            put(ACC_PRIVATE, "ACC_PRIVATE");
            put(ACC_PROTECTED, "ACC_PROTECTED");
            put(ACC_STATIC, "ACC_STATIC");
        }
    };
    public static final Map<Integer, String> fieldStrs = new LinkedHashMap<Integer, String>() {
        {
            put(ACC_PUBLIC, "ACC_PUBLIC");
            put(ACC_PRIVATE, "ACC_PRIVATE");
            put(ACC_PROTECTED, "ACC_PROTECTED");
            put(ACC_STATIC, "ACC_STATIC");
            put(ACC_FINAL, "ACC_FINAL");
            put(ACC_VOLATILE, "ACC_VOLATILE");
            put(ACC_TRANSIENT, "ACC_TRANSIENT");
            put(ACC_SYNTHETIC, "ACC_SYNTHETIC");
            put(ACC_ENUM, "ACC_ENUM");
        }
    };
    public static final Map<Integer, String> methodStrs = new LinkedHashMap<Integer, String>() {
        {
            put(ACC_PUBLIC, "ACC_PUBLIC");
            put(ACC_PRIVATE, "ACC_PRIVATE");
            put(ACC_PROTECTED, "ACC_PROTECTED");
            put(ACC_STATIC, "ACC_STATIC");
            put(ACC_FINAL, "ACC_FINAL");
            put(ACC_SYNCHRONIZED, "ACC_SYNCHRONIZED");
            put(ACC_BRIDGE, "ACC_BRIDGE");
            put(ACC_VARARGS, "ACC_VARARGS");
            put(ACC_NATIVE, "ACC_NATIVE");
            put(ACC_ABSTRACT, "ACC_ABSTRACT");
            put(ACC_STRICT, "ACC_STRICT");
            put(ACC_SYNTHETIC, "ACC_SYNTHETIC");
            put(ACC_CONSTRUCTOR, "ACC_CONSTRUCTOR");
            put(ACC_DECLARED_SYNCHRONIZED, "ACC_DECLARED_SYNCHRONIZED");
        }
    };

    public static final int
            ACC_CLASS_MASK = ACC_PUBLIC | ACC_FINAL | ACC_INTERFACE | ACC_ABSTRACT
                            | ACC_SYNTHETIC | ACC_ANNOTATION | ACC_ENUM,

            ACC_INNER_CLASS_MASK = ACC_CLASS_MASK | ACC_PRIVATE | ACC_PROTECTED | ACC_STATIC,

            ACC_FIELD_MASK = ACC_PUBLIC | ACC_PRIVATE | ACC_PROTECTED | ACC_STATIC | ACC_FINAL
                            | ACC_VOLATILE | ACC_TRANSIENT | ACC_SYNTHETIC | ACC_ENUM,

            ACC_METHOD_MASK = ACC_PUBLIC | ACC_PRIVATE | ACC_PROTECTED | ACC_STATIC | ACC_FINAL
                            | ACC_SYNCHRONIZED | ACC_BRIDGE | ACC_VARARGS | ACC_NATIVE
                            | ACC_ABSTRACT | ACC_STRICT | ACC_SYNTHETIC | ACC_CONSTRUCTOR
                            | ACC_DECLARED_SYNCHRONIZED;

    private static String accStr(Map<Integer, String> map, long flags) {
        StringBuilder builder = new StringBuilder();
        for (Integer flag : map.keySet()) {
            if ((flag & flags) != 0) {
                builder.append(map.get(flag)).append(" | ");
            }
        }
        if (builder.length() > 0) {
            builder.setLength(builder.length() - 3);
        }
        return builder.toString();
    }

    public static String accClassStr(long flags) {
        return accStr(classStrs, flags);
    }

    public static String accFieldStr(long flags) {
        return accStr(fieldStrs, flags);
    }

    public static String accMethodStr(long flags) {
        return accStr(methodStrs, flags);
    }

}
