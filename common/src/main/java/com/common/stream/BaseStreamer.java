package com.common.stream;

import com.common.LogUtil;

/**
 *
 * Created by xueqiulxq on 16/07/2017.
 */

public class BaseStreamer {

    public enum Endian {
        Little, Big
    }

    private byte[] mData = new byte[8];
    private int cursor = 0;

    public BaseStreamer() {
    }

    public void use(byte[] data) {
        mData = data;
        cursor = 0;
    }

    public int length() {
        return mData != null ? mData.length : 0;
    }

    /**
     * If len > 0 read len bytes else if len < 0 read to the end.
     * @param len length of bytes to read
     * @return byte array
     */
    public byte[] read(int len) {
        if (cursor >= mData.length) {
            LogUtil.e("Stream has finished!!");
            return null;
        }
        if (cursor + len > mData.length) {
            LogUtil.e(String.format("Cannot read %d bytes with only %d remains. Return %bytes!",
                    len, mData.length - cursor, mData.length - cursor));
            len = mData.length - cursor;
        } else if (len < 0) {
            len = mData.length - cursor;
        }
        byte[] ret = new byte[len];
        System.arraycopy(mData, cursor, ret, 0, len);
        cursor += len;
        return ret;
    }

    protected long readUnsignedInt(Endian endian) {
        byte[] buf = read(4);
        long ret = 0;
        if (endian == Endian.Little) {
            for (int i=3; i>=0; --i) {
                ret <<= 8;
                ret |= (buf[i] & 0xff);
            }
        } else {
            for (int i=0; i<=4; ++i) {
                ret <<= 8;
                ret |= (buf[i] & 0xff);
            }
        }
        return ret;
    }

    protected int readSignedInt(Endian endian) {
        byte[] buf = read(4);
        int ret = 0;
        if (endian == Endian.Little) {
            for (int i=3; i>=0; --i) {
                ret <<= 8;
                ret |= (buf[i] & 0xff);
            }
        } else {
            for (int i=0; i<=4; ++i) {
                ret <<= 8;
                ret |= (buf[i] & 0xff);
            }
        }
        return ret;
    }

    protected int readUnsignedShort(Endian endian) {
        byte[] buf = read(2);
        if (endian == Endian.Little) {
            return (buf[1] & 0xff) << 8 | (buf[0] & 0xff);
        } else {
            return (buf[0] & 0xff) << 8 | (buf[1] & 0xff);
        }
    }

    protected int readSignedShort(Endian endian) {
        byte[] buf = read(2);
        if (endian == Endian.Little) {
            return (buf[1] & 0xff) << 8 | (buf[0] & 0xff);
        } else {
            return (buf[0] & 0xff) << 8 | (buf[1] & 0xff);
        }
    }

    protected char readChar8(Endian endian) {
        byte[] buf = read(1);
        return (char) buf[0];
    }

    protected char readChar16(Endian endian) {
        byte[] buf = read(2);
        if (endian == Endian.Little) {
            return (char) ((buf[1] & 0xff) << 8 | (buf[0] & 0xff));
        } else {
            return (char) ((buf[0] & 0xff) << 8 | (buf[1] & 0xff));
        }
    }
}
