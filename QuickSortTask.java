import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class QuickSortTask extends RecursiveAction {
    int[] array;
    int start;
    int end;
    boolean random;

    public QuickSortTask(int[] _array, int _start, int _end, boolean _random) {
        array = _array;
        start = _start;
        end = _end;
        random = _random;
    } //QuickSortTask()

    private static int partition(int[] a, int start, int end, int pivot) //psuedocode from https://en.wikipedia.org/wiki/Quicksort
    {   //uses randomly selected pivot to estimate a more accurate (or perhaps natural is a better word) pivot point.
        //Put another way, we are using the random pivot to guess a good median value.
        // We then move this pivot point to the end.
        if (a[pivot] < a[start]) {
            swap(a, start, pivot);
        }
        if (a[end] < a[start]) {
            swap(a, end, start);
        }
        if (a[pivot] < a[end]) {
            swap(a, end, pivot);
        }
        double piv = a[end];

        //does quicksort().
        // i stores the index where every element to the left is less than the pivot
        // and every element to the right is greater than the pivot.
        int i = start;
        for (int x = start; x < end; x++) {
            if (a[x] < piv) {
                swap(a, x, i);
                i = i + 1;
            }
        }
        //swaps the pivot into place.
        swap(a, i, end);
        //returns the pivot's index. This will be used to further sort the left and right side of this partition.
        return i;
    } //partition()

    //helper method. Completes swaps.
    private static void swap(int[] arr, int index1, int index2) {
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;

    } //swap()

    @Override
    //Separates array into partitions, creates new tasks for each partition.
    protected void compute() {
        if (end - start <= 1000) {
            int[] temp = new int[(end - start) + 1];
            if (temp.length >= 0) System.arraycopy(array, start, temp, 0, temp.length);
            Arrays.sort(temp);
            if (temp.length >= 0) System.arraycopy(temp, 0, array, start, temp.length);
            return;
        }
        int pivot;
        if (random) {
            pivot = start + (int) (Math.random() * (end - start)); // takes random partition
        } else {
            pivot = start + (int) ((end - start) / 2.0); // takes mid partition

        }
        int partition = partition(array, start, end, pivot);
        QuickSortTask left = new QuickSortTask(array, start, partition - 1, random);
        left.fork();
        QuickSortTask right = new QuickSortTask(array, partition + 1, end, random);
        right.fork();

        left.join();
        right.join();

    } //compute()
}
