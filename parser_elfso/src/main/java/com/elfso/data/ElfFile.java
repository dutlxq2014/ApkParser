package com.elfso.data;

import com.common.LogUtil;
import com.elf.excep.FormatException;
import com.elfso.stream.BigEndianStreamer;
import com.elfso.stream.LittleEndianStreamer;
import com.elfso.stream.SectionStreamer;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Created by xueqiulxq on 07/07/2017.
 */

public class ElfFile {

    // Tools
    private SectionStreamer mStreamer;
    // Sections
    public Eident eident;
    public ElfHeader elfHeader;
    public ProgramHeader[] programHeaders;
    public SectionHeader[] sectionHeaders;
    public StringTable shStringTable;

    public ElfFile() {

    }

    public void parse(RandomAccessFile racFile) throws IOException, FormatException {

        racFile.seek(0);

        // Determine endian
        eident = parseIdent(racFile);
        if (eident.ei_data == Eident.ELFDATA2MSB) {
            mStreamer = new BigEndianStreamer();
        } else if (eident.ei_data == Eident.ELFDATA2LSB) {
            mStreamer = new LittleEndianStreamer();
        } else {
            throw new FormatException("Illegal data format");
        }

        // header
        elfHeader = parseHeader(racFile);

        // Program header
        programHeaders = parseProgramHeader(racFile);

        // Section header
        sectionHeaders = parseSectionHeaders(racFile);

        // String table
        shStringTable = parseShStringTable(racFile);

        completeElfDetails();

        LogUtil.i("Parse end!\n");
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder(4096);

        builder.append(elfHeader).append('\n');

        for (int i=0; i<programHeaders.length; ++i) {
            builder.append("Program header-").append(i).append('\n');
            builder.append(programHeaders[i]);
        }

        for (int i=0; i<sectionHeaders.length; ++i) {
            builder.append("Section header-").append(i).append('\n');
            builder.append(sectionHeaders[i]);
        }

        builder.append(shStringTable).append('\n');

        return builder.toString();
    }

    private Eident parseIdent(RandomAccessFile racFile) throws IOException {
        long old = racFile.getFilePointer();
        racFile.seek(0);
        byte[] bytes = new byte[Eident.EI_NIDENT];
        racFile.read(bytes, 0, bytes.length);
        Eident eident = Eident.parseFrom(bytes);
        racFile.seek(old);
        return eident;
    }

    private ElfHeader parseHeader(RandomAccessFile racFile) throws IOException {
        long old = racFile.getFilePointer();
        racFile.seek(0);
        byte[] bytes = new byte[ElfHeader.LENGTH];
        racFile.read(bytes, 0, bytes.length);
        mStreamer.use(bytes);
        ElfHeader header = ElfHeader.parseFrom(mStreamer);
        racFile.seek(old);
        return header;
    }

    private ProgramHeader[] parseProgramHeader(RandomAccessFile racFile) throws IOException {
        long old = racFile.getFilePointer();
        racFile.seek(0);
        long offset = elfHeader.e_phoff;
        int entSize = elfHeader.e_phentsize;
        int num = elfHeader.e_phnum;

        byte[] bytes = new byte[entSize * num];
        racFile.seek(offset);
        racFile.read(bytes, 0, bytes.length);
        mStreamer.use(bytes);

        ProgramHeader[] phs = new ProgramHeader[num];
        for (int i=0; i<num; ++i) {
            phs[i] = ProgramHeader.parserFrom(mStreamer);
        }
        racFile.seek(old);
        return phs;
    }

    private StringTable parseShStringTable(RandomAccessFile racFile) throws IOException {
        long old = racFile.getFilePointer();
        racFile.seek(0);
        int ndx = elfHeader.e_shstrndx;
        SectionHeader shstrHeader = sectionHeaders[ndx];
        racFile.seek(shstrHeader.sh_offset);
        byte[] bytes = new byte[(int)shstrHeader.sh_size];
        racFile.read(bytes, 0, bytes.length);
        mStreamer.use(bytes);
        StringTable strTab = StringTable.parseFrom(mStreamer);
        racFile.seek(old);
        return strTab;
    }

    private SectionHeader[] parseSectionHeaders(RandomAccessFile racFile) throws IOException {
        long old = racFile.getFilePointer();
        racFile.seek(0);
        long offset = elfHeader.e_shoff;
        int entSize = elfHeader.e_shentsize;
        int num = elfHeader.e_shnum;

        byte[] bytes = new byte[entSize * num];
        racFile.seek(offset);
        racFile.read(bytes, 0, bytes.length);
        mStreamer.use(bytes);

        SectionHeader[] shs = new SectionHeader[num];
        for (int i=0; i<num; ++i) {
            shs[i] = SectionHeader.parseFrom(mStreamer);
        }
        racFile.seek(old);
        return shs;
    }

    private void completeElfDetails() {
        for (SectionHeader sh : sectionHeaders) {
            sh.fillDetail(shStringTable);
        }
    }
}
