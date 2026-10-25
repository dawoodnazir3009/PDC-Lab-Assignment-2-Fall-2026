package Q5;

public class ParallelMinSearch extends SensorSearchEngine 
{
    public ParallelMinSearch(double[] sensorData, int startIdx, int endIdx, int numThredas)
    {
        super(sensorData, startIdx, endIdx, numThredas);
    }

    @Override 
    public void processGrid()
    {
        long t0 = System.nanoTime();
        int[] bounds = partitionBounds();
        double[] localMin = new double[numThreads];
        Thread[] workers = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) 
        {
            final int id = i;
            final int from = bounds[i];
            final int to = bounds[i + 1];
            workers[i] = new Thread(() -> {double min = Double.MAX_VALUE;
                for (int j = from; j < to; j++) 
                {
                    if (sensorData[j] < min) 
                    {
                        min = sensorData[j];
                    }
                }
                localMin[id] = min;
                System.out.printf("  [%s] segment [%d..%d) local min temperature = %.3f%n",
                        Thread.currentThread().getName(), from, to, min);
            }, "MinWorker-" + (i + 1));
            workers[i].start();
        }
 
        // Wait for all workers, then reduce the partial results
        double globalMin = Double.MAX_VALUE;
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
            globalMin = Math.min(globalMin, localMin[i]);
        }
 
        result = globalMin;
        elapseNanos = System.nanoTime() - t0;
    }
}