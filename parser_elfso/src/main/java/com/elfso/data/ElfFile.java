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
    public ElfHeader elfHeader;
    public ProgramHeader programHeader;

    public ElfFile() {

    }

    public void parse(RandomAccessFile racFile) throws IOException, FormatException {

        racFile.seek(0);

        // Determine endian
        Eident eident = parseIdent(racFile);
        if (eident.ei_data == Eident.ELFDATA2MSB) {
            mStreamer = new BigEndianStreamer();
        } else if (eident.ei_data == Eident.ELFDATA2LSB) {
            mStreamer = new LittleEndianStreamer();
        } else {
            throw new FormatException("Illegal data format");
        }

        // header
        elfHeader = parseHeader(racFile);
        System.out.println(elfHeader);


        LogUtil.i("Parse end!");
    }

    private Eident parseIdent(RandomAccessFile racFile) throws IOException {
        long old = racFile.getFilePointer();
        racFile.seek(0);
        byte[] ident = new byte[Eident.EI_NIDENT];
        racFile.read(ident, 0, ident.length);
        Eident eident = Eident.parseFrom(ident);
        racFile.seek(old);
        return eident;
    }

    private ElfHeader parseHeader(RandomAccessFile racFile) throws IOException {
        long old = racFile.getFilePointer();
        racFile.seek(0);
        byte[] header = new byte[ElfHeader.LENGTH];
        racFile.read(header, 0, header.length);
        mStreamer.use(header);
        ElfHeader elfHeader = ElfHeader.parseFrom(mStreamer);
        racFile.seek(old);
        return elfHeader;
    }
}
