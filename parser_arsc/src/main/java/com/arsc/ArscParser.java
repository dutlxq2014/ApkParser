package com.arsc;

import com.arsc.data.ArscFile;
import com.arsc.data.ResTableEntry;
import com.common.FileUtil;
import com.common.LogUtil;
import com.common.PrintUtil;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

public class ArscParser {

    private static final String TAG = "ArscParser";

    private static final String OUTPUT_DIR = "parser_arsc/build/";

    public static void main(String[] args) {

        String fileName = "parser_arsc/res/resources.arsc";
        ArscParser parser = new ArscParser();
        ArscFile arscFile = parser.parse(fileName);
        // System.out.println(arscFile);

        System.out.println();
        LogUtil.i(TAG, "Result:");
        // Dump raw chunks to build directory
        try {
            String targetFile = OUTPUT_DIR + "raw_chunks.txt";
            FileOutputStream fos = new FileOutputStream(targetFile);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(arscFile.toString());
            writer.close();
            System.out.println("Raw chunk: " + targetFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Build public.xml
        try {
            String targetFile = OUTPUT_DIR + "public.xml";
            FileOutputStream fos = new FileOutputStream(targetFile);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(arscFile.buildPublicXml());
            writer.close();
            System.out.println("Public xml: " + targetFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
        LogUtil.i(TAG, "Test getResource:");
        // Test
        int resId = 0x7f010002;
        ResTableEntry res = arscFile.getResource(resId);
        if (res != null) {
            LogUtil.i(TAG, res.toString());
        } else {
            LogUtil.i(TAG, "Resource ID 0x" + PrintUtil.hex4(resId) + " cannot be found.");
        }
    }

    public ArscFile parse(String mfFileName) {
        RandomAccessFile racFile = null;
        try {
            racFile = FileUtil.loadAsRAF(mfFileName);
            ArscFile mfFile = new ArscFile();
            mfFile.parse(racFile);
            return mfFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileUtil.closeQuietly(racFile);
        }
        return null;
    }

}

