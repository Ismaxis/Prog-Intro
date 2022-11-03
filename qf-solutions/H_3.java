package H;
 
import java.util.Scanner;
import java.util.Arrays;
 
public class HighLoadDB {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
         
        try {
            int n = scn.nextInt();
            int[] a = new int[n];
            int maxA = 0;
            int sum = 0;
            IntList f = new IntList();
            int curTransaction = 0;
            int cntr = 0;
            for (int i = 0; i < n; i++) {
                int nextT = scn.nextInt();
                a[i] = sum + nextT;
                maxA = Math.max(maxA, nextT);
                sum += nextT;

                while (cntr < a[curTransaction]) {
                    f.append(curTransaction);
                    cntr++;
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
                int b = f.get(t);
                int curIndex = a[b-1] + t;
                while (curIndex < sum) {
                    b = f.get(curIndex);
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


class IntList { 
    private int[] storage;
    private int actualSize;

    public IntList(int initSize) {
        storage = new int[initSize];
        actualSize = 0;
    }

    public IntList() {
        this(1);
    }

    public IntList(IntList that) {
        this.storage = Arrays.copyOf(that.storage, that.storage.length);
        this.actualSize = that.actualSize; 
    }

    public int size() {
        return actualSize;
    }
    
    public void append(int value) {
        adjustLength();
        storage[actualSize++] = value;
    }

    public void set(int index, int value) {
        if (isValidIndex(index)) {
            storage[index] = value;
        }
    }

    public int[] toIntArray() {
        return Arrays.copyOf(storage, actualSize);
    }

    public int get(int index) {
        if (isValidIndex(index)) {
            return storage[index];
        } else {
            throw new ArrayIndexOutOfBoundsException(index);
        }
    }

    private void adjustLength() {
        if (actualSize == storage.length) {
            storage = Arrays.copyOf(storage, actualSize * 2);
        }
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < actualSize;
    }
}