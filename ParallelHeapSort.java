import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.lang.*;

public class ParallelHeapSort implements Sort {
    //creates task to currently add into priority blocking queue.
    //then removes sequentially
    public void doSort(int[] array) {
        PriorityBlockingQueue<Integer> heap = new PriorityBlockingQueue<>();
        int nThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(nThreads);
        List<Callable<String>> tasks = new ArrayList<Callable<String>>();
        int extra = 0;
        if (array.length % nThreads != 0) {
            extra = array.length % nThreads;
        }
        int range = array.length / nThreads;
        for (int i = 0; i < nThreads; i++) {
            int start;
            int end;
            if (i == nThreads - 1) {
                start = i * range;
                end = start + range + extra;
            } else {
                start = i * range;
                end = start + range;
            }
            tasks.add(new ParallelHeapSortTask(heap, start, end, array));
        }
        try { // waits for all tasks to finish
            pool.invokeAll(tasks);
        } catch (InterruptedException e) {

        }
        pool.shutdown();
        for (int i = 0; i < array.length; i++) {
            array[i] = heap.remove();

        }
    }
}
