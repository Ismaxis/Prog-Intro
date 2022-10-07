import java.io.*;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class MyScanner {
    private final static int DEFAULT_BUFFER_SIZE = 1024;

    private CompareMethod cmp;
    private InputStreamReader reader;

    private char[] buffer;
    private boolean streamEnded;
    
    private int startOfNextToken;
    private int lenOfNextToken;
    private int startOfNextLine;
    private int lenOfNextLine;
    private int lenOfLineSeparator;

    MyScanner(InputStream stream, CompareMethod cmp) {
        this.cmp = cmp;
        try {
            this.reader = new InputStreamReader(stream, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace(System.err);
        }
        buffer = new char[DEFAULT_BUFFER_SIZE];
        try {
            readInBuffer();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    MyScanner(String str, CompareMethod cmp) {
        this(new ByteArrayInputStream(str.getBytes()), cmp);
    }

    public CompareMethod getCompareMethodObj() {
        return cmp;
    }

    public String nextLine() {
        if (!hasNextLine()) {
            throw new NoSuchElementException();
        }
        
        lenOfNextLine = findLenOfNextLine();

        String result = new String(buffer, startOfNextLine, lenOfNextLine - lenOfLineSeparator);
        startOfNextLine += lenOfNextLine;
        lenOfNextLine = 0;
        startOfNextToken = startOfNextLine;
        lenOfNextToken = 0;
        return result;
    }

    public boolean hasNextLine() {
        if (buffer.length - startOfNextLine <= 0) { 
            if (streamEnded) {
                return false;
            } else {
                try {
                    readInBuffer();
                } catch (IOException e) {
                    System.err.println("has next line read failure");
                    return false;
                }
                startOfNextLine = 0;
                return buffer.length > 0;
            }
            
        } else {
            return true;
        } 
    }

    private int findLenOfNextLine() {
        lenOfLineSeparator = 0;
        int i = 0;
        try {    
            while (true) {
                if (startOfNextLine + i + 1 >= buffer.length) {
                    if (streamEnded) {
                        lenOfLineSeparator = parseLineSeparator(buffer[startOfNextLine + i], ' ');
                        i = buffer.length;
                        break;
                    }
                    readInBuffer(startOfNextLine, buffer.length - startOfNextLine);
                    startOfNextLine = 0;
                } else {
                    int lineSeparatorLen = parseLineSeparator(buffer[startOfNextLine + i], buffer[startOfNextLine + i + 1]);
                    if (lineSeparatorLen > 0) {
                        lenOfLineSeparator = lineSeparatorLen;
                        i += lineSeparatorLen; 
                        break;
                    }
                    i++;
                }
            }       
        } catch (IOException e) {
            System.err.println("Find len of line failure");
        }
        
        return i;
    }

    private int parseLineSeparator(char ch1, char ch2) {
        if (ch1 == '\r') {
            if (ch2 == '\n') {
                return 2;
            } else {
                return 1;
            }
        } else if (ch1 == '\n') {
                return 1;
        } else {
            return 0;
        }
    }

    public int nextInt() {
        int value;
        String token = nextToken();
        if (Character.toLowerCase(token.charAt(token.length() - 1)) == 'o') {
            value = (int)Long.parseLong(token.substring(0, token.length() - 1), 8);
        } else {
            value = (int)Long.parseLong(token);
        }
        return value;
    }

    public boolean hasNextInt() {
        return hasNextToken() && canParseNextTokenToInt();
    }

    private boolean canParseNextTokenToInt() { 
        boolean isOctal = false;
        int offset = 0;

        if (!Character.isDigit(buffer[startOfNextToken])) {
            if (buffer[startOfNextToken] == '-') {
                offset = 1;
            } else if(buffer[startOfNextToken] == '+') {
                offset = 1;
            } else {
                return false;
            }
        }
        if (!Character.isDigit(buffer[startOfNextToken + lenOfNextToken - 1])) {
            if (Character.toLowerCase(buffer[startOfNextToken + lenOfNextToken - 1]) == 'o') {
                isOctal = true;
            }   else {
                return false;
            }
        }

        for (int i = offset; i < lenOfNextToken - (isOctal ? 1 : 0); i++) {
            if (!Character.isDigit(buffer[startOfNextToken + i])) {
                return false;
            }
        }

        return true;
    }

    public String nextToken() {
        if (lenOfNextToken == 0) {
            startOfNextToken = findStartOfNextToken();
            lenOfNextToken = findLenOfNextToken();
    
            if (lenOfNextToken == 0) {
                throw new NoSuchElementException();
            }
        }

        String result = new String(buffer, startOfNextToken, lenOfNextToken);
        startOfNextToken += lenOfNextToken;
        lenOfNextToken = 0;
        startOfNextLine = startOfNextToken;
        lenOfNextLine = 0;  
        return result;
    }

    public boolean hasNextToken() {
        startOfNextToken = findStartOfNextToken();
        lenOfNextToken = findLenOfNextToken();
        return lenOfNextToken != 0;
    }

    private int findLenOfNextToken() {
        if (streamEnded) {
            return buffer.length - startOfNextToken;
        }
        
        int i = 0;
        try {
            while(cmp.isPartOfToken(buffer[startOfNextToken + i])) {
                if (startOfNextToken + i + 1 >= buffer.length) {
                    if (streamEnded) {
                        i = buffer.length;
                        break;
                    }
                    readInBuffer(startOfNextToken, i + 1);
                    startOfNextToken = 0;
                } else {
                    i++;
                }
            }
        } catch (IOException e) {
            System.err.println("Find len of token failure");
        }
        
        return i;
    }

    private int findStartOfNextToken() {
        if (streamEnded) {
            return startOfNextToken;
        }

        int i = 0;
        try {
            while(!cmp.isPartOfToken(buffer[startOfNextToken + i])) {
                if (startOfNextToken + i + 1 >= buffer.length) {
                    if (streamEnded) {
                        i = buffer.length;
                        break;
                    }
                    readInBuffer(startOfNextToken, i + 1);
                    i = 0;
                    startOfNextToken = 0;
                } else {
                    i++;
                }
            }
        } catch (IOException e) {
            System.err.println("Find start of token failure");
        }
        return startOfNextToken + i;
    }
    
    private void readInBuffer() throws IOException {
        readInBuffer(buffer.length,0);
    }

    private void readInBuffer(int startOfReminder, int lenOfReminder) throws IOException {
        if (startOfReminder == 0) {
            buffer = Arrays.copyOf(buffer, buffer.length * 2);
        } else {
            buffer = Arrays.copyOfRange(buffer, startOfReminder, buffer.length + DEFAULT_BUFFER_SIZE);
        }
        int read = reader.read(buffer, lenOfReminder, buffer.length - lenOfReminder);
        int amountOfValidData = 0;
        if(read < 0) {
            streamEnded = true;
            reader.close();
            amountOfValidData = lenOfReminder;
        } else {
            amountOfValidData = read + lenOfReminder;
        }
        if (amountOfValidData != buffer.length) {
            buffer = Arrays.copyOf(buffer, amountOfValidData);
        }
    }
}
