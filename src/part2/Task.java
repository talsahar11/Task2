package part2;

import java.util.concurrent.Callable;

public class Task<T> implements Callable<T>, Comparable {
    private Operation<T> operation ;
    private TaskType type ;
    private Task(Operation<T> operation, TaskType type){
        this.operation = operation ;
        this.type = type ;
    }
    private Task(Operation<T> operation){
        this.operation = operation ;
        this.type = TaskType.OTHER ;
    }
    public static Task createTask(Operation operation, TaskType type){
        return new Task(operation, type) ;
    }
    public static Task createTask(Operation operation){
        return new Task(operation) ;
    }
    @Override
    public T call() throws Exception {
        return operation.run();
    }

    public int getType() {
        return type.getPriorityValue();
    }


    @Override
    public int compareTo(Object o) {
        Task otherTask = (Task) o ;
        if(this.getType() > otherTask.getType()){
            return 1 ;
        }else if(this.getType() < otherTask.getType()){
            return -1 ;
        }
        return 0;
    }
}
