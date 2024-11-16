package deque;

public class ArrayDeque<T> {
    private T[] items; // 存储双端队列元素的数组
    private int size; // 双端队列中元素的数量
    private int front; // 指向双端队列前端的索引
    private int back; // 指向双端队列后端的索引

    // 构造函数，初始化一个大小为8的空双端队列
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        front = 0;
        back = 1;
    }

    // 在双端队列前端添加一个元素
    public void addFirst(T item) {
        if (size == items.length) {
            resize(items.length * 2); // 如果数组已满，扩容为原来的两倍
        }
        front = (front - 1 + items.length) % items.length; // 更新前端索引，循环数组
        items[front] = item; // 在前端位置添加元素
        size += 1; // 更新元素数量
    }

    // 在双端队列后端添加一个元素
    public void addLast(T item) {
        if (size == items.length) {
            resize(items.length * 2); // 如果数组已满，扩容为原来的两倍
        }
        items[back] = item; // 在后端位置添加元素
        back = (back + 1) % items.length; // 更新后端索引，循环数组
        size += 1; // 更新元素数量
    }

    // 从双端队列前端移除并返回一个元素
    public T removeFirst() {
        if (isEmpty()) {
            return null; // 如果双端队列为空，返回null
        }
        T item = items[front]; // 获取前端元素
        items[front] = null; // 将前端位置置空
        front = (front + 1) % items.length; // 更新前端索引，循环数组
        size -= 1; // 更新元素数量
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2); // 如果元素数量小于数组长度的四分之一，缩小数组
        }
        return item; // 返回移除的元素
    }

    // 从双端队列后端移除并返回一个元素
    public T removeLast() {
        if (isEmpty()) {
            return null; // 如果双端队列为空，返回null
        }
        back = (back - 1 + items.length) % items.length; // 更新后端索引，循环数组
        T item = items[back]; // 获取后端元素
        items[back] = null; // 将后端位置置空
        size -= 1; // 更新元素数量
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2); // 如果元素数量小于数组长度的四分之一，缩小数组
        }
        return item; // 返回移除的元素
    }

    // 获取指定索引处的元素
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null; // 如果索引无效，返回null
        }
        return items[(front + index) % items.length]; // 返回指定索引处的元素
    }

    // 返回双端队列中的元素数量
    public int size() {
        return size;
    }

    // 调整数组大小
    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity]; // 创建一个新的数组
        for (int i = 0; i < size; i++) {
            a[i] = items[(front + i) % items.length]; // 将旧数组中的元素复制到新数组
        }
        items = a; // 更新数组引用
        front = 0; // 重置前端索引
        back = size; // 更新后端索引
    }

    // 检查双端队列是否为空
    boolean isEmpty() {
        return size == 0;
    }
}