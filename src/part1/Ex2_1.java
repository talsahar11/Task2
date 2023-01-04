package part1;

import java.io.*;
import java.util.ArrayList;
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
                if(currentFile.exists()){
                    currentFile.delete() ;
                }
                currentFile.createNewFile() ;
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
        return fileNames ;
    }
    public static int getNumOfLines(String[] fileNames){
        long startTime = System.currentTimeMillis() ;
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
        long endTime = System.currentTimeMillis() ;
        long executionTime = endTime - startTime ;
        System.out.println("The getNumOfLines method runs in: " + executionTime + " Milliseconds.");
        return numOfLines ;
    }
    public int getNumOfLinesThreads(String[] fileNames){
        long startTime = System.currentTimeMillis() ;
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
        long endTime = System.currentTimeMillis() ;
        long executionTime = endTime - startTime ;
        System.out.println("The getNumOfLinesThreads method runs in: " + executionTime + " Milliseconds.");
        return atomicInt.get() ;
    }
    public int getNumOfLinesThreadPool(String[] fileNames){
        long startTime = System.currentTimeMillis() ;
        int totalNumOfLines = 0 ;
        int numOfFiles = fileNames.length ;
        ExecutorService threadPool = Executors.newFixedThreadPool(numOfFiles) ;
        List<Future<Integer>> futureList = new ArrayList<>();
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
        long endTime = System.currentTimeMillis() ;
        long executionTime = endTime - startTime ;
        System.out.println("The getNumOfLinesThreadPool method runs in: " + executionTime + " Milliseconds.");
        return totalNumOfLines ;
    }
    
    public void cleanFiles(){
        String name = "file_" ;
        int i = 0 ;
        boolean keepGoing = true ;
        File file = null ;
        while(keepGoing){
            file = new File(name + i) ;
            if(!file.exists()){
                keepGoing = false ;
            }else{
                file.delete() ;
            }
            i++ ;
        }
        System.out.println("Cleaned: " + i + "Files.");
    }
}
