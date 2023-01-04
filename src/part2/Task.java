package part2;

import java.util.concurrent.Callable;

/**
 * Task class implements the Callable interface.
 * This class is the only type that will be submitted to the ThreadPoolExecutor.
 * The CustomExecutor will wrap Callables with this class before submitting them to execute.
 * The instantiation will be available not directly but through the factory methods: CreateTask(Callable callable),
 * CreateTask(Callable callable, TaskType type).
 * @param <T> - A Generic type will be declared at the class Creation.
 */
public class Task<T> implements Callable<T> {
    static int taskNum = 0 ;
    private String taskName = null ;
    private TaskType type ;
    private Callable<T> callable ;

    /**
     * The call overridden method will trigger the call method of the callable provided at the instantiation.
     * @return A Generic Type which should be declared at instantiation.
     * @throws Exception - if the call method failed.
     */
    @Override
    public T call() throws Exception {
        return callable.call() ;
    }

    /**
     * Private constructor, for safety, can only be accessed from the createTask methods below.
     * @param callable - a callable instance.
     * @param type - the type of the callable.
     */
    private Task(Callable<T> callable, TaskType type){
        this.callable = callable ;
        this.type = type ;
        this.taskName = "Task - " + taskNum++ ;
    }

    /**
     * Factory method - create new task with a provided callable and taskType.
     * @param callable - the callable.
     * @param type - the taskType.
     * @return a new Task.
     */
    public static Task createTask(Callable callable, TaskType type){
        return new Task(callable, type) ;
    }

    /**
     * FactoryMethod - create new task with a provided callable, in this case, taskType is not provided, and the task
     * type is set to default (TaskType.Other).
     * @param callable - the callable.
     * @return a new Task.
     */
    public static Task createTask(Callable callable){
        return new Task(callable, TaskType.OTHER) ;
    }

    /**
     * Getter for the type of the Task instance.
     * @return An integer represents the priority.
     */
    public int getType() {
        return type.getPriorityValue();
    }

    /**
     * Getter for the name of the Task instance.
     * @return A String represents the name.
     */
    public String getTaskName() {
        return taskName;
    }
}
