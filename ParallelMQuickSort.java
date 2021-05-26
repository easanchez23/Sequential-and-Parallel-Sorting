import java.util.concurrent.ForkJoinPool;

public class ParallelMQuickSort implements Sort {
    public void doSort(int[] array) {
        doParallelQuickSort(array, 0, array.length - 1);
    }

    //creates pool
    public void doParallelQuickSort(int[] array, int start, int end) {
        int N_THREADS = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(N_THREADS);
        pool.invoke(new QuickSortTask(array, start, end, false));
    }
}
