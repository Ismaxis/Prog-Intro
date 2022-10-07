import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;


public class Wspp {
    static final int BUFFER_SIZE = 1024;
    public static void main(String[] args) {
        try {
            Map<String, WordStatistics> map = countWordsInFile(args[0]);

            outputResult(map, args[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Not enougth command line variables (2 expected): " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {   
            System.out.println("Input/Output error: " + e.getMessage());
        }
    }

    public static Map<String, WordStatistics> countWordsInFile(String fileName) throws FileNotFoundException, IOException {
        Map<String, WordStatistics> hashMap = new LinkedHashMap<>();
        BufferedReader reader = createReader(fileName);
        try {
            char[] buffer = new char[BUFFER_SIZE];
            int read = reader.read(buffer);
            String reminder = "";
            int totalAmountOfWords = 0;
            while (read >= 0) {
                reminder = parseWords(hashMap, reminder + new String(buffer, 0, read));
                read = reader.read(buffer);
            }
        } finally {
            reader.close();
        }

        return hashMap;
    }

    public static void outputResult(Map<String, WordStatistics> map, String fileName) throws FileNotFoundException, IOException {
        BufferedWriter writer = createWriter(fileName); 
        try {
            for (Entry<String, WordStatistics> entry : map.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue());
                writer.newLine();   
            }   
        } finally {
            writer.close();
        }
    }


    public static String parseWords(Map<String, WordStatistics> map, String source) {
        for (int i = 0; i < source.length(); i++) {
            int start = i;

            while (isPartOfWord(source.charAt(i))) {
                i++;
                if (i == source.length()) {
                    return source.substring(start, i); // reminder
                }
            }
            if (start < i) { // ?? && reminder.isEmpty())
                String key = source.substring(start, i).toLowerCase();
                WordStatistics curWordStat;
                if (map.containsKey(key)) {
                    curWordStat = map.get(key);
                } else {
                    curWordStat = new WordStatistics();
                }
                curWordStat.addOccurency(start);
                map.put(key, curWordStat);
            }
        }

        return "";
    }

    public static boolean isPartOfWord(char ch) {
        return Character.isLetter(ch) || Character.DASH_PUNCTUATION == Character.getType(ch) || ch == '\'';
    }

    public static BufferedReader createReader(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        return new BufferedReader(
            new InputStreamReader(
                new FileInputStream(fileName), "UTF8"), BUFFER_SIZE); 
    }

    public static BufferedWriter createWriter(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        return new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream(fileName), "UTF8"));
    }
}

class WordStatistics{
    private int countOfOccurences;
    private IntList occurences;

    WordStatistics() {
        countOfOccurences = 0;
        occurences = new IntList();
    }

    public void addOccurency(int indexOfOccurency) {
        occurences.append(indexOfOccurency);
        countOfOccurences++;
    }
    public int getCountOfOccurences() {
        return countOfOccurences;
    }
    public int[] getOccurences() {
        return occurences.toIntArray();
    }
}
