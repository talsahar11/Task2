package tests.ex2_2;

import org.junit.jupiter.api.Test;
import part2.MaxPriorityHolder;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxPriorityHolderTest {
    MaxPriorityHolder maxPriorityHolder = new MaxPriorityHolder() ;
    @Test
    void testAdd() {
        maxPriorityHolder.addValue(6);
        maxPriorityHolder.addValue(3);
        maxPriorityHolder.addValue(8);
        maxPriorityHolder.addValue(9);
        maxPriorityHolder.addValue(3);
        maxPriorityHolder.addValue(2);
        maxPriorityHolder.addValue(2);
        maxPriorityHolder.addValue(0);
        maxPriorityHolder.addValue(10);
        assertEquals(0, maxPriorityHolder.getLowestValue());
    }

    @Test
    void testRemove(){
        maxPriorityHolder.addValue(4);
        maxPriorityHolder.addValue(8);
        maxPriorityHolder.addValue(3);
        maxPriorityHolder.addValue(7);
        maxPriorityHolder.addValue(2);
        maxPriorityHolder.addValue(2);
        maxPriorityHolder.addValue(2);
        maxPriorityHolder.addValue(12);
        maxPriorityHolder.addValue(4);
        maxPriorityHolder.removeValue(2) ;
        maxPriorityHolder.removeValue(2) ;
        assertEquals(2, maxPriorityHolder.getLowestValue());
    }

    @Test
    void testGetLowest(){
        maxPriorityHolder.addValue(8);
        assertEquals(8, maxPriorityHolder.getLowestValue());
        maxPriorityHolder.addValue(10);
        assertEquals(8, maxPriorityHolder.getLowestValue());
        maxPriorityHolder.addValue(1);
        assertEquals(1, maxPriorityHolder.getLowestValue());
        maxPriorityHolder.addValue(1);
        assertEquals(1, maxPriorityHolder.getLowestValue());
        maxPriorityHolder.removeValue(1) ;
        assertEquals(1, maxPriorityHolder.getLowestValue());
        maxPriorityHolder.removeValue(1) ;
        assertEquals(8, maxPriorityHolder.getLowestValue());
        maxPriorityHolder.addValue(4);
        assertEquals(4, maxPriorityHolder.getLowestValue());



    }
}
