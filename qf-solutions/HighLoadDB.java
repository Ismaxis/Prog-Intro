import java.util.Scanner;

public class HighLoadDB {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        
        try {
            int n = scn.nextInt();
            int[] a = new int[n];
            int maxA = 0;
            int sum = 0;
            for (int i = 0; i < n; i++) {
                int nextT = scn.nextInt();
                a[i] = sum + nextT;
                maxA = Math.max(maxA, nextT);
                sum += nextT;
            }

            int[] f = new int[sum];
            int curTransaction = 0;
            for (int i = 0; i < sum;) {
                while (i < a[curTransaction]) {
                    f[i] = curTransaction;
                    i++;
                }
                curTransaction++;
            }

            int q = scn.nextInt();
            int[] ans = new int[q];
            for (int i = 0; i < q; i++) {
                int t = scn.nextInt();
                if (t < maxA) {
                    ans[i] = -1;
                    continue;
                } else if (t == sum) {
                    ans[i] = 1;
                    continue;
                }

                ans[i] = 2;
                int b = f[t];
                int curIndex = a[b-1] + t;
                while (curIndex < sum) {
                    b = f[curIndex];
                    ans[i]++;
                    curIndex = a[b-1] + t;
                }
            }
            
            for (int i : ans) {
                System.out.println(i == -1 ? "Impossible" : i);
            }
        } finally {
            scn.close();
        }
    }
}
