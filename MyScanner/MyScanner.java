import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class MyScanner {
    private final static int DEFAULT_BUFFER_SIZE = 1024;

    private CompareMethod cmp;
    private InputStreamReader reader;

    private char[] buffer;
    private int amountOfValidData;
    private boolean streamEnded;
    
    private int startOfNextToken;
    private int lenOfNextToken;
    private int lenOfNextLine;

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

        String result = new String(buffer, startOfNextToken, lenOfNextLine);
        startOfNextToken += lenOfNextLine + 1;
        lenOfNextLine = 0;
        return result;
    }

    public boolean hasNextLine() {
        lenOfNextLine = findLenOfNextLine();
        return startOfNextToken < amountOfValidData;
    }

    private int findLenOfNextLine() {
        int i = 0;
        try {    
            while (startOfNextToken + i < amountOfValidData && buffer[startOfNextToken + i] != '\n') {
                i++;
            }

            if (startOfNextToken + i == buffer.length) {
                readInBuffer(startOfNextToken, i);
                if (!streamEnded) {
                    startOfNextToken = 0;
                    i = findLenOfNextLine(); 
                }
            }
        } catch (IOException e) {
            return 0;
        }
        
        return i;
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
        return result;
    }

    public boolean hasNextToken() {
        startOfNextToken = findStartOfNextToken();
        lenOfNextToken = findLenOfNextToken();
        return lenOfNextToken != 0;
    }

    private int findLenOfNextToken() {
        int i = 0;
        try {
            while(startOfNextToken + i < amountOfValidData && cmp.isPartOfToken(buffer[startOfNextToken + i])) {
                i++;
            }

            if (startOfNextToken + i == buffer.length) {
                readInBuffer(startOfNextToken, i);
                if (!streamEnded) {
                    startOfNextToken = 0;
                    i = findLenOfNextToken(); 
                }
            }
        } catch (IOException e) {
            return 0;
        }
        
        return i;
    }

    private int findStartOfNextToken() {
        if (streamEnded) {
            return startOfNextToken;
        }

        int i = startOfNextToken;
        while(i != buffer.length && !cmp.isPartOfToken(buffer[i])) {
            i++;
        }
        
        try {
            if (i == buffer.length) {
                readInBuffer();
                if (!streamEnded) {
                    startOfNextToken = 0;
                    i = findStartOfNextToken();
                }
            }
        } catch (IOException e) {
            System.err.println("find start failure");
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
        if(read < 0) {
            streamEnded = true;
            reader.close();
        } else {
            amountOfValidData = read + lenOfReminder;
        }
    }

    // public static void main(String[] args) {    
    //     CompareMethod cmp;
    //     // cmp = new WhiteSpace();
    //     cmp = new PartOfWord();     
    //     // MyScanner scn = new MyScanner(System.in, cmp);
    //     MyScanner scn;
    //     scn = new MyScanner("1\n\n3 2", cmp);
    //     while (scn.hasNextLine()) {
    //         System.out.println(scn.nextLine());
    //     }

    //     // try {
    //     // 	scn = new MyScanner(new FileInputStream("MyScanner/file.txt"), cmp);
    //     //     while (scn.hasNextLine()) {
    //     //         System.out.println(scn.nextLine());
    //     //     }
    //     // } catch (FileNotFoundException e) {
    //     // 	e.printStackTrace(System.err);
    //     // }
    // }
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
