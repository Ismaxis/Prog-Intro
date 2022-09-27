import java.io.*;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;


public class WordStatWordsPrefix {
    static final int BUFFER_SIZE = 1024;
    public static void main(String[] args) {
        try {
            LinkedHashMap<String, Integer> hashMap = countWordsInFile(args[0]);

            outputResult(hashMap, args[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Not enougth command line variables (2 expected): " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {   
            System.out.println("Input/Output error: " + e.getMessage());
        }
    }

    public static LinkedHashMap<String, Integer> countWordsInFile(String fileName) throws FileNotFoundException, IOException {
        LinkedHashMap<String, Integer> hashMap = new LinkedHashMap<>();
        BufferedReader reader = createReader(fileName);
        try {
            char[] buffer = new char[BUFFER_SIZE];
            int read = reader.read(buffer);
            String reminder = "";
            while (read >= 0) {
                reminder = parseWords(hashMap, reminder + new String(buffer, 0, read));
                read = reader.read(buffer);
            }
        } finally {
            reader.close();
        }

        return hashMap;
    }

    public static void outputResult(LinkedHashMap<String, Integer> hashMap, String fileName) throws FileNotFoundException, IOException {
        BufferedWriter writer = createWriter(fileName); 
        try {
            TreeSet<String> sortedSet = new TreeSet(hashMap.keySet());
            for (String prefics : sortedSet) {
                writer.write(prefics + " " + hashMap.get(prefics));
                writer.newLine();   
            }   
        } finally {
            writer.close();
        }
    }


    public static String parseWords(LinkedHashMap<String, Integer> hashMap, String source) {
        String reminder = "";
        for (int i = 0; i < source.length(); i++) {
            int start = i;

            while (isPartOfWord(source.charAt(i))) {
                i++;
                if (i == source.length()) {
                    reminder = source.substring(start, i);
                    break;
                }
            }
            if (start < i && reminder.isEmpty()) {
                String key = source.substring(start, start + 3).toLowerCase();
                int value = hashMap.containsKey(key) ? hashMap.get(key) : 0;
                hashMap.put(key, value + 1);
            }
        }

        return reminder;
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
