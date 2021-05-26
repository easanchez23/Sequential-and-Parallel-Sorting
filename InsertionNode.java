import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Node class for SortedLinkedList
public class InsertionNode {
    Integer item;
    InsertionNode next;
    Lock lock;

    public InsertionNode(Integer item) {
        this.item = item;
        this.next = null;
        this.lock = new ReentrantLock();
    }

}