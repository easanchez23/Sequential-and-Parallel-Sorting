import java.util.Arrays;

public class ListInsertionSort implements Sort {
    @Override
    //does insertion sort on SortedLinkedList
    public void doSort(int[] array) {
        SortedLinkedList list = new SortedLinkedList();
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        InsertionNode node = list.head.next;
        for (int i = 0; i < array.length; i++) {
            array[i] = node.item;
            node = node.next;
        }
    }
}
