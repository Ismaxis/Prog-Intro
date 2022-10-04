import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;


public class WordStatWordsPrefix {
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
}
