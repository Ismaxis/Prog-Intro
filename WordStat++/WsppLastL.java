import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import myscanner.MyBuffer;

public class WsppLastL {
    static final int BUFFER_SIZE = 1024;
    static int lineNumber = 1, wordNumber = 1;
    public static void main(String[] args) {
        try {
            if (args.length >= 2) {
                Map<String, WordStatistics> map = countWordsInFile(args[0]);
                outputResult(map, args[1]);
            } else {
                System.err.println("Not enougth command line args (2 expected)");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {   
            System.out.println("Input/Output error: " + e.getMessage());
        }
        
        // try {
        //     Map<String, WordStatistics> map = countWordsInFile("WordStat++\\in.txt");
        //     outputResult(map, "WordStat++\\out.txt");
        // } catch (FileNotFoundException e) {
        //     System.out.println("File not found: " + e.getMessage());
        // } catch (IOException e) {   
        //     System.out.println("Input/Output error: " + e.getMessage());
        // }
    }

    public static Map<String, WordStatistics> countWordsInFile(String fileName) throws FileNotFoundException, IOException {
        Map<String, WordStatistics> map = new LinkedHashMap<>();
        MyBuffer buffer = new MyBuffer(new FileInputStream(fileName));
        int i = 0;
        char curChar = buffer.nextChar();
        while (buffer.hasNextChar()) { 
            while (buffer.hasNextChar() && !isPartOfWord(curChar)) { 
                if (curChar == '\r') {
                    if (buffer.hasNextChar() && buffer.nextChar() != '\n') {
                        buffer.resetLookIndex(i);
                    }
                    lineNumber++;
                    wordNumber = 1;
                    i++;
                } else if (curChar == '\n') {
                    lineNumber++;
                    wordNumber = 1;
                }
                i++;
                curChar = buffer.nextChar();
            }

            int start = i;
            while (isPartOfWord(curChar)) {
                i++;
                if (!buffer.hasNextChar()) {
                    break;
                }
                curChar = buffer.nextChar();
            }

            if (start < i || isPartOfWord(curChar)) {
                String key = new String(buffer.getChars(start, i - start)).toLowerCase();
                if (buffer.hasNextChar()) {
                    curChar = buffer.nextChar();
                }
                i = 0;
                WordStatistics curWordStat;
                
                if (map.containsKey(key)) {
                    curWordStat = map.get(key);
                } else {
                    curWordStat = new WordStatistics();
                }
                
                curWordStat.addOccurency(wordNumber++, lineNumber);
                map.put(key, curWordStat);
            }
        }

        return map;
    }

    // public static String parseWords(Map<String, WordStatistics> map, String source) {
    //     for (int i = 0; i < source.length(); i++) {
    //         int start = i;
            
    //         while (isPartOfWord(source.charAt(i))) {
    //             i++;
    //             if (i == source.length()) {
    //                 return source.substring(start, i); // reminder
    //             }
    //         }
    //         if (source.charAt(i) == '\n') {
    //             lineNumber++;
    //             wordNumber = 1;
    //         }
    //         if (start < i) {
    //             String key = source.substring(start, i).toLowerCase();
    //             WordStatistics curWordStat;
                
    //             if (map.containsKey(key)) {
    //                 curWordStat = map.get(key);
    //             } else {
    //                 curWordStat = new WordStatistics();
    //             }
                
    //             curWordStat.addOccurency(wordNumber++, lineNumber);
    //             map.put(key, curWordStat);
    //         }
    //     }
    //     return "";
    // }

    public static boolean isPartOfWord(char ch) {
        return Character.isLetter(ch) || Character.DASH_PUNCTUATION == Character.getType(ch) || ch == '\'';
    }

    public static void outputResult(Map<String, WordStatistics> map, String fileName) throws FileNotFoundException, IOException{
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));
        try {
            for (Entry<String, WordStatistics> entry : map.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue());
                writer.newLine();    
            }   
        } finally {
            writer.close();
        }
    }
}
