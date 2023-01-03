package part2;

import java.util.concurrent.*;

public class CustomExecutor {
    private BlockingQueue taskQueue ;
    private ExecutorService threadPool ;
    int coreNumOfThreads, maxNumOfThreads ;
    public CustomExecutor(){
        taskQueue = new PriorityBlockingQueue();
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        maxNumOfThreads = availableProcessors - 1 ;
        coreNumOfThreads = availableProcessors / 2 ;
        threadPool = new ThreadPoolExecutor(coreNumOfThreads, maxNumOfThreads, 300, TimeUnit.MILLISECONDS, taskQueue) ;
    }
    public Future submit(Task task){
        return threadPool.submit(task) ;

    }
    public Future submit(Operation operation){
        Task task = Task.createTask(operation) ;
        return threadPool.submit(task) ;
    }
    public Future submit(Operation operation, TaskType type){
        Task task = Task.createTask(operation, type) ;
        return threadPool.submit(task) ;
    }
}
