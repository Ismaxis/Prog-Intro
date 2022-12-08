import java.util.Arrays;

import myscanner.MyScanner;
import myscanner.WhiteSpace;

public class ReverseOctDec {
    public static void main(String[] args) {
        MyScanner in = new MyScanner(System.in, new WhiteSpace());
        int[][] arr = parseInput(in);

        printReverseArray(arr);
    }

    public static int[][] parseInput(MyScanner inputScanner) {
        int[][] resultArr = new int[1][];
        MyScanner lineScanner;
        int amount = 0;

        while (inputScanner.hasNextLine()) {
            lineScanner = new MyScanner(inputScanner.nextLine(), inputScanner.getCompareMethodObj());
            if (amount >= resultArr.length) {
                resultArr = Arrays.copyOf(resultArr, resultArr.length * 2);
            }
            resultArr[amount++] = parseLine(lineScanner);
        }
        resultArr = Arrays.copyOf(resultArr, amount);
        return resultArr;
    }

    public static int[] parseLine(MyScanner lineScanner) {
        int[] lineArr = new int[1];
        int amount = 0;
        while (lineScanner.hasNextInt()) {
            if (amount >= lineArr.length) {
                lineArr = Arrays.copyOf(lineArr, lineArr.length * 2);
            }

            lineArr[amount++] = lineScanner.nextInt();
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
