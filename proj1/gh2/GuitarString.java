package gh2;

import deque.Deque;
import deque.LinkedListDeque;

// Note: This file will not compile until you complete the Deque implementations
public class GuitarString {
    /** 常量。不要更改。 */
    private static final int SR = 44100;      // 采样率
    private static final double DECAY = .996; // 能量衰减因子

    /* 用于存储声音数据的缓冲区。 */
    private Deque<Double> buffer;

    /* 创建给定频率的吉他弦。 */
    public GuitarString(double frequency) {
        int capacity = (int) Math.round(SR / frequency); // 计算缓冲区容量
        buffer = new LinkedListDeque<>(); // 使用 LinkedListDeque 初始化缓冲区
        for (int i = 0; i < capacity; i++) {
            buffer.addLast(0.0); // 用零填充缓冲区
        }
    }

    /* 通过用白噪声替换缓冲区来拨动吉他弦。 */
    public void pluck() {
        int size = buffer.size();
        for (int i = 0; i < size; i++) {
            buffer.removeFirst(); // 清空缓冲区
            double r = Math.random() - 0.5; // 生成 -0.5 到 0.5 之间的随机数
            buffer.addLast(r); // 用随机数替换
        }
    }

    /* 通过执行 Karplus-Strong 算法的一次迭代来推进模拟一个时间步。 */
    public void tic() {
        double firstSample = buffer.removeFirst(); // 移除前面的样本
        double secondSample = buffer.get(0); // 获取下一个样本
        double newSample = DECAY * 0.5 * (firstSample + secondSample); // 计算新样本
        buffer.addLast(newSample); // 将新样本加入缓冲区
    }

    /* 返回缓冲区前面的 double 值。 */
    public double sample() {
        return buffer.get(0); // 返回前面的样本
    }
}