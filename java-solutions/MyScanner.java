import java.io.*;
import java.util.NoSuchElementException;

public class MyScanner {
    private CompareMethod cmp;
    private MyBuffer buffer;

    private int startOfNextToken;
    private int lenOfNextToken;

    private int lenOfNextLine;
    
    private int lenOfLineSeparator;

    MyScanner(InputStream stream, CompareMethod cmp) {
        this.buffer = new MyBuffer(stream);
        this.cmp = cmp;
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

            char curChar = buffer.nextChar();
            if (curChar == '\r' || curChar == '\n') {
                if (buffer.hasNextChar() && buffer.nextChar() == '\n') {
                    i++;
                    lenOfLineSeparator = 2;
                } else {
                    lenOfLineSeparator = 1;
                }
                break;
            }
        }
            
        return i;
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
        while(buffer.hasNextChar() && !cmp.isPartOfToken(buffer.nextChar())) {
            i++;
        }
        return startOfNextToken + i;
    }
}


/* :NOTE
WARNING: A command line option has enabled the Security Manager
WARNING: The Security Manager is deprecated and will be removed in a future release
Testing Base
    Running test 01: java Reverse [1 input lines]
    Running test 02: java Reverse [2 input lines]
    Running test 03: java Reverse [3 input lines]
    Running test 04: java Reverse [4 input lines]
Exception in thread "main" java.lang.AssertionError: Line 3:
     expected ``,
       actual `3 2 1`
        at base.Asserts.error(Asserts.java:75)
        at base.Asserts.assertTrue(Asserts.java:41)
        at base.Asserts.assertEquals(Asserts.java:20)
        at base.Runner.lambda$testEquals$0(Runner.java:36)
        at base.TestCounter.lambda$test$0(TestCounter.java:58)
        at base.TestCounter.lambda$testV$2(TestCounter.java:71)
        at base.Log.silentScope(Log.java:40)
        at base.TestCounter.testV(TestCounter.java:70)
        at base.TestCounter.test(TestCounter.java:57)
        at base.Runner.testEquals(Runner.java:30)
        at reverse.ReverseTester$Checker.test(ReverseTester.java:102)
        at reverse.ReverseTester$Checker.test(ReverseTester.java:96)
        at reverse.ReverseTester$Checker.test(ReverseTester.java:149)
        at reverse.ReverseTester.run(ReverseTester.java:67)
        at reverse.ReverseTester.lambda$variant$2(ReverseTester.java:44)
        at base.Selector.lambda$test$2(Selector.java:79)
        at base.Log.lambda$action$0(Log.java:18)
        at base.Log.silentScope(Log.java:40)
        at base.Log.scope(Log.java:31)
        at base.Log.scope(Log.java:24)
        at base.Selector.lambda$test$3(Selector.java:79)
        at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        at base.Selector.test(Selector.java:79)
        at base.Selector.main(Selector.java:51)
        at reverse.FullFastReverseTest.main(FullFastReverseTest.java:15)
ERROR: Tests: failed

 */