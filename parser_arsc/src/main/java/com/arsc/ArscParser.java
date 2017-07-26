package com.arsc;

import com.arsc.data.ArscFile;
import com.common.FileUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ArscParser {


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


    public static void main(String[] args) {

        String fileName = "parser_arsc/res/resources.arsc";
        ArscParser parser = new ArscParser();
        ArscFile arscFile = parser.parse(fileName);
        System.out.println(arscFile);
    }
}

