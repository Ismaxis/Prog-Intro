import java.util.Scanner;
import java.util.Arrays;

public class Reverse {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[][] arr = parseInput(in);

        printReverseArray(arr);
    }

    public static int[][] parseInput(Scanner inputScanner) {
        int[][] resultArr = {};
        Scanner lineScanner;

        while (inputScanner.hasNextLine()) {
            lineScanner = new Scanner(inputScanner.nextLine());
            int[] newRow = parseLine(lineScanner);
            // place for analyze

            resultArr = Arrays.copyOf(resultArr, resultArr.length + 1);
            resultArr[resultArr.length - 1] = newRow;
        }

        return resultArr;
    }

    public static int[] parseLine(Scanner lineScanner) {
        int[] lineArr = {};

        while (lineScanner.hasNextInt()) {
            int value = lineScanner.nextInt();
            // place for analyze

            lineArr = Arrays.copyOf(lineArr, lineArr.length + 1);
            lineArr[lineArr.length - 1] = value;
        }

        return lineArr;
    }

    public static void printReverseArray(int[][] arr) {
        for (int i = arr.length - 1; i >= 0; --i) {
            String rowOutput = "";
            if (arr[i].length > 0) {
                for (int j = arr[i].length - 1; j >= 0; --j) {
                    rowOutput = rowOutput + " " + arr[i][j];
                }
            }
            System.out.println(rowOutput);
        }
    }
}
