import java.util.Arrays;

public class ReverseSum {
    public static void main(String[] args) {
        MyScanner inputScanner = new MyScanner(System.in, new WhiteSpace());
        int[][] arr = parseInput(inputScanner);

        printRowAndColSum(arr);
    }

    public static int[][] parseInput(MyScanner inputScanner) {
        int[][] resultArr = new int[1][];
        int count = 0;

        while (inputScanner.hasNextLine()) {
            if (count >= resultArr.length) {
                resultArr = Arrays.copyOf(resultArr, resultArr.length * 2);
            }
            resultArr[count++] = parseLine(new MyScanner(inputScanner.nextLine(), new WhiteSpace()));
        }

        resultArr = Arrays.copyOf(resultArr, count);
        return resultArr;
    }

    public static int[] parseLine(MyScanner lineScanner) {
        int[] lineArr = new int[1];
        int count = 0;
        while (lineScanner.hasNextToken()) {
            if (count >= lineArr.length) {
                lineArr = Arrays.copyOf(lineArr, lineArr.length * 2);
            }
            lineArr[count++] = Integer.parseInt(lineScanner.nextToken());
        }

        lineArr = Arrays.copyOf(lineArr, count);
        return lineArr;
    }

    public static void printRowAndColSum(int[][] arr) {
        int[] rowSums = calcRowSums(arr);
        int[] colSums = calcColSums(arr);

        for (int i = 0; i < arr.length ; i++) {
            if (arr[i].length > 0) {
                for (int j = 0; j < arr[i].length; ++j) {
                    System.out.print(rowSums[i] + colSums[j] - arr[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public static int[] calcRowSums(int[][] arr) {
        int[] rowArr = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                rowArr[i] += arr[i][j];
            }
        }
        return rowArr;
    }
    
    public static int[] calcColSums(int[][] arr) {
        int maxLength = getMaxArrLen(arr);
        int[] colSums = new int[maxLength];

        for (int i = 0; i < maxLength; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[j].length > i) {
                    colSums[i] += arr[j][i];
                }
            }
        }
        return colSums;
    }

    public static int getMaxArrLen(int[][] arr) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            max = (arr[i].length > max) ? arr[i].length : max;
        }
        return max;
    }
}
