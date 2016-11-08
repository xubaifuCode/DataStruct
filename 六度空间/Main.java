package 六度空间;

import java.io.*;

/**
 * Created by xu-cat on 16-11-7.
 * https://pta.patest.cn/pta/test/1342/exam/4/question/22502
 * N = 10000
 * M = 330000
 */
public class Main {
    static MyQueue myQue = new MyQueue();
    static MyLinked[] myLinked;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        int n, m;

        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) in.nval;
            in.nextToken();
            m = (int) in.nval;

            myLinked = new MyLinked[n + 1];

            for (int i = 1; i <= n; i++) {
                myLinked[i] = new MyLinked();
            }

            for (int i = 0, from, to; i < m; i++) {
                in.nextToken();
                from = (int) in.nval;
                in.nextToken();
                to = (int) in.nval;
                myLinked[from].insert(new Node(to));
                myLinked[to].insert(new Node(from));
            }

            for (int i = 1; i <= n; i++) {
                visited = new boolean[n + 1];
                out.printf("%d: %.2f%%\n", i, bfs(i) * 1.0 / n * 100.0);
            }
            out.flush();
        }
    }

    static int bfs(int v) {
        myQue.clear();
        myQue.add(v);
        visited[v] = true;
        Node t;
        int total = 1, last = v, tail = v, layer = 0;

        while (!myQue.isEmpty()) {
            v = myQue.remove();
            myLinked[v].reset();
            while (myLinked[v].hasNext()) {
                t = myLinked[v].next();
                if (!visited[t.to]) {
                    myQue.add(t.to);
                    visited[t.to] = true;
                    tail = t.to;
                    total++;
                }
            }
            if (v == last) {
                layer++;
                last = tail;
            }
            if (layer == 6) {
                break;
            }
        }
        return total;
    }
}

class MyQueue {
    int[] q;
    int head, tail;
    int max, length;

    MyQueue() {
        this.max = 10000;
        this.q = new int[max];
        this.head = this.tail = 0;
        this.length = 0;
    }

    void clear() {
        this.head = this.tail = this.length = 0;
    }

    boolean isEmpty() {
        return this.length == 0;
    }

    void add(int t) {
        q[this.tail++] = t;
        this.tail = this.tail % max;
        this.length++;
    }

    int remove() {
        int t = q[this.head++];
        this.head = this.head % max;
        this.length--;
        return t;
    }
}

class MyLinked {
    Node head, curr, tail;

    MyLinked() {
        this.curr = this.tail = this.head = new Node();
    }

    void insert(Node t) {
        this.tail.next = t;
        this.tail = this.tail.next;
    }

    void reset() {
        this.curr = this.head;
    }

    Node next() {
        return this.curr;
    }

    boolean hasNext() {
        this.curr = this.curr.next;
        return curr != null;
    }
}

class Node {
    int to;
    Node next;

    Node() {
    }

    Node(int to) {
        this.to = to;
    }
}
