import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;


public class WordStatWordsPrefix {
    private static final int BUFFER_SIZE = 1024;
    public static void main(String[] args) {
        try {
            if (args.length >= 2) {
                Map<String, Integer> map = countWordsInFile(args[0]);

                outputResult(map, args[1]);
            } else {
                System.out.println("Not enougth command line args (2 expected)");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {   
            System.out.println("Unsupported encoding: " + e.getMessage());
        } catch (IOException e) {   
            System.out.println("Input/Output error: " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }

    public static Map<String, Integer> countWordsInFile(String fileName) throws FileNotFoundException, IOException {
        HashMap<String, Integer> hashMap = new HashMap<>();
        MyScanner scn = new MyScanner(new FileInputStream(fileName), new PartOfWord());
        
        while (scn.hasNextToken()) {
            String key = scn.nextToken().toLowerCase();
            key = key.substring(0, Math.min(3, key.length()));
            int value =  hashMap.getOrDefault(key, 0);
            hashMap.put(key, value + 1);
        }

        // BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF8"), BUFFER_SIZE); 
        // try {
        //     char[] buffer = new char[BUFFER_SIZE];
        //     int read = reader.read(buffer);
        //     String reminder = "";
        //     while (read >= 0) {
        //         reminder = parseWords(hashMap, reminder + new String(buffer, 0, read));
        //         read = reader.read(buffer);
        //     }
        // } finally {
        //     reader.close();
        // }

        return hashMap;
    }

    public static void outputResult(Map<String, Integer> hashMap, String fileName) throws FileNotFoundException, IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF8"));  
        try {
            TreeSet<String> sortedSet = new TreeSet<>(hashMap.keySet());
            for (String prefics : sortedSet) {
                writer.write(prefics + " " + hashMap.get(prefics));
                writer.newLine();   
            }   
        } finally {
            writer.close();
        }
    }

    public static String parseWords(Map<String, Integer> hashMap, String source) {
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
                String key = source.substring(start, min(start + 3, i)).toLowerCase();
                int value = hashMap.getOrDefault(key, 0);
                hashMap.put(key, value + 1);
            }
        }
        return reminder;
    }

    public static boolean isPartOfWord(char ch) {
        return Character.isLetter(ch) || Character.DASH_PUNCTUATION == Character.getType(ch) || ch == '\'';
    }
    
    public static BufferedReader createReader(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF8"), BUFFER_SIZE); 
    }
    
    public static BufferedWriter createWriter(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        return new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream(fileName), "UTF8"));         
    }

    public static int min(int a, int b) {
        return a <= b ? a : b;
    }
}
