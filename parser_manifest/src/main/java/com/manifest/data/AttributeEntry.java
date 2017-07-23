package com.manifest.data;

import com.common.PrintUtil;
import com.manifest.stream.MfStreamer;

/**
 *
 * Created by xueqiulxq on 19/07/2017.
 */

public class AttributeEntry {

    public long namespaceUri;
    public long name;
    public long valueString;
    public long type;
    public long data;

    // Assistant
    public String namespaceUriStr;
    public String nameStr;
    public String valueStringStr;
    public String typeStr;
    public String dataStr;

    public static AttributeEntry parseFrom(MfStreamer s, StringChunk stringChunk) {
        AttributeEntry entry = new AttributeEntry();
        entry.namespaceUri = s.readUInt();
        entry.name = s.readUInt();
        entry.valueString = s.readUInt();
        entry.type = s.readUInt() >> 24;
        entry.data = s.readUInt();
        // Fill data
        entry.namespaceUriStr = stringChunk.getString((int) entry.namespaceUri);
        entry.nameStr = stringChunk.getString((int) entry.name);
        entry.valueStringStr = stringChunk.getString((int) entry.valueString);
        entry.typeStr = AttributeType.getAttributeType(entry);
        entry.dataStr = AttributeType.getAttributeData(entry, stringChunk);
        return entry;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        String form3 = "%-16s %-16s %s\n";
        builder.append(String.format(form3, "namespaceUri", PrintUtil.hex4(namespaceUri), namespaceUriStr));
        builder.append(String.format(form3, "name", PrintUtil.hex4(name), nameStr));
        builder.append(String.format(form3, "valueString", PrintUtil.hex4(valueString), valueStringStr));
        builder.append(String.format(form3, "type", PrintUtil.hex4(type), typeStr));
        builder.append(String.format(form3, "data", PrintUtil.hex4(data), dataStr));
        return builder.toString();
    }
}
