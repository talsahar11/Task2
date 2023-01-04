package part2;

import java.util.concurrent.*;

/**
 * CustomExecutor extends the ThreadPoolExecutor class with extended features.
 * 1. The tasks are being executed from the queue by their priority, from lowest to highest.
 * 2. The submit method may receive:
 *         (Callable) - and then a task will be created with the default priority of 3 - (Other), and then will submittted.
 *         (Callable, TaskType) - and then a task will be created with the given priority, and then will submitted.
 *         (Task) - and then the task will be submitted as is.
 * 3. The executor core pool size will be half of the number of cores available to the JVM.
 * 4. The executor max pool size will be the number of cores available to the JVM - 1.
 * 5. The keep alive time of an idle threads will be defined as 300 Milliseconds.
 * 6. The max priority task in the queue is being maintained can be achieved at any given time by  the getMaxPriority()
 *    method.
 */
public class CustomExecutor extends ThreadPoolExecutor {
    private MaxPriorityHolder maxPriorityHolder = new MaxPriorityHolder() ;

    /**
     * Constructor - creates the CustomExecutor with the pre-defined: corePoolSize, maxPoolSize, keepAliveTime, and
     * Priority queue.
     */
    public CustomExecutor() {
        super(Runtime.getRuntime().availableProcessors()/2,
                Runtime.getRuntime().availableProcessors() - 1, 300, TimeUnit.MILLISECONDS
                , new PriorityBlockingQueue<>());
    }

    /**
     * Submits a task to the threadPool, if the given task is callable, wrap the callable with the Task class, and
     * submit it to the threadPool.
     * if the current active tasks is bigger or equals to the corePoolSize, the task will be sent to the queue,
     * so update the max priority if needed.
     * @param task - The task to submit, may be an instance of: Callable or Task.
     * @return Future - holds the return value of the given Task.
     */
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        if(!(task instanceof Task)){
            task = Task.createTask(task) ;
        }
        if(this.getCorePoolSize() <= this.getActiveCount()){
            Task t = (Task) task ;
            maxPriorityHolder.addValue(((Task<T>) task).getType());
        }
        return super.submit(task);
    }

    /**
     * Submits a task to the threadPool, handles the case the user wants to submit a Callable and a TaskType.
     * In that case, the method creates a new Task instance with the given Callable and TaskType, and then submits
     * it to the threadPool.
     * @param callable - The callable to submit.
     * @param type - The selected TaskType to the callable.
     * @return Future - holds the return value of the given Task.
     */
    public <T> Future<T> submit(Callable<T> callable, TaskType type) {
        Task<T> task = Task.createTask(callable, type) ;
        return super.submit(task);
    }

    /**
     * This function runs before the execution of a given task.
     * It is being overridden to remove the priority value of the task being executed, because this task was dequeued.
     * @param t - The executor thread.
     * @param r - The runnable(Task) about to run.
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        CustomFutureTask task = (CustomFutureTask) r ;
        maxPriorityHolder.removeValue(task.getPriority()) ;
    }

    /**
     * This method is overridden for returning a CustomFutureTask instead of FutureTask.
     * The need to return CustomFutureTask is that the PriorityQueue cannot compare two FutureTasks,
     * but ables to compare between two CustomFutureTasks, which implements the comparable interface.
     * @param callable - The submitted callable.
     * @return RunnableFuture - an interface that the CustomFutureTask implements.
     */
    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new CustomFutureTask<T>(callable) ;
    }

    /**
     * Get the current max priority of the queued task instances.
     * @return Integer holds the current max priority.
     *         If null - the queue is empty.
     */
    public Integer getMaxPriority(){
        return maxPriorityHolder.getLowestValue() ;
    }
}
