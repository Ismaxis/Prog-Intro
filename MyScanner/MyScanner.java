import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MyScanner {
    private final static int DEFAULT_BUFFER_SIZE = 1024;

    private CompareMethod cmp;
    private InputStreamReader reader;

    private char[] buffer;
    private int amountOfData;
    private boolean streamEnded;
    
    private int startOfNextToken;
    private int lenOfNextToken;
    //private int offset;
    private int startOfNextLine;
    private int endOfNextLine;

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

    // public String nextLine() {
    //     if (endOfNextLine <= offset) {
    //         endOfNextLine = offset + findEndOfLine();
    //     }
    //     String result = buffer.substring(offset, endOfNextLine).replace(System.lineSeparator(), ""); // removing \n
    //     offset = endOfNextLine;
    //     return result;
    // }

    // public boolean hasNextLine() {
    //     endOfNextLine = findEndOfLine() + offset;
    //     return endOfNextLine > offset;
    // }

    // private int findEndOfLine() {
    //     int i = 0;
    //     try {
    //         offset = findStartOfLine();
            
    //         while(offset + i != buffer.length() && buffer.charAt(offset + i) != '\n') {
    //             i++;
    //         }

    //         if (offset + i == buffer.length()) {
    //             readInBuffer(buffer.substring(offset));
    //             offset = 0;
    //             endOfNextLine = 0;
    //             if (!streamEnded) {
    //                 i = findEndOfLine(); 
    //             }
    //         } else {
    //             i++;
    //         }
    //     } catch (IOException e) {
    //         return 0;
    //     }
        
    //     return i;
    // }

    // private int findStartOfLine() {
    //     if (streamEnded) {
    //         return offset;
    //     }

    //     int i = offset;
    //     while(i != buffer.length && buffer[i] == '\n') {
    //         i++;
    //     }
        
    //     return i;
    // }

    public String nextToken() {
        if (lenOfNextToken == 0) {
            startOfNextToken = findStart();
            lenOfNextToken = findLen();
    
            if (lenOfNextToken == 0) {
                throw new NoSuchElementException();
            }
        }

        String result = new String(buffer, startOfNextToken, lenOfNextToken);
        startOfNextToken += lenOfNextToken;
        lenOfNextToken = 0;
        return result;
    }

    public boolean hasNextToken() {
        startOfNextToken = findStart();
        lenOfNextToken = findLen();
        return lenOfNextToken != 0;
    }

    private int findLen() {
        int i = 0;
        try {
            while(startOfNextToken + i != amountOfData && cmp.isPartOfToken(buffer[startOfNextToken + i])) {
                i++;
            }

            if (startOfNextToken + i == buffer.length) {
                readInBuffer(startOfNextToken, i);
                startOfNextToken = 0;
                if (!streamEnded) {
                    i = findLen(); 
                }
            }
        } catch (IOException e) {
            return 0;
        }
        
        return i;
    }

    private int findStart() {
        if (streamEnded) {
            return startOfNextToken;
        }

        int i = startOfNextToken;
        while(i != buffer.length && !cmp.isPartOfToken(buffer[i])) {
            i++;
        }
        
        return i;
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
        amountOfData = read + lenOfReminder;
        if(read < 0) {
            streamEnded = true;
            reader.close();
        }
    }


    public static void main(String[] args) {    
        CompareMethod cmp;
        cmp = new WhiteSpace();
        // cmp = new PartOfWord();     
        // MyScanner scn = new MyScanner(System.in, cmp);
        MyScanner scn;
        scn = new MyScanner("asd2 awdawd 123 sadwa d \n\n\n\n ", cmp);
        while (scn.hasNextToken()) {
            System.out.println(scn.nextToken());
        }

        // try {
        // 	scn = new MyScanner(new FileInputStream("MyScanner/file.txt"), cmp);
        //     while (scn.hasNextLine()) {
        //         System.out.println(scn.nextLine());
        //     }
        // } catch (FileNotFoundException e) {
        // 	e.printStackTrace(System.err);
        // }
    }
}


interface CompareMethod{
    public boolean isPartOfToken(char ch);
}

class WhiteSpace implements CompareMethod {
    @Override
    public boolean isPartOfToken(char ch) {
        return !Character.isWhitespace(ch);
    }
}

class PartOfWord implements CompareMethod {
    @Override
    public boolean isPartOfToken(char ch) {
        return Character.isLetter(ch) || Character.DASH_PUNCTUATION == Character.getType(ch) || ch == '\'';
    }
}
