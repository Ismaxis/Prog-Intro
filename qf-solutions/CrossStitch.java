import java.util.Scanner;
import java.util.Stack;


public class CrossStitch {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        try {
            int x = scn.nextInt();
            int y = scn.nextInt();

            int vertCount = (x + 1) * (y + 1);

            int[][] faceEdges = new int[vertCount][vertCount];
            int[][] backEdges = new int[vertCount][vertCount];

            int[] startVertex = {0, 0};
            scn.nextLine();
            for (int i = 0; i < y; i++) {
                String line = scn.nextLine();
                for (int j = 0; j < x; j++) {
                    if (line.charAt(j) == 'X') {
                        setEdge(faceEdges, (x + 1) * i + j, (x + 1) * (i + 1) + j + 1, 1); // \
                        setEdge(faceEdges, (x + 1) * (i + 1) + j, (x + 1) * i + j + 1, 1); // /

                        setEdge(backEdges, (x + 1) * i + j, (x + 1) * i + j + 1, 1); // _
                        setEdge(backEdges, (x + 1) * (i + 1) + j + 1, (x + 1) * (i + 1) + j, 1); // _
                        setEdge(backEdges, (x + 1) * i + j, (x + 1) * (i + 1) + j, 1); // |
                        setEdge(backEdges, (x + 1) * (i + 1) + j + 1, (x + 1) * i + j + 1, 1); // |

                        startVertex[0] = j + 1;
                        startVertex[1] = i + 1;
                    }
                }
            }

            Stack<int[]> s = new Stack<>();  // :NOTE: don't use Stack
            s.push(startVertex);

            boolean isFace = true;
            while (!s.empty()) {
                int[] curVertex = s.peek();
                boolean edgeFound = findEdge(faceEdges, backEdges, isFace, curVertex, s, x, y);

                if (!edgeFound) {
                    break;
                }

                isFace = !isFace;
            }

            System.out.println(s.size() - 1);
            for (int[] is : s) {
                System.out.printf("%d %d%n", is[0], is[1]);
            }


        } finally {
            scn.close();
        }
    }

    public static boolean findEdge(
        int[][] faceEdges,
        int[][] backEdges,
        boolean isFace,
        int[] vertex,
        Stack<int[]> s,
        int x,
        int y
    ) {
        int curIndex = (x + 1) * vertex[1] + vertex[0];
        int[][] mainEdges = isFace ? faceEdges : backEdges;
        int[][] secEdges = isFace ? backEdges : faceEdges;

        for (int i = 1; i >= -1; i--) {
            for (int j = -1; j < 2; j++) {
                int[] secVert = {j + vertex[0], i + vertex[1]};
                if ((secVert[1] >= 0 && secVert[1] < y + 1) && (secVert[0] >= 0 && secVert[0] < x + 1)) {
                    int secIndex = (x + 1) * secVert[1] + secVert[0];
                    if (mainEdges[curIndex][secIndex] == 1) {
                        for (int k = 0; k < secEdges.length; k++) {
                            if (secEdges[secIndex][k] == 1) {
                                s.push(secVert);
                                if (isFace) {
                                    setEdge(mainEdges, curIndex, secIndex, -1);
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public static void setEdge(int[][] arr, int i, int j, int val) {
        arr[i][j] = val;
        arr[j][i] = val;
    }
}