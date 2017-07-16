package com.elfso;

import com.common.FileUtil;
import com.common.LogUtil;
import com.elf.excep.FormatException;
import com.elfso.data.ElfFile;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ElfParser {

    public ElfFile parse(String soFileName) {
        RandomAccessFile racFile = null;
        try {
            racFile = FileUtil.loadAsRAF(soFileName);
            ElfFile elf = new ElfFile();
            elf.parse(racFile);
            return elf;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileUtil.closeQuietly(racFile);
        }
        return null;
    }

    public static void main(String[] args) {

        String fileName = "parser_elfso/res/libhello-jni.so";
//        String fileName = "parser_elfso/res/libjiagu.so";
        ElfParser parser = new ElfParser();
        ElfFile elfFile = parser.parse(fileName);
        if (elfFile != null) {
            LogUtil.i(elfFile.toString());
        } else {
            LogUtil.e("Parse failed: " + fileName);
        }
    }
}
