package com.manifest;

import com.common.FileUtil;
import com.manifest.data.MfFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ManifestParser {

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

    public static final void main(String[] args) {

        String fileName = "parser_manifest/res/DemoManifest.xml";
        ManifestParser parser = new ManifestParser();
        MfFile mfFile = parser.parse(fileName);
        if (mfFile != null) {
            System.out.println(mfFile.toString());
        } else {
            System.out.println("Parse failed: " + fileName);
        }
    }
}
