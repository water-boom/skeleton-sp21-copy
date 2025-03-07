package randomizedtest;

import timingtest.SLList;

public class testThreeAddThereRemove {
    public static void main(String[] args) {
        BuggyAList<Integer> b = new BuggyAList<>();
        AListNoResizing<Integer> a = new AListNoResizing<>();
        SLList<Integer> s = new SLList<>();
        for (int i = 0; i < 1000; i += 1) {
            a.addLast(i);
            s.addLast(i);
            b.addLast(i);
        }
        for (int i = 1; i < 1000; i *= 2) {
            System.out.println(a.get(i));
            System.out.println(b.get(i));
            System.out.println(s.get(i));
        }
    }

}
