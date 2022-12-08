package myscanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MyBuffer {
    private final static int DEFAULT_BUFFER_SIZE = 1024;

    private InputStreamReader reader;

    private char[] buffer;
    private boolean streamEnded;

    private int lookIndex;
    private int readIndex;

    public MyBuffer(InputStream stream) {
        reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
        readInBuffer();
    }

    public String getSubString(int len) {
        return getSubString(0, len);
    }

    public String getSubString(int start, int len) {
        if (start < 0 || start + len > buffer.length) {
            throw new ArrayIndexOutOfBoundsException();
        }

        int offset = readIndex + start;
        readIndex += start + len;
        lookIndex = readIndex;
        return new String(buffer, offset, len);
    }

    public char nextChar() {
        ensureHasNextChar();
        return buffer[lookIndex++];
    }

    public char peekChar() {
        ensureHasNextChar();
        return buffer[lookIndex];
    }

    private void ensureHasNextChar() {
        if (lookIndex >= buffer.length) {
            readInBuffer();
        }
    }

    public boolean hasNextChar() {
        if (lookIndex == buffer.length && !streamEnded) {
            readInBuffer();
        }
        return !(lookIndex == buffer.length && streamEnded);
    }

    public void resetLookIndex(int offset) {
        lookIndex = readIndex + offset;
    }

    private void readInBuffer() {
        try {
            int lenOfReminder;

            if (buffer == null) {
                buffer = new char[DEFAULT_BUFFER_SIZE];
                lenOfReminder = 0;
            } else {
                lenOfReminder = buffer.length - readIndex;
                if (readIndex == 0) {
                    buffer = Arrays.copyOf(buffer, buffer.length * 2);
                } else {
                    buffer = Arrays.copyOfRange(buffer, readIndex, buffer.length + DEFAULT_BUFFER_SIZE);
                }
            }

            int read = reader.read(buffer, lenOfReminder, buffer.length - lenOfReminder);
            if (read < buffer.length - lenOfReminder) {
                streamEnded = true;
                reader.close();
            }

            int amountOfValidData = (read < 0 ? 0 : read) + lenOfReminder;
            if (amountOfValidData != buffer.length) {
                buffer = Arrays.copyOf(buffer, amountOfValidData);
            }

            readIndex = 0;
            lookIndex = Math.max(0, lenOfReminder);
        } catch (Exception e) {
            System.err.println("Reading error in MyBuffer: " + e.getMessage());
            streamEnded = true;
        }
    }

    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
