package part2;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * CustomFutureTask extends the FutureTask class and implements the Comparable interface.
 * The class propose is to enable to the PriorityQueue in the ThreadPool to compare between the FutureTasks queued in
 * it by the priority of the original Task class it wrapping.
 * @param <T> A Generic type will be declared at the class Creation.
 */
public class CustomFutureTask<T> extends FutureTask<T> implements Comparable<CustomFutureTask<T>> {
    private final int priority ;
    private final String name ;

    /**
     * Constructor - create a new CustomFutureTask, set the priority and the task name of the given task.
     * @param callable - the given Task.
     */
    public CustomFutureTask(Callable<T> callable) {
        super(callable);
        Task<T> task = (Task<T>) callable ;
        this.priority = task.getType() ;
        this.name = task.getTaskName() ;
    }

    /**
     * Getter for the priority of the instance of this class.
     * @return int - the priority.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Getter for the name of the instance of this class.
     * @return String - the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Compares between the current instance and another given CustomFutureTask instance by their priority.
     * The lower priority value - is the most priority task.
     * @param other - The other instance to compare to.
     * @return - 0: if priorities are equal.
     *         - -1: if current priority is less than the other's priority.
     *         - 1: if the current priority is more than the other's priority.
     */
    @Override
    public int compareTo(CustomFutureTask other) {
        return Integer.compare(this.priority, other.getPriority()) ;
    }
}
