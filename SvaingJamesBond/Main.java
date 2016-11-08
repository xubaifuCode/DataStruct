package SvaingJamesBond;

import java.io.*;

/**
 * Created by xu-cat on 16-11-7.
 * https://pta.patest.cn/pta/test/1342/exam/4/question/23159
 */
public class Main {
    static MyQueue q1, q2;

    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        int n, d, x, y;

        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) in.nval;
            in.nextToken();
            d = (int) in.nval;

            q1 = new MyQueue(100);
            q2 = new MyQueue(100);

            for (int i = 0; i < n; i++) {
                in.nextToken();
                x = (int) in.nval;
                in.nextToken();
                y = (int) in.nval;
                q1.add(new Node(x, y));
            }

            out.println(bfs(d) ? "Yes" : "No");
            out.flush();

        }
    }

    static boolean bfs(int d) {
        int n;
        Node t1, t2;
        boolean flag = true;
        q2.add(new Node(0, 0));
        while (!q2.isEmpty()) {
            t1 = q2.remove();

            if (Math.max(Math.abs(t1.x), Math.abs(t1.y)) + d >= 50) {
                return true;
            }

            n = q1.length;
            for (int i = 0; i < n; i++) {
                t2 = q1.remove();
                if (inRange(t2.x - t1.x, t2.y - t1.y, flag ? d + 7.5 : d)) {
                    q2.add(t2);
                } else {
                    q1.add(t2);
                }
            }
            flag = false;
        }
        return false;
    }

    static boolean inRange(int d1, int d2, double d) {
        return d1 * d1 + d2 * d2 <= d * d;
    }

    static class MyQueue {
        Node[] nodes;
        int head, tail, n, length;

        MyQueue(int n) {
            this.n = n;
            this.length = 0;
            this.nodes = new Node[n];
            this.head = this.tail = 0;
        }

        boolean isEmpty() {
            return this.length == 0;
        }

        boolean isFull() {
            return this.length == this.n;
        }

        void add(Node t) {
            if (isFull()) {
                return;
            }
            this.nodes[this.tail++] = t;
            this.tail = this.tail % this.n;
            this.length++;
        }

        Node remove() {
            Node t = nodes[this.head++];
            this.head = this.head % this.n;
            this.length--;
            return t;
        }
    }
}

class Node {
    int x;
    int y;

    Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
