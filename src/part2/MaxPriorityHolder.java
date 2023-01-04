package part2;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * MaxPriorityHolder is a synchronized class, it will hold the current max priority of the task instances waiting to
 * being executed in the queue.
 * Each time a task is being enqueued this class will be updated, and each time a task is being dequeued, this
 * class will be updated, and that will help to maintain the max priority at any given time.
 */
public class MaxPriorityHolder {
    private LinkedList<Integer> values;
    private Integer lowestValue;
    private ReentrantLock lock;

    /**
     * Constructor - initialize the elements.
     */
    public MaxPriorityHolder() {
        this.values = new LinkedList<Integer>();
        this.lowestValue = null;
        this.lock = new ReentrantLock();
    }

    /**
     * Adding values to the values linked list, if the current lowest priority is bigger then the given value,
     * replace the lowest priority with the value.
     * @param value - the value to add to the list.
     */
    public void addValue(Integer value) {
        lock.lock();
        try {
            values.add(value);
            if (lowestValue == null || value < lowestValue) {
                lowestValue = value;
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Removes a given value from the list. if the removed value is the lowest value, find the new lowest value
     * after removing it.
     * @param value - the value to be removed.
     * @return the value.
     */
    public Integer removeValue(Integer value) {
        lock.lock();
        try {
            values.remove(value);
            if (value == lowestValue) {
                lowestValue = null;
                for (Integer v : values) {
                    if (lowestValue == null || v < lowestValue) {
                        lowestValue = v;
                    }
                }
            }
            return value;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Get the current lowest value (Highest priority).
     * @return the lowest value.
     */
    public Integer getLowestValue() {
        lock.lock();
        try {
            return lowestValue;
        } finally {
            lock.unlock();
        }
    }
}
