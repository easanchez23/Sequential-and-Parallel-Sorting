import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelInsertionSort implements Sort {
    // creates tasks to add into NonBlockingList
    public void doSort(int[] array) {
        NonblockingList<Integer> list = new NonblockingList<>();
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
            tasks.add(new ParallelInsertionTask(array, start, end, list));
        }
        try { // waits for all tasks to finish
            pool.invokeAll(tasks);
        } catch (InterruptedException e) {

        }
        pool.shutdown();
        NonblockingList<Integer>.Node node = list.head.next.getReference();
        for (int i = 0; i < array.length; i++) {
            array[i] = node.item;
            node = node.next.getReference();
        }
    }

}