import java.util.PriorityQueue;

public class HeapSort implements Sort {
    //does heap sort by adding and removing from priority queue
    public void doSort(int[] array) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int j : array) {
            queue.add(j);
        }
        int index = 0;
        //removes elements in sorted order
        while (!queue.isEmpty()) {
            array[index] = queue.remove();
            index++;
        }
    }
}
