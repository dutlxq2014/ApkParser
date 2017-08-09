package com.dex;

import com.common.FileUtil;
import com.common.LogUtil;
import com.dex.data.DexFile;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

public class DexParser {

    private static final String TAG = DexParser.class.getSimpleName();

    private static final String OUTPUT_DIR = "parser_dex/build/";

    public DexFile parse(String dexFileName) {
        RandomAccessFile racFile = null;
        try {
            racFile = FileUtil.loadAsRAF(dexFileName);
            DexFile dexFile = new DexFile();
            dexFile.parse(racFile);
            return dexFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            FileUtil.closeQuietly(racFile);
        }
        return null;
    }

    public static void main(String[] args) {

        String fileName = "parser_dex/res/classes.dex";
        DexParser dexParser = new DexParser();
        DexFile dexFile = dexParser.parse(fileName);
        System.out.println(dexFile);

        System.out.println();
        LogUtil.i(TAG, "Result:");
        try {
            String targetFile = OUTPUT_DIR + "raw_chunk.txt";
            FileOutputStream fos = new FileOutputStream(targetFile);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(dexFile.toString());
            writer.close();
            System.out.println("Raw chunk: " + targetFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
