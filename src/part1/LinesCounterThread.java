package part1;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class LinesCounterThread extends Thread {
    String fileName ;
    AtomicInteger totalNumOfLines, numOfThreadFinished ;
    int linesNum ;
    public LinesCounterThread(String fileName, AtomicInteger totalNumOfLines, AtomicInteger numOfThreadFinished){
        this.fileName = fileName ;
        this.totalNumOfLines = totalNumOfLines ;
        this.numOfThreadFinished = numOfThreadFinished ;
        linesNum = 0 ;
    }
    @Override
    public void run() {
        super.run();
        File file = new File(fileName) ;
        try {
            if (!file.exists()) {
                throw new FileNotFoundException("File has not been found.");
            }
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while(bufferedReader.readLine() != null){
                linesNum++ ;
            }
            totalNumOfLines.addAndGet(linesNum) ;
            numOfThreadFinished.addAndGet(1) ;
            bufferedReader.close();
            fileReader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
