package com.dex.data;

import com.dex.stream.DexStreamer;
import com.dex.stream.LittleEndianStreamer;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Created by xueqiulxq on 07/08/2017.
 */

public class DexFile {

    private DexStreamer mStreamer;
    public DexHeader dexHeader;
    public StringPool stringPool;
    public TypePool typePool;
    public ProtoPool protoPool;
    public FieldPool fieldPool;
    public MethodPool methodPool;
    public ClassPool classPool;

    public void parse(RandomAccessFile racFile) throws IOException {
        racFile.seek(0);
        mStreamer = new LittleEndianStreamer();

        byte[] headerBytes;

        headerBytes = new byte[DexHeader.LENGTH];
        racFile.read(headerBytes, 0, headerBytes.length);
        mStreamer.use(headerBytes);
        dexHeader = DexHeader.parseFrom(mStreamer);

        stringPool = StringPool.parseFrom(racFile, mStreamer, dexHeader);
        typePool = TypePool.parseFrom(racFile, mStreamer, dexHeader, stringPool);
        protoPool = ProtoPool.parseFrom(racFile, mStreamer, dexHeader, stringPool, typePool);
        fieldPool = FieldPool.parseFrom(racFile, mStreamer, dexHeader, stringPool, typePool);
        methodPool = MethodPool.parseFrom(racFile, mStreamer, dexHeader, stringPool, typePool, protoPool);
        classPool = ClassPool.parseFrom(racFile, mStreamer, dexHeader, stringPool, typePool, protoPool);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(dexHeader);
        builder.append('\n');
        builder.append(stringPool);
        builder.append('\n');
        builder.append(typePool);
        builder.append('\n');
        builder.append(protoPool);
        builder.append('\n');
        builder.append(fieldPool);
        builder.append('\n');
        builder.append(methodPool);
        builder.append('\n');
        builder.append(classPool);
        return builder.toString();
    }
}
