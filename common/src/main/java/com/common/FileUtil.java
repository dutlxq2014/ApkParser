package com.common;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileUtil {

    public static RandomAccessFile loadAsRAF(String pathname) throws FileNotFoundException {
        if (!new File(pathname).exists()) {
            return null;
        }
        return new RandomAccessFile(pathname, "r");
    }

    public static void closeQuietly(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
