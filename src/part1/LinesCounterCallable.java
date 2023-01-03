package part1;

import java.io.*;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class LinesCounterCallable implements Callable<Integer> {
    String fileName ;

    public LinesCounterCallable(String fileName) {
        this.fileName = fileName ;
    }
    @Override
    public Integer call() throws Exception {
        File file = new File(fileName);
        int linesNum = 0 ;
        try {
            if (!file.exists()) {
                throw new FileNotFoundException("File has not been found.");
            }
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (bufferedReader.readLine() != null) {
                linesNum++;
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linesNum ;
    }
}
