// import java.util.Arrays;

// public class Reverse {
//     public static void main(String[] args) {
//         MyScanner in = new MyScanner(System.in, new WhiteSpace());
//         int[][] arr = parseInput(in);

//         printReverseArray(arr);
//     }

//     public static int[][] parseInput(MyScanner inputScanner) {
//         int[][] resultArr = new int[1][];
//         MyScanner lineScanner;
//         int amount = 0;

//         while (inputScanner.hasNextLine()) {
//             lineScanner = new MyScanner(inputScanner.nextLine(), inputScanner.getCompareMethodObj());
//             int[] newRow = parseLine(lineScanner);

//             if (amount >= resultArr.length) {
//                 resultArr = Arrays.copyOf(resultArr, resultArr.length * 2);
//             }
//             resultArr[amount] = newRow;
//             amount++;
//         }

//         resultArr = Arrays.copyOf(resultArr, amount);
//         return resultArr;
//     }

//     public static int[] parseLine(MyScanner lineScanner) {
//         int[] lineArr = new int[1];
//         int amount = 0;
//         while (lineScanner.hasNextToken()) {
//             int value = Integer.parseInt(lineScanner.nextToken());

//             if (amount >= lineArr.length) {
//                 lineArr = Arrays.copyOf(lineArr, lineArr.length * 2);
//             }
//             lineArr[amount] = value;
//             amount++;
//         }

//         lineArr = Arrays.copyOf(lineArr, amount);

//         return lineArr;
//     }

//     public static void printReverseArray(int[][] arr) {
//         for (int i = arr.length - 1; i >= 0; --i) {
//             if (arr[i].length > 0) {
//                 for (int j = arr[i].length - 1; j >= 0; --j) {
//                     System.out.print(arr[i][j] + " ");
//                 }
//             }
//             System.out.println();
//         }
//     }
// }
