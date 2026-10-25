package Q4;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;


public class ForkJoinPortfolioApp 
{
    private static final double RISK_CAP = 800.0;

    private static double serialSum(double[] a) 
    {
        double sum = 0.0;
        for (double v : a) 
        {
            sum += v;
        }
        return sum;
    }

    private static void serialStress(double[] a) 
    {
        for (int i = 0; i < a.length; i++) 
        {
            if (a[i] > RISK_CAP) 
            {
                a[i] -= a[i] * 0.05;
            }
        }
    }

    public static void main(String[] args) 
    {
        int size;
        if (args.length > 0) 
            size = Integer.parseInt(args[0]);
        else
            size = 10_000_000;

        // Generate synthetic asset values in [10, 1000)
        Random rnd = new Random(42);
        double[] original = new double[size];
        for (int i = 0; i < size; i++) 
        {
            original[i] = 10.0 + rnd.nextDouble() * 990.0;
        }

        ForkJoinPool pool = new ForkJoinPool();   // parallelism = available cores
        System.out.println("--- Parallel Portfolio Assessment Engine ---");
        System.out.print("Assets: " + size + " | Risk cap: " + RISK_CAP);
        System.out.println(" | Pool parallelism: " + pool.getParallelism());

        // ---- Single-run results (correctness) ----
        double serialRisk = serialSum(original);
        double parallelRisk = pool.invoke(new ParallelRiskCalculator(original, 0, size));

        double[] serialStressed = original.clone();
        serialStress(serialStressed);

        double[] parallelStressed = original.clone();
        pool.invoke(new PortfolioThresholdTask(parallelStressed, 0, size, RISK_CAP));

        int penalized = 0;
        for (double v : original) 
        {
            if (v > RISK_CAP) penalized++;
        }
        double serialStressedRisk = serialSum(serialStressed);
        double parallelStressedRisk = pool.invoke(new ParallelRiskCalculator(parallelStressed, 0, size));

        System.out.printf("%nCumulative risk (serial)   : %.4f%n", serialRisk);
        System.out.printf("Cumulative risk (parallel) : %.4f%n", parallelRisk);
        System.out.println("Assets above cap (penalized): " + penalized);
        System.out.printf("Post-stress risk (serial)  : %.4f%n", serialStressedRisk);
        System.out.printf("Post-stress risk (parallel): %.4f%n", parallelStressedRisk);
        System.out.println("Results match: "
                + (Math.abs(serialRisk - parallelRisk) < 1e-6 * serialRisk
                && Math.abs(serialStressedRisk - parallelStressedRisk) < 1e-6 * serialStressedRisk));

        
        int reps = Math.max(5, 2_000_000 / size);
        for (int w = 0; w < 50; w++) 
        {      // warm-up
            serialSum(original);
            pool.invoke(new ParallelRiskCalculator(original, 0, size));
            double[] t1 = original.clone();
            serialStress(t1);
            double[] t2 = original.clone();
            pool.invoke(new PortfolioThresholdTask(t2, 0, size, RISK_CAP));
        }

        long serialNs = 0, parallelNs = 0;
        for (int r = 0; r < reps; r++) 
        {
            double[] s = original.clone();
            double[] p = original.clone();

            long t = System.nanoTime();
            serialSum(s);
            serialStress(s);
            serialNs += System.nanoTime() - t;

            t = System.nanoTime();
            pool.invoke(new ParallelRiskCalculator(p, 0, size));
            pool.invoke(new PortfolioThresholdTask(p, 0, size, RISK_CAP));
            parallelNs += System.nanoTime() - t;
        }

        double serialMs = serialNs / 1e6 / reps;
        double parallelMs = parallelNs / 1e6 / reps;
        System.out.println("\n--- Processing time (average of " + reps + " runs) ---");
        System.out.printf("Serial   : %.4f ms%n", serialMs);
        System.out.printf("Parallel : %.4f ms%n", parallelMs);
        System.out.printf("Speedup  : %.2fx%n", serialMs / parallelMs);

        pool.shutdown();
    }
}