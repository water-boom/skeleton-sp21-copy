package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator; // 用于比较元素的比较器

    // 构造函数，使用给定的比较器初始化MaxArrayDeque
    public MaxArrayDeque(Comparator<T> c) {
        super(); // 调用父类ArrayDeque的构造函数
        this.comparator = c; // 初始化比较器
    }

    // 使用构造函数中提供的比较器返回队列中的最大元素
    public T max() {
        return max(this.comparator); // 调用带有比较器参数的max方法
    }

    // 使用给定的比较器返回队列中的最大元素
    public T max(Comparator<T> c) {
        if (this.isEmpty()) { // 如果队列为空，返回null
            return null;
        }
        T maxItem = this.get(0); // 假设第一个元素为最大元素
        for (int i = 1; i < this.size(); i++) { // 遍历队列中的所有元素
            T currentItem = this.get(i); // 获取当前元素
            if (c.compare(currentItem, maxItem) > 0) { // 如果当前元素大于最大元素
                maxItem = currentItem; // 更新最大元素
            }
        }
        return maxItem; // 返回最大元素
    }
}