package com.dex.data;

import com.common.PrintUtil;
import com.dex.stream.DexStreamer;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Created by xueqiulxq on 19/09/2017.
 */

public class CodeItem {

    public int registersSize;   // 2B number of register need for current code segment
    public int insSize;         // 2B number of input parameters
    public int outsSize;        // 2B number of parameters for other method
    public int triesSize;       // 2B number of try_item
    public long debugInfoOff;   // 4B offset to debug_info_item in current code segment
    public long insnsSize;      // 4B size of instructions list. insns is short for instructions
    public int[] insns;         // 2B*n
//    public int padding;                 // optional
//    public try_item tries[triesSize];   // optional
//    public encoded_catch_handler_list handlers  // optional

    public static CodeItem parseFrom(RandomAccessFile racFile, DexStreamer s,
                                     StringPool stringPool, TypePool typePool, ProtoPool protoPool) throws IOException {
        CodeItem item = new CodeItem();
        byte[] sizeBytes = new byte[2*4 + 4*2];
        racFile.read(sizeBytes, 0, sizeBytes.length);
        s.use(sizeBytes);
        item.registersSize = s.readU2();
        item.insSize = s.readU2();
        item.outsSize = s.readU2();
        item.triesSize = s.readU2();
        item.debugInfoOff = s.readU4();
        item.insnsSize = s.readU4();
        item.insns = new int[0];
        int[] insns = item.insns = new int[(int) item.insnsSize];

        byte[] insnsBytes = new byte[insns.length * 2];
        racFile.read(insnsBytes, 0, insnsBytes.length);
        s.use(insnsBytes);
        for (int i=0; i<insns.length; ++i) {
            insns[i] = s.readU2();
        }

        return item;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String form2 = "%s=%d ";
        String form2Str = "%s=%s ";
        builder.append("-- CodeItems --\n");
        builder.append(String.format(form2, "registersSize", registersSize));
        builder.append(String.format(form2, "insSize", insSize));
        builder.append(String.format(form2, "outsSize", outsSize));
        builder.append(String.format(form2, "triesSize", triesSize));
        builder.append(String.format(form2Str, "debugInfoOff", debugInfoOff));
        builder.append(String.format(form2Str, "insnsSize", insnsSize));
        builder.append('\n');
        builder.append("insns[]: ");
        for (int i=0; i<insns.length; ++i) {
            builder.append(PrintUtil.hex2(insns[i])).append(' ');
        }
        builder.append("\n");

        return builder.toString();
    }
}
