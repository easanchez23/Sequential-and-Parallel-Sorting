import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Driver {
    public static int[] SIZES;
    static int[] model;

    //checks correctness of sort algorithm
    public static boolean isCorrect(int[] results) {
        for (int i = 0; i < results.length; i++) {
            if (i != results[i]) {
                return false;
            }
        }
        return true;
    }

    //randomly shuffles array
    public static void shuffle(ArrayList<int[]> holder) { // adapted from: https://www.journaldev.com/32661/shuffle-array-java
        Random rand = new Random();
        int[] model = holder.get(0);
        for (int i = 0; i < model.length; i++) {
            int randomIndexToSwap = rand.nextInt(model.length);
            int temp = model[randomIndexToSwap];
            for (int[] x : holder) {
                x[randomIndexToSwap] = x[i];
                x[i] = temp;
            }
        }
    }

    //runs base test and resets array
    public static void doBaseTest(String name, int[] array, Sort algo, HashMap<String, Long> times, int size) {
        long start = System.currentTimeMillis();
        algo.doSort(array);
        long time = System.currentTimeMillis() - start;
        times.put(name, time);
        System.out.printf("|  %6d |  %s          |            %6d |        0    |    %s |\n", size, name, time, isCorrect(array));
        System.arraycopy(model, 0, array, 0, array.length);

    }

    //runs parallel test and resets array
    public static void doParallelTest(String name, int[] array, Sort algo, HashMap<String, Long> times, int size, String compare) {
        long start = System.currentTimeMillis();
        algo.doSort(array);
        long time = System.currentTimeMillis() - start;
        System.out.printf("|  %6d | %s |           %6d |      %6.2f |    %s |\n", size, name,
                time, (double) times.get(compare) / time, isCorrect(array));
        System.arraycopy(model, 0, array, 0, array.length);


    }

    public static void main(String[] args) {
        boolean unbounded = false;
        SIZES = new int[]{1_000, 10_000, 50_000, 100_000, 1_000_000, 10_000_000, 50_000_000};
        if (args.length == 1) {
            if (args[0].equals("n")) {
                unbounded = true;
            } else if (!args[0].equals("y")) {
                throw new IllegalArgumentException();
            }
        } else if (args.length == 2) {
            int size;
            try {
                size = Integer.parseInt(args[0]);
                SIZES = new int[]{size};
                if (size <= 0)
                    throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.err.println("The 'samplesize' argument must be a positive integer.");
                System.exit(1);
            }
            if (args[1].equals("n")) {
                unbounded = true;
            } else if (!args[1].equals("y")) {
                throw new IllegalArgumentException();
            }
        }
        if (!unbounded) System.out.println("Running tests on " + Arrays.toString(SIZES) + ".\n" +
                "We will not run insertion sort on sizes greater than 100,000 because their runtimes are unreasonable.\n"
                + "If you would like to run this test to completion for all sizes pass 'n' (no quotes) as a argument. \n" +
                "If you would like to run on different array sizes, pass desired size and 'n' (no quotes) if you want to run insertion sorts til completion. \n"
                + "Pass desired size and 'y' (no quotes) if you do not want to run insertion sort on arrays size greater than 100,000. \n" +
                "You cannot only pass an array size.");
        if (unbounded)
            System.out.println("Running tests on " + Arrays.toString(SIZES) + ".\n" +
                    "If you would like to run this test without running insertion sort to completion on large array sizes, pass 'y' (no quotes) as a argument \n" +
                    "If you would like to run on different array sizes, pass desired size and 'n' (no quotes) if you want to run insertion sorts til completion. \n"
                    + "Pass desired size and 'y' (no quotes) if you do not want to run insertion sort on arrays size greater than 100,000. \n" +
                    "You cannot only pass an array size.");

        System.out.print("""
                |---------|----------------|------------------|-------------|---------|
                |  size   | type           |    runtime (ms)  | improvement | passed  |
                |---------|----------------|------------------|-------------|---------|
                """);

        HeapSort heapsort = new HeapSort();
        MergeSort mergesort = new MergeSort();
        RandomQuickSort rquicksort = new RandomQuickSort();
        InsertionSort insertionsort = new InsertionSort();
        ListInsertionSort linkinsert = new ListInsertionSort();
        MidQuickSort mquicksort = new MidQuickSort();
        ParallelHeapSort parallelheapsort = new ParallelHeapSort();
        ParallelMergeSort parallelmergesort = new ParallelMergeSort();
        ParallelRQuickSort parallelrquicksort = new ParallelRQuickSort();
        ParallelMQuickSort parallelmquicksort = new ParallelMQuickSort();
        ParallelInsertionSort parallelinsertionsort = new ParallelInsertionSort();
        for (int size : SIZES) {
            HashMap<String, Long> times = new HashMap<>();
            ArrayList<int[]> arrayHolder = new ArrayList<>();
            int[] array = new int[size];
            model = new int[size];
            for (int i = 0; i < array.length; i++) {
                model[i] = i;
                array[i] = i;
            }

            arrayHolder.add(model);
            arrayHolder.add(array);
            shuffle(arrayHolder);

            //initializes sort object

            //call new sort here
            doBaseTest("heap", array, heapsort, times, size);
            doBaseTest("merge", array, mergesort, times, size);
            doBaseTest("random quick", array, rquicksort, times, size);
            doBaseTest("middle quick", array, mquicksort, times, size);

            if (size <= 100_000 || unbounded) {
                doBaseTest("insert", array, insertionsort, times, size);
                doBaseTest("linked insert", array, linkinsert, times, size);
                doParallelTest("parallel insert", array, parallelinsertionsort, times, size, "linked insert");
            }
            doParallelTest("parallel heap", array, parallelheapsort, times, size, "heap");
            doParallelTest("parallel merge", array, parallelmergesort, times, size, "merge");
            doParallelTest("parallel random quick", array, parallelrquicksort, times, size, "random quick");
            doParallelTest("parallel middle quick", array, parallelmquicksort, times, size, "middle quick");
            System.out.print("|---------|----------------|------------------|-------------|---------|\n");
        }
    }
}

