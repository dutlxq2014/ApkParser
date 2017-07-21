package com.manifest;

import com.common.FileUtil;
import com.manifest.data.MfFile;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

public class ManifestParser {

    private static final String OUTPUT_DIR = "parser_manifest/build/";

    public static final void main(String[] args) {

        String fileName = "parser_manifest/res/compiled_manifest.xml";
        ManifestParser parser = new ManifestParser();
        MfFile mfFile = parser.parse(fileName);
        if (mfFile != null) {
            System.out.println(mfFile.toString());
        } else {
            System.out.println("Parse failed: " + fileName);
        }

        // Dump raw chunks to build directory
        try {
            FileOutputStream fos = new FileOutputStream(OUTPUT_DIR + "raw_chunks.txt");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(mfFile.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Dump format xml to build director
        try {
            FileOutputStream fos = new FileOutputStream(OUTPUT_DIR + "format_mf.xml");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(mfFile.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MfFile parse(String mfFileName) {
        RandomAccessFile racFile;
        try {
            racFile = FileUtil.loadAsRAF(mfFileName);
            MfFile mfFile = new MfFile();
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
