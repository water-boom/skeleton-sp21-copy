package deque;

public class LinkedListDeque<T> implements Deque<T> {
    public class Node {
        T value; // 节点存储的值
        Node next; // 指向下一个节点
        Node prev; // 指向前一个节点

        public Node(T value) {
            this.value = value; // 初始化节点值
        }
    }

    private Node sentinel; // 哨兵节点
    private int size; // 链表的大小

    public LinkedListDeque() {
        sentinel = new Node(null); // 创建哨兵节点
        sentinel.next = sentinel; // 哨兵节点指向自身
        sentinel.prev = sentinel; // 哨兵节点指向自身
        size = 0; // 初始大小为 0
    }

    @Override
    public void addFirst(T value) {
        Node newNode = new Node(value); // 创建新节点
        newNode.next = sentinel.next; // 新节点指向当前第一个节点
        newNode.prev = sentinel; // 新节点的前指针指向哨兵节点
        sentinel.next.prev = newNode; // 当前第一个节点的前指针指向新节点
        sentinel.next = newNode; // 哨兵节点的下一个指针指向新节点
        size++; // 更新大小
    }

    @Override
    public void addLast(T value) {
        Node newNode = new Node(value); // 创建新节点
        newNode.prev = sentinel.prev; // 新节点的前指针指向当前最后一个节点
        newNode.next = sentinel; // 新节点的后指针指向哨兵节点
        sentinel.prev.next = newNode; // 当前最后一个节点的下一个指针指向新节点
        sentinel.prev = newNode; // 哨兵节点的前指针指向新节点
        size++; // 更新大小
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null; // 如果链表为空，返回 null
        }
        Node firstNode = sentinel.next; // 获取第一个节点
        sentinel.next = firstNode.next; // 移动哨兵节点的指针
        firstNode.next.prev = sentinel; // 更新第二个节点的前指针
        size--; // 更新大小
        return firstNode.value; // 返回删除的值
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null; // 如果链表为空，返回 null
        }
        Node lastNode = sentinel.prev; // 获取最后一个节点
        sentinel.prev = lastNode.prev; // 更新哨兵节点的前指针
        lastNode.prev.next = sentinel; // 更新倒数第二个节点的下一个指针
        size--; // 更新大小
        return lastNode.value; // 返回删除的值
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) return null; // 索引越界，返回 null
        Node current = sentinel.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public int size() {
        return size; // 返回当前大小
    }

    // 获取指定索引的节点值（递归方式）
    public T getRecursive(int index) {
        if (index < 0 || index >= size) return null; // 索引越界，返回 null
        return getRecursiveHelper(sentinel.next, index); // 调用辅助方法
    }

    private T getRecursiveHelper(Node node, int index) {
        if (index == 0) {
            return node.value; // 返回当前节点值
        }
        return getRecursiveHelper(node.next, index - 1); // 继续递归
    }

    // 打印链表的内容
    public void printDeque() {
        Node current = sentinel.next; // 从第一个节点开始
        while (current != sentinel) { // 遇到哨兵节点停止
            System.out.print(current.value + " "); // 打印当前节点值
            current = current.next; // 移动到下一个节点
        }
        System.out.println();
    }

    // 测试
    public static void main(String[] args) {
        LinkedListDeque<Integer> deque = new LinkedListDeque<>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(3);
        deque.printDeque(); // 输出: 3 1 2

        System.out.println("Removed from front: " + deque.removeFirst()); // 输出: Removed from front: 3
        deque.printDeque(); // 输出: 1 2

        System.out.println("Remove last: " + deque.removeLast()); // 输出: Remove last: 2
        deque.printDeque(); // 输出: 1
        System.out.println("Get element at index 0 using recursion: " + deque.getRecursive(0)); // 输出: Get element at index 0 using recursion: 1
    }
}