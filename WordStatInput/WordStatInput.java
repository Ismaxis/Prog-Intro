import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map.Entry;


public class WordStatInput {
    static final int BUFFER_SIZE = 16;
    public static void main(String[] args) {
        LinkedHashMap<String, Integer> hashMap = new LinkedHashMap<>();

        try {
            BufferedReader reader = createReader(args[0]);
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
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            BufferedWriter writer = createWriter(args[1]);
            try {
                for (Entry<String,Integer> entry : hashMap.entrySet()) {
                    writer.write(entry.getKey() + " " + entry.getValue() + "\n");
                }  
            } finally {
                writer.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
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

        return reminder;
    }

    public static boolean isPartOfWord(char ch) {
        return Character.isLetter(ch) || Character.DASH_PUNCTUATION == Character.getType(ch) || ch == '\'';
    }

    public static BufferedReader createReader(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        return new BufferedReader(
            new InputStreamReader(
                new FileInputStream(fileName), "utf-8")); 
    }

    public static BufferedWriter createWriter(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        return new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream(fileName), "utf-8"));
    }
}
