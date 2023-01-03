package part1;

import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class Ex2_1 {
    public static String[] createTextFiles(int n, int seed, int bound) {

        int sumLines = 0 ;

        File currentFile = null ;
        FileWriter fileWriter = null ;
        PrintWriter printWriter = null ;
        Random rand = new Random(seed) ;
        int numberOfLines = 0 ;
        String[] fileNames = new String[n] ;
        String currentFileName = "" ;
        String line = "Hello to the OOP world." ;
        for(int i = 0 ; i < n ; i++){
            currentFileName = "file_" + i ;
            currentFile = new File(currentFileName) ;
            try {
                if(!currentFile.exists()){
                    currentFile.createNewFile() ;
                }else{
                    throw new IOException("File already exists. couldn't make file with name: " + currentFileName) ;
                }
                fileWriter = new FileWriter(currentFile.getName(), true) ;
                printWriter = new PrintWriter(fileWriter) ;
                numberOfLines = rand.nextInt(bound) ;
                sumLines += numberOfLines ;
                for(int j = 0 ; j < numberOfLines ; j++){
                    printWriter.println(line) ;
                }
                fileNames[i] = currentFileName ;
                printWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Lines: " + sumLines);
        return fileNames ;
    }
    public static int getNumOfLines(String[] fileNames){
        File currentFile = null ;
        FileReader fileReader = null ;
        BufferedReader bufferedReader = null ;
        int numOfLines = 0 ;
        for(int i = 0 ; i < fileNames.length ; i++){
            currentFile = new File(fileNames[i]) ;
            try {
                if(!currentFile.exists()){
                    throw new FileNotFoundException("The file provided has not been found.") ;
                }else {
                    fileReader = new FileReader(currentFile);
                    bufferedReader = new BufferedReader(fileReader) ;
                    while(bufferedReader.readLine() != null){
                        numOfLines++ ;
                    }
                    bufferedReader.close();
                    fileReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return numOfLines ;
    }
    public int getNumOfLinesThreads(String[] fileNames){
        LinesCounterThread currentThread = null ;
        AtomicInteger atomicInt = new AtomicInteger(0) ;
        AtomicInteger numOfThreadsFinished = new AtomicInteger(0) ;
        int numOfFiles = fileNames.length ;
        for(String fileName: fileNames){
            currentThread = new LinesCounterThread(fileName, atomicInt, numOfThreadsFinished) ;
            currentThread.start();
        }
        while(numOfThreadsFinished.get() != numOfFiles){

        }
        return atomicInt.get() ;
    }
    public int getNumOfLinesThreadPool(String[] fileNames){
        int totalNumOfLines = 0 ;
        int numOfFiles = fileNames.length ;
        ExecutorService threadPool = Executors.newFixedThreadPool(numOfFiles) ;
        List<Future<Integer>> futureList = null ;
        for(int i = 0 ; i < numOfFiles ; i++){
            LinesCounterCallable task = new LinesCounterCallable(fileNames[i]) ;
            futureList.add(threadPool.submit(task)) ;
        }
        for(int i = 0 ; i < numOfFiles ; i++){
            try {
                totalNumOfLines += futureList.get(i).get() ;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        threadPool.shutdown();
        return totalNumOfLines ;
    }
}
