package wordstat;

public class WordStatistics {
    private IntList occurences;
    private int amountOfOccurences;
    private int lastLineOccur;

    public WordStatistics() {
        occurences = new IntList();
        lastLineOccur = 0;
    }

    public void addOccurency(int indexOfOccurency, int lineNumber) {
        if (lineNumber == lastLineOccur) {
            occurences.set(occurences.size() - 1, indexOfOccurency);
        } else {
            occurences.append(indexOfOccurency);
        }

        lastLineOccur = lineNumber;
        amountOfOccurences++;
    }

    public void addOccurency(int indexOfOccurency) {
        occurences.append(indexOfOccurency);
        amountOfOccurences++;
    }

    public int[] getOccurences() {
        return occurences.toIntArray();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(amountOfOccurences);
        for (int position : getOccurences()) {
            s.append(" " + position);
        }
        return s.toString();
    }
}