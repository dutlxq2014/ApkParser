package com.dex.data;

import com.dex.stream.DexStreamer;
import com.dex.stream.LittleEndianStreamer;

import java.io.RandomAccessFile;

/**
 *
 * Created by xueqiulxq on 07/08/2017.
 */

public class DexFile {

    private DexStreamer mStreamer;

    public void parse(RandomAccessFile racFile) {

        mStreamer = new LittleEndianStreamer();

    }
}
