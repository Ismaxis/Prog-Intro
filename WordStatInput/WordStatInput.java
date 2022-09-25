import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map.Entry;


public class WordStatInput {
    public static void main(String[] args) {
        try {
            LinkedHashMap<String, Integer> hashMap = countWordsInFile(args[0]);

            outputResult(hashMap, args[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Not enougth command line variables (2 expexted): " + e.getMessage());
        }
    }

    public static LinkedHashMap<String, Integer> countWordsInFile(String fileName) {
        LinkedHashMap<String, Integer> hashMap = new LinkedHashMap<>();

        try {
            BufferedReader reader = createReader(fileName);
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
            System.out.println("IO Exception: " + e.getMessage());
        }

        return hashMap;
    }

    public static void outputResult(LinkedHashMap<String, Integer> hashMap, String fileName) {
        try {
            PrintWriter writer = createWriter(fileName); 
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

    public static void parseWords(LinkedHashMap<String, Integer> hashMap, String string) {
        for (int i = 0; i < string.length(); i++) {
            int start = i;

            while (i != string.length() && isPartOfWord(string.charAt(i))) {
                i++;
            }

            if (start < i) {
                String key = string.substring(start, i).toLowerCase();
                int value = hashMap.containsKey(key) ? hashMap.get(key) : 0;
                hashMap.put(key, value + 1);
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
