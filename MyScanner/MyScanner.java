import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.NoSuchElementException;

public class MyScanner {
    private static int BUFFER_SIZE = 1024;

    private CompareMethod cmp;
    private InputStreamReader reader;

    private String buffer;
    private boolean streamEnded;

    private String nextToken;
    private int offset;
    private int endOfLine;
    
    MyScanner(InputStream stream, CompareMethod cmp) {
        this.cmp = cmp;
        try {
            this.reader = new InputStreamReader(stream, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace(System.err);
        }
        streamEnded = false;
        nextToken = "";
        offset = 0;
        endOfLine = 0;

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
        if (endOfLine <= offset) {
            endOfLine = offset + findEndOfLine();
        }
        String result = buffer.substring(offset, endOfLine).replace(System.lineSeparator(), ""); // removing \n
        offset = endOfLine;
        return result;
    }

    public boolean hasNextLine() {
        endOfLine = findEndOfLine() + offset;
        return endOfLine > offset;
    }

    private int findEndOfLine() {
        int i = 0;
        try {
            offset = findStartOfLine();
            
            while(offset + i != buffer.length() && buffer.charAt(offset + i) != '\n') {
                i++;
            }

            if (offset + i == buffer.length()) {
                readInBuffer(buffer.substring(offset));
                offset = 0;
                endOfLine = 0;
                if (!streamEnded) {
                    i = findEndOfLine(); 
                }
            } else {
                i++;
            }
        } catch (IOException e) {
            return 0;
        }
        
        return i;
    }

    private int findStartOfLine() {
        if (streamEnded) {
            return offset;
        }

        int i = offset;
        while(i != buffer.length() && buffer.charAt(i) == '\n') {
            i++;
        }
        
        return i;
    }

    public String nextToken() {
        if (nextToken.isEmpty()) {
            if (!hasNextToken()) {
                throw new NoSuchElementException();
            }
        }
        String result = nextToken;
        nextToken = "";
        return result;
    }

    public boolean hasNextToken() {
        int endOfToken = findEnd();
        if(endOfToken == 0) {
            return false;
        } else {
            nextToken = buffer.substring(offset, offset + endOfToken);
            offset += endOfToken;
            return !nextToken.isEmpty();
        }
    }

    private int findEnd() {
        int i = 0;
        try {
            offset = findStart();
            while(offset + i != buffer.length() && cmp.isPartOfToken(buffer.charAt(offset + i))) {
                i++;
            }

            if (offset + i == buffer.length()) {
                readInBuffer(buffer.substring(offset));
                offset = 0;
                if (!streamEnded) {
                    i = findEnd(); 
                }
            }
        } catch (IOException e) {
            return 0;
        }
        
        return i;
    }

    private int findStart() {
        if (streamEnded) {
            return offset;
        }

        int i = offset;
        while(i != buffer.length() && !cmp.isPartOfToken(buffer.charAt(i))) {
            i++;
        }
        
        return i;
    }

    private void readInBuffer() throws IOException {
        readInBuffer("");
    }

    private void readInBuffer(String reminder) throws IOException {
        char[] charBuf = new char[BUFFER_SIZE];
        try {
            int read = reader.read(charBuf);
            if (read >= 0) {
                buffer = reminder + new String(charBuf, 0, read);
            } else {
                buffer = reminder;
                streamEnded = true;
                reader.close();
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    // public static void main(String[] args) {    
    //     // CompareMethod cmp;
    //     // cmp = new WhiteSpace();
    //     // cmp = new PartOfWord();     
    //     // MyScanner scn = new MyScanner(System.in, cmp);
    //     // MyScanner scn;
    //     // try {
    //     // 	scn = new MyScanner(new FileInputStream("MyScanner/file.txt"), cmp);
    //     //     while (scn.hasNextLine()) {
    //     //         System.out.println(scn.nextLine());
    //     //     }
    //     // } catch (FileNotFoundException e) {
    //     // 	e.printStackTrace(System.err);
    //     // }
    //     Scanner scn = new Scanner("123 \n\n\n 123 123 ");

    //     while (scn.hasNextLine()) {
    //         System.out.println(scn.nextLine());
    //     }
        
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
