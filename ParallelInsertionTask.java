import java.util.concurrent.Callable;

public class ParallelInsertionTask implements Callable<String> {
    int[] array;
    int start;
    int end;
    NonblockingList<Integer> list;

    public ParallelInsertionTask(int[] _array, int _start, int _end, NonblockingList<Integer> _list) {
        array = _array;
        start = _start;
        end = _end;
        list = _list;
    }

    //adds into nonblocking list
    public String call() {
        for (int i = start; i < end; i++) {
            list.add(array[i]);
        }
        return "done";
    }
}
