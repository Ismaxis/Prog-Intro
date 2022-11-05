package E;

import java.util.Arrays;
import java.util.Scanner;

public class Equidistant {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        try {   
            int n = scn.nextInt();
            int m = scn.nextInt();

            boolean[][] roads = new boolean[n][n];
            for (int i = 0; i < n - 1; i++) {
                int city1 = scn.nextInt() - 1;
                int city2 = scn.nextInt() - 1;
                roads[city1][city2] = true; 
                roads[city2][city1] = true;    
            }

            boolean[] teams = new boolean[n];
            for (int i = 0; i < m; i++) {
                teams[scn.nextInt() - 1] = true;
            }

            boolean[] visited = new boolean[n]; 
            IntList trace = new IntList();

            int maxDepth = 0;
            IntList maxDepthTrace = new IntList();
            int v = -1;
            visited[0] = true;
            trace.append(0);

            for (int i = 0; i < n; i++) {
                if (roads[0][i] && !visited[i]) {
                    trace.append(i);
                    dfs(roads, visited, i, trace);
                    int depth = trace.size();
                    for (int j = depth - 1; j >= 0; j--) {
                        if (teams[trace.get(j)]) {
                            if (depth > maxDepth) {
                                maxDepth = depth;
                                v = trace.get(depth/2);
                                maxDepthTrace = trace;
                            }
                            trace = new IntList();
                            trace.append(0);
                            break;
                        }
                    }
                }
            }

            // v - середина в самом глубоком пути
            boolean isPosible = true;

            visited = new boolean[n]; 
            int checkDepth = -1;
            for (int i = maxDepth/2 + 1; i < maxDepth; i++) {
                int curCity = maxDepthTrace.get(i);
                if (roads[v][curCity] && !visited[curCity]) {
                    dfs(roads, visited, curCity, maxDepthTrace);
                    int depth = maxDepthTrace.size();
                    for (int j = depth - 1; j >= 0; j--) {
                        if (teams[trace.get(j)]) {
                            if (checkDepth == -1) {
                                checkDepth = depth;
                            } else if (checkDepth != depth) {
                                isPosible = false;
                            }
                            break;
                        }
                    }
                    if (!isPosible) {
                        break;
                    }
                }
            }

            if (isPosible) {
                System.out.println("YES");
                System.out.println(v);
            } else {
                System.out.println("NO");
            }

        } finally {
            scn.close();
        }
    }

    public static void dfs(boolean[][] roads, boolean[] visited, int i, IntList trace) {
        visited[i] = true;
        for (int j = 0; j < roads.length; j++) {
            if (roads[i][j]) {
                if (!visited[j]) {
                    trace.append(j);
                    dfs(roads, visited, j, trace);
                }
            }
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