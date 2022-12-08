import myscanner.MyScanner;
import myscanner.PartOfWord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class WsppLastL {
    public static void main(final String[] args) {
        try {
            if (args.length >= 2) {
                outputResult(countWordsInFile(args[0]), args[1]);
            } else {
                System.err.println("Not enough command line args (2 expected)");
            }
        } catch (final FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (final IOException e) {
            // :NOTE: input/output
            System.out.println("Input/Output error: " + e.getMessage());
        }
    }

    public static Map<String, WordStatistics> countWordsInFile(final String fileName) throws FileNotFoundException {
        final MyScanner scn = new MyScanner(new FileInputStream(fileName), new PartOfWord());
        try {
            final Map<String, WordStatistics> map = new LinkedHashMap<>();
            int lineNumber = 1, wordNumber = 1;
            while (scn.hasNextToken()) {
                final String key = scn.nextToken().toLowerCase();

                if (lineNumber != scn.getLineNumber()) {
                    wordNumber = 1;
                    lineNumber = scn.getLineNumber();
                }

                WordStatistics curWordStat;
                curWordStat = map.get(key);
                if (curWordStat == null) {
                    curWordStat = new WordStatistics();
                    map.put(key, curWordStat);
                }
                curWordStat.addOccurency(wordNumber++, lineNumber);
            }
            return map;
        } finally {
            scn.close();
        }
    }

    public static void outputResult(final Map<String, WordStatistics> map, final String fileName) throws IOException {
        final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName),
                StandardCharsets.UTF_8));
        try {
            for (final Entry<String, WordStatistics> entry : map.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue());
                writer.newLine();
            }
        } finally {
            writer.close();
        }
    }
}
