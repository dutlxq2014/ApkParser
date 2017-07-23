package com.manifest.data;

import com.common.LogUtil;
import com.manifest.stream.LittleEndianStreamer;
import com.manifest.stream.MfStreamer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by xueqiulxq on 16/07/2017.
 */

public class MfFile {

    private MfStreamer mStreamer;
    public MfHeader header;
    public StringChunk stringChunk;
    public ResourceIdChunk resourceIdChunk;
    public StartNamespaceChunk startNamespaceChunk;
    public List<StartTagChunk> startTagChunks;
    public List<EndTagChunk> endTagChunks;
    public List<TagChunk> tagChunks;
    public EndNamespaceChunk endNamespaceChunk;

    public MfFile() {
        startTagChunks = new ArrayList<StartTagChunk>();
        endTagChunks = new ArrayList<EndTagChunk>();
        tagChunks = new ArrayList<TagChunk>();
    }

    public void parse(RandomAccessFile racFile) throws IOException {
        racFile.seek(0);
        mStreamer = new LittleEndianStreamer();

        byte[] headerBytes = new byte[MfHeader.LENGTH];
        racFile.read(headerBytes, 0, headerBytes.length);
        header = parseHeader(headerBytes);

        int chunkIdx = 0;
        int cursor = MfHeader.LENGTH;
        do {
            byte[] infoBytes = new byte[ChunkInfo.LENGTH];
            cursor += racFile.read(infoBytes, 0, infoBytes.length);
            mStreamer.use(infoBytes);
            ChunkInfo info = ChunkInfo.parseFrom(mStreamer);
            info.chunkIndex = chunkIdx++;

            // Chunk size = ChunkInfo + BodySize
            byte[] chunkBytes = new byte[(int)info.chunkSize];
            System.arraycopy(infoBytes, 0, chunkBytes, 0, ChunkInfo.LENGTH);
            cursor += racFile.read(chunkBytes, ChunkInfo.LENGTH, (int)info.chunkSize - ChunkInfo.LENGTH);
            StartTagChunk startTagChunk;
            EndTagChunk endTagChunk;
            switch ((int)info.chunkType) {
                case ChunkInfo.STRING_CHUNK_ID:
                    stringChunk = parseStringChunk(chunkBytes);
                    break;
                case ChunkInfo.RESOURCE_ID_CHUNK_ID:
                    resourceIdChunk = parseResourceIdChunk(chunkBytes);
                    break;
                case ChunkInfo.START_NAMESPACE_CHUNK_ID:
                    startNamespaceChunk = parseStartNamespaceChunk(chunkBytes);
                    break;
                case ChunkInfo.START_TAG_CHUNK_ID:
                    startTagChunk = parseStartTagChunk(chunkBytes);
                    startTagChunks.add(startTagChunk);
                    tagChunks.add(startTagChunk);
                    break;
                case ChunkInfo.EDN_TAG_CHUNK_ID:
                    endTagChunk = parseEndTagChunk(chunkBytes);
                    endTagChunks.add(endTagChunk);
                    tagChunks.add(endTagChunk);
                    break;
                case ChunkInfo.CHUNK_ENDNS_CHUNK_ID:
                    endNamespaceChunk = parseEndNamespaceChunk(chunkBytes);
                    break;
                default:
                    break;
            }
            LogUtil.i(info.toString());
        } while (cursor < header.fileLength);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(4096);
        builder.append('\n');
        builder.append(header).append('\n');
        builder.append(stringChunk).append('\n');
        builder.append(resourceIdChunk).append('\n');
        builder.append(startNamespaceChunk).append('\n');

        builder.append("-- StartTag Chunks --").append('\n');
        for (int i=0; i<startTagChunks.size(); ++i) {
            builder.append("StartTagChunk_").append(i).append('\n');
            builder.append(startTagChunks.get(i)).append('\n');
        }
        builder.append("-- EndTag Chunks --").append('\n');
        for (int i=0; i<endTagChunks.size(); ++i) {
            builder.append("EndTagChunk_").append(i).append('\n');
            builder.append(endTagChunks.get(i)).append('\n');
        }
        builder.append(endNamespaceChunk).append('\n');
        return builder.toString();
    }

    /**
     * Convert MfFile object to XML string.
     * @return xml
     */
    public String toXmlString() {
        StringBuilder builder = new StringBuilder(4096);
        int depth = 0;
        builder.append("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>\n");
        for (TagChunk tagChunk : tagChunks) {
            if (tagChunk instanceof StartTagChunk) {
                builder.append(createStartTagXml((StartTagChunk) tagChunk, depth));
                ++depth;
            } else if (tagChunk instanceof EndTagChunk) {
                --depth;
                builder.append(createEndTagXml((EndTagChunk) tagChunk, depth));
            }
        }
        return builder.toString();
    }


    private String createStartTagXml(StartTagChunk chunk, int depth) {
        StringBuilder builder = new StringBuilder(256);
        String lineIndent = makeLineIndent(depth, 4);
        if ("manifest".equals(chunk.nameStr)) {
            builder.append("<manifest\n");
            for (Map.Entry<String, String> entry : startNamespaceChunk.prefix2UriMap.entrySet()) {
                builder.append("    ").append("xmlns:").append(entry.getKey()).append("=")
                        .append("\"").append(entry.getValue()).append("\"");
            }
        } else {
            builder.append(lineIndent);
            builder.append("<").append(chunk.nameStr);
        }
        List<AttributeEntry> attrEntries = chunk.attributes;
        if (attrEntries.size() > 0) {
            for (AttributeEntry entry : attrEntries) {

                builder.append("\n");
                builder.append(lineIndent).append("    ");
                String prefixName = startNamespaceChunk.uri2prefixMap.get(entry.namespaceUriStr);
                if (prefixName != null) {
                    builder.append(prefixName).append(':');
                }
                builder.append(entry.nameStr).append('=')
                        .append("\"").append(entry.dataStr).append("\"");
            }
        }
        builder.append(" >\n");
        return builder.toString();
    }

    private String createEndTagXml(EndTagChunk chunk, int depth) {
        StringBuilder builder = new StringBuilder(256);
        String lineIndent = makeLineIndent(depth, 4);
        builder.append(lineIndent);
        builder.append("</").append(chunk.nameStr).append(">").append('\n');
        return builder.toString();
    }

    private String makeLineIndent(int depth, int indent) {
        StringBuilder builder = new StringBuilder(depth * indent);
        for (int i=0; i<depth * indent; ++i) {
            builder.append(' ');
        }
        return builder.toString();
    }

    public MfHeader parseHeader(byte[] data) throws IOException {
        mStreamer.use(data);
        return MfHeader.parseFrom(mStreamer);
    }

    public StringChunk parseStringChunk(byte[] chunkData) throws IOException {
        mStreamer.use(chunkData);
        return StringChunk.parseFrom(mStreamer);
    }

    public ResourceIdChunk parseResourceIdChunk(byte[] chunkData) throws IOException {
        mStreamer.use(chunkData);
        return ResourceIdChunk.parseFrom(mStreamer);
    }

    public StartNamespaceChunk parseStartNamespaceChunk(byte[] chunkData) {
        mStreamer.use(chunkData);
        return StartNamespaceChunk.parseFrom(mStreamer, stringChunk);
    }

    public StartTagChunk parseStartTagChunk(byte[] chunkData) {
        mStreamer.use(chunkData);
        return StartTagChunk.parseFrom(mStreamer, stringChunk);
    }

    public EndTagChunk parseEndTagChunk(byte[] chunkData) {
        mStreamer.use(chunkData);
        return EndTagChunk.parseFrom(mStreamer, stringChunk);
    }

    public EndNamespaceChunk parseEndNamespaceChunk(byte[] chunkData) {
        mStreamer.use(chunkData);
        return EndNamespaceChunk.parseFrom(mStreamer, stringChunk);
    }
}
