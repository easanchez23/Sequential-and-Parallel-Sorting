
public class SortedLinkedList {
    public InsertionNode head;

    public SortedLinkedList() {
        head = new InsertionNode(Integer.MIN_VALUE);
        head.next = new InsertionNode(Integer.MAX_VALUE);
    }

    public boolean add(int item) {
        while (true) {
            InsertionNode pred = head;
            InsertionNode curr = pred.next;
            while (curr.item <= item) {
                pred = curr;
                curr = curr.next;
            }
            InsertionNode node = new InsertionNode(item);
            node.next = curr;
            pred.next = node;
            return true;

        }
    }

    public boolean remove(int item) {
        while (true) {
            InsertionNode pred = head;
            InsertionNode curr = pred.next;
            while (curr.item < item) {
                pred = curr;
                if (curr.next == null) return false;
                curr = curr.next;
            }
            pred.next = curr.next;
            return true;
        }
    }
}
