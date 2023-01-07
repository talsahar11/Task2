package tests.ex2_2;

import org.junit.jupiter.api.Test;
import part2.Task;
import part2.TaskType;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TaskTest {
    @Test
    void CreateTaskTest(){
        Task<Integer> task1 = Task.createTask(()-> 86) ;
        Task<String> task2 = Task.createTask(() -> "Hello to the world!", TaskType.IO) ;

        ExecutorService threadPool = Executors.newFixedThreadPool(5) ;
        Future<Integer> res1 = threadPool.submit(task1) ;
        Future<String> res2 = threadPool.submit(task2) ;
        try {
            assertEquals(86, res1.get());
            assertEquals("Hello to the world!", res2.get());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertEquals(3, task1.getType());
        assertEquals(2, task2.getType());

        assertEquals("Task - 0", task1.getTaskName());
        assertEquals("Task - 1", task2.getTaskName());
    }
}
