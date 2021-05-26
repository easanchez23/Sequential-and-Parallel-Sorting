# Sequential-and-Parallel-Sorting
This program comprehensively explores sorting algorithms and potential parallel optimization. I consider heap sort, insertion sort, random quick sort, middle quick sort, and merge sort–– as well as their parallel implementations. 

To run tests of sorting algorithms, first compile directory.
1. [default functionality] Pass no arguments and Driver will run all sorting algorithms on these sizes:
{1_000, 10_000, 50_000, 100_000, 1_000_000, 10_000_000, 50_000_000}. It will not run insertion sort on array sizes
greater than 100,000. Example terminal statement: java Driver
2. If you would like to run insertion sorts on ALL array sizes pass 'n' (no quotes) as an argument. (Note:
this will likely take hours to complete) Example terminal statement: java Driver n
3. If you would like to choose your own array size, pass in your desired int size followed by 'n' or 'y' (no quotes).
Passing 'n' will run insertion sorts on any array size. 'y' will run insertion sorts only on array sizes less than
or equal to 100,000. Example terminal statement: java Driver y      OR      java Driver n

Notes: You cannot pass in only an array size, you must also pass in 'y' or 'n.' While our computers do not run out of memory
running Driver on its default setting, your computer might. If this happens pass in array sizes individually or choose an array
size that interests you. You can also manually edit the Driver's code to create sizes more suitable to your machine.

