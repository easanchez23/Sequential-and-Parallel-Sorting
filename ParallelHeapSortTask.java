import java.util.concurrent.Callable;
import java.util.concurrent.PriorityBlockingQueue;

public class ParallelHeapSortTask  implements Callable<String> {
    PriorityBlockingQueue<Integer> a;
    int start;
    int end;
    int[] array;

    public ParallelHeapSortTask(PriorityBlockingQueue<Integer> a, int s, int e, int[] array) {
        this.a = a;
        this.start = s;
        this.end = e;
        this.array = array;
    }
    // adds into priority queue
    public String call() {
            for (int i = start; i < end; i++) {
                a.add(array[i]);
            }
            return "done";
    }
}
