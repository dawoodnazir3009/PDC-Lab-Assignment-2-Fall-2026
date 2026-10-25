package Q5;
import java.util.Random;

public class SensorGridMonitorApp 
{
    private static final int SENSOR_COUNT = 50_000;
    private static final int WORKERS = 4;

    public static void main(String[] args)
    {


        Random rnd = new Random(7);
        double[] telemetry = new double[SENSOR_COUNT];

        for (int i =0; i < SENSOR_COUNT; i++)
        {
            telemetry[i] = 20.0 + rnd.nextDouble() * 60.0;
        }

        telemetry[12_345] = -12.5;
        telemetry[37_890] = 312.8;

        System.out.println("--- IoT Sensor Grid Monitor ---");
        System.out.println("Sensor nodes: " + SENSOR_COUNT + " | Worker threads per search: " + WORKERS);
 
        // Serial reference for verification and timing
        long s0 = System.nanoTime();
        double serialMin = Double.MAX_VALUE;
        double serialMax = -Double.MAX_VALUE;
        for (double v : telemetry) 
        {
            if (v < serialMin) serialMin = v;
            if (v > serialMax) serialMax = v;
        }
        long serialNs = System.nanoTime() - s0;
 
        SensorSearchEngine minSearch = new ParallelMinSearch(telemetry, 0, SENSOR_COUNT, WORKERS);
        SensorSearchEngine maxSearch = new ParallelMaxSearch(telemetry, 0, SENSOR_COUNT, WORKERS);
 
        // Run both reductions simultaneously, each on its own coordinator thread
        Thread minRunner = new Thread(minSearch::processGrid, "MinSearch-Coordinator");
        Thread maxRunner = new Thread(maxSearch::processGrid, "MaxSearch-Coordinator");
 
        System.out.println("\n[main] Launching min and max reductions simultaneously...");
        long p0 = System.nanoTime();
        minRunner.start();
        maxRunner.start();
        try 
        {
            minRunner.join();
            maxRunner.join();
        } 
        
        catch (InterruptedException e) 
        {
            Thread.currentThread().interrupt();
            return;
        }
        long parallelNs = System.nanoTime() - p0;
 
        System.out.println("\n--- Global Critical Thresholds ---");
        System.out.printf("Minimum temperature (parallel): %.3f%n", minSearch.getResult());
        System.out.printf("Peak vibration spike (parallel): %.3f%n", maxSearch.getResult());
        System.out.println("Matches serial reference: "
                + (minSearch.getResult() == serialMin && maxSearch.getResult() == serialMax));
 
        System.out.println("\n--- Execution Timing ---");
        System.out.printf("Min search time     : %.4f ms%n", minSearch.getElapseMillis());
        System.out.printf("Max search time     : %.4f ms%n", maxSearch.getElapseMillis());
        System.out.printf("Parallel total time : %.4f ms (min and max running together)%n", parallelNs / 1e6);
        System.out.printf("Serial reference    : %.4f ms%n", serialNs / 1e6);





    }


    
}
