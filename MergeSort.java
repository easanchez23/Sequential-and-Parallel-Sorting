public class MergeSort implements Sort {

    public void doSort(int[] array) {
        doMerge(array, 0, array.length - 1);
    }

    //breaks array into partitions
    public void doMerge(int[] array, int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            doMerge(array, start, mid);
            doMerge(array, mid + 1, end);
            sort(array, start, mid, end);
        }
    }

    //sorts partition
    public static void sort(int[] array, int start, int mid, int end) {
        int[] holder = new int[(end - start) + 1];
        int holderIndex = 0;
        int firstIndex = start;
        int secondIndex = mid + 1;
        while (firstIndex <= mid && secondIndex <= end) {
            if (array[firstIndex] <= array[secondIndex]) {
                holder[holderIndex] = array[firstIndex];
                holderIndex = holderIndex + 1;
                firstIndex = firstIndex + 1;
            } else {
                holder[holderIndex] = array[secondIndex];
                holderIndex = holderIndex + 1;
                secondIndex = secondIndex + 1;
            }
        }
        while (firstIndex <= mid) {
            holder[holderIndex] = array[firstIndex];
            holderIndex = holderIndex + 1;
            firstIndex = firstIndex + 1;
        }
        while (secondIndex <= end) {
            holder[holderIndex] = array[secondIndex];
            holderIndex = holderIndex + 1;
            secondIndex = secondIndex + 1;
        }
        if (holder.length >= 0) System.arraycopy(holder, 0, array, start, holder.length);
    }
}
