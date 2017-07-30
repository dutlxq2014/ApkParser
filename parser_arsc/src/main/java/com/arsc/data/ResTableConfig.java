package com.arsc.data;

import com.arsc.stream.ArscStreamer;

/**
 *
 * Created by xueqiulxq on 29/07/2017.
 */

public class ResTableConfig {

    public long size;    // 4

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

        return config;
    }
}
