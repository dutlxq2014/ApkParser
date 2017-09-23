package com.dex.data;

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }
}
