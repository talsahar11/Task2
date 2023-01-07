package part1;

import java.io.*;
import java.util.concurrent.Callable;

/**
 * LinesCounterCallable implements the Callable interface.
 * The class propose is to calculate and return the number of lines of a text file.
 */
public class LinesCounterCallable implements Callable<Integer> {
    String fileName ;

    /**
     * Constructor
     * @param fileName - the file to scan the lines from.
     */
    public LinesCounterCallable(String fileName) {
        this.fileName = fileName ;
    }

    /**
     * The call method implemented from the Callable interface.
     * Create a file object and scan the number of lines from it, then return the number.
     * @return - the number of lines from the given text file.
     * @throws Exception - if the file could not be opened.
     */
    @Override
    public Integer call() {
        File file = new File(fileName);
        int linesNum = 0 ;
        try {
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
