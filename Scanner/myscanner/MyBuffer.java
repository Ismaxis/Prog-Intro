package myscanner;
import java.io.*;
import java.util.Arrays;

public class MyBuffer {
    private final static int DEFAULT_BUFFER_SIZE = 1024;

    private InputStreamReader reader;

    private char[] buffer;
    private boolean streamEnded;

    private int lookIndex;
    private int readIndex;


    MyBuffer(InputStream stream) {
        try {
            reader = new InputStreamReader(stream, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace(System.err);
        }

        try {
            readInBuffer();
        } catch (IOException e) {
            System.err.println("Read in buffer error: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    public char[] getChars(int len) {
        return getChars(0, len);
    }

    public char[] getChars(int start, int len) {
        if (start < 0 || start + len > buffer.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int from = readIndex + start;
        int to = from + len;
        readIndex += start + len;
        lookIndex = readIndex;
        return Arrays.copyOfRange(buffer, from, to);
    }
    
    public char nextChar() {
        if (lookIndex >= buffer.length) {
            try {
                readInBuffer();
            } catch (IOException e) {
                System.err.println("Reading error: " + e.getMessage());
                e.printStackTrace(System.err);
            }
        }
        return buffer[lookIndex++];
    }

    public boolean hasNextChar() {
        if (lookIndex == buffer.length && !streamEnded) {
            try {
                readInBuffer();
            } catch (IOException e) {
                System.err.println("Reading error: " + e.getMessage());
                e.printStackTrace(System.err);
            }
        }
        return !(lookIndex == buffer.length && streamEnded);
    }

    public void resetLookIndex(int offsetFromRead) {
        lookIndex = readIndex + offsetFromRead;
    }

    private void readInBuffer() throws IOException {
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

        if(read < buffer.length - lenOfReminder) {
            streamEnded = true;
            reader.close();
        } 
        
        int amountOfValidData = (read < 0 ? 0 : read) + lenOfReminder;
        if (amountOfValidData != buffer.length) {
            buffer = Arrays.copyOf(buffer, amountOfValidData);
        }

        readIndex = 0;
        lookIndex = Math.max(0, lenOfReminder);
    }
}
