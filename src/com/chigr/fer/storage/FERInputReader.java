package com.chigr.fer.storage;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This is a class for reading FERProvider file
 * Created by Саша on 23.02.2017.
 */
public class FERInputReader {
    private DataInputStream dis;
    private String encoding;

    public FERInputReader(InputStream is, String encoding) {
        this.dis = new DataInputStream(is);
        this.encoding = encoding;
    }

    public int readInt() throws IOException {
        return dis.readInt();
    }

    public String readString() throws IOException {
        int length = readInt();
        if (length > 0) {
            byte[] buf = new byte[length];
            int readCount = dis.read(buf);

        }
        return null;
    }
}
