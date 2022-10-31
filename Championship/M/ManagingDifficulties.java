package M;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ManagingDifficulties {
    public static void main(String[] args) {    
        Scanner scn = new Scanner(System.in);
        int[] counts;
        try {   
            int t = scn.nextInt();
            counts = new int[t];
            for (int testNum = 0; testNum < t; testNum++) {
                Map<Integer, Integer> map = new HashMap<>();
                int n = scn.nextInt();
                int[] a = new int[n];
                for (int i = 0; i < n; i++) {
                    a[i] = scn.nextInt();
                }
                
                int count = 0;
                map.put(a[n-1], 1);
                for (int j = n - 2; j > 0; j--) {
                    for (int i = 0; i < j; i++) {
                        count += map.getOrDefault(2*a[j] - a[i], 0);
                    }
                    map.put(a[j], map.getOrDefault(a[j], 0) + 1);
                }

                counts[testNum] = count;
            }
        } finally {
            scn.close();
        }
        
        for (int count : counts) {
            System.out.println(count);
        }
    }
}
