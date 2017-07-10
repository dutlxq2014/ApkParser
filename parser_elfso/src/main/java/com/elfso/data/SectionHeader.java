package com.elfso.data;

import com.common.PrintUtil;
import com.elfso.stream.SectionStreamer;

/**
 *
 * Created by xueqiulxq on 07/07/2017.
 */

public class SectionHeader {

    public static final int SHN_UNDEF = 0;
    public static final int SHN_LORESERVE = 0xFF00;
    public static final int SHN_LOPROC = 0xFF00;
    public static final int SHN_HIPROC = 0XFF1F;
    public static final int SHN_ABS = 0xFFF1;
    public static final int SHN_COMMON = 0xFFF2;
    public static final int SHN_HIRESERVE = 0xFFFF;

    // sh_type
    public static final int SHT_NULL = 0;
    public static final int SHT_PROGBITS = 1;
    public static final int SHT_SYMTAB = 2;
    public static final int SHT_STRTAB = 3;
    public static final int SHT_RELA = 4;
    public static final int SHT_HASH = 5;
    public static final int SHT_DYNAMIC = 6;
    public static final int SHT_NOTE = 7;
    public static final int SHT_NOBITS = 8;
    public static final int SHT_REL = 9;
    public static final int SHT_SHLIB = 10;
    public static final int SHT_DYNSYM = 11;
    public static final int SH_NUM = 12;
    public static final int SHT_LOPROC = 0x70000000;
    public static final int SHT_HIPROC = 0x7FFFFFFF;
    public static final int SHT_LOUSER = 0x80000000;
    public static final int SHT_HIUSER = 0X8FFFFFFF;

    // sh_flags
    public static final int SHF_WRITE = 0x1;    // mask
    public static final int SHF_ALLOC = 0x2;
    public static final int SHF_EXECINSTR = 0x4;
    public static final int SHF_MASKPROC = 0xF0000000;

    // sh_link sh_info

    public long sh_name;        // Elf32_Word
    public long sh_type;        // Elf32_Word
    public long sh_flags;       // Elf32_Word
    public long sh_addr;        // Elf32_Addr
    public long sh_offset;      // Elf32_Off
    public long sh_size;        // Elf32_Word
    public long sh_link;        // Elf32_Word
    public long sh_info;        // Elf32_Word
    public long sh_addralign;   // Elf32_Word
    public long sh_entsize;     // Elf32_Word

    public static SectionHeader parseFrom(SectionStreamer s) {
        SectionHeader h = new SectionHeader();
        h.sh_name = s.readElf32Word();
        h.sh_type = s.readElf32Word();
        h.sh_flags = s.readElf32Word();
        h.sh_addr = s.readElf32Addr();
        h.sh_offset = s.readElf32Off();
        h.sh_size = s.readElf32Word();
        h.sh_link = s.readElf32Word();
        h.sh_info = s.readElf32Word();
        h.sh_addralign = s.readElf32Word();
        h.sh_entsize = s.readElf32Word();
        return h;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String form = "%-12s\t%s\n";
        builder.append(String.format(form, "sh_name", PrintUtil.hex4(sh_name)));
        String type;
        switch ((int)sh_type) {
            case SHT_NULL: type = "SHT_NULL"; break;
            case SHT_PROGBITS: type = "SHT_PROGBITS .comment .data .data1 .debug .fini .got .init .interp .line .plt .rodata .rodata1 .text"; break;
            case SHT_SYMTAB: type = "SHT_SYMTAB .symtab"; break;
            case SHT_STRTAB: type = "SHT_STRTAB .dynstr .shstrtab .strtab"; break;
            case SHT_RELA: type = "SHT_RELA .relaname"; break;
            case SHT_HASH: type = "SHT_HASH .hash"; break;
            case SHT_DYNAMIC: type = "SHT_DYNAMIC .dynamic"; break;
            case SHT_NOTE: type = "SHT_NOTE .note"; break;
            case SHT_NOBITS: type = "SHT_NOBITS .bss"; break;
            case SHT_REL: type = "SHT_REL relname"; break;
            case SHT_SHLIB: type = "SHT_SHLIB"; break;
            case SHT_DYNSYM: type = "SHT_DYNSYM .dynsym"; break;
            case SH_NUM: type = "SH_NUM"; break;
            case SHT_LOPROC: type = "SHT_LOPROC"; break;
            case SHT_HIPROC: type = "SHT_HIPROC"; break;
            case SHT_LOUSER: type = "SHT_LOUSER"; break;
            case SHT_HIUSER: type = "SHT_HIUSER"; break;
            default: type = "unknow";
        }
        builder.append(String.format(form, "sh_type", PrintUtil.hex4(sh_type) + "\t" + type));
        builder.append(String.format(form, "sh_flags", PrintUtil.hex4(sh_flags)));
        builder.append(String.format(form, "sh_addr", PrintUtil.hex4(sh_addr)));
        builder.append(String.format(form, "sh_offset", PrintUtil.hex4(sh_offset)));
        builder.append(String.format(form, "sh_size", PrintUtil.hex4(sh_size)));
        builder.append(String.format(form, "sh_link", PrintUtil.hex4(sh_link)));
        builder.append(String.format(form, "sh_info", PrintUtil.hex4(sh_info)));
        builder.append(String.format(form, "sh_addralign", PrintUtil.hex4(sh_addralign)));
        builder.append(String.format(form, "sh_entsize", PrintUtil.hex4(sh_entsize)));

        builder.append('\n');
        return builder.toString();
    }
}
