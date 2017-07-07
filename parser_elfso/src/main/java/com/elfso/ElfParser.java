package com.elfso;

import com.common.FileUtil;
import com.elf.excep.FormatException;
import com.elfso.data.ElfFile;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ElfParser {

    public ElfFile parse(RandomAccessFile racFile) {
        if (racFile == null) {
            return null;
        }
        try {
            ElfFile elf = new ElfFile();
            elf.parse(racFile);
            return elf;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        RandomAccessFile raf = null;
        try {
            raf = FileUtil.loadAsRAF("parser_elfso/res/libjiagu.so");
            ElfFile elfFile = new ElfParser().parse(raf);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileUtil.closeQuietly(raf);

    }
}
