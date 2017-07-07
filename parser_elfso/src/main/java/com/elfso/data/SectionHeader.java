package com.elfso.data;

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
}
