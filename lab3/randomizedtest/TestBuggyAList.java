package randomizedtest;

import afu.org.checkerframework.checker.igj.qual.I;
import edu.princeton.cs.algs4.StdRandom;
import net.sf.saxon.om.Item;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> correct=new AListNoResizing<>();
        BuggyAList<Integer> broken =new BuggyAList<>();
        correct.addLast(5);
        correct.addLast(20);
        correct.addLast(10);

        broken.addLast(5);
        broken.addLast(20);
        broken.addLast(10);

        assertEquals(correct.size(),broken.size());

        assertEquals(correct.removeLast(), broken.removeLast());
        assertEquals(correct.removeLast(), broken.removeLast());
        assertEquals(correct.removeLast(), broken.removeLast());
    }
    @Test
    public void JUnit() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);

            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                correct.addLast(randVal);
                broken.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");

            } else if (operationNumber == 1) {
                // size
                int correct_size = correct.size();
                int broken_size=broken.size();
                assertEquals("Size mismatch after operation " + i,correct_size,broken_size);
                System.out.println("size: " + correct_size);

            }else if (operationNumber == 2 && correct.size() > 0) {
                // getLast - 仅当列表非空时执行
                Integer correctLast = correct.getLast();
                Integer brokenLast = broken.getLast();
                assertEquals("getLast mismatch after operation " + i, correctLast, brokenLast);
                System.out.println("getLast: " + correctLast);

            }else if (operationNumber == 3 && correct.size() > 0) {
                Integer correctRemoved = correct.removeLast();
                Integer brokenRemoved = broken.removeLast();
                assertEquals("removeLast mismatch after operation " + i, correctRemoved, brokenRemoved);
                System.out.println("removeLast: " + correctRemoved);
            }
            assertEquals("Size mismatch after operation " + i, correct.size(), broken.size());
        }
    }
}
