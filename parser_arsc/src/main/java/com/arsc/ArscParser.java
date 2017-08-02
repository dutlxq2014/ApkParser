package com.arsc;

import com.arsc.data.ArscFile;
import com.common.FileUtil;
import com.common.LogUtil;

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

        LogUtil.e(TAG, "Result:");
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
    }

    public ArscFile parse(String mfFileName) {
        RandomAccessFile racFile;
        try {
            racFile = FileUtil.loadAsRAF(mfFileName);
            ArscFile mfFile = new ArscFile();
            mfFile.parse(racFile);
            return mfFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

