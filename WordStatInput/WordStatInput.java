import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map.Entry;


public class WordStatInput {
    public static void main(String[] args) {
        LinkedHashMap<String, Integer> hashMap = new LinkedHashMap<>();

        try {
            BufferedReader reader = createReader(args[0]);
            try {
                String line = reader.readLine();
                while (line != null) {
                    parseWords(hashMap, line);
                    line = reader.readLine();
                }
            } finally {
                reader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            PrintWriter writer = createWriter(args[1]);
            try {
                for (Entry<String, Integer> entry : hashMap.entrySet()) {
                    writer.println(entry.getKey() + " " + entry.getValue());
                }  
            } finally {
                writer.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }

    public static void parseWords(LinkedHashMap<String, Integer> hashMap, String source) {
        for (int i = 0; i < source.length(); i++) {
            int start = i;

            while (i != source.length() && isPartOfWord(source.charAt(i))) {
                i++;
            }

            if (start < i) {
                String key = source.substring(start, i).toLowerCase();
                int value;
                if (hashMap.containsKey(key)) {
                    value = hashMap.get(key) + 1;
                } else {
                    value = 1;
                }
                hashMap.put(key, value);
            }
        }
    }

    public static boolean isPartOfWord(char ch) {
        return Character.isLetter(ch) || Character.DASH_PUNCTUATION == Character.getType(ch) || ch == '\'';
    }

    public static BufferedReader createReader(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        return new BufferedReader(
            new InputStreamReader(
                new FileInputStream(fileName), "utf8")); 
    }

    public static PrintWriter createWriter(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        return new PrintWriter(
                new OutputStreamWriter(
                    new FileOutputStream(fileName), "utf8"));
    }
}
