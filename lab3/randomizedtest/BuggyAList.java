package randomizedtest;

/** 基于数组的列表类。
 *  作者：Josh Hug
 */

//         0 1  2 3 4 5 6 7
// items: [6 9 -1 2 0 0 0 0 ...]
// size: 5

/* 不变量：
 addLast: 我们想要添加的下一个项目将进入位置 size
 getLast: 我们想要返回的项目在位置 size - 1
 size: 列表中的项目数量应该是 size。
*/

public class BuggyAList<Item> {
    private Item[] items;
    private int size;

    /** 创建一个空列表。 */
    public BuggyAList() {
        items = (Item[]) new Object[8]; // 初始容量为 8
        size = 0;
    }

    /** 将底层数组的大小调整为目标容量。 */
    private void resize(int capacity) {
        Item[] a = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i += 1) {
            a[i] = items[i];
        }
        items = a;
    }

    /** 将 X 插入到列表的末尾。 */
    public void addLast(Item x) {
        if (size == items.length) {
            resize(size * 2); // 如果数组已满，则将其大小加倍
        }
        items[size] = x;
        size = size + 1;
    }

    /** 返回列表末尾的项目。 */
    public Item getLast() {
        return items[size - 1];
    }

    /** 获取列表中的第 i 个项目（0 是前面）。 */
    public Item get(int i) {
        return items[i];
    }

    /** 返回列表中的项目数量。 */
    public int size() {
        return size;
    }

    /** 删除列表末尾的项目并返回删除的项目。 */
    public Item removeLast() {
        Item x = getLast();
        items[size - 1] = null;
        size = size - 1;
        if ((size > 0) && (size == items.length / 4)) {
            resize(items.length / 2); // 如果大小是数组长度的四分之一，则将其大小减半
        }
        return x;
    }
}