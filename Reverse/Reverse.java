import java.util.Scanner;
import java.util.Arrays;

public class Reverse {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[][] arr = parseInput(in);

        printReverseArray(arr);
    }

    public static int[][] parseInput(Scanner inputScanner) {
        int[][] resultArr = new int[1][];
        Scanner lineScanner;
        int amount = 0;

        while (inputScanner.hasNextLine()) {
            lineScanner = new Scanner(inputScanner.nextLine());
            int[] newRow = parseLine(lineScanner);
            // place for analyze

            if (amount >= resultArr.length) {
                resultArr = Arrays.copyOf(resultArr, resultArr.length * 2);
            }
            resultArr[amount] = newRow;
            amount++;
        }

        resultArr = Arrays.copyOf(resultArr, amount);
        return resultArr;
    }

    public static int[] parseLine(Scanner lineScanner) {
        int[] lineArr = new int[1];
        int amount = 0;
        while (lineScanner.hasNextInt()) {
            int value = lineScanner.nextInt();
            // place for analyze

            if (amount >= lineArr.length) {
                lineArr = Arrays.copyOf(lineArr, lineArr.length * 2);
            }
            lineArr[amount] = value;
            amount++;
        }

        lineArr = Arrays.copyOf(lineArr, amount);

        return lineArr;
    }

    public static void printReverseArray(int[][] arr) {
        for (int i = arr.length - 1; i >= 0; --i) {
            if (arr[i].length > 0) {
                for (int j = arr[i].length - 1; j >= 0; --j) {
                    System.out.print(arr[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}
