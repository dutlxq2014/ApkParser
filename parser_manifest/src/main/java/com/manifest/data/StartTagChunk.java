package com.manifest.data;

import com.common.PrintUtil;
import com.manifest.stream.MfStreamer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by xueqiulxq on 17/07/2017.
 */

public class StartTagChunk extends TagChunk {

    public long flags;          // Flags indicating start tag or end tag.
    public long attributeCount; // Count of attributes in tag
    public long classAttribute;
    public List<AttributeEntry> attributes;

    public static StartTagChunk parseFrom(MfStreamer s, StringChunk stringChunk) {
        StartTagChunk chunk = new StartTagChunk();
        chunk.chunkType = s.readUInt();
        chunk.chunkSize = s.readUInt();
        chunk.lineNumber = s.readUInt();
        chunk.unknown = s.readUInt();
        chunk.nameSpaceUri = s.readUInt();
        chunk.name = s.readUInt();
        chunk.flags = s.readUInt();
        chunk.attributeCount = s.readUInt();
        chunk.classAttribute = s.readUInt();

        chunk.attributes = new ArrayList<AttributeEntry>((int) chunk.attributeCount);
        for (int i=0; i<chunk.attributeCount; ++i) {
            chunk.attributes.add(AttributeEntry.parseFrom(s, stringChunk));
        }

        // Fill data
        chunk.nameSpaceUriStr = stringChunk.getString((int) chunk.nameSpaceUri);
        chunk.nameStr = stringChunk.getString((int) chunk.name);
        return chunk;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(512);
        String form2 = "%-16s %s\n";
        String form3 = "%-16s %-16s %s\n";

        builder.append(String.format(form2, "chunkType", PrintUtil.hex4(chunkType)));
        builder.append(String.format(form2, "chunkSize", PrintUtil.hex4(chunkSize)));
        builder.append(String.format(form2, "lineNumber", PrintUtil.hex4(lineNumber)));
        builder.append(String.format(form2, "unknown", PrintUtil.hex4(unknown)));
        builder.append(String.format(form3, "nameSpaceUri", PrintUtil.hex4(nameSpaceUri), nameSpaceUriStr));
        builder.append(String.format(form3, "name", PrintUtil.hex4(name), nameStr));
        builder.append(String.format(form2, "flags", PrintUtil.hex4(flags)));
        builder.append(String.format(form2, "attributeCount", PrintUtil.hex4(attributeCount)));
        builder.append(String.format(form2, "classAttribute", PrintUtil.hex4(classAttribute)));
        for (int i=0; i<attributeCount; ++i) {
            builder.append(" <AttributeEntry.").append(i).append(" />").append('\n');
            builder.append(attributes.get(i));
        }
        return builder.toString();
    }
}
