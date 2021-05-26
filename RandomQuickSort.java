public class RandomQuickSort implements Sort {
    public void doSort(int[] array) {
        doQuickSort(array, 0, array.length - 1);
    }

    //creates partitions
    public void doQuickSort(int[] array, int start, int end) {
        if (start < end) {
            int pivot = start + (int) (Math.random() * (end - start));
            int partition = partition(array, start, end, pivot); //takes random pivot
            doQuickSort(array, start, partition - 1);
            doQuickSort(array, partition + 1, end);
        }
    }

    //does sort
    private int partition(int[] a, int start, int end, int pivot) {
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


        int i = start;
        for (int x = start; x < end; x++) {
            if (a[x] < piv) {
                swap(a, x, i);
                i = i + 1;
            }
        }
        swap(a, i, end);
        return i;
    }

    //swap helper method
    private static void swap(int[] arr, int index1, int index2) {
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;

    }
}