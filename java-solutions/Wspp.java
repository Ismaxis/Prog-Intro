import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import myscanner.MyScanner;
import myscanner.PartOfWord;

public class Wspp {
    public static void main(String[] args) {
        try {
            if (args.length >= 2) {
                outputResult(countWordsInFile(args[0]), args[1]);
            } else {
                System.err.println("Not enougth command line args (2 expected)");
            }
        } catch (final FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (final IOException e) {
            System.out.println("Output error: " + e.getMessage());
        }
    }

    public static Map<String, WordStatistics> countWordsInFile(String fileName) throws FileNotFoundException {
        Map<String, WordStatistics> map = new LinkedHashMap<>();
        MyScanner scn = new MyScanner(new FileInputStream(fileName), new PartOfWord());
        try {
            int wordNumber = 1;
            while (scn.hasNextToken()) {
                String key = scn.nextToken().toLowerCase();
                if (wordNumber == 24) {
                    System.err.println();
                }

                WordStatistics curWordStat;
                curWordStat = map.get(key);
                if (curWordStat == null) {
                    curWordStat = new WordStatistics();
                    map.put(key, curWordStat);
                }
                curWordStat.addOccurency(wordNumber++);
            }
            return map;
        } finally {
            scn.close();
        }
    }

    public static void outputResult(Map<String, WordStatistics> map, String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName),
                StandardCharsets.UTF_8));
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
