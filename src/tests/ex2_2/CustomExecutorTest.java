package tests.ex2_2;

import org.junit.jupiter.api.Test;
import part2.CustomExecutor;
import part2.CustomFutureTask;
import part2.Task;
import part2.TaskType;

import java.util.concurrent.*;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

public class CustomExecutorTest {
    private final CustomExecutor executor ;

    public CustomExecutorTest(){
        executor = new CustomExecutor() ;
    }

    @Test
    void testSubmit(){
        Future<String> futureT1 = executor.submit(()-> "Hello") ;
        Future<Integer> futureT2 = executor.submit(()-> 12, TaskType.COMPUTATIONAL) ;
        Task<Boolean> t3 = Task.createTask(()-> true, TaskType.IO) ;
        Future<Boolean> futureT3 = executor.submit(t3) ;
        try {
            assertEquals("Hello", futureT1.get());
            assertEquals(12, futureT2.get());
            assertEquals(true, futureT3.get());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testPriorityQueue() {
        Task<String> task0 = Task.createTask(() -> {
            sleep(1);
            return "Task 0 has been executed.";
        }, TaskType.OTHER);
        Task<Boolean> task1 = Task.createTask(() -> {
            sleep(1);
            return true;
        }, TaskType.COMPUTATIONAL);
        Task<String> task2 = Task.createTask(() -> {
            sleep(1);
            return "Task 2 has been executed";
        }, TaskType.IO);
        Task<Integer> task3 = Task.createTask(() -> {
            sleep(1);
            return 3;
        });
        Task<Float> task4 = Task.createTask(() -> {
            sleep(1);
            return 0.01223;
        });
        Task<Integer> task5 = Task.createTask(() -> {
            sleep(1);
            return 5;
        }, TaskType.COMPUTATIONAL);
        Task<Float> task6 = Task.createTask(() -> {
            sleep(1);
            return 0.333333;
        }, TaskType.IO);
        BlockingQueue queue = executor.getQueue() ;
        executor.submit(task0) ;
        executor.submit(task1) ;
        executor.submit(task2) ;
        executor.submit(task3) ;
        executor.submit(task4) ;
        executor.submit(task5) ;
        executor.submit(task6) ;
        executor.submit(task0) ;
        executor.submit(task1) ;
        executor.submit(task2) ;
        executor.submit(task3) ;
        executor.submit(task4) ;
        executor.submit(task5) ;
        executor.submit(task6) ;
        CustomFutureTask futureTask = null ;
        int currentPriority = 1 ;
        while(queue.peek() != null) {
            futureTask = (CustomFutureTask) queue.poll();
            assertTrue(futureTask.getPriority() >= currentPriority);
            currentPriority = futureTask.getPriority() ;
        }
    }

    @Test
    void testProperties(){
        int corePoolSize = Runtime.getRuntime().availableProcessors() / 2 ;
        int maxPoolSize = Runtime.getRuntime().availableProcessors() - 1 ;
        int keepAlive = 300 ;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS ;
        assertEquals(corePoolSize, executor.getCorePoolSize());
        assertEquals(maxPoolSize, executor.getMaximumPoolSize());
        assertEquals(keepAlive, executor.getKeepAliveTime(timeUnit));
    }

    @Test
    void testShutdown() throws InterruptedException {
        Task<String> task0 = Task.createTask(() -> {
            sleep(1);
            return "Task 0 has been executed.";
        }, TaskType.OTHER);
        Task<Boolean> task1 = Task.createTask(() -> {
            sleep(1);
            return true;
        }, TaskType.COMPUTATIONAL);
        Task<String> task2 = Task.createTask(() -> {
            sleep(1);
            return "Task 2 has been executed";
        }, TaskType.IO);
        Task<Integer> task3 = Task.createTask(() -> {
            sleep(1);
            return 3;
        });
        Task<Float> task4 = Task.createTask(() -> {
            sleep(1);
            return 0.01223;
        });
        Task<Integer> task5 = Task.createTask(() -> {
            sleep(1);
            return 5;
        }, TaskType.COMPUTATIONAL);
        Task<Float> task6 = Task.createTask(() -> {
            sleep(1);
            return 0.333333;
        }, TaskType.IO);
        Task<Double> task7 = Task.createTask(() -> {
            sleep(1);
            return 0.333333;
        }, TaskType.IO);
        Task<StringBuilder> task8 = Task.createTask(() -> {
            sleep(1);
            return new StringBuilder("Hello") ;
        }, TaskType.OTHER);
        Task<String> task9 = Task.createTask(() -> {
            sleep(1);
            return "Hello";
        }, TaskType.COMPUTATIONAL);


        Future<String> future0 = executor.submit(task0) ;
        Future<Boolean> future1 = executor.submit(task1) ;
        Future<String> future2 = executor.submit(task2) ;
        Future<Integer> future3 = executor.submit(task3) ;
        Future<Float> future4 = executor.submit(task4) ;
        Future<Integer> future5 = executor.submit(task5) ;
        Future<Float> future6 = executor.submit(task6) ;
        Future<Double> future7 = executor.submit(task7) ;
        Future<StringBuilder> future8 = executor.submit(task8) ;

        executor.shutdown();
        assertThrows(RejectedExecutionException.class, () -> {
            executor.submit(task9) ;
        });
        Thread.sleep(5);
        assertTrue(future0.isDone());
        assertTrue(future1.isDone());
        assertTrue(future2.isDone());
        assertTrue(future3.isDone());
        assertTrue(future4.isDone());
        assertTrue(future5.isDone());
        assertTrue(future6.isDone());
        assertTrue(future7.isDone());
        assertTrue(future8.isDone());
        assertTrue(executor.isShutdown());
    }
}
