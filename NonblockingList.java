import java.util.concurrent.atomic.*;

//nonBlockingList taken from lecture code
//used for parallel insertion sort
public class NonblockingList<T> {

    Node head;

    public NonblockingList() {
        this.head = new Node(Integer.MIN_VALUE);
        Node tail = new Node(Integer.MAX_VALUE);
        while (!head.next.compareAndSet(null, tail, false, false)) ;
    }

    public boolean add(T item) {
        int key = item.hashCode();
        boolean splice;
        while (true) {
            Window window = find(head, key);
            Node pred = window.pred;
            Node curr = window.curr;

            if (curr.key == key) {
                return false;
            }

            Node node = new Node(item);
            node.next = new AtomicMarkableReference<Node>(curr, false);
            if (pred.next.compareAndSet(curr, node, false, false)) {
                return true;
            }
        }
    }

    public boolean remove(T item) {
        int key = item.hashCode();
        boolean snip;
        while (true) {
            Window window = find(head, key);
            Node pred = window.pred;
            Node curr = window.curr;

            if (curr.key != key) {
                return false;
            }

            Node succ = curr.next.getReference();
            snip = curr.next.attemptMark(succ, true);
            if (!snip) {
                continue;
            }
            pred.next.compareAndSet(curr, succ, false, false);
            return true;
        }
    }

    public boolean contains(T item) {
        int key = item.hashCode();

        Window window = find(head, key);

        Node pred = window.pred;
        Node curr = window.curr;

        return (curr.key == key);
    }

    public class Node {
        T item;
        int key;
        AtomicMarkableReference<Node> next;

        public Node(int key) {
            this.item = null;
            this.key = key;
            this.next = new AtomicMarkableReference<Node>(null, false);
        }

        public Node(T item) {
            this.item = item;
            this.key = item.hashCode();
            this.next = new AtomicMarkableReference<Node>(null, false);
        }
    }

    class Window {

        public Node pred;

        public Node curr;

        public Window(Node pred, Node curr) {
            this.pred = pred;
            this.curr = curr;
        }
    }

    public Window find(Node head, long key) {
        Node pred = null;
        Node curr = null;
        Node succ = null;
        boolean[] marked = {false};
        boolean snip;

        retry:
        while (true) {
            pred = head;
            curr = pred.next.getReference();
            while (true) {
                succ = curr.next.get(marked);
                while (marked[0]) {
                    snip = pred.next.compareAndSet(curr, succ, false, false);
                    if (!snip) continue retry;
                    curr = pred.next.getReference();
                    succ = curr.next.get(marked);
                }
                if (curr.key >= key) {
                    return new Window(pred, curr);
                }
                pred = curr;
                curr = succ;
            }
        }

    }
}



