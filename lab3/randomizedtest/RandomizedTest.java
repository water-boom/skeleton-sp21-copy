package randomizedtest;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedTest {

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();

        int N = 500;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                System.out.println("size: " + size);
            }else if (operationNumber == 2) {
                // getLast
                if (L.size() == 0) {
                    System.out.println("List is empty");
                } else {
                    int last = L.getLast();
                    System.out.println("getLast: " + last);
                }
            } else if(operationNumber == 3) {
                // removeLast
                if (L.size() == 0) {
                    System.out.println("List is empty");
                }
            }
        }
    }
}