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
    }
    
}
