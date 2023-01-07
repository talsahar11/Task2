package part1;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * LinesCounterThread extends Thread, and its propose is to calculate the number of lines in the text file.
 * The number of lines will then be added into an atomic integer provided in the struct of the instance.
 */
public class LinesCounterThread extends Thread{
    String fileName ;
    AtomicInteger totalNumOfLines, numOfThreadFinished ;
    int linesNum ;

    /**
     * Constructor - initialize the variables used in the thread.
     * @param fileName - The file to read from.
     * @param totalNumOfLines - An atomic integer holds the total number of lines from all the files.
     * @param numOfThreadFinished - An atomic integer, increased by 1 each time a thread finish his job, used to tell
     *                              when all files have been read.
     */
    public LinesCounterThread(String fileName, AtomicInteger totalNumOfLines, AtomicInteger numOfThreadFinished){
        this.fileName = fileName ;
        this.totalNumOfLines = totalNumOfLines ;
        this.numOfThreadFinished = numOfThreadFinished ;
        linesNum = 0 ;
    }

    /**
     * Run method of the thread - calculates the number of lines in the provided file, and then add the number to the
     * total lines variable. After adding the number of lines, add 1 to the number of threads finished.
     */
    @Override
    public void run() {
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
