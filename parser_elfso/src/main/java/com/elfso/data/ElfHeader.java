package com.elfso.data;

import com.common.PrintUtil;
import com.elfso.stream.SectionStreamer;

/**
 *
 * Created by xueqiulxq on 07/07/2017.
 */

public class ElfHeader {

    public static final int LENGTH = 0x34;

    // e_type
    public static final int ET_NONE = 0;
    public static final int ET_REL = 1;
    public static final int ET_EXEC = 2;
    public static final int ET_DYN = 3;
    public static final int ET_CORE = 4;
    public static final int ET_LOPROC = 0Xff00;
    public static final int ET_HIPROC = 0xffff;
    // e_machine
    public static final int EM_NONE = 0;
    public static final int EM_M32 = 1;
    public static final int EM_SPARC = 2;
    public static final int EM_386 = 3;
    public static final int EM_68K = 4;
    public static final int EM_88 = 5;
    public static final int EM_860 = 7;
    public static final int EM_MIPS = 8;
    public static final int EM_MIPS_RS4_BE = 10;
    public static final int EM_SPARC64 = 11;
    public static final int EM_PARISC = 15;
    public static final int EM_SPARC32PLUS = 18;
    public static final int EM_PPC = 20;
    public static final int EM_ARM = 40;
    public static final int EM_ALPHA = 41;
    public static final int EM_SPARCV9 = 43;
    public static final int EM_ALPHA_EXP = 0x9026;
    public static final int EM_AMD64 = 62;
    public static final int EM_VAX = 75;
    public static final int EM_NUM = 15;
    // e_version
    public static final int EV_NONE = 0;
    public static final int EV_CURRENT = 1;

    public Eident e_indent;     // ELF Identification
    public int e_type;          // 10h Elf32_Half   object file type
    public int e_machine;       // 12h Elf32_Half   machine
    public long e_version;      // 14h Elf32_Word   object file version
    public long e_entry;        // 18h Elf32_Addr   virtual entry point
    public long e_phoff;        // 1ch Elf32_Off    program header table offset
    public long e_shoff;        // 20h Elf32_Off    section header table offset
    public long e_flags;        // 24h Elf32_Word   processor-specific flags
    public int e_ehsize;        // 28h Elf32_Half   ELF header size
    public int e_phentsize;     // 2ah Elf32_Half   program header entry size
    public int e_phnum;         // 2ch Elf32_Half   number of program header entries
    public int e_shentsize;     // 2eh Elf32_Half   section header entry size
    public int e_shnum;         // 30h Elf32_Half   number of section header entries
    public int e_shstrndx;      // 32h Elf32_Half   section header table's section header string table entry offset

    public static ElfHeader parseFrom(SectionStreamer s) {
        ElfHeader header = new ElfHeader();
        header.e_indent = Eident.parseFrom(s.read(Eident.EI_NIDENT));
        header.e_type = s.readElf32Half();
        header.e_machine = s.readElf32Half();
        header.e_version = s.readElf32Word();
        header.e_entry = s.readElf32Addr();
        header.e_phoff = s.readElf32Off();
        header.e_shoff = s.readElf32Off();
        header.e_flags = s.readElf32Word();
        header.e_ehsize = s.readElf32Half();
        header.e_phentsize = s.readElf32Half();
        header.e_phnum = s.readElf32Half();
        header.e_shentsize = s.readElf32Half();
        header.e_shnum = s.readElf32Half();
        header.e_shstrndx = s.readElf32Half();
        return header;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Elf header:\n");
        builder.append(this.e_indent);

        String form1 = "%-12s\t";
        builder.append(String.format(form1, "e_type"));
        switch (e_type) {
            case ET_NONE: builder.append("ET_NONE"); break;
            case ET_REL: builder.append("ET_REL"); break;
            case ET_EXEC: builder.append("ET_EXEC"); break;
            case ET_DYN: builder.append("ET_DYN"); break;
            case ET_CORE: builder.append("ET_CORE"); break;
            case ET_LOPROC: builder.append("ET_LOPROC"); break;
            case ET_HIPROC: builder.append("ET_HIPROC"); break;
            default: builder.append(PrintUtil.hex2(e_type));
        }
        builder.append('\n');

        builder.append(String.format(form1, "e_machine"));
        switch (e_machine) {
            case EM_NONE: builder.append("EM_NONE"); break;
            case EM_M32: builder.append("EM_M32"); break;
            case EM_SPARC: builder.append("EM_SPARC"); break;
            case EM_386: builder.append("EM_386"); break;
            case EM_68K: builder.append("EM_68K"); break;
            case EM_88: builder.append("EM_88"); break;
            case EM_860: builder.append("EM_860"); break;
            case EM_MIPS: builder.append("EM_MIPS"); break;
            case EM_MIPS_RS4_BE: builder.append("EM_MIPS_RS4_BE"); break;
            case EM_SPARC64: builder.append("EM_SPARC64"); break;
            case EM_PARISC: builder.append("EM_PARISC"); break;
            case EM_SPARC32PLUS: builder.append("EM_SPARC32PLUS"); break;
            case EM_PPC: builder.append("EM_PPC"); break;
            case EM_ARM: builder.append("EM_ARM"); break;
            case EM_ALPHA: builder.append("EM_ALPHA"); break;
            case EM_SPARCV9: builder.append("EM_SPARCV9"); break;
            case EM_ALPHA_EXP: builder.append("EM_ALPHA_EXP"); break;
            case EM_AMD64: builder.append("EM_AMD64"); break;
            case EM_VAX: builder.append("EM_VAX"); break;
            default: builder.append(PrintUtil.hex2(e_machine));
        }

        builder.append('\n');

        String form2 = "%-12s\t%s\n";
        builder.append(String.format(form2, "e_version", PrintUtil.hex4(e_version)));
        builder.append(String.format(form2, "e_entry", PrintUtil.hex4(e_entry)));
        builder.append(String.format(form2, "e_phoff", PrintUtil.hex4(e_phoff)));
        builder.append(String.format(form2, "e_shoff", PrintUtil.hex4(e_shoff)));
        builder.append(String.format(form2, "e_flags", PrintUtil.hex4(e_flags)));

        builder.append(String.format(form2, "e_ehsize", PrintUtil.hex2(e_ehsize)));
        builder.append(String.format(form2, "e_phentsize", PrintUtil.hex2(e_phentsize)));
        builder.append(String.format(form2, "e_phnum", PrintUtil.hex2(e_phnum)));
        builder.append(String.format(form2, "e_shentsize", PrintUtil.hex2(e_shentsize)));
        builder.append(String.format(form2, "e_shnum", PrintUtil.hex2(e_shnum)));
        builder.append(String.format(form2, "e_shstrndx", PrintUtil.hex2(e_shstrndx)));
        return builder.toString();
    }
}
