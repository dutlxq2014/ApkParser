package com.arsc.data;

import com.arsc.stream.ArscStreamer;
import com.common.PrintUtil;

/**
 *
 * Created by xueqiulxq on 29/07/2017.
 */

public class ResTableConfig {

    public long size;    // 4    size of config object

    // union 4bytes
    public int mcc, mnc; // 2 + 2
    public long imsi;    // 4

    // union 4bytes
    public int language, country;   // 2 + 2
    public long locale;             // 4

    // union 4bytes
    public int orientation, touchScreen, density;   // 1 + 1 + 2
    public long screenType;                         // 4

    // union 4bytes
    public int keyboard, navigation, inputFlags, inputPad0; // 1 + 1 + 1 + 1
    public long input;                                      // 4

    // union 4bytes
    public int screenWidth, screenHeight;   // 2 + 2
    public long screenSize;                 // 4

    // union 4bytes
    public int sdkVersion, minorVersion;    // 2 + 2
    public long version;                    // 4

    // union 4bytes
    public int screenLayout, uiModeByte, smallestScreenWidthDp; // 1 + 1 + 2
    public long screenConfig;                                   // 4

    // union 4bytes
    public int screenWidthDp, screenHeightDp;   // 2 + 2
    public long screenSizeDp;                   // 4

    public byte[] localeScript;   // 4
    public byte[] localeVariant;  // 8

    public static ResTableConfig parseFrom(ArscStreamer s) {
        ResTableConfig config = new ResTableConfig();
        int cursor = s.getCursor();
        int start = cursor;

        config.size = s.readUInt();
        cursor += 4;

        config.mcc = s.readUShort();
        config.mnc = s.readUShort();
        s.seek(cursor);     // Reset cursor to get union value.
        config.imsi = s.readUInt();
        cursor += 4;

        config.language = s.readUShort();
        config.country = s.readUShort();
        s.seek(cursor);
        config.locale = s.readUInt();
        cursor += 4;

        config.orientation = s.readUInt8();
        config.touchScreen = s.readUInt8();
        config.density = s.readUShort();
        s.seek(cursor);
        config.screenType = s.readUInt();
        cursor += 4;

        config.keyboard = s.readUInt8();
        config.navigation = s.readUInt8();
        config.inputFlags = s.readUInt8();
        config.inputPad0 = s.readUInt8();
        s.seek(cursor);
        config.input = s.readUInt();
        cursor += 4;

        config.screenWidth = s.readUShort();
        config.screenHeight = s.readUShort();
        s.seek(cursor);
        config.screenSize = s.readUInt();
        cursor += 4;

        config.sdkVersion = s.readUShort();
        config.minorVersion = s.readUShort();
        s.seek(cursor);
        config.version = s.readUInt();
        cursor += 4;

        config.screenLayout = s.readUInt8();
        config.uiModeByte = s.readUInt8();
        config.smallestScreenWidthDp = s.readUShort();
        s.seek(cursor);
        config.screenConfig = s.readUInt();
        cursor += 4;

        config.screenWidthDp = s.readUShort();
        config.screenHeightDp = s.readUShort();
        s.seek(cursor);
        config.screenSizeDp = s.readUInt();
        cursor += 4;

        config.localeScript = s.read(4);
        config.localeVariant = s.read(8);

        s.seek(start + config.size);

        return config;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String form2 = "%s:%s\n";
        String union2 = "{%s:%s, %s:%s | %s:%s}\n";
        String union3 = "{%s:%s, %s:%s, %s:%s | %s:%s}\n";
        String union4 = "{%s:%s, %s:%s, %s:%s, %s:%s | %s:%s}\n";
        builder.append("<ResTableConfig>\n");
        builder.append(String.format(form2, "size", size));
        builder.append(String.format(union2, "mcc", PrintUtil.hex2(mcc), "mnc", PrintUtil.hex2(mnc), "imsi", PrintUtil.hex4(imsi)));
        builder.append(String.format(union2, "language", PrintUtil.hex2(language), "country", PrintUtil.hex2(country), "locale", PrintUtil.hex4(locale)));
        builder.append(String.format(union3, "orientation", PrintUtil.hex1(orientation), "touchScreen", PrintUtil.hex1(touchScreen), "density", PrintUtil.hex2(density), "screenType", PrintUtil.hex4(screenType)));
        builder.append(String.format(union4, "keyboard", PrintUtil.hex1(keyboard), "navigation", PrintUtil.hex1(navigation), "inputFlags", PrintUtil.hex2(inputFlags),  "inputPad0", PrintUtil.hex2(inputPad0), "input", PrintUtil.hex4(input)));
        builder.append(String.format(union2, "screenWidth", PrintUtil.hex2(screenWidth), "screenHeight", PrintUtil.hex2(screenHeight), "screenSize", PrintUtil.hex4(screenSize)));
        builder.append(String.format(union2, "sdkVersion", PrintUtil.hex2(sdkVersion), "minorVersion", PrintUtil.hex2(minorVersion), "version", PrintUtil.hex4(version)));
        builder.append(String.format(union3, "screenLayout", PrintUtil.hex1(screenLayout), "uiModeByte", PrintUtil.hex1(uiModeByte), "smallestScreenWidthDp", PrintUtil.hex2(smallestScreenWidthDp), "screenConfig", PrintUtil.hex4(screenConfig)));
        builder.append(String.format(union2, "screenWidthDp", PrintUtil.hex2(screenWidthDp), "screenHeightDp", PrintUtil.hex2(screenHeightDp), "screenSizeDp", PrintUtil.hex4(screenSizeDp)));
        builder.append("localeScript: ");
        for (int i=0; i<localeScript.length; ++i) {
            builder.append(PrintUtil.hex1(localeScript[i] & 0xff)).append(' ');
        }
        builder.append('\n');
        builder.append("localeVariant: ");
        for (int i=0; i<localeVariant.length; ++i) {
            builder.append(PrintUtil.hex1(localeVariant[i] & 0xff)).append(' ');
        }
        builder.append('\n');
        builder.append("</ResTableConfig>\n");
        return builder.toString();
    }
}
