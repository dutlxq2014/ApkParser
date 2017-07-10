package com.elfso.data;

import com.common.PrintUtil;
import com.elfso.stream.SectionStreamer;

/**
 *
 * Created by xueqiulxq on 07/07/2017.
 */

public class ProgramHeader {

    // p_type
    public static final int PT_NULL = 0;
    public static final int PT_LOAD = 1;
    public static final int PT_DYNAMIC = 2;
    public static final int PT_INTERP = 3;
    public static final int PT_NOTE = 4;
    public static final int PT_SHLIB = 5;
    public static final int PT_PHDR = 6;
    public static final int PT_LOPROC = 0x70000000;
    public static final int PT_HIPROC = 0x7fffffff;

    public long p_type;     // Elf32_Word   segment type
    public long p_offset;   // Elf32_Off    segment offset
    public long p_vaddr;    // Elf32_Addr   virtual address of segment
    public long p_addr;     // Elf32_Addr   physical address
    public long p_filesz;   // Elf32_Word   number of bytes in file for segment
    public long p_memsz;    // Elf32_Word   number of bytes in mem for segment
    public long p_flags;    // Elf32_Word   flags
    public long p_align;    // Elf32_Word   memory alignment

    public static ProgramHeader parserFrom(SectionStreamer s) {
        ProgramHeader ph = new ProgramHeader();
        ph.p_type = s.readElf32Word();
        ph.p_offset = s.readElf32Off();
        ph.p_vaddr = s.readElf32Addr();
        ph.p_addr = s.readElf32Addr();
        ph.p_filesz = s.readElf32Word();
        ph.p_memsz = s.readElf32Word();
        ph.p_flags = s.readElf32Word();
        ph.p_align = s.readElf32Word();
        return ph;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String form = "%-12s\t%s\n";
        String type;
        switch ((int)p_type) {
            case PT_NULL: type = "PT_NULL"; break;
            case PT_LOAD: type = "PT_LOAD"; break;
            case PT_DYNAMIC: type = "PT_DYNAMIC"; break;
            case PT_INTERP: type = "PT_INTERP"; break;
            case PT_NOTE: type = "PT_NOTE"; break;
            case PT_SHLIB: type = "PT_SHLIB"; break;
            case PT_PHDR: type = "PT_PHDR"; break;
            case PT_LOPROC: type = "PT_LOPROC"; break;
            case PT_HIPROC: type = "PT_HIPROC"; break;
            default: type = "unknown";
        }
        builder.append(String.format(form, "p_type", PrintUtil.hex4(p_type) + "\t" + type));
        builder.append(String.format(form, "p_offset", PrintUtil.hex4(p_offset)));
        builder.append(String.format(form, "p_vaddr", PrintUtil.hex4(p_vaddr)));
        builder.append(String.format(form, "p_addr", PrintUtil.hex4(p_addr)));
        builder.append(String.format(form, "p_filesz", PrintUtil.hex4(p_filesz)));
        builder.append(String.format(form, "p_memsz", PrintUtil.hex4(p_memsz)));
        builder.append(String.format(form, "p_flags", PrintUtil.hex4(p_flags)));
        builder.append(String.format(form, "p_align", PrintUtil.hex4(p_align)));
        builder.append('\n');

        return builder.toString();
    }
}