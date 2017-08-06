package com.dex;

import com.common.FileUtil;
import com.common.LogUtil;
import com.dex.data.DexFile;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class DexParser {

    private static final String TAG = DexParser.class.getSimpleName();

    public DexFile parse(String dexFileName) {
        RandomAccessFile racFile;
        try {
            racFile = FileUtil.loadAsRAF(dexFileName);
            DexFile dexFile = new DexFile();
            dexFile.parse(racFile);
            return dexFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        String fileName = "parser_dex/res/classes.dex";
        DexParser dexParser = new DexParser();
        DexFile dexFile = dexParser.parse(fileName);

        System.out.println();
        LogUtil.i(TAG, "Result:");
    }
}
