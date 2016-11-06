package ReversingLinkedList;

/**
 * https://pta.patest.cn/pta/test/1342/exam/4/question/19210
 *
 * 测试节点6未通过，超时！
 */

import java.io.*;

public class Main {
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static void main(String[] args) throws IOException {
        String s;
        String[] group;
        Node[] myHash;
        int maxNum, i, first, data;
        MyLinkedList myLinkedList = new MyLinkedList();


        while ((s = in.readLine()) != null) {
            myLinkedList.clear();
            myHash = new Node[100000];

            group = s.split(" ");
            first = Integer.parseInt(group[0]);
            maxNum = Integer.parseInt(group[1]);
            data = Integer.parseInt(group[2]);
            myLinkedList.setReverseNum(data);

            for (i = 0; i < maxNum; i++) {
                group = in.readLine().split(" ");

                data = Integer.parseInt(group[0]);

                myHash[data] = new Node(data, Integer.valueOf(group[1]), Integer.valueOf(group[2]));
            }

            while (first != -1) {
                if (myHash[first] != null) {
                    myLinkedList.insert(myHash[first]);
                    first = myHash[first].nextAddress;
                }
            }

            myLinkedList.reverse();
            myLinkedList.showLinkedList();
        }
    }

    static class MyLinkedList {
        private Node head, pre, curr, next, last;
        private int reverseNum, length;

        MyLinkedList() {
            this.head = new Node();
            this.length = 0;
        }


        void setReverseNum(int reverseNum) {
            this.reverseNum = reverseNum;
        }

        void clear() {
            this.head.next = null;
            this.reverseNum = 0;
            this.length = 0;
        }

        void insert(Node newNode) {
            curr = this.head;
            if (curr.next == null) {
                last = curr.next = newNode;
            } else {
                last.next = newNode;
                last = last.next;
            }
            this.length++;
        }

        /**
         * 链表的翻转
         */
        void reverse() {
            int times = this.length / this.reverseNum;
            if (reverseNum <= 1 || this.reverseNum > this.length) {
                return;
            }

            boolean flag;
            //tempRear记录尾
            Node tempRear = this.head, tempNode = tempRear.next;
            for (int j = 0; j < times; j++) {
                //需要翻转节点的前一个节点
                pre = tempRear.next;
                //需要翻转的节点
                curr = pre.next;

                //翻转reverseNum次
                for (int i = 1; i < reverseNum; i++) {

                    //tempRear定位到尾节点
                    //tempNode记录下一次会成为尾节点的头节点
                    if (i == 1) {
                        tempRear = tempNode;
                        tempNode = pre;
                    }

                    //保留需要翻转节点的下一个节点
                    next = curr.next;
                    //需要翻转的节点指向其前一个节点，即翻转
                    curr.next = pre;
                    curr.nextAddress = pre.address;

                    if (i == reverseNum - 1) {
                        //如果翻转结束时，j等于0表示第一次翻转，头指针需要改变
                        if (j == 0) {
                            this.head.next.next = next;
                            this.head.next = curr;
                            this.head.next.nextAddress = next == null ? -1 : next.address;
                        } else {
                            //改变变成尾节点的头结点，指向剩余节点，即连接剩余节点
                            //如2->1->3->4->5,tempRear记录的是1,3是指向4和5的
                            //翻转之后4指向3了，3不应该指向4，而是应该指向5，next便是5
                            tempRear.next.next = next;
                            tempRear.next.nextAddress = next == null ? -1 : next.address;


                            //翻转之后，原尾节点，重新连接新节点
                            //原2->1->3<-4   3->5
                            //改变尾2->1->4->3->5
                            tempRear.next = curr;
                            tempRear.nextAddress = curr.address;

                            //tempRear重新定位到翻转后的尾节点
                            //此时会指向3
                            tempRear = tempNode;
                        }
                        break;
                    }

                    pre = curr;
                    curr = next;
                }
            }
        }

        void showLinkedList() {
            curr = this.head.next;
            while (curr.next != null) {
                out.printf("%05d %d %05d\n", curr.address, curr.data, curr.nextAddress);
                curr = curr.next;
            }
            out.printf("%05d %d %d\n", curr.address, curr.data, curr.nextAddress);
            out.flush();
        }
    }
}

class Node {
    int data;
    int address;
    int nextAddress;
    Node next;

    Node() {
    }

    Node(int address, int data, int nextAddress) {
        this.address = address;
        this.data = data;
        this.nextAddress = nextAddress;
    }
}