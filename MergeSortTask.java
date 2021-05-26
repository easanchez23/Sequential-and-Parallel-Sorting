import java.util.concurrent.RecursiveAction;

public class MergeSortTask extends RecursiveAction {
    int[] array;
    int start;
    int end;
    MergeSort sorter = new MergeSort();

    public MergeSortTask(int[] _array, int _start, int _end) {
        array = _array;
        start = _start;
        end = _end;
    }

    @Override
    //breaks array into partitions, creating a new fork task and then sorts once all subtasks have completed
    protected void compute() {
        if (end - start <= 1000) {
            int[] temp = new int[(end - start) + 1];
            if (temp.length >= 0) System.arraycopy(array, start, temp, 0, temp.length);
            sorter.doSort(temp);
            if (temp.length >= 0) System.arraycopy(temp, 0, array, start, temp.length);
            return;
        }
        int mid = (start + end) / 2;
        MergeSortTask left = new MergeSortTask(array, start, mid);
        MergeSortTask right = new MergeSortTask(array, mid + 1, end);

        left.fork();
        right.fork();

        left.join();
        right.join();

        MergeSort.sort(array, start, mid, end);


    }
}
