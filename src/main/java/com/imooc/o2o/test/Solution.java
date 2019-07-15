package com.imooc.o2o.test;



class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}

class Solution {

    public static ListNode createNode(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }

        ListNode headNode = new ListNode(arr[0]);
        ListNode curNode = headNode;

        for (int i = 1; i < arr.length; i++) {
            curNode.next = new ListNode(arr[i]);
            curNode = curNode.next;
        }

        return headNode;
    }

    public static void printNode(ListNode head) {

        ListNode curNode = head;
        while (curNode != null) {
            System.out.print(curNode.val+ "-->");
            curNode = curNode.next;
        }

        System.out.println("Null");
    }

    /**206 反转链表
     * 反转一个链表需要三个指针 preNode curNode nextNode
     * 循环的结束条件 curNode == null
     * @param head
     * @return preNode
     */
    public ListNode reverseList(ListNode head) {

        ListNode preNode = null;
        ListNode curNode = head;

        while (curNode != null) {
            ListNode nextNode = curNode.next;
            curNode.next = preNode;
            preNode = curNode;
            curNode = nextNode;

        }

        return preNode;
    }

    /**
     * 203. 移除链表元素
     *1 使用虚拟头结点
     * 2 使用递归
     */
    public ListNode removeElements(ListNode head, int val) {

        if (head == null) {
            return null;
        }

        ListNode res = removeElements(head.next, val);

        if (head.val == val) {
            return res;
        } else {
            return head;
        }

    }

    public ListNode remove(ListNode head, int val) {


        ListNode dummyNode = new ListNode(0);
        dummyNode.next = head;

        ListNode curNode = dummyNode;
        while (curNode.next != null) {
            if (curNode.next.val == val) {
                curNode.next = curNode.next.next;
            } else {
                curNode = curNode.next;
            }

        }

        return dummyNode.next;
    }

    public static void main(String[] args) {
        ListNode node = createNode(new int[]{1, 2, 4, 6, 10, 2, 10});
        printNode(node);
        Solution solution = new Solution();
        ListNode reverseList = solution.removeElements(node, 10);
        printNode(reverseList);
    }
}
