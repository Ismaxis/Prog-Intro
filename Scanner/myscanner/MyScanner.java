package myscanner;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

public class MyScanner {
    private CompareMethod cmp;
    private MyBuffer buffer;

    private int startOfNextToken;
    private int lenOfNextToken;

    private int lenOfNextLine;
    
    private int lenOfLineSeparator;

    private int lineSepsMet;

    public MyScanner(InputStream stream, CompareMethod cmp) {
        this.buffer = new MyBuffer(stream);
        this.cmp = cmp;
    }

    public MyScanner(String str, CompareMethod cmp) {
        this(new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8)), cmp);
    }

    public CompareMethod getCompareMethodObj() {
        return cmp;
    }

    public String nextLine() {
        if (!hasNextLine()) {
            throw new NoSuchElementException();
        }
        lenOfNextLine = findLenOfNextLine();

        String result = new String(buffer.getChars(lenOfNextLine), 0, lenOfNextLine - lenOfLineSeparator);
        lenOfNextLine = 0;
        startOfNextToken = 0;
        lenOfNextToken = 0;
        return result;
    }

    public boolean hasNextLine() {
        return buffer.hasNextChar();
    }

    private int findLenOfNextLine() {
        lenOfLineSeparator = 0;
        int i = 0;  

        while (buffer.hasNextChar()) {
            i++;
            lenOfLineSeparator = analyzeForLineSeparator(buffer.nextChar());
            if (lenOfLineSeparator > 0) {
                i += lenOfLineSeparator - 1;
                lineSepsMet++;
                break;
            }
        }
            
        return i;
    }

    private int analyzeForLineSeparator(char curChar) {
        int length = 0;
        if (curChar == '\r') {
            if (buffer.hasNextChar() && buffer.nextChar() == '\n') {
                length = 2;
            } else {
                length = 1;
            }
        } else if (curChar == '\n') {
            length = 1;
        }

        return length;
    }

    public int nextInt() {
        String token = nextToken();
        int value;
        if (Character.toLowerCase(token.charAt(token.length() - 1)) == 'o') {
            value = Integer.parseUnsignedInt(token.substring(0, token.length() - 1), 8);
        } else {
            value = Integer.parseInt(token);
        }
        return value;
    }

    public boolean hasNextInt() {
        return hasNextToken() && canParseNextTokenToInt();
    }

    private boolean canParseNextTokenToInt() {
        buffer.resetLookIndex(startOfNextToken);
        for (int i = 0; i < lenOfNextToken; i++) {
            char curChar = buffer.nextChar();

            if (!Character.isDigit(curChar)) {
                if (i == 0) {
                    if (curChar == '-' || curChar == '+') {
                        continue;
                    }
                } else if (i == lenOfNextToken - 1) {
                    if (Character.toLowerCase(curChar) == 'o') {
                        continue;
                    }
                } 
                return false;
            }
        }

        return true;
    }

    public String nextToken() {
        if (!hasNextToken()) {
            throw new NoSuchElementException();
        }

        String result = new String(buffer.getChars(startOfNextToken, lenOfNextToken));
        startOfNextToken = 0;
        lenOfNextToken = 0;
        lenOfNextLine = 0;
        return result;
    }

    public boolean hasNextToken() {
        boolean result;
        if (lenOfNextToken != 0) {
            result = true;
        } else {
            startOfNextToken = findStartOfNextToken();
            lenOfNextToken = findLenOfNextToken();
            result = lenOfNextToken != 0;
        }

        return result;
    }

    private int findLenOfNextToken() {
        buffer.resetLookIndex(startOfNextToken);
        int i = 0;
        while(buffer.hasNextChar() && cmp.isPartOfToken(buffer.nextChar())) {
            i++;
        }
        return i;
    }

    private int findStartOfNextToken() {
        int i = 0;
        while (buffer.hasNextChar()) {
            char curChar = buffer.nextChar();
            if (!cmp.isPartOfToken(curChar)) {     
                i++;
                int length = analyzeForLineSeparator(curChar);
                if (length > 0) {
                    i += length - 1;
                    lineSepsMet++;
                }
            } else {
                break;
            }
        }

        return startOfNextToken + i;
    }
    
    public int getLineNumber() {
        return lineSepsMet + 1;
    }
}