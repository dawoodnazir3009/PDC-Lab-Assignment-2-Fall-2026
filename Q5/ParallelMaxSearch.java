package Q5;

public class ParallelMaxSearch extends SensorSearchEngine 
{
    public ParallelMaxSearch(double[] sensorData, int startIdx, int endIdx, int numThreads) {
        super(sensorData, startIdx, endIdx, numThreads);
    }

    @Override 
    public void processGrid()
    {
        long t0 = System.nanoTime();
        int[] bounds = partitionBounds();
        double[] localMax = new double[numThreads];
        Thread[] workers = new Thread[numThreads];
 
        for (int i = 0; i < numThreads; i++) 
        {
            final int id = i;
            final int from = bounds[i];
            final int to = bounds[i + 1];
            workers[i] = new Thread(() -> {double max = -Double.MAX_VALUE;
                for (int j = from; j < to; j++) 
                {
                    if (sensorData[j] > max) 
                    {
                        max = sensorData[j];
                    }
                }
                localMax[id] = max;
                System.out.printf("  [%s] segment [%d..%d) local peak vibration = %.3f%n",Thread.currentThread().getName(), from, to, max);
            }, "MaxWorker-" + (i + 1));

            workers[i].start();
        }
 
        // Wait for all workers
        double globalMax = -Double.MAX_VALUE;

        for (int i = 0; i < numThreads; i++) 
        {
            try 
            {
                workers[i].join();
            } 
            catch (InterruptedException e) 
            {
                Thread.currentThread().interrupt();
                return;
            }
            globalMax = Math.max(globalMax, localMax[i]);
        }
 
        result = globalMax;
        elapseNanos = System.nanoTime() - t0;

    }
    
}
