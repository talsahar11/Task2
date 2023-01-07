package tests.ex2_2;

import org.junit.jupiter.api.Test;
import part2.CustomFutureTask;
import part2.Task;
import part2.TaskType;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomFutureTaskTest {
    private final Task<Integer> task1 ;
    private final Task<String> task2 ;
    private final Task<Boolean> task3 ;
    public CustomFutureTaskTest(){
        task1 = Task.createTask(()-> 15) ;

        task2 = Task.createTask(() -> "Hello to the world!", TaskType.COMPUTATIONAL) ;

        task3 = Task.createTask(() -> true, TaskType.COMPUTATIONAL) ;
    }
    @Test
    void testEquals(){
        CustomFutureTask<Integer> futureTask1 = new CustomFutureTask<>(task1) ;
        CustomFutureTask<String> futureTask2 = new CustomFutureTask<>(task2) ;
        CustomFutureTask<Boolean> futureTask3 = new CustomFutureTask<>(task3) ;

        assertEquals(1, futureTask1.compareTo(futureTask2));
        assertEquals(-1, futureTask2.compareTo(futureTask1));
        assertEquals(0, futureTask2.compareTo(futureTask3));
    }

    @Test
    void testCall(){
        CustomFutureTask<Integer> futureTask1 = new CustomFutureTask<>(task1) ;
        CustomFutureTask<String> futureTask2 = new CustomFutureTask<>(task2) ;
        CustomFutureTask<Boolean> futureTask3 = new CustomFutureTask<>(task3) ;

        ExecutorService threadPool = Executors.newFixedThreadPool(5) ;

        threadPool.submit(futureTask1) ;
        threadPool.submit(futureTask2) ;
        threadPool.submit(futureTask3) ;

        try {
            assertEquals(15, futureTask1.get());
            assertEquals("Hello to the world!", futureTask2.get());
            assertEquals(true, futureTask3.get());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
