package tests.ex2_1;

import org.junit.jupiter.api.Test;
import part1.Ex2_1;
import part1.LinesCounterCallable;
import part1.LinesCounterThread;

import java.io.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Ex2_1_Test {

    //---Test that each file created with a correct number of lines, less that the bound argument.
    @Test
    public void testNumberOfLinesBound() {
        int n = 10;
        int seed = 570;
        int bound = 100;
        String[] fileNames = Ex2_1.createTextFiles(n, seed, bound);
        assertEquals(n, fileNames.length);
        for (int i = 0; i < n; i++) {
            File currentFile = new File(fileNames[i]);
            assertTrue(currentFile.exists());
            int numOfLines = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
                while (reader.readLine() != null) {
                    numOfLines++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            assertTrue(numOfLines <= bound);
        }
        Ex2_1.cleanFiles();
    }

    //---Tests that the correct number of files is created---
    @Test
    void TestNumberOfFilesCreated(){
        int n = 28;
        int seed = 570;
        int bound = 100;
        Ex2_1.createTextFiles(n, seed, bound) ;
        String baseName = "file_" ;
        int fileNum = 0 ;
        File file = new File(baseName + fileNum) ;
        while(file.exists()){
            fileNum++ ;
            file = new File(baseName + fileNum) ;
        }
        assertEquals(n, fileNum);
        Ex2_1.cleanFiles();
    }

    @Test
    public void testGetNumOfLines() {
        int numOfFiles = 27;
        int seed = 207;
        int bound = 50;
        String[] fileNames = Ex2_1.createTextFiles(numOfFiles, seed, bound);
        int expectedNumOfLines = 0;
        for (int i = 0; i < numOfFiles; i++) {
            File currentFile = new File(fileNames[i]);
            try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
                while (reader.readLine() != null) {
                    expectedNumOfLines++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int actualNumOfLines = Ex2_1.getNumOfLines(fileNames);
        assertEquals(expectedNumOfLines, actualNumOfLines);
        Ex2_1.cleanFiles();
    }

    @Test
    public void testGetNumOfLinesThreads() {
        Ex2_1 ex2_1 = new Ex2_1() ;
        int numOfFiles = 60;
        int seed = 3090;
        int bound = 59;
        String[] fileNames = Ex2_1.createTextFiles(numOfFiles, seed, bound);
        int expectedNumOfLines = 0;
        for (int i = 0; i < numOfFiles; i++) {
            File currentFile = new File(fileNames[i]);
            try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
                while (reader.readLine() != null) {
                    expectedNumOfLines++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int actualNumOfLines = ex2_1.getNumOfLinesThreads(fileNames) ;
        assertEquals(expectedNumOfLines, actualNumOfLines);
        Ex2_1.cleanFiles();
    }

    @Test
    public void testGetNumOfLinesThreadPool() {
        Ex2_1 ex2_1 = new Ex2_1() ;
        int numOfFiles = 88;
        int seed = 5555;
        int bound = 79;
        String[] fileNames = Ex2_1.createTextFiles(numOfFiles, seed, bound);
        int expectedNumOfLines = 0;
        for (int i = 0; i < numOfFiles; i++) {
            File currentFile = new File(fileNames[i]);
            try (BufferedReader reader = new BufferedReader(new FileReader(currentFile))) {
                while (reader.readLine() != null) {
                    expectedNumOfLines++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int actualNumOfLines = ex2_1.getNumOfLinesThreadPool(fileNames) ;
        assertEquals(expectedNumOfLines, actualNumOfLines);
        Ex2_1.cleanFiles();
    }

    @Test
    void testCallable(){
        String fileName = "testFile.txt" ;
        File file = new File(fileName);
        int expectedNumOfLines = 587 ;
        String line = "Callable Test!" ;
        FileWriter fileWriter = null;
        try {
            file.createNewFile() ;
            fileWriter = new FileWriter(file.getName(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter printWriter = new PrintWriter(fileWriter) ;
        for(int j = 0 ; j < expectedNumOfLines ; j++){
            printWriter.println(line) ;
        }
        printWriter.close();
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ExecutorService threadPool = Executors.newFixedThreadPool(1) ;
        LinesCounterCallable callable = new LinesCounterCallable(fileName) ;
        Future<Integer> result = threadPool.submit(callable) ;
        Integer actualNumOfLines = null ;
        try {
            actualNumOfLines = result.get() ;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expectedNumOfLines, actualNumOfLines);
        file.delete() ;
        threadPool.shutdown();
    }

    @Test
    void testThread(){
        String fileName = "testFile.txt" ;
        File file = new File(fileName);
        int expectedNumOfLines = 587 ;
        String line = "Callable Test!" ;
        FileWriter fileWriter = null;
        try {
            file.createNewFile() ;
            fileWriter = new FileWriter(file.getName(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter printWriter = new PrintWriter(fileWriter) ;
        for(int j = 0 ; j < expectedNumOfLines ; j++){
            printWriter.println(line) ;
        }
        printWriter.close();
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AtomicInteger actualNumOfLines = new AtomicInteger(0) ;
        AtomicInteger numOfThreadFinished = new AtomicInteger(0) ;
        LinesCounterThread thread = new LinesCounterThread(fileName, actualNumOfLines, numOfThreadFinished) ;
        thread.start();
        while(numOfThreadFinished.get() < 1){

        }
        assertEquals(expectedNumOfLines, actualNumOfLines.get());
        file.delete() ;
    }
}
