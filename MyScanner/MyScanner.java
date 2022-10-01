import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.NoSuchElementException;

public class MyScanner {
    private static int BUFFER_SIZE = 256;
    private CompareMethod cmp;
    private InputStreamReader reader;
    private String buffer;
    private String nextToken;
    private int offset;
    
    MyScanner(InputStream stream, CompareMethod cmp){
        this.cmp = cmp;
        try {
            this.reader = new InputStreamReader(stream, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace(System.err);
        }
    }
    
    public String nextToken() {
        if (nextToken.isEmpty()) {
            if (!findNextToken()) {
                throw new NoSuchElementException();
            }
        }
        String result = nextToken;
        nextToken = "";
        return result;
    }

    public boolean hasNextToken() {
        return findNextToken();
    }

    private boolean findNextToken() {
        try {
            findStartOfNextToken();
        } catch (IOException e) {
            return false;
        }

        int i = 0;
        while(offset + i != buffer.length() && cmp.isPartOfToken(buffer.charAt(offset + i))) {
            i++;
        }
        nextToken = buffer.substring(offset, offset + i);
        offset = offset + i;

        return !nextToken.isEmpty();
    }

    private void findStartOfNextToken() throws IOException {
        if (buffer == null) {
            readInBuffer();
        }
        int i = offset;
        while(i != buffer.length() && !cmp.isPartOfToken(buffer.charAt(i))) {
            i++;
        }
        
        if(i == buffer.length()) {
            readInBuffer();
            offset = 0;
            findStartOfNextToken();
        } else {
            offset = i;
        }
    }

    private void readInBuffer() throws IOException {
        readInBuffer("");
    }

    private void readInBuffer(String reminder) throws IOException {
        char[] charBuf = new char[BUFFER_SIZE];
        try {
            int read = reader.read(charBuf);
            buffer = reminder + new String(charBuf, 0, read);
        } finally {
            reader.close();
        }
    }




    public static void main(String[] args) {    
        CompareMethod cmp;
        
        if(args[0].equals("w")) {
            cmp = new WhiteSpace();
        } else {
            cmp = new PartOfWord();
        }

        String str = args[1];//"123 312 2456";
        
        MyScanner scn = new MyScanner(new ByteArrayInputStream(str.getBytes()), cmp);
        while (scn.hasNextToken()) {
            System.out.println(scn.nextToken());
        }
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
