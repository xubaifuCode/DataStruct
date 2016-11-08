package ForwardsOnWeibo;

import java.io.*;

/**
 * Created by xu-cat on 16-11-7.
 * https://www.patest.cn/contests/pat-a-practise/1076
 */
public class Main {
    static UserFollowList[] userFollowLists;
    static MyQueue myque = new MyQueue();
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        int n, l, m, k;

        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) in.nval;
            in.nextToken();
            l = (int) in.nval;

            userFollowLists = new UserFollowList[n + 1];

            for (int i = 1; i <= n; i++) {
                userFollowLists[i] = new UserFollowList();
                in.nextToken();
                m = (int) in.nval;
                for (int j = 0; j < m; j++) {
                    in.nextToken();
                    userFollowLists[i].insert(new Node((int) in.nval));
                }
            }

            //k个用户发表推文的最大可见数
            in.nextToken();
            k = (int) in.nval;

            for (int i = 0; i < k; i++) {
                in.nextToken();
                out.println(bfs((int) in.nval, l, n));
            }
            out.flush();
        }
    }

    static int bfs(int postUser, int level, int n) {
        myque.clear();
        int total = 0, tail = postUser, last = postUser, layer = 0;

        visited = new boolean[n + 1];

        myque.add(postUser);
        visited[postUser] = true;

        while (!myque.isEmpty()) {
            postUser = myque.remove();

            for (int i = 1; i <= n; i++) {
                if (!visited[i] && isFollowed(i, postUser)) {
                    myque.add(i);
                    visited[i] = true;
                    tail = i;
                    total++;
                }
            }

            if (last == postUser) {
                last = tail;
                layer++;
            }

            if (layer == level) {
                break;
            }
        }
        return total;
    }

    static boolean isFollowed(int u, int f) {
        userFollowLists[u].reset();
        while (userFollowLists[u].hasNext()) {
            if (userFollowLists[u].next().followId == f) {
                return true;
            }
        }
        return false;
    }
}

class MyQueue {
    int[] myQue;
    int max, tail, head, length;

    MyQueue() {
        this.max = 1001;
        clear();
        this.myQue = new int[this.max];
    }

    void clear() {
        this.head = this.tail = this.length = 0;
    }

    boolean isEmpty() {
        return this.length == 0;
    }

    void add(int uId) {
        myQue[this.tail++] = uId;
        this.tail %= this.max;
        this.length++;
    }

    int remove() {
        int followedId = myQue[this.head++];
        this.head %= this.max;
        this.length--;
        return followedId;
    }
}

class UserFollowList {
    Node head, curr, tail;

    UserFollowList() {
        this.head = this.tail = new Node();
    }

    void insert(Node t) {
        tail.next = t;
        tail = tail.next;
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
    int followId;
    Node next;

    Node() {
    }

    Node(int followId) {
        this.followId = followId;
    }
}
