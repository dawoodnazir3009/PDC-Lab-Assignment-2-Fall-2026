package Q7;

public class CacheLocalityBenchmark 
{
    private static final int N = 2000;
    private static final int ITERATIONS = 10;
    private static final int WARMUP = 2;

    private static final double[][] matrix = new double[N][N]; //2-D array
    

    static double traverseRowMajor()
    {

        double sum = 0.0;
        for ( int i = 0; i < N; i++)
        {
            for ( int j = 0; j < N; j++)
            {
                sum += matrix [i][j];

            }
        }
        return sum;
    }


    static double traverseColumnMajor()
    {
        double sum = 0.0;
        for ( int j = 0; j < N; j++)
        {
            for ( int i = 0; i < N; i++)
            {
                sum += matrix [i][j];

            }
        }
        return sum;
    }

    public static void main(String[] args)
    {
        for ( int i = 0; i < N; i++)
        {
            for ( int j = 0; j < N; j++)
            {
                 matrix [i][j] = (i + j) % 10;

            }
        }



        System.out.println("--- Cache Locality Benchmark --- ");
        System.out.print("Matrix: " + N + " x " + N + "doubles (");
        System.out.println((N * N * 8L / (1024*1024)) + " MB) | Iterations: " + ITERATIONS);
    
        double warmsink = 0;
        for (int w = 0; w < WARMUP; w++)
        {

            warmsink += traverseRowMajor();
            warmsink += traverseColumnMajor();
        }

        double[] rowms = new double[ITERATIONS];
        double[] colms = new double[ITERATIONS];
        double RowSum = 0, ColSum = 0;
        
        for (int it = 0; it < ITERATIONS; it++) 
        {
            long t0 = System.nanoTime();
            RowSum = traverseRowMajor();
        rowms[it] = (System.nanoTime() - t0) / 1e6;
 
            t0 = System.nanoTime();
            ColSum = traverseColumnMajor();
            colms[it] = (System.nanoTime() - t0) / 1e6;
        }

        System.out.println("\nIteration   Row-Major (ms)   Column-Major (ms)");
        double rowTotal = 0, colTotal = 0;
        for (int it = 0; it < ITERATIONS; it++) {
            System.out.printf("%5d       %12.3f      %14.3f%n", it + 1, rowms[it], colms[it]);
            rowTotal += rowms[it];
            colTotal += colms[it];
        }
 
        double rowAvg = rowTotal / ITERATIONS;
        double colAvg = colTotal / ITERATIONS;
 
        System.out.println("\n--- Results ---");
        System.out.printf("Row-Major average    : %.3f ms%n", rowAvg);
        System.out.printf("Column-Major average : %.3f ms%n", colAvg);
        System.out.printf("Speedup (Column / Row): %.2fx faster with Row-Major%n", colAvg / rowAvg);
        System.out.println("Sums match: " + (RowSum == ColSum) + " (" + RowSum + ")");
        if (warmsink < 0) 
        {
            System.out.println(warmsink);   // never true; keeps warm-up work from being discarded
        }
        System.out.println("\n--- Explanation ---");
        System.out.println("A Java double[N][N] is an array of row arrays; each row is a contiguous");
        System.out.println("block of " + (N * 8) + " bytes. A CPU cache line is 64 bytes = 8 doubles.");
        System.out.println("Row-Major reads neighbouring doubles, so one cache-line fill serves 8");
        System.out.println("accesses and the hardware prefetcher streams the next lines ahead of use.");
        System.out.println("Column-Major jumps " + (N * 8) + " bytes between accesses, so every access");
        System.out.println("touches a different cache line (and page), using 1 of 8 doubles loaded");
        System.out.println("and causing many more cache and TLB misses and memory stalls.");
    }
}
 