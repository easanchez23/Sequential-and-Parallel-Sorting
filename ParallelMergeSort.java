import java.util.concurrent.ForkJoinPool;

public class ParallelMergeSort implements Sort {
    public void doSort(int[] array) {
        doParallelMergeSort(array, 0, array.length - 1);
    }

    //creates pool
    public void doParallelMergeSort(int[] array, int start, int end) {
        int N_THREADS = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(N_THREADS);
        pool.invoke(new MergeSortTask(array, start, end));
    }
}
