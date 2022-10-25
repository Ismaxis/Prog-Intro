import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import myscanner.MyScanner;
import myscanner.PartOfWord;

public class WsppLastL {
    public static void main(final String[] args) {
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
    }

    public static Map<String, WordStatistics> countWordsInFile(String fileName) throws IOException {
        Map<String, WordStatistics> map = new LinkedHashMap<>();
        MyScanner scn = new MyScanner(new FileInputStream(fileName), new PartOfWord());
        int lineNumber = 1, wordNumber = 1;
        while(scn.hasNextToken()) {
            String key = scn.nextToken().toLowerCase();

            if (lineNumber != scn.getLineNumber()) {
                wordNumber = 1;
                lineNumber = scn.getLineNumber();
            }

            WordStatistics curWordStat;
            if (map.containsKey(key)) {
                curWordStat = map.get(key);
            } else {
                curWordStat = new WordStatistics();
            }
            curWordStat.addOccurency(wordNumber++, lineNumber);
            map.put(key, curWordStat);
        }

        try {
            scn.close();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }

        return map;
    }

    public static boolean isPartOfWord(char ch) {
        return Character.isLetter(ch) || Character.DASH_PUNCTUATION == Character.getType(ch) || ch == '\'';
    }

    public static void outputResult(Map<String, WordStatistics> map, String fileName) throws FileNotFoundException, IOException{
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8));
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
