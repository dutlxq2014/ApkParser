package com.elfso.data;

import com.common.PrintUtil;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Created by xueqiulxq on 07/07/2017.
 */

public class Eident {

    public static final int EI_NIDENT = 16;
    public static final int EI_MAG0 = 0;
    public static final int EI_MAG1 = 1;
    public static final int EI_MAG2 = 2;
    public static final int EI_MAG3 = 3;
    public static final int EI_CLASS = 4;
    public static final int EI_DATA = 5;
    public static final int EI_VERSION = 6;
    public static final int EI_PAD = 7;

    public static final int ELFCLASSNONE = 0;
    public static final int ELFCLASS32 = 1;
    public static final int ELFCLASS64 = 2;
    public static final int ELFDATANONE = 0;
    public static final int ELFDATA2LSB = 1;
    public static final int ELFDATA2MSB = 2;
    public static final int EV_NONE = 0;
    public static final int EV_CURRENT = 1;

    public byte[] e_ident = new byte[EI_NIDENT];
    public byte ei_mag0;    // 0x7f
    public byte ei_mag1;    // 'E'
    public byte ei_mag2;    // 'L'
    public byte ei_mag3;    // 'F'
    public byte ei_class;
    public byte ei_data;
    public byte ei_version;
    public byte ei_pad;
    public byte[] ext;

    public Eident(byte[] eIdent) {
        e_ident = eIdent;

        ei_mag0 = eIdent[0];
        ei_mag1 = eIdent[1];
        ei_mag2 = eIdent[2];
        ei_mag3 = eIdent[3];
        ei_class = eIdent[4];
        ei_data = eIdent[5];
        ei_version = eIdent[6];
        ei_pad = eIdent[7];
        ext = new byte[eIdent.length - 8];
        System.arraycopy(eIdent, 8, ext, 0, ext.length);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(PrintUtil.hex1(ei_mag0))
                .append(PrintUtil.bChar(ei_mag1))
                .append(PrintUtil.bChar(ei_mag2))
                .append(PrintUtil.bChar(ei_mag3));
        builder.append('\t');

        if (ei_class == ELFCLASSNONE) {
            builder.append("ELFCLASSNONE");
        } else if (ei_class == ELFCLASS32) {
            builder.append("ELFCLASS32");
        } else if (ei_class == ELFCLASS64) {
            builder.append("ELFCLASS64");
        }
        builder.append('\t');

        if (ei_data == ELFDATANONE) {
            builder.append("ELFDATANONE");
        } else if (ei_data == ELFDATA2LSB) {
            builder.append("ELFDATA2LSB");
        } else if (ei_data == ELFDATA2MSB) {
            builder.append("ELFDATA2MSB");
        }
        builder.append('\t');

        if (ei_version == EV_NONE) {
            builder.append("EV_NONE");
        } else if (ei_version == EV_CURRENT){
            builder.append("EV_CURRENT");
        }
        builder.append('\t');

        builder.append(PrintUtil.hex1(ei_pad)).append('\t');
        for (int i=0; i<ext.length; ++i) {
            builder.append(PrintUtil.hex1(ext[i])).append('\t');
        }

        builder.append('\n');
        return builder.toString();
    }

    public static Eident parseFrom(byte[] ident) {
        return new Eident(ident);
    }
}
