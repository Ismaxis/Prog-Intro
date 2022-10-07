import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Wspp {
    public static void main(String[] args) {
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

    public static Map<String, WordStatistics> countWordsInFile(String fileName) throws FileNotFoundException, IOException {
        Map<String, WordStatistics> map = new LinkedHashMap<>();
        MyScanner scn = new MyScanner(new FileInputStream(fileName), new PartOfWord());
    
        int curWord = 1;
        while (scn.hasNextToken()) {
            String key = scn.nextToken().toLowerCase();
            WordStatistics curWordStat;
            if (map.containsKey(key)) {
                curWordStat = map.get(key);
            } else {
                curWordStat = new WordStatistics();
            }

            curWordStat.addOccurency(curWord++);
            map.put(key, curWordStat);
        }
        
        return map;
    }

    public static void outputResult(Map<String, WordStatistics> map, String fileName) throws FileNotFoundException, IOException{
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));
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

class WordStatistics{
    private IntList occurences;

    WordStatistics() {
        occurences = new IntList();
    }

    public void addOccurency(int indexOfOccurency) {
        occurences.append(indexOfOccurency);
    }
    public int[] getOccurences() {
        return occurences.toIntArray();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(occurences.size());
        for (int position : getOccurences()) {
            s.append(" " + position);
        }
        return s.toString();
    }
} 
