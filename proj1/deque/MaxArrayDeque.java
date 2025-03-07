package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;

    /**
     * Creates a MaxArrayDeque with the given Comparator.
     * @param c the Comparator to use for finding the maximum element
     */
    public MaxArrayDeque(Comparator<T> c) {
        super();
        this.comparator = c;
    }

    /**
     * Returns the maximum element in the deque as governed by the previously given Comparator.
     * If the MaxArrayDeque is empty, simply return null.
     * @return the maximum element in the deque, or null if the deque is empty
     */
    public T max() {
        return max(this.comparator);
    }

    /**
     * Returns the maximum element in the deque as governed by the parameter Comparator c.
     * If the MaxArrayDeque is empty, simply return null.
     * @param c the Comparator to use for finding the maximum element
     * @return the maximum element in the deque, or null if the deque is empty
     */
    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T maxElement = get(0);
        for (int i = 1; i < size(); i++) {
            T currentElement = get(i);
            if (c.compare(currentElement, maxElement) > 0) {
                maxElement = currentElement;
            }
        }
        return maxElement;
    }
}