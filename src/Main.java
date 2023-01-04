import part1.Ex2_1;

public class Main {
    public static void main(String[] args) throws Exception {
        Ex2_1 test = new Ex2_1() ;
//        String[] fileNames = Ex2_1.createTextFiles(1000, 2122, 99999) ;
//        int numberOfLines = Ex2_1.getNumOfLines(fileNames) ;
//        System.out.println("Number Of Lines: " + numberOfLines);
//        int numberOfLinesThreads = test.getNumOfLinesThreads(fileNames) ;
//        System.out.println("Number of lines by threads: " + numberOfLinesThreads);
//        int numberOfLinesThreadPool = test.getNumOfLinesThreadPool(fileNames) ;
//        System.out.println("Number of lines by thread pool: " + numberOfLinesThreadPool);
          test.cleanFiles();
//        CustomCallable<Integer> task = CustomCallable.createTask(()-> {
//            int sum = 0 ;
//            for(int i = 0 ; i < 10 ; i++){
//                sum += 10 ;
//            }
//            return sum ;
//        }, TaskType.COMPUTATIONAL) ;
//        CustomExecutor executor = new CustomExecutor() ;
//        Future<Integer> numFuture = executor.submit(task) ;
//        System.out.println(numFuture.get()) ;
//        System.out.println(task.call()) ;

    }

}