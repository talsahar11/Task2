package part2;

import java.util.ArrayList;
import java.util.List;

public class PriorityQueue {
    private ArrayList<Task> queue ;

    public PriorityQueue(){
        queue = new ArrayList<>() ;
    }

    public void push(Task task){
        queue.add(binarySearch(task.getType()), task);
    }

    public Task pop(){
        Task t = queue.get(0) ;
        queue.remove(0) ;
        return t ;
    }

    private int binarySearch(int priority){
        int qSize = queue.size() ;
        if(qSize == 0){
            return 0 ;
        }
        int start = 0 , end = qSize ;
        int currentIndex = end / 2 ;
        while(start != end){
            if(queue.get(currentIndex).getType() < priority) {
                end -= Math.ceil((end-start) / 2f) ;
            }else if(queue.get(currentIndex).getType() > priority){
                start += Math.ceil((end-start) / 2f) ;
            }else{
                return currentIndex ;
            }
            currentIndex = start + (end-start)/2 ;
        }
        return currentIndex ;
    }

    public int getSize(){
        return queue.size();
    }
}
