import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class FileWriter {
    public static void main(String[] args) throws IOException {
        //change paths to whatever you need
        File se = new File("seq.csv");
        File pa = new File("par.csv");
        File ar = new File("arr.csv");
        BufferedWriter seq = new BufferedWriter(new java.io.FileWriter(se));
        BufferedWriter arr = new BufferedWriter(new java.io.FileWriter(ar));
        BufferedWriter par = new BufferedWriter(new java.io.FileWriter(pa));
        int[] SIZES = new int[]{1_000, 10_000, 50_000, 100_000};
//        int[] SIZES = {1_000};
        for (int size : SIZES) {
            Long[][] times = new Long[3][1];
            for (int i = 0; i < 1; i++) {
                int[] array = new int[size];
                int[] model = new int[size];
                ArrayList<int[]> arrayHolder = new ArrayList<>();
                for (int j = 0; j < array.length; j++) {
                    model[j] = j;
                    array[j] = j;
                }
                arrayHolder.add(model);
                arrayHolder.add(array);
                Driver.shuffle(arrayHolder);

                ListInsertionSort s = new ListInsertionSort();//change algo
                long start = System.currentTimeMillis();
                s.doSort(array); // change algo
                long time = System.currentTimeMillis() - start;
                times[0][i] = time;
                System.arraycopy(model, 0, array, 0, array.length);

                ParallelInsertionSort r = new ParallelInsertionSort(); //change algo
                long ps = System.currentTimeMillis();
                r.doSort(array); // change algo
                long pt = System.currentTimeMillis() - ps;
                times[1][i] = pt;
                System.arraycopy(model, 0, array, 0, array.length);

                InsertionSort a = new InsertionSort(); //change algo
                long as = System.currentTimeMillis();
                r.doSort(array); // change algo
                long at = System.currentTimeMillis() - as;
                times[2][i] = at;
                System.arraycopy(model, 0, array, 0, array.length);

            }
            for (int j = 0; j < times[0].length; j++) {
                seq.write(times[0][j].toString() + "");
                if (j < times[0].length - 1) {
                    seq.write(",");
                }
            }
            seq.newLine();
            for (int j = 0; j < times[1].length; j++) {
                par.write(times[1][j].toString() + "");
                if (j < times[1].length - 1) {
                    par.write(",");
                }
            }
            par.newLine();
            for (int j = 0; j < times[2].length; j++) {
                arr.write(times[2][j].toString() + "");
//                if (j < times[0].length - 1) {
                    arr.write(",");
//                }
            }
//            seq.newLine();
        }
        par.close();
        seq.close();
        arr.close();
        System.err.println("done");

    }
}
