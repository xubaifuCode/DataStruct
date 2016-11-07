package 列出连通集;

import java.io.*;

/**
 * Created by xu-cat on 16-11-6.
 * https://pta.patest.cn/pta/test/1342/exam/4/question/22500
 */
public class Main {
    static boolean[] visited;
    static int[] queue;
    static int head, tail;
    static boolean[][] map;
    static int e, n;

    static StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
    static PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String[] args) throws IOException {

        int v1, v2;
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) in.nval;
            in.nextToken();
            e = (int) in.nval;

            map = new boolean[n + 1][n + 1];
            queue = new int[n];

            for (int i = 0; i < e; i++) {
                in.nextToken();
                v1 = (int) in.nval;
                in.nextToken();
                v2 = (int) in.nval;
                map[v1][v2] = true;
                map[v2][v1] = true;
            }

            visited = new boolean[n];
            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    out.print("{ ");
                    dfs(i);
                    out.println("}");
                }
            }
            visited = new boolean[n];
            for (int i = 0; i < n; i++) {
                if (!visited[i]) {
                    out.print("{ ");
                    bfs(i);
                    out.println("}");
                }
            }
            out.flush();
        }
    }

    static void dfs(int v) {
        visited[v] = true;
        out.print(v + " ");
        for (int i = 0; i < n; i++) {
            if (map[v][i] && !visited[i]) {
                dfs(i);
            }
        }
    }

    static void bfs(int v) {
        head = tail = 0;
        queue[tail++] = v;
        visited[v] = true;
        while (tail != head) {
            v = queue[head++];
            out.print(v + " ");

            for (int i = 0; i < n; i++) {
                if (!visited[i] && map[v][i]) {
                    queue[tail++] = i;
                    visited[i] = true;
                }
            }
        }
    }
}
